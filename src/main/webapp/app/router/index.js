import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate', // for vue-router 2.2+
]);
import Router, { RouteConfig } from 'vue-router';

const Home = () => import('@/home/Home.vue');
const Error = () => import('@/core/error/error.vue');
import Register from '@/account/register/Register.vue';
import TagPosts from '@/tag/TagPosts.vue';
import EditProfile from '@/account/profile/EditProfile.vue';
import PostDetail from '@/home/PostDetail.vue';
import MyPosts from '@/profile/MyPosts.vue';
import UserPosts from '@/profile/UserPosts.vue';
import Activate from '@/profile/Activate.vue';
Vue.use(Router);

// prettier-ignore
const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    /* search */
    {
      path: '/search',
      name: 'Search',
      component: () => import('@/search/Index.vue')
    },
    /* show by tag */
    {
      path: '/tag/:tag',
      name: 'Tag',
      component: TagPosts
    },
    {
      path: '/profile/edit',
      name: 'EditProfile',
      component: EditProfile
    },
    {
      path: '/post/:id',
      name: 'PostDetail',
      component: PostDetail
    },
    {
      path: '/profile/my-posts',
      name: 'MyPosts',
      component: MyPosts
    },
    {
      path: '/profile/my-likes',
      name: 'MyLikes',
      component: () => import('@/profile/MyLikes.vue')
    },
    {
      path: '/profile/my-bookmarks',
      name: 'MyBookmarks',
      component: () => import('@/profile/MyBookmarks.vue')
    },
    {
      path: '/posts/by-user/:login',
      name: 'UserPosts',
      component: UserPosts
    },
    {
      path: '/account/activate',
      name: 'Activate',
      component: Activate
    }
  ]
});

export default router;
