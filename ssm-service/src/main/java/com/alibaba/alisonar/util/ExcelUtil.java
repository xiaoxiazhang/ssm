/**
 * 
 */
package com.alibaba.alisonar.util;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.alisonar.annotation.ExcelColumnMeta;

/**
 * @author wb-zxx263018
 *
 */
public class ExcelUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	
	//简单excel构造
	public static <T> HSSFWorkbook  buildCommonExcel(String sheetName,List<T> dtos, Class<T> dtoClass){
		HSSFWorkbook workBook = new HSSFWorkbook();
		buildExcelHeader(workBook, sheetName, dtoClass);
		buildCommonExcelBody(workBook, dtos, dtoClass);
		return workBook;
	}
	
	public static HSSFCellStyle createCellStyle(HSSFWorkbook workBook, boolean isHeader) {
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		// 自动换行
		cellStyle.setWrapText(true);

		// 表头的样式
		if (isHeader) {
			cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 表头字体设置
			HSSFFont font = workBook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			cellStyle.setFont(font);
		}
		return cellStyle;
	}

	public static <T> void buildExcelHeader(HSSFWorkbook workBook, String sheetName, Class<T> dtoClass) {
		// 创建sheet
		HSSFSheet sheet = workBook.createSheet(sheetName);
		CellStyle headerCellStyle = createCellStyle(workBook,true);
		HSSFRow headRow = sheet.createRow(0);
		
		Map<Integer, String> map = new TreeMap<Integer, String>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		Field[] fields = dtoClass.getDeclaredFields();
		int max = -1;
		for (int n = 0; n < fields.length; n++) {
			ExcelColumnMeta meta = fields[n].getAnnotation(ExcelColumnMeta.class);
			if (meta != null && StringUtils.isNotBlank(meta.colName()) && meta.colIndex()>=0) {
				if(meta.colIndex() > max) max= meta.colIndex();
				map.put(meta.colIndex(), meta.colName());
			}
		}
		logger.info("excel列信息===>{}",map.keySet());
		for(int i = 0; i <= max; i++){
			sheet.setColumnWidth(i, 16 * 256);
			HSSFCell cell = headRow.createCell(i);
			String colName = map.get(i)==null ? "未知列-" + i:map.get(i);
			cell.setCellValue(colName);
			cell.setCellStyle(headerCellStyle);
		}
	}
	
	public  static <T> void buildCommonExcelBody(HSSFWorkbook workBook,List<T> dtos,Class<T> dtoClass){
		HSSFSheet sheet = workBook.getSheetAt(0);
		CellStyle bodyCellStyle = createCellStyle(workBook, false);
		Map<Integer, Field> map = new HashMap<Integer, Field>();
		Field[] fields = dtoClass.getDeclaredFields();
		int max = -1;
		for (int n = 0; n < fields.length; n++) {
			ExcelColumnMeta meta = fields[n].getAnnotation(ExcelColumnMeta.class);
			if (meta != null && StringUtils.isNotBlank(meta.colName()) && meta.colIndex()>=0) {
				if(meta.colIndex() > max) max= meta.colIndex();
				fields[n].setAccessible(true);
				map.put(meta.colIndex(), fields[n]);

			}
		}
		try {
			for (int i = 0; i < dtos.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				T dto = dtos.get(i);
				for (int j = 0; j <= max; j++) {
					Field field = map.get(j);
					if (field == null) {
						row.createCell(j).setCellValue("-");
					} else {
						Object obj = field.get(dto);
						HSSFCell cell = row.createCell(j);
						cell.setCellValue(obj==null? "-" : obj.toString());
						cell.setCellStyle(bodyCellStyle);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void buildExcelBody(HSSFWorkbook workBook, List<Object[]> datas, int[] disMergeCols,
			CellStyle bodyCellStyle) {
		HSSFSheet sheet = workBook.getSheetAt(0);
		for (int i = 0; i < datas.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			Object[] objs = datas.get(i);
			// 是否是重复行
			boolean isRepeatRow = i != 0 && objs[0].toString().equals(datas.get(i - 1)[0].toString());
			for (int j = 0; j < objs.length - 1; j++) {

				if (disMergeCols != null && !ArrayUtils.contains(disMergeCols, j) && isRepeatRow) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(bodyCellStyle);
					CellRangeAddress range = new CellRangeAddress(i, i + 1, j, j);
					sheet.addMergedRegion(range);

				} else {
					HSSFCell cell = row.createCell(j);
					// objs[0]是比较数据
					if (objs[j + 1] != null) {
						cell.setCellValue(objs[j + 1].toString());
					}
					cell.setCellStyle(bodyCellStyle);
				}
			}

		}
	}

	

}
