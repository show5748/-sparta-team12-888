package com.example.intermediate.repository;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.CommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    List<CommentHeart> findAllByComment(Comment comment);
}
