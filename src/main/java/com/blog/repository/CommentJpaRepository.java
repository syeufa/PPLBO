package com.blog.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.vo.Comment;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByRegDateDesc(Long postId);

    List<Comment> findByPostIdAndContentContainingIgnoreCase(Long postId, String query);

}
