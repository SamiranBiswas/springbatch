package com.capgemini.springbatch.jobconfig;

import com.capgemini.springbatch.service.EmployeeServiceAdaptor;
import com.capgemini.springbatch.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;

@Configuration
public class SpringJob {
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("HelloJob")
                .incrementer(new RunIdIncrementer())
                .start(step()).build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("HelloWorld").chunk(1)
              //  .reader(flatFileItemReader(null))
               // .reader(xmlItemReader(null))
              //  .reader(jdbcBatchItemWriter(null))
                .reader(itemReaderAdapter(null))
                .writer(items -> items.stream().forEach(i ->
                        System.out.println(i))).build();
    }

    @Bean
    public ItemReaderAdapter itemReaderAdapter(EmployeeServiceAdaptor employeeService){
        ItemReaderAdapter<Employee> adapter = new ItemReaderAdapter<>();
        adapter.setTargetObject(employeeService);
        adapter.setTargetMethod("nextEmployee");
        return adapter;
    }


    @Bean
    public JdbcCursorItemReader<Employee> jdbcBatchItemWriter(DataSource dataSource){
        BeanPropertyRowMapper<Employee> mapper = new BeanPropertyRowMapper<>();
        mapper.setMappedClass(Employee.class);
        return new JdbcCursorItemReaderBuilder<Employee>()
                .name("JDBC_ITEM_READER")
                .dataSource(dataSource)
                .sql("select * from employee")
                .rowMapper(mapper)
                .build();
    }

    /**
     * XML-READER
     **/
    @Bean
    @StepScope
    public StaxEventItemReader xmlItemReader(@Value("#{jobParameters[fileInput]}")
                                                     FileSystemResource fileSystemResource){
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setMappedClass(Employee.class);
        return new StaxEventItemReaderBuilder()
                .name("XML")
                .resource(fileSystemResource)
                .addFragmentRootElements("employee")
                .unmarshaller(jaxb2Marshaller)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader flatFileItemReader( @Value("#{jobParameters[fileInput]}")
                                                              FileSystemResource fileSystemResource) {

        BeanWrapperFieldSetMapper setMapper = new BeanWrapperFieldSetMapper<Employee>();
        setMapper.setTargetType(Employee.class);
        return new FlatFileItemReaderBuilder<Employee>()
                .linesToSkip(1)
                .name("CSV-READER")
                // .resource(new FileSystemResource("input/employee.csv"))
                .resource(fileSystemResource)
                .delimited()
                .delimiter(",")
                .names("id", "name", "email")
                .fieldSetMapper(setMapper)
                .build();
        /*FlatFileItemReaderBuilder flatFileItemReader = new FlatFileItemReaderBuilder<Employee>();
        flatFileItemReader.name("ITEM-READER")
                .resource(new FileSystemResource("input/employee.csv"))
                .delimited().delimiter(",")
                .names("id","name","email")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>(){
                    @Override
                    public void setTargetType(Class<? extends Employee> type) {
                        super.setTargetType(type);
                    }
                });
        flatFileItemReader.linesToSkip(1);
        return flatFileItemReader.build();*/
    }

  /*  @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory, ItemReader<Employee> itemReader,
                   ItemWriter<Employee> itemWriter) {
        Step step = stepBuilderFactory.get("ETL-File-Load")
                .<Employee, Employee>chunk(10)
                .reader(itemReader)
                .processor((ItemProcessor<Employee, Employee>) item -> {
                    System.out.println("inside processor");
                    item.setName(item.getName().toUpperCase());
                    return item;
                })
                .writer(itemWriter).build();
        return jobBuilderFactory.get("ETL-JOB")
                .incrementer(new RunIdIncrementer())
                .start(step).build();
    }


    @Bean
    public ItemWriter<Employee> itemWriter(DataSource dataSource) {
        System.out.println("inside writer");
        return new JdbcBatchItemWriterBuilder<Employee>()
                .dataSource(dataSource)
                .sql("insert into employee (id,name,email) values(:id,:name,:email)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>())
                .build();

       *//* JdbcBatchItemWriter batchItemWriter = new JdbcBatchItemWriter();
        batchItemWriter.setDataSource(dataSource);
        batchItemWriter.setSql("insert into employee (id,name,email) values(:id,:name,:email)");
        batchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
        return batchItemWriter;*//*
    }

    @Bean
    public FlatFileItemReader<Employee> itemReader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .linesToSkip(1)
                .name("CSV-READER")
                .resource(new ClassPathResource("employee.csv"))
                .strict(false)
                .delimited()
                .delimiter(",")
                .names("id","name","email")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {{
                    setTargetType(Employee.class);
                }})
                .build();
       *//* FlatFileItemReader flatFileItemReader = new FlatFileItemReader();
        flatFileItemReader.setResource(new ClassPathResource("employee.csv"));
        flatFileItemReader.setName("CSV-READER");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;*//*
    }

    @Bean
    public LineMapper lineMapper() {
        DefaultLineMapper defaultLineMapper = new DefaultLineMapper();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        // delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "name", "email");
        BeanWrapperFieldSetMapper setMapper = new BeanWrapperFieldSetMapper();
        setMapper.setTargetType(Employee.class);
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(setMapper);
        return defaultLineMapper;
    }*/
}
