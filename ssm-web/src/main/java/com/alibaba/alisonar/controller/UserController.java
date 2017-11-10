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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.service.AuthUserService;
import com.alibaba.alisonar.user.AuthUserSearch;
import com.alibaba.alisonar.util.DatatableDto;
import com.alibaba.alisonar.util.ResultDto;
import com.alibaba.alisonar.util.ResultDtoFactory;

/**
 * @author wb-zxx263018
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private AuthUserService authUserService;

	@ModelAttribute
	public void setAuthUser(Integer id, Model model) {
		if (id != null) {
			AuthUser authUser= authUserService.findOne(id);
			logger.info("modal====>{}",authUser);
			model.addAttribute("authUser", authUser);
		}

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String toUserListPage(Model model) {
		return "user/user_list";

	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableDto<AuthUser> listAuthUser(@RequestBody AuthUserSearch search) {
		logger.info("AuthUserSearch===>{}", search);
		DatatableDto<AuthUser> resultDto = new DatatableDto<AuthUser>();
		resultDto.setRows(authUserService.findProvidedUser(search));
		resultDto.setTotal(authUserService.findProvidedUserCount(search));
		return resultDto;

	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<AuthUser> saveUser(AuthUser user) {
		logger.info("user===>{}", user);
		authUserService.saveAuthUser(user);
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

	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<AuthUser> editUser(@ModelAttribute("authUser")AuthUser authUser) {
		logger.info("user===>{}", authUser);
		authUserService.updateAuthUser(authUser);
		return ResultDtoFactory.toAck(null);

	}

	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<AuthUser> deleteUserById(@PathVariable Integer id) {
		logger.info("userid===>{}", id);
		AuthUser authUser = authUserService.findOne(id);
		authUser.setAuFlag(0);
		authUserService.updateAuthUser(authUser);
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
			logger.error(e.getMessage());
		}

	}

}
