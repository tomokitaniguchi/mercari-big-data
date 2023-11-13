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
       System.out.println(indexList);
       return indexList;
   }

   /**
    * 大カテゴリーを取得する
    * @return 
    */
   public List<Index> bigCategory(){
       String sql = "SELECT name FROM big_category;";
       List<Index> bigCategoryList = template.query(sql,LIST_ROW_MAPPER);
       return bigCategoryList;
   }

   /**
    * 中カテゴリーを取得する
    * @return
    */
   public List<Index> middleCategory(){
       String sql = "SELECT name FROM category WHERE id BETWEEN 11 AND 148;";
       List<Index> middleCategoryList = template.query(sql,LIST_ROW_MAPPER);
       return middleCategoryList;
   }

   /**
    * 小カテゴリーを取得
    * @return
    */
   public List<Index> smallCategory(){
       String sql = "SELECT name FROM category WHERE id BETWEEN 149 AND 1435;";
       List<Index> smallCategoryList = template.query(sql,LIST_ROW_MAPPER);
       return smallCategoryList;
   }
}
