package com.solta.problemlog.controller;

import com.solta.problem.dto.ProblemResponseDTO;
import com.solta.problem.service.ProblemService;
import com.solta.problemlog.dto.request.ProblemLogRequestDTO;
import com.solta.problemlog.service.ProblemLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problemLog")
public class ProblemLogController {
    private final ProblemLogService problemLogService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerProblemLog(@Valid @RequestBody ProblemLogRequestDTO problemLogRequestDTO){
        Long dummyMemberId = 1L;
        problemLogService.registerProblemLog(problemLogRequestDTO, dummyMemberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
