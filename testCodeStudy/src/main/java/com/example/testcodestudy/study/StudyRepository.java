package com.example.testcodestudy.study;


import com.example.testcodestudy.domain.Member;
import com.example.testcodestudy.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface StudyRepository extends JpaRepository<Study, Long> {

}
