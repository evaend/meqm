package com.phxl.ysy.web;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phxl.core.base.constant.MimeType;
import com.phxl.core.base.util.FTPUtils;
import com.phxl.core.base.util.FTPUtils.Insertable;

/**
 * FTP资源访问类接口 <br/>
 * @date: 2016年7月13日 上午9:11:11
 * @author 黄文君
 * @version 1.0
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/ftp")
public class FtpSourceController {
	public final Logger logger = LoggerFactory.getLogger(FTPUtils.class);
	
	FTPUtils service = new FTPUtils();
	/**
	 * 下载（预览）文档
	 * @param isPreview     是否预览（true是，false否）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/**/*.*")
	@ResponseBody
	public void download(@RequestParam(defaultValue = "true") final Boolean isPreview, HttpServletRequest request, 
			final HttpServletResponse response) throws Exception {
		String uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
		String ftpBaseUri = request.getContextPath() + "/ftp";
		String filePath = uri.substring(uri.indexOf(ftpBaseUri) + ftpBaseUri.length());
		logger.debug("FTP资源路径 => filePath={}, uri={}", filePath, uri);
		Assert.notNull(filePath, "filePath，不能为空");

		final OutputStream out = new BufferedOutputStream(response.getOutputStream());
		FTPUtils.download(filePath, out, new Insertable() {
			@Override
			public void afterProcess(FTPClient ftpClient, String directory, String fileName, String fileType) throws Exception {
				String mimeType = "";
				if(isPreview) {//预览
					if (fileType.matches("pdf|PDF")) {
						mimeType = MimeType.APPLICATION_OF_PDF;
					} else if (fileType.matches("jpg|JPG|png|PNG|jpeg|JPEG|gif|GIF|ico|ICO|icon|ICON|bmp|BMP")) {
						mimeType = MimeType.IMAGE_OF_JPEG;
					} else if (fileType.matches("html|HTML|htm|HTM")) {
						mimeType = MimeType.TEXT_OF_HTML;
					} else if (fileType.matches("csv|CSV")) {
						mimeType = MimeType.TEXT_OF_CSV;
					} else if (fileType.matches("txt|TXT")) {
						mimeType = MimeType.TEXT_OF_PLAIN;
					} else if (fileType.matches("xml|XML")) {
						mimeType = MimeType.TEXT_OF_XML;
					} else {
						mimeType = MimeType.TEXT_OF_PLAIN;
					}
					logger.debug("【文档预览】mimeType = {}", mimeType);
					response.setContentType(mimeType);
					out.flush();
				}else {//下载
					mimeType = MimeType.APPLICATION_OF_OCTET_STREAM;
					logger.debug("【文档下载】mimeType = {}", mimeType);
					response.setContentType(MimeType.APPLICATION_OF_OCTET_STREAM);
					response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
					out.flush();
				}
			}
		});
	}
	
	@RequestMapping("/post")
	@ResponseBody
	public String postFile(){
		return "success";
	}
}
