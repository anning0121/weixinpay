<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
<title>Insert title here</title>
<script type="text/javascript">

//common.get("/*****/wxpay/WxPayUnifiedorder?out_trade_no="+orderId,function(data){  
  //  var codeUrl = data.codeUrl;  
  //  if(codeUrl!=null && codeUrl!=""){  
 //   $("#id_wxtwoCode").attr('src',"******/wxpay/qr_codeImg?code_url="+codeUrl);  
 //   }  
//}); 
function pic(){
	//alert("0.0");

	var codeUrl="http://www.baidu.com";
	var imgsrc;
 /* 	var orderId=10086;
	 $.get("WxPayUnifiedorder?out_trade_no="+orderId,function(data){  
	    var codeUrl = data.codeUrl;  
	    if(codeUrl!=null && codeUrl!=""){  
	   $("#id_wxtwoCode").attr('src',"qr_codeImg?code_url="+codeUrl);   
	    }  
	});    */
	 
	
	
	 $("#id_wxtwoCode").attr('src',"qr_codeImg?code_url="+codeUrl);
	
/* 	 $.get("qr_codeImg",{"codeUrl":codeUrl},function(data){
 		imgsrc=data;
	 
	 },'json'); */
	 
	  
	 
	 
	/*  
	$.ajax({
		type:'post',
		url:'spring-mvc/qr_codeImg',
		data:{"codeUrl":codeUrl},
		dataType:'json',
		success:function(data){
			alert(data);
			imgsrc=data;
			
		}
		
	}); */
	 
	// $("#id_wxtwoCode").attr('src',imgsrc);

	
}


</script>
</head>
<body>
<div class="weimg">  
<input type="button" value="提交" onclick="pic()">
    <!-- <img src="../common/img/wei_06.png"> -->  
<img id="id_wxtwoCode"src=""/>  
<p>打开手机端微信<br>扫一扫继续支付</p>  
</div>  
</body>
</html>