package com.example.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.domain.Items;

@Component
@StepScope
public class ItemsWriter implements ItemWriter<Items> {
  
  @Override
  public void write(Chunk<? extends Items> chunk) throws Exception {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'write'");
  }
}
