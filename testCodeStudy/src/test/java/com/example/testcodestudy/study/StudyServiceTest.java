package com.example.testcodestudy.study;

import com.example.testcodestudy.domain.Member;
import com.example.testcodestudy.domain.Study;
import com.example.testcodestudy.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock //객체를 생성해주는 어노테이션, 주로 구현체가 없는 인터페이스를 객체를 만들어 테스트할때 사용
    MemberService memberService;

    @Test
    //한 메서드 안에서만 쓰는 목 객체를 만들 경우 테스트할 메서드의 파라미터 값에 넣어주면 된다
    void createStudyService(@Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("haha@gmail.com");

        //memberService.findById(1L)가 호출되면 Optional.of(member)리턴
        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Study sutdy = new Study(10, "java");
        studyService.createNewStudy(1L, sutdy);


    }

}