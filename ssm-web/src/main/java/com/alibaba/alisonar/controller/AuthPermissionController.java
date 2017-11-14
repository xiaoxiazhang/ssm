/**
 * 
 */
package com.alibaba.alisonar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.alisonar.domain.AuthPermission;
import com.alibaba.alisonar.dto.AuthPermissionDTO;
import com.alibaba.alisonar.service.AuthPermissionService;
import com.alibaba.alisonar.util.DatatableDto;
import com.alibaba.alisonar.util.ResultDto;
import com.alibaba.alisonar.util.ResultDtoFactory;

/**
 * @author wb-zxx263018
 *
 */

@Controller
@RequestMapping("/permission")
public class AuthPermissionController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthPermissionController.class);
	
	@Autowired
	private AuthPermissionService authPermissionService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String toPermissionListPage(Model model) {
		return "permission/permission_list";

	}
	
	@RequestMapping(value = "/listAuthPermission", method = RequestMethod.POST)
	@ResponseBody
	public DatatableDto<AuthPermissionDTO> listAuthPermission(@RequestBody AuthPermissionDTO authPermissionDTO) {
		logger.info("AuthUserSearch===>{}", authPermissionDTO);
		return authPermissionService.buildDatatableDto(authPermissionDTO);

	}
	
	@RequestMapping(value = "/checkPermission", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkPermission( AuthPermissionDTO authPermissionDTO) {
		return authPermissionService.checkPermission(authPermissionDTO);

	}
	
	@RequestMapping(value = "/savePermission", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> saveAuthPermission(AuthPermission authPermission) {
		logger.info("authPermission===>{}", authPermission);
		authPermissionService.insertSelective(authPermission);
		return ResultDtoFactory.toAck(null);

	}
	
	@RequestMapping(value = "/updatePermission", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> updatePermission(AuthPermission authPermission) {
		authPermissionService.updateByPrimaryKeySelective(authPermission);
		return ResultDtoFactory.toAck(null);

	}
	
	
	@RequestMapping(value = "/deletePermission", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> deletePermission(Long id) {
		authPermissionService.deleteByPrimaryKey(id);
		return ResultDtoFactory.toAck(null);

	}
	
	
	
	

}
