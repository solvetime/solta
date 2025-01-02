package com.solta.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HttpMessage {

    SUCCESS("SUCCESS"),
    CREATED("CREATED"),
    ERROR("ERROR");

    private final String msg;
}
