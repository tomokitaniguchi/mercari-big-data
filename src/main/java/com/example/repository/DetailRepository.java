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
public class DetailRepository {
  
     @Autowired
     private NamedParameterJdbcTemplate template;

     private static final RowMapper<Items> LIST_ROW_MAPPER = new BeanPropertyRowMapper<>(Items.class);

     public List<Items> detail(Integer id){
      String sql = "SELECT I.ID,NAME,CONDITION,BIG_CATEGORY,MIDDLE_CATEGORY,SMALL_CATEGORY,BRAND,PRICE,SHIPPING,DESCRIPTION \n" + //
                "FROM ITEMS AS I INNER JOIN \n" + //
                "(SELECT A.ID,B.BIG_CATEGORY,B.MIDDLE_CATEGORY,B.SMALL_CATEGORY \n" + //
                "FROM CATEGORY AS A INNER JOIN (SELECT DISTINCT ID,\n" + //
                "SPLIT_PART(NAME_ALL,'/',1) AS BIG_CATEGORY,\n" + //
                "SPLIT_PART(NAME_ALL,'/',2) AS MIDDLE_CATEGORY,\n" + //
                "SPLIT_PART(NAME_ALL,'/',3) AS SMALL_CATEGORY\n" + //
                "FROM CATEGORY WHERE NAME_ALL IS NOT NULL ) AS B ON A.ID = B.ID\n" + //
                "ORDER BY ID) AS C ON I.CATEGORY = C.ID WHERE i.id=:id ";
      SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
      List<Items> detailList = template.query(sql,param,LIST_ROW_MAPPER);
      return detailList;
     }
}
