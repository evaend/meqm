package com.phxl.ysy.web.weixin.ticket;

/**
 * jsapi_ticket 的数据实体
 * @author 张艳丽
 *	Create Date 2017/12/13
 */
public class JsapiTicket {
	private String errcode;	//错误编码
	
	private String errmsg;	//错误内容
	
	private String ticket;	//ticket
	
	private int expires_in;	//有效时间

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	
	
}
