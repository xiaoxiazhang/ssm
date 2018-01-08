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
import com.alibaba.alisonar.domain.AuthRole;
import com.alibaba.alisonar.dto.AuthRoleDTO;
import com.alibaba.alisonar.dto.DatatableDTO;
import com.alibaba.alisonar.dto.ResultDTO;
import com.alibaba.alisonar.factory.ResultDTOFactory;
import com.alibaba.alisonar.service.AuthRoleService;

/**
 * @author wb-zxx263018
 *
 */
@Controller
@RequestMapping(value="role")
public class AuthRoleController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthRoleController.class);
	
	
	@Autowired 
	private AuthRoleService authRoleService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String toRoleListPage(Model model) {
		return "role/role_list";
	}
	
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String toAuthorizePage(Model model) {
		return "role/authorize";
	}
	
	@RequestMapping(value = "/listAuthRole", method = RequestMethod.POST)
	@ResponseBody
	public DatatableDTO<AuthRoleDTO> listAuthPermission(@RequestBody AuthRoleDTO authRoleDTO) {
		logger.info("authRoleDTO===>{}", authRoleDTO);
		return authRoleService.buildDatatableDto(authRoleDTO);

	}
	
	@RequestMapping(value = "/checkRole", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkRole( AuthRoleDTO authRoleDTO) {
		return authRoleService.checkRole(authRoleDTO);

	}
	
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<String> saveRole(AuthRole authRole) {
		logger.info("authRole===>{}", authRole);
		authRoleService.insertSelective(authRole);
		return ResultDTOFactory.toAck(null);

	}
	
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<String> updateRole(AuthRole authRole) {
		authRoleService.updateByPrimaryKeySelective(authRole);
		return ResultDTOFactory.toAck(null);

	}
	
	
	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<String> deleteRole(Long id) {
		authRoleService.deleteRole(id);
		return ResultDTOFactory.toAck(null);

	}
	
	

}
