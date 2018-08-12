package com.zhongda.museum.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final int CONNECT_TIMEOUT = 5000;//设置超时毫秒数

    private static final int SOCKET_TIMEOUT = 10000;//设置传输毫秒数

    private static final int REQUESTCONNECT_TIMEOUT = 3000;//获取请求超时毫秒数

    private static final int CONNECT_TOTAL = 200;//最大连接数

    private static final int CONNECT_ROUTE = 20;//设置每个路由的基础连接数

    private static final int VALIDATE_TIME = 30000;//设置重用连接时间

    private static final String RESPONSE_CONTENT = "通信失败";

    private static PoolingHttpClientConnectionManager manager = null;

    private static CloseableHttpClient client = null;

    static {
        ConnectionSocketFactory csf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory lsf = createSSLConnSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", csf).register("https", lsf).build();
        manager = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加到200
        manager.setMaxTotal(CONNECT_TOTAL);
        // 将每个路由基础的连接增加到20
        manager.setDefaultMaxPerRoute(CONNECT_ROUTE);
        // 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建
        manager.setValidateAfterInactivity(VALIDATE_TIME);
        // 设置socket超时时间
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
        manager.setDefaultSocketConfig(socketConfig);
        RequestConfig requestConf = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(REQUESTCONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        client = HttpClients.custom().setConnectionManager(manager).setDefaultRequestConfig(requestConf).setRetryHandler(
                //实现了HttpRequestRetryHandler接口的retryRequest(IOException exception, int executionCount, HttpContext context)方法
                (exception, executionCount, context) -> {
                    if(executionCount >= 3)
                        return false; // 如果已经重试了3次, 就放弃
                    if(exception instanceof NoHttpResponseException)
                        return true; //如果服务器断掉了连接, 那么重试
                    if(exception instanceof SSLHandshakeException)
                        return false; // 不要重试SSL握手异常
                    if(exception instanceof InterruptedIOException)
                        return true; //IO传输中断重试
                    if(exception instanceof UnknownHostException)
                        return false; //未知服务器
                    if(exception instanceof ConnectTimeoutException)
                        return true; //超时就重试
                    if(exception instanceof SSLException)
                        return false; // ssl握手异常

                    HttpClientContext clientContext  = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext .getRequest();
                    // 如果请求是幂等的，就再次尝试
                    if(!(request instanceof HttpEntityEnclosingRequest))
                        return true;
                    return false;
                }).build();
        
        if(manager!=null && manager.getTotalStats()!=null)
        	logger.info("连接池状态："+manager.getTotalStats().toString());
    }
    
    /**
     * SSL的socket工厂创建
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        SSLContext context;
        try {
            context = SSLContext.getInstance(SSLConnectionSocketFactory.TLS); 
            TrustManager[] tm = {new X509TrustManager(){
				@Override
				public void checkClientTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
				}
				@Override
				public void checkServerTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
				}
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}}};
			context.init(null, tm, null);
            // 创建SSLSocketFactory , 不校验域名 ,取代以前验证规则
            sslsf = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        } catch (GeneralSecurityException e) {
        	logger.error("SSL上下文创建失败，由于" + e.getLocalizedMessage());
            e.printStackTrace();
        } 
        return sslsf;
    }   
    
    public static String httpGetRequest(String url) {  
        HttpGet httpGet = new HttpGet(url);  
        return getResult(httpGet);  
    }  
  
    public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {  
        URIBuilder ub = new URIBuilder();  
        ub.setPath(url);  
  
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);  
        ub.setParameters(pairs);  
  
        HttpGet httpGet = new HttpGet(ub.build());  
        return getResult(httpGet);  
    }  
  
    public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)  
            throws URISyntaxException {  
        URIBuilder ub = new URIBuilder();  
        ub.setPath(url);  
  
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);  
        ub.setParameters(pairs);  
  
        HttpGet httpGet = new HttpGet(ub.build());  
        for (Map.Entry<String, Object> param : headers.entrySet()) {  
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));  
        }  
        return getResult(httpGet);  
    }  
  
    public static String httpPostRequest(String url) {  
        HttpPost httpPost = new HttpPost(url);  
        return getResult(httpPost);  
    }  
  
    public static String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException {  
        HttpPost httpPost = new HttpPost(url);  
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);  
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));  
        return getResult(httpPost);  
    }  
  
    public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)  
            throws UnsupportedEncodingException {  
        HttpPost httpPost = new HttpPost(url);  
  
        for (Map.Entry<String, Object> param : headers.entrySet()) {  
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));  
        }  
  
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);  
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));  
  
        return getResult(httpPost);  
    }
    
    /**
     * 以POST方式发送json数据
     * @param url 
     * @param jsonData 需要发送的json数据
     * @return 
     */
	public static String httpPostRequest(String url, String jsonData) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		// 绑定到请求 Entry
		StringEntity se = new StringEntity(jsonData, "UTF-8");
		se.setContentType("text/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		httpPost.setEntity(se);
		return getResult(httpPost);	
	}

  
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {  
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();  
        for (Map.Entry<String, Object> param : params.entrySet()) {  
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));  
        }  
        return pairs;  
    }  
    
    /**
     * 执行请求后得到的结果
     */
    private static String getResult(HttpRequestBase method) {
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response = null;
        String responseContent = RESPONSE_CONTENT;
        try {
            response = client.execute(method, context);//执行GET/POST请求
            HttpEntity entity = response.getEntity();//获取响应实体
            if(entity != null) {
                Charset responseCharset  = ContentType.getOrDefault(entity).getCharset();
                responseContent = EntityUtils.toString(entity, responseCharset);
                responseContent = new String(responseContent.getBytes(Charset.forName("UTF-8")));
                EntityUtils.consume(entity);
            }
        } catch(ConnectTimeoutException cte) {
        	logger.error("请求连接超时，由于 " + cte.getLocalizedMessage());
            cte.printStackTrace();
        } catch(SocketTimeoutException ste) {
        	logger.error("请求通信超时，由于 " + ste.getLocalizedMessage());
            ste.printStackTrace();
        } catch(ClientProtocolException cpe) {
        	logger.error("协议错误（比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合），由于 " + cpe.getLocalizedMessage());
            cpe.printStackTrace();
        } catch(IOException ie) {
        	logger.error("实体转换异常或者网络异常， 由于 " + ie.getLocalizedMessage());
            ie.printStackTrace();
        } finally {
            try {
                if(response != null) {
                    response.close();
                }
            } catch(IOException e) {
            	logger.error("响应关闭异常， 由于 " + e.getLocalizedMessage());
            }
            if(method != null) {
                method.releaseConnection();
            } 
        }
        return responseContent;
    }
    
    public static Map<HttpRoute, PoolStats> getConnectionManagerStats() {
    	if (manager != null) {
    		Set<HttpRoute> routeSet = manager.getRoutes();
    		if (routeSet != null && !routeSet.isEmpty()) {
    			Map<HttpRoute, PoolStats> routeStatsMap = new HashMap<HttpRoute, PoolStats>();
    			for (HttpRoute route : routeSet) {
    				PoolStats stats = manager.getStats(route);
    				routeStatsMap.put(route, stats);
    			}
    			return routeStatsMap;
    		}
    	}
    	return null;
    }
    
    public static PoolStats getConnectionManagerTotalStats() {
    	if (manager != null) {
    		return manager.getTotalStats();
    	}
    	return null;
    }
    
    /**
	* 关闭系统时关闭httpClient
	*/
	public static void releaseHttpClient() {
		try {
			client.close();
		} catch (IOException e) {
			logger.error("关闭httpClient异常" + e);
		} finally {
			if (manager != null) {
				manager.shutdown();
			}
		}
	}
}