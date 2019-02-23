package org.occidere.dailyizonelinebot.service;

import org.occidere.dailyizonelinebot.dto.InstagramPhoto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author occidere
 * @since 2019-02-23
 * Blog: https://blog.naver.com/occidere
 * Github: https://github.com/occidere
 */
public interface InstagramService extends MongoRepository<InstagramPhoto, String> {
	@Query("{'author': ?0, 'date': {$gte: ?1, $lte: ?2}}")
	List<InstagramPhoto> findImageByAuthorAndDateRange(String author, String startTime, String endTime);
}
