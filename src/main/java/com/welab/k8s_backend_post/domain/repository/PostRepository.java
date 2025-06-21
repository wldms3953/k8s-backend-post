package com.welab.k8s_backend_post.domain.repository;


import com.welab.k8s_backend_post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByUserId(String userId);
}
