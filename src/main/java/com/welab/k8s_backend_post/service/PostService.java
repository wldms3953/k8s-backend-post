package com.welab.k8s_backend_post.service;

import com.welab.k8s_backend_post.common.dto.PostCommentCreateDto;
import com.welab.k8s_backend_post.common.dto.PostCreateDto;
import com.welab.k8s_backend_post.common.exception.NotFound;
import com.welab.k8s_backend_post.domain.entity.Post;
import com.welab.k8s_backend_post.domain.entity.PostComment;
import com.welab.k8s_backend_post.domain.repository.PostCommentRepository;
import com.welab.k8s_backend_post.domain.repository.PostRepository;
import com.welab.k8s_backend_post.event.dto.PostCommentEvent;
import com.welab.k8s_backend_post.event.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 게시글 CRUD
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final KafkaMessageProducer kafkaMessageProducer;

    // 게시글 생성
    @Transactional
    public void createPost(PostCreateDto createDto) {
        Post post = createDto.toEntity();        // DTO → Entity 변환
        postRepository.save(post);               // DB에 저장
    }

    // 댓글 추가 + Kafka 메시지 발행
    @Transactional
    public void addPostComment(PostCommentCreateDto createDto) {
        // 댓글이 달릴 게시글 조회
        Post post = postRepository.findById(createDto.getPostId())
                .orElseThrow(() -> new NotFound("포스팅 글을 찾을 수 없습니다."));

        PostComment postComment = createDto.toEntity();   // DTO → Entity 변환
        postCommentRepository.save(postComment);          // DB에 댓글 저장
        post.addComment(postComment);                     // Post 엔티티에도 댓글 추가 (연관관계 설정)

        // Kafka 이벤트 발행 (댓글 생성 이벤트)
        kafkaMessageProducer.send(
                PostCommentEvent.Topic,                   // 토픽명
                PostCommentEvent.fromEntity("Create", postComment) // 이벤트 객체 생성
        );
    }
}
