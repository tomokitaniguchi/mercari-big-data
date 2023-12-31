package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Items;
import com.example.repository.DetailRepository;

@Service

public class DetailService {

  @Autowired
  private DetailRepository repository;

  public List<Items> detailList(Integer id){
    return repository.detail(id);
  }
}
