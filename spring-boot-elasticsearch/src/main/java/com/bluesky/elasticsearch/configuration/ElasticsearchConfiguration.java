/*
 * Copyright (c) 2022, Zero. All rights reserved.
 */

package com.bluesky.elasticsearch.configuration;


import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author blue Sky
 * @version v1.0
 */
@Configuration
@AllArgsConstructor
public class ElasticsearchConfiguration {

	private final ElasticsearchCustomProperties elasticsearchCustomProperties;

	@Bean
	RestClientBuilderCustomizer kongRestClientBuilderCustomizer() {
		return new ElasticsearchRestClientBuilderCustomizer(elasticsearchCustomProperties);
	}

}
