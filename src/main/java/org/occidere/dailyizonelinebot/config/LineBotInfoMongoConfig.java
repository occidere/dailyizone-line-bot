package org.occidere.dailyizonelinebot.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author occidere
 * @since 2019-02-24
 * Blog: https://blog.naver.com/occidere
 * Github: https://github.com/occidere
 */
@Configuration
@EnableMongoRepositories(
		basePackages = "org.occidere.dailyizonelinebot.repository.linebotinfo",
		mongoTemplateRef = "lineBotInfoMongoTemplate")
public class LineBotInfoMongoConfig {
	@Bean(name = "lineBotInfoMongoTemplate")
	public MongoTemplate mongoTemplate(@Qualifier("lineBotInfoMongoFactory") MongoDbFactory mongoFactory) {
		return new MongoTemplate(mongoFactory);
	}

	@Bean(name = "lineBotInfoMongoFactory")
	public MongoDbFactory mongoFactory(@Qualifier("lineBotInfoMongoProperties") MongoProperties props) {
		return new SimpleMongoDbFactory(new MongoClientURI(props.getUri()));
	}

	@Bean(name = "lineBotInfoMongoProperties")
	@ConfigurationProperties(prefix = "mongodb.linebotinfo")
	public MongoProperties properties() {
		return new MongoProperties();
	}

}
