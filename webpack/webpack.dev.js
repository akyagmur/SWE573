'use strict';
const BrowserSyncPlugin = require('browser-sync-webpack-plugin');
const webpack = require('webpack');
const path = require('path');

const { styleLoaders } = require('./vue.utils');
const config = require('./config');

module.exports = (env, options) => {
  const devConfig = {
    module: {
      rules: styleLoaders({ sourceMap: config.dev.cssSourceMap, usePostCSS: true }),
    },
    // cheap-module-eval-source-map is faster for development
    devtool: config.dev.devtool,
    output: {
      filename: 'app/[name].[contenthash].bundle.js',
      chunkFilename: 'app/[id].[chunkhash].chunk.js',
    },
    optimization: {
      moduleIds: 'named',
    },
    plugins: [
      {
        apply: compiler => {
          compiler.hooks.afterEmit.tap('AfterEmitPlugin', compilation => {
            if (options.watch) {
              // eslint-disable-next-line no-console
              // run `prettier` on generated files
              // execute command
              const filesPath = path.join(__dirname, '../src/main/webapp/');
              const exec = require('child_process').exec;
              exec(`prettier -w ${filesPath}`, (error, stdout, stderr) => {
                console.log(stdout);
              });
            }
          });
        }
      }
    ],
  };
  if (!options.env.WEBPACK_SERVE) return devConfig;
  devConfig.plugins.push(
    new BrowserSyncPlugin(
      {
        host: 'localhost',
        port: 9000,
        proxy: {
          target: `http://localhost:${options.watch ? '8080' : '9060'}`,
          ws: true,
        },
        socket: {
          clients: {
            heartbeatTimeout: 60000,
          },
        },
        /*
        ,ghostMode: { // uncomment this part to disable BrowserSync ghostMode; https://github.com/jhipster/generator-jhipster/issues/11116
          clicks: false,
          location: false,
          forms: false,
          scroll: false
        } */
      },
      {
        reload: true,
      }
    )
  );
  return devConfig;
};
