package com.example.backgroundnews.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * PackageName 	: batch
 * FileName 	: CrollingScheduler
 * Author 		: jhchoi
 * Date 		: 2022-05-24
 * Description 	:
 * ======================================================
 * DATE				    AUTHOR				NOTICE
 * ======================================================
 * 2022-05-24			jhchoi				최초 생성
 */
@Component
@RequiredArgsConstructor
public class CrollingScheduler {
	//
	private final Job job; // crolling job
	private final JobLauncher jobLauncher;

	// 3분마다 실행
	@Scheduled(fixedDelay = 60 * 3000L)
	public void executeJob() {
		try {
			jobLauncher.run(job, new JobParametersBuilder().addString("datetime", LocalDateTime.now().toString()).toJobParameters());
		} catch (JobExecutionException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
