package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.items;
import com.example.service.DetailService;

// import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/detail")
public class DetailController {
  
  // @Autowired
	// private HttpSession session;

  @Autowired
  private DetailService service;

  @GetMapping("")
  public String detail(Model model, Integer id){
    // 商品詳細情報を取得
    List<items> detailList = service.detailList(id);
    model.addAttribute("detailList", detailList);
    return "detail.html";
  }
}
