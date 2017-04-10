package me.securechat4.client;

import java.util.List;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpsApi {
	
	public static JSONArray get(String route, List<NameValuePair> params) {
		JSONArray result = null;
		
		try {
			SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, (certificate, authType) -> true)
					.build();
			
			CloseableHttpClient client = HttpClients.custom()
					.setSSLContext(sslContext)
					.setSSLHostnameVerifier(new NoopHostnameVerifier())
					.build();
			
			HttpGet httpGet = new HttpGet(AppConfig.URL + route + "/" /*+ URLEncodedUtils.format(params, "utf-8")*/);
			
			httpGet.setHeader(HttpHeaders.AUTHORIZATION, App.getJWT());
			System.out.println(App.getJWT());
			
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Status Code: " + response.getStatusLine().getStatusCode());
			String content = EntityUtils.toString(response.getEntity());
			
			System.out.println("Content: " + content);
			
			if (!content.isEmpty()) {
				JSONParser parser = new JSONParser();			
				result = (JSONArray) parser.parse(content);
			}

			
			
			client.close();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static JSONObject post(String route, JSONObject json) {
		JSONObject result = null;
		
		try {
			SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, (certificate, authType)-> true)
					.build();
			
			CloseableHttpClient client = HttpClients.custom()
					.setSSLContext(sslContext)
					.setSSLHostnameVerifier(new NoopHostnameVerifier())
					.build();
			
			HttpPost httpPost = new HttpPost(AppConfig.URL + route);
			System.out.println("JSON Out: " + json.toJSONString());
			StringEntity entity = new StringEntity(json.toJSONString());
			
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			
			CloseableHttpResponse response = client.execute(httpPost);
			System.out.println("Status Code: " + response.getStatusLine().getStatusCode());
			String content = EntityUtils.toString(response.getEntity());
			
			if (!content.isEmpty()) {
				JSONParser parser = new JSONParser();			
				result = (JSONObject) parser.parse(content);
			}

			System.out.println("Content: " + content);
			
			client.close();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
