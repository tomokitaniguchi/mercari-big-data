package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Items;
import com.example.service.IndexService;

@Controller
@RequestMapping("/edit")
public class EditController {
  @Autowired
  private IndexService service;
  @GetMapping("")
  public String edit(Model model){
    // 大カテゴリーを取得 
    List<Items> bigCategoryList = service.bigCategory();
    model.addAttribute("bigCategoryList",bigCategoryList);
    // 中カテゴリーを取得
    List<Items> middleCategoryList = service.middleCategory();
    model.addAttribute("middleCategoryList",middleCategoryList);
    // 小カテゴリーを取得
    List<Items> smallCategoryList = service.smallCategory();
    model.addAttribute("smallCategoryList",smallCategoryList);
    return "edit.html";
  }
}
