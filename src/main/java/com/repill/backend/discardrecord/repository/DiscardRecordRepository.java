package com.repill.backend.discardrecord.repository;

import com.repill.backend.discardrecord.entity.DiscardRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscardRecordRepository extends JpaRepository<DiscardRecord, Long> {
    List<DiscardRecord> findAllByMemberId (Long memberId);
}
