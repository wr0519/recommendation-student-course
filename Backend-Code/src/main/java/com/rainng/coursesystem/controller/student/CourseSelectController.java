package com.rainng.coursesystem.controller.student;

import com.rainng.coursesystem.config.themis.annotation.Student;
import com.rainng.coursesystem.controller.BaseController;
import com.rainng.coursesystem.model.vo.response.ResultVO;
import com.rainng.coursesystem.service.student.CourseSelectService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Student
@RequestMapping("/api/student/course/select")
@RestController
public class CourseSelectController extends BaseController {
    private final CourseSelectService service;

    public CourseSelectController(CourseSelectService service) {
        this.service = service;
    }

    @RequestMapping("/detail/{id}")
    public ResultVO getCourseDetail(@PathVariable Integer id){
        return service.getCourseDeatil(id);
    }

    @RequestMapping("/recommend")
    public ResultVO getRecommend() {
        return service.getRecommend();
    }

    @RequestMapping("/page/count")
    public ResultVO getPageCount(String courseName, String teacherName) {
        return service.getPageCount(courseName, teacherName);
    }

    @RequestMapping("/page/{index}")
    public ResultVO getPage(@PathVariable Integer index, String courseName, String teacherName) {
        return service.getPage(index, courseName, teacherName);
    }

    @PostMapping("/{id}")
    public ResultVO create(@PathVariable Integer id) {
        service.delrecommend(id);
        return service.create(id);
    }
}
