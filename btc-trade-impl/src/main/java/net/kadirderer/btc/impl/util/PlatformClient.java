package net.kadirderer.btc.impl.util;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PlatformClient {
	
	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
	
	private RestTemplate restTemplate;
	private String endPointUrl;
	
	private ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public ClientHttpRequestFactory httpRequestFactory() {
		return new HttpComponentsClientHttpRequestFactory(httpClient());
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
		List<HttpMessageConverter<?>> converters = restTemplate
				.getMessageConverters();

		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
				jsonConverter.setObjectMapper(objectMapper());
			}
		}

		return restTemplate;
	}

	@Bean
	public HttpClient httpClient() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
		connectionManager
				.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
				
		HttpClient defaultHttpClient = HttpClientBuilder.create()
				.setConnectionManager(connectionManager).build();
		
		return defaultHttpClient;
	}	
	
	public PlatformClient(String endPointUrl) {
		this.restTemplate = restTemplate();
		this.endPointUrl = endPointUrl;		
	}
	
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
    
    public String createUrl(String uri) {
    	
    	if (uri == null || uri.length() == 0) {
    		return endPointUrl;
    	}
    	
        StringBuilder sb = new StringBuilder();
        sb.append(endPointUrl);
        
        if (endPointUrl.charAt(endPointUrl.length() - 1) == '/') {
        	if (uri.charAt(0) == '/') {
        		sb.append(uri.substring(1));
        	} else {
        		sb.append(uri);
        	}
        } else {
        	if (uri.charAt(0) == '/') {
        		sb.append(uri);
        	} else {
        		sb.append('/');
        		sb.append(uri);
        	}
        }
        
        return sb.toString();
    }

}
