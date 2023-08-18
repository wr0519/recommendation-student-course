package com.rainng.coursesystem.service;

import com.rainng.coursesystem.manager.LoginStatusManager;
import com.rainng.coursesystem.manager.UserManager;
import com.rainng.coursesystem.model.bo.AuthInfoBO;
import com.rainng.coursesystem.model.bo.LoginStatusBO;
import com.rainng.coursesystem.model.constant.UserType;
import com.rainng.coursesystem.model.vo.response.ResultVO;
import com.rainng.coursesystem.util.Md5Encrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService extends BaseService {
    private static final String PASSWORD_SALT = "_Rain_Ng-_Azure_99";

    private final HttpSession session;
    private final UserManager manager;
    private final LoginStatusManager loginStatusManager;
    private final Md5Encrypt md5Encrypt;

    public UserService(HttpSession session, UserManager manager, LoginStatusManager loginStatusManager, Md5Encrypt md5Encrypt) {
        this.session = session;
        this.manager = manager;
        this.loginStatusManager = loginStatusManager;
        this.md5Encrypt = md5Encrypt;
    }

    public ResultVO login(String username, String password, Integer userType) {
        AuthInfoBO authInfo = manager.getAuthInfoByUsername(username, userType);//调用Manager层的函数，对数据与数据库值进行校对
        if (authInfo == null) {//判断用户是否存在，反馈前端信息
            return failedResult("用户不存在");
        }
        String passwordHash = computePasswordHash(password);
        //判断密码是否正确，反馈前端信息
        if (!passwordHash.equals(authInfo.getPassword())) {
            return failedResult("密码错误");
        }
        //对数据库中的用户登录时间进行更新
        if (authInfo.getUserType().equals(UserType.STUDENT)) {
            manager.updateStudentLastLoginTime(username);
        }
        //往Session中放入学生用户信息
        LoginStatusBO statusBO = LoginStatusBO.fromAuthInfo(authInfo);
        loginStatusManager.setLoginStatus(session, statusBO);
        //返回反馈信息
        return result(statusBO);
    }

    public ResultVO getLoginStatus() {
        LoginStatusBO statusBO = loginStatusManager.getLoginStatus(session);
        return result(statusBO);
    }

    public ResultVO logout() {
        loginStatusManager.setLoginStatus(session, null);
        return result("注销成功");
    }

    public String computePasswordHash(String password) {
        String md5 = md5Encrypt.computeHexString(password);
        return md5Encrypt.computeHexString(md5 + PASSWORD_SALT);
    }
}
