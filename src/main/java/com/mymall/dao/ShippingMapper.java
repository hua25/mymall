package com.mymall.dao;

import com.mymall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    /**
     * 根据用户id和收货地址id删除收货地址
     *
     * @param userId
     * @param shippingId
     * @return
     */
    int deleteByUserIdAndShippingId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 更新收货地址
     *
     * @param shipping
     * @return
     */
    int updateByShipping(Shipping shipping);

    /**
     * 查询收货地址
     *
     * @param userId
     * @param shippingId
     * @return
     */
    Shipping selectByUserIdShippingId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 查询用户购物车列表
     *
     * @param userId
     * @return
     */
    List<Shipping> selectByUserId(Integer userId);
}