package com.rainng.coursesystem.manager.student;

import com.rainng.coursesystem.dao.StudentLikeDAO;
import com.rainng.coursesystem.manager.BaseManager;
import com.rainng.coursesystem.model.entity.StudentLikeEntity;
import org.springframework.stereotype.Component;

@Component("student_CourseLikeManager")
public class CourseLikeManager extends BaseManager {
    private final StudentLikeDAO studentLikeDAO;

    public CourseLikeManager(StudentLikeDAO studentLikeDAO) {
        this.studentLikeDAO = studentLikeDAO;
    }

    public void insert(StudentLikeEntity studentLikeEntity){
        studentLikeDAO.insert(studentLikeEntity);
    }
}
