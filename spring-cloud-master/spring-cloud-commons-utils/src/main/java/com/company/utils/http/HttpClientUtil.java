package com.company.utils.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Method;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: FengJie
 * @Date: 2019/3/1 0001 22:19
 * @Description:  HttpClient 工具类
 */
public class HttpClientUtil {

    private HttpClient httpClient;

    /**
     * 构造函数传递自定义HttpClient
     * @param httpClient
     */
    public HttpClientUtil(HttpClient httpClient){
        if(ObjectUtil.isNotNull(httpClient)){
            this.httpClient = httpClient;
        }else {
            this.httpClient = HttpClients.createDefault();
        }
    }

    /**
     * 创建http连接池
     * @param maxTotal   最大连接数
     * @param defaultMaxPerRoute  默认的每个路由的最大连接数
     * @return
     */
    public HttpClientConnectionManager createHttpClientPool(Integer maxTotal,Integer defaultMaxPerRoute){
        PoolingHttpClientConnectionManager httpPool = new PoolingHttpClientConnectionManager();
        httpPool.setMaxTotal(maxTotal);
        httpPool.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return httpPool;
    }

    /**
     * request请求相关配置
     * @param connectTimeout 连接超时时间
     * @param connectionRequestTimeout 读超时时间（等待数据超时时间）
     * @param socketTimeout 从池中获取连接超时时间
     * @return
     */
    public RequestConfig requestConfig(Integer connectTimeout,Integer connectionRequestTimeout,Integer socketTimeout ){
        return RequestConfig.custom().setConnectTimeout(connectTimeout)
                                     .setConnectionRequestTimeout(connectionRequestTimeout)
                                     .setSocketTimeout(socketTimeout).build();
    }

    /**
     * 创建HttpClient 配置连接池
     * @param poolManager  http连接池
     * @param requestConfig request请求相关配置
     * @return
     */
    public HttpClient createHttpClient(HttpClientConnectionManager poolManager,RequestConfig requestConfig) {
        return HttpClientBuilder.create().setConnectionManager(poolManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    /**
     * get请求返回输入流
     * @param url 请求地址
     * @param param 请求参数
     * @return
     */
    public InputStream doGetStream(String url, Map<String, String> param)  {
        HttpResponse response = execute(Method.GET.name(), url, param);
        //请求成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream stream = null;
                try {
                    stream = response.getEntity().getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(ObjectUtil.isNull(stream)){
                    return null;
                }
                return new BufferedInputStream(stream);
            }
            return null;
    }

    /**
     * get请求返回输入流
     * @param url  请求地址
     * @return
     */
    public InputStream doGetStream(String url)  {
       return doGetStream(url,null);
    }


    /**
     * get请求返回字符串结果
     * @param url 请求地址
     * @param param  请求参数
     * @return
     */
    public String doGet(String url, Map<String, String> param){
        HttpResponse response = execute(Method.GET.name(), url, param);
        return getResult(response);
    }

    /**
     * get请求返回字符串结果
     * @param url 请求地址
     * @return
     */
    public String doGet(String url){
        return doGet(url,null);
    }

    /**
     * POST请求
     * @param url 请求地址
     * @param param  请求参数
     * @return
     */
    public String doPost(String url, Map<String, String> param){
        HttpResponse response = execute(Method.POST.name(), url, param);
        return getResult(response);
    }

    /**
     * POST请求
     * @param url 请求参数
     * @return
     */
    public String doPost(String url){
        return doPost(url,null);
    }


    /**
     * 统一请求
     * @param method 请求方式
     * @param url  请求地址
     * @param param  请求参数
     * @return
     */
    private HttpResponse execute(String method,String url, Map<String, String> param){
        HttpRequestBase request = null;

        try {
            //绑定参数
                if(StrUtil.equals(Method.GET.name(),method)){
                    URIBuilder builder = new URIBuilder(url);
                    //拼接参数
                    if(CollUtil.isNotEmpty(param)){
                        param.forEach((k, v)-> builder.addParameter(k,v));
                    }
                    URI uri = builder.build();
                    request = new HttpGet(uri);

                }

                if(StrUtil.equals(Method.POST.name(),method)){
                    request =  new HttpPost(url);
                    if(CollUtil.isNotEmpty(param)){
                        //创建参数列表
                        List<BasicNameValuePair> paramList = param.entrySet().stream()
                                .map(e -> new BasicNameValuePair(e.getKey(), e.getValue())).collect(Collectors.toList());
                        // 模拟表单
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,CharsetUtil.UTF_8);
                        ((HttpPost) request).setEntity(entity);
                    }
                }
            return  this.httpClient.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放连接
            request.releaseConnection();
        }
        return null;
    }

    /**
     * 获取请求的结果
     * @param response
     * @return
     */
    private String getResult(HttpResponse response){
        if(ObjectUtil.isNull(response)){
            return StrUtil.EMPTY;
        }
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            try {
                String result = EntityUtils.toString(response.getEntity(), CharsetUtil.UTF_8);
                return StrUtil.nullToEmpty(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return StrUtil.EMPTY;
    }
    }




