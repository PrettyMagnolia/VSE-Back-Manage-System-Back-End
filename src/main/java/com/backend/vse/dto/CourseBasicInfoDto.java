package com.backend.vse.dto;

import com.backend.vse.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseBasicInfoDto implements Comparable{
    Long courseId;
    String courseName;
    String semester;
    int year;

    public CourseBasicInfoDto(Course course){
        if(course == null){
            return;
        }
        this.courseId = course.getCourseId();
        this.courseName = course.getCourseName();
        this.semester = course.getSemester();
        this.year = course.getYear();
    }

    @Override
    public int compareTo(Object object) {
        CourseBasicInfoDto o = (CourseBasicInfoDto) object;
        if(year != o.getYear()){
            return year - o.year;
        }
        else if(semester.equals("spring") && o.semester.equals("fall")){
            return -1;
        }
        else if(semester.equals("fall") && o.semester.equals("spring")){
            return 1;
        }
        else{
            return 0;
        }

    }
}
