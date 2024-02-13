package com.example.backgroundnews.service.crolling;

import com.example.backgroundnews.domain.News;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java
	.util.List;

/**
 * PackageName 	: PACKAGE_NAME
 * FileName 	: CrollingBot
 * Author 		: jhchoi
 * Date 		: 2022-05-24
 * Description 	:
 * ======================================================
 * DATE				    AUTHOR				NOTICE
 * ======================================================
 * 2022-05-24			jhchoi				최초 생성
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Component
public class LandCrollingBot {
	// Daum 부동산 뉴스 크롤링 봇
	private static Long lastLandNewsId = 0L;
	private String landOrigin = "https://realestate.daum.net/news/all";

	public List<News> getLandNewsList() {
		List<News> landNewsList = new ArrayList<>();


		try {
			Document doc = Jsoup.connect(landOrigin).get();
			Elements elements = doc.select("div.cont");

			for (int i = elements.size() - 1; i >= 1; i--) {
				String url = "https:" + elements.get(i).childNode(3).childNode(1).attr("href");
				String title = "*%5B%5B%EB%8B%A4%EC%9D%8C%EB%B6%80%EB%8F%99%EC%82%B0%5D%5D " + elements.get(i).childNode(1).childNode(1).unwrap().toString() + "*%0A";
				Long newsId = Long.valueOf(url.substring(url.lastIndexOf("/") + 1));
				if (lastLandNewsId < newsId) {
					News landNews = new News(url, title, newsId);
					landNewsList.add(landNews);
					lastLandNewsId = newsId;
					System.out.println("[[다음부동산]] : " + newsId);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return landNewsList;
	}

	public List<News> getTopNewsList() {
		List<News> topNewsList = new ArrayList<>();
		try {
			Document doc = Jsoup.connect(landOrigin).get();
			Elements elements = doc.select(".section_majornews").select(".tit");

			for (int i = elements.size() - 1; i >= 1; i--) {
				String url = "https:" + elements.get(i).childNode(1).attr("href");

				String title = elements.get(i).childNode(1).unwrap().toString();
				Long newsId = Long.valueOf(url.substring(url.lastIndexOf("/") + 1));
				News landNews = new News(url, title, newsId);
				topNewsList.add(landNews);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return topNewsList;
	}
}
