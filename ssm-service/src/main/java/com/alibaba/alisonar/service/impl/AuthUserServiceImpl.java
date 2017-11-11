/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.alisonar.dao.AuthUserMapper;
import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.dto.AuthUserSearch;
import com.alibaba.alisonar.service.AuthUserService;
import com.alibaba.alisonar.util.ExcelUtil;
import com.alibaba.alisonar.util.MyStringUtil;
import com.alibaba.alisonar.util.PasswordHelper;

/**
 * @author wb-zxx263018
 *
 */
@Service
@Transactional
public class AuthUserServiceImpl implements AuthUserService {

	private final static Logger logger = LoggerFactory.getLogger(AuthUserServiceImpl.class);

	@Autowired
	private AuthUserMapper authUserMapper;

	

	@Override
	public int insertSelective(AuthUser record) {
		record.setSalt("18868801131");
		record.setPassword(PasswordHelper.encryptPassword("md5", 2, "123456", record.getSalt()));
		return authUserMapper.insertSelective(record);
		
	}

	@Override
	public AuthUser selectByPrimaryKey(Long id) {
		return authUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AuthUser record) {
		return authUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return authUserMapper.deleteByPrimaryKey(id);
	}
	


	@Override
	public AuthUser findByUsername(String username) {
		return authUserMapper.findByUsername(username);

	}

	@Override
	public List<AuthUser> findAll() {
		return authUserMapper.findAll();
	}


	@Override
	public List<AuthUser> findProvidedUser(AuthUserSearch search) {
		// 如果字段和属性不满足驼峰式和下划线转化要求，排序会报错
		if (StringUtils.isNotBlank(search.getSortName())) {
			String columnName = MyStringUtil.underscoreName(search.getSortName());
			search.setSortName(columnName);

		}
		return authUserMapper.findProvidedUser(search);

	}

	@Override
	public Integer findProvidedUserCount(AuthUserSearch search) {
		return authUserMapper.findProvidedUserCount(search);

	}

	@Override
	public HSSFWorkbook buildExcelWorkBook(AuthUserSearch search) {
		String sheetName = "用户详情";
		String[] header = new String[] { "用户名", "邮件名", "密码", "开始时间" };
		HSSFWorkbook workBook = new HSSFWorkbook();
		CellStyle headerCellStyle = ExcelUtil.createCellStyle(workBook, true);
		ExcelUtil.buildExcelHeader(workBook, sheetName, header, headerCellStyle);

		CellStyle bodyCellStyle = ExcelUtil.createCellStyle(workBook, false);
		List<AuthUser> users = authUserMapper.findProvidedUser(search);
		HSSFSheet sheet = workBook.getSheetAt(0);
		for (int i = 0; i < users.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(users.get(i).getUsername());
			row.createCell(1).setCellValue(users.get(i).getEmail());
			row.createCell(2).setCellValue(users.get(i).getPassword());
			row.createCell(3).setCellValue(users.get(i).getGmtCreate());
			row.getCell(0).setCellStyle(bodyCellStyle);
			row.getCell(1).setCellStyle(bodyCellStyle);
			row.getCell(2).setCellStyle(bodyCellStyle);
			row.getCell(3).setCellStyle(bodyCellStyle);

		}
		return workBook;
	}

	@Override
	public Set<String> findRoles(String username) {
		return authUserMapper.findRoles(username);
		
	}

	@Override
	public Set<String> findPermissions(String username) {
		return authUserMapper.findPermissions(username);
		
	}
	
	

}
