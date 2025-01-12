package com.solta.problem.domain;

import com.solta.tag.domain.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "problem_tag")
public class ProblemTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problem_id", foreignKey = @ForeignKey(name = "fk_problemtag_problem"))
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "fk_problemtag_tag"))
    private Tag tag;

    public ProblemTag(Problem problem, Tag tag) {
        this.problem = problem;
        this.tag = tag;
    }
}
