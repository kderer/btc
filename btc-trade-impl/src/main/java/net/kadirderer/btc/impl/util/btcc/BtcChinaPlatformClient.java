package net.kadirderer.btc.impl.util.btcc;

import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import net.kadirderer.btc.db.dao.PlatformAPIDao;
import net.kadirderer.btc.db.dao.UserAccessKeyDao;
import net.kadirderer.btc.db.model.PlatformAPI;
import net.kadirderer.btc.db.model.UserAccessKey;
import net.kadirderer.btc.impl.util.PlatformClient;
import net.kadirderer.btc.util.enumaration.ApiAccessKeyType;

@Component
public class BtcChinaPlatformClient {
	
	@Autowired
	private UserAccessKeyDao uakDao;
	
	@Autowired
	private PlatformAPIDao paDao;
	
	@Autowired
	private PlatformClient platformClient;
	
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	private static Logger logger = LoggerFactory.getLogger(BtcChinaPlatformClient.class);
	
	/**
	 * 
	 * @param api
	 * @param parameters
	 * @return
	 */
	public String callApi(BtcChinaApiCallable api, String username, String... parameters) throws Exception {
		PlatformAPI queryorderApi = paDao.findByApiType("BTCCHINA", api.getApiType());
		List<UserAccessKey> keyList = uakDao.findByUserName(username);
		
		String accessKey = getKeyValueFromList(ApiAccessKeyType.BTC_CHINA_ACCESS_KEY.getCode(), keyList);
		String secretKey = getKeyValueFromList(ApiAccessKeyType.BTC_CHINA_SECRET_KEY.getCode(), keyList);
		
		String tonce = String.valueOf((System.currentTimeMillis() * 1000));
		
		StringBuilder params = new StringBuilder();
		params.append("tonce=").append(tonce);
		params.append("&accesskey=").append(accessKey);
		params.append("&requestmethod=post&id=1");
		params.append("&method=").append(api.getApiName());
		params.append("&params=").append(api.getParameters(parameters));
		
		String hash = getSignature(params.toString(), secretKey);
		String url = queryorderApi.getUrl();		
		String userpass = accessKey + ":" + hash;
		String basicAuth = "Basic " + DatatypeConverter.printBase64Binary(userpass.getBytes());
		
		String postdata = "{\"method\": \"" + api.getApiName() + "\", "
				+ "\"params\": [" + api.getJSONRequestParameters(parameters) + "], "
				+ "\"id\": 1}";
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Json-Rpc-Tonce", tonce.toString());
		requestHeaders.add("Authorization", basicAuth);
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				postdata, requestHeaders);	
		
		ResponseEntity<String> response = platformClient.getRestTemplate().exchange(url,
				HttpMethod.POST, requestEntity, String.class);
		
		logger.info("BTC CHINA API CALL: ["  + api.getApiName() + "]["
				+ postdata +"][" + response.getStatusCode() + "]");
		
		return response.getBody();
	}
	
	private String getKeyValueFromList(char keyType, List<UserAccessKey> keyList) {
		
		for(UserAccessKey uak : keyList) {
			if(uak.getKeyType() == keyType) {
				return uak.getKeyValue();
			}
		}
		
		return null;
	}
	
	private String getSignature(String data, String key) throws Exception {

		// get an hmac_sha1 key from the raw key bytes
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
				HMAC_SHA1_ALGORITHM);

		// get an hmac_sha1 Mac instance and initialize with the signing key
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);

		// compute the hmac on input data bytes
		byte[] rawHmac = mac.doFinal(data.getBytes());

		return bytArrayToHex(rawHmac);
	}
	
	private String bytArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder();
		for (byte b : a)
			sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}
	
	@Autowired
	public void setUserAccessKeyDao(UserAccessKeyDao puakDao) {
		uakDao = puakDao;
	}
	
	@Autowired
	public void setUserAccessKeyDao(PlatformAPIDao ppaDao) {
		paDao = ppaDao;
	}

}
