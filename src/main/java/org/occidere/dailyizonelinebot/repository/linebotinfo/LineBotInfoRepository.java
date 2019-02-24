package org.occidere.dailyizonelinebot.repository.linebotinfo;

import org.occidere.dailyizonelinebot.dto.LineBotInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author occidere
 * @since 2019-02-24
 * Blog: https://blog.naver.com/occidere
 * Github: https://github.com/occidere
 */
public interface LineBotInfoRepository extends MongoRepository<LineBotInfo, String> {
	@Query("{'name': ?0}")
	LineBotInfo findLineBotInfoByName(String name);
}
