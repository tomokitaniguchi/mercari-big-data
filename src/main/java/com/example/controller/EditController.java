package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Items;
import com.example.form.EditForm;
import com.example.service.EditService;
import com.example.service.IndexService;

@Controller
@RequestMapping("/edit")
public class EditController {
  @Autowired
  private IndexService indexService;
  @Autowired
  private EditService editService;
  @Autowired
  private EditService detailService;

  @GetMapping("")
  public String edit(Model model, Integer id){

    List<Items> detailList = detailService.detailList(id);
    model.addAttribute("detailList", detailList);

    // 大カテゴリーを取得 
    List<Items> bigCategoryList = indexService.bigCategory();
    model.addAttribute("bigCategoryList",bigCategoryList);
    // 中カテゴリーを取得
    List<Items> middleCategoryList = indexService.middleCategory();
    model.addAttribute("middleCategoryList",middleCategoryList);
    // 小カテゴリーを取得
    List<Items> smallCategoryList = indexService.smallCategory();
    model.addAttribute("smallCategoryList",smallCategoryList);
    return "edit.html";
  }

  @PostMapping("/edit-do")
  public String editDo(Items item, EditForm form){
    System.out.println(form);
    editService.edit(item);
    return "detail.html";
  }
}
