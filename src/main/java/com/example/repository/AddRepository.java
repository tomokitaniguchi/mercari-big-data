package com.example.repository;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Items;

@Repository
public class AddRepository {
  
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Items> LIST_ROW_MAPPER = new BeanPropertyRowMapper<>(Items.class);

    /**
     * 新規商品情報を追加
     * @param items
     */
    public void add(Items items){
      String sql = "INSERT INTO ITEMS (name,price,category,brand,condition,shipping,description) VALUES (:name,:price,:category,:brand,:condition,:shipping,:description);";
      SqlParameterSource param = new BeanPropertySqlParameterSource(items);
      template.update(sql, param);
    }

    /**
     * 追加した商品のidを取得する
     */
    public List<Items> getAddItem(){
      String sql = "SELECT id FROM items WHERE id = (SELECT MAX(id) FROM items);";
      List<Items> getAddItemList = template.query(sql, LIST_ROW_MAPPER);
      return getAddItemList;
    }
}
