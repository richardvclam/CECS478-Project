package me.securechat4.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpsApi {
	
	/**
	 * HTTP GET request from the server. Returns a JSON object from the server.
	 * @param route is the route to request the server from
	 * @return a JSON object
	 */
	public static Object get(String route) {
		Object result = null;
		
		try {
			SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, (certificate, authType) -> true)
					.build();
			
			CloseableHttpClient client = HttpClients.custom()
					.setSSLContext(sslContext)
					.setSSLHostnameVerifier(new NoopHostnameVerifier())
					.build();
			
			HttpGet httpGet = new HttpGet(Constants.URL + route);
			
			httpGet.setHeader(HttpHeaders.AUTHORIZATION, App.getJWT());
			//System.out.println(App.getJWT());
			
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Status Code: " + response.getStatusLine().getStatusCode());
			String content = EntityUtils.toString(response.getEntity());
			
			System.out.println("Content: " + content);
			
			if (!content.isEmpty()) {
				JSONParser parser = new JSONParser();			
				result = parser.parse(content);
			}

			client.close();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * HTTP POST request to the server. Returns a JSON object from the server.
	 * @param route the route to post the request to
	 * @param json the JSON object with the data parameters to post to
	 * @param authenticate whether to send the JWT as a header for authentication by the server
	 * @return a JSON object response from server
	 */
	public static JSONObject post(String route, JSONObject json, boolean authenticate) {
		JSONObject result = null;
		
		try {
			SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, (certificate, authType)-> true)
					.build();
			
			CloseableHttpClient client = HttpClients.custom()
					.setSSLContext(sslContext)
					.setSSLHostnameVerifier(new NoopHostnameVerifier())
					.build();
			
			HttpPost httpPost = new HttpPost(Constants.URL + route);
			System.out.println("JSON Out: " + json.toJSONString());
			StringEntity entity = new StringEntity(json.toJSONString());
			
			httpPost.setEntity(entity);
			if (authenticate) {
				httpPost.setHeader(HttpHeaders.AUTHORIZATION, App.getJWT());
			}
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
