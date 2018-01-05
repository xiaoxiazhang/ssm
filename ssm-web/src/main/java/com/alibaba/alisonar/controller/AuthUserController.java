/**
 * 
 */
package com.alibaba.alisonar.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.dto.AuthUserDTO;
import com.alibaba.alisonar.dto.AuthUserSearch;
import com.alibaba.alisonar.service.AuthRoleService;
import com.alibaba.alisonar.service.AuthUserService;
import com.alibaba.alisonar.util.DatatableDto;
import com.alibaba.alisonar.util.ResultDto;
import com.alibaba.alisonar.util.ResultDtoFactory;

/**
 * @author wb-zxx263018
 *
 */
@Controller
@RequestMapping(value = "/user")
public class AuthUserController {

	private static final Logger logger = LoggerFactory.getLogger(AuthUserController.class);

	@Autowired
	private AuthUserService authUserService;
	
	@Autowired
	private AuthRoleService authRoleService;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String toUserListPage(Model model) {
		model.addAttribute("roles", authRoleService.findAll());
		return "user/user_list";

	}

	@RequestMapping(value = "/listAuthUser", method = RequestMethod.POST)
	@ResponseBody
	public DatatableDto<AuthUserDTO> listAuthUser(@RequestBody AuthUserDTO authUserDTO) {
		logger.info("AuthUserSearch===>{}", authUserDTO);
		return authUserService.buildDatatableDto(authUserDTO);

	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<AuthUser> saveUser(AuthUserDTO authUserDTO) {
		logger.info("user===>{}", authUserDTO);
		authUserService.saveUser(authUserDTO);
		return ResultDtoFactory.toAck(null);

	}
	
	@RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkUsername(@RequestParam String username){
		if(authUserService.findByUsername(username) != null){
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<AuthUser> updateUser(AuthUserDTO authUserDTO) {
		logger.info("user===>{}", authUserDTO);
		authUserService.updateUser(authUserDTO);
		return ResultDtoFactory.toAck(null);

	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> deleteUser(Long id) {
		authUserService.deleteUser(id);
		return ResultDtoFactory.toAck(null);

	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> resetPassword(Long id) {
		authUserService.resetPassword(id);
		return ResultDtoFactory.toAck(null);

	}
	
	

	@RequestMapping(value = "/exportUserExcel", method = RequestMethod.GET)
	public void toExcle( AuthUserSearch search,HttpServletRequest request, HttpServletResponse response)  {
		
		HSSFWorkbook wb = authUserService.buildExcelWorkBook(search);
		String excelName = "用户详情.xls";
		try {
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String(excelName.getBytes("GB2312"), "ISO8859-1"));
			ServletOutputStream outputStream = response.getOutputStream();
			wb.write(outputStream);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
