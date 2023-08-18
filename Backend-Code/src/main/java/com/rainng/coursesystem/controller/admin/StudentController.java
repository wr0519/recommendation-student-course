package com.rainng.coursesystem.controller.admin;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rainng.coursesystem.config.themis.annotation.Admin;
import com.rainng.coursesystem.controller.BaseController;
import com.rainng.coursesystem.model.entity.StudentEntity;
import com.rainng.coursesystem.model.entity.StudentLikeEntity;
import com.rainng.coursesystem.model.vo.response.ResultVO;
import com.rainng.coursesystem.recommend.RecommendModel;
import com.rainng.coursesystem.service.admin.StudentService;
import org.bson.Document;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Admin(Admin.STUDENT_MANAGE)
@RequestMapping("/api/admin/student")
@RestController
public class StudentController extends BaseController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @RequestMapping("/recommend")
    public ResultVO runRecommend() throws Exception {
        service.deleteLike();
        RecommendModel.runRecommend();
        MongoClient mongoclient=new MongoClient("127.0.0.1",27017);
        MongoDatabase db=mongoclient.getDatabase("moan_course");
        //获取集合对象
        MongoCollection<Document> collection = db.getCollection("result");
        FindIterable<Document> finds = collection.find();
        for (Document doc:
                finds) {
            List<String> list = new ArrayList<>();
            list.addAll(doc.keySet());
            Integer userId = Integer.parseInt(list.get(1));
            String value = (String) doc.get(userId.toString());
            String[] likes = value.split(",");
            for (String like:
                    likes) {
                StudentLikeEntity studentLikeEntity = new StudentLikeEntity(userId,Integer.parseInt(like));
                service.insertLike(studentLikeEntity);
            }
        }
        //删除计算结果，为下次计算做预备工作
        db.getCollection("res").drop();
        db.getCollection("s1").drop();
        db.getCollection("s2").drop();
        db.getCollection("s3").drop();
        db.getCollection("s4").drop();
        db.getCollection("s5").drop();
        db.getCollection("s6").drop();
        db.getCollection("selected").drop();
        db.getCollection("result").drop();

        mongoclient.close();
        return result("更新成功！");
    }

    @GetMapping("/{id}")
    public ResultVO get(@PathVariable Integer id) {
        return service.get(id);
    }

    @PostMapping
    public ResultVO create(@RequestBody @Validated StudentEntity entity) {
        return service.create(entity);
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @PutMapping
    public ResultVO update(@RequestBody @Validated StudentEntity entity) {
        return service.update(entity);
    }

    @RequestMapping("/page/count")
    public ResultVO getPageCount(String majorName, String className, String name) {
        return service.getPageCount(majorName, className, name);
    }

    @RequestMapping("/page")
    public ResultVO getPage(String majorName, String className, String name) {
        return service.getPage(1, majorName, className, name);
    }

    @RequestMapping("/page/{index}")
    public ResultVO getPage(@PathVariable Integer index, String majorName, String className, String name) {
        return service.getPage(index, majorName, className, name);
    }

    @Admin
    @RequestMapping("/names")
    public ResultVO listName() {
        return service.listName();
    }
}
