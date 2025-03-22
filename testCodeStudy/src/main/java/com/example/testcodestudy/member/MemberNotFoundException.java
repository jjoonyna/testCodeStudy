package com.example.testcodestudy.member;

public class MemberNotFoundException extends RuntimeException {
  public MemberNotFoundException(String message) {
    super("멤버를 찾을수 없습니다");
  }
}
