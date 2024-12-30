package com.solta.tag.service;

import com.solta.tag.domain.Tag;
import com.solta.tag.dto.TagDisplayName;
import com.solta.tag.dto.TagItem;
import com.solta.tag.dto.response.TagResponse;
import com.solta.tag.repository.TagRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class TagService {
    private final RestTemplate restTemplate;
    private final TagRepository tagRepository;
    private String apiUrl = "https://solved.ac/api/v3/tag/list";
    private final Environment env;

    public TagService(RestTemplate restTemplate, TagRepository tagRepository, Environment env) {
        this.restTemplate = restTemplate;
        this.tagRepository = tagRepository;
        this.env = env;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void updateTags(){
        if (Arrays.asList(env.getActiveProfiles()).contains("local")) {
            TagResponse tagResponse = fetchTagsFromApi();

            for(TagItem tagItem : tagResponse.getItems()){
                String tagKey = tagItem.getKey();
                String nameKo = extractDisplayName(tagItem, "ko");

                Tag tag = new Tag(tagKey, nameKo);

                tagRepository.save(tag);
            }
        }
    }

    private TagResponse fetchTagsFromApi() {
        ResponseEntity<TagResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }

    private String extractDisplayName(TagItem item, String language){
        return item.getDisplayNames()
                .stream()
                .filter(tagDisplayName -> tagDisplayName.getLanguage().equals(language))
                .findFirst()
                .map(TagDisplayName::getName)
                .orElse(null);
    }
}
