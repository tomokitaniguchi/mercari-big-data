package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Original {

  public Integer id;
  public String name;
  public Integer condition_id;
  public Integer category_name;
  public String brand;
  public Integer price;
  public Integer shipping;
  public String description;
  
}
