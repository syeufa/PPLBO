package com.blog.service;

// import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.blog.repository.PostJpaRepository;
import com.blog.repository.PostRepository;
import com.blog.vo.Post;

import io.micrometer.common.util.StringUtils;

@Service
public class PostService {
    private static List<Post> posts;

    // @Autowired
    // JpaRepository jpaRepository;

    @Autowired
    PostJpaRepository postJpaRepository;

    @Autowired
    PostRepository postRepository;

    // private CrudRepository<Post, Long> jpaRepository;

    public Post getPost(Long id) {
        // Creating a new Post instance
        Post post = postJpaRepository.findOneById(id);
        return post;
    }

    public List<Post> getPosts() {
        posts = postJpaRepository.findAllByOrderByUpdtDateDesc();
        return posts;
    }

    public List<Post> getPostsOrderByUpdtAsc() {
        return postJpaRepository.findAllByOrderByUpdtDateAsc();
    }

    public List<Post> getPostsOrderByRegDesc() {
        return postRepository.findPostOrderByRegDateDesc();
    }

    // public List<Post> searchPostByTitle(String query) {
    // List<Post> posts = postRepository.findPostLikeTitle(query);
    // return posts;
    // }

    public List<Post> searchPostByContent(String query) {
        return postRepository.findPostLikeContent(query);
    }

    public boolean savePost(Post post) {
        Post result = postJpaRepository.save(post);
        boolean isSuccess = true;

        if (result == null) {
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean deletePost(Long id) {
        Post result = postJpaRepository.findOneById(id);

        if (result == null)
            return false;

        postJpaRepository.deleteById(id);
        return true;
    }

    public boolean updatePost(Post post) {
        Post result = postJpaRepository.findOneById(post.getId());

        if (result == null)
            return false;

        if (!StringUtils.isEmpty(post.getTitle())) {
            result.setTitle(post.getTitle());
        }

        postJpaRepository.save(result);

        return true;
    }

    public List<Post> findByTitle(String query) {
        List<Post> posts = postJpaRepository.findByTitleContainingOrderByUpdtDateDesc(query);
        return posts;
    }

    public List<Post> findByContent(String content) {
        return postRepository.findByContentContainingIgnoreCase(content);
    }

}