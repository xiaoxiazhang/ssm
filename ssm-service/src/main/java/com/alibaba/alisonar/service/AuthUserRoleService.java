package com.alibaba.alisonar.service;

import com.alibaba.alisonar.domain.AuthUserRole;

public interface AuthUserRoleService extends BaseService<AuthUserRole> {

	void deleteByUserId(Long id);


}
