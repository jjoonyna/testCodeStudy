package com.example.testcodestudy.member;

import com.example.testcodestudy.domain.Member;
import com.example.testcodestudy.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);

    void validate(Long memberId);
    void notify(Study newStudy);

    void notify(Member member);
}
