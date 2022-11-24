package com.bluesky.elasticsearch;

import com.bluesky.elasticsearch.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
class SpringBootElasticsearchApplicationHttpTests {

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	@Test
	void contextLoads() {
		Student response = elasticsearchOperations.save(new Student("测试","测试学校"), IndexCoordinates.of("test"));
		System.out.println(response);
	}

}
