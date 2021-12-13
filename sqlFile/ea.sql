/*
 Navicat Premium Data Transfer

 Source Server         : ea
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : rm-bp1v25gip581jpm37oo.mysql.rds.aliyuncs.com:3306
 Source Schema         : ea

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 13/12/2021 21:14:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for algorithm
-- ----------------------------
DROP TABLE IF EXISTS `algorithm`;
CREATE TABLE `algorithm`  (
  `alg_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '算法id',
  `algorithm_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '算法名称',
  `username` int(10) NOT NULL COMMENT '上传者id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '算法时间',
  `classification` int(11) NOT NULL COMMENT '算法种类：1：平滑处理、2：滤波处理、3：CV伏安法、4：DPV、5：SWV、6：LSV ',
  `change_time` timestamp NULL DEFAULT NULL COMMENT '最新修改时间',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '算法文件路径',
  `is_used` tinyint(4) NOT NULL DEFAULT 2 COMMENT '是否启用 1 ：启用 2 ：未启用',
  PRIMARY KEY (`alg_id`) USING BTREE,
  INDEX `fk_algorithm_user_1`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of algorithm
-- ----------------------------
INSERT INTO `algorithm` VALUES (1, '吃饭', 20190002, '2021-11-09 11:38:26', 1, NULL, '测试用的，不存在', 2);
INSERT INTO `algorithm` VALUES (2, 'TestDemo', 20190002, '2021-11-19 20:52:23', 1, NULL, 'D:\\WorkPlace_Code\\IDEA_Code\\Electrochemical-analysis-system\\uploads\\uploadAlgorithms\\TestDemo.java', 1);

-- ----------------------------
-- Table structure for algorithm_send
-- ----------------------------
DROP TABLE IF EXISTS `algorithm_send`;
CREATE TABLE `algorithm_send`  (
  `alg_send_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '算法申请id',
  `alg_id` int(10) NOT NULL COMMENT '新算法id，是指申请时生成的文件算法id',
  `apply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '算法申请时间',
  `username` int(10) NOT NULL COMMENT '申请者账号',
  `classification` int(4) NOT NULL COMMENT '申请类别、0：添加算法、-1：删除算法、>0：修改算法，修改算法的类别值也是被修改的原算法的id值',
  PRIMARY KEY (`alg_send_id`) USING BTREE,
  INDEX `fk_algorithm_send_algorithm_1`(`alg_id`) USING BTREE,
  INDEX `fk_algorithm_send_user_1`(`username`) USING BTREE,
  CONSTRAINT `fk_algorithm_send_algorithm` FOREIGN KEY (`alg_id`) REFERENCES `algorithm` (`alg_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_algorithm_send_algorithm2` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of algorithm_send
-- ----------------------------

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用来标识文件',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录文件名称',
  `url` char(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件所在地址',
  `owner` int(10) NOT NULL COMMENT '文件所属账号',
  `size` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录文件大小',
  `hash` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录文件hash值用于比较文件',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '文件类型:1 用户文件,2，系统备份文件',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '文件状态: 1 正常, 2 被删除',
  `produce_time` timestamp NULL DEFAULT NULL COMMENT '记录此文件产生时间',
  `modified_time` timestamp NULL DEFAULT NULL COMMENT '记录文件最新的处理时间',
  `data_start` double NULL DEFAULT NULL COMMENT '记录文件数据的起始x0',
  `data_end` double NULL DEFAULT NULL COMMENT '记录文件数据的结束x1',
  `data_bottom` double NULL DEFAULT NULL COMMENT '记录文件数据的最低y0',
  `data_peak` double NULL DEFAULT NULL COMMENT '记录文件数据的最高y1',
  `data_precision` double NULL DEFAULT NULL COMMENT '记录文件数据的精度',
  `data_cycle` double NULL DEFAULT NULL COMMENT '记录文件数据实验的循环圈数',
  `data_rate` double NULL DEFAULT NULL COMMENT '记录文件数据实验的速率',
  `data_results` double NULL DEFAULT NULL COMMENT '记录文件数据实验的结果',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_file_user_1`(`owner`) USING BTREE,
  CONSTRAINT `fk_file_user_1` FOREIGN KEY (`owner`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES (88, 'b.txt', '/user/file', 20190003, '100', 'qweqwe12wd', 1, 1, '2021-10-05 19:23:21', '2021-10-10 19:23:26', 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `file` VALUES (91, 'b.txt', '/user/file', 20190003, '100', 'qweqwe12wd', 1, 2, '2021-10-05 19:23:21', '2021-10-10 19:23:26', 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `file` VALUES (92, 'b.txt', '/user/file', 20190003, '100', 'qweqwe12wd', 1, 2, '2021-10-05 19:23:21', '2021-10-10 19:23:26', 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `file` VALUES (94, 'a.txt', '/user/file', 20190003, '100', 'qweqwe12wd', 1, 2, '2021-10-05 19:23:21', '2021-10-10 19:23:26', 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `file` VALUES (98, 'dpv2 30 10-2', '/uploads/files/dpv2 30 10-2.txt', 20190003, '2524Byte', NULL, 1, 1, '2021-11-17 14:49:23', NULL, 0.504, 1, 0.000007216, 0.00001789, 0.0001, NULL, NULL, NULL);
INSERT INTO `file` VALUES (103, '40', '/uploads/files/40.txt', 20190003, '3125Byte', NULL, 1, 1, '2021-11-23 18:43:30', NULL, 0.404, 1, -0.00003298, -0.000007911, 0.00001, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for freeze
-- ----------------------------
DROP TABLE IF EXISTS `freeze`;
CREATE TABLE `freeze`  (
  `freeze_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '冻结的id',
  `username` int(10) NOT NULL COMMENT '用户账号，连接user表的外键',
  `freeze_reason` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '冻结用户的理由',
  `freeze_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '璁板綍鍒涘缓鐨勬椂闂?',
  PRIMARY KEY (`freeze_id`) USING BTREE,
  UNIQUE INDEX `freeze_username_uindex`(`username`) USING BTREE,
  CONSTRAINT `freeze_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of freeze
-- ----------------------------
INSERT INTO `freeze` VALUES (1, 20190002, '攻击系统', '2021-10-24 17:58:32');
INSERT INTO `freeze` VALUES (9, 20190010, '违反规则', '2021-11-04 20:57:47');
INSERT INTO `freeze` VALUES (10, 20190014, '违规', '2021-11-04 21:05:33');
INSERT INTO `freeze` VALUES (11, 20190005, '我想', '2021-11-14 16:00:49');
INSERT INTO `freeze` VALUES (12, 20190004, 'www', '2021-11-14 16:12:56');
INSERT INTO `freeze` VALUES (13, 20210030, '测试', '2021-11-18 08:41:59');
INSERT INTO `freeze` VALUES (14, 20210050, '测试2', '2021-11-18 08:48:16');
INSERT INTO `freeze` VALUES (15, 20190003, 'qweqeqeqwe', '2021-11-19 20:21:19');
INSERT INTO `freeze` VALUES (16, 20190006, '123456', '2021-11-21 10:54:29');

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `message` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '日志标准信息',
  `level` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '日志级别',
  `user` int(10) NOT NULL COMMENT '日志产生者',
  `recorder` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '日志记录器，一般使用类名，便于定位问题所在',
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志产生时间，默认current_timestamp自动记录当前时间',
  `type` tinyint(4) NOT NULL COMMENT '日志类型,0系统日志,3研究员日志,2管理员日志,1超管日志',
  `is_file` int(1) NULL DEFAULT NULL COMMENT '记录是否为文件操作，0非文件操作，1文件操作',
  `boundary` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编号',
  `file_type` int(2) NULL DEFAULT NULL COMMENT '文件操作  1上传 2移除 3还原 4删除 5导出 ',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_1`(`user`) USING BTREE,
  CONSTRAINT `fk_user_1` FOREIGN KEY (`user`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of operation
-- ----------------------------
INSERT INTO `operation` VALUES (3, '冻结用户', 'INFO', 20190002, 'UserService.freeze', '2021-11-14 15:43:50', 2, 0, '1202', 0);
INSERT INTO `operation` VALUES (4, '解冻用户', 'INFO', 20190002, 'UserService.unfreeze', '2021-11-14 15:46:12', 2, 0, '1302', 0);
INSERT INTO `operation` VALUES (5, '添加角色', 'INFO', 20190001, 'RoleService.', '2021-11-14 15:47:19', 1, 0, '1403', 0);
INSERT INTO `operation` VALUES (6, '删除角色', 'NFO', 20190001, 'RoleService.deleteRole', '2021-11-14 15:48:01', 1, 0, '1404', 0);
INSERT INTO `operation` VALUES (18, '添加冻结记录', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.FreezeService.insertFreeze', '2021-11-19 20:21:29', 2, 0, '1201', 0);
INSERT INTO `operation` VALUES (19, '更改用户状态', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.UserService.updateUserStatus', '2021-11-19 20:21:29', 2, 0, '1202', 0);
INSERT INTO `operation` VALUES (20, '添加算法审核申请(*^▽^*)', 'INFO', 20190002, 'com.bluedot.electrochemistry.service.AlgorithmSendService.addAlgorithmSend', '2021-11-19 20:52:43', 2, 0, '0804', 0);
INSERT INTO `operation` VALUES (21, '移除文件', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.FileService.remove', '2021-11-21 10:35:22', 3, 0, '0206', 0);
INSERT INTO `operation` VALUES (22, '还原文件', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.FileService.restore', '2021-11-21 10:36:29', 3, 0, '0210', 0);
INSERT INTO `operation` VALUES (23, '更改用户状态', 'INFO', 20190006, 'com.bluedot.electrochemistry.service.UserService.updateUserStatus', '2021-11-21 10:54:31', 2, 0, '1202', 0);
INSERT INTO `operation` VALUES (24, '移除文件', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.FileService.remove', '2021-11-23 17:46:11', 3, 0, '0206', 0);
INSERT INTO `operation` VALUES (25, 'doSomething', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.FileService.export', '2021-11-23 17:49:03', 3, 0, '0207', 0);
INSERT INTO `operation` VALUES (26, 'doSomething', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.FileService.export', '2021-11-23 17:49:14', 3, 0, '0207', 0);
INSERT INTO `operation` VALUES (27, 'doSomething', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.FileService.export', '2021-11-23 18:44:02', 3, 0, '0207', 0);
INSERT INTO `operation` VALUES (28, '移除文件', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.FileService.remove', '2021-11-23 18:46:44', 3, 0, '0206', 0);
INSERT INTO `operation` VALUES (29, 'doSomething', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.FileService.export', '2021-11-23 18:47:26', 3, 0, '0207', 0);
INSERT INTO `operation` VALUES (30, 'doSomething', 'INFO', 20190003, 'com.bluedot.electrochemistry.service.FileService.export', '2021-11-23 18:51:12', 3, 0, '0207', 0);

-- ----------------------------
-- Table structure for right
-- ----------------------------
DROP TABLE IF EXISTS `right`;
CREATE TABLE `right`  (
  `right_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `right_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限描述',
  `right_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限路径',
  `right_level` int(1) NULL DEFAULT NULL COMMENT '0: 超级管理员 1: 管理员 2:普通用户',
  PRIMARY KEY (`right_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 304 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of right
-- ----------------------------
INSERT INTO `right` VALUES (101, '管理员管理', '对管理员的增删改查', '/', 0);
INSERT INTO `right` VALUES (102, '操作记录管理', '对操作记录的增删改查', '/', 0);
INSERT INTO `right` VALUES (103, '系统备份', '系统备份的相关操作', '/', 0);
INSERT INTO `right` VALUES (104, '系统还原', '系统还原的相关操作', '/', 0);
INSERT INTO `right` VALUES (105, '算法审核', '算法审核的相关操作', '/', 0);
INSERT INTO `right` VALUES (106, '角色管理', '角色的增删改查', '/', 0);
INSERT INTO `right` VALUES (201, '用户管理', '用户的增删改查', '/', 1);
INSERT INTO `right` VALUES (202, '解冻审核', '解冻审核的相关操作', '/', 1);
INSERT INTO `right` VALUES (203, '文件管理(管理员)', '文件的增删改查', '/', 1);
INSERT INTO `right` VALUES (204, '操作记录', '操作记录的增删改查', '/', 1);
INSERT INTO `right` VALUES (205, '算法管理', '算法的增删改查', '/', 1);
INSERT INTO `right` VALUES (301, '电化学分析', '电化学分析的相关操作', '/', 2);
INSERT INTO `right` VALUES (302, '文件管理', '对用户自己提交文件的增删改查', '/', 2);
INSERT INTO `right` VALUES (303, '回收站管理', '对用户个人的回收站的管理', '/', 2);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `gen_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `role_type` int(11) NOT NULL COMMENT '0:管理员；1：普通用户',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 334 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (100, '超级管理员', '2021-11-13 18:50:25', '管理管理员', 0);
INSERT INTO `role` VALUES (200, '管理员', '2021-11-13 18:50:27', '管理普通用户', 1);
INSERT INTO `role` VALUES (300, '普通用户', '2021-11-13 19:13:34', '站点使用者', 2);
INSERT INTO `role` VALUES (331, 'aaa', '2021-11-16 21:10:05', 'aaa', 1);
INSERT INTO `role` VALUES (332, '算法管理员', '2021-11-19 20:40:32', '管理算法', 0);
INSERT INTO `role` VALUES (333, '算法管理员', '2021-11-19 20:40:38', '管理算法', 0);

-- ----------------------------
-- Table structure for role_right
-- ----------------------------
DROP TABLE IF EXISTS `role_right`;
CREATE TABLE `role_right`  (
  `rr_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '记录标识',
  `role_id` int(10) NOT NULL COMMENT '角色ID',
  `right_id` int(10) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`rr_id`) USING BTREE,
  INDEX `a`(`role_id`) USING BTREE,
  INDEX `b`(`right_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of role_right
-- ----------------------------
INSERT INTO `role_right` VALUES (1, 100, 101);
INSERT INTO `role_right` VALUES (2, 100, 102);
INSERT INTO `role_right` VALUES (3, 100, 103);
INSERT INTO `role_right` VALUES (4, 100, 104);
INSERT INTO `role_right` VALUES (5, 100, 105);
INSERT INTO `role_right` VALUES (6, 100, 106);
INSERT INTO `role_right` VALUES (7, 200, 201);
INSERT INTO `role_right` VALUES (9, 200, 203);
INSERT INTO `role_right` VALUES (10, 200, 204);
INSERT INTO `role_right` VALUES (11, 200, 205);
INSERT INTO `role_right` VALUES (12, 300, 301);
INSERT INTO `role_right` VALUES (13, 300, 302);
INSERT INTO `role_right` VALUES (14, 300, 303);
INSERT INTO `role_right` VALUES (15, 200, 202);
INSERT INTO `role_right` VALUES (17, 331, 202);
INSERT INTO `role_right` VALUES (18, 331, 205);
INSERT INTO `role_right` VALUES (19, 331, 303);
INSERT INTO `role_right` VALUES (20, 332, 205);
INSERT INTO `role_right` VALUES (21, 332, 303);
INSERT INTO `role_right` VALUES (22, 332, 302);

-- ----------------------------
-- Table structure for unfreeze
-- ----------------------------
DROP TABLE IF EXISTS `unfreeze`;
CREATE TABLE `unfreeze`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `freeze_id` int(11) NOT NULL COMMENT '冻结表的id，外键',
  `reviewer` int(11) NULL DEFAULT NULL COMMENT '瀹℃牳浜?',
  `username` int(10) NOT NULL COMMENT '用户名',
  `application_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交申请时间',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `handle_status` tinyint(4) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '处理状态  0: 审核中  1: 审核通过 2: 审核未通过审核中代表未处理, 审核通过和审核未通过代表已处理',
  `refuse_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核不通过理由',
  `application_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请理由',
  `gmt_modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '璁板綍淇敼鏃堕棿',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unfreeze_application_freeze_id_uindex`(`freeze_id`) USING BTREE,
  INDEX `unfreeze_user`(`username`) USING BTREE,
  CONSTRAINT `unfreeze_freeze` FOREIGN KEY (`freeze_id`) REFERENCES `freeze` (`freeze_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `unfreeze_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of unfreeze
-- ----------------------------
INSERT INTO `unfreeze` VALUES (8, 12, NULL, 20190004, '2021-11-14 16:13:34', '64098056@qq.com', 0001, NULL, '就申请一下', '2021-11-14 19:53:02');
INSERT INTO `unfreeze` VALUES (9, 13, NULL, 20210030, '2021-11-14 16:20:50', 'asdas3@qq.com', 0001, NULL, '测试', '2021-11-18 08:50:26');
INSERT INTO `unfreeze` VALUES (20, 14, NULL, 20210050, '2021-11-18 08:50:01', 'asda5@qq.com', 0002, '违规', '测试2', '2021-11-18 08:51:32');
INSERT INTO `unfreeze` VALUES (22, 1, NULL, 20190002, '2021-11-19 19:53:09', 'klpjxau@163.com', 0001, NULL, '666666666666666666666', '2021-11-19 20:28:58');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` int(10) NOT NULL COMMENT '用户名',
  `password` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码6-18位',
  `nickname` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称  2-5位',
  `gender` tinyint(4) NULL DEFAULT NULL COMMENT '性别  0: 女 1: 男',
  `age` tinyint(4) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `birth` timestamp NULL DEFAULT NULL COMMENT '出生日期',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '0:被冻结\n1:正常\n2: 被删除',
  `portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gmt_created` timestamp NULL DEFAULT NULL COMMENT '该条记录的创建时间',
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录被修改时间, 数据库自动更新时间',
  PRIMARY KEY (`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (20190001, '123456', '超级管理员', 0, 18, '1234567@163.com', '2021-09-13 21:44:33', 1, 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/avatar/man.jpeg', '2021-09-13 21:44:42', '2021-10-17 19:57:32');
INSERT INTO `user` VALUES (20190002, '123456', '小蓝', 0, 0, 'klpjxau@163.com', '2000-02-01 23:11:19', 1, 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/avatar/man.jpeg', '2021-10-14 23:12:03', '2021-11-03 14:51:38');
INSERT INTO `user` VALUES (20190003, '123456', '研究员', 1, 19, '18357535863@163.com', '2021-10-25 10:10:43', 1, 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/avatar/man.jpeg', '2021-10-25 10:10:58', '2021-10-25 10:10:58');
INSERT INTO `user` VALUES (20190004, '123456', '小美', 0, 16, '6409805612@qq.com', '2021-11-11 16:15:33', 1, 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/avatar/man.jpeg', NULL, '2021-10-29 12:14:36');
INSERT INTO `user` VALUES (20190005, '123456', '管理员3号', 0, 70, 'asdsd@qq.com', NULL, 1, 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/avatar/man.jpeg', NULL, '2021-10-28 18:51:28');
INSERT INTO `user` VALUES (20190006, '123456', '研究员2号', 0, 45, 'adsda1111@qq.com', NULL, 0, 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/avatar/man.jpeg', NULL, '2021-11-12 20:19:51');
INSERT INTO `user` VALUES (20190010, '123456', '1', 1, 15, 'qwwq86ww@qq.com', NULL, 1, 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/avatar/man.jpeg', NULL, '2021-11-03 14:36:59');
INSERT INTO `user` VALUES (20190011, '1234567', '管理员2号', 0, 0, 'asdas@qq.com', NULL, 0, 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/avatar/man.jpeg', NULL, '2021-10-30 16:28:29');
INSERT INTO `user` VALUES (20190012, '1234567', '3', 0, 0, 'jgfjghks@qq.com', NULL, 0, NULL, NULL, '2021-10-30 16:28:29');
INSERT INTO `user` VALUES (20190013, '1234567', '6', 0, 0, 'gafaf@qq.com', NULL, 0, NULL, NULL, '2021-10-30 16:28:40');
INSERT INTO `user` VALUES (20190014, '123456', '4', 0, 0, 'ajfnajsf@qq.com', NULL, 0, NULL, NULL, '2021-10-30 16:28:32');
INSERT INTO `user` VALUES (20190015, '1234567', NULL, 0, 0, 'sadsd@qq.com', NULL, 0, NULL, NULL, '2021-11-16 19:45:57');
INSERT INTO `user` VALUES (20190016, '1234567', NULL, 0, 0, 'asdqwe@qq.com', NULL, 0, NULL, NULL, '2021-11-16 19:48:04');
INSERT INTO `user` VALUES (20190020, '123456', NULL, 0, 0, 'asdfas@qq.com', NULL, 1, NULL, NULL, '2021-11-17 19:42:05');
INSERT INTO `user` VALUES (20190021, '123456', NULL, 0, 0, 'asda@qq.com', NULL, 1, NULL, NULL, '2021-11-17 20:17:33');
INSERT INTO `user` VALUES (20190023, '1234567', NULL, 0, 0, 'iuothnj@qq.com', NULL, 0, NULL, NULL, '2021-11-18 21:47:53');
INSERT INTO `user` VALUES (20190024, '123456', NULL, 0, 0, 'uhfjgh@qq.com', NULL, 1, NULL, NULL, '2021-11-19 20:36:58');
INSERT INTO `user` VALUES (20200001, '123456', 'testfreeze1', 0, 0, NULL, NULL, 1, NULL, '2021-10-22 10:25:07', '2021-10-22 10:57:52');
INSERT INTO `user` VALUES (20210010, '123456', '10', 1, 20, 'asdas1@qq.com', NULL, 1, NULL, NULL, '2021-11-18 08:29:59');
INSERT INTO `user` VALUES (20210020, '123456', '20', 1, 21, 'asdas2@qq.com', NULL, 1, NULL, NULL, '2021-11-18 08:30:37');
INSERT INTO `user` VALUES (20210030, '123456', '305', 1, 14, 'asdas3@qq.com', NULL, 1, NULL, NULL, '2021-11-18 08:31:21');
INSERT INTO `user` VALUES (20210040, '123456', '40', 1, 15, 'asdas4@qq.com', NULL, 1, NULL, NULL, '2021-11-18 08:31:44');
INSERT INTO `user` VALUES (20210050, '123456', '50', 0, 18, 'asdas5@qq.com', NULL, 0, NULL, NULL, '2021-11-18 08:32:01');
INSERT INTO `user` VALUES (20210060, '123456', '60', 1, 19, 'asdas6@qq.com', NULL, 1, NULL, NULL, '2021-11-18 08:32:31');
INSERT INTO `user` VALUES (20210070, '123456', '70', 1, 50, 'asdas7@qq.com', NULL, 1, NULL, NULL, '2021-11-18 08:32:45');
INSERT INTO `user` VALUES (20210080, '123456', '80', 1, 40, 'asdas8@qq.com', NULL, 1, NULL, NULL, '2021-11-18 08:33:17');
INSERT INTO `user` VALUES (20210090, '123456', '90', 0, 40, 'asdas9@qq.com', NULL, 1, NULL, NULL, '2021-11-18 08:33:40');
INSERT INTO `user` VALUES (29525844, 'root123', '小马', 1, 1, '1711489228@qq.com', '2020-10-01 00:00:00', 1, 'https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/mixue.png', '2021-10-22 10:25:07', '2021-10-24 13:28:09');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_role_id` int(10) NOT NULL AUTO_INCREMENT COMMENT ' 记录标识',
  `username` int(10) NOT NULL COMMENT '用户ID',
  `role_id` int(10) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_role_id`) USING BTREE,
  INDEX `fk_tuser_role_user_1`(`username`) USING BTREE,
  INDEX `fk_tuser_role_trole_1`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 20190001, 100);
INSERT INTO `user_role` VALUES (2, 20190002, 200);
INSERT INTO `user_role` VALUES (3, 20190003, 300);
INSERT INTO `user_role` VALUES (4, 20190004, 200);
INSERT INTO `user_role` VALUES (5, 20190005, 200);
INSERT INTO `user_role` VALUES (6, 20190006, 300);
INSERT INTO `user_role` VALUES (7, 20190010, 200);
INSERT INTO `user_role` VALUES (8, 20190011, 200);
INSERT INTO `user_role` VALUES (9, 20190012, 200);
INSERT INTO `user_role` VALUES (15, 20190013, 200);
INSERT INTO `user_role` VALUES (19, 20190015, 200);
INSERT INTO `user_role` VALUES (20, 20190016, 200);
INSERT INTO `user_role` VALUES (21, 20190020, 200);
INSERT INTO `user_role` VALUES (31, 20210010, 300);
INSERT INTO `user_role` VALUES (32, 20210020, 300);
INSERT INTO `user_role` VALUES (33, 20210030, 300);
INSERT INTO `user_role` VALUES (34, 20210040, 300);
INSERT INTO `user_role` VALUES (35, 20210050, 300);
INSERT INTO `user_role` VALUES (36, 20210060, 300);
INSERT INTO `user_role` VALUES (37, 20210070, 300);
INSERT INTO `user_role` VALUES (38, 20210080, 300);
INSERT INTO `user_role` VALUES (39, 20210090, 300);
INSERT INTO `user_role` VALUES (40, 20190023, 200);

-- ----------------------------
-- View structure for algorithm_send_view
-- ----------------------------
DROP VIEW IF EXISTS `algorithm_send_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `algorithm_send_view` AS select `user`.`nickname` AS `sender_nickname`,`algorithm_send`.`classification` AS `send_type`,`algorithm_send`.`apply_time` AS `apply_time`,`algorithm_send`.`alg_send_id` AS `alg_send_id`,`temp001`.`creater_nickname` AS `creater_nickname`,`temp001`.`algorithm_name` AS `algorithm_name`,`temp001`.`alg_create_username` AS `alg_create_username`,`temp001`.`url` AS `url`,`temp001`.`change_time` AS `change_time`,`temp001`.`created_time` AS `created_time`,`temp001`.`alg_id2` AS `alg_id` from ((`algorithm_send` join `user` on((`algorithm_send`.`username` = `user`.`username`))) join (select `user`.`nickname` AS `creater_nickname`,`algorithm`.`algorithm_name` AS `algorithm_name`,`algorithm`.`username` AS `alg_create_username`,`algorithm`.`url` AS `url`,`algorithm`.`change_time` AS `change_time`,`algorithm`.`created_time` AS `created_time`,`algorithm`.`alg_id` AS `alg_id2` from (`algorithm` join `user` on((`user`.`username` = `algorithm`.`username`)))) `temp001` on((`algorithm_send`.`alg_id` = `temp001`.`alg_id2`)));

-- ----------------------------
-- View structure for algorithm_view
-- ----------------------------
DROP VIEW IF EXISTS `algorithm_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `algorithm_view` AS select `user`.`nickname` AS `nickname`,`algorithm`.`algorithm_name` AS `algorithm_name`,`algorithm`.`url` AS `url`,`algorithm`.`created_time` AS `created_time`,`algorithm`.`classification` AS `classification`,`algorithm`.`change_time` AS `change_time`,`algorithm`.`is_used` AS `is_used`,`algorithm`.`alg_id` AS `alg_id` from (`user` join `algorithm` on((`user`.`username` = `algorithm`.`username`)));

-- ----------------------------
-- View structure for file_detail
-- ----------------------------
DROP VIEW IF EXISTS `file_detail`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `file_detail` AS select `file`.`id` AS `id`,`file`.`name` AS `name`,`file`.`size` AS `size`,`file`.`owner` AS `owner`,`file`.`status` AS `status`,`file`.`produce_time` AS `produce_time`,`file`.`modified_time` AS `modified_time`,`file`.`data_start` AS `data_start`,`file`.`data_end` AS `data_end`,`file`.`data_bottom` AS `data_bottom`,`file`.`data_peak` AS `data_peak`,`file`.`data_precision` AS `data_precision`,`user`.`nickname` AS `nickname` from (`file` join `user` on((`file`.`owner` = `user`.`username`)));

-- ----------------------------
-- View structure for operation_sadmin_view
-- ----------------------------
DROP VIEW IF EXISTS `operation_sadmin_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `operation_sadmin_view` AS select `operation`.`id` AS `id`,`operation`.`message` AS `message`,`operation`.`user` AS `user`,`operation`.`type` AS `type`,`operation`.`time` AS `time`,`user`.`nickname` AS `nickname` from (`operation` join `user` on((`operation`.`user` = `user`.`username`)));

-- ----------------------------
-- View structure for user_freeze_application
-- ----------------------------
DROP VIEW IF EXISTS `user_freeze_application`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_freeze_application` AS select `freeze`.`username` AS `username`,`user`.`portrait` AS `portrait`,`user`.`nickname` AS `nickname`,`user`.`email` AS `email`,`unfreeze`.`application_reason` AS `application_reason`,`unfreeze`.`application_time` AS `application_time`,`freeze`.`freeze_reason` AS `freeze_reason`,`freeze`.`freeze_time` AS `freeze_time` from ((`freeze` join `user` on((`freeze`.`username` = `user`.`username`))) join `unfreeze` on((`freeze`.`freeze_id` = `unfreeze`.`freeze_id`)));

-- ----------------------------
-- View structure for user_right_view
-- ----------------------------
DROP VIEW IF EXISTS `user_right_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_right_view` AS select `right`.`right_name` AS `right_name`,`user`.`username` AS `username` from ((((`user` join `user_role` on((`user`.`username` = `user_role`.`username`))) join `role` on((`user_role`.`tr_id` = `role`.`role_id`))) join `role_right` on((`role`.`role_id` = `role_right`.`role_id`))) join `right` on((`role_right`.`right_id` = `right`.`right_id`))) where ((`user`.`username` = `user_role`.`username`) and (`user_role`.`tr_id` = `role`.`role_id`) and (`role`.`role_id` = `role_right`.`role_id`) and (`role_right`.`right_id` = `right`.`right_id`));

-- ----------------------------
-- View structure for user_unfreeze
-- ----------------------------
DROP VIEW IF EXISTS `user_unfreeze`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_unfreeze` AS select `freeze`.`username` AS `username`,`user`.`portrait` AS `portrait`,`user`.`nickname` AS `nickname`,`user`.`email` AS `email`,`unfreeze`.`application_reason` AS `application_reason`,`unfreeze`.`application_time` AS `application_time`,`freeze`.`freeze_reason` AS `freeze_reason`,`freeze`.`freeze_time` AS `freeze_time` from ((`freeze` join `user` on((`freeze`.`username` = `user`.`username`))) join `unfreeze` on((`freeze`.`freeze_id` = `unfreeze`.`freeze_id`)));

SET FOREIGN_KEY_CHECKS = 1;
