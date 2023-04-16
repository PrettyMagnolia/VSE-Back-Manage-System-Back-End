package com.backend.vse.service;

import com.backend.vse.dto.StudentSimpleDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface StudentService {
    ArrayList<StudentSimpleDto> selectByCourseId(Long courseId);
}
