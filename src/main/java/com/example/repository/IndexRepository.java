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
       String sql = "SELECT i.id, name, condition, c.category, brand, price, shipping, description FROM items AS i INNER JOIN (SELECT id, name_all AS category FROM category) AS c ON i.category = c.id ORDER BY id LIMIT 20 OFFSET 0;";
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
       String sql = "SELECT distinct name FROM category WHERE id BETWEEN 11 AND 148 ORDER BY name;";
       List<Index> middleCategoryList = template.query(sql,LIST_ROW_MAPPER);
       return middleCategoryList;
   }

   /**
    * 小カテゴリーを取得
    * @return
    */
   public List<Index> smallCategory(){
       String sql = "SELECT distinct name FROM category WHERE id BETWEEN 149 AND 1435 ORDER BY name;";
       List<Index> smallCategoryList = template.query(sql,LIST_ROW_MAPPER);
       return smallCategoryList;
   }
}
