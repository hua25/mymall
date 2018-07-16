package com.mymall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 常量
 *
 * @author HUA
 * Created by HUA on 2018/5/24.
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    /**
     * 产品排序
     */
    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }

    public interface Cart {
        /**
         * 购物车选择状态
         */
        int CHECKED = 1;

        /**
         * 购物车中未选中
         */
        int UN_CHECKED = 0;

        /**
         * 购物车数量限制失败
         */
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        /**
         * 购物车数量限制成功
         */
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_FAIL";

    }

    /**
     * 角色
     */
    public interface Role {
        /**
         * 普通用户
         */
        int ROLE_CUSTOMER = 0;
        /**
         * 管理员
         */
        int ROLE_ADMIN = 1;
    }

    /**
     * 商品状态
     */
    public enum ProductStatusEnum {
        ON_SALE(1, "在线");
        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }


}
