package com.mymall.service;

import com.github.pagehelper.PageInfo;
import com.mymall.common.ServerResponse;
import com.mymall.pojo.Shipping;

public interface IShippingService {
    /**
     * 添加收货地址
     *
     * @param userId   用户id
     * @param shipping 收货地址信息
     * @return
     */
    ServerResponse add(Integer userId, Shipping shipping);

    /**
     * 删除收货地址
     *
     * @param userId     用户id
     * @param shippingId 收货地址id
     * @return
     */
    ServerResponse<String> delete(Integer userId, Integer shippingId);

    /**
     * 更新收货地址
     *
     * @param userId   用户id
     * @param shipping
     * @return
     */
    ServerResponse<String> update(Integer userId, Shipping shipping);

    /**
     * 根据id查询收货地址
     *
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<Shipping> selectByShippingId(Integer userId, Integer shippingId);

    /**
     * 查询用户所有收货地址
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
