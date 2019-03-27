package com.company.utils.wechat;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.company.utils.http.HttpClientUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;

import static com.company.utils.wechat.WeChatUrl.*;


/**
 * @author FengJie
 * @Date: 2018/11/27 14:12
 * @Description:  微信工具类
 */
public class WeChatUtils {

    private HttpClientUtil httpClientUtil;


    public WeChatUtils(HttpClientUtil httpClientUtil){
        this.httpClientUtil = httpClientUtil;
    }

    /**
     * 根据appId和appSecret获取access_token(使用凭证)
     * @param appId
     * @param secret
     * @return
     */
    public  JSONObject  getAccessToken(String appId,String secret){
        Map<String, String> parMaMap = CollUtil.newHashMap();
        parMaMap.put("appid",appId);
        parMaMap.put("secret",secret);
        parMaMap.put("grant_type","client_credential");
        String res = this.httpClientUtil.doGet(TOKEN_PATH, parMaMap);
       return getResult(res);
    }

    /**
     * 通过code换取网页授权access_token
     * @param appId
     * @param secret
     * @param code
     * @return
     */
    public  JSONObject getAccessToken(String appId,String secret,String code){
        Map<String, String> parMaMap = CollUtil.newHashMap();
        parMaMap.put("appid",appId);
        parMaMap.put("secret",secret);
        parMaMap.put("code",code);
        parMaMap.put("grant_type","authorization_code");
        String res = this.httpClientUtil.doGet(ACCESS_TOKEN_URL, parMaMap);
        return getResult(res);
    }

    /**
     * 刷新access_token
     * @param appId
     * @param accessToken
     * @return
     */
    public JSONObject refreshAccessToken(String appId ,String accessToken){
        Map<String, String> parMaMap = CollUtil.newHashMap();
        parMaMap.put("appid",appId);
        parMaMap.put("grant_type","refresh_token");
        parMaMap.put("refresh_token",accessToken);
        String res = this.httpClientUtil.doGet(REFRESH_TOKEN_URL, parMaMap);
        return getResult(res);
    }



    /**
     * /拉取用户信息(需scope为 snsapi_userinfo)
     * @param accessToken
     * @param openid
     * @return
     */
    public JSONObject getWinXinUserInfo(String accessToken,String openid){
        Map<String, String> parMaMap = CollUtil.newHashMap();
        parMaMap.put("access_token",accessToken);
        parMaMap.put("openid",openid);
        parMaMap.put("lang","zh_CN");
        String res = this.httpClientUtil.doGet(USERINFO_URL, parMaMap);
        return getResult(res);
    }

    /**
     * 检验授权凭证（access_token）是否有效
     * @param accessToken
     * @param openid
     * @return
     */
    public JSONObject checkAccessToken(String accessToken,String openid){
        Map<String, String> parMaMap = CollUtil.newHashMap();
        parMaMap.put("access_token",accessToken);
        parMaMap.put("openid",openid);
        String res = this.httpClientUtil.doGet(SNS_AUTH_URL, parMaMap);
        return getResult(res);
    }

    /**
     * 根据access_token获取jsapi_ticket
     * @param accessToken
     * @return
     */
    public JSONObject getJsApiTicket(String accessToken){
        Map<String, String> parMaMap = CollUtil.newHashMap();
        parMaMap.put("type","jsapi");
        parMaMap.put("access_token",accessToken);
        String res = this.httpClientUtil.doGet(TICKET_PATH, parMaMap);
        return getResult(res);
    }


    /**
     * 获取签名
     * @param url
     * @param appId
     * @param secret
     * @return
     */
    public  JSONObject getSign(String url,String appId,String secret){

        JSONObject resultJSON = new JSONObject();
        //获取access_token
        JSONObject accessTokenJson = getAccessToken(appId, secret);
        String accessToken = accessTokenJson.getString("access_token");
        if(StrUtil.isNotEmpty(accessToken)){
            //获取jsapi_ticket
            JSONObject jsApiTicket = getJsApiTicket(accessToken);
            String ticket = jsApiTicket.getString("ticket");
            if(StrUtil.isNotEmpty(ticket)){
                String string1;
                String signature = "";
                //随机字符串
                String noncestr = UUID.randomUUID().toString();
                //时间戳
                String timestamp = Long.toString(System.currentTimeMillis() / 1000);

                //注意这里参数名必须全部小写，且必须有序
                string1 = "jsapi_ticket=" + ticket +
                        "&noncestr=" + noncestr +
                        "&timestamp=" + timestamp +
                        "&url=" + url;
                try
                {
                    MessageDigest crypt = MessageDigest.getInstance("SHA-1");
                    crypt.reset();
                    crypt.update(string1.getBytes("UTF-8"));
                    signature = byteToHex(crypt.digest());
                }
                catch (NoSuchAlgorithmException e)
                {
                    e.printStackTrace();
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                resultJSON.put("url", url);
                resultJSON.put("appId", appId);
                resultJSON.put("jsapi_ticket", ticket);
                resultJSON.put("nonceStr", noncestr);
                resultJSON.put("timestamp", timestamp);
                resultJSON.put("signature", signature);
            }
        }

        return resultJSON;
    }




    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 获取JSON结果
     * @param result
     * @return
     */
    private static JSONObject getResult(String result){
        if(StrUtil.isNotEmpty(result)){
            return JSONObject.parseObject(result);
        }
        return new JSONObject();
    }

}
