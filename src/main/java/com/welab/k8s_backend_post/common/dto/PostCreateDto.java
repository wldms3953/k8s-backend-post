package com.welab.k8s_backend_post.common.dto;

import com.welab.k8s_backend_post.common.web.context.GatewayRequestHeaderUtils;
import com.welab.k8s_backend_post.domain.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateDto {
    @NotBlank(message = "타이틀을 입력하세요.")
    private String title;
    @NotBlank(message = "본문을 입력하세요.")
    private String content;
    public Post toEntity() {
        Post post = new Post();
        post.setUserId(GatewayRequestHeaderUtils.getUserIdOrThrowException());
        post.setPost(this.title, this.content);
        return post;
    }
}
