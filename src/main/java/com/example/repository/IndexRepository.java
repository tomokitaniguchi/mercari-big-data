package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION \n" + //
               "FROM ITEMS AS I INNER JOIN \n" + //
               "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY \n" + //
               "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
               "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
               "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
               "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
               "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
               "ORDER BY ID) AS C ON I.CATEGORY = C.ID ORDER BY ID LIMIT 30 OFFSET 0;";
       List<Items> indexList = template.query(sql,LIST_ROW_MAPPER);
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

   /**
    * 商品検索機能
    * @param items
    * @return
    */
   public List<Items> search(Items items){
    // 全項目検索
    if(items.name!=null && items.bigCategory!=null && items.middleCategory!=null
    && items.smallCategory!=null && items.brand!=null){
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE NAME LIKE :name AND BIG_CATEGORY = :bigCategory AND MIDDLE_CATEGORY = :middleCategory AND SMALL_CATEGORY = :smallCategory AND brand LIKE :brand ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource()
       .addValue("name", "%" + items.name + "%")
       .addValue("bigCategory", items.bigCategory)
       .addValue("middleCategory", items.middleCategory)
       .addValue("smallCategory", items.smallCategory) 
       .addValue("brand", "%" + items.brand + "%"); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("全項目で検索されました");
       return searchList;
    }
    // 商品名と大中小カテゴリーで検索
    if (items.name!=null && items.bigCategory!=null && items.middleCategory!=null && items.smallCategory!=null) {
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE name LIKE :name AND BIG_CATEGORY = :bigCategory AND MIDDLE_CATEGORY = :middleCategory AND SMALL_CATEGORY = :smallCategory ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource()
       .addValue("name", "%" + items.name + "%")
       .addValue("bigCategory", items.bigCategory)
       .addValue("middleCategory", items.middleCategory)
       .addValue("smallCategory", items.smallCategory); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("商品名と大中小カテゴリーで検索されました");
       return searchList;
    }
    // 商品名と大カテゴリーと中カテゴリーで検索
    if (items.name!=null && items.bigCategory!=null && items.middleCategory!=null) {
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE name LIKE :name AND BIG_CATEGORY = :bigCategory AND MIDDLE_CATEGORY = :middleCategory ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource()
       .addValue("name", "%" + items.name + "%")
       .addValue("bigCategory", items.bigCategory)
       .addValue("middleCategory", items.middleCategory); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("商品名と大カテゴリーと中カテゴリーで検索されました");
       return searchList;
    }
    // 商品名と大カテゴリーとブランド名で検索
    if (items.name!=null && items.bigCategory!=null && items.brand!=null) {
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE name LIKE :name AND BIG_CATEGORY = :bigCategory AND brand = :brand ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource()
       .addValue("name", "%" + items.name + "%")
       .addValue("bigCategory", items.bigCategory)
       .addValue("brand", items.brand); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("商品名と大カテゴリーとブランド名で検索されました");
       return searchList;
    }
    // 商品名と大カテゴリーで検索
    if (items.name!=null && items.bigCategory!=null) {
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE name LIKE :name AND BIG_CATEGORY = :bigCategory ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource()
       .addValue("name", "%" + items.name + "%")
       .addValue("bigCategory", items.bigCategory); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("商品名と大カテゴリーで検索されました");
       return searchList;
    }
    // 商品名だけで検索
    if (items.name!=null) {
      String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE name LIKE :name ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + items.name + "%"); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("商品名だけで検索されました");
       return searchList; 
    } 
    // 大中小カテゴリーとブランド名で検索
    if (items.bigCategory!=null && items.middleCategory!=null && items.smallCategory!=null && items.brand!=null) {
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE BIG_CATEGORY = :bigCategory AND MIDDLE_CATEGORY = :middleCategory AND SMALL_CATEGORY = :smallCategory AND BRAND LIKE :brand ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource()
       .addValue("bigCategory", items.bigCategory)
       .addValue("middleCategory", items.middleCategory)
       .addValue("smallCategory", items.smallCategory)
       .addValue("brand", "%" + items.brand + "%");
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("大中小カテゴリーとブランド名で検索されました");
       return searchList;
    }
    // 大カテゴリーと中カテゴリーとブランド名で検索
    if (items.bigCategory!=null && items.middleCategory!=null && items.brand!=null) {
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE BIG_CATEGORY = :bigCategory AND MIDDLE_CATEGORY = :middleCategory AND BRAND LIKE :brand ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource()
       .addValue("bigCategory", items.bigCategory)
       .addValue("middleCategory", items.middleCategory)
       .addValue("brand", "%" + items.brand + "%");
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("大カテゴリーと中カテゴリーとブランド名で検索されました");
       return searchList;
    }
    // 大カテゴリーとブランド名で検索
    if (items.bigCategory!=null && items.brand!=null) {
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE BIG_CATEGORY = :bigCategory AND BRAND LIKE :brand ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource()
       .addValue("bigCategory", items.bigCategory)
       .addValue("brand", "%" + items.brand + "%");
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("大カテゴリーとブランド名で検索されました");
       return searchList;
    }
    // 大中小カテゴリーで検索
    if(items.bigCategory!=null && items.middleCategory!=null && items.smallCategory!=null){
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE BIG_CATEGORY = :bigCategory AND MIDDLE_CATEGORY = :middleCategory AND SMALL_CATEGORY = :smallCategory ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource().addValue("bigCategory", items.bigCategory).addValue("middleCategory", items.middleCategory).addValue("smallCategory", items.smallCategory); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("大+中+小カテゴリーで検索されました");
       return searchList;
    } 
    // 大中カテゴリーで検索
    if(items.bigCategory!=null && items.middleCategory!=null){
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE BIG_CATEGORY = :bigCategory AND MIDDLE_CATEGORY = :middleCategory ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource().addValue("bigCategory", items.bigCategory).addValue("middleCategory", items.middleCategory); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("大+中カテゴリーで検索されました");
       return searchList;
    } 
    // 大カテゴリーだけで検索
    if(items.bigCategory!=null){
        String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE BIG_CATEGORY = :bigCategory ORDER BY ID LIMIT 30 OFFSET 0;";
        SqlParameterSource param = new MapSqlParameterSource().addValue("bigCategory", items.bigCategory); 
        List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
        System.out.println("大カテゴリーだけで検索されました"+items.bigCategory);
        return searchList;
    }
    // 中カテゴリーだけで検索
    if (items.parentId!=null){
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE PARENT_ID = :parent_id ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource().addValue("parent_id", items.parentId); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("中カテゴリーだけで検索されました");
       return searchList;
    } 
    // 小カテゴリーだけで検索
    if (items.category!=null){
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE CATEGORY = :category ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource().addValue("category", items.category); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("小カテゴリーだけで検索されました");
       return searchList;
    } 
    // ブランド名だけで検索
    if (items.brand!=null){
       String sql = "SELECT I.ID,NAME,CONDITION,PARENT_ID,CATEGORY,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION\n" + //
            "FROM ITEMS AS I INNER JOIN \n" + //
            "(SELECT A.ID,A.PARENT_ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY\n" + //
            "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
            "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
            "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
            "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
            "ORDER BY ID) AS C ON I.CATEGORY = C.ID\n" + //
            "WHERE BRAND = :brand ORDER BY ID LIMIT 30 OFFSET 0;";
       SqlParameterSource param = new MapSqlParameterSource().addValue("brand", items.brand); 
       List<Items> searchList = template.query(sql, param, LIST_ROW_MAPPER);
       System.out.println("ブランド名だけで検索されました");
       return searchList;
    }
    return null;
  }
}
