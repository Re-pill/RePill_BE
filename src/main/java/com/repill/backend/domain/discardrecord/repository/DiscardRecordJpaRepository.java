package com.repill.backend.domain.discardrecord.repository;

import com.repill.backend.domain.discardrecord.entity.DiscardRecord;
import com.repill.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscardRecordJpaRepository extends JpaRepository<DiscardRecord, Long> {

    List<DiscardRecord> findAllByMember(Member member);
}
