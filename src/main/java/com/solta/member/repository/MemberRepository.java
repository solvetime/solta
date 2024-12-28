package com.solta.member.repository;

import com.solta.member.domain.Member;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public Member findByEmail(String email) {
        return memberJpaRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);
    }

    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }
}
