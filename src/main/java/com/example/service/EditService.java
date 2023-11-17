package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Items;
import com.example.repository.DetailRepository;
import com.example.repository.EditRepository;

@Service
public class EditService {
  @Autowired
  private EditRepository editRepository;
  @Autowired
  private DetailRepository detailRepository;

  public void edit(Items item){
    editRepository.edit(item);
  }

  public List<Items> detailList(Integer id) {
    return detailRepository.detail(id);
  }

}
