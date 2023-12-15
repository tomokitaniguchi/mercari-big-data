package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Items;
import com.example.form.EditForm;
import com.example.service.AddService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/add")
public class AddController {

  @Autowired
  private AddService service;
  @Autowired
  private HttpSession session;

  @GetMapping("")
  public String add(Model model, EditForm editForm){
    model.addAttribute("bigCategoryList",service.bigCategory());
    model.addAttribute("middleCategoryList",service.middleCategory());
    model.addAttribute("smallCategoryList",service.smallCategory());
    return "add.html";
  }

  @PostMapping("/add-do")
  public String addDo(Model model, Items items, @Validated EditForm editForm, BindingResult result){
    System.out.println(items);
    // エラー時の処理
    if (result.hasErrors()) {
      return add(model, editForm);
    }
    // 必要なプロパティに値をセット
    if (items.getDescription()=="") {
      items.setDescription("No description yet");
    }
    items.setShipping(0);
    // データ型を整合
    items.setCategory(Integer.parseInt(items.getSmallCategory())); 
    // 商品追加処理
    service.add(items);
    // 商品情報を取得
    List<Items> getAddItemsList = service.getAddItems();
    for (Items getAddItems : getAddItemsList){
      Integer id = getAddItems.getId();
      List<Items> detailList = service.detailList(id);
      session.setAttribute("detailList", detailList);
    }
    return "/detail";
  }
}
