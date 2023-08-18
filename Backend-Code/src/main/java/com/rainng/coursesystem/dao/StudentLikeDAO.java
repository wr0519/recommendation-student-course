package com.rainng.coursesystem.dao;

import com.rainng.coursesystem.dao.mapper.StudentLikeMapper;
import com.rainng.coursesystem.model.entity.StudentLikeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class StudentLikeDAO {
    private final StudentLikeMapper mapper;

    public StudentLikeDAO(StudentLikeMapper mapper) {
        this.mapper = mapper;
    }

    public int insert(StudentLikeEntity entity) {
        return mapper.insert(entity);
    }
}
