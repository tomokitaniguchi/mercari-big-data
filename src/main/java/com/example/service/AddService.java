package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Items;
import com.example.repository.AddRepository;
import com.example.repository.DetailRepository;
import com.example.repository.IndexRepository;

@Service
public class AddService {
  
  @Autowired
  private AddRepository addRepository;

  @Autowired
  private IndexRepository indexRepository;

  @Autowired
  private DetailRepository detailRepository;

  public List<Items> bigCategory(){
    return indexRepository.bigCategory();
  }

  public List<Items> middleCategory(){
    return indexRepository.middleCategory();
  }

  public List<Items> smallCategory(){
    return indexRepository.smallCategory();
  }

  public void add(Items items){
    addRepository.add(items);
  }

  public List<Items> detailList(Integer id) {
    return detailRepository.detail(id);
  }

  public List<Items> getAddItems(){
    return addRepository.getAddItem();
  }
}
