package com.hanghae.greenstep.SubmitMssionTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageUploadTest {



    @Test
    @DisplayName("가로세로가 320 이상인 사진은 리사이징하여 업로드 한다")
    public boolean imageUploadTest() throws Exception{
        Assertions.assertDoesNotThrow(() -> new ImageUploadTest(true));
    }
}
