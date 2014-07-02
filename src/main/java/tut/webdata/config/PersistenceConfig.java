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
	
}
