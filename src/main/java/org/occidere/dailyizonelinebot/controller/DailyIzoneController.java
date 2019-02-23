package org.occidere.dailyizonelinebot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.occidere.dailyizonelinebot.dto.InstagramPhoto;
import org.occidere.dailyizonelinebot.service.InstagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author occidere
 * @since 2019-02-23
 * Blog: https://blog.naver.com/occidere
 * Github: https://github.com/occidere
 */
@Slf4j
@RestController
@LineMessageHandler
public class DailyIzoneController {
	@Autowired
	private LineMessagingClient lineMessagingClient;

	@Value("${line.bot.id}")
	private String dailyIzoneId;

	@Autowired
	private InstagramService instagramService;

	@GetMapping(value = "/health")
	public String healtCheck() {
		return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
	}


	@RequestMapping(value = "/notify/line/izone/image", method = RequestMethod.GET)
	public String pushImageEvent(@RequestParam(value = "range", defaultValue = "1") int range,
								 @RequestParam(value = "author") String author) throws Exception {
		Map<String, Object> response = new HashMap<>();

		LocalDateTime now = LocalDateTime.now();
		LocalDate today = now.toLocalDate();
		response.put("today", today.toString());
		log.info("Today: " + today);

		String from = today.minusDays(range).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "000000";
		response.put("from", from);
		String to = today.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "235959";
		response.put("to", to);
		log.info("Range: {} ~ {}", from, to);

		List<InstagramPhoto> photos = instagramService.findImageByAuthorAndDateRange(author, from, to);
		log.info("photos: {}", photos);

		if (CollectionUtils.isEmpty(photos)) {
			String msg = String.format("[%s]\n새로운 사진 없음", now.format(DateTimeFormatter.ISO_DATE_TIME));
			response.put("message", msg);
			pushText(msg);
		} else {
			for (InstagramPhoto photo : photos) {
				// Response 응답 용 객체
				Map<String, Object> logMap = new HashMap<String, Object>() {{
					put("author", photo.getAuthor());
					put("date", photo.getDate());
					put("pageUrl", photo.getPageUrl());
					put("content", photo.getContent());
					put("originImageUrls", photo.getOriginImageUrls());
				}};
				log.info("Photo: {}", photo);
				response.put(photo.getDate(), logMap);

				photo.getOriginImageUrls().forEach(this::pushImage); // 전송
			}
		}

		String jsonResp = new ObjectMapper().writeValueAsString(response);
		log.info("Response: " + jsonResp);

		return jsonResp;
	}

	private void pushImage(String url) {
		Message message = new ImageMessage(url, url);
		PushMessage pushMessage = new PushMessage(dailyIzoneId, message);

		try {
			BotApiResponse response = lineMessagingClient
					.pushMessage(pushMessage)
					.get();
			log.info("{}", response.toString());
		} catch (Exception e) {
			log.error("Push Image 실패!", e);
			e.printStackTrace();
		}
	}

	private void pushText(String text) {
		Message message = new TextMessage(text);
		PushMessage pushMessage = new PushMessage(dailyIzoneId, message);

		try {
			BotApiResponse response = lineMessagingClient
					.pushMessage(pushMessage)
					.get();

			log.info("{}", response.toString());
		} catch (Exception e) {
			log.error("Push Text 실패!", e);
			e.printStackTrace();
		}
	}
}