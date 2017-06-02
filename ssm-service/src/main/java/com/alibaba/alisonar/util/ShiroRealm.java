package com.alibaba.alisonar.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.alisonar.dao.AuthUserMapper;
import com.alibaba.alisonar.domain.AuthUser;


/**
 * @author wb-zxx263018
 *
 */
public class ShiroRealm extends AuthorizingRealm {

	Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

	@Autowired
	private AuthUserMapper userMapper;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		AuthUser userInfo = userMapper.findByUsername(username);
		logger.info("登录用户userinfo:{}",userInfo);
		if (userInfo == null) {
			throw new UnknownAccountException("该用户不存在!");
		}
		Object principal = username; //数据库中user的用户名
		Object credentials = userInfo.getPassword(); //数据库中user的密码
		String realmName = getName();
		ByteSource credentialsSalt = ByteSource.Util.bytes(userInfo.getSalt());//盐值

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt,
				realmName);

		return info;
	}

	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("18868801131");
		int hashIterations = 2;

		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

	//访问授权页面会出发执行。
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Object principal = principals.getPrimaryPrincipal();

		Set<String> roles = new HashSet<>();
		roles.add("user");
		if ("admin".equals(principal)) {
			roles.add("admin");
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

		return info;
	}
}
