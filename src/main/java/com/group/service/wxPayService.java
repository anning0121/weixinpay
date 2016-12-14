package com.group.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.omg.CORBA.Request;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.group.util.HttpUtil;
import com.group.util.PayCommonUtil;
import com.group.util.PayConfigUtil;
import com.group.util.XMLUtil;
public class wxPayService {
	public static String weixin_pay(String out_trade_no,HttpServletRequest request) throws Exception {  
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
        String body = "dxlm";   // 商品名称  
        //String out_trade_no = "11338"; // 订单号  
     /* //查询订单数据表获取订单信息  
        PayOrder payOrder = payOrderDao.get(PayOrder.class,out_trade_no); */
        // 获取发起电脑 ip  
       // String spbill_create_ip = PayConfigUtil.CREATE_IP; 
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase (ip)) 
        { ip = request.getHeader("Proxy-Client-IP");}
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase (ip)) { 
        	 ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        	ip = request.getRemoteAddr(); 
        }            
        String spbill_create_ip = "123.12.12.123";
        // 回调接口   
        String notify_url = PayConfigUtil.NOTIFY_URL;  
        String trade_type = "NATIVE";  
        String time_start =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());  
        Calendar ca = Calendar.getInstance();  
        ca.setTime(new Date());  
        ca.add(Calendar.DATE, 1);           
        String time_expire =  new SimpleDateFormat("yyyyMMddHHmmss").format(ca.getTime());  
        
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
        packageParams.put("time_start", time_start);  
        packageParams.put("time_expire", time_expire);     
        String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);  
        packageParams.put("sign", sign);  
          //将请求参数转换为xml格式的string
        System.out.println(packageParams);
        String requestXML = PayCommonUtil.getRequestXml(packageParams);  
        System.out.println("请求xml：：：："+requestXML);  
       //请求微信统一下单接口
        String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);  
  System.out.println("返回xml：：：："+resXml);
          //解析xml,返回第一级元素键值对
        Map map = XMLUtil.doXMLParse(resXml); 
        System.out.println(map);
        //String return_code = (String) map.get("return_code");  
        //String prepay_id = (String) map.get("prepay_id");  
        String urlCode = (String) map.get("code_url");  
          System.out.println("打印调用统一下单接口生成二维码url:::::"+urlCode);
        return urlCode;  
}  
	
	
    /** 
	 * 生成二维码图片 不存储 直接以流的形式输出到页面 
	 * @param content 
	 * @param response 
	 */ 
	@SuppressWarnings({ "unchecked", "rawtypes" })  
	public static void encodeQrcode(String content,HttpServletResponse response){  
	    if(content==null || "".equals(content))  
	        return;  
	   MultiFormatWriter multiFormatWriter = new MultiFormatWriter();  
	   Map hints = new HashMap();  
	   hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); //设置字符集编码类型  
	   BitMatrix bitMatrix = null;  
	   try {  
	       bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300,hints);  
	       
	       
	       BufferedImage image = toBufferedImage(bitMatrix);  
	       System.out.println("++++++++++++++++++++"+image);
	       //输出二维码图片流  
	       try {  
	           ImageIO.write(image, "png", response.getOutputStream());  
	       } catch (IOException e) {  
	           // TODO Auto-generated catch block  
	           e.printStackTrace();  
	       }  
	   } catch (WriterException e1) {  
	       // TODO Auto-generated catch block  
	       e1.printStackTrace();  
	   }           
	}  
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	public static BufferedImage toBufferedImage(BitMatrix matrix) {

		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
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
	
	
	
	
	
	
	/**
	 * 微信回调
	 */
	
	
public static void weixin_notify(HttpServletRequest request,HttpServletResponse response) throws Exception{  
   
        //读取参数  
        InputStream inputStream ;  
        StringBuffer sb = new StringBuffer();  
        inputStream = request.getInputStream();  
        String s ;  
        Log logger = (Log) Logger.getRootLogger(); 
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
        while ((s = in.readLine()) != null){  
            sb.append(s);  
        }  
        in.close();  
        inputStream.close();  
  
        //解析xml成map  
        Map<String, String> m = new HashMap<String, String>();  
        m = XMLUtil.doXMLParse(sb.toString());  
          
        //过滤空 设置 TreeMap  
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();        
        Iterator it = m.keySet().iterator();  
        while (it.hasNext()) {  
            String parameter = (String) it.next();  
            String parameterValue = m.get(parameter);  
              
            String v = "";  
            if(null != parameterValue) {  
                v = parameterValue.trim();  
            }  
            packageParams.put(parameter, v);  
        }  
          
        // 账号信息  
        String key = PayConfigUtil.API_KEY; // key  
        
        logger.info(packageParams);  
        //判断签名是否正确  
        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams,key)) {  
            //------------------------------  
            //处理业务开始  
            //------------------------------  
            String resXml = "";  
            if("SUCCESS".equals((String)packageParams.get("result_code"))){  
                // 这里是支付成功  
                //////////执行自己的业务逻辑////////////////  
                String mch_id = (String)packageParams.get("mch_id");  
                String openid = (String)packageParams.get("openid");  
                String is_subscribe = (String)packageParams.get("is_subscribe");  
                String out_trade_no = (String)packageParams.get("out_trade_no");  
                  
                String total_fee = (String)packageParams.get("total_fee");  
                  
                logger.info("mch_id:"+mch_id);  
                logger.info("openid:"+openid);  
                logger.info("is_subscribe:"+is_subscribe);  
                logger.info("out_trade_no:"+out_trade_no);  
                logger.info("total_fee:"+total_fee);  
                  
                //////////执行自己的业务逻辑////////////////  
                  
                logger.info("支付成功");  
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
                  
            } else {  
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));  
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";  
            }  
            //------------------------------  
            //处理业务完毕  
            //------------------------------  
            BufferedOutputStream out = new BufferedOutputStream(  
                    response.getOutputStream());  
            out.write(resXml.getBytes());  
            out.flush();  
            out.close();  
        } else{  
            logger.info("通知签名验证失败");  
        }  
          
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
