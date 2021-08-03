package com.hyerijang.book.springboot.web.dto;

import com.hyerijang.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsResponseDto(Posts entity) {

        this.id = entity.getId();
        this.content = entity.getContent();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
    }

}
