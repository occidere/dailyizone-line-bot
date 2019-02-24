package org.occidere.dailyizonelinebot.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author occidere
 * @since 2019-02-24
 * Blog: https://blog.naver.com/occidere
 * Github: https://github.com/occidere
 */
@Data
@Document(collection = "line")
public class LineBotInfo {
	private static final long serialVersionUID = 1L;

	@Id
	@Field(value = "channelId")
	private String channelId;

	private String name;
	private String token;
	private String secret;
	private String botId;
}
