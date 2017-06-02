/**
 * 
 */
package com.alibaba.alisonar.util;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author wb-zxx263018
 *
 */
public class ExcelUtil {
	public static HSSFWorkbook buildExcelWorkBook(String sheetName, String[] header, List<Object[]> datas,
			int[] disMergeCols) {
		HSSFWorkbook workBook = new HSSFWorkbook();
		CellStyle headerCellStyle = createCellStyle(workBook, true);
		CellStyle bodyCellStyle = createCellStyle(workBook, false);
		buildExcelHeader(workBook, sheetName, header, headerCellStyle);
		if (datas != null) {
			buildExcelBody(workBook, datas, disMergeCols, bodyCellStyle);

		}
		return workBook;

	}

	public static void buildExcelHeader(HSSFWorkbook workBook, String sheetName, String[] header,
			CellStyle headerCellStyle) {

		// 创建sheet
		HSSFSheet sheet = workBook.createSheet(sheetName);

		HSSFRow headRow = sheet.createRow(0);
		for (int i = 0; i < header.length; i++) {
			// 设置整体列格式
			sheet.setColumnWidth(i, 16 * 256);
			HSSFCell cell = headRow.createCell(i);
			cell.setCellValue(header[i].toString());
			cell.setCellStyle(headerCellStyle);
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

	public static HSSFCellStyle createCellStyle(HSSFWorkbook workBook, boolean isHeader) {
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		// 自动换行
		cellStyle.setWrapText(true);

		// 设置字体
		HSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示

		// 表头的样式
		if (isHeader) {
			cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 表头字体设置
			cellStyle.setFont(font);

		}

		return cellStyle;
	}

}
