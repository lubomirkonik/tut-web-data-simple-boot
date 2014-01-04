package tut.webdata.services;

import java.util.List;

import tut.webdata.domain.MenuItem;

public interface MenuService {

  List<MenuItem> requestAllMenuItems();
  MenuItem requestMenuItem(String id);
  MenuItem createMenuItem(MenuItem menuItem);

}
