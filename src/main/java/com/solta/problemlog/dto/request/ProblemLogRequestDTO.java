package com.solta.problemlog.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemLogRequestDTO {
    private Integer problemNumber;
    private String title;
    private List<String> tags;
    private Integer solveDuration;
}
