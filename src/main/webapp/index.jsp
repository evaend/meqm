<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>

<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js" type="text/javascript"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	function scanCode (){
	   wx.scanQRCode({
	        // 默认为0，扫描结果由微信处理，1则直接返回扫描结果
	        needResult : 1,
	        desc : 'scanQRCode desc',
	        success : function(res) {
				alert(res);
	        },
	        error: function(err){
	        	console.log(err)
	        }
	    });

	}
	$(function() {
	    var appId = $("#appId").val();//时间戳
	    var timestamp = $("#timestamp").val();//时间戳
	    var nonceStr = $("#noncestr").val();//随机串
	    var signature = $("#signature").val();//签名
	    wx.config({
	        debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	        appId : appId, // 必填，公众号的唯一标识
	        timestamp : timestamp, // 必填，生成签名的时间戳
	        nonceStr : nonceStr, // 必填，生成签名的随机串
	        signature : signature,// 必填，签名，见附录1
	        jsApiList : [ 'scanQRCode' ]
	    // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	    });
	    setTimeout(function(){
	    	scanCode();
	    }, 200)
	});
</script>
</head>

<body>
    <input id="appId" type="hidden" value="${requestScope.appId}" />
    <input id="timestamp" type="hidden" value="${requestScope.timestamp}" />
    <input id="noncestr" type="hidden" value="${requestScope.nonceStr}" />
    <input id="signature" type="hidden" value="${requestScope.signature}" />
    
    <input id="id_securityCode_input">
	<button id="scanQRCode">扫码</button>
	
<script type="text/javascript">
	$("#scanQRCode").click(function() {
		scanCode();
	});
</script>
	
</body>
</html>
