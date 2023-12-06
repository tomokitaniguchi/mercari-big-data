package com.example.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EditForm {

  @NotBlank(message = "商品名を入力してください。")
  public String name;
  public Integer condition;
  public String category;
  @NotNull(message = "カテゴリーを選んでください。")
  public String bigCategory;
  @NotNull(message = "カテゴリーを選んでください。")
  public String middleCategory;
  @NotNull(message = "カテゴリーを選んでください。")
  public String smallCategory;
  public String brand;
  @NotNull(message = "値段を入力してください。")
  public Integer price;
  public String description;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Integer getCondition() {
    return condition;
  }
  public void setCondition(Integer condition) {
    this.condition = condition;
  }
  public String getCategory() {
    return category;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public String getBigCategory() {
    return bigCategory;
  }
  public void setBigCategory(String bigCategory) {
    this.bigCategory = bigCategory;
  }
  public String getMiddleCategory() {
    return middleCategory;
  }
  public void setMiddleCategory(String middleCategory) {
    this.middleCategory = middleCategory;
  }
  public String getSmallCategory() {
    return smallCategory;
  }
  public void setSmallCategory(String smallCategory) {
    this.smallCategory = smallCategory;
  }
  public String getBrand() {
    return brand;
  }
  public void setBrand(String brand) {
    this.brand = brand;
  }
  public Integer getPrice() {
    return price;
  }
  public void setPrice(Integer price) {
    this.price = price;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  
}
