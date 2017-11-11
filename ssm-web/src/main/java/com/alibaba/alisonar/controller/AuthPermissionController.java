/**
 * 
 */
package com.alibaba.alisonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.alisonar.service.AuthPermissionService;

/**
 * @author wb-zxx263018
 *
 */

@Controller
@RequestMapping("/permission")
public class AuthPermissionController {
	
	@Autowired
	private AuthPermissionService AuthPermissionService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String toUserListPage(Model model) {
		return "permission/permission_list";

	}
	
	
	
	
	
	
	

}
