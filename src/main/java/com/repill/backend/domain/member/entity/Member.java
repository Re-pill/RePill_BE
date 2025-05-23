package com.repill.backend.domain.member.entity;

import com.repill.backend.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private Integer ecoContribution = 0;

    private Integer level = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_rank")
    private Rank memberRank;

    private Integer participantCount;
}
