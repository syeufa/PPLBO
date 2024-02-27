package com.blog.controller;

// import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blog.service.CommentService;
import com.blog.vo.Comment;
import com.blog.vo.Result;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    public Object savePost(jakarta.servlet.http.HttpServletResponse response, @RequestBody Comment commentParam) {
        Comment comment = new Comment(commentParam.getPostId(), commentParam.getUser(), commentParam.getComment());
        boolean isSuccess = commentService.saveComment(comment);

        if (isSuccess) {
            return new Result(200, "Success");
        } else {
            response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Fail");
        }
    }

    @GetMapping("/comments")
    public List<Comment> getComments(@RequestParam("post_id") Long postId) {
        return commentService.getCommentList(postId);
    }

    @GetMapping
    public Object getCommentById(@RequestParam("id") Long id) {
        return commentService.getComment(id);
    }

    @DeleteMapping
    public Object deleteComment(HttpServletResponse response, @RequestParam("id") Long id) {
        boolean isDeleted = commentService.deleteComment(id);

        if (isDeleted) {
            return new Result(200, "Comment deleted successfully");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new Result(404, "Comment not found");
        }
    }

    @GetMapping("/search")
    public List<Comment> searchComments(
            @RequestParam(name = "post_id") Long postId,
            @RequestParam(name = "query") String query) {
        return commentService.searchComments(postId, query);
    }

}