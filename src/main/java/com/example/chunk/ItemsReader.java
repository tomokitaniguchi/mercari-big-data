package com.example.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.domain.Original;

@Component //後々BatchConfigでAutowiredするためDIコンテナに管理させるためのアノテーション
@StepScope //同じStepでのみ同じインスタンスが使われるようにするスコープ
public class ItemsReader implements ItemReader<Original>{

  private FlatFileItemReader<Original> reader;

  private static final String[] COLUMNS = {"id", "name", "condition", "category", "brand", "price", "shipping", "description"};

  // 文字化け対策のために設定
  private static final String ENCORDING_TYPE = "UTF-8";

  // 一列目はカラム名なのでスキップする
  private static final int LINES_TO_SKIP = 1;

  public ItemsReader(String classPath) {
        // リーダーを宣言
        reader = new FlatFileItemReader<>();
        // 引数で渡されたCSVクラスをリーダーにセット
        reader.setResource(new ClassPathResource(classPath));
        reader.setLineMapper(createLineMapper());
        reader.setLinesToSkip(LINES_TO_SKIP);
        reader.setEncoding(ENCORDING_TYPE);
        reader.open(new ExecutionContext());
    }

  /**
   * readメソッドをオーバーライドし、Index型で返すように設定.
   */
  @Override
  public Original read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    return reader.read();
  }
  
   /**
     * Index型一行ずつ詰めたLineMapperを返すメソッド.
     * 
     * @return
     */
  private LineMapper<Original> createLineMapper() {
        DefaultLineMapper<Original> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(createLineTokenizer());
        BeanWrapperFieldSetMapper<Original> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        // 分けたカラムはOriginalに準ずることを設定
        fieldSetMapper.setTargetType(Original.class);
        // 全行入れることを設定
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    private LineTokenizer createLineTokenizer() {
        // CSVファイルのためカンマで区切ることを設定
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_COMMA);
        tokenizer.setNames(COLUMNS);

        return tokenizer;
    }
}
