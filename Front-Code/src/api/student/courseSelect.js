import * as ajax from "../../common/ajax";

export const select = id => ajax.post("/student/course/select/" + id, {});

export const detail = id =>ajax.post("/student/course/select/detail/" + id,{});

export const getPageCount = (courseName, teacherName) =>
  ajax.get("/student/course/select/page/count", {
    courseName: courseName,
    teacherName: teacherName
  });

export const getPage = (index, courseName, teacherName) =>
  ajax.get("/student/course/select/page/" + index, {
    courseName: courseName,
    teacherName: teacherName
  });

  export const getRecommend = () =>
  ajax.get("/student/course/select/recommend");

export const pageSize = 20;
