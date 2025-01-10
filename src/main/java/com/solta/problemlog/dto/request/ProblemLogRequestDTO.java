package com.solta.problemlog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProblemLogRequestDTO {
    private Integer problemNumber;
    private Integer solveDuration;
}
