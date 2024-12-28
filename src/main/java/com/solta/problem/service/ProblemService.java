package com.solta.problem.service;

import com.solta.problem.dto.ProblemResponseDTO;
import com.solta.problemlog.util.ProblemApiClient;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {
    private final ProblemApiClient problemApiClient;

    public ProblemService(ProblemApiClient problemApiClient) {
        this.problemApiClient = problemApiClient;
    }

    public ProblemResponseDTO getProblemDetails(int problemId){
        return problemApiClient.fetchProblemById(problemId);
    }
}
