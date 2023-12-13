package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {

  public Integer id;
  public String name;
  public Integer condition;
  public Integer parentId;
  public Integer category;
  public String bigCategory;
  public String middleCategory;
  public String smallCategory;
  public String brand;
  public Integer price;
  public Integer shipping;
  public String description;
}
