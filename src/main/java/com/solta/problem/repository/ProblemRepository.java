package com.solta.problem.repository;

import com.solta.problem.domain.Problem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {
    Optional<Problem> findProblemByProblemNumber(Integer problemNumber);
}

