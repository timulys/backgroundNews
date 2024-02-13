package com.example.backgroundnews.service.crolling;

import com.example.backgroundnews.domain.News;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * PackageName 	: com.example.backgroundnews.service.crolling
 * FileName 	: EcoCrollingBot
 * Author 		: jhchoi
 * Date 		: 2022-07-05
 * Description 	:
 * ======================================================
 * DATE				    AUTHOR				NOTICE
 * ======================================================
 * 2022-07-05			jhchoi				최초 생성
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class EcoCrollingBot {
	// Daum 경제 전체 기사 크롤링 봇
	private static Long lastEcoNewsId = 0L;
	private String ecoOrigin = "https://news.daum.net/breakingnews/economic";

	public List<News> getEcoNewsList() {
		List<News> ecoNewsList = new ArrayList<>();

		try {
			Document doc = Jsoup.connect(ecoOrigin).get();
			Elements elements = doc.select("div.cont_thumb");

			for (int i = elements.size() - 1; i >= 1; i--) {
				String url = "";
				String title = "";
				if (elements.get(i).childNode(1).childNodes().size() < 2) {
					url = elements.get(i).childNode(3).childNode(0).attr("href");
					title += "*%5B%5B%EB%8B%A4%EC%9D%8C%EA%B2%BD%EC%A0%9C%5D%5D " + elements.get(i).childNode(3).childNode(0).unwrap().toString() + "*%0A";
				} else {
					url = elements.get(i).childNode(1).childNode(1).attr("href");
					title += "*%5B%5B%EB%8B%A4%EC%9D%8C%EA%B2%BD%EC%A0%9C%5D%5D " + elements.get(i).childNode(1).childNode(1).unwrap().toString() + "*%0A";
				}
				Long newsId = Long.valueOf(url.substring(url.lastIndexOf("/") + 1));
				if (lastEcoNewsId < newsId) {
					News ecoNews = new News(url, title, newsId);
					ecoNewsList.add(ecoNews);
					lastEcoNewsId = newsId;
					System.out.println("[[다음경제]] : " + newsId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ecoNewsList;
	}
}
