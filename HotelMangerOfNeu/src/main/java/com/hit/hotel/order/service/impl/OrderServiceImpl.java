package com.hit.hotel.order.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hit.hotel.enums.OrderStatus;
import com.hit.hotel.order.mapper.OrderMapper;
import com.hit.hotel.order.model.Order;
import com.hit.hotel.order.service.IOrderService;
import com.hit.hotel.order.service.IRoomTypeService;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IRoomTypeService roomTypeService;
//    @Autowired
//    private RoomService roomService;

    @Override
    public int insert(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public int addOrder(Order order) {
        return orderMapper.insertSelective(order);
    }

    @Override
    public int delete(Integer orderId) {
        return orderMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public Order selectById(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public Order selectByNameAndPhone(String name, String phone) {
        Order order = new Order();
        order.setName(name);
        order.setPhone(phone);
        order.setOrderStatus(OrderStatus.PAID.getCode());
        return orderMapper.selectByNameAndPhone(order);
    }

    @Override
    public int update(Order order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 订单支付
     *  1.更改订单状态 -3
     *  2.修改房型余量 -2
     *  3.修改房间状态 -1
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public int payOrder(int orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null | order.getOrderStatus() != OrderStatus.UNPAID.getCode()) {
            return -3;
        }
        if (roomTypeService.updateRest(order.getRoomTypeId(),-1) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -2;
        }
        order.setOrderStatus(OrderStatus.PAID.getCode());
        if (orderMapper.updateByPrimaryKeySelective(order) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }

    /**
     * 取消订单
     * 1. 更改订单状态 -3
     * 2. 修改房型余量（已付款）-2
     * @param orderId
     * @return
     */
    @Override
    public int cancelOrder(int orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null ) return -3;
        order.setOrderStatus(OrderStatus.WAS_CANCELED.getCode());
        if (roomTypeService.updateRest(order.getRoomTypeId(),1) != 1){
            return -2;
        }
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Integer getOrderCount() {
        return orderMapper.getOrderCount();
    }

    @Override
    public List<Order> selectByUserId(int userId) {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public List<Order> AllOrders() {
        return orderMapper.selectAll();
    }

    @Override
    public List<Order> UsersAllOrders(Integer userId) {
        return orderMapper.selectAllByUser(userId, OrderStatus.WAS_DELETED.getCode());
    }


}
