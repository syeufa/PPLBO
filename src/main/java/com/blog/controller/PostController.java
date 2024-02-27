package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestMapping;
import com.blog.service.PostService;
import com.blog.vo.Post;
import com.blog.vo.Result;

import jakarta.servlet.http.HttpServletResponse;

// import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/post")
    public Post getPost(@RequestParam("id") Long id) {
        Post post = postService.getPost(id);
        return post;
    }

    @GetMapping("/posts")
    public List<Post> getPosts() {
        List<Post> posts = postService.getPosts();

        return posts;
    }

    @GetMapping("/post/{id}")
    public Post getPostPathParam(@PathVariable("id") Long id) {
        Post post = postService.getPost(id);
        return post;
    }

    @GetMapping("/posts/updtdate/asc")
    public List<Post> getPostsOrderByUpdtDateAsc() {
        return postService.getPostsOrderByUpdtAsc();
    }

    @GetMapping("/posts/regdate/desc")
    public List<Post> getPostsOrderByRegDesc() {
        return postService.getPostsOrderByRegDesc();
    }

    // @GetMapping("/posts/search/title")
    // public List<Post> searchByTitle(@RequestParam("query") String query) {
    // List<Post> posts = postService.searchPostByTitle(query);
    // return posts;
    // }

    // @GetMapping("/posts/search/content")
    // public List<Post> searchByContent(@RequestParam("query") String query) {
    // return postService.searchPostByContent(query);
    // }

    @PostMapping("/post")
    public Object savePost(HttpServletResponse response, @RequestBody Post postParam) {
        Post post = new Post(postParam.getUser(), postParam.getTitle(), postParam.getContent());
        boolean isSuccess = postService.savePost(post);

        if (isSuccess) {
            return new Result(200, "Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Fail");
        }

    }

    @DeleteMapping("/post")
    public Object deletePost(HttpServletResponse response, @RequestParam("id") Long id) {
        boolean isSuccess = postService.deletePost(id);

        if (isSuccess) {
            return new Result(200, "Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Fail");
        }
    }

    @PutMapping("/post")
    public Object modifyPost(HttpServletResponse response, @RequestBody Post postParam) {
        Post post = new Post(postParam.getId(), postParam.getTitle(), postParam.getContent());
        boolean isSuccess = postService.updatePost(post);

        if (isSuccess) {
            return new Result(200, "Succes");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Fail");
        }

    }

    // Endpoint baru dengan metode JPA
    @GetMapping("/search/content")
    public List<Post> searchByContent(@RequestParam("content") String content) {
        return postService.findByContent(content);
    }

    // Endpoint '/search/title' yang sudah ada
    @GetMapping("/search/title")
    public List<Post> searchByTitle(@RequestParam("title") String title) {
        return postService.findByTitle(title);
    }

}