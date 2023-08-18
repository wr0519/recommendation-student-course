package com.rainng.coursesystem.controller;

import com.rainng.coursesystem.model.vo.request.LoginVO;
import com.rainng.coursesystem.model.vo.response.ResultVO;
import com.rainng.coursesystem.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserController extends BaseController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResultVO login(@Validated @RequestBody LoginVO loginVO) {
        String username = loginVO.getUsername();//获取前端传来的用户名数据
        String password = loginVO.getPassword();//获取前端发送的密码
        Integer userType = loginVO.getUserType();//获取用户类型数据
        return service.login(username, password, userType);//调用Service层判断输入信息的合法性
    }

    @RequestMapping("/login/status")
    public ResultVO getLoginStatus() {
        return service.getLoginStatus();
    }

    @RequestMapping("/logout")
    public ResultVO logout() {
        return service.logout();
    }
}
