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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

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
  @Autowired
  private HttpSession session;
  /**
   * 編集画面を表示する
   * @param model
   * @param id
   * @param items
   * @param bigCategory
   * @param middleCategory
   * @return
   */
  @GetMapping("")
  public String edit(Model model, Integer id, Items items, String bigCategory, String middleCategory, EditForm editForm){    
    // 商品情報を取得
    List<Items> detailList = detailService.detailList(id);
    model.addAttribute("detailList", detailList);
    // 大カテゴリーを取得 
    List<Items> bigCategoryList = editService.bigCategory();
    model.addAttribute("bigCategoryList",bigCategoryList);
    // 中カテゴリーを取得
    List<Items> middleCategoryList = editService.middleCategory(detailList.get(0), bigCategory);
    model.addAttribute("middleCategoryList",middleCategoryList);
    // 小カテゴリーを取得
    List<Items> smallCategoryList = editService.smallCategory(detailList.get(0), middleCategory);
    model.addAttribute("smallCategoryList",smallCategoryList);
    return "edit.html";
  }

  /**
   * 非同期処理のリクエストに対するレスポンスをJSON形式で返すメソッド
   * @param items
   * @param bigCategory
   * @param middleCategory
   * @return
   * @throws JsonProcessingException
   */
  @GetMapping("/category-response")
  public ResponseEntity<String> categoryResponse(Items items, String bigCategory, String middleCategory) throws JsonProcessingException{
    if (bigCategory!=null) {
      // 中カテゴリーを取得
      List<Items> middleCategoryList = editService.middleCategory(items, bigCategory);
      // middleCategoryList を JSON 形式に変換する
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonMiddleCategoryList = objectMapper.writeValueAsString(middleCategoryList);
      // JSON レスポンスを返す
      return ResponseEntity.status(HttpStatus.OK).body(jsonMiddleCategoryList);
    } else {
      // 小カテゴリーを取得
      List<Items> smallCategoryList = editService.smallCategory(items, middleCategory);
      // smallCategoryList を JSON 形式に変換する
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonMiddleCategoryList = objectMapper.writeValueAsString(smallCategoryList);
      // JSON レスポンスを返す
      return ResponseEntity.status(HttpStatus.OK).body(jsonMiddleCategoryList);
    }
  }
  
  /**
   * 商品情報を編集する
   * @param items
   * @return
   */
  @PostMapping("/edit-do")
  public String editDo(Model model, Items items, Integer id, @Validated EditForm editForm, BindingResult result, String bigCategory, String middleCategory){
    // エラー時の処理
    if (result.hasErrors()) {
      return edit(model, id, items, bigCategory, middleCategory, editForm);
    }
    // 必要なプロパティに値をセット
    if (items.getDescription()=="") {
      items.setDescription("No description yet");
    }
    if (items.getBrand()=="") {
      items.setBrand(null);
    }
    // データ型を整合
    items.setCategory(Integer.parseInt(items.getSmallCategory())); 
    // 更新処理
    editService.edit(items);
    // 商品情報を取得
    List<Items> detailList = detailService.detailList(id);
    session.setAttribute("detailList", detailList);
    return "/detail";
  }
}
