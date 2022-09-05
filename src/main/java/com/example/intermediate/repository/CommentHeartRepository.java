package com.example.intermediate.repository;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.CommentHeart;
import com.example.intermediate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    List<CommentHeart> findAllByComment(Comment comment);
    //Optional<PostHeart> findPostHeartByPostAndMember(Post post, Member member);
    Optional<CommentHeart> findCommentHeartByCommentAndMember(Comment comment, Member member);
}
