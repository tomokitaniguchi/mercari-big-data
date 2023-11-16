package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Items;

@Repository
public class IndexRepository {
   @Autowired
   private NamedParameterJdbcTemplate template;

   private static final RowMapper<Items> LIST_ROW_MAPPER = new BeanPropertyRowMapper<>(Items.class);

   /**
    * 商品一覧を取得する
    * @return
    */
   public List<Items> index(){
       String sql = "SELECT I.ID,NAME,CONDITION,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION \n" + //
               "FROM ITEMS AS I INNER JOIN \n" + //
               "(SELECT A.ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY \n" + //
               "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
               "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
               "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
               "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
               "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
               "ORDER BY ID) AS C ON I.CATEGORY = C.ID ORDER BY ID LIMIT 20 OFFSET 0;";
       List<Items> indexList = template.query(sql,LIST_ROW_MAPPER);
       System.out.println(indexList);
       return indexList;
   }

   /**
    * 大カテゴリーを取得する
    * @return 
    */
   public List<Items> bigCategory(){
       String sql = "SELECT name FROM big_category;";
       List<Items> bigCategoryList = template.query(sql,LIST_ROW_MAPPER);
       return bigCategoryList;
   }

   /**
    * 中カテゴリーを取得する
    * @return
    */
   public List<Items> middleCategory(){
       String sql = "SELECT distinct name FROM category WHERE id BETWEEN 11 AND 148 ORDER BY name;";
       List<Items> middleCategoryList = template.query(sql,LIST_ROW_MAPPER);
       return middleCategoryList;
   }

   /**
    * 小カテゴリーを取得
    * @return
    */
   public List<Items> smallCategory(){
       String sql = "SELECT distinct name FROM category WHERE id BETWEEN 149 AND 1435 ORDER BY name;";
       List<Items> smallCategoryList = template.query(sql,LIST_ROW_MAPPER);
       return smallCategoryList;
   }
}
