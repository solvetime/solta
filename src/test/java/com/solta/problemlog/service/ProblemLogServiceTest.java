package com.solta.problemlog.service;

import com.solta.problem.domain.Problem;
import com.solta.problem.dto.ProblemResponseDTO;
import com.solta.problem.repository.ProblemRepository;
import com.solta.problem.service.ProblemService;
import com.solta.problemlog.dto.request.ProblemLogRequestDTO;
import com.solta.tier.domain.Tier;
import com.solta.tier.repository.TierRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith({SpringExtension.class})
class ProblemLogServiceTest {
    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private TierRepository tierRepository;

    @InjectMocks
    private ProblemLogService problemLogService; // storeProblem 메서드가 포함된 서비스 클래스

    @InjectMocks
    private ProblemService problemService;

    @Test
    @DisplayName("동시에 같은 문제를 등록할 때 첫 요청만 성공한다")
    void storeProblem_ConcurrencyTest_Simple() throws InterruptedException {
        // Arrange
        ProblemLogRequestDTO request = new ProblemLogRequestDTO(2557, 120);
        ProblemResponseDTO problemDetails = ProblemResponseDTO.builder().level(1).build();
        Tier mockTier = new Tier(1, "Bronze");
        Problem savedProblem = Problem.builder().problemNumber(2557).tier(mockTier).build();

        when(problemRepository.findProblemByProblemNumber(12345))
                .thenReturn(Optional.empty()) // 처음에는 문제 존재하지 않음
                .thenReturn(Optional.of(savedProblem)); // 이후에는 문제 존재

        when(tierRepository.findById(1)).thenReturn(Optional.of(mockTier));
        when(problemRepository.save(any(Problem.class)))
                .thenReturn(savedProblem) // 첫 저장 성공
                .thenThrow(new DataIntegrityViolationException("Duplicate entry")); // 이후 저장은 예외 발생

        int threadCount = 5; // 동시 요청 개수
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());

        // Act
        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                try {
                    problemLogService.storeProblem(request, problemDetails);
                } catch (Throwable e) {
                    exceptions.add(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 작업 완료할 때까지 대기

        // Assert
        assertEquals(1, threadCount - exceptions.size(), "성공한 요청은 하나여야 합니다");
        assertEquals(threadCount - 1, exceptions.size(), "예외는 나머지 요청 수만큼 발생해야 합니다");
        executor.shutdown();
    }

}