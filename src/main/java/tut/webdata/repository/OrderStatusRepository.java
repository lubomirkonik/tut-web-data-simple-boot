package tut.webdata.repository;

import tut.webdata.domain.OrderStatus;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;

import java.util.Collection;
import java.util.UUID;

//import org.springframework.data.repository.CrudRepository;

public interface OrderStatusRepository extends GemfireRepository<OrderStatus, UUID> { //CrudRepository<OrderStatus, String>
	OrderStatus findByOrderId(UUID orderId);

	@Query("SELECT DISTINCT * FROM /YummyNoodleOrder WHERE orderId = $1 ORDER BY statusDate")
	public Collection<OrderStatus> getOrderHistory(UUID orderId);
}