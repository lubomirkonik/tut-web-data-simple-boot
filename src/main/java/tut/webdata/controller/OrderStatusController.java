package tut.webdata.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tut.webdata.domain.Order;
import tut.webdata.domain.OrderStatus;
import tut.webdata.services.OrderService;
//import tut.webdata.core.services.OrderService;
//import tut.webdata.events.orders.OrderDetailsEvent;
//import tut.webdata.events.orders.OrderStatusEvent;
//import tut.webdata.events.orders.RequestOrderDetailsEvent;
//import tut.webdata.events.orders.RequestOrderStatusEvent;
//import tut.webdata.web.domain.OrderStatus;

@Controller
@RequestMapping("/order/{orderId}")
public class OrderStatusController {

	private static final Logger LOG = LoggerFactory
			.getLogger(OrderStatusController.class);

	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.GET)
	public String orderStatus(@ModelAttribute("orderStatus") OrderStatus orderStatus) {
		LOG.debug("Get order status for order id {} customer {}", orderStatus.getOrderId(), orderStatus.getName());
		return "/order";
	}

	@ModelAttribute("orderStatus")
	private OrderStatus getOrderStatus(@PathVariable("orderId") String orderId) {
		Order orderDetailsEvent = orderService.requestOrder(UUID.fromString(orderId));
		OrderStatus orderStatusEvent = orderService.requestOrderStatusByOrderId(orderId); //UUID.fromString(orderId)

		OrderStatus status = new OrderStatus();
		status.setName(orderDetailsEvent.getName());
		status.setOrderId(orderId);  // UUID.fromString(orderId)
		status.setStatus(orderStatusEvent.getStatus());
		
		return status;
	}
}
