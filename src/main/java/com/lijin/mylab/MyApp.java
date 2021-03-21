package com.lijin.mylab;

import com.lijin.mylab.utils.DateUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;


@SpringBootApplication
@MapperScan("com.lijin.mylab.dao.mybatis.mapper")
public class MyApp {
	public static final Logger logger = LoggerFactory.getLogger(MyApp.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MyApp.class);
		app.addInitializers(applicationContext -> {
			logger.info("#############启动啦##########");
			logger.info("Welcomde: " + DateUtil.now19());
		});
		app.addListeners(new ApplicationListener<ApplicationEvent>() {
			@Override
			public void onApplicationEvent(ApplicationEvent event) {
				logger.info("#######App Event: {}########", event.toString());
			}
		});
		app.run(args);
	}

	@Bean
	public CommandLineRunner start() {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				logger.info("####app is tarted...#####");
				logger.info("####启动参数：" + Arrays.toString(args));
			}
		};
	}

}
