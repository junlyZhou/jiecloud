package com.company.utils.wechat;

/**
 * @author FengJie
 * @Date: 2018/11/27 12:45
 * @Description:  微信公众号相关的配置
 */
public class WeChatUrl {

    /**
     * 微信平台测试账号
     */
    /*public static final String APP_ID = "wx73435a66ea1d6366";
    public static final String APP_SECRET = "f2402699bf90eda17e2f4b54b61f970d";*/

    public static final String APP_ID = "wxcd7555a2d1d6250d";
    public static final String APP_SECRET = "dcd2afec48a0a2935092880cdddea42f";
    public static final String WECHAT_TOKEN = "xx";
    // 商户号
    public static final String MCH_ID = "xx";
    // API密钥
    public static final String API_KEY = "xx";

    /**
     * 异步通知URL
     */
    public static final String NOTIFY_URL = "自己的异步通知URL";

    /**
     * 微信支付统一接口(POST)
     */
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 微信退款接口(POST)
     */
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 订单查询接口(POST)
     */
    public static final String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     *  关闭订单接口(POST)
     */
    public static final String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    /**
     * 退款查询接口(POST)
     */
    public static final String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
    /**
     * 对账单接口(POST)
     */
    public static final String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    /**
     * 短链接转换接口(POST)
     */
    public static final String PAY_SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
    /**
     * 接口调用上报接口(POST)
     */
    public static final String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
    /**
     * Token
     */
    public static final String TOKEN_PATH = "https://api.weixin.qq.com/cgi-bin/token";
    /**
     * getticket
     */
    public static final String  TICKET_PATH = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";


    /**
     *  授权链接
     */
    public static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    /**
     * 获取token的链接
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     *  刷新token
     */
    public static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    /**
     * 获取授权用户信息
     */
    public static final String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    /**
     * 判断用户accessToken是否有效
     */
    public static final String SNS_AUTH_URL = "https://api.weixin.qq.com/sns/auth";
    /**
     * 授权登陆链接
     */
    public static final String QRCONNECT_PATH = "https://open.weixin.qq.com/connect/qrconnect";

}
