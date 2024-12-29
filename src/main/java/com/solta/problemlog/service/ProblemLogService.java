package com.solta.problemlog.service;

import com.solta.problem.domain.Problem;
import com.solta.problem.repository.ProblemRepository;
import com.solta.problemlog.domain.ProblemLog;
import com.solta.problemlog.dto.request.ProblemLogRequestDTO;
import com.solta.problemlog.repository.ProblemLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProblemLogService {
    private final ProblemLogRepository problemLogRepository;
    private final ProblemRepository problemRepository;

    public ProblemLogService(ProblemLogRepository problemLogRepository, ProblemRepository problemRepository) {
        this.problemLogRepository = problemLogRepository;
        this.problemRepository = problemRepository;
    }

    @Transactional
    public ProblemLog registerProblemLog(ProblemLogRequestDTO problemLogRequestDTO){
        //데이터 존재 여부 확인 및 저장
        Problem problem = storeProblem(problemLogRequestDTO);

        //문제 기록 저장
        return storeProblemLog(problemLogRequestDTO, problem);
    }

    private Problem storeProblem(ProblemLogRequestDTO problemLogRequestDTO){
        Problem problem = problemRepository.findProblemByProblemNumber(problemLogRequestDTO.getProblemNumber())
                .orElseGet(() -> Problem.builder()
                            .problemNumber(problemLogRequestDTO.getProblemNumber())
                            .build()
                );
        return problemRepository.save(problem);
    }

    private ProblemLog storeProblemLog(ProblemLogRequestDTO problemLogRequestDTO, Problem problem){
        ProblemLog problemLog = ProblemLog.builder()
                .solvedDuration(problemLogRequestDTO.getSolveDuration())
                .problem(problem)
                .build();

        return problemLogRepository.save(problemLog);
    }
}
