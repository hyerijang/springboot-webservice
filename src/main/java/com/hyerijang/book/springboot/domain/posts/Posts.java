package com.hyerijang.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//setter 사용 x
//Entity 클래스는 절대로 Request/Response 클래스로 사용해서는 안되기 때문에 dto에  PostsSaveRequestDto 별도로 생성
//=> Entity 클래스는  데이터베이스와 맞닿은 핵심 클래스이기 때문
@Getter
@NoArgsConstructor
@Entity
public class Posts  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}