package com.rainng.coursesystem.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

@TableName("rc_recommend")
@Data
public class StudentLikeEntity {
    public static final String ID = "student_id";
    public static final String C_ID = "course_id";

    @NotNull
    @TableId(value = ID, type = IdType.AUTO)
    private Integer id;

    @NotNull
    @TableId(value = C_ID, type = IdType.AUTO)
    private Integer c_id;

    public StudentLikeEntity(int userId, int parseInt) {
        this.id = userId;
        this.c_id = parseInt;
    }
}
