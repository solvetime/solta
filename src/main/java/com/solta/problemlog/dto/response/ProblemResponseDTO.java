package com.solta.problemlog.dto.response;

import com.solta.problemlog.dto.ProblemTagDTO;
import com.solta.problemlog.dto.ProblemTitlesTranslatedDTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
