-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.36-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 sonar.auth_permission 结构
CREATE TABLE IF NOT EXISTS `auth_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父节点(0表示无父节点)',
  `permission` varchar(50) NOT NULL COMMENT '权限标识',
  `perm_url` varchar(200) DEFAULT NULL COMMENT '权限url',
  `level` tinyint(4) DEFAULT NULL COMMENT '等级',
  `order_num` tinyint(4) DEFAULT NULL COMMENT '菜单排序',
  `menu_icon` varchar(50) DEFAULT NULL COMMENT '菜单icon',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  `is_active` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未启用 1:启用',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未删除 1:删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- 正在导出表  sonar.auth_permission 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `auth_permission` DISABLE KEYS */;
INSERT INTO `auth_permission` (`id`, `parent_id`, `permission`, `perm_url`, `level`, `order_num`, `menu_icon`, `description`, `is_active`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES
	(1, 0, 'M_AUTH', '', 1, NULL, NULL, '用户权限管理模块', 0, 0, '2018-01-11 15:10:04', '2018-01-11 15:11:32'),
	(2, 1, 'Permission', '/permission/', 2, NULL, NULL, '权限管理', 0, 0, '2018-01-11 15:11:13', '2018-01-11 15:11:13'),
	(3, 1, 'Role', '/role/', 2, NULL, NULL, '角色管理', 0, 0, '2018-01-11 15:11:58', '2018-01-11 15:11:58'),
	(4, 1, 'User', '/user/', 2, NULL, NULL, '用户管理', 0, 0, '2018-01-11 15:12:47', '2018-01-11 15:12:47'),
	(9, 2, 'permission.add', '/permission/savePermission', 3, NULL, NULL, '权限添加', 0, 0, '2018-01-11 16:14:06', '2018-01-11 17:20:31'),
	(10, 2, 'permission.update', '/permission/updatePermission', 3, NULL, NULL, '权限更新', 0, 1, '2018-01-11 16:14:35', '2018-01-11 16:34:09'),
	(11, 2, 'permission:update', '/permission/updatePermission', 3, NULL, NULL, '权限更新', 0, 0, '2018-01-11 16:35:55', '2018-01-11 16:35:55'),
	(12, 2, 'permission.delete', '/permission/deletePermission', 3, NULL, 'fa fa-circle', '权限删除', 0, 0, '2018-01-15 10:34:43', '2018-01-15 13:57:50'),
	(16, 2, 'permission.list', '/permission/listAuthPermission', 3, NULL, 'fa fa-circle', '权限查询', 0, 0, '2018-01-15 13:56:13', '2018-01-15 13:57:54'),
	(17, 2, 'permission.hehe', '/permission/hehe', 3, 1, 'fa fa-circle', 'dddd', 0, 1, '2018-01-15 14:02:12', '2018-01-15 14:02:32');
/*!40000 ALTER TABLE `auth_permission` ENABLE KEYS */;

-- 导出  表 sonar.auth_role 结构
CREATE TABLE IF NOT EXISTS `auth_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role` varchar(50) NOT NULL DEFAULT '0' COMMENT '角色标识',
  `description` varchar(50) DEFAULT '0' COMMENT '描述',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未删除 1:删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 正在导出表  sonar.auth_role 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `auth_role` DISABLE KEYS */;
INSERT INTO `auth_role` (`id`, `role`, `description`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES
	(1, 'admin', '管理员', 0, '2018-01-11 15:17:57', '2018-01-11 15:17:57'),
	(2, 'user', '一般用户', 0, '2018-01-15 16:19:58', '2018-01-15 16:19:58');
/*!40000 ALTER TABLE `auth_role` ENABLE KEYS */;

-- 导出  表 sonar.auth_role_permission 结构
CREATE TABLE IF NOT EXISTS `auth_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `auth_role_id` bigint(20) NOT NULL COMMENT '关联auth_role的主键',
  `auth_permission_id` bigint(20) NOT NULL COMMENT '关联auth_permission的主键',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='用户角色中间表';

