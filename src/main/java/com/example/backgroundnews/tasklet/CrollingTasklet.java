package com.example.backgroundnews.tasklet;

import com.example.backgroundnews.service.Telegram;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * PackageName 	: batch
 * FileName 	: CrollingTasklet
 * Author 		: jhchoi
 * Date 		: 2022-05-24
 * Description 	:
 * ======================================================
 * DATE				    AUTHOR				NOTICE
 * ======================================================
 * 2022-05-24			jhchoi				최초 생성
 */
@Slf4j
@RequiredArgsConstructor
public class CrollingTasklet implements Tasklet {
	//
	private final Telegram telegram;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("executed tasklet");
		telegram.funcTelegram();
		return RepeatStatus.FINISHED;
	}
}


