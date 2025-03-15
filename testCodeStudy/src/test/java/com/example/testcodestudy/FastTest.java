package com.example.testcodestudy;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //메서드에만 어노테이션 적용 가능 설정
@Retention(RetentionPolicy.RUNTIME) //어노테이션을 런타임까지 유지하는 설정
@Test // 테스트용 어노테이션 설정
@Tag("fast") //이 어노테이션은 fast란 태그를 가진 어노테이션으로 설정
public @interface FastTest {
}
