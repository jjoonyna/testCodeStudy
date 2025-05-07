package com.example.testcodestudy.study;

import com.example.testcodestudy.domain.Member;
import com.example.testcodestudy.domain.Study;
import com.example.testcodestudy.domain.StudyStatus;
import com.example.testcodestudy.member.MemberService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest //테스트 할 객체에 빈을 등록해주기 위해 필요
@ExtendWith(MockitoExtension.class) //모키토를 사용할수 있게 확장해주는 어노테이션 단 SpringBootTest 어노테이션 있을시 필요 없음
@ActiveProfiles("test")
@Testcontainers //테스트 컨테이너 사용하기 위해 필요
@ContextConfiguration(initializers = StudyServiceTest.ContainerPropertyInitializer.class) //Context 연결
class StudyServiceTest {

    @Mock //객체를 생성해주는 어노테이션, 주로 구현체가 없는 인터페이스를 객체를 만들어 테스트할때 사용
    MemberService memberService;

    @Autowired
    StudyRepository studyRepository;

    @Autowired
    Environment enviroment;

    //위와 동일
    @Value("${container.port}")
    int port;

    @Container // 컨테이너 사용한다는 어노테이션
    private static MySQLContainer mySQLContainer= new MySQLContainer().withDatabaseName("studytest");
    //db를 직접 가져오는게 아니라 도커의 이미지를 가져와서 연결 가능
    static GenericContainer mysqlContainer= new GenericContainer("mysql")
            .withExposedPorts(3306)
            .withEnv("MYSQL_DB", "studytest");

            //Wait을 사용하여 해당 로그메세지, 포트, http 요청 이 있은후 사용하도록 대기
            /*.waitingFor(Wait.forHttp("/create"))
            .waitingFor(Wait.forLogMessage("안녕",1))
            .waitingFor(Wait.forListeningPort());*/

    @BeforeAll
    static void beforeAll() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        mySQLContainer.followOutput(logConsumer);
    }

    //매번 테스트 마다 레파지토리를 지운다(조금이라도 테스트를 빠르게 하기 위해)
    @BeforeEach
    void beforeEach() {
        System.out.println(mySQLContainer.getMappedPort(3306));
        System.out.println("===========================");
        System.out.println(enviroment.getProperty("container.port"));
        System.out.println(port);
        studyRepository.deleteAll();
        System.out.println(mySQLContainer.getLogs());
    }


    /*@BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }*/

    @Test
    //한 메서드 안에서만 쓰는 목 객체를 만들 경우 테스트할 메서드의 파라미터 값에 넣어주면 된다
    void createStudyService() {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("haha@gmail.com");

        Study study = new Study(10, "test");

        given(memberService.findById(anyLong())).willReturn(Optional.of(member));

        studyService.createNewStudy(1L, study);

        assertEquals(1L, study.getOwnerId());
        then(memberService).should(times(1)).notify(study);
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

/*
        Study study = new Study(10, "java");

        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));

        when(studyRepository.save(study)).thenReturn(study);
        studyService.createNewStudy(1L, study);

        //notify라는 메서드가 1번 호출 되었는지 확인
        verify(memberService, times(1)).notify(study);

        //더이상 memberService에서 액션이 일어나지 않아야할때 확인
        //verifyNoMoreInteractions(memberService);

        verify(memberService, times(1)).notify(member);

        //vallidate는 전혀 호출되지 않았는지 확인
        verify(memberService, never()).validate(any());

        //메서드 호출 순서가 맞는지 확인
        InOrder inOrder = Mockito.inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
*/



    }

    @DisplayName("연습문제")
    @Test
    void openStudy(){
        //Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "test");
        assertNull(study.getOpenedDateTime());

        //BDDMockito.given(studyRepository.save(study)).willReturn(study);
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
    //property 정보를 spring context에 저장
    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            mySQLContainer.start();
            //container.port 프로퍼티 추가
            TestPropertyValues.of("container.port=" + mySQLContainer.getMappedPort(3306))
                    .applyTo(applicationContext.getEnvironment());
        }
    }
}