package com.solta.problem.controller;

import com.solta.problem.dto.ProblemResponseDTO;
import com.solta.problem.service.ProblemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping("/{problemId}")
    public ResponseEntity<ProblemResponseDTO> getProblem(@PathVariable int problemId){
        ProblemResponseDTO problem = problemService.getProblemDetails(problemId);
        return ResponseEntity.ok(problem);
    }
}
