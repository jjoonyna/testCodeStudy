package com.example.testcodestudy.study;

import com.example.testcodestudy.domain.Member;
import com.example.testcodestudy.domain.Study;
import com.example.testcodestudy.domain.StudyStatus;
import com.example.testcodestudy.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

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

/*
        //memberService.findById(1L)가 호출되면 Optional.of(member)리턴
        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Optional<Member> newMember = memberService.findById(1L);
        assertEquals(newMember.get().getEmail(), "haha@gmail.com");


        //아이디값이 어떤값이든 Optional.of(member)를 리턴
        Mockito.when(memberService.findById(any())).thenReturn(Optional.of(member));

        assertEquals("haha@gmail.com", memberService.findById(2L).get().getEmail());
        assertEquals("haha@gmail.com", memberService.findById(1L).get().getEmail());

        //객체의 validate가 1L일때 예외 던짐
        Mockito.doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
        
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));
        memberService.validate(2L);
*/


/*
        //첫번째는 멤버를 반환 두번째는 런테임 에러 반환 세번째는 빈객체를 반환하는 Stubbing
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> newMember = memberService.findById(1L);
        assertEquals("haha@gmail.com", newMember.get().getEmail());
        assertThrows(RuntimeException.class,() -> memberService.findById(2L));
        assertEquals(Optional.empty(), memberService.findById(3L));
*/

        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Study study = new Study(10, "java");
        when(studyRepository.save(study)).thenReturn(study);
        studyService.createNewStudy(1L, study);

        //notify라는 메서드가 1번 호출 되었는지 확인
        verify(memberService, times(1)).notify(study);

        //더이상 memberService에서 액션이 일어나지 않아야할때 확인
        verifyNoMoreInteractions(memberService);
        verify(memberService, times(1)).notify(member);

        //vallidate는 전혀 호출되지 않았는지 확인
        verify(memberService, never()).validate(any());

        //메서드 호출 순서가 맞는지 확인
        InOrder inOrder = Mockito.inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);




    }

    @DisplayName("연습문제")
    @Test
    void openStudy(@Mock StudyRepository studyRepository){
        //Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "테스트");

        BDDMockito.given(studyRepository.save(study)).willReturn(study);
        //위와 동일
        //when(studyRepository.save(study)).thenReturn(study);




        //When
        studyService.openStudy(study);

        //Then
        assertEquals(study.getStatus(), StudyStatus.OPENED);
        assertNotNull(study.getOpenedDateTime());

        then(memberService).should(times(1)).notify(study);
        //위와 동일
        //verify(memberService, times(1)).notify(study);



    }
}