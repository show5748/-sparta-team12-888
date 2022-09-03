package com.example.intermediate.repository;

import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    List<PostHeart> findAllByPost(Post post);
}
