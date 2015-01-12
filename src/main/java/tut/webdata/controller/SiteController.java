package tut.webdata.controller;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tut.webdata.domain.Basket;
import tut.webdata.domain.MenuItem;
import tut.webdata.services.MenuService;

@Controller
//@RequestMapping("/")
public class SiteController {

	private static final Logger LOG = LoggerFactory.getLogger(SiteController.class);
	
	private MenuService menuService;
	
	@Autowired
	private Basket basket;
	
	@Autowired
	public SiteController(MenuService menuService) {
		this.menuService = menuService;
//		if (menuService.requestAllMenuItems().size() == 0) {
//			init();
//		}
	}
	
	@PostConstruct
	private void init() {
		menuService.createMenuItem(menuItem("YM1", new BigDecimal("1.99"), 11, "Yummy Noodles"));
		menuService.createMenuItem(menuItem("YM2", new BigDecimal("2.99"), 12, "Special Noodles"));
		menuService.createMenuItem(menuItem("YM3", new BigDecimal("3.59"), 13, "Low Cal Noodles"));
		menuService.createMenuItem(menuItem("YM4", new BigDecimal("4.09"), 5, "Greek Salad"));
	}
	
	private MenuItem menuItem(String id, BigDecimal cost, int minutesToPrepare, String name) {
		MenuItem item = new MenuItem();
		item.setId(id);
		item.setCost(cost);
		item.setMinutesToPrepare(minutesToPrepare);
		item.setName(name);
		return item;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getCurrentMenu(Model model) {
		LOG.debug("Yummy MenuItemDetails to home view");
		model.addAttribute("menuItems", menuService.requestAllMenuItems());
		return "/home";
	}
	
	@ModelAttribute("basket")
	private Basket getBasket() {
		return basket;
	}
}
