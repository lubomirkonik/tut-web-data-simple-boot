package tut.webdata.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import tut.webdata.domain.Order;
import tut.webdata.domain.OrderStatus;
import tut.webdata.repository.*;
import tut.webdata.services.AccountService;
import tut.webdata.services.MenuEventHandler;
import tut.webdata.services.MenuService;
import tut.webdata.services.OrderEventHandler;
import tut.webdata.services.OrderService;
//import tut.webdata.services.OrderStatusUpdateEventHandler;
//import tut.webdata.services.OrderStatusUpdateService;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {

	@Bean
	public AccountService accountPersistenceService(AccountRepository accountRepository) {
		return new AccountService(accountRepository);
	}
	
	@Bean
	public OrderService ordersPersistenceService(OrdersRepository ordersRepository, OrderStatusRepository orderStatusRepository) {
//		this.ordersRepository = ordersRepository;
//		this.orderStatusRepository = orderStatusRepository;
//		init();
		return new OrderEventHandler(ordersRepository , orderStatusRepository);
	}
	
	@Bean
	public MenuService menuPersistenceService(MenuItemRepository menuItemRepository) {
		return new MenuEventHandler(menuItemRepository);
	}

//	@Bean
//	public OrderStatusUpdateService orderStatusUpdateService() {
//		return new OrderStatusUpdateEventHandler();
//	}
	
//	private OrdersRepository ordersRepository;
//	private OrderStatusRepository orderStatusRepository;
//	
//	private void init() {
//		createOrder(createOrder(UUID.randomUUID().toString(), new Date(), createOrderItems("YM4", "YM1"), "Mark Brown", "Greenwitch St. 402/C, London", "4802 35"));
//		createOrder(createOrder(UUID.randomUUID().toString(), new Date(), createOrderItems("YM2", "YM1", "YM3"), "Valter Longo", "Le Marais St. 156/D, Paris", "569 21"));
//	}
//
//	private void createOrder(Order order) {
//	ordersRepository.save(order);
//	orderStatusRepository.save(new OrderStatus(order.getId(), (UUID.randomUUID()).toString(), new Date(), "Order Received"));
//	}
//	
//	private Order createOrder(String id, Date dateTimeOfSubmission, Map<String, Integer> orderItems, String name, String address, String postcode) {
//		Order order = new Order();
//		order.setId(id);
//		order.setDateTimeOfSubmission(dateTimeOfSubmission);
//		order.setOrderItems(orderItems);
//		order.setName(name);
//		order.setAddress1(address);
//		order.setPostcode(postcode);
//		return order;
//	}
//
//	private Map<String, Integer> createOrderItems(String... orderIds) {
//		Map<String, Integer> orderItems = new HashMap<String, Integer>();
//		for (String orderId : orderIds) {
//			orderItems.put(orderId, 1);
//		}
//		return orderItems;
//	}
	
}
