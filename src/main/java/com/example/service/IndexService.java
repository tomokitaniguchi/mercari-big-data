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
  
  private List<Index> index(){
    return repository.index();
  }
}
