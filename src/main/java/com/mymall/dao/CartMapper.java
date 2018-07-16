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

    int selectCartProductCheckedStatusByUserId(Integer userId);
}