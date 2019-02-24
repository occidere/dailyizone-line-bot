package org.occidere.dailyizonelinebot.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
		basePackages = "org.occidere.dailyizonelinebot.repository.instagram",
		mongoTemplateRef = "instagramMongoTemplate")
public class InstagramMongoConfig {
	@Primary
	@Bean(name = "instagramMongoTemplate")
	public MongoTemplate mongoTemplate(@Qualifier("instagramMongoFactory") MongoDbFactory mongoFactory) {
		return new MongoTemplate(mongoFactory);
	}

	@Primary
	@Bean(name = "instagramMongoFactory")
	public MongoDbFactory mongoFactory(@Qualifier("instagramMongoProperties") MongoProperties props) {
		return new SimpleMongoDbFactory(new MongoClientURI(props.getUri()));
	}

	@Primary
	@Bean(name = "instagramMongoProperties")
	@ConfigurationProperties(prefix = "mongodb.instagram")
	public MongoProperties properties() {
		return new MongoProperties();
	}

}
