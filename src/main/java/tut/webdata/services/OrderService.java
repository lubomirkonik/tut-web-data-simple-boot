package tut.webdata.services;

import java.util.List;
import java.util.UUID;

import tut.webdata.domain.Order;
import tut.webdata.domain.OrderStatus;

public interface OrderService {

  public List<Order> requestAllOrders();

  public Order requestOrder(UUID key);

  public OrderStatus requestOrderStatus(UUID key);
  
  public OrderStatus requestOrderStatusByOrderId(UUID key);

  public Order createOrder(Order order);

  public OrderStatus setOrderStatus(OrderStatus orderStatus);

//  public Order setOrderPayment(PaymentDetails paymentDetails);

//  public Order deleteOrder(UUID key);

}
