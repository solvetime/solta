package com.solta.problemlog.service;

import com.solta.member.domain.Member;
import com.solta.member.repository.MemberRepository;
import com.solta.problem.domain.Problem;
import com.solta.problem.dto.ProblemResponseDTO;
import com.solta.problem.repository.ProblemRepository;
import com.solta.problem.service.ProblemService;
import com.solta.problemlog.domain.ProblemLog;
import com.solta.problemlog.dto.request.ProblemLogRequestDTO;
import com.solta.problemlog.repository.ProblemLogRepository;
import com.solta.tier.domain.Tier;
import com.solta.tier.repository.TierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemLogService {
    private final ProblemLogRepository problemLogRepository;
    private final ProblemRepository problemRepository;
    private final TierRepository tierRepository;
    private final MemberRepository memberRepository;
    private final ProblemService problemService;

    @Transactional
    public void registerProblemLog(ProblemLogRequestDTO problemLogRequestDTO, Long memberId){
        //데이터 존재 여부 확인 및 저장
        Problem problem = storeProblem(problemLogRequestDTO);

        //문제 기록 저장
        storeProblemLog(problemLogRequestDTO, problem, memberId);
    }

    private Problem storeProblem(ProblemLogRequestDTO problemLogRequestDTO){
        Problem problem = problemRepository.findProblemByProblemNumber(problemLogRequestDTO.getProblemNumber())
                .orElseGet(() -> {
                    ProblemResponseDTO problemDetails = problemService.getProblemDetails(problemLogRequestDTO.getProblemNumber());
                    int tierId = problemDetails.getLevel();

                    Tier tier = tierRepository.findById(tierId)
                            .orElseThrow(() -> new IllegalArgumentException("티어가 존재하지 않음"));

                    return Problem.builder()
                            .problemNumber(problemLogRequestDTO.getProblemNumber())
                            .tier(tier)
                            .build();
                });

        try{
            return problemRepository.save(problem);
        }catch (DataIntegrityViolationException e){
            return problemRepository.findProblemByProblemNumber(problemLogRequestDTO.getProblemNumber())
                    .orElseThrow(() -> new IllegalStateException("문제를 불러올 수 없음"));
        }
    }

    private void storeProblemLog(ProblemLogRequestDTO problemLogRequestDTO, Problem problem, Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 멤버가 존재하지 않음"));

        ProblemLog problemLog = ProblemLog.builder()
                .solvedDuration(problemLogRequestDTO.getSolveDurationInSeconds())
                .problem(problem)
                .member(member)
                .build();

        problemLogRepository.save(problemLog);
    }
}
