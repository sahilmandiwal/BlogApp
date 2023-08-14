package com.sahil.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahil.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
