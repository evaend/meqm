<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title>扫一扫</title>
		<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_514194_drxaixti8gp66r.css">
	<style type="text/css">
		*{
			margin: 0;
			padding: 0;
		}
		a{
			text-decoration: none;
		}
		body{
			background: #f5f5f9;
		}
		.iconfont{
		   font-size: 30vw;
		}
		.flex{display: -webkit-box;}
		.centerv{-webkit-box-align: center;}
		.centerh{-webkit-box-pack: center;}  
		.header{
			display: flex;
			align-items: center;
			width:100%;
			height: 11vw;
			font-size:5vw;
			background: #108ee9;
		}
		.header .iconfont{
			font-size:5vw;
			color: #fff;
			padding: 1vw 0 0 3vw;
			background: #108ee9;
		}
		.header span{
			color: #fff;
			display: inline-block;
			width: 85%;
			text-align: center;
		}
		.content p{
			width: 100%;
			padding-top: 20vw;
		}
		.content p a{
			display: inline-block;
			color: #333;
		}
		.text{
			font-size:5vw;
		}
	</style>
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js" type="text/javascript"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	$(function() {
		 var appId = $('#appId').val();
		 var timestamp = $('#timestamp').val();
		 var nonceStr = $('#nonceStr').val();
		 var signature = $('#signature').val();
		 var sessionId = $('#sessionId').val();
		 wx.config({
			        debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			        appId : appId, // 必填，公众号的唯一标识
			        timestamp : timestamp, // 必填，生成签名的时间戳
			        nonceStr : nonceStr, // 必填，生成签名的随机串
			        signature : signature,// 必填，签名
			        jsApiList : [ 'scanQRCode' ]
			    // 必填，需要使用的JS接口列表，所有JS接口列表
			    });
	 	    wx.ready(function() {
	 		   wx.scanQRCode({
			        // 默认为0，扫描结果由微信处理，1则直接返回扫描结果
			        needResult : 1,
			        desc : 'scanQRCode desc',
			        success : function(res) {
						window.location.href ='/meqm/assetsRecordController/selectAssetsRecordFstate?qrcode='+res.resultStr;
			        },
			        error: function(err){
			        	console.log(err)
			        }
			    });
		    })
 	     setTimeout(function(){
			$('.wrapper').show();
	    }, 3000)  
	});
</script>
</head>

<body>
	<div class="wrapper" style='display: none;'>
		<div class="header centerv">
			<a href=javascript:history.go(-1)><i class="iconfont icon-i-left"></i></a>
			<span>医商云</span>
		</div>
		<div class="content" id="scanQRCode">
			<p class="flex centerh"><a href='#'><i class="iconfont icon-erweima"></i></a></p>
			<p class="text flex centerh">点击图标扫一扫</p>
		</div>
	</div>
    <input id="appId" type="hidden" value="${requestScope.appId}" />
    <input id="timestamp" type="hidden" value="${requestScope.timestamp}" />
    <input id="nonceStr" type="hidden" value="${requestScope.nonceStr}" />
    <input id="signature" type="hidden" value="${requestScope.signature}" />
    <input id="sessionId" type="hidden" value="${requestScope.sessionId}" />
	
	<script type="text/javascript">
		$("#scanQRCode").click(function() {
			   wx.scanQRCode({
			        // 默认为0，扫描结果由微信处理，1则直接返回扫描结果
			        needResult : 1,
			        desc : 'scanQRCode desc',
			        success : function(res) {
						window.location.href = '/meqm/assetsRecordController/selectAssetsRecordFstate?qrcode='+res.resultStr;
			        },
			        error: function(err){
			        	console.log(err)
			        }
			    });
		});
	</script>
	
</body>
</html>
