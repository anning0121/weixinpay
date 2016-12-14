<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
<title>Insert title here</title>
<script type="text/javascript">

 
//支付前订单状态为未支付
//支付后返回后台通知修改订单状态为已支付
//用setInterval 3s请求一次后台，当订单状态改变跳转页面 
/*  $(document).ready(function () {
	    setInterval("ajaxstatus()", 50000);    
	});
	
	function ajaxstatus() {
	    if ($("#out_trade_no").val() != 0) {
	        $.ajax({
	            url: "URL?tradeno=" + $("#out_trade_no").val(),
	            type: "GET",
	            dataType:"json",
	            data: "",
	            success: function (data) {
// 	            	alert(data);
	                if (data==1) { //订单状态为1表示支付成功
	                   window.location.href = "weixinNotify.html"; //页面跳转
	                }
	            },
	            error: function () {
	                 alert("请求订单状态出错"); 
	            }
	        });
	    }
	
	} 



 */


 










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
  	var orderId=10087;
	 $.get("WxPayUnifiedorder.html?out_trade_no="+orderId,function(data){  
	    var codeUrl = data.codeUrl;  
	    if(codeUrl!=null && codeUrl!=""){  
	   $("#id_wxtwoCode").attr('src',"qr_codeImg.html?code_url="+codeUrl);   
	    }  
	});    
	 
	
	
	// $("#id_wxtwoCode").attr('src',"qr_codeImg.html?code_url="+codeUrl);
	
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