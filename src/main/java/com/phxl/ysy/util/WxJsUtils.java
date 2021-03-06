package com.phxl.ysy.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;

import com.phxl.ysy.web.weixin.AccessTokenServlet;
import com.phxl.ysy.web.weixin.ticket.JsapiTicketInfo;
import com.phxl.ysy.web.weixin.ticket.JsapiTicketServlet;

public class WxJsUtils {
	public static void main(String[] args) throws Exception {

        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "http://hsms.com.cn/meqm/WxServlet/test/test";
        Map<String, String> ret = sign(url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    };

    public static Map<String, String> sign(String url) throws Exception {
        Map<String, String> ret = new HashMap<String, String>();
        //这里的jsapi_ticket是获取的jsapi_ticket。
        if (JsapiTicketInfo.jsapiTicket == null) {
			AccessTokenServlet ser = new AccessTokenServlet();
			ser.init();
		}
        if (JsapiTicketInfo.jsapiTicket == null) {
			JsapiTicketServlet ser = new JsapiTicketServlet();
			ser.init();
		}
        String jsapi_ticket = JsapiTicketInfo.jsapiTicket.getTicket();
        String nonce_str = create_nonce_str();	//随机数
        String timestamp = create_timestamp();	//时间戳
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

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

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);	//微信jsapi通行证
        ret.put("nonceStr", nonce_str);		//随机数
        ret.put("timestamp", timestamp);	//时间戳
        ret.put("signature", signature);	//微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。

        return ret;
    }

    /**
     * 获取微信加密签名signature
     * @param hash
     * @return
     */
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
     * 获取随机数
     * @return
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取时间戳
     * @return
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
