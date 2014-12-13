package com.pictureshare.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class InternetConn {

	/**
	 * 根据指定的urlStr（连接）获取索要的数据,以什么编码请求
	 */
	public static String getUrlResult(String urlStr, String chart) {
		String result = "";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(10000);
			InputStreamReader isr = new InputStreamReader(
					connection.getInputStream(), chart);
			BufferedReader br = new BufferedReader(isr);
			String tempResult = null;
			while ((tempResult = br.readLine()) != null) {
				result += tempResult;
			}
			isr.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "-1";
		}
		return result;
	}

	public static String pushURL(String str) {
		String returnCode = "-1";
		if (str.equals(""))
			return "-1";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(str);
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream is = response.getEntity().getContent();
				returnCode = isStreamString(is);
			}else{
				returnCode = "-1";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			returnCode = "-1";
		} catch (IOException e) {
			e.printStackTrace();
			returnCode = "-1";
		} catch (Exception e) {
			e.printStackTrace();
			returnCode = "-1";
		}
		return returnCode;
	}

	/**
	 * 从输入流中读取信息
	 * @param is=输入流
	 * @return 返回网页信息
	 * @throws Exception
	 */
	private static String isStreamString(InputStream is) throws Exception {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = is.read(buf)) != -1) {
			bo.write(buf, 0, len);
		}
		return new String(bo.toByteArray());
	}

}
