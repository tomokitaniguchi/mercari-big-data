package com.example.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
// import org.springframework.batch.item.Chunk;
// import org.springframework.batch.item.ItemWriter;
// import org.springframework.batch.item.database.JpaItemWriter;
// import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

// import com.example.domain.Index;
// import com.example.domain.Original;

@Component
@StepScope
public class ItemsProcessor {//implements ItemProcessor<A, Index>{
  
  // @Override
  // public Index process(Original original) throws Exception {
  //   Index index = new Index();

  //   index.setId(original.getId());
  //   index.setName(original.getName());
  //   index.setCondition(original.getCondition_id());
  //   index.setCategory(original.getCategory_name());
  //   index.setBrand(original.getBrand());
  //   index.setPrice(original.getPrice());
  //   index.setShipping(original.getShipping());
  //   index.setDescription(original.getDescription());

  //   return index;
  // }
}


  