package com.solta.problem.domain;

import com.solta.tier.domain.Tier;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "problem")
public class Problem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int problemNumber;

    @ManyToOne
    @JoinColumn(name = "tier_id", foreignKey = @ForeignKey(name = "fk_problem_level"))
    private Tier tier;
}
