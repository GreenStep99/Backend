package com.hanghae.greenstep.notice.embeddable;

import com.hanghae.greenstep.shared.notice.RelatedURL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RelatedURLTest {

    @Test
    @DisplayName("관련 링크가 공백이 아닐 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new RelatedURL("koner.kr/study"));
    }
}