package com.blog.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

import com.blog.vo.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByUpdtDateDesc();

    List<Post> findAllByOrderByUpdtDateAsc();

    Post findOneById(Long id);

    List<Post> findByTitleContainingOrderByUpdtDateDesc(String query);

    List<Post> findByContentContainingIgnoreCase(String content);

    List<Post> findByTitleContainingIgnoreCase(String title);
}