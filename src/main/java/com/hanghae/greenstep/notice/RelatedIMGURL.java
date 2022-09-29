package com.hanghae.greenstep.notice;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class RelatedIMGURL {
    private static final int MAX_LENGTH = 255;

    @Column(nullable = false, length = MAX_LENGTH)
    private String imgUrl;

    public RelatedIMGURL(String imgUrl) {
        if (isNotValidRelatedIMGURL(imgUrl)) {
            throw new CustomException(ErrorCode.VALID_NOT_IMG_URL);
        }
        this.imgUrl = imgUrl;
    }

    private boolean isNotValidRelatedIMGURL(String imgUrl) {
        return Objects.isNull(imgUrl) || imgUrl.length() > MAX_LENGTH || imgUrl.isEmpty();

    }
}