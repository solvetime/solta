package com.solta.problemlog.controller;

import com.solta.problem.dto.ProblemResponseDTO;
import com.solta.problem.service.ProblemService;
import com.solta.problemlog.dto.request.ProblemLogRequestDTO;
import com.solta.problemlog.service.ProblemLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/problemLog")
public class ProblemLogController {
    private ProblemLogService problemLogService;
    private ProblemService problemService;

    public ProblemLogController(ProblemLogService problemLogService, ProblemService problemService) {
        this.problemLogService = problemLogService;
        this.problemService = problemService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerProblemLog(@RequestBody ProblemLogRequestDTO problemLogRequestDTO){
        Long dummyMemberId = 1L;
        ProblemResponseDTO problemDetails = problemService.getProblemDetails(problemLogRequestDTO.getProblemNumber());
        problemLogService.registerProblemLog(problemLogRequestDTO, problemDetails, dummyMemberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
