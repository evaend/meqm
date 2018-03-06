/**
 * Project Name:html2pdf File Name:CreateHtml.java Package Name:com.kirin.common.web Date:2015年11月16日下午5:33:44 Copyright (c) 2015, PHXL All Rights Reserved.
 */

package com.phxl.ysy.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.phxl.core.base.util.DateUtils;
import com.phxl.core.base.util.FileUploadUtil;
import com.phxl.core.base.util.PdfReportHeaderFooter;
import com.phxl.core.base.util.SystemConfig;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 * ClassName:CreateHtml <br/>
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
@Controller
@RequestMapping("/createHtml")
public class CreateHtml{
	public static class Base64ImageProvider extends AbstractImageProvider{

		public Image retrieve(String src) {
			int pos = src.indexOf("base64,");
			try {
				if (src.startsWith("data") && pos > 0) {
					byte[] img = Base64.decode(src.substring(pos + 7));
					return Image.getInstance(img);
				} else {
					return Image.getInstance(src);
				}
			} catch (BadElementException ex) {
				return null;
			} catch (IOException ex) {
				return null;
			}
		}

		public String getImageRootPath() {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping("/printPDF")
	public String printPDF(HttpServletResponse pe, HttpServletRequest request) throws Exception {
//		createPDF(pe,request);
		return "";
	}
	
	public static String createQRCodePDF(HttpServletResponse pe, HttpServletRequest request,
	    List<Map<String, Object>> test_data, List<String> tableFileds, List<String> titleFileds, String title, Map<String, String> formatMap) throws Exception {
       // String path = request.getSession().getServletContext().getRealPath("/");
        String htmlFileContent ="";        
        //Document document = new Document(PageSize.A4.rotate());// 横向PageSize.A4.rotate()    
        // 页边空白
        Rectangle rec = new Rectangle(200,115);
        //Rectangle   pageSize = PageSize.A4;
        Document document  = new Document(rec);
        document.setMargins(1, 0, -15, 0);
        String html_path =SystemConfig.getProperty("QrcodeHtmlPath");//模板路径
        String path = FileUploadUtil.fileReplace(html_path, request);
        File htmlFile = new File(path);
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        
        PdfWriter writer = PdfWriter.getInstance(document, ba);
        document.open();
        if(test_data!=null && !test_data.isEmpty()){
            for(Map<String, Object> data:test_data){
                
                htmlFileContent = file2String(htmlFile, "UTF-8").trim();
                htmlFileContent = parseQrcodeHTML(htmlFileContent,data,tableFileds,titleFileds,title,formatMap);    
                
                document.newPage();
                document.setPageSize(new RectangleReadOnly(200,115));
                // CSS
                CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
                
                // HTML
                HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
                htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
                htmlContext.setImageProvider(new Base64ImageProvider());
                
                // Pipelines
                PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
                HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
                CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
                
                // XML Worker
                XMLWorker worker = new XMLWorker(css, true);
                XMLParser p = new XMLParser(worker);
                //第一页
                String qrcode = data.get("assetsRecordGuid").toString();
                String str_1 = htmlFileContent.replace("${qrImage}", "<img src=\"data:image/png;base64,"+createQRCode(qrcode)+"\" style=\"width:87px;height: 87px;\" />");
                System.out.println("STR_1:"+str_1);               
                p.parse(new ByteArrayInputStream(str_1.getBytes()));
            }
        }
        
        document.close();
        pe.setContentType("application/pdf");
        pe.setContentLength(ba.size());
        try {
            ServletOutputStream out = pe.getOutputStream();
            ba.writeTo(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
	
	/**
	 * 文本文件转换为指定编码的字符串
	 * @param file 文本文件
	 * @param encoding 编码类型
	 * @return 转换后的字符串
	 * @throws IOException
	 */
	public static String file2String(File file, String encoding) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			StringBuilder stringBuilder = new StringBuilder();
			String content;
			while ((content = bufferedReader.readLine()) != null) {
				stringBuilder.append(content);
			}
			return stringBuilder.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

public static String parseQrcodeHTML(String html,Map<String,Object> dataMap, List<String> tableFileds, List<String> titleFileds, String title, Map<String, String> formatMap) throws Exception{
    
	for(int i = 0; i < titleFileds.size(); i++){
		String titleString = dataMap.get(titleFileds.get(i))==null?"":dataMap.get(titleFileds.get(i)).toString();
		title = title.replace("${"+titleFileds.get(i)+"}", titleString);
	}
	
	html = html.replace("${title}", title);
	
	for (int i = 0; i < tableFileds.size(); i++) {
		String tableString = dataMap.get(tableFileds.get(i))==null?"":dataMap.get(tableFileds.get(i)).toString();
		String columnValueFormat = MapUtils.isEmpty(formatMap)? null : formatMap.get(tableFileds.get(i));//数据格式
		if(StringUtils.isNotBlank(columnValueFormat)){
			if(tableString.length() > Integer.valueOf(columnValueFormat))
				tableString = tableString.substring(0,Integer.valueOf(columnValueFormat));
		}
		html = html.replace("${"+tableFileds.get(i)+"}", tableString);
	}
   
//    String qrcode = createQRCode(dataMap.get("qrcode").toString());
    //html = html.replace("${qrImage}", qrcode);//二维码64位字节流
//    html = html.replace("${qrImage}", "<img src=\"data:image/png;base64,"+qrcode+"\" style=\"width:213px;height: 214px;margin-top:10%;margin-left:12%\"/>");
    return html;
}
	
	/**
	 * 
	 * subString:动态截取，并在指定位置插入指定字符串. <br/> 
	 */
	public static String subString(String string,int subindex,String addStr){
		if(string.length() <= subindex){
			return string;
		}
		String retStr = "";
		int intbegin = 0;
		while (true) {
			if(string.length() >= intbegin+subindex){
				retStr += string.substring(intbegin,intbegin+subindex)+addStr;
			}else{
				retStr += string.substring(retStr.replaceAll(addStr, "").length(),string.length());
				break;
			}
			intbegin += subindex;
		}
		return retStr;
	}
	/**
	 * 根据传入的数据来创建二维码64位字节流
	 */
	public static String createQRCode(String data) throws ServletException, IOException {  
		String qrtext = data;
        
        ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageType.PNG).stream();
        byte[] arr = out.toByteArray();
        // 对字节数组Base64编码  
        String str64 = Base64.encodeBytes(arr);// 返回Base64编码过的字节数组字符串
        return str64;
    }
	
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * 根据传入的数据来生成一维码（条形码）
	 * @param data 数据
	 * @param width 条码宽度
	 * @param height 条码高度
	 * @return Base64编码过的字节数组字符串
	 */
	public static String createBarCode(String data, Integer width, Integer height) {
//		if (width == null || width < 200) {
//			width = 200;
//		}
//		if (height == null || height < 50) {
//			height = 50;
//		}
		try {
			// 文字编码
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, width, height, null);
			// 转换为BufferedImage
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
				}
			}
			// 转换为Base64编码过的字节数组字符串
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(image, "png", imOut);
			byte[] arr = bs.toByteArray();
			String str64 = Base64.encodeBytes(arr);
			return str64;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static BitMatrix toBarCodeMatrix(String str, Integer width, Integer height) {
		if (width == null || width < 200) {
			width = 200;
		}
		if (height == null || height < 50) {
			height = 50;
		}
		try {
			// 文字编码
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height, hints);
			return bitMatrix;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
	
	public static void main(String[] args) {
//		String str = parseHTML("",null);
		String str ="11111111  ${afterTable 123a<bcdefr>阿红纹中文hyhjukilop7777788 }  333333333";
//		^\\$\\{afterTable|\\}$
//		\\$\\{afterTable.*\\}
		Pattern pattern = Pattern.compile(".*\\$\\{afterTable.*\\}.*");
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceAll("");
        System.out.println(str);
	}
}
