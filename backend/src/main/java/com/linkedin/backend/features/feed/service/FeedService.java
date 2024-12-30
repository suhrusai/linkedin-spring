package com.linkedin.backend.features.feed.service;

import com.linkedin.backend.features.authentication.model.AuthenticationUser;
import com.linkedin.backend.features.authentication.repository.AuthenticationUserRepository;
import com.linkedin.backend.features.feed.dto.PostDto;
import com.linkedin.backend.features.feed.model.Comment;
import com.linkedin.backend.features.feed.model.Post;
import com.linkedin.backend.features.feed.repository.CommentRepository;
import com.linkedin.backend.features.feed.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    private final PostRepository postRepository;
    private final AuthenticationUserRepository userRepository;
    private final CommentRepository commentRepository;

    public FeedService(PostRepository postRepository, AuthenticationUserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public Post createPost(PostDto postDto, Long id) {
        AuthenticationUser author = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = new Post(postDto.getContent(),author);
        post.setPicture(postDto.getPicture());
        return postRepository.save(post);
    }

    public Post editPost(Long postId, Long authorId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        AuthenticationUser author = userRepository.findById(authorId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(!post.getAuthor().equals(author)) {
            throw new IllegalArgumentException("You are not allowed to edit this post");
        }

        post.setPicture(postDto.getPicture());
        post.setContent(postDto.getContent());
        return postRepository.save(post);
    }

    public List<Post> getFeedPosts(Long authenticatedUserId) {
        return postRepository.findByAuthorIdNotOrderByCreatedAtDesc(authenticatedUserId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public void deletePost(Long postId, Long id) {
        AuthenticationUser author = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        if(!post.getAuthor().equals(author)) {
            throw new IllegalArgumentException("You are not allowed to delete this post");
        }
        postRepository.delete(post);
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.getPostsByAuthorId(userId);
    }

    public Post likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        AuthenticationUser user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(post.getLikes().contains(user)){
            post.getLikes().remove(user);
        }else{
            post.getLikes().add(user);
        }
        return postRepository.save(post);
    }

    public Comment addComment(Long postId, Long id, String content) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        AuthenticationUser user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = new Comment(post,user,content);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, Long id) {
        AuthenticationUser author = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        if(!comment.getAuthor().equals(author)) {
            throw new IllegalArgumentException("You are not allowed to delete this comment");
        }
        commentRepository.delete(comment);
    }

    public Comment editComment(Long commentId, Long id, String content) {
        AuthenticationUser author = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        if(!comment.getAuthor().equals(author)) {
            throw new IllegalArgumentException("You are not allowed to edit this comment");
        }
        comment.setContent(content);
        return commentRepository.save(comment);
    }
}
