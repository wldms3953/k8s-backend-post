package com.welab.k8s_backend_post.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 게시글 Entity
@Slf4j
@Entity
@Table(
        name = "post",
        indexes = {
                @Index(columnList = "user_id"),
                @Index(columnList = "created_datetime"),
                @Index(columnList = "updated_datetime")
        }
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Long id; // 게시글 ID (기본키)

    @Column(name = "title", nullable = false)
    @Getter
    private String title; // 게시글 제목

    @Column(name = "content", nullable = false)
    @Getter
    private String content; // 게시글 내용

    // 게시글 수정 메서드: 제목/내용 수정 시 호출
    public void setPost(String title, String contents) {
        this.title = title;
        this.content = contents;
        this.updatedDatetime = LocalDateTime.now(); // 수정일 갱신
    }

    @Column(name = "user_id", nullable = false)
    @Getter @Setter
    private String userId; // 작성자 ID

    @Column(name = "created_datetime", nullable = false)
    @Getter
    private LocalDateTime createdDatetime = LocalDateTime.now(); // 생성 시각

    @Column(name = "updated_datetime")
    @Getter
    private LocalDateTime updatedDatetime; // 수정 시각

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostComment> comments = new ArrayList<>(); // 게시글에 달린 댓글 목록

    // 댓글 조회 메서드
    public List<PostComment> getComments() {
        return this.comments;
    }

    // 댓글 추가 메서드
    public void addComment(PostComment comment) {
        comment.setPost(this); // 연관관계 설정
        this.comments.add(comment);
    }

}
