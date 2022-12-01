/*
 * Copyright (c) 2022, wangguodong194@163.com. All rights reserved.
 */

package  com.bluesky.elasticsearch.configuration;;

import cn.hutool.core.io.resource.ResourceUtil;
import com.bluesky.elasticsearch.configuration.ElasticsearchCustomProperties;
import lombok.AllArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HttpContext;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Optional;

/**
 * RestClientBuilder 的回调接口，
 * 会在保留默认自动配置的情况下，增加自定义的内容
 *
 * @author blue Sky
 * @version V1.0
 * @description
 * @date 2022-11-24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@AllArgsConstructor
public class ElasticsearchRestClientBuilderCustomizer implements RestClientBuilderCustomizer {

	private final ElasticsearchCustomProperties elasticsearchCustomProperties;


	@Override
	public void customize(RestClientBuilder builder) {

	}

	/**
	 * 在原有HttpAsyncClientBuilder
	 * 基础上增加SSL的自定义配置
	 *
	 * @param builder the builder
	 * @since 2.3.0
	 */
	@Override
	public void customize(HttpAsyncClientBuilder builder)  {


		builder.setDefaultIOReactorConfig(IOReactorConfig.custom()
				// 保持连接检测对方主机是否崩溃，避免（服务器）永远阻塞于TCP连接的输入 需要net.ipv4.tcp_keepalive_time配合
				.setSoKeepAlive(true).build());

		if (!elasticsearchCustomProperties.isUseSsl()) {
			return;
		}

		final SSLContext sslContext;
		// 存在自定义证书使用自定义模式，不存在使用默认
		if (StringUtils.hasText(elasticsearchCustomProperties.getKeyStorePath())) {
			// 信任的证书
			TrustManager[] tm = getTrustManagers();
			try {
				// 创建SSL
				sslContext = SSLContext.getInstance("TLSv1.2");
				sslContext.init(null, tm, null);
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				// 使用默认的
				sslContext = SSLContext.getDefault();
				sslContext.init(null, null, null);
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}
		builder.setSSLContext(sslContext);

	}


	@Override
	public void customize(RequestConfig.Builder builder) {
		RestClientBuilderCustomizer.super.customize(builder);
	}

	private TrustManager[] getTrustManagers() {
		TrustManager[] tm;
		try (InputStream stream = ResourceUtil.getStream(elasticsearchCustomProperties.getKeyStorePath())) {
			// 获取证书对象
			KeyStore keyStore = KeyStore.getInstance("JKS");
			// 加载证书
			keyStore.load(stream, Optional.ofNullable(elasticsearchCustomProperties.getKeyStorePassword()).orElse("").toCharArray());
			// 自定义证书管理器
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			// 初始化自定义证书
			tmf.init(keyStore);
			tm = tmf.getTrustManagers();
		} catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
			throw new RuntimeException(e);
		}
		return tm;
	}

}
