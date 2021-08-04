package com.hyerijang.book.springboot.web;

import com.hyerijang.book.springboot.service.posts.PostService;
import com.hyerijang.book.springboot.web.dto.PostsResponseDto;
import com.hyerijang.book.springboot.web.dto.PostsSaveRequestDto;
import com.hyerijang.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController //RestController!
public class PostsApiController {

    private final PostService postService;
    //*HTTP메서드*
    //GET은 서버의 리소스에서 데이터를 요청시
    //POST는 서버의 리소스를 새로 생성(+ 업데이트)할 때 사용
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    //*REST방식*
    //실제로는 요청을 보낼 때 GET, POST 외에도 PUT, PATCH, DELETE 등 더 자세한 동작을 정의할 수 있습니다.
    //보통 PUT은 전체 수정(대체), PATCH는 부분 수정, DELETE는 제거 요청 시 사용합니다.
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postService.update(id,requestDto);
    }


    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findByID(@PathVariable Long id) {
        return postService.findById(id);
    }


}
