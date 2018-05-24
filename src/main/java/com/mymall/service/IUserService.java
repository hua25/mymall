package com.mymall.service;

import com.mymall.common.ServerResponse;
import com.mymall.pojo.User;

/**
 * Created by HUA on 2018/5/24.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);
}
