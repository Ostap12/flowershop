package ua.galicia.flowershop.dao;
 
import java.util.List;

import ua.galicia.flowershop.model.CartInfo;
import ua.galicia.flowershop.model.OrderDetailInfo;
import ua.galicia.flowershop.model.OrderInfo;
import ua.galicia.flowershop.model.PaginationResult;


public interface OrderDAO {

    public void saveOrder(CartInfo cartInfo);

    public PaginationResult<OrderInfo> listOrderInfo(int page,
                                                     int maxResult, int maxNavigationPage);

    public OrderInfo getOrderInfo(String orderId);

    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);

}