/**
 * 
 */
package com.alibaba.alisonar.controller;

import java.util.List;

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
import com.alibaba.alisonar.dto.DatatableDTO;
import com.alibaba.alisonar.dto.ResultDTO;
import com.alibaba.alisonar.factory.ResultDTOFactory;
import com.alibaba.alisonar.service.AuthPermissionService;

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
		//model.addAttribute("parentNode", authPermissionService.getAllParentNode());
		return "permission/permission_list";

	}
	
	@RequestMapping(value = "/listAuthPermission", method = RequestMethod.POST)
	@ResponseBody
	public DatatableDTO<AuthPermissionDTO> listAuthPermission(@RequestBody AuthPermissionDTO authPermissionDTO) {
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
	public ResultDTO<String> saveAuthPermission(AuthPermission authPermission) {
		logger.info("authPermission===>{}", authPermission);
		authPermissionService.insertSelective(authPermission);
		return ResultDTOFactory.toAck(null);

	}
	
	@RequestMapping(value = "/updatePermission", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<String> updatePermission(AuthPermission authPermission) {
		authPermissionService.updateByPrimaryKeySelective(authPermission);
		return ResultDTOFactory.toAck(null);

	}
	
	
	@RequestMapping(value = "/deletePermission", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<String> deletePermission(Long id) {
		authPermissionService.deletePermission(id);
		return ResultDTOFactory.toAck(null);

	}
	
	
	@RequestMapping(value = "/getSelect2ParentNode", method = RequestMethod.GET)
	@ResponseBody
	public List<AuthPermissionDTO> getSelect2ParentNode( AuthPermissionDTO authPermissionDTO) {
		logger.info("AuthUserSearch===>{}", authPermissionDTO);
		return authPermissionService.getSelect2ParentNode(authPermissionDTO.getSearchStr());

	}
	
	
	@RequestMapping(value = "/getPermissionByParentId", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<AuthPermissionDTO> getPermissionByParentId(Long parentId) {
		logger.info("parentId===>{}", parentId);
		return ResultDTOFactory.toAck(authPermissionService.getPermissionByParentId(parentId));

	}
	

}
