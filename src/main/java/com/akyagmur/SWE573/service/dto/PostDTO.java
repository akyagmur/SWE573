package com.akyagmur.swe573.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.*;

import com.akyagmur.swe573.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the {@link com.akyagmur.swe573.domain.Post} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 500)
    private String title;

    private String url;

    @NotNull
    @Size(min = 0, max = 1000)
    private String content;

    private ZonedDateTime created_at;

    private ZonedDateTime updated_at;

    private String image_url;

    @Value("true")
    private Boolean is_private;

    @JsonIgnore
    private User author;

    // tags related to the post Tag model
    @JsonProperty("tags")
    private Set<TagDTO> tags;

    private Long created_by;

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public String getAuthorName() {
        if (author == null) {
            return "";
        }
        return author.getFirstName() + " " + author.getLastName();
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }

    public ZonedDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(ZonedDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getIs_private() {
        return is_private;
    }

    public void setIs_private(Boolean is_private) {
        this.is_private = is_private;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    /* created_by */
    public Long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, postDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", url='" + getUrl() + "'" +
            ", content='" + getContent() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", updated_at='" + getUpdated_at() + "'" +
            "}";
    }
}
