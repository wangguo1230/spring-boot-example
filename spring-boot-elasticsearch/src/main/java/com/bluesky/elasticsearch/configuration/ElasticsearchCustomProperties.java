/*
 * Copyright (c) 2022, wangguodong194@163.com. All rights reserved.
 */

package com.bluesky.elasticsearch.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author blue Sky
 * @version V1.0
 * @description
 * @date 2022-11-23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.elasticsearch.custom")
public class ElasticsearchCustomProperties {

	/**
	 * 是否https
	 */
	private boolean useSsl;

	/**
	 * 证书路径
	 */
	private String keyStorePath;

	/**
	 * 证书密码
	 */
	private String keyStorePassword;
}
