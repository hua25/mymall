package com.mymall.service.impl;

import com.mymall.common.Const;
import com.mymall.common.ServerResponse;
import com.mymall.common.TokenCache;
import com.mymall.dao.UserMapper;
import com.mymall.pojo.User;
import com.mymall.service.IUserService;
import com.mymall.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by HUA on 2018/5/24.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {

        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        //MD5加密
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        //置空密码
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse<String> validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);

        // 使用MD5加密密码
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }

        return ServerResponse.createByErrorMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            int resultCount;
            //校验用户名和email
            switch (type) {
                case Const.USERNAME:
                    resultCount = userMapper.checkUsername(str);
                    if (resultCount > 0) {
                        return ServerResponse.createByErrorMessage("用户已存在");
                    }
                    break;
                case Const.EMAIL:
                    resultCount = userMapper.checkEmail(str);
                    if (resultCount > 0) {
                        return ServerResponse.createByErrorMessage("Email已存在");
                    }
                    break;
                default:
                    return ServerResponse.createByErrorMessage("参数错误");
            }

        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (!validResponse.isSuccess()) {
            //用户不存在
            ServerResponse.createByErrorMessage("用户不存在");
        }

        String quesion = userMapper.queryQueryByUsername(username);

        if (StringUtils.isNotBlank(quesion)) {
            return ServerResponse.createBySuccess(quesion);
        }

        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }


    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            //说明问题及问题答案是该用户的，并且都是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PERFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }

        return ServerResponse.createByErrorMessage("答案错误!");
    }


    @Override
    public ServerResponse<String> forgetResetPassword(String forgetToken, String username, String passwordNew) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误，需要token");
        }

        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (!validResponse.isSuccess()) {
            //用户不存在
            ServerResponse.createByErrorMessage("用户不存在");
        }

        String token = TokenCache.getKey(TokenCache.TOKEN_PERFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("Token无效!");
        }

        if (StringUtils.equals(token, forgetToken)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功！");
            } else {
                return ServerResponse.createByErrorMessage("Token错误，请重新获取！");
            }
        }

        return ServerResponse.createByErrorMessage("修改密码失败!");
    }


    @Override
    public ServerResponse<String> resetPassword(User user, String passwordOld, String passwordNew) {
        int resultCount = userMapper.checkPassword(user.getId(), MD5Util.MD5EncodeUtf8(passwordOld));
        if (resultCount < 1) {
            return ServerResponse.createByErrorMessage("原始密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));

        int updateCount = userMapper.updateByPrimaryKeySelective(user);

        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }

        return ServerResponse.createByErrorMessage("密码更新失败");
    }


    @Override
    public ServerResponse<User> updateInformation(User user) {
        //username是不能被更新的
        //校验email
        int resultCount = userMapper.checkEmailByUserId(user.getId(), user.getEmail());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("email已存在,请更换email后重试");
        }

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {
            return ServerResponse.createBySuccess("个人信息更新成功", updateUser);
        }

        return ServerResponse.createByErrorMessage("个人信息更新失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess(user);
    }
}
