package com.example.backgroundnews.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PackageName 	: domain
 * FileName 	: LandNews
 * Author 		: jhchoi
 * Date 		: 2022-05-24
 * Description 	:
 * ======================================================
 * DATE				    AUTHOR				NOTICE
 * ======================================================
 * 2022-05-24			jhchoi				최초 생성
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class News {
	private String url;
	private String title;
	private Long newsId;
}
