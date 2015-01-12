package tut.webdata.repository;

import tut.webdata.domain.MenuItem;

import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItem, String> {

//  public List<MenuItem> findByIngredientsNameIn(String... name);

}
