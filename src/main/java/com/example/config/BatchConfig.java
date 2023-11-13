package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.domain.Index;
import com.example.domain.Original;

@Component
@Configuration
public class BatchConfig {

  @Autowired
	private ItemReader<? extends Original> reader;
  @Autowired
	private ItemProcessor<? super Original, ? extends Index> processor;
  @Autowired
	private ItemWriter<? super Index> writer;

  @Bean
  Step stepOfRailwayLine(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepOfIndex", jobRepository)
                .<Original, Index>chunk(1000, transactionManager)
                .reader(reader)
                .processor(processor).writer(writer).build();
    }

  @Bean
  Job job(JobRepository jobRepository, Step stepOfPrefecture, Step stepOfMunicipality, Step stepOfAddress,
          Step stepOfHittakuriTokyo2021, Step stepOfHittakuriTokyo2020, Step stepOfHittakuriTokyo2019,
          Step stepOfRailwayLine, Step stepOfStation) {
      return new JobBuilder("job", jobRepository).incrementer(new RunIdIncrementer()).start(stepOfPrefecture)
              .next(stepOfMunicipality).next(stepOfAddress).next(stepOfHittakuriTokyo2021)
              .next(stepOfHittakuriTokyo2020).next(stepOfHittakuriTokyo2019).next(stepOfRailwayLine)
              .next(stepOfStation).build();
    }
}
