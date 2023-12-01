package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Items;

@Repository
public class EditRepository {

  @Autowired
  private NamedParameterJdbcTemplate template;

  private static final RowMapper<Items> LIST_ROW_MAPPER = new BeanPropertyRowMapper<>(Items.class);

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
    * 大カテゴリーに付随した中カテゴリーを取得する
    * @return
    */
   public List<Items> middleCategory(Items items,String bigCategory){
       String sql = "SELECT distinct MIDDLE_CATEGORY,BIG_CATEGORY\n" + //
               "FROM ITEMS AS I INNER JOIN (\n" + //
               "SELECT A.ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY \n" + //
               "FROM CATEGORY AS A \n" + //
               " INNER JOIN (\n" + //
               "SELECT DISTINCT ID,\n" + //
               "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
               "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
               "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
               "FROM CATEGORY WHERE NAME_ALL IS NOT NULL \n" + //
               " ) AS B ON A.ID = B.ID\n" + //
               "ORDER BY ID\n" + //
               ") AS C ON I.CATEGORY = C.ID WHERE BIG_CATEGORY=:bigCategory order by MIDDLE_CATEGORY;";
      if (bigCategory!=null) { 
        // 大カテゴリーが変更されたとき
        SqlParameterSource param = new MapSqlParameterSource().addValue("bigCategory", bigCategory);
        List<Items> middleCategoryList = template.query(sql,param,LIST_ROW_MAPPER);
        return middleCategoryList;
      } else {
        SqlParameterSource param = new MapSqlParameterSource().addValue("bigCategory", items.bigCategory);
        List<Items> middleCategoryList = template.query(sql,param,LIST_ROW_MAPPER);
        return middleCategoryList;
       }     
   }

  /**
   * 中カテゴリーに付随した小カテゴリーを取得する
   * @return
   */
   public List<Items> smallCategory(Items items, String middleCategory){
     String sql = "SELECT distinct SMALL_CATEGORY,MIDDLE_CATEGORY\n" + //
         "FROM ITEMS AS I INNER JOIN (\n" + //
         "SELECT A.ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY \n" + //
         "FROM CATEGORY AS A \n" + //
         " INNER JOIN (\n" + //
         "SELECT DISTINCT ID,\n" + //
         "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
         "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
         "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
         "FROM CATEGORY WHERE NAME_ALL IS NOT NULL \n" + //
         " ) AS B ON A.ID = B.ID\n" + //
         "ORDER BY ID\n" + //
         ") AS C ON I.CATEGORY = C.ID WHERE MIDDLE_CATEGORY=:middleCategory order by SMALL_CATEGORY;";
      if (middleCategory!=null) { 
        // 中カテゴリーが変更されたとき
        SqlParameterSource param = new MapSqlParameterSource().addValue("middleCategory", middleCategory);
        List<Items> smallCategoryList = template.query(sql,param,LIST_ROW_MAPPER);
        return smallCategoryList;
      } else {
        SqlParameterSource param = new MapSqlParameterSource().addValue("middleCategory", items.middleCategory);
        List<Items> smallCategoryList = template.query(sql,param,LIST_ROW_MAPPER);
        return smallCategoryList;
       }  
   }

   /**
    * 商品情報を変更
    * @param items
    */
   public void edit(Items items){
    String sql = "UPDATE items SET name=:name, price=:price, big_category=:bigCategory, middle_category=:middleCategory, small_category=:smallCategory, brand=:brand, condition=:condition, description=:description WHERE id=:id;";
    SqlParameterSource param = new BeanPropertySqlParameterSource(items);
    template.update(sql, param);
   }
}
