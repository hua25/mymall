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

    /**
     * 更新购物车中的商品数量
     *
     * @param userId    用户id
     * @param productId 产品id
     * @param count     商品数量
     * @return
     */
    ServerResponse<CartVO> update(Integer userId, Integer productId, Integer count);

    /**
     * 删除购物车中的商品
     *
     * @param userId     用户id
     * @param productIds 商品id，多个id之间用逗号分隔
     * @return
     */
    ServerResponse<CartVO> deleteProduct(Integer userId, String productIds);

    /**
     * 查询用户购物车信息
     *
     * @param userId 用户id
     * @return
     */
    ServerResponse<CartVO> list(Integer userId);

    /**
     * 选择或者反选购物车中所有的商品
     *
     * @param userId    用户id
     * @param checked   选择状态
     * @param productId 产品id
     * @return
     */
    ServerResponse<CartVO> selectOrUnSelect(Integer userId, Integer checked, Integer productId);

    /**
     * 查询用户购物车中的产品数量
     *
     * @param userId 用户id
     * @return
     */
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
