package com.repill.backend.discardrecord.repository;

import com.repill.backend.discardrecord.entity.DiscardRecord;
import com.repill.backend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscardRecordJpaRepository extends JpaRepository<DiscardRecord, Long> {

    List<DiscardRecord> findAllByMember(Member member);
}
