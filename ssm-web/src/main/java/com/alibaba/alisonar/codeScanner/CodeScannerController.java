/**
 * 
 */
package com.alibaba.alisonar.codeScanner;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.domain.CodeRepository;
import com.alibaba.alisonar.service.AuthUserService;
import com.alibaba.alisonar.service.CodeRepositoryService;
import com.alibaba.alisonar.util.MyBeanUtil;
import com.alibaba.alisonar.vo.CodeRepositoryVO;

/**
 * @author wb-zxx263018
 *
 */
@Controller
public class CodeScannerController {
	
	private final static Logger logger = LoggerFactory.getLogger(CodeScannerController.class);
	
	@Autowired
	private CodeRepositoryService codeRepositoryService;
	
	@Autowired
	private AuthUserService authUserService;
	
	@RequestMapping(value="/codeScanner")
	@ResponseBody
	public String saveCodeRepository(CodeRepositoryVO codeRepositoryVO){
		CodeRepository codeRepository = new CodeRepository();
		MyBeanUtil.copyProperties(codeRepositoryVO, codeRepository);
		logger.info("codeRepository======>{}",codeRepository);
		//登录信息
		Subject subject = SecurityUtils.getSubject();
		AuthUser userInfo = authUserService.findByUsername(subject.getPrincipal().toString());
		codeRepository.setUserId(userInfo.getId());
		codeRepositoryService.saveCodeRepository(codeRepository);
		return "SUCESS";
	}
	
	
}
