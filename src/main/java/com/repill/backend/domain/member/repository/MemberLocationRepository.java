package com.repill.backend.domain.member.repository;

import com.repill.backend.domain.member.entity.Member;
import com.repill.backend.domain.member.entity.MemberLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberLocationRepository extends JpaRepository<MemberLocation, Long> {
    Optional<MemberLocation> findByMember(Member member);
}
