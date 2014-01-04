package tut.webdata.services;

import java.util.ArrayList;
import java.util.List;

import tut.webdata.domain.MenuItem;
import tut.webdata.repository.MenuItemRepository;

public class MenuEventHandler implements MenuService {

	private MenuItemRepository menuItemRepository;

	public MenuEventHandler(MenuItemRepository menuItemRepository) {
		this.menuItemRepository = menuItemRepository;
	}
	
	@Override
	public List<MenuItem> requestAllMenuItems() {
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		for (MenuItem menuItem : menuItemRepository.findAll()) {
			menuItems.add(menuItem);
		}
		return menuItems;
	}

	@Override
	public MenuItem requestMenuItem(String id) {
		MenuItem item = menuItemRepository.findOne(id);
		if (item == null) {
			return MenuItem.notFound(id);
		}
		return item;
	}

	@Override
	public MenuItem createMenuItem(MenuItem menuItem) {
		MenuItem item = menuItemRepository.save(menuItem);
		return item;
	}

}
