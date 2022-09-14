package com.hanghae.greenstep.shared.notice;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class RelatedURL {
    private String url;

    public RelatedURL(String url) {
        this.url = url;
    }
}
