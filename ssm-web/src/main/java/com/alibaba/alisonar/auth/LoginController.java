/**
 * 
 */
package com.alibaba.alisonar.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.alisonar.dto.ResultDTO;
import com.alibaba.alisonar.dto.UserLoginDTO;
import com.alibaba.alisonar.factory.ResultDTOFactory;

/**
 * @author wb-zxx263018
 *
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	//登录页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		return "login";

	}

	//登录验证
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<String> login(UserLoginDTO userLoginDTO,HttpSession session) {

		logger.info("userLoginDTO======>{}", userLoginDTO);
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
			// token.setRememberMe(true);
			try {
				currentUser.login(token);
			} catch (AuthenticationException ae) {
				logger.error("exception:" + ae.getMessage());
				return ResultDTOFactory.toNack(HttpStatus.INTERNAL_SERVER_ERROR.value(), "用户认证失败");
			}
		}
		logger.info("当前的用户是===>{}",currentUser.getPrincipal());
		session.setAttribute("username", currentUser.getPrincipal());
		return ResultDTOFactory.toAck(null);
	}
	
	
	@RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
	public String unauthorizePage(Model model) {
		model.addAttribute("msg", "没有权限");
		return "error";

	}
	
	/*@RequestMapping(value = "/logout", method = RequestMethod.GET)  
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {  
		Subject currentUser = SecurityUtils.getSubject();
	    // 如果已经登录，则跳转到管理首页  
	    if(currentUser.getPrincipal() != null){  
	    	currentUser.logout();  
	    }  
	    return "redirect:/login";  
	}  */
	
	
	//首页
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(Model model) {
		return "home";

	}
}
