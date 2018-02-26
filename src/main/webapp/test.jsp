<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>

<!-- <script src="/js/jquery-1.10.2.min.js" type="text/javascript"></script> -->
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js" type="text/javascript"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<script type="text/javascript">
	var appId =  "${appId}";
	var timestamp =  "${timestamp}";
	var nonceStr =  "${nonceStr}";
	var signature =  "${signature}";
// 	var appId = $("#appId").val();//appid
// 	var timestamp = $("#timestamp").val();//时间戳
// 	var nonceStr = $("#noncestr").val();//随机串
// 	var signature = $("#signature").val();//签名
	//假设已引入微信jssdk。【支持使用 AMD/CMD 标准模块加载方法加载】
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: appId, // 必填，公众号的唯一标识
	    timestamp: timestamp, // 必填，生成签名的时间戳
	    nonceStr: nonceStr, // 必填，生成签名的随机串
	    signature: signature,// 必填，签名，见附录1
	    jsApiList: ['startRecord','onVoicePlayEnd','stopRecord','playVoice','uploadVoice'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	wx.ready(function(){
// 		//注册微信播放录音结束事件【一定要放在wx.ready函数内】
// 		wx.onVoicePlayEnd({
// 		    success: function (res) {
// // 		        stopWave();
// 				var localId = res.localId; // 返回音频的本地ID
// 		    }
// 		}),
// 	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	    
// 		wx.error(function(res){
// 			console.log(err)
// 		    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		
// 		});
	});
	
</script>
</head>

<body>
	<button class="luyin">开始</button>
	<button class="bofang">播放</button>
	<button class="upload">上传</button>
	
	<script type="text/javascript">
		var localId = null;
		var luyintime = null;
		//按住开始按钮时，开始录音
		$('.luyin').on('touchstart',function () {
			console.log("111");
	        wx.startRecord({
	            success: function(){
	                START = new Date().getTime();
	                wx.onVoiceRecordEnd({
	                    // 录音时间超过一分钟没有停止的时候会执行 complete 回调
	                    complete: function (res) {
	                        alert('最多只能录制一分钟');
	                        localId = res.localId;
// 	                        uploadluyin(localId,60000);
	                    }
	                });
	            },
	            cancel: function () {
	                alert('用户拒绝授权录音');
	                return false;
	            }
	        });
	
	    })
	    //松开开始按钮时，结束录音
	    $('.luyin').on('touchend',function () {
	        END = new Date().getTime();
	        //录音时间
	        luyintime=END - START;
	        if(luyintime < 2000){
	            END = 0;
	            START = 0;
	            wx.stopRecord({});
	            alert('录音时间不能少于2秒');
	            return false;
	            //小于300ms，不录音
	        }else {
	            wx.stopRecord({
	                success: function (res) {
	                    localId = res.localId;
// 	                    uploadluyin(localId,luyintime);
	                    wx.playVoice({
	        	    		localId: localId // 需要播放的音频的本地ID，由stopRecord接口获得
	        	    		});
	                }
	            });
	        }
	    })
	    
	     //按播放按钮，播放录音
	     $('.bofang').on('click',function () {
	    	 wx.playVoice({
		    		localId: localId // 需要播放的音频的本地ID，由stopRecord接口获得
		    		});
	     });


	     //上传录音（上传到服务器）
	     $('.upload').on('click',function () {
	        wx.uploadVoice({
	            localId: localId, // 需要上传的音频的本地ID，由stopRecord接口获得
	            isShowProgressTips: 1, // 默认为1，显示进度提示
	            success: function (res) {
	                var serverId = res.serverId; // 返回音频的服务器端ID
	                console.log(serverId);
	                $.post("/test/luyinTest", {
	                            "serverId": serverId,
	                            "luyintime": luyintime
	                        },
	                        function (data) {
	                            if (data.success == 1) {
	                                alert('录音成功');
	                            } else {
	                                alert(data);
	                            }
	                        }, "json");
	            }
	        });
	    });
	</script>
</body>
</html>
