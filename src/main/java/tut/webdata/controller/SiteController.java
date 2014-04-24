package tut.webdata.controller;

//import java.security.Principal;
//import java.util.Collection;
//import java.util.ArrayList;
//import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tut.webdata.domain.Basket;
import tut.webdata.domain.MenuItem;
import tut.webdata.services.MenuService;
//import tut.webdata.events.menu.AllMenuItemsEvent;
//import tut.webdata.events.menu.MenuItemDetails;
//import tut.webdata.events.menu.RequestAllMenuItemsEvent;
import tut.webdata.support.web.AjaxUtils;
import tut.webdata.support.web.MessageHelper;

@Controller
//@RequestMapping("/")
public class SiteController {

	private static final Logger LOG = LoggerFactory.getLogger(SiteController.class);
//	private static final String ADMIN_ROLE = "ROLE_ADMIN";
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private Basket basket;
	
//	@ModelAttribute("page")
//    public String module() {
//        return "home";
//    }
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getCurrentMenu(Model model) {
		LOG.debug("Yummy MenuItemDetails to home view");
		model.addAttribute("menuItems", menuService.requestAllMenuItems()); //getMenuItems(menuService.requestAllMenuItems(new RequestAllMenuItemsEvent()))
		return "/home";
//		return user.isAdmin() ? "/adminHome" : "/home";
		
//		http://stackoverflow.com/questions/3021200/how-to-check-hasrole-in-java-code-with-spring-security
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		return authorities.contains(new SimpleGrantedAuthority(ADMIN_ROLE)) ? "/adminHome" : "/home";
		
//		private boolean isRolePresent(Collection<GrantedAuthority> authorities, String role) {
//		    boolean isRolePresent = false;
//		    for (GrantedAuthority grantedAuthority : authorities) {
//		      isRolePresent = grantedAuthority.getAuthority().equals(role);
//		      if (isRolePresent) break;
//		    }
//		    return isRolePresent;
//		  }
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String getItems(Model model) {
		LOG.debug("Yummy MenuItemDetails to admin view");
		model.addAttribute("menuItems", menuService.requestAllMenuItems());
		return "/admin";
	}

	@RequestMapping(value = "/addMenuItem")  //bez lomitka
	public String addMenuItem(@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		if (AjaxUtils.isAjaxRequest(requestedWith)) {
            return "admin/menu_item-add".concat(" :: addMenuItemForm");
        }
        return "admin/menu_item-add";		//bez lomitka
    }
	
	@RequestMapping(value = "/addMenuItem", method = RequestMethod.POST)
	public String addMenuItem(@Valid @ModelAttribute("addMenuItemForm") MenuItem menuItem, Errors errors, RedirectAttributes redirectAttrs) {  //BindingResult result
		if (errors.hasErrors()) {
			return "/admin/menu_item-add";
		}
		LOG.debug("No errors, continue with creating of menu item {}:", menuItem.getName());
		menuService.createMenuItem(menuItem);
//		redirectAttrs.addFlashAttribute("message","Menu item has been created!");
		MessageHelper.addSuccessAttribute(redirectAttrs, "Menu item has been created!");	//"addMenuItem.success"
		return "redirect:/admin";
	}
	
//	validacia pre update
//	redirect att
	@RequestMapping(value = "/updateMenuItem", method = RequestMethod.POST)
	public String updateMenuItem(@Valid @ModelAttribute("menuItemUpd") MenuItem menuItem, BindingResult result, RedirectAttributes redirectAttrs) {
		if (result.hasErrors()) {
//			MessageHelper.addErrorAttribute(redirectAttrs, "updateMenuItem.error");
			return "/admin";
		}
		LOG.debug("Update {} on MongoDB", menuItem.getId());
		menuService.updateMenuItem(menuItem);
		MessageHelper.addSuccessAttribute(redirectAttrs, "Menu item has been updated!");
		return "redirect:/admin";
	}

//	redirect att	
	@RequestMapping(value = "/deleteMenuItem", method = RequestMethod.POST)
	public String removeMenuItem(@ModelAttribute("menuItemDel") MenuItem menuItem, RedirectAttributes redirectAttrs) {
		LOG.debug("Remove {} from MongoDB", menuItem.getId());
		menuService.deleteMenuItem(menuItem.getId());
		MessageHelper.addSuccessAttribute(redirectAttrs, "Menu item has been deleted!");
		return "redirect:/admin";
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

	@ModelAttribute("addMenuItemForm")
	private MenuItem getMenuItem() {
		return new MenuItem();
	}
}
