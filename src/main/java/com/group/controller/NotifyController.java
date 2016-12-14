package com.group.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.service.wxPayService;





@Controller
public class NotifyController {
	
	
	@RequestMapping(value = "/weixinNotify")  
	@ResponseBody  
	public void weixinNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{  
	       System.out.println("支付回调方法开始！");  
	    HashMap<String,Object> map = new HashMap<String,Object>();  
	    wxPayService.weixin_notify(request, response);  
	    System.out.println("支付回调方法结束！");  
	}  
	

}
