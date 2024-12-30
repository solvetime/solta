package com.solta.tier.repository;

import com.solta.tier.domain.Tier;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TierRepository extends JpaRepository<Tier, Integer> {
    Optional<Tier> findById(int tierId);
}
