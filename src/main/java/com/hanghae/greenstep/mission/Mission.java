package com.hanghae.greenstep.mission;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Mission {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(length = 80)
    private String missionName;

    @Column(length = 220)
    private String missionContent;

    @Column
    private String missionIconUrl;

    @Column
    private String missionImageUrl;

    @Column(length = 80)
    private String missionType;

    @Column
    private Boolean onShow = false;

    @Column(length = 80)
    private String tag;

    public void updateOnShow(Boolean check) {
        this.onShow = check;
    }
}
