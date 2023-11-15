package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.items;
import com.example.repository.IndexRepository;

@Service
public class IndexService {
  
  @Autowired
  private IndexRepository repository;
  
  public List<items> index(){
    return repository.index();
  }

  public List<items> bigCategory(){
    return repository.bigCategory();
  }

  public List<items> middleCategory(){
    return repository.middleCategory();
  }

  public List<items> smallCategory(){
    return repository.smallCategory();
  }
}
