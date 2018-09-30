package com.rd.channel.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpClientUtil {
	private static int connectionRequestTimeout = 80000;
	private static int connectTimeout = 80000;
	private static int socketTimeout = 80000;
	private static String charset = "UTF-8";
	/**
	 * 通过httpClint发送get请求
	 * @param url 请求url
	 * @param charset 编码
	 * @return
	 * @throws Exception 
	 */
	public static String  doGet(String url,String charset) throws Exception{
		HttpClientBuilder httpClientBuilder = null;
		CloseableHttpClient closeableHttpClient = null;
		String rtnStr = null;
        try{
        	httpClientBuilder = HttpClientBuilder.create();
    		//HttpClient  
            closeableHttpClient = httpClientBuilder.build();
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
            		.setConnectionRequestTimeout(connectionRequestTimeout)
            		.setConnectTimeout(connectTimeout)
            		.setSocketTimeout(socketTimeout)
            		.build();
            httpGet.setConfig(requestConfig);
        	//执行get请求  
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
            	String msg = "访问失败！！HTTP_STATUS=" + httpResponse.getStatusLine().getStatusCode();
				throw new HttpException(msg);
            }
            //获取响应消息实体  
            HttpEntity httpEntity = httpResponse.getEntity();  
            //判断响应实体是否为空  
            if (httpEntity != null) {  
            	rtnStr = EntityUtils.toString(httpEntity,charset);
            }  
        }finally{
        	if(closeableHttpClient != null){
    			closeableHttpClient.close();
    		}
        }
        return rtnStr;
	}
	
	/**
	 * 通过httpClint发送get请求
	 * @param url 请求url
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static String  doGet(String url) throws Exception{
		HttpClientBuilder httpClientBuilder = null;
		CloseableHttpClient closeableHttpClient = null;
		String rtnStr = null;
        try{
        	httpClientBuilder = HttpClientBuilder.create();
    		//HttpClient  
            closeableHttpClient = httpClientBuilder.build();
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
            		.setConnectionRequestTimeout(connectionRequestTimeout)
            		.setConnectTimeout(connectTimeout)
            		.setSocketTimeout(socketTimeout)
            		.build();
            httpGet.setConfig(requestConfig);
        	//执行get请求  
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
            	String msg = "访问失败！！HTTP_STATUS=" + httpResponse.getStatusLine().getStatusCode();
				throw new HttpException(msg);
            }
            //获取响应消息实体  
            HttpEntity httpEntity = httpResponse.getEntity();  
            //判断响应实体是否为空  
            if (httpEntity != null) {  
            	rtnStr = EntityUtils.toString(httpEntity,charset);
            }  
        }finally{
        	if(closeableHttpClient != null){
    			closeableHttpClient.close();
    		}
        }
        return rtnStr;
	}
	
	public static String doPost(List<NameValuePair> formparams,String url) throws Exception{
		HttpClientBuilder httpClientBuilder = null;
		CloseableHttpClient closeableHttpClient = null;
		UrlEncodedFormEntity entity = null;
		HttpResponse httpResponse = null;
		String rtnStr = null;
		try{
			//创建HttpClientBuilder  
			httpClientBuilder = HttpClientBuilder.create();  
			//HttpClient  
			closeableHttpClient = httpClientBuilder.build();
			RequestConfig requestConfig = RequestConfig.custom()
	        		.setConnectionRequestTimeout(connectionRequestTimeout)
	        		.setConnectTimeout(connectTimeout)
	        		.setSocketTimeout(socketTimeout)
	        		.build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			entity = new UrlEncodedFormEntity(formparams, charset);  
	        httpPost.setEntity(entity);
	        //post请求  
	        httpResponse = closeableHttpClient.execute(httpPost);
	        if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
            	String msg = "访问失败！！HTTP_STATUS=" + httpResponse.getStatusLine().getStatusCode();
				throw new HttpException(msg);
            }
	        HttpEntity httpEntity = httpResponse.getEntity();
	        if (httpEntity != null) {
	        	//打印响应内容  
	        	rtnStr = EntityUtils.toString(httpEntity, charset);
	        }
		}finally{
			if(closeableHttpClient != null){
    			closeableHttpClient.close();
    		}
		}
		return rtnStr;
	}
	/**
	 * 通过httpClient 发送post请求
	 * @param formparams
	 * @param url
	 * @param charset
	 * @throws IOException
	 */
	public static String doPost(List<NameValuePair> formparams,String url,String charset) throws Exception{
		HttpClientBuilder httpClientBuilder = null;
		CloseableHttpClient closeableHttpClient = null;
		UrlEncodedFormEntity entity = null;
		HttpResponse httpResponse = null;
		String rtnStr = null;
		HttpPost httpPost = null;
		try{
			//创建HttpClientBuilder  
			httpClientBuilder = HttpClientBuilder.create();  
			//HttpClient  
			closeableHttpClient = httpClientBuilder.build();
			RequestConfig requestConfig = RequestConfig.custom()
	        		.setConnectionRequestTimeout(connectionRequestTimeout)
	        		.setConnectTimeout(connectTimeout)
	        		.setSocketTimeout(socketTimeout)
	        		.build();
			httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			entity = new UrlEncodedFormEntity(formparams, charset);  
	        httpPost.setEntity(entity);
	        //post请求  
	        httpResponse = closeableHttpClient.execute(httpPost);
	        if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
            	String msg = "访问失败！！HTTP_STATUS=" + httpResponse.getStatusLine().getStatusCode();
				throw new HttpException(msg);
            }
	        HttpEntity httpEntity = httpResponse.getEntity();
	        if (httpEntity != null) {
	        	//打印响应内容  
	        	rtnStr = EntityUtils.toString(httpEntity, charset);
	        }
		}finally{
			if (httpPost != null) {
    			httpPost.releaseConnection();
			}
    		if(closeableHttpClient != null){
    			closeableHttpClient.close();
    		}
		}
		return rtnStr;
	}
	
	/**
	 * 通过httpClient发送post请求
	 * @param requestParams
	 * @param url
	 * @param charset
	 * @throws Exception 
	 */
	public static String doPost(Map<String,String> requestParams,String url,String charset) throws Exception{
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Iterator<String> it = requestParams.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			formparams.add(new BasicNameValuePair(key, requestParams.get(key)));
		}
		return doPost(formparams, url, charset);
	}
	
	public static String doPost(Map<String,String> requestParams,String url) throws Exception{
		return doPost(requestParams, url, charset);
	}
	
	
	public static String doPostWithNoRsp(Map<String,String> requestParams,String url,String charset) throws Exception{
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Iterator<String> it = requestParams.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			formparams.add(new BasicNameValuePair(key, requestParams.get(key)));
		}
		return doPostWithNoRsp(formparams, url, charset);
	}
	
	public static String doPostWithNoRsp(Map<String,String> requestParams,String url) throws Exception{
		return doPostWithNoRsp(requestParams, url, charset);
	}
	
	public static String doPostWithNoRsp(List<NameValuePair> formparams,String url,String charset) throws Exception{
		HttpClientBuilder httpClientBuilder = null;
		CloseableHttpClient closeableHttpClient = null;
		UrlEncodedFormEntity entity = null;
		String rtnStr = null;
		try{
			//创建HttpClientBuilder  
			httpClientBuilder = HttpClientBuilder.create();  
			//HttpClient  
			closeableHttpClient = httpClientBuilder.build();
			HttpPost httpPost = new HttpPost(url);
			entity = new UrlEncodedFormEntity(formparams, charset);  
	        httpPost.setEntity(entity);
	        //post请求  
	        closeableHttpClient.execute(httpPost);
		}finally{
			if(closeableHttpClient != null){
    			closeableHttpClient.close();
    		}
		}
		return rtnStr;
	}
	
	/**
	 * 使用json传输
	 * @param url 请求地址
	 * @param body 请求体
	 * @param charset 编码，默认编码是UTF-8
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, String body,String charset) throws Exception {
		CloseableHttpClient closeableHttpClient = null;
		HttpPost post = null;
		String rtnStr = null;
		CloseableHttpResponse httpResponse = null;
		try {
			if(StringUtils.isBlank(charset)){
				charset = HttpClientUtil.charset;
			}
			closeableHttpClient = HttpClientBuilder.create().build();
			post = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom()
	        		.setConnectionRequestTimeout(connectionRequestTimeout)
	        		.setConnectTimeout(connectTimeout)
	        		.setSocketTimeout(socketTimeout)
	        		.build();
			post.setConfig(requestConfig);
			HttpEntity entity = new StringEntity(body, charset);
			post.setHeader("Content-Type", "application/json");
			post.setEntity(entity);
			httpResponse = closeableHttpClient.execute(post);
			if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
	            String msg = "访问失败！！HTTP_STATUS=" + httpResponse.getStatusLine().getStatusCode();
				throw new HttpException(msg);
	        }else{
	        	HttpEntity httpEntity = httpResponse.getEntity();
			    if (httpEntity != null) {
			        //打印响应内容  
			        rtnStr = EntityUtils.toString(httpEntity, charset);
			     }
			    return rtnStr;
	        }
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}
			if (post != null) {
				post.releaseConnection();
			}
			if(closeableHttpClient != null){
				closeableHttpClient.close();
			}
		}
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String postFile(File file,String url,String charset){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String rtnStr = null;
		try{
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.setCharset(Charset.forName(charset));
			builder.addBinaryBody("file", file);
			builder.addTextBody("key", "post");//设置请求参数
			builder.addTextBody("appkey", "text/plain");//设置请求参数
			HttpEntity entity = builder.build();// 生成 HTTP POST 实体  	
			
			HttpPost httppost = new HttpPost(url); 
			httppost.setEntity(entity);//设置请求参数
			HttpResponse httpResponse = httpclient.execute(httppost);// 发起请求 并返回请求的响应
			if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
            	String msg = "访问失败！！HTTP_STATUS=" + httpResponse.getStatusLine().getStatusCode();
				throw new HttpException(msg);
            }
	        //getEntity()  
	        HttpEntity httpEntity = httpResponse.getEntity();
	        if (httpEntity != null) {
	        	//打印响应内容  
	        	rtnStr = EntityUtils.toString(httpEntity, charset);
	        }
		}catch(Exception e){
			e.printStackTrace();
		} finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
		return rtnStr;
	}
	
	/**
	 * 下载文件
	 * @param requestParams
	 * @param url
	 * @param charset
	 * @return
	 */
	public static InputStream getFile(Map<String,String> requestParams,String url,String charset){
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if(requestParams!=null && requestParams.size()>0){
			Iterator<String> it = requestParams.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				formparams.add(new BasicNameValuePair(key, requestParams.get(key)));
			}
		}
		InputStream is = null;
		HttpClientBuilder httpClientBuilder = null;
		CloseableHttpClient closeableHttpClient = null;
		UrlEncodedFormEntity entity = null;
		HttpResponse httpResponse = null;
		try{
			//创建HttpClientBuilder  
			httpClientBuilder = HttpClientBuilder.create();  
			//HttpClient  
			closeableHttpClient = httpClientBuilder.build();
			RequestConfig requestConfig = RequestConfig.custom()
	        		.setConnectionRequestTimeout(connectionRequestTimeout)
	        		.setConnectTimeout(connectTimeout)
	        		.setSocketTimeout(socketTimeout)
	        		.build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			entity = new UrlEncodedFormEntity(formparams, charset);  
	        httpPost.setEntity(entity);
	        //post请求  
	        httpResponse = closeableHttpClient.execute(httpPost);
	        if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
            	String msg = "访问失败！！HTTP_STATUS=" + httpResponse.getStatusLine().getStatusCode();
				throw new HttpException(msg);
            }
	        HttpEntity httpEntity = httpResponse.getEntity();
	        long length = httpEntity.getContentLength();
	        if(length <= 0){
	        	return null;
	        }else{
	        	is = httpEntity.getContent();
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/*try {
        		if(closeableHttpClient != null){
        			closeableHttpClient.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}
		return is;
	}
	
	/**
	 * 下载对账文件
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream downLoad(String url) throws Exception{
		HttpClientBuilder httpClientBuilder = null;
		CloseableHttpClient httpClient = null;
		InputStream in = null;
		httpClientBuilder = HttpClientBuilder.create();
		//HttpClient  
		httpClient = httpClientBuilder.build();
	    HttpGet httpGet = new HttpGet(url);
	    RequestConfig requestConfig = RequestConfig.custom()
         		.setConnectionRequestTimeout(connectionRequestTimeout)
         		.setConnectTimeout(connectTimeout)
         		.setSocketTimeout(socketTimeout)
         		.build();
         httpGet.setConfig(requestConfig);
	    HttpResponse httpResponse = httpClient.execute(httpGet);
	    if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
        	String msg = "访问失败！！HTTP_STATUS=" + httpResponse.getStatusLine().getStatusCode();
			throw new HttpException(msg);
        }
	    HttpEntity entity = httpResponse.getEntity();
	    in = entity.getContent();
	    return in;
	}
	
	public static String doPostForHttps(Map<String,String> requestParams,String url) throws Exception{
		CloseableHttpClient closeableHttpClient = null;
		UrlEncodedFormEntity entity = null;
		HttpResponse httpResponse = null;
		String rtnStr = null;
		HttpPost httpPost = null;
		try{
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			if(requestParams != null){
				Iterator<String> it = requestParams.keySet().iterator();
				while(it.hasNext()){
					String key = it.next();
					formparams.add(new BasicNameValuePair(key, requestParams.get(key)));
				}
			}
			//HttpClient  
			closeableHttpClient = createSSLClientDefault();
			RequestConfig requestConfig = RequestConfig.custom()
	        		.setConnectionRequestTimeout(connectionRequestTimeout)
	        		.setConnectTimeout(connectTimeout)
	        		.setSocketTimeout(socketTimeout)
	        		.build();
			httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			entity = new UrlEncodedFormEntity(formparams, charset);  
	        httpPost.setEntity(entity);
	        //post请求  
	        httpResponse = closeableHttpClient.execute(httpPost);
	        if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
            	String msg = "访问失败！！HTTP_STATUS=" + httpResponse.getStatusLine().getStatusCode();
				throw new HttpException(msg);
            }
	        HttpEntity httpEntity = httpResponse.getEntity();
	        if (httpEntity != null) {
	        	//打印响应内容  
	        	rtnStr = EntityUtils.toString(httpEntity, charset);
	        }
		}finally{
			if (httpPost != null) {
    			httpPost.releaseConnection();
			}
    		if(closeableHttpClient != null){
    			closeableHttpClient.close();
    		}
		}
		return rtnStr;
	}
	
	/**
	 * 创建 CloseableHttpClient
	 * @return
	 * @throws Exception
	 */
	private static CloseableHttpClient createSSLClientDefault() throws Exception{
		@SuppressWarnings("deprecation")
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		}).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
}
