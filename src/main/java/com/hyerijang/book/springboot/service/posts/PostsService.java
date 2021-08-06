package com.hyerijang.book.springboot.service.posts;

import com.hyerijang.book.springboot.domain.posts.Posts;
import com.hyerijang.book.springboot.domain.posts.PostsRepository;
import com.hyerijang.book.springboot.web.dto.PostsListResponseDto;
import com.hyerijang.book.springboot.web.dto.PostsResponseDto;
import com.hyerijang.book.springboot.web.dto.PostsSaveRequestDto;
import com.hyerijang.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다 id=" + id));
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
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    // @Transactional(readOnly = true)
    // 트랜잭션 범위는 유지하고 조회 기능만 남겨둠 => 조회속도 개선 (등록,수정,삭제 기능이 없는 서비스 서비스 메소드에서 사용하는 것 추천)
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc()
                .stream()
                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());

        //자바8부터 Stream 을 사용 할 수 있습니다.
        //기존에 자바 컬렉션이나 배열의 원소를 가공할떄, for문, foreach 등으로 원소 하나씩 골라내여 가공을 하였다면,
        //Stream 을 이용하여 람다함수형식으로 간결하고 깔끔하게 요소들의 처리가 가능

        // 배열의 원소를 가공하는데 있어 map, filter, sorted 등 이 있습니다.
        // map은 요소들을 특정조건에 해당하는 값으로 변환해 줍니다. 요소들을 대,소문자 변형 등 의 작업을 하고 싶을떄 사용 가능 합니다.
        // filter는 요소들을 조건에 따라 걸러내는 작업을 해줍니다. 길이의 제한, 특정문자포함 등 의 작업을 하고 싶을때 사용 가능합니다.
        // sorted는 요소들을 정렬해주는 작업을 해줍니다.

        // 요소들의 가공이 끝났다면 리턴해줄 결과를 collect 를 통해 만들어줍니다.
    }


}
