package com.example.testcodestudy.study;


import com.example.testcodestudy.domain.Member;
import com.example.testcodestudy.domain.Study;
import com.example.testcodestudy.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class StudyService {
    private final MemberService memberService;
    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        assert memberService != null;
        assert studyRepository != null;
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }
    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwnerId(member.orElseThrow(() -> new IllegalArgumentException("Member is null")).getId());
        return studyRepository.save(study);
    }
}
