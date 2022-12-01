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

	/**
	 * HttpAsyncClient 连接保活时间 服务器默认的TCP保活时间为 net.ipv4.tcp_keepalive_time = 7200 2小时
	 * 如果服务器关闭了连接 这时候客户端还持有连接 没有提前关闭 这时候访问就会报 Connection reset by peer
	 * 所以这个时间要小于服务端的时间
	 */
	private Long keepAliveTime = 7199L;
}
