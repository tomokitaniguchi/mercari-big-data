package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Items;

@Repository
public class EditRepository {

  @Autowired
  private NamedParameterJdbcTemplate template;

     public void edit(Items items){
      String sql = "UPDATE items SET name=:name, price=:price, ";
      SqlParameterSource param = new BeanPropertySqlParameterSource(items);
      template.update(sql, param);
     }
}
