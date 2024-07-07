package com.invento.product.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

@Configuration
@EnableMongoAuditing
public class MongoClientConfig extends AbstractMongoClientConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(MongoClientConfig.class);
	
	@Value("${spring.data.mongodb.uri}")
	private String connectionString;
	
	@Value("${spring.data.mongodb.database}")
	private String database;
	
	@Override
	protected String getDatabaseName() {
		return database;
	}
	
	@Bean
	public MongoClientSettings mongoClientSettings() {
		log.info("Creating MongoClientSettings for mongoClient and MongoTemplate");
		return MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).build();
	}
}
