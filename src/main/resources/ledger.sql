/*
 Navicat Premium Data Transfer

 Source Server         : 本机电脑
 Source Server Type    : MySQL
 Source Server Version : 50553
 Source Host           : localhost:3306
 Source Schema         : ledger

 Target Server Type    : MySQL
 Target Server Version : 50553
 File Encoding         : 65001

 Date: 08/01/2020 21:05:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `category_id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '品类ID',
  `category_type` tinyint(1) NOT NULL COMMENT '品类类型（支出收入）',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '品类名',
  PRIMARY KEY (`category_id`) USING BTREE,
  INDEX `category_type_inde`(`category_type`) USING BTREE COMMENT '类型索引'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_gender` tinyint(1) NULL DEFAULT NULL COMMENT '用户性别',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `user_password` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `user_real_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户网名',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name_unique`(`user_name`) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
