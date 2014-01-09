package tut.webdata.domain;

//import tut.webdata.events.menu.MenuItemDetails;
import org.hibernate.validator.constraints.NotEmpty;
//import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// {!begin top}
@Document(collection = "menu")
public class MenuItem implements Serializable {

  @NotNull  //err -
  @NotEmpty	//obmedzenie funguje
  @Id
  private String id;

  @NotNull
  @NotEmpty
  @Field("itemName")
  @Indexed
  private String name;
// {!end top}

  private String description;

  private Set<Ingredient> ingredients;
  
//  @NotNull
//  @NotEmpty
  @DecimalMin(value = "0.1")
  @DecimalMax(value = "99.99")
  private BigDecimal cost;

//  @NotNull
//  @NotEmpty
  @Min(value = 1)
  @Max(value = 60)
  private int minutesToPrepare;

  public String getDescription() {
    return description;
  }

  public Set<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setIngredients(Set<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public int getMinutesToPrepare() {
    return minutesToPrepare;
  }

  public void setMinutesToPrepare(int minutesToPrepare) {
    this.minutesToPrepare = minutesToPrepare;
  }

//  public MenuItemDetails toStatusDetails() {
//    return new MenuItemDetails(id, name, cost, minutesToPrepare);
//  }
//
//  public static MenuItem fromStatusDetails(MenuItemDetails orderStatusDetails) {
//    MenuItem item = new MenuItem();
//
//    item.cost = orderStatusDetails.getCost();
//    item.id = orderStatusDetails.getId();
//    item.minutesToPrepare = orderStatusDetails.getMinutesToPrepare();
//    item.name = orderStatusDetails.getName();
//
//    return item;
//  }
  
  private boolean entityFound = true;
  public static MenuItem notFound(String key) {
	  MenuItem ev = new MenuItem();
	  ev.id = key;
	  ev.entityFound=false;
	  return ev;
  }
}