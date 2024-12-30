package com.solta.problemlog.service;

import com.solta.member.domain.Member;
import com.solta.member.repository.MemberJpaRepository;
import com.solta.problem.domain.Problem;
import com.solta.problem.dto.ProblemResponseDTO;
import com.solta.problem.repository.ProblemRepository;
import com.solta.problemlog.domain.ProblemLog;
import com.solta.problemlog.dto.request.ProblemLogRequestDTO;
import com.solta.problemlog.repository.ProblemLogRepository;
import com.solta.tier.domain.Tier;
import com.solta.tier.repository.TierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProblemLogService {
    private final ProblemLogRepository problemLogRepository;
    private final ProblemRepository problemRepository;
    private final TierRepository tierRepository;
    private final MemberJpaRepository memberRepository;

    public ProblemLogService(ProblemLogRepository problemLogRepository, ProblemRepository problemRepository,
                             TierRepository tierRepository, MemberJpaRepository memberRepository) {
        this.problemLogRepository = problemLogRepository;
        this.problemRepository = problemRepository;
        this.tierRepository = tierRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void registerProblemLog(ProblemLogRequestDTO problemLogRequestDTO, ProblemResponseDTO problemDetails, Long memberId){
        //데이터 존재 여부 확인 및 저장
        Problem problem = storeProblem(problemLogRequestDTO, problemDetails);

        //문제 기록 저장
        storeProblemLog(problemLogRequestDTO, problem, memberId);
    }

    private Problem storeProblem(ProblemLogRequestDTO problemLogRequestDTO, ProblemResponseDTO problemDetails){
        Problem problem = problemRepository.findProblemByProblemNumber(problemLogRequestDTO.getProblemNumber())
                .orElseGet(() -> {
                    int tierId = problemDetails.getLevel();

                    System.out.println(tierId);
                    Tier tier = tierRepository.findById(tierId)
                            .orElseThrow(() -> new IllegalArgumentException("티어가 존재하지 않음"));

                    return Problem.builder()
                            .problemNumber(problemLogRequestDTO.getProblemNumber())
                            .tier(tier)
                            .build();
                });
        return problemRepository.save(problem);
    }

    private void storeProblemLog(ProblemLogRequestDTO problemLogRequestDTO, Problem problem, Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 멤버가 존재하지 않음"));

        ProblemLog problemLog = ProblemLog.builder()
                .solvedDuration(problemLogRequestDTO.getSolveDuration())
                .problem(problem)
                .member(member)
                .build();

        problemLogRepository.save(problemLog);
    }
}
