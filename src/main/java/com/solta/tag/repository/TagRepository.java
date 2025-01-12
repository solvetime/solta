package com.solta.tag.repository;

import com.solta.tag.domain.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
    Optional<Tag> findTagByTagKeyIs(String key);
}
