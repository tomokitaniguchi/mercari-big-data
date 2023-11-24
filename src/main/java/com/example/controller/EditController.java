package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Items;
import com.example.form.EditForm;
import com.example.service.EditService;

@Controller
@RequestMapping("/edit")
public class EditController {
  @Autowired
  private EditService editService;
  @Autowired
  private EditService detailService;

  @GetMapping("")
  public String edit(Model model, Integer id, Items items){
    List<Items> detailList = detailService.detailList(id);
    model.addAttribute("detailList", detailList);

    // 大カテゴリーを取得 
    List<Items> bigCategoryList = editService.bigCategory();
    model.addAttribute("bigCategoryList",bigCategoryList);
    // 中カテゴリーを取得
    List<Items> middleCategoryList = editService.middleCategory(detailList.get(0));
    model.addAttribute("middleCategoryList",middleCategoryList);
    // 小カテゴリーを取得
    List<Items> smallCategoryList = editService.smallCategory(detailList.get(0));
    model.addAttribute("smallCategoryList",smallCategoryList);
    return "edit.html";
  }

  @PostMapping("/select")
  public String handleEditRequest(Items item, EditForm form, @RequestParam("bigCategory") String bigCategory){
    System.out.println(bigCategory+"q");
    // BeanUtils.copyProperties(form, item);
    editService.middleCategory(item);
    System.out.println(editService.middleCategory(item));
    return "redirect:/edit";
  }
}
