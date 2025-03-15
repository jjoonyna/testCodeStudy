package com.example.testcodestudy;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.assertj.core.api.Assertions.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    @EnabledOnOs(OS.WINDOWS) //해당 OS에서만 테스트 실행, OS가 아닌 JRE로 할경우 자바버전으로 조건 설정
    //@EnabledIfEnvironmentVariable(named = "test", matches = "TEST") //환경변수값이 맞는 조건에서 실행
    //@Tag("fast") //tag값으로 해당하는 테스트 메서드만 실행할수있다.
    @FastTest
    void create(){

        Study study = new Study(10);

        //AssertJ 라이브러리 추가해서 사용
        //값이 0보다 큰지 확인
        assertThat(study.getLimit()).isGreaterThan(0);
        System.out.println(study.getLimit());

/*
        //해당 시간내에 완수했는지 확인
        assertTimeout(Duration.ofMillis(100),()->{
            new Study(10);
            Thread.sleep(100);
        });
*/

/*
        //내가 생각한 에러메시지와 같은지 확인
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("리밋은 0보다 커야함", exception.getMessage());
*/

/*
        Study study = new Study(-10);

        //인스턴스가 null인지 확인
        assertNotNull(study);

        //assertAll로 묶어줄시 테스트 실패가 떠도 나머지 테스트도 확인 가능함
        //미사용시 처음실패만 뜨고 그 이후 테스트는 알수 없음
        assertAll(
                //내가 원하는 결과값, 테스트할 값이 같은지 확인
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디 처음값은 "+ StudyStatus.DRAFT +"여야함"),
                //해당 값이 ture인지 확인
                () -> assertTrue(study.getLimit() >0, ()->"스터디 인원은 0보다 커야함")
        );
*/


    }

    @Test
    //@Disabled //해당 어노테이션있는 테스트는 실행하지 않는다
    //@DisabledOnOs(OS.WINDOWS) //해당 OS에서 테스트 제외
    @Tag("slow")
    void create1(){
        System.out.println("create1");
    }

    //All의 경우 전체 테스트 시작 전,끝난 후로 한번씩 동작한다. static void를 사용한다, 리턴타입 불가
    @BeforeAll
    static void beforeAll(){
        System.out.println("beforeAll");
    }
    @AfterAll
    static void afterAll(){
        System.out.println("afterAll");
    }

    //each의 경우 각 테스트 시작 전,끝난 후로 한번씩 동작한다. static을 쓰지않는다
    @BeforeEach
    void beforeEach(){
        System.out.println("beforeEach");
    }
    @AfterEach
    void afterEach(){
        System.out.println("afterEach");
    }
}