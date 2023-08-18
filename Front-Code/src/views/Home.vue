<template>
  <div class="home-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-id-badge"></i> 首页-太理教务要闻
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="main-wrap">
      <el-container class="home-container">
        <el-aside class="home-aside" width="65%">
          <el-table :data="tableData" stripe>
            <el-table-column
              align="center"
              label="日期"
              prop="date"
              width="120px"
            ></el-table-column>
            <el-table-column
              align="center"
              label="标题"
              prop="title"
            ></el-table-column>
            <el-table-column align="center" label="操作" width="120px">
              <template slot-scope="scope">
                <el-button
                  @click="openNews(scope.row.url)"
                  size="mini"
                  type="primary"
                  >查看
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-aside>
        <el-aside class="home-aside" width="35%">
          <div class="aside-container">
            <el-card :body-style="{ padding: '0px' }">
              <img class="aside-img" src="http://www.tyut.edu.cn/__local/9/26/25/3A4C1D003F536900BF1D95554F6_FEBD26F6_30A96.jpg" />
              <div style="padding: 20px">
                <a
                  href="http://www.tyut.edu.cn/info/1027/25134.htm"
                  style="text-decoration:none"
                  target="_blank"
                >
                  <div class="aside-title">
                    <b>太原理工大学120周年校庆工作总结表彰大会隆重召开</b>
                  </div>
                  <div class="aside-content">
                    1月10日下午，太原理工大学在明向校区学生活动中心召开校庆工作总结表彰大会。校党委书记郑强，校党委副书记、副校长（主持行政工作）孙宏斌，校党委副书记李晋平、刘润祥，校党委常委、纪委书记尉安英，校党委常委、副校长吴斗庆、肖连团、任喜莹出席大会...
                  </div>
                </a>
              </div>
            </el-card>
          </div>
        </el-aside>
      </el-container>
    </div>
  </div>
</template>

<script>
import * as api from "../api/news";

export default {
  name: "Home",
  data() {
    return {
      tableData: []
    };
  },
  methods: {
    query() {
      api.get().then(res => {
        this.tableData = res;
      });
    },
    openNews(url) {
      window.open(url, "_blank");
    }
  },
  created() {
    this.query();
  }
};
</script>

<style scoped>
.home-wrap {
  height: 100%;
  width: 100%;
}

.main-wrap {
  margin-top: 10px;
  height: 100%;
  width: 100%;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.home-container {
  height: 100%;
  width: 90%;
  margin: auto;
}

.home-aside {
  height: 100%;
}

.aside-container {
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  height: 200px;
  margin-top: 20px;
}

.aside-img {
  width: 100%;
}

.aside-title {
  color: #333;
  font-size: 18px;
}

.aside-content {
  font-size: 12px;
  color: #999;
}
</style>
