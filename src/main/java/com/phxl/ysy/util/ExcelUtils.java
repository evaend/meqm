package com.phxl.ysy.util;

import java.io.InputStream;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.core.base.util.LocalStringUtils;

/**
 * Excel表格处理工具
 *
 * @author 黄文君
 * @version 1.0
 * @date 2017年5月26日 下午2:26:50
 * @since JDK 1.6
 */
public class ExcelUtils {
	public final static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

	/**
	 * 直接将excel表格封装成实体对象列表
	 *
	 * @param is            文档数据流
	 * @param dataRowOffset 首个数据行: 相对标题行的偏移量（正整数）
	 * @param clazz         映射实体类型
	 * @param properties    属性/列顺序
	 * @return List<T>			    实体列表
	 * @throws Exception
	 * @author 黄文君
	 * @date 2017年5月26日 下午2:31:00
	 */
	public static <T> List<T> readExcel(InputStream is, Integer dataRowOffset, Class<T> clazz, String... properties) throws Exception {
		return readExcel(is, dataRowOffset == null ? 1 : dataRowOffset, clazz, null, properties);
	}

	/**
	 * 直接将excel表格封装成实体对象列表
	 *
	 * @param is            文档数据流
	 * @param dataRowOffset 首个数据行: 相对标题行的偏移量（正整数）
	 * @param clazz         映射实体类型
	 * @param entityHander  实体对象附加处理器
	 * @param properties    属性/列顺序
	 * @return List<T>			   实体列表
	 * @throws Exception
	 * @author 黄文君
	 * @date 2017年5月26日 下午2:31:00
	 */
	public static <T> List<T> readExcel(InputStream is, Integer dataRowOffset, Class<T> clazz, EntityHandler<T> entityHander, String... properties) throws Exception {
		Assert.notNull(dataRowOffset, "标题行的行号，必需指定");
		Workbook workbook = null;
		
		/*String fileType =FilenameUtils.getExtension(filename);
		logger.trace("fileType => " + FilenameUtils.getExtension(filename));
		if (fileType.equalsIgnoreCase("xls")) { //如果excel是2003文档格式
			workbook = new HSSFWorkbook(is);
		}else if (fileType.equalsIgnoreCase("xlsx")) { //如果excel是2007文档格式
			workbook = new XSSFWorkbook(is);
		}else{
			throw new ValidationException("不支持的表格文档类型");
		}*/

		if (!is.markSupported()) {
			is = new PushbackInputStream(is, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(is)) {
			workbook = new HSSFWorkbook(is);
		} else if (POIXMLDocument.hasOOXMLHeader(is)) {
//			workbook = new XSSFWorkbook(OPCPackage.open(is));
		} else {
			throw new ValidationException("不支持的表格文档类型");
		}

		logger.trace("表格数量 => {}", workbook.getNumberOfSheets());
		Assert.notEmpty(properties, "请指定表格列与属性映射列表");
		logger.trace("列字段映射 => {}, 共{}列", Arrays.toString(properties), properties.length);

		List<T> entityList = null;
		StringBuffer rows = new StringBuffer("\n");
		for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
			Sheet sheet = workbook.getSheetAt(sheetNum);
			String sheetName = sheet.getSheetName();
			int totalRowNum = sheet.getPhysicalNumberOfRows();
			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			if (totalRowNum == 0) {//跳过空表格
				continue;
			}

			//------------------------------表头字段索引------------------------------
			Row headerRow = sheet.getRow(firstRowNum);//表头一行
			if (headerRow == null) continue;
			String[] columnNames = new String[headerRow.getLastCellNum() - headerRow.getFirstCellNum()];
			if (logger.isTraceEnabled()) rows.append("【列头索引】\t\t");
			for (int cellnum = headerRow.getFirstCellNum(), i = 0; cellnum <= headerRow.getLastCellNum(); cellnum++, i++) {
				Cell cell = headerRow.getCell(cellnum);
				if (cell == null) continue;
				Object value = getCellValue(cell);//读取单元格内容
				if ((value instanceof CharSequence) && StringUtils.isBlank(value.toString())) value = null;
				if (logger.isTraceEnabled()) rows.append((value != null ? value : "--\t") + " \t ");
				if (value == null) continue;
				for (String column : properties) {
					String columnName = value.toString();
					if (column.startsWith(columnName)) {
						columnNames[i] = column.substring(columnName.length() + 1, column.length() - 1);
					}
				}
			}
			if (logger.isTraceEnabled()) rows.append("\n");
			logger.info("[第{}张表格:{}]总行数={}, 首行行号={}, 最后行号={}, 列表头部字段索引 ==> {}", new Object[]{sheetNum, sheetName, totalRowNum, firstRowNum, lastRowNum, Arrays.toString(columnNames)});

			/*
			 * 数据行号的相对偏移量，应该是正整数
			 * 举例：
			 * (1)如果标题行占一行，那边偏移量为1。
			 * (1)如果标题行占一行，表头其他部分占n行，那边偏移量为1+n。
			 */
			if (dataRowOffset < 0) {
				throw new ValidationException("首个数据行: 相对标题行的偏移量，应该是正整数");
			}
			for (int rownum = firstRowNum + dataRowOffset; rownum <= lastRowNum; rownum++) {//一般开始于:firstRowNum + dataRowOffset
				Row row = sheet.getRow(rownum);
				if (row == null) continue;

				int firstCellNum = row.getFirstCellNum();
				int lastCellNum = row.getLastCellNum();
				T entity = clazz.newInstance();
				boolean isBlankRow = true;
				StringBuffer rowString = new StringBuffer();
				for (int cellnum = firstCellNum, i = 0; cellnum < firstCellNum + columnNames.length; cellnum++, i++) {
					Cell cell = row.getCell(cellnum);
					if (cell == null) continue;
					Object value = getCellValue(cell);//读取单元格内容
					if ((value instanceof CharSequence) && StringUtils.isBlank(value.toString())) value = null;
					if (columnNames[i] != null && value != null) BeanUtils.setProperty(entity, columnNames[i], value);
					isBlankRow = (columnNames[i] != null && value != null ? false : isBlankRow);
					if (logger.isTraceEnabled() && !isBlankRow) rowString.append(value != null ? value : "--\t").append(" \t ");
				}
				//检查实体信息完整性与合法性
				if (!isBlankRow) {
					if (entityHander != null) entity = entityHander.process(sheetName, rownum, entity);
					if (entity != null) (entityList = entityList == null ? new ArrayList<T>() : entityList).add(entity);
					if (logger.isTraceEnabled() && StringUtils.isNotBlank(rowString.toString())) {
						rows.append("[第" + rownum + "行: " + firstCellNum + "至" + lastCellNum + "列] - ").append(rowString).append("\n");
					}
				}
			}
		}
		LocalAssert.notEmpty(entityList, "表格至少1行信息（列表头部除外）");
		if (logger.isTraceEnabled() && rows.length() > 0) {
			logger.trace(rows.deleteCharAt(rows.length() - 1).toString());//logger.trace("表格共{}条记录，具体如下：{}\n", entityList.size(), JSONUtils.toPrettyJsonLoosely(entityList));
		}
		return entityList;
	}

