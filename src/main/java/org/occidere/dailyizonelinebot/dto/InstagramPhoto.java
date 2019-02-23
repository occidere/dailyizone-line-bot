package org.occidere.dailyizonelinebot.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author occidere
 * @since 2019-02-23
 * Blog: https://blog.naver.com/occidere
 * Github: https://github.com/occidere
 */
@Data
@Document(collection = "instagram")
public class InstagramPhoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String pageUrl;

	private String date; // yyyyMMddHHmmss
	private String author;
	private String content;
	private List<String> originImageUrls;
	private List<String> savedImageUrls;

}
