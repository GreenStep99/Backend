package com.hanghae.greenstep.mission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Mission {

    @GeneratedValue
    @Id
    private Long id;
    @Column(length = 80)
    private String missionName;

    @Column(length = 220)
    private String missionContent;

    @Column
    private String missionImageUrl;

    @Column(length = 80)
    private String missionType;

    @Column
    private Boolean onShow = false;

}
