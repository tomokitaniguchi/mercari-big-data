package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Index {

  public Integer id;
  public String name;
  public Integer condition;
  public String category;
  public String brand;
  public Integer price;
  public Integer shipping;
  public String description;
}
