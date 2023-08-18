<template>
  <div class="course-select-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-book"></i> 选修课程
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="query-form">
        <el-row :gutter="20">
          <el-col :offset="15" :span="3">
            <el-input
              @keyup.enter.native="query"
              placeholder="课程名"
              v-model="queryForm.courseName"
            />
          </el-col>
          <el-col :span="3">
            <el-input
              @keyup.enter.native="query"
              placeholder="教师名"
              v-model="queryForm.teacherName"
            />
          </el-col>
          <el-col :span="3">
            <el-button @click="query" icon="el-icon-search" type="primary"
              >搜索
            </el-button>
          </el-col>
          <el-col :span="3">
            <i class="el-icon-back"></i>
            <el-button type="text" @click="switchChange()">
              <p v-show='flag==true'>查看全部课程</p>
              <p v-show='flag==false'>返回推荐界面</p>
            </el-button>
          </el-col>
          
        </el-row>
      </div>

      <el-row justify="center" type="flex">
        <el-pagination
          :current-page.sync="pageIndex"
          :page-size="pageSize"
          :total="pageSize * pageCount"
          @current-change="getPage"
          background
          layout="prev, pager, next"
        >
        </el-pagination>
      </el-row>

      <div class="table">
        <el-table :data="tableData" stripe>
          <el-table-column label="课程Id" prop="courseId" />
          <el-table-column label="课程名" prop="courseName" width="180px" />
          <el-table-column label="教师" prop="teacherName" />
          <el-table-column label="学分" prop="credit" />
          <el-table-column
            align="center"
            label="上课时间"
            prop="time"
            width="130px"
          />
          <el-table-column label="已选人数" prop="selectedCount" />
          <el-table-column label="课程容量" prop="maxSize" />
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <el-button type="text"
                @click="detail(scope.row.courseId,scope.row.courseName)"
              >详细信息</el-button>
              <el-button
                @click="select(scope.row.courseId)"
                size="mini"
                type="success"
                >选修
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import * as api from "../../api/student/courseSelect";

export default {
  name: "StudentCourseSelect",
  data() {
    return {
      queryForm: {
        courseName: "",
        teacherName: ""
      },
      flag : true,
      tableData: [],
      pageSize: api.pageSize,
      pageCount: 1,
      pageIndex: 1
    };
  },
  methods: {
    query() {
      api
        .getPageCount(this.queryForm.courseName, this.queryForm.teacherName)
        .then(res => {
          this.pageCount = res;
          this.pageIndex = 1;
          this.getRecommend();//this.getPage(1)
        });
    },
    switchChange(){
      if(this.flag == true){
        this.flag = false;
        this.getPage(1);
      }else{
        this.flag = true;
        this.getRecommend();
      }
    },
    getPage(pageIndex) {
      api
        .getPage(
          pageIndex,
          this.queryForm.courseName,
          this.queryForm.teacherName
        )
        .then(res => {
          this.tableData = res;
        });
    },
    getRecommend() {
      api
        .getRecommend()
        .then(res => {
          this.tableData = res;
        });
    },
    select(id) {//前端发送选课信息
      api.select(id).then(() => {
        this.$message.success("选修成功!");//成功返回前端信息
        if(this.flag == false){
          this.getPage(1);
        }else{
          this.getRecommend();//获取推荐课程列表
        }
        
      });
    },
    detail(id,courseName){
      api.detail(id).then(res => {
        this.$alert(res, courseName, {
          confirmButtonText: '确定',
        });
      });
    }
  },
  created() {
    this.query();
  }
};
</script>

<style scoped></style>
