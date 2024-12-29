package com.solta.problemlog.domain;

import com.solta.member.domain.Member;
import com.solta.problem.domain.Problem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity(name = "problem_log")
@NoArgsConstructor
@AllArgsConstructor
public class ProblemLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private int id;

    @Column(name = "solved_duration")
    private int solvedDuration;

    @ManyToOne
    @JoinColumn(name = "problem_id", foreignKey = @ForeignKey(name = "fk_problemlog_problem"))
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_problemlog_member"))
    private Member member;
}
