package com.repill.backend.discardrecord.repository;

import com.repill.backend.discardrecord.entity.DiscardRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscardRecordJpaRepository extends JpaRepository<DiscardRecord, Long> {
}
