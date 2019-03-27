package com.company.utils.message;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author FengJie
 * @Date: 2018/10/17 16:38
 * @Description:  短信发送服务
 */
public class SmsUtils {

    /**
     * 阿里云通信发送消息
     * @param accessKeyId accessKeyId
     * @param accessKeySecret accessKeySecret
     * @param signName  短信签名
     * @param mobile  手机号
     * @param templateCode 模板编号
     * @param templateParam 模板参数
     * @return
     * @throws Exception
     */
    public static boolean aliYunSendMessage(
            String accessKeyId, String accessKeySecret,String signName,
            String mobile,String templateCode,String templateParam) throws Exception {

            DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
            IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        CommonResponse response = client.getCommonResponse(request);
        //返回结果
        JSONObject result = JSON.parseObject(response.getData());
        System.out.println(result.toJSONString());
        return StrUtil.equalsIgnoreCase(result.getString("Code"),"ok");
    }

}
