package com.akyagmur.swe573.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 500)
    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "url")
    private String url;

    @NotNull
    @Size(min = 0, max = 1000)
    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @NotNull
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long created_by;

    @Column(name = "updated_by")
    private Long updated_by;

    @Column(name = "created_at")
    private ZonedDateTime created_at;

    @Column(name = "updated_at")
    private ZonedDateTime updated_at;

    // default value is true
    @Column(name = "is_private")
    private Boolean is_private;

    @Column(name = "image_url")
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = false, updatable = false)
    private User author;

    @ManyToMany
    @JoinTable(
        name = "rel_post__tag",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    Set<Tag> tags;

    public Long getId() {
        return this.id;
    }

    public Post id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Post title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public Post url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return this.content;
    }

    public Post content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreated_by() {
        return this.created_by;
    }

    public Post created_by(Long created_by) {
        this.setCreated_by(created_by);
        return this;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public Long getUpdated_by() {
        return this.updated_by;
    }

    public Post updated_by(Long updated_by) {
        this.setUpdated_by(updated_by);
        return this;
    }

    public void setUpdated_by(Long updated_by) {
        this.updated_by = updated_by;
    }

    public ZonedDateTime getCreated_at() {
        return this.created_at;
    }

    public Post created_at(ZonedDateTime created_at) {
        this.setCreated_at(created_at);
        return this;
    }

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }

    public ZonedDateTime getUpdated_at() {
        return this.updated_at;
    }

    public Post updated_at(ZonedDateTime updated_at) {
        this.setUpdated_at(updated_at);
        return this;
    }

    public void setUpdated_at(ZonedDateTime updated_at) {
        this.updated_at = updated_at;
    }

    // many to many relation ship with tag model
    public Set<Tag> getTags() {
        return this.tags;
    }

    public Post tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Boolean getIs_private() {
        return this.is_private;
    }

    public Post is_private(Boolean is_private) {
        this.setIs_private(is_private);
        return this;
    }

    public void setIs_private(Boolean is_private) {
        this.is_private = is_private;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public Post image_url(String image_url) {
        this.setImage_url(image_url);
        return this;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public User getAuthor() {
        return this.author;
    }

    @PreUpdate
    public void preUpdate() {
        updated_at = ZonedDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", url='" + getUrl() + "'" +
            ", content='" + getContent() + "'" +
            ", created_by=" + getCreated_by() +
            ", updated_by=" + getUpdated_by() +
            ", created_at='" + getCreated_at() + "'" +
            ", updated_at='" + getUpdated_at() + "'" +
            "}";
    }
}
