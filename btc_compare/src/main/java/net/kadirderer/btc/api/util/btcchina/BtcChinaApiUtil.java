package net.kadirderer.btc.api.util.btcchina;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;

import net.kadirderer.btc.api.ApiAccessKeyType;
import net.kadirderer.btc.db.dao.PlatformAPIDao;
import net.kadirderer.btc.db.dao.UserAccessKeyDao;
import net.kadirderer.btc.db.model.PlatformAPI;
import net.kadirderer.btc.db.model.UserAccessKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BtcChinaApiUtil {
	
	private static UserAccessKeyDao uakDao;
	private static PlatformAPIDao paDao;
	
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	private static Logger logger = LoggerFactory.getLogger(BtcChinaApiUtil.class);
	
	/**
	 * 
	 * @param api
	 * @param parameters
	 * @return
	 */
	public static String callApi(BtcChinaApiCallable api, String username, String... parameters) throws Exception {
		PlatformAPI queryorderApi = paDao.findByApiType("BTCCHINA", api.getApiType());
		List<UserAccessKey> keyList = uakDao.findByUserName(username);
		
		String accessKey = BtcChinaApiUtil.getKeyValueFromList(ApiAccessKeyType.BTC_CHINA_ACCESS_KEY.getCode(), keyList);
		String secretKey = BtcChinaApiUtil.getKeyValueFromList(ApiAccessKeyType.BTC_CHINA_SECRET_KEY.getCode(), keyList);
		
		String tonce = String.valueOf((System.currentTimeMillis() * 1000));
		String params = "tonce=" + tonce.toString()
				+ "&accesskey=" + accessKey
				+ "&requestmethod=post&id=1"
				+ "&method=" + api.getApiName()
				+ "&params=" + api.getParameters(parameters);
		
		String hash = BtcChinaApiUtil.getSignature(params, secretKey);

		String url = queryorderApi.getUrl();
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		String userpass = accessKey + ":" + hash;
		String basicAuth = "Basic "
				+ DatatypeConverter.printBase64Binary(userpass.getBytes());

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		con.setRequestProperty("Authorization", basicAuth);

		String postdata = "{\"method\": \"" + api.getApiName() + "\", "
				+ "\"params\": [" + api.getJSONRequestParameters(parameters) + "], "
				+ "\"id\": 1}";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postdata);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		
		logger.info("BTC CHINA API CALL: ["  + api.getApiName() + "]["
				+ postdata +"][" + responseCode + "]");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
	}
	
	private static String getKeyValueFromList(char keyType, List<UserAccessKey> keyList) {
		
		for(UserAccessKey uak : keyList) {
			if(uak.getKeyType() == keyType) {
				return uak.getKeyValue();
			}
		}
		
		return null;
	}
	
	private static String getSignature(String data, String key) throws Exception {

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
	
	private static String bytArrayToHex(byte[] a) {
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
