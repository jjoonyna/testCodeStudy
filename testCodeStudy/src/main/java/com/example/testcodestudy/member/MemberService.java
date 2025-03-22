package com.example.testcodestudy.member;

import com.example.testcodestudy.domain.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;
}
