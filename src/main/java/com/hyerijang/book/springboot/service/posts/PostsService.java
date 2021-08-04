package com.hyerijang.book.springboot.service.posts;

import com.hyerijang.book.springboot.domain.posts.Posts;
import com.hyerijang.book.springboot.domain.posts.PostsRepository;
import com.hyerijang.book.springboot.web.dto.PostsResponseDto;
import com.hyerijang.book.springboot.web.dto.PostsSaveRequestDto;
import com.hyerijang.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();

    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다 id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent()); //영속성 컨텐츠 이므로 쿼리 안날려도 됨. p.113
        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }


    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }


}
