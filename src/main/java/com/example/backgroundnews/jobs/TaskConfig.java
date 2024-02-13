package com.example.backgroundnews.jobs;

import com.example.backgroundnews.service.Telegram;
import com.example.backgroundnews.tasklet.CrollingTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * PackageName 	: batch
 * FileName 	: TaskConfig
 * Author 		: jhchoi
 * Date 		: 2022-05-24
 * Description 	:
 * ======================================================
 * DATE				    AUTHOR				NOTICE
 * ======================================================
 * 2022-05-24			jhchoi				최초 생성
 */
@Configuration
@RequiredArgsConstructor
public class TaskConfig {
	private final JobBuilderFactory jobBuilderFactory; // job 빌더 생성용
	private final StepBuilderFactory stepBuilderFactory; // step 빌더 생성용


	// JobBuilderFactory를 통해 CrollingJob을 생성
	@Bean
	public Job crollingJob() {
		return jobBuilderFactory.get("crollingJob")
			.start(crollingStep()) // Step 설정
			.build();
	}

	@Bean
	public Step crollingStep() {
		return stepBuilderFactory.get("crollingStep")
			.tasklet(new CrollingTasklet(new Telegram())) // Tasklet 설정
			.build();
	}
}
