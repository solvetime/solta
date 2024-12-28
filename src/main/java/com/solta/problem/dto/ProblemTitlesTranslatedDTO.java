package com.solta.problem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemTitlesTranslatedDTO {
    private String language;
    private String languageDisplayName;
    private String title;
    private boolean isOriginal;
}
