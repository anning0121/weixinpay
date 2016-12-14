package com.group.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.group.service.wxPayService;



@Controller
public class WxPayController {
	
	/**
	 * 微信扫码支付统一下单
	 */
	@RequestMapping(value = "WxPayUnifiedorder", method = RequestMethod.GET)
	@ResponseBody
	public Object WxPayUnifiedorder(String out_trade_no,HttpServletRequest request) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String codeUrl = wxPayService.weixin_pay(out_trade_no, request);
		map.put("codeUrl",codeUrl);		
		return map;
	}
	
	/** 
	 * 生成二维码图片并直接以流的形式输出到页面 
	 * @param code_url 
	 * @param response 
	 */  
	@RequestMapping(value="qr_codeImg")  
	@ResponseBody  
	public void getQRCode(String code_url,HttpServletResponse response){  
		System.out.println("++++++++++++++++++++++"+code_url);
	    wxPayService.encodeQrcode(code_url, response);  
	}  
	
	@RequestMapping(value="qr")  
	@ResponseBody  
	public void getQRCode1(String code_url,HttpServletResponse response){  
		System.out.println("++++++++++++++++++++++"+code_url);
	   // wxPayService.encodeQrcode(code_url, response); 
		
	    String str="success";
	    String str1="succ"+new String("ess");
	    System.out.println(str==str1);
	   int result=0;
	   int i=2;
	   switch(i){
	   case 1:
		   result=result+i;
		case 2:
			result=result+i*2;
	   
		case 3:
			result=result+i*3;
	   }
	   System.out.println(result);
	    
	    
	    
	}  
	
	
	
	/*
	@RequestMapping("/weixin")
	public static String weixin_pay() throws Exception {  
		System.out.println("来了");
        // 账号信息  
        String appid = PayConfigUtil.APP_ID;  // appid  
        //String appsecret = PayConfigUtil.APP_SECRET; // appsecret  
        String mch_id = PayConfigUtil.MCH_ID; // 商业号  
        String key = PayConfigUtil.API_KEY; // key  
  
        String currTime = PayCommonUtil.getCurrTime();  
        String strTime = currTime.substring(8, currTime.length());  
        String strRandom = PayCommonUtil.buildRandom(4) + "";  
        String nonce_str = strTime + strRandom;  
          
        String order_price = "1"; // 价格   注意：价格的单位是分  
        String body = "goodssssss";   // 商品名称  
        String out_trade_no = "11338"; // 订单号  
          
        // 获取发起电脑 ip  
        String spbill_create_ip = PayConfigUtil.CREATE_IP;  
        // 回调接口   
        String notify_url = PayConfigUtil.NOTIFY_URL;  
        String trade_type = "NATIVE";  
          
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();  
        packageParams.put("appid", appid);  
        packageParams.put("mch_id", mch_id);  
        packageParams.put("nonce_str", nonce_str);  
        packageParams.put("body", body);  
        packageParams.put("out_trade_no", out_trade_no);  
        packageParams.put("total_fee", order_price);  
        packageParams.put("spbill_create_ip", spbill_create_ip);  
        packageParams.put("notify_url", notify_url);  
        packageParams.put("trade_type", trade_type);  
  
        String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);  
        packageParams.put("sign", sign);  
          
        String requestXML = PayCommonUtil.getRequestXml(packageParams);  
        System.out.println("requestXML"+requestXML);  
   
        String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);  
  System.out.println("resXml"+resXml);
          
        Map map = XMLUtil.doXMLParse(resXml);  
        //String return_code = (String) map.get("return_code");  
        //String prepay_id = (String) map.get("prepay_id");  
        String urlCode = (String) map.get("code_url");  
          System.out.println("urlCode"+urlCode);
        return urlCode;  
}  
	
	
	
	public static String QRfromGoogle(String chl) throws Exception {  
	    int widhtHeight = 300;  
	    String EC_level = "L";  
	    int margin = 0;  
	    chl = UrlEncode(chl);  
	    String QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight  
	            + "&cht=qr&chld=" + EC_level + "|" + margin + "&chl=" + chl;  
	  
	    return QRfromGoogle;  
	}  
	
	
	
	
	public static String UrlEncode(String src)  throws UnsupportedEncodingException {  
	    return URLEncoder.encode(src, "UTF-8").replace("+", "%20");  
	}  
	
	
*/
	}
