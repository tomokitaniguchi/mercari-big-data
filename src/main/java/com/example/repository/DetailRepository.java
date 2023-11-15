package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.items;

@Repository
public class DetailRepository {
  
     @Autowired
     private NamedParameterJdbcTemplate template;

     private static final RowMapper<items> LIST_ROW_MAPPER = new BeanPropertyRowMapper<>(items.class);

     public List<items> detail(Integer id){
      String sql = "SELECT i.id, name, condition, c.category, brand, price, shipping, description FROM items AS i INNER JOIN (SELECT id, name_all AS category FROM category) AS c ON i.category = c.id WHERE i.id=:id ";
      SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
      List<items> detailList = template.query(sql,param,LIST_ROW_MAPPER);
      return detailList;
     }
}
