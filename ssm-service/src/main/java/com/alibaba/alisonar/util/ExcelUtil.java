/**
 * 
 */
package com.alibaba.alisonar.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.alisonar.annotation.ExcelColumnMeta;

/**
 * @author wb-zxx263018
 *
 */
public class ExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	private static final String OFFICE_EXCEL_XLS = ".xls";
	private static final String OFFICE_EXCEL_XLSX = ".xlsx";


	// 构造简单excel
	public static <T> Workbook buildCommonExcel(String sheetName, List<T> dtos, Class<T> dtoClass) {
		Workbook workBook = new HSSFWorkbook();
		workBook.createSheet(sheetName);
		buildExcelHeader(workBook, dtoClass,true);
		buildCommonExcelBody(workBook, dtos, dtoClass);
		return workBook;
	}

	// 构造模板Excel
	public static <T> Workbook buildTemplateExcel(String sheetName, Class<T> dtoClass) {
		Workbook workBook = new XSSFWorkbook();
		workBook.createSheet(sheetName);
		buildExcelHeader(workBook, dtoClass,false);
		return workBook;
	}
	
	public static  Workbook buildWorkBookByType(String file){
		if(StringUtils.isBlank(file)) return null;
		Workbook workBook = null;
		if(file.endsWith(OFFICE_EXCEL_XLS)){
			workBook = new HSSFWorkbook();
		}else if(file.endsWith(OFFICE_EXCEL_XLSX)){
			workBook = new XSSFWorkbook();
		}
		return workBook;
	}

	public static CellStyle createCellStyle(Workbook workBook, boolean isHeader) {
		CellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
		// 自动换行
		cellStyle.setWrapText(true);

		// 表头的样式
		if (isHeader) {
			cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			// 表头字体设置
			Font font = workBook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 粗体显示
			cellStyle.setFont(font);
		}
		return cellStyle;
	}

	public static <T> void buildExcelHeader(Workbook workBook, Class<T> dtoClass, boolean isOutputType) {

		Sheet sheet = workBook.getSheetAt(0);
		CellStyle headerCellStyle = createCellStyle(workBook, true);
		Row headRow = sheet.createRow(0);
		Map<Integer, String> map = new TreeMap<Integer, String>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		int max = -1;
		Field[] fields = dtoClass.getDeclaredFields();
		if (isOutputType) {//导出类型
			for (int n = 0; n < fields.length; n++) {
				ExcelColumnMeta meta = fields[n].getAnnotation(ExcelColumnMeta.class);
				if (meta != null && StringUtils.isNotBlank(meta.colName()) && meta.outputColIndex() >= 0) {
					if (meta.outputColIndex() > max)
						max = meta.outputColIndex();
					map.put(meta.outputColIndex(), meta.colName());
				}
			}
		} else {////导入类型
			for (int n = 0; n < fields.length; n++) {
				ExcelColumnMeta meta = fields[n].getAnnotation(ExcelColumnMeta.class);
				if (meta != null && StringUtils.isNotBlank(meta.colName()) && meta.inputColIndex() >= 0) {
					if (meta.inputColIndex() > max)
						max = meta.inputColIndex();
					map.put(meta.inputColIndex(), meta.colName());
				}
			}
		}
		logger.info("excel列信息===>{}", map.keySet());
		for (int i = 0; i <= max; i++) {
			sheet.setColumnWidth(i, 16 * 256);
			Cell cell = headRow.createCell(i);
			String colName = map.get(i) == null ? "未知列-" + i : map.get(i);
			cell.setCellValue(colName);
			cell.setCellStyle(headerCellStyle);
		}
	}

	public static <T> void buildCommonExcelBody(Workbook workBook, List<T> dtos, Class<T> dtoClass) {
		Sheet sheet = workBook.getSheetAt(0);
		CellStyle bodyCellStyle = createCellStyle(workBook, false);
		Map<Integer, Field> map = new HashMap<Integer, Field>();
		Field[] fields = dtoClass.getDeclaredFields();
		int max = -1;
		for (int n = 0; n < fields.length; n++) {
			ExcelColumnMeta meta = fields[n].getAnnotation(ExcelColumnMeta.class);
			if (meta != null && StringUtils.isNotBlank(meta.colName()) && meta.outputColIndex() >= 0) {
				if (meta.outputColIndex() > max)
					max = meta.outputColIndex();
				fields[n].setAccessible(true);
				map.put(meta.outputColIndex(), fields[n]);

			}
		}
		try {
			for (int i = 0; i < dtos.size(); i++) {
				Row row = sheet.createRow(i + 1);
				T dto = dtos.get(i);
				for (int j = 0; j <= max; j++) {
					Field field = map.get(j);
					Cell cell = row.createCell(j);
					if (field == null) {
						cell.setCellValue("-");
					} else {
						Object obj = field.get(dto);
						cell.setCellValue(obj == null ? "-" : obj.toString());
					}
					cell.setCellStyle(bodyCellStyle);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void buildExcelBody(Workbook workBook, List<Object[]> datas, int[] disMergeCols,
			CellStyle bodyCellStyle) {
		Sheet sheet = workBook.getSheetAt(0);
		for (int i = 0; i < datas.size(); i++) {
			Row row = sheet.createRow(i + 1);
			Object[] objs = datas.get(i);
			// 是否是重复行
			boolean isRepeatRow = i != 0 && objs[0].toString().equals(datas.get(i - 1)[0].toString());
			for (int j = 0; j < objs.length - 1; j++) {

				if (disMergeCols != null && !ArrayUtils.contains(disMergeCols, j) && isRepeatRow) {
					Cell cell = row.createCell(j);
					cell.setCellStyle(bodyCellStyle);
					CellRangeAddress range = new CellRangeAddress(i, i + 1, j, j);
					sheet.addMergedRegion(range);

				} else {
					Cell cell = row.createCell(j);
					// objs[0]是比较数据
					if (objs[j + 1] != null) {
						cell.setCellValue(objs[j + 1].toString());
					}
					cell.setCellStyle(bodyCellStyle);
				}
			}

		}
	}

	public static <T> List<T> loadExcelData(Class<T> dtoClass, InputStream inputStream) {
		List<T> result = new ArrayList<T>();
		try {
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			/*
			 * 通过注解绑定excel列和对象域的关系到map
			 */
			Map<Integer, Field> map = new HashMap<Integer, Field>();
			Field[] fields = dtoClass.getDeclaredFields();
			for (int n = 0; n < fields.length; n++) {
				ExcelColumnMeta meta = fields[n].getAnnotation(ExcelColumnMeta.class);
				if (meta != null && meta.inputColIndex() >= 0) {
					fields[n].setAccessible(true);
					map.put(meta.inputColIndex(), fields[n]);
				}
			}
			logger.info("列和对象filed关系===>{}", map);

			// 遍历（除标题行0）,把excel中行数据放入dto对象
			for (int n = 1; n <= sheet.getLastRowNum(); n++) {
				Row row = sheet.getRow(n);
				if (row == null)
					continue;
				T dto = dtoClass.newInstance();

				// 遍历列， 把列值绑定到importDto对象的域中
				for (int i = 0; i < row.getLastCellNum(); i++) {
					Cell cell = row.getCell(i);
					// 绑定值到importDto对应的field上面
					if (map.get(i) != null) {
						String typeInfo = map.get(i).getGenericType().toString();
						Object cellValue = getCellValue(cell, typeInfo);
						logger.info("行信息===>{},列信息===>{},类型信息===>{},值===>{}", n, i, typeInfo, cellValue);
						map.get(i).set(dto, cellValue);
					}
				}
				result.add(dto);

			}
		} catch (Exception e) {
			logger.info("加载excel数据失败：", e);
		}
		return result;
	}

	public static Object getCellValue(Cell cell, String typeInfo) {
		if (cell == null || getCellValue(cell) == null)
			return null;

		Object obj = null;

		if (typeInfo.endsWith("String")) {
			obj = getCellValue(cell).toString();

		} else if (typeInfo.endsWith("Long")) {
			obj = Double.valueOf(getCellValue(cell).toString());
			obj = (long) obj;

		} else if (typeInfo.endsWith("Integer")) {
			int i = (int) Double.valueOf(getCellValue(cell).toString()).doubleValue();
			obj = Integer.valueOf(i);

		} else if (typeInfo.endsWith("Double")) {
			long l = (long) Double.valueOf(getCellValue(cell).toString()).doubleValue();
			obj = Long.valueOf(l);

		} else if (typeInfo.endsWith("Date")) {
			String[] pattern = new String[] { "yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMddHHmmss",
					"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };
			try {
				obj = DateUtils.parseDate(getCellValue(cell).toString(), pattern);
			} catch (Exception e) {
				logger.error("时间日期转换失败" + e.getCause().toString());
			}

		} else {
			logger.warn("存在没有处理的类型信息===》{}", typeInfo);
		}
		return obj;
	}

	public static Object getCellValue(Cell cell) {
		Object obj = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			obj = StringUtils.isBlank(cell.getStringCellValue()) ? null : cell.getStringCellValue();
			break;

		case Cell.CELL_TYPE_NUMERIC:
			obj = cell.getNumericCellValue();
			break;
		default:
			break;
		}
		return obj;
	}

}
