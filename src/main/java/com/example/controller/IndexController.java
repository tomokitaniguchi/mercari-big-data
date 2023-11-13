package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Index;
import com.example.service.IndexService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/index")
public class IndexController {

  @Autowired
	private HttpSession session;

  @Autowired
  private IndexService service;
  
  @GetMapping("")
  public String index(Model model){
    //大カテゴリーを取得 
    List<Index> bigCategoryList = service.bigCategory();
    model.addAttribute("bigCategoryList",bigCategoryList);
    // 中カテゴリーを取得
    List<Index> middleCategoryList = service.middleCategory();
    model.addAttribute("middleCategoryList",middleCategoryList);
    // 小カテゴリーを取得
    List<Index> smallCategoryList = service.smallCategory();
    model.addAttribute("smallCategoryList",smallCategoryList);

    return "list.html";
  }

  @PostMapping("/post")
  public String indexPost(){
    List<Index> indexList = service.index();
    session.setAttribute("indexList", indexList);
    return "redirect:list";
  }
}
