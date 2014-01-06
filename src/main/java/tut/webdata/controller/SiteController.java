package tut.webdata.controller;

//import java.util.ArrayList;
//import java.util.List;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tut.webdata.domain.Basket;
import tut.webdata.domain.MenuItem;
import tut.webdata.services.MenuService;
//import tut.webdata.events.menu.AllMenuItemsEvent;
//import tut.webdata.events.menu.MenuItemDetails;
//import tut.webdata.events.menu.RequestAllMenuItemsEvent;

@Controller
//@RequestMapping("/")
public class SiteController {

	private static final Logger LOG = LoggerFactory.getLogger(SiteController.class);
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private Basket basket;
		
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getCurrentMenu(Model model) {
		LOG.debug("Yummy MenuItemDetails to home view");
		model.addAttribute("menuItems", menuService.requestAllMenuItems()); //getMenuItems(menuService.requestAllMenuItems(new RequestAllMenuItemsEvent()))
		return "/home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	// do validation
	public String addMenuItem(@ModelAttribute("menuItemAdd") MenuItem menuItem, BindingResult result, RedirectAttributes redirectAttrs) {
		if (result.hasErrors()) {
			return "redirect:/";
		}
		LOG.debug("No errors, continue with creating of menu item {}:",
				menuItem.getName());
		menuService.createMenuItem(menuItem);
		redirectAttrs.addFlashAttribute("message","Menu item has been created!");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/updateMenuItem", method = RequestMethod.POST)
	public String updateMenuItem(@ModelAttribute("menuItemUpd") MenuItem menuItem) {
		LOG.debug("Update {} on MongoDB", menuItem.getId());
		menuService.updateMenuItem(menuItem);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/deleteMenuItem", method = RequestMethod.POST)
	public String removeMenuItem(@ModelAttribute("menuItemDel") MenuItem menuItem) {
		LOG.debug("Remove {} from MongoDB", menuItem.getId());
		menuService.deleteMenuItem(menuItem.getId());
		return "redirect:/";
	}
	
//	private List<MenuItem> getMenuItems(AllMenuItemsEvent requestAllMenuItems) {
//		List<MenuItem> menuDetails = new ArrayList<MenuItem>();
//		
//		for (MenuItemDetails menuItemDetails : requestAllMenuItems.getMenuItemDetails()) {
//			menuDetails.add(MenuItem.fromMenuDetails(menuItemDetails));
//		}
//
//		return menuDetails;
//	}
	
	@ModelAttribute("basket")
	private Basket getBasket() {
		return basket;
	}

	@ModelAttribute("menuItemAdd")
	private MenuItem getMenuItem() {
		return new MenuItem();
	}
}
