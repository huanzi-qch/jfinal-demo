/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : jfinal_demo

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 08/09/2023 10:34:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '博客id',
     `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客标题',
     `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '博客内容',
     `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (2, 'test 111', 'test 1', '1');
INSERT INTO `blog` VALUES (3, 'test 22', 'test 2', '1');
INSERT INTO `blog` VALUES (4, 'test 44', 'test 4', '2');
INSERT INTO `blog` VALUES (5, 'test 5', 'test 5', '2');
INSERT INTO `blog` VALUES (6, 'test 6', 'test 6', '1');
INSERT INTO `blog` VALUES (11, '11', '11长大的路径发送的飞', '3');
INSERT INTO `blog` VALUES (12, '12', '12', '3');
INSERT INTO `blog` VALUES (13, '13', '13', '3');
INSERT INTO `blog` VALUES (14, '14', '14', '3');
INSERT INTO `blog` VALUES (15, '15', '15', '3');
INSERT INTO `blog` VALUES (16, '16', '16xxxxxxx', '3');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
     `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
     `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
     PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '张三');
INSERT INTO `user` VALUES ('2', '李四');
INSERT INTO `user` VALUES ('3', '王五');
INSERT INTO `user` VALUES ('4', '王麻子子');


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
     `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表id',
     `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
     `role_menu` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '角色菜单可视权限（可以不关联菜单，单独做成菜单管理直接与用户关联）',
     `role_url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '角色URL访问权限',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', '[{\"menuName\":\"系统管理\",\"menuPath\":\"/sys/xtgl\"},{\"menuName\":\"用户管理\",\"menuPath\":\"/sys/yhgl\"},{\"menuName\":\"网站门户管理\",\"menuPath\":\"/portal/mhgl\"}]', '/sys/*,/portal/mhgl,/getLoginUser');
INSERT INTO `sys_role` VALUES ('2', '部门领导', '[{\"menuName\":\"用户管理\",\"menuPath\":\"/sys/yhgl\"},{\"menuName\":\"网站门户管理\",\"menuPath\":\"/portal/mhgl\"}]', '/sys/yhgl,/portal/mhgl,/getLoginUser');
INSERT INTO `sys_role` VALUES ('3', '普通员工', '[{\"menuName\":\"网站门户管理\",\"menuPath\":\"/portal/mhgl\"}]', '/portal/mhgl,/getLoginUser');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
     `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表id',
     `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
     `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
     `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '系统管理员', 'admin', '000000');
INSERT INTO `sys_user` VALUES ('2', '张三-部门经理', 'zhangsan', '111111');
INSERT INTO `sys_user` VALUES ('3', '小芳-前台接待', 'xiaofang', '222222');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
      `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表id',
      `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
      `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户-角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '1', '2');
INSERT INTO `sys_user_role` VALUES ('3', '1', '3');
INSERT INTO `sys_user_role` VALUES ('4', '2', '2');
INSERT INTO `sys_user_role` VALUES ('5', '3', '3');

SET FOREIGN_KEY_CHECKS = 1;
