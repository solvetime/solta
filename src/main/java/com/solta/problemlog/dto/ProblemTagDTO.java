package com.solta.problemlog.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemTagDTO {
    private String key;
    private boolean isMeta;
    private int bojTagId;
    private int problemCount;
    private List<TagDisplayNameDTO> displayNames;
    private List<TagAliasDTO> aliases;
}
