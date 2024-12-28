package com.solta.tag.dto.response;

import com.solta.tag.dto.TagItem;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponse {
    private int count;
    private List<TagItem> items;
}
