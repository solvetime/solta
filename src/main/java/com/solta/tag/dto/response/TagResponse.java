package com.solta.tag.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponse {
    private int count;
    private List<TagItem> items;
}
