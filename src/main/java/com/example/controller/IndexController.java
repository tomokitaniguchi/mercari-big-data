package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.IndexService;

@Controller
@RequestMapping("/index")
public class IndexController {

  @Autowired
  private IndexService service;
  
  @GetMapping("")
  public String index(){
    return "list.html";
  }

  @PostMapping("/post")
  public String indexPost(){
    return "redirect:list";
  }
}
