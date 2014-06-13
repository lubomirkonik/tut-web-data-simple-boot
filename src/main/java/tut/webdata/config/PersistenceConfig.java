package tut.webdata.config;

import tut.webdata.domain.MenuItem;
import tut.webdata.repository.*;
import tut.webdata.services.AccountService;
import tut.webdata.services.MenuEventHandler;
import tut.webdata.services.MenuService;
import tut.webdata.services.OrderEventHandler;
import tut.webdata.services.OrderService;
import tut.webdata.services.OrderStatusUpdateEventHandler;
import tut.webdata.services.OrderStatusUpdateService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

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
//		menuItemRepository.deleteAll();
//		menuItemRepository.save(menuItem("YM3", new BigDecimal("3.99"), 13, "Low cal Yummy Noodles"));
//		menuItemRepository.save(menuItem("YM2", new BigDecimal("2.99"), 12, "Special Yummy Noodles"));
//		menuItemRepository.save(menuItem("YM1", new BigDecimal("1.99"), 11, "Yummy Noodles"));
		return new MenuEventHandler(menuItemRepository);
	}

//	@Bean
//	public OrderStatusUpdateService orderStatusUpdateService() {
//		return new OrderStatusUpdateEventHandler();
//	}
	
//	private MenuItem menuItem(String id, BigDecimal cost, int minutesToPrepare, String name) {
//		MenuItem item = new MenuItem();
//		item.setId(id);
//		item.setCost(cost);
//		item.setMinutesToPrepare(minutesToPrepare);
//		item.setName(name);
//		return item;
//	}
}
