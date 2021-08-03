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

 Date: 29/07/2021 18:10:00
*/

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
INSERT INTO `blog` VALUES (2, 'test 1', 'test 1', '1');
INSERT INTO `blog` VALUES (3, 'test 2', 'test 2', '1');
INSERT INTO `blog` VALUES (4, 'test 4', 'test 4', '2');
INSERT INTO `blog` VALUES (5, 'test 5', 'test 5', '2');
INSERT INTO `blog` VALUES (6, 'test 6', 'test 6', '1');
INSERT INTO `blog` VALUES (11, '23232', '3123123', '3');
INSERT INTO `blog` VALUES (12, '12', '12', '3');
INSERT INTO `blog` VALUES (13, '13', '13', '3');
INSERT INTO `blog` VALUES (14, '14', '14', '3');
INSERT INTO `blog` VALUES (15, '15', '15', '3');
INSERT INTO `blog` VALUES (16, '16', '16', '3');
