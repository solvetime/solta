package com.solta.problem.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProblemResponseDTO {
    private Integer problemId;
    private String titleKo;
    private List<ProblemTitlesTranslatedDTO> titles;
    private boolean isSolvable;
    private boolean isPartial;
    private Integer acceptedUserCount;
    private Integer level;
    private Integer votedUserCount;
    private boolean sprout;
    private boolean givesNoRating;
    private boolean isLevelLocked;
    private Double averageTries;
    private boolean official;
    private List<ProblemTagDTO> tags;
    private Object metaData;
}
