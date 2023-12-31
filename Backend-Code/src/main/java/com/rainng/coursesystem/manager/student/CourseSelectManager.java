package com.rainng.coursesystem.manager.student;

import com.rainng.coursesystem.dao.CourseDAO;
import com.rainng.coursesystem.dao.StudentCourseDAO;
import com.rainng.coursesystem.dao.StudentDAO;
import com.rainng.coursesystem.manager.BaseManager;
import com.rainng.coursesystem.model.bo.CourseItemBO;
import com.rainng.coursesystem.model.bo.StudentCourseSelectItemBO;
import com.rainng.coursesystem.model.entity.CourseEntity;
import com.rainng.coursesystem.model.entity.StudentCourseEntity;
import com.rainng.coursesystem.model.entity.StudentEntity;
import com.rainng.coursesystem.model.vo.response.table.StudentCourseItemVO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CourseSelectManager extends BaseManager {
    private final CourseDAO courseDAO;
    private final StudentDAO studentDAO;
    private final StudentCourseDAO studentCourseDAO;

    public CourseSelectManager(CourseDAO courseDAO, StudentDAO studentDAO, StudentCourseDAO studentCourseDAO) {
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
        this.studentCourseDAO = studentCourseDAO;
    }

    public Integer getPageCount(Integer studentId, String courseName, String teacherName) {
        Integer departmentId = studentDAO.getDepartmentIdById(studentId);
        Integer grade = studentDAO.getGradeById(studentId);
        return calcPageCount(courseDAO.countStudentCanSelect(departmentId, studentId, grade, courseName, teacherName), StudentCourseDAO.PAGE_SIZE);
    }

    public List<StudentCourseSelectItemBO> getPage(Integer index, Integer studentId, String courseName, String teacherName) {
        Integer departmentId = studentDAO.getDepartmentIdById(studentId);
        Integer grade = studentDAO.getGradeById(studentId);
        return courseDAO.getStudentCanSelectPage(index, studentId, departmentId, grade, courseName, teacherName);
    }

    public List<StudentCourseSelectItemBO> getRecommend(Integer studentId) {
        return studentCourseDAO.getRecommend(studentId);
    }

    public CourseEntity getCourseById(Integer courseId) {
        return courseDAO.get(courseId);
    }

    public String getCourseDetail(Integer courseId){return courseDAO.getCourseDetail(courseId);}

    public StudentEntity getStudentById(Integer studentId) {
        return studentDAO.get(studentId);
    }

    public boolean delRecommend(Integer userId,Integer courseId){
        return courseDAO.delRecommend(userId,courseId);
    }

    public boolean inSameDepartment(Integer courseId, Integer studentId) {
        return courseDAO.getDepartmentIdById(courseId)
                .equals(studentDAO.getDepartmentIdById(studentId));
    }

    public StudentCourseEntity getStudentCourseByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        return studentCourseDAO.getByCourseIdAndStudentId(courseId, studentId);
    }

    public Integer getStudentGradeById(Integer studentId) {
        return studentDAO.getGradeById(studentId);
    }

    @Transactional
    public int create(StudentCourseEntity entity) {
        courseDAO.increaseSelectedCount(entity.getCourseId());
        return studentCourseDAO.insert(entity);
    }

    public int countStudentCourseSelectedByTimePart(Integer studentId, String timePart) {
        return studentCourseDAO.countStudentCourseSelectedByTimePart(studentId, timePart);
    }
}
