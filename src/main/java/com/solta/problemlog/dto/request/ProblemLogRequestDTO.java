package com.solta.problemlog.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemLogRequestDTO {
    private Integer problemNumber;
    private Integer solveDuration;
}
