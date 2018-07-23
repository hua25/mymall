package com.mymall.dao;

import com.mymall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    /**
     * 根据userId和productId查询购物车
     *
     * @param userId
     * @param productId
     * @return
     */
    Cart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    /**
     * 根据userId查询该用户的购物车
     *
     * @param userId 用户id
     * @return
     */
    List<Cart> selectCartsByUserId(Integer userId);

    /**
     * 检查用户购物车是否全选
     *
     * @param userId
     * @return 结果大于0:未全选;等于0:全选
     */
    int selectCartProductCheckedStatusByUserId(Integer userId);

    /**
     * 删除用户购物车中的商品
     *
     * @param userId     用户id
     * @param productIds 商品id列表
     * @return
     */
    int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIds") List<String> productIds);

    /**
     * 勾选购物车中商品是否被选择的状态
     *
     * @param userId    用户Id
     * @param checked   1:全选;0:全不选
     * @param productId 商品id
     * @return
     */
    int checkedOrUncheckedProduct(@Param("userId") Integer userId, @Param("userId") int checked, @Param("productId") Integer productId);

    /**
     * 查询用户购物车中的商品数量
     *
     * @param userId
     * @return
     */
    int selectCartProductCount(Integer userId);
}