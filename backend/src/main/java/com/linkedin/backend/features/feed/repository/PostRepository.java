package com.linkedin.backend.features.feed.repository;

import com.linkedin.backend.features.feed.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorIdNotOrderByCreatedAtDesc(Long authenticatedUserId);

    List<Post> findAllByOrderByCreatedAtDesc();


    List<Post> getPostsByAuthorId(Long userId);
}
