package com.capgemini.springbatch;

import com.capgemini.springbatch.CustomJobLauncher.CustomJobLauncher;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.capgemini"})
public class SpringbatchApplication {

	public static void main(String[] args) {
	//	customJobLauncher.run();
		SpringApplication.run(SpringbatchApplication.class, args);
	}

}