-- 正在导出表  sonar.auth_role_permission 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `auth_role_permission` DISABLE KEYS */;
INSERT INTO `auth_role_permission` (`id`, `auth_role_id`, `auth_permission_id`, `gmt_create`, `gmt_modified`) VALUES
	(21, 1, 1, '2018-01-15 17:52:33', '2018-01-15 17:52:33'),
	(22, 1, 2, '2018-01-15 17:52:33', '2018-01-15 17:52:33'),
	(23, 1, 9, '2018-01-15 17:52:33', '2018-01-15 17:52:33'),
	(24, 1, 11, '2018-01-15 17:52:33', '2018-01-15 17:52:33'),
	(25, 1, 12, '2018-01-15 17:52:33', '2018-01-15 17:52:33'),
	(26, 1, 16, '2018-01-15 17:52:33', '2018-01-15 17:52:33'),
	(27, 1, 3, '2018-01-15 17:52:33', '2018-01-15 17:52:33'),
	(28, 1, 4, '2018-01-15 17:52:33', '2018-01-15 17:52:33');
/*!40000 ALTER TABLE `auth_role_permission` ENABLE KEYS */;

-- 导出  表 sonar.auth_user 结构
CREATE TABLE IF NOT EXISTS `auth_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(12) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名(唯一)',
  `password` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户密码',
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户邮箱',
  `salt` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '加密盐值',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '标志位，0:未删除 1已删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='后台用户表';

-- 正在导出表  sonar.auth_user 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` (`id`, `username`, `password`, `email`, `salt`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES
	(1, 'admin', '928bfd2577490322a6e19b793691467e', 'admin@163.com', 'admin', 0, '2017-06-13 13:28:38', '2018-01-05 11:25:31');
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;

-- 导出  表 sonar.auth_user_role 结构
CREATE TABLE IF NOT EXISTS `auth_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `auth_user_id` bigint(20) NOT NULL COMMENT '关联auth_user的主键',
  `auth_role_id` bigint(20) NOT NULL COMMENT '关联auth_role的主键',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_id_auth_role_id` (`auth_user_id`,`auth_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色中间表';

-- 正在导出表  sonar.auth_user_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `auth_user_role` DISABLE KEYS */;
INSERT INTO `auth_user_role` (`id`, `auth_user_id`, `auth_role_id`, `gmt_create`, `gmt_modified`) VALUES
	(1, 1, 1, '2018-01-11 15:18:12', '2018-01-11 15:18:12');
/*!40000 ALTER TABLE `auth_user_role` ENABLE KEYS */;

-- 导出  表 sonar.department 结构
CREATE TABLE IF NOT EXISTS `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_name` varchar(50) NOT NULL COMMENT '部门名称',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='部门';

-- 正在导出表  sonar.department 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`id`, `dept_name`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES
	(1, '测试部门', 0, '2017-06-13 13:27:27', '2017-06-13 13:27:27'),
	(2, '开发部门', 0, '2017-06-13 13:27:39', '2017-06-13 13:27:39');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;

-- 导出  表 sonar.dict_data 结构
CREATE TABLE IF NOT EXISTS `dict_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(50) NOT NULL COMMENT '字典类型编码',
  `value` varchar(250) NOT NULL COMMENT '值',
  `seq` tinyint(4) NOT NULL COMMENT '序号',
  `is_deleted` tinyint(4) NOT NULL COMMENT '0:启用 1:禁用',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公共字典表';

-- 正在导出表  sonar.dict_data 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `dict_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_data` ENABLE KEYS */;

-- 导出  表 sonar.dict_type 结构
CREATE TABLE IF NOT EXISTS `dict_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(50) NOT NULL COMMENT '字典类型编号',
  `name` varchar(100) NOT NULL COMMENT '字典类型名称',
  `is_deleted` tinyint(4) NOT NULL COMMENT '0:启用 1::禁用',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型';

-- 正在导出表  sonar.dict_type 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `dict_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_type` ENABLE KEYS */;

-- 导出  表 sonar.employee 结构
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_id` int(11) NOT NULL COMMENT '逻辑外键（部门id）',
  `emp_name` varchar(50) NOT NULL COMMENT '员工名称',
  `gender` char(1) NOT NULL COMMENT '性别',
  `email` varchar(255) NOT NULL COMMENT '邮件',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0 ; 1)',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='员工表';

-- 正在导出表  sonar.employee 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`, `dept_id`, `emp_name`, `gender`, `email`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES
	(1, 2, 'zzzz', 'M', 'zzzz@163.com', 0, '2017-06-13 13:43:49', '2017-06-13 13:43:49');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
