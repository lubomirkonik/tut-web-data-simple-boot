package tut.webdata.repository;

import tut.webdata.domain.MenuItem;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItem, String> { //, AnalyseIngredients

  public List<MenuItem> findByIngredientsNameIn(String... name);

}
