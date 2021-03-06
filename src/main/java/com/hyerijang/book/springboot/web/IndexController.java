package com.hyerijang.book.springboot.web;

import com.hyerijang.book.springboot.config.auth.LoginUser;
import com.hyerijang.book.springboot.config.auth.dto.SessionUser;
import com.hyerijang.book.springboot.service.posts.PostsService;
import com.hyerijang.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

//    @GetMapping("/")
//    public String index(Model model, @LoginUser SessionUser user) {
//        model.addAttribute("posts", postsService.findAllDesc());
//        if (user != null) {
//            model.addAttribute("userName", user.getName());
//        }
//        return "index";
//    }

    @GetMapping("/")
    public String index(Model model , @LoginUser SessionUser user) //서버 템플릿 엔진에서 사용할 수 있는 객체 저장
    {
        model.addAttribute("posts", postsService.findAllDesc()); //index.mustache 에 post라는 이름으로  postsService.findAllDesc()의 결과 저장
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
