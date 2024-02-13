package com.example.backgroundnews.service;

import com.example.backgroundnews.domain.News;
import com.example.backgroundnews.service.crolling.EcoCrollingBot;
import com.example.backgroundnews.service.crolling.LandCrollingBot;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

/**
 * PackageName 	: com.company
 * FileName 	: telegram
 * Author 		: jhchoi
 * Date 		: 2022-05-24
 * Description 	:
 * ======================================================
 * DATE				    AUTHOR				NOTICE
 * ======================================================
 * 2022-05-24			jhchoi				최초 생성
 */
@NoArgsConstructor
public class Telegram {
	// Crolling Bot List
	public static LandCrollingBot landCrollingBot = new LandCrollingBot();
	public static EcoCrollingBot ecoCrollingBot = new EcoCrollingBot();
	public static final String ECO_TOKEN = "5356708613:AAEM1dnmDNsQl_0qATWbeBPXMI-9UFmZ7AU";
	public static final String LAND_TOKEN = "6221913525:AAGktnB0Jq6avhm90plK_66EtjouKOY5Tx8";
	public static final String ECO_CHAT_ID = "-1001756337436";
	public static final String LAND_CHAT_ID = "1086324779";
	// Daum 경제, 사회, IT 뉴스 크롤링 봇 개발 예정

	// kafka service

	public void funcTelegram() {
		// 부동산 뉴스 묶음 전송
		String landArticle = "";
		List<News> landNewsList = landCrollingBot.getLandNewsList();
		if (landNewsList.size() > 0) {
			for (int i = 0; i < landNewsList.size(); i++) {
				landArticle += landNewsList.get(i).getTitle() + landNewsList.get(i).getUrl() + "%0A%0A";
			}
			sendMessage(landArticle, LAND_TOKEN, LAND_CHAT_ID);
		}
		
		// 부동산+경제 뉴스 묶음 전송

		List<News> ecoNewsList = landNewsList;
		ecoNewsList.addAll(ecoCrollingBot.getEcoNewsList());
		String article = "";
		for (int i = 0; i < ecoNewsList.size(); i++) {
			article += ecoNewsList.get(i).getTitle() + ecoNewsList.get(i).getUrl() + "%0A%0A";
			if (i != 0 && i % 10 == 0) {
				sendMessage(article, ECO_TOKEN, ECO_CHAT_ID);
				article = "";
			} else if (i == ecoNewsList.size() - 1) {
				sendMessage(article, ECO_TOKEN, ECO_CHAT_ID);
				article = "";
			}
		}
	}

	private void sendMessage(String article, String TOKEN, String CHAT_ID) {
		BufferedReader in = null;
		if (article != "") {
			try {
				String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
				URL obj = new URL("https://api.telegram.org/bot" + TOKEN + "/sendmessage?chat_id=" + CHAT_ID + "&text= " + dateTime + "%0A%0A" + article + "&parse_mode=markdown");
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
				String line;

				while ((line = in.readLine()) != null) {
					System.out.println(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
