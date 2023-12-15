package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Items;
import com.example.service.IndexService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/index")
public class IndexController {

  @Autowired
  private IndexService service;
  @Autowired
  private HttpSession session;
  
  /**
   * 商品一覧画面を表示
   * @param model
   * @param items
   * @return
   */
  @GetMapping("")
  public String index(Model model,Items items){
    // 商品一覧を取得
    List<Items> indexList = service.index();
    session.setAttribute("indexList",indexList);
    // 大カテゴリーを取得 
    List<Items> bigCategoryList = service.bigCategory();
    model.addAttribute("bigCategoryList",bigCategoryList);
    // 中カテゴリーを取得
    List<Items> middleCategoryList = service.middleCategory();
    model.addAttribute("middleCategoryList",middleCategoryList);
    // 小カテゴリーを取得
    List<Items> smallCategoryList = service.smallCategory();
    model.addAttribute("smallCategoryList",smallCategoryList);
    return "list.html";
  }

  /**
   * 商品検索機能
   * @param model
   * @param items
   * @return
   */
  @GetMapping("/search")
  public String search(Model model, Items items){
    System.out.println("最初に入った"+items);
    // 空文字などをnullに統一
    if (items.name=="") { 
      items.name=null;
    } 
    if (items.bigCategory==null || items.bigCategory=="") {
      items.bigCategory=null;
    }
    if (items.middleCategory==null || items.middleCategory=="" || items.middleCategory.equals("- childCategory -")) {
      items.middleCategory=null;
    }
    if (items.smallCategory==null || items.smallCategory=="" || items.smallCategory.equals("- grandChild -")) {
      items.smallCategory=null;
    }
    if (items.brand=="") {
      items.brand=null;
    }
    // 項目が一つも選ばれていない場合、一覧表示にリダイレクト
    if (items.name==null && items.category==null && items.parentId==null
    && items.bigCategory==null && items.brand==null){
      System.out.println("未選択");
      return "redirect:/index";
    }
    // 商品情報を格納
    session.setAttribute("indexList",service.search(items));
    model.addAttribute("bigCategoryList",service.bigCategory());
    model.addAttribute("middleCategoryList",service.middleCategory());
    model.addAttribute("smallCategoryList",service.smallCategory());
    return "list.html";
  }
}
