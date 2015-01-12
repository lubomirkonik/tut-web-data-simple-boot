package tut.webdata.repository;

import tut.webdata.domain.OrderStatus;

import org.springframework.data.repository.CrudRepository;

public interface OrderStatusRepository extends CrudRepository<OrderStatus, String> {
	OrderStatus findByOrderId(String orderId);
}