package com.mymall.service;

import com.mymall.common.ServerResponse;
import com.mymall.vo.CartVO;

public interface ICartService {

    /**
     * 添加商品到购物车
     *
     * @param userId    用户id
     * @param productId 产品id
     * @param count     商品数量
     * @return
     */
    ServerResponse<CartVO> add(Integer userId, Integer productId, Integer count);
}
