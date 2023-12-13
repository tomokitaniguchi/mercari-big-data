package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Items;
import com.example.repository.IndexRepository;

@Service
public class IndexService {
  
  @Autowired
  private IndexRepository repository;
  
  public List<Items> index(){
    return repository.index();
  }

  public List<Items> bigCategory(){
    return repository.bigCategory();
  }

  public List<Items> middleCategory(){
    return repository.middleCategory();
  }

  public List<Items> smallCategory(){
    return repository.smallCategory();
  }

  public List<Items> search(Items items){
    return repository.search(items); 
  }
}
