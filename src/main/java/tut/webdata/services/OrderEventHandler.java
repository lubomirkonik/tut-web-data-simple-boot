package tut.webdata.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import tut.webdata.domain.Order;
import tut.webdata.domain.OrderStatus;
import tut.webdata.repository.OrderStatusRepository;
import tut.webdata.repository.OrdersRepository;

public class OrderEventHandler implements OrderService {

	private final OrdersRepository orderRepository;
	private final OrderStatusRepository orderStatusRepository;

	public OrderEventHandler(
			final OrdersRepository orderRepository,
			final OrderStatusRepository orderStatusRepository) {
		this.orderRepository = orderRepository;
		this.orderStatusRepository = orderStatusRepository;
	}
	  
	@Override
	public List<Order> requestAllOrders() {
		List<Order> orders = new ArrayList<Order>();
		for (Order order : orderRepository.findAll()) {
			orders.add(order);
		}
		return orders;
	}

	@Override
	public Order requestOrder(UUID key) {
		Order order = orderRepository.findOne(key.toString());
		if (order == null) {
			return Order.notFound(key.toString());
		}
		return order;
	}

	@Override
	public OrderStatus requestOrderStatus(UUID key) {
		OrderStatus status = orderStatusRepository.findOne(key);
		if (status == null) {
			return OrderStatus.notFound(key);
		}
		return status;
	}
	
	@Override
	public OrderStatus requestOrderStatusByOrderId(UUID key) {
		OrderStatus status = orderStatusRepository.findByOrderId(key);
		if (status == null) {
			return OrderStatus.notFound(key);
		}
		return status;
	}

	@Override
	public Order createOrder(Order order) {
		orderRepository.save(order);
		setOrderStatus(new OrderStatus(UUID.fromString(order.getId()), UUID.randomUUID(), new Date(), "Order Received"));
		return order;
	}

	@Override
	public OrderStatus setOrderStatus(OrderStatus orderStatus) {
		orderStatusRepository.save(orderStatus);
		return orderStatus;
	}

}
