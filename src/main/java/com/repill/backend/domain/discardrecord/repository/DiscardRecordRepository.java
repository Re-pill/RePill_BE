package com.repill.backend.domain.discardrecord.repository;

import com.repill.backend.domain.discardrecord.entity.DiscardRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscardRecordRepository extends JpaRepository<DiscardRecord, Long> {
    List<DiscardRecord> findAllByMemberId (Long memberId);
}
