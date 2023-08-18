package com.rainng.coursesystem.service.student;

import com.rainng.coursesystem.manager.student.CourseLikeManager;
import com.rainng.coursesystem.model.entity.StudentLikeEntity;
import com.rainng.coursesystem.service.BaseService;
import org.springframework.stereotype.Service;

@Service("courseLikeService")
public class CourseLikeService extends BaseService {
    private final CourseLikeManager courseLikeManager;

    public CourseLikeService(CourseLikeManager courseLikeManager) {
        this.courseLikeManager = courseLikeManager;
    }

    public void insert(StudentLikeEntity studentLikeEntity){
        courseLikeManager.insert(studentLikeEntity);
    }
}
