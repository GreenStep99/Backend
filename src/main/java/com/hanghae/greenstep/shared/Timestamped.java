package com.hanghae.greenstep.shared;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

    @Getter
    @MappedSuperclass
    @EntityListeners(AuditingEntityListener.class)
    public abstract class Timestamped {

        @CreatedDate
        @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createdAt;

        @LastModifiedDate
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime modifiedAt;

    }