	/**
	 * 实体处理器
	 *
	 * @author 黄文君
	 * @version 1.0
	 * @date 2017年5月27日 下午2:39:37
	 * @since JDK 1.6
	 */
	public interface EntityHandler<T> {

		/**
		 * 实体对象信息处理与加工
		 *
		 * @param sheetName 表格名称
		 * @param rownum    表格行号
		 * @param entity    实体对象
		 * @return T
		 * @author 黄文君
		 * @date 2017年5月27日 下午2:39:34
		 */
		public T process(String sheetName, int rownum, T entity);

	}

	/**
	 * 读取行数据单元格的值
	 *
	 * @param cell 单元格对象
	 * @return Object  单元格值
	 * @author 黄文君
	 * @date 2017年5月26日 下午2:30:30
	 */
	public static Object getCellValue(Cell cell) throws Exception {
		Object cellValue = null;
		if (cell != null) {
			//判断数据的类型
			switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING: //字符串
					cellValue = LocalStringUtils.toHalfWidth(cell.getStringCellValue());
					break;
				case HSSFCell.CELL_TYPE_NUMERIC: //数字
					cellValue = cell.getNumericCellValue();
					if (DateUtil.isCellDateFormatted(cell)) {//如果是日期
						cellValue = cell.getDateCellValue();
					} else {
						HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
						String formatValue = dataFormatter.formatCellValue(cell);
						if (NumberUtils.isDigits(formatValue)) {
							cellValue = new BigInteger(formatValue);
						} else if (NumberUtils.isNumber(formatValue)) {
							cellValue = new BigDecimal(formatValue);
						}
						//logger.trace("formatValue=" + formatValue + " => cellValue=" + cellValue);
					}
					break;
				case HSSFCell.CELL_TYPE_FORMULA: //公式
					cellValue = cell.getCellFormula();
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: //布尔值
					cellValue = cell.getBooleanCellValue();
					break;
				case HSSFCell.CELL_TYPE_BLANK: //空白单元格
					cellValue = "";
					break;
				case Cell.CELL_TYPE_ERROR:
					cellValue = cell.getErrorCellValue();
					break;
				default:
					cellValue = null;
					break;
			}
		}
		return cellValue;
	}

}
