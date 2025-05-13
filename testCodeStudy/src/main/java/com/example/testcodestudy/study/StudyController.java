package com.example.testcodestudy.study;

import com.example.testcodestudy.domain.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudyController {
    final StudyRepository studyRepository;

    @GetMapping("/study/{id}")
    public Study getStudy(@PathVariable long id) {
        return studyRepository.findById(id).orElseThrow(()->new IllegalArgumentException(id+"찾을수 없습니다"));
    }

    @PostMapping("/study")
    public Study addStudy(@RequestBody Study study) {
        return studyRepository.save(study);
    }
}
