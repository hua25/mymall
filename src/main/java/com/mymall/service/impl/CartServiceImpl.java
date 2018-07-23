package com.mymall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mymall.common.Const;
import com.mymall.common.ResponseCode;
import com.mymall.common.ServerResponse;
import com.mymall.dao.CartMapper;
import com.mymall.dao.ProductMapper;
import com.mymall.pojo.Cart;
import com.mymall.pojo.Product;
import com.mymall.service.ICartService;
import com.mymall.utils.BigDecimalUtil;
import com.mymall.utils.PropertisUtil;
import com.mymall.vo.CartProductVO;
import com.mymall.vo.CartVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加商品到购物车
     *
     * @param userId    用户id
     * @param productId 产品id
     * @param count     商品数量
     * @return
     */
    @Override
    public ServerResponse<CartVO> add(Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null) {
            //商品不在该用户的购物车中，需要新增一个
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setProductId(productId);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setUserId(userId);

            cartMapper.insert(cartItem);
        } else {
            //商品在购物车中，更新商品数量
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.list(userId);
    }

    /**
     * 获取用户购物车信息，根据商品库存判断商品数量，并计算购物车总价
     *
     * @param userId
     * @return
     */
    private CartVO getCartVoLimit(Integer userId) {
        CartVO cartVO = new CartVO();

        List<Cart> cartList = cartMapper.selectCartsByUserId(userId);

        List<CartProductVO> cartProductVOList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cart : cartList) {
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cart.getId());
                cartProductVO.setUserId(cart.getUserId());
                cartProductVO.setProductId(cart.getProductId());

                Product product = productMapper.selectByPrimaryKey(cart.getProductId());

                if (product != null) {
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cart.getQuantity()) {
                        //库存充足
                        buyLimitCount = cart.getQuantity();
                        cartProductVO.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStatus();
                        cartProductVO.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);

                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cart.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVO.setQuantity(buyLimitCount);
                    //产品总价
                    cartProductVO.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVO.getQuantity().doubleValue()));
                    cartProductVO.setProductChecked(cart.getChecked());
                }
                //购物车总价
                if (cart.getChecked() == Const.Cart.CHECKED) {
                    //如果已经勾选，增加到整个购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                }
                cartProductVOList.add(cartProductVO);
            }
        }

        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartProductVOList(cartProductVOList);
        cartVO.setAllChecked(this.getAllCheckedStatus(userId));
        cartVO.setImageHost(PropertisUtil.getProperty("ftp.server.http.prefix"));
        return cartVO;
    }

    /**
     * 判断用户购物车中商品是否全选
     *
     * @param userId
     * @return
     */
    private boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0 ? true : false;
    }

    /**
     * 更新购物车中的商品数量
     *
     * @param userId    用户id
     * @param productId 产品id
     * @param count     商品数量
     * @return
     */
    @Override
    public ServerResponse<CartVO> update(Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);

        //判断购物车中是否已存在
        if (cart != null) {
            cart.setQuantity(count);
        }
        //更新购物车
        cartMapper.updateByPrimaryKeySelective(cart);

        //重新计算购物车总价
        return this.list(userId);
    }

    /**
     * 删除购物车中的商品
     *
     * @param userId     用户id
     * @param productIds 商品id，多个id之间用逗号分隔
     * @return
     */
    @Override
    public ServerResponse<CartVO> deleteProduct(Integer userId, String productIds) {
        //使用Guava的Splitter分割字符串并转为集合
        List<String> productIdList = Splitter.on(",").splitToList(productIds);

        if (CollectionUtils.isEmpty(productIdList)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        cartMapper.deleteByUserIdProductIds(userId, productIdList);
        return this.list(userId);
    }

    /**
     * 查询用户购物车信息
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public ServerResponse<CartVO> list(Integer userId) {
        CartVO cartVO = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVO);
    }

    /**
     * 选择或者反选购物车中所有的商品
     *
     * @param userId    用户id
     * @param checked   选择状态
     * @param productId 产品id
     * @return
     */
    @Override
    public ServerResponse<CartVO> selectOrUnSelect(Integer userId, Integer checked, Integer productId) {
        cartMapper.checkedOrUncheckedProduct(userId, checked, null);
        return this.list(userId);
    }

    /**
     * 查询用户购物车中的产品数量
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        if (userId == null){
            return ServerResponse.createBySuccess(0);
        }
        int count = cartMapper.selectCartProductCount(userId);
        return ServerResponse.createBySuccess(count);
    }
}
