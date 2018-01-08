/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.alisonar.dao.AuthUserMapper;
import com.alibaba.alisonar.domain.AuthRole;
import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.domain.AuthUserRole;
import com.alibaba.alisonar.dto.AuthUserDTO;
import com.alibaba.alisonar.dto.AuthUserSearch;
import com.alibaba.alisonar.dto.DatatableDTO;
import com.alibaba.alisonar.dto.ResultDTO;
import com.alibaba.alisonar.factory.ResultDTOFactory;
import com.alibaba.alisonar.mapstuct.AuthUserConventor;
import com.alibaba.alisonar.service.AuthRoleService;
import com.alibaba.alisonar.service.AuthUserRoleService;
import com.alibaba.alisonar.service.AuthUserService;
import com.alibaba.alisonar.util.ExcelUtil;
import com.alibaba.alisonar.util.MyStringUtil;
import com.alibaba.alisonar.util.PasswordHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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

	@Autowired
	private AuthRoleService authRoleService;

	@Autowired
	private AuthUserRoleService authUserRoleService;

	@Autowired
	private AuthUserConventor authUserConventor;

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
	public DatatableDTO<AuthUserDTO> buildDatatableDto(AuthUserDTO authUserDTO) {
		DatatableDTO<AuthUserDTO> resultDto = new DatatableDTO<AuthUserDTO>();
		PageHelper.startPage(authUserDTO.getOffset() / authUserDTO.getLimit() + 1, authUserDTO.getLimit());
		List<AuthUserDTO> list = authUserMapper.listAuthUser(authUserDTO);
		PageInfo<AuthUserDTO> page = new PageInfo(list);
		resultDto.setRows(page.getList());
		resultDto.setTotal(page.getTotal());
		return resultDto;
	}

	@Override
	public void saveUser(AuthUserDTO authUserDTO) {
		AuthUser authUser = authUserConventor.DTO2entity(authUserDTO);
		String salt = RandomStringUtils.randomAlphabetic(8);
		authUser.setSalt(salt);
		authUser.setPassword(PasswordHelper.encryptPassword("md5", 2, "123456", salt)); // 默认密码123456
		authUserMapper.insertSelective(authUser);
		if (authUserDTO.getRoles() != null) {
			for (String role : authUserDTO.getRoles()) {
				AuthRole authRole = authRoleService.getAuthRoleByRole(role);
				AuthUserRole authUserRole = new AuthUserRole();
				authUserRole.setAuthRoleId(authRole.getId());
				authUserRole.setAuthUserId(authUser.getId());
				authUserRoleService.insertSelective(authUserRole);
			}
		}
	}

	@Override
	public void updateUser(AuthUserDTO authUserDTO) {
		AuthUser authUser = authUserConventor.DTO2entity(authUserDTO);
		logger.info("authUser===>{}", authUser);
		authUserMapper.updateByPrimaryKeySelective(authUser);
		// 删存然后重新插入
		authUserRoleService.deleteByUserId(authUser.getId());
		if (authUserDTO.getRoles() != null) {
			for (String role : authUserDTO.getRoles()) {
				AuthRole authRole = authRoleService.getAuthRoleByRole(role);
				AuthUserRole authUserRole = new AuthUserRole();
				authUserRole.setAuthRoleId(authRole.getId());
				authUserRole.setAuthUserId(authUser.getId());
				authUserRoleService.insertSelective(authUserRole);

			}
		}

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
	public void deleteUser(Long id) {
		authUserMapper.deleteUser(id);

	}

	@Override
	public void resetPassword(Long id) {
		AuthUser authUser = authUserMapper.selectByPrimaryKey(id);
		String password = "123456"; // 默认密码
		password = PasswordHelper.encryptPassword("md5", 2, password, authUser.getSalt());
		authUserMapper.updateUserPassword(id, password);

	}

	@Override
	public Workbook buildExcelWorkBook(AuthUserDTO authUserDTO) {
		List<AuthUserDTO> dtos = authUserMapper.listAuthUser(authUserDTO);
		return ExcelUtil.buildCommonExcel("用户详情", dtos, AuthUserDTO.class);
	}

	@Override
	public Set<String> findRoles(String username) {
		return authUserMapper.findRoles(username);

	}

	@Override
	public Set<String> findPermissions(String username) {
		return authUserMapper.findPermissions(username);

	}

	@Override
	public ResultDTO<String> saveAuthUserByExcel(InputStream inputStream) {
		try {
			List<AuthUserDTO> dtos = ExcelUtil.loadExcelData(AuthUserDTO.class, inputStream);
			logger.info("excel解析后List<AuthUserDTO>===>{}", dtos);
			for(AuthUserDTO dto: dtos){
				saveUser(dto);
			}
			return ResultDTOFactory.toAck(null);
		} catch (Exception e) {
			logger.error("读入流失败：" , e);
			return ResultDTOFactory.toNack(500, "读入流失败");
			
		}
		
	}

}
