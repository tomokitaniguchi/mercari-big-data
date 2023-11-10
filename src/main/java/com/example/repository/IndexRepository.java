package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Index;

@Repository
public class IndexRepository {
   @Autowired
   private NamedParameterJdbcTemplate template;

   private static final RowMapper<Index> LIST_ROW_MAPPER = new BeanPropertyRowMapper<>(Index.class);

   public List<Index> index(){
       String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items;";
       List<Index> indexList = template.query(sql,LIST_ROW_MAPPER);
       return indexList;
   }
}
