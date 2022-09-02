package com.hanghae.greenstep.post;

public class PostResponseDto {
    private Long postId;
    private String imgUrl;

    public PostResponseDto(Post post){
        this.postId = post.getId();
        this.imgUrl = post.getImgUrl();

    }
}
