package com.solta.tag.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagItem {
    private String key;
    private boolean isMeta;
    private int bojTagId;
    private int problemCount;
    private List<TagDisplayName> displayNames;
    private List<TagAlias> aliases;
}
