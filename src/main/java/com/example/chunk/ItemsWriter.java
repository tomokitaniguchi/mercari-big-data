package com.example.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.domain.Index;

@Component
@StepScope
public class ItemsWriter implements ItemWriter<Index> {
  
  @Override
  public void write(Chunk<? extends Index> chunk) throws Exception {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'write'");
  }
}
