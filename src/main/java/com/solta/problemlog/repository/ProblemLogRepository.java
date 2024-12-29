package com.solta.problemlog.repository;

import com.solta.problemlog.domain.ProblemLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemLogRepository extends JpaRepository<ProblemLog, Integer> {
}
