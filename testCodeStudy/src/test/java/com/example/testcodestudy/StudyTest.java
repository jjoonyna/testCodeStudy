package com.example.testcodestudy;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import static org.assertj.core.api.Assertions.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {


    //단순 반복 테스트
    @DisplayName("공부해라")
    @RepeatedTest(value = 10, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        //RepetitionInfo는 반복하는 테스트의 정보 확인이 가능하다
        System.out.println("Create study"+repetitionInfo.getCurrentRepetition()+"/"+repetitionInfo.getTotalRepetitions());
    }

    //인자를 넣어서 하는 테스트
    @DisplayName("채용공고")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"취업하기가", "많이", "힘들어지고", "있네요"}) //원하는 인자들을 넣어준다
    @EmptySource //비어있는 값을 넣어준다
    @NullSource //널을 넣어준다
    //@NullAndEmptySource //널과 비어있는 인자를 넣어준다
    void parameterizedTest(String message){
        System.out.println(message);
    }

    //인자 변환해서 넣어 테스트
    @DisplayName("다른 인자 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest2(@ConvertWith(StudyConverter.class) Study study){
        System.out.println(study.getLimit());
    }

    //다른 타입으로 변환 시켜주는 클래스
    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, () -> "Study만 convert 가능");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("생성자 인자 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바스터디'", "20, 스프링"})
    void parameterizedTest3(Integer limit, String name){
        System.out.println(new Study(limit, name));
    }

    //위와 동일한 다른 방법
    @DisplayName("생성자 인자 테스트2")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바스터디'", "20, 스프링"})
    void parameterizedTest4(@AggregateWith(StudyAggregator.class) Study study){
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator{

        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }

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