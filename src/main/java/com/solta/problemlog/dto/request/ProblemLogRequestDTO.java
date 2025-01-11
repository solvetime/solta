package com.solta.problemlog.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ProblemLogRequestDTO {
    @NotNull(message = "문제 번호는 필수 입력값입니다.")
    private Integer problemNumber;
    @NotEmpty(message = "풀이 시간은 필수 입력값입니다.")
    @Pattern(
            regexp = "^\\d{1,2}:\\d{2}:\\d{2}$",
            message = "해결 시간은 HH:mm:ss 형식이어야 합니다."
    )
    private String solveDuration;
}
