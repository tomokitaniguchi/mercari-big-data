package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Index;
import com.example.repository.IndexRepository;

@Service
public class IndexService {
  
  @Autowired
  private IndexRepository repository;
  
  public List<Index> index(){
    return repository.index();
  }

  public List<Index> bigCategory(){
    return repository.bigCategory();
  }

  public List<Index> middleCategory(){
    return repository.middleCategory();
  }

  public List<Index> smallCategory(){
    return repository.smallCategory();
  }
}
