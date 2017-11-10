/**
 * 
 */
package com.alibaba.alisonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.alisonar.service.AuthRoleService;

/**
 * @author wb-zxx263018
 *
 */
@Controller
@RequestMapping(value="role")
public class AuthRoleController {
	
	@Autowired 
	private AuthRoleService authRoleService;
	
	
	

}
