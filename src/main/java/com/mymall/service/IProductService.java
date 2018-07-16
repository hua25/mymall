package com.mymall.service;

import com.github.pagehelper.PageInfo;
import com.mymall.common.ServerResponse;
import com.mymall.pojo.Product;
import com.mymall.vo.ProductDetailVO;

/**
 * Created by HUA on 2018/6/24.
 */
public interface IProductService {
    /**
     * 新增或更新产品
     *
     * @param product
     * @return
     */
    ServerResponse saveOrUpdateProduct(Product product);

    /**
     * 更新产品销售状态
     *
     * @param productId
     * @param status
     * @return
     */
    ServerResponse setSaleStatus(Integer productId, Integer status);

    /**
     * 后台管理-获取产品详情
     *
     * @param productId
     * @return
     */
    ServerResponse<ProductDetailVO> manageProductDetail(Integer productId);

    /**
     * 获取产品列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    /**
     * 获取产品详情
     * @param productId 产品id
     * @return
     */
    ServerResponse<ProductDetailVO> getProductDetail(Integer productId);

    /**
     * 通过关键字或分类查询产品
     * @param keyword 关键字
     * @param categoryId 分类id
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return
     */
    ServerResponse<PageInfo> getProductListByKeyWordCategory(String keyword, Integer categoryId, int pageNum, int pageSize,String orderBy);
}
