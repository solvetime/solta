package com.solta.global.common.response;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@Builder
public class ApiResponse<T> {

    private final String message;
    private final T data;
    private final ZonedDateTime sendTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    public ResponseEntity<ApiResponse<T>> toEntity(final HttpStatus httpStatus){
        return ResponseEntity.status(httpStatus).body(this);
    }
}
