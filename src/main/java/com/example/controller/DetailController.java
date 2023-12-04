package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Items;
import com.example.service.DetailService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/detail")
public class DetailController {
  
  @Autowired
	private HttpSession session;

  @Autowired
  private DetailService service;

  @GetMapping("")
  public String detail(Integer id){
    // 商品詳細情報を取得
    List<Items> detailList = service.detailList(id);
    session.setAttribute("detailList", detailList);
    return "detail.html";
  }
}
