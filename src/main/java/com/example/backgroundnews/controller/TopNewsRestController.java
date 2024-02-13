package com.example.backgroundnews.controller;

import com.example.backgroundnews.domain.News;
import com.example.backgroundnews.service.crolling.LandCrollingBot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PackageName 	: com.example.backgroundnews.controller
 * FileName 	: TopNewsRestController
 * Author 		: jhchoi
 * Date 		: 2023-08-22
 * Description 	:
 * ======================================================
 * DATE				    AUTHOR				NOTICE
 * ======================================================
 * 2023-08-22			jhchoi				최초 생성
 */
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class TopNewsRestController {
	private final LandCrollingBot landCrollingBot;

	@GetMapping("/top")
	public ResponseEntity<List<News>> topNewsList() {
		return ResponseEntity.ok().body(landCrollingBot.getTopNewsList());
	}
}
