package com.repill.backend.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String location;

    private Double latitude;

    private Double longitude;

    public void setLocation(String location) {
        this.location = location;
    }

    public MemberLocation(Member member, String location) {
        this.member = member;
        this.location = location;
    }
}
