/*
Navicat MySQL Data Transfer

Source Server         : local_sports
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : sports

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-01-13 13:01:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `password_salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '随机密钥',
  `rights` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权利',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别0男1女',
  `age` int(3) NOT NULL DEFAULT '0' COMMENT '年龄',
  `department` int(11) NOT NULL DEFAULT '0' COMMENT '部门',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态0锁定1正常-1删除',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '头像',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` int(11) DEFAULT '0',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_account` int(11) DEFAULT '0',
  `is_admin` tinyint(1) DEFAULT '0' COMMENT '0非系统管理员1系统管理员',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='管理员用户';

-- ----------------------------
-- Table structure for app_attachments
-- ----------------------------
DROP TABLE IF EXISTS `app_attachments`;
CREATE TABLE `app_attachments` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `fileid` bigint(20) NOT NULL COMMENT '文件ID',
  `oldfilename` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '上传文件名',
  `filename` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '新文件名',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '路径',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '类型',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标题',
  `desc` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '描述',
  `filetype` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '后缀',
  `filesize` int(11) NOT NULL DEFAULT '0' COMMENT '大小',
  `isimage` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否是图片',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fileid` (`fileid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件表';

-- ----------------------------
-- Table structure for app_city
-- ----------------------------
DROP TABLE IF EXISTS `app_city`;
CREATE TABLE `app_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  `layer` tinyint(4) NOT NULL DEFAULT '0' COMMENT '级数',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '名称',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否删除【1：不删除，0：删除】',
  `sort_id` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `zipcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '区号',
  `lcode` int(10) NOT NULL DEFAULT '0' COMMENT '行政编号',
  `pinyin` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `param_setting` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '城市相关参数设置',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='城市表';

-- ----------------------------
-- Table structure for app_config
-- ----------------------------
DROP TABLE IF EXISTS `app_config`;
CREATE TABLE `app_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '值',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `content` text NOT NULL COMMENT '配置内容描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='配置信息表';

-- ----------------------------
-- Table structure for app_emoji
-- ----------------------------
DROP TABLE IF EXISTS `app_emoji`;
CREATE TABLE `app_emoji` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL,
  `file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '表情包文件',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '预览图',
  `download` int(11) NOT NULL COMMENT '使用次数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='APP表情包';

-- ----------------------------
-- Table structure for app_news
-- ----------------------------
DROP TABLE IF EXISTS `app_news`;
CREATE TABLE `app_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cityid` int(11) NOT NULL DEFAULT '0' COMMENT '城市ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标题',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '内容类型',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '首图',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '视频地址',
  `read_cnt` int(11) NOT NULL DEFAULT '0',
  `content` text CHARACTER SET utf8 NOT NULL COMMENT '内容',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1启用  0禁用   -1删除',
  `sort_id` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COMMENT='新闻公告';

-- ----------------------------
-- Table structure for app_news_type
-- ----------------------------
DROP TABLE IF EXISTS `app_news_type`;
CREATE TABLE `app_news_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '分类名称',
  `pid` int(11) NOT NULL DEFAULT '1' COMMENT '上级id',
  `level` tinyint(4) NOT NULL DEFAULT '1' COMMENT '层级',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '首图',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '描述',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1启用  0禁用   -1删除',
  `sort_id` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='新闻分类';

-- ----------------------------
-- Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` mediumint(8) NOT NULL DEFAULT '10' COMMENT '版本号',
  `forceupdate` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否强制更新',
  `filemd5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件的MD5码大写',
  `platform` enum('android','ios') CHARACTER SET utf8 NOT NULL DEFAULT 'android',
  `filename` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件id',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `url` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='APP版本信息';

-- ----------------------------
-- Table structure for area_number
-- ----------------------------
DROP TABLE IF EXISTS `area_number`;
CREATE TABLE `area_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL COMMENT '区划代码',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '区划名称',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0无效1有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '上级行政区划代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3231 DEFAULT CHARSET=utf8mb4 COMMENT='行政区划代码';

-- ----------------------------
-- Table structure for catalog_menu
-- ----------------------------
DROP TABLE IF EXISTS `catalog_menu`;
CREATE TABLE `catalog_menu` (
  `menuid` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parentid` int(11) NOT NULL DEFAULT '0' COMMENT '父菜单id',
  `menuname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
  `sortid` int(11) NOT NULL COMMENT '排序id',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '菜单状态1有效0无效',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '菜单连接地址',
  `icon` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `type` tinyint(4) NOT NULL COMMENT '菜单类型',
  PRIMARY KEY (`menuid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COMMENT='目录菜单';

-- ----------------------------
-- Table structure for catalog_type
-- ----------------------------
DROP TABLE IF EXISTS `catalog_type`;
CREATE TABLE `catalog_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `order_id` tinyint(1) NOT NULL COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `gid` int(11) NOT NULL AUTO_INCREMENT COMMENT '跑团ID',
  `type` smallint(11) NOT NULL DEFAULT '0' COMMENT '跑团类型',
  `pid` int(11) DEFAULT '0' COMMENT '上级跑团ID',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '团状态,0审核1正常-1关闭-2禁用',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '跑团名称',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '跑团介绍',
  `number` int(11) NOT NULL DEFAULT '0' COMMENT '跑团人数',
  `regtype` enum('direct','apply','table') CHARACTER SET utf8 NOT NULL DEFAULT 'apply' COMMENT 'direct直接加入apply审核通过table填写表格',
  `hotnum` int(11) NOT NULL DEFAULT '0' COMMENT '活跃度',
  `sortid` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '排序ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '头像',
  `ismain` smallint(11) NOT NULL DEFAULT '0' COMMENT '0 不是大群 1大群',
  `qrcode` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `istemp` smallint(11) NOT NULL DEFAULT '0' COMMENT '0非临时群1临时群',
  `condition` varchar(2014) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '入群条件',
  `thumbnail` varchar(128) NOT NULL DEFAULT '' COMMENT '头像缩略图',
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8mb4 COMMENT='跑团信息表';

-- ----------------------------
-- Table structure for group_app
-- ----------------------------
DROP TABLE IF EXISTS `group_app`;
CREATE TABLE `group_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '应用功能名称',
  `appname` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '应用标示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跑团功能表';

-- ----------------------------
-- Table structure for group_apply
-- ----------------------------
DROP TABLE IF EXISTS `group_apply`;
CREATE TABLE `group_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT '跑团id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `type` enum('input','select','mutiselect') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'input' COMMENT 'input输入框select单选框mutiselect多选',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跑团申请表格';

-- ----------------------------
-- Table structure for group_apply_user
-- ----------------------------
DROP TABLE IF EXISTS `group_apply_user`;
CREATE TABLE `group_apply_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT '跑团id',
  `uid` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '申请的内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='跑团用户申请';

-- ----------------------------
-- Table structure for group_app_config
-- ----------------------------
DROP TABLE IF EXISTS `group_app_config`;
CREATE TABLE `group_app_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT '跑团id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1启用-1禁用',
  `appid` int(11) NOT NULL COMMENT '应用id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '配置内容',
  `sortid` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '排序ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跑团功能应用配置';

-- ----------------------------
-- Table structure for group_card_sign
-- ----------------------------
DROP TABLE IF EXISTS `group_card_sign`;
CREATE TABLE `group_card_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_id` int(11) NOT NULL COMMENT '起始卡号',
  `end_id` int(11) NOT NULL COMMENT '结束卡号',
  `totalmiles` decimal(13,2) NOT NULL COMMENT '上一张卡群的跑步量',
  `sign_uid` int(11) NOT NULL COMMENT '开卡人id',
  `curr_signid` int(11) NOT NULL COMMENT '当前打卡id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '卡状态0无效 1有效',
  `gid` int(11) NOT NULL COMMENT '开卡的群id',
  `header` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '卡头',
  `footer` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '卡尾',
  `start_time` bigint(15) NOT NULL COMMENT '开始时间',
  `end_time` bigint(15) NOT NULL COMMENT '结束时间',
  `deadline` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '截止时间',
  `sign_list` text COLLATE utf8mb4_unicode_ci COMMENT '上一次打卡人数排名统计',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='开卡';

-- ----------------------------
-- Table structure for group_level
-- ----------------------------
DROP TABLE IF EXISTS `group_level`;
CREATE TABLE `group_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT '跑团id',
  `level` smallint(6) NOT NULL COMMENT '999固定为管理员1固定为普通用户0为游客',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '等级名称',
  `levelrule` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `content` text CHARACTER SET utf8 NOT NULL COMMENT '等级规则',
  `sortid` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '排序ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跑团等级以及权限表';

-- ----------------------------
-- Table structure for group_pace
-- ----------------------------
DROP TABLE IF EXISTS `group_pace`;
CREATE TABLE `group_pace` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL COMMENT '群id',
  `min_pace` int(11) NOT NULL DEFAULT '0' COMMENT '最低配速',
  `type` smallint(4) NOT NULL DEFAULT '0' COMMENT '跑步类型',
  `min_distance` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '最短距离',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='群配速表';

-- ----------------------------
-- Table structure for group_photos
-- ----------------------------
DROP TABLE IF EXISTS `group_photos`;
CREATE TABLE `group_photos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT '跑团id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '相册名',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '图片',
  `picnums` smallint(6) NOT NULL DEFAULT '0' COMMENT '相册图片数',
  `sortid` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '排序ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跑团相册';

-- ----------------------------
-- Table structure for group_stat
-- ----------------------------
DROP TABLE IF EXISTS `group_stat`;
CREATE TABLE `group_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL COMMENT '跑团ID',
  `number` int(11) NOT NULL DEFAULT '0' COMMENT '跑团人数',
  `topic` int(11) NOT NULL DEFAULT '0' COMMENT '话题数',
  `qunnumber` int(11) NOT NULL DEFAULT '0' COMMENT '聊天群数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跑团统计数据';

-- ----------------------------
-- Table structure for group_statistics
-- ----------------------------
DROP TABLE IF EXISTS `group_statistics`;
CREATE TABLE `group_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `gid` int(11) NOT NULL COMMENT '打卡的群id',
  `runmiles` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '周跑量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '统计开始时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '统计结束时间',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '统计状态0周统计中，1周统计完成',
  `type` tinyint(4) NOT NULL COMMENT '1：周统计 2：月统计 3：年统计',
  `sid` varchar(50) NOT NULL COMMENT '统计id',
  `period` bigint(15) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COMMENT='打卡周统计';

-- ----------------------------
-- Table structure for group_tags
-- ----------------------------
DROP TABLE IF EXISTS `group_tags`;
CREATE TABLE `group_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT '跑团ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跑团标签';

-- ----------------------------
-- Table structure for group_topic
-- ----------------------------
DROP TABLE IF EXISTS `group_topic`;
CREATE TABLE `group_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL COMMENT '跑团id',
  `type` enum('notice','topic','event') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'topic' COMMENT 'notice公告topic话题event赛事',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '内容',
  `istop` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否置顶',
  `likes` mediumint(8) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `shared` mediumint(8) NOT NULL DEFAULT '0' COMMENT '转帖、分享数',
  `sortid` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `sortid` (`sortid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='跑团内容表，话题、公告、赛事';

-- ----------------------------
-- Table structure for group_type
-- ----------------------------
DROP TABLE IF EXISTS `group_type`;
CREATE TABLE `group_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '跑团类型Id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '跑团类型名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态0未启用 1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='跑团类型表';

-- ----------------------------
-- Table structure for group_user
-- ----------------------------
DROP TABLE IF EXISTS `group_user`;
CREATE TABLE `group_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT '跑团id',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '跑团昵称',
  `uid` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态，0审核1正常-1禁用',
  `level` smallint(6) NOT NULL DEFAULT '0' COMMENT '等级，管理员等级默认为999',
  `intro` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '自我介绍',
  `points` int(11) NOT NULL DEFAULT '0' COMMENT '跑团积分',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `root` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户权限0普通用户 1超级用户 2管理员',
  `open` tinyint(4) NOT NULL DEFAULT '0' COMMENT '开卡权限 0 无 1 有',
  `realname` varchar(20) NOT NULL DEFAULT '' COMMENT '打卡时的真实姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=358 DEFAULT CHARSET=utf8mb4 COMMENT='跑团用户表';

-- ----------------------------
-- Table structure for group_user_sign
-- ----------------------------
DROP TABLE IF EXISTS `group_user_sign`;
CREATE TABLE `group_user_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT '跑团id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `targetmiles` int(11) NOT NULL COMMENT '目标路程',
  `nowmiles` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '当前路程',
  `lastdate` bigint(15) NOT NULL DEFAULT '0' COMMENT '最后打卡时间 年月日',
  `resetdate` tinyint(4) NOT NULL DEFAULT '1' COMMENT '每月重置时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='跑团用户打卡设置';

-- ----------------------------
-- Table structure for group_user_sign_log
-- ----------------------------
DROP TABLE IF EXISTS `group_user_sign_log`;
CREATE TABLE `group_user_sign_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT '跑团id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `runmiles` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '本次跑量',
  `pic` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '截图',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `card_id` int(11) NOT NULL COMMENT '母卡id',
  `sign_id` int(11) NOT NULL COMMENT '打卡id',
  `type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '跑步类型',
  `period` bigint(15) NOT NULL DEFAULT '0' COMMENT '跑步时长',
  `altitude` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '海拔高度',
  `thumbnail` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '跑步轨迹缩略图',
  `nowmiles` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '打卡时的月跑量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='跑团用户打卡记录';

-- ----------------------------
-- Table structure for history_msg
-- ----------------------------
DROP TABLE IF EXISTS `history_msg`;
CREATE TABLE `history_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'app key',
  `form_user_id` int(11) NOT NULL COMMENT '发送者id',
  `to_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '接受者id',
  `targettype` smallint(4) NOT NULL COMMENT '会话类型，二人会话是 1 、讨论组会话是 2 、群组会话是 3 、聊天室会话是 4 、客服会话是 5 、 系统通知是 6 、应用公众服务是 7 、公众服务是 8。targetType',
  `group_id` int(11) NOT NULL DEFAULT '0' COMMENT '根据不同的 targetType，可能是讨论组 Id、群组 Id 或聊天室 Id ，如 targetType 为 1 时可忽略 GroupId',
  `classname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '消息类型：文本消息 RC:TxtMsg 、 图片消息 RC:ImgMsg 、语音消息 RC:VcMsg 、图文消息 RC:ImgTextMsg 、位置消息 RC:LBSMsg 、添加联系人消息 RC:ContactNtf 、提示条通知消息 RC:InfoNtf 、资料通知消息 RC:ProfileNtf 、通用命令通知消息 RC:CmdNtf 、客服握手消息 RC:HsMsg 、客服挂断消息 RC:SpMsg',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '消息内容',
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '消息时间',
  `msg_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '消息唯一码',
  `img_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '图片地址',
  `voice_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '声音地址',
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标题',
  `latitude` decimal(8,4) NOT NULL DEFAULT '0.0000' COMMENT '纬度值',
  `longitude` decimal(8,4) NOT NULL DEFAULT '0.0000' COMMENT '纬度值',
  `poi` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '兴趣点',
  `file_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '文件名称',
  `file_size` int(11) NOT NULL DEFAULT '0' COMMENT '文件大小',
  `file_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '文件类型',
  `file_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '文件地址',
  `turn_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '跳转地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=272 DEFAULT CHARSET=utf8mb4 COMMENT='历史消息';

-- ----------------------------
-- Table structure for hongbao
-- ----------------------------
DROP TABLE IF EXISTS `hongbao`;
CREATE TABLE `hongbao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '红包编码',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态-1：过期   0:非正常状态 1：正常  2：已领完',
  `uid` int(11) NOT NULL COMMENT '发红包的人',
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT '跑团id',
  `type` enum('common','group_p','group_c') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'common' COMMENT '红包类型',
  `money` bigint(11) NOT NULL DEFAULT '0' COMMENT '面额',
  `leftmoney` bigint(11) NOT NULL DEFAULT '0' COMMENT '剩余金额',
  `remark` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `password` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '红包口令',
  `maxnum` int(11) NOT NULL DEFAULT '0' COMMENT '发送上限人数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `return_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '红包返还状态-1：未返还 0：无 1：以返还',
  `time` bigint(15) NOT NULL DEFAULT '0' COMMENT '红包被抢完时间',
  `limit_money` bigint(11) DEFAULT '0' COMMENT '没人可领金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='红包';

-- ----------------------------
-- Table structure for hongbao_log
-- ----------------------------
DROP TABLE IF EXISTS `hongbao_log`;
CREATE TABLE `hongbao_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hongbao_id` int(11) NOT NULL COMMENT '红包id',
  `uid` int(11) NOT NULL COMMENT '红包接受人',
  `money` bigint(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='领取红包表';

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `type` enum('goods','advance') COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态-2 取消 -1删除 0未下单 1已下单  2已发货  3完成',
  `money` bigint(11) NOT NULL DEFAULT '0' COMMENT '需要支付的金额',
  `uid` int(11) NOT NULL,
  `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态0：等待支付 1：支付完成',
  `pay_time` bigint(15) NOT NULL DEFAULT '0' COMMENT '支付时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ----------------------------
-- Table structure for orders_goods_detail
-- ----------------------------
DROP TABLE IF EXISTS `orders_goods_detail`;
CREATE TABLE `orders_goods_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL COMMENT '订单号',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `goodsname` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `price` bigint(11) NOT NULL COMMENT '价格',
  `nums` int(11) NOT NULL COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for pay_log
-- ----------------------------
DROP TABLE IF EXISTS `pay_log`;
CREATE TABLE `pay_log` (
  `payid` bigint(18) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `price` bigint(15) NOT NULL COMMENT '金额',
  `order_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单id',
  `type` enum('weixin','advance','alipay') CHARACTER SET utf8 NOT NULL COMMENT '类型',
  `outerid` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付外id',
  `tradestatus` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付接口状态',
  `param` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数',
  `returnparam` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口回调内容',
  `addtime` bigint(15) NOT NULL COMMENT '添加时间',
  `lasttime` bigint(15) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`payid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付记录';

-- ----------------------------
-- Table structure for pay_tixian
-- ----------------------------
DROP TABLE IF EXISTS `pay_tixian`;
CREATE TABLE `pay_tixian` (
  `id` bigint(18) NOT NULL,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '提现状态 0提现审核 1提现完成 -1审核失败 -2提现异常',
  `money` bigint(11) NOT NULL COMMENT '提现金额',
  `remark` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='提现表';

-- ----------------------------
-- Table structure for private_chat
-- ----------------------------
DROP TABLE IF EXISTS `private_chat`;
CREATE TABLE `private_chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL COMMENT '跑团id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `to_uid` int(11) NOT NULL COMMENT '私聊方id',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态1正常 -1结束私聊关系',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` int(11) NOT NULL DEFAULT '0',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_account` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COMMENT='私聊表';

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ENTRY_ID` varchar(95) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_DURABLE` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `LOCK_NAME` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `STR_PROP_1` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STR_PROP_2` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STR_PROP_3` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1启用  0禁用   -1删除',
  `nickname` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '密码',
  `password_salt` varchar(32) NOT NULL COMMENT '随机秘钥',
  `avatar` varchar(256) NOT NULL DEFAULT '' COMMENT '头像id',
  `regtime` bigint(15) NOT NULL DEFAULT '0' COMMENT '注册时间',
  `logintime` bigint(15) NOT NULL DEFAULT '0' COMMENT '上次登录时间',
  `logintimes` mediumint(9) NOT NULL DEFAULT '0' COMMENT '登录次数',
  `points` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `advance` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `token` varchar(64) NOT NULL DEFAULT '' COMMENT '登录授权',
  `adress` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '地址',
  `sex` tinyint(1) NOT NULL DEFAULT '-1' COMMENT '-1 未设置    0男     1女',
  `birthday` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '生日',
  `realname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '姓名',
  `personal_introduction` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '个人介绍',
  `qr_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '二维码',
  `sign` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '签名',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮箱',
  `graduate` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '毕业院校',
  `money` bigint(11) NOT NULL DEFAULT '0' COMMENT '余额',
  `nocashmoney` bigint(11) NOT NULL DEFAULT '0' COMMENT '不可提现余额',
  `thumbnail` varchar(256) NOT NULL DEFAULT '' COMMENT '头像缩略图',
  `openid` varchar(50) NOT NULL DEFAULT '' COMMENT '微信id',
  `create_group` tinyint(4) NOT NULL DEFAULT '0' COMMENT '创群权利',
  `pay_pwd` varchar(255) NOT NULL DEFAULT '' COMMENT '支付密码',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for user_action_log
-- ----------------------------
DROP TABLE IF EXISTS `user_action_log`;
CREATE TABLE `user_action_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `action` enum('login','logout','edit','weixin_login') DEFAULT NULL COMMENT 'login:登录logout:登出edit编辑资料',
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录授权',
  `deviceid` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '设备ID',
  `platform` enum('weixin','android','pc','ios') CHARACTER SET utf8 NOT NULL DEFAULT 'pc',
  `version` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '版本号',
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '来源IP',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4311 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户操作记录表';

-- ----------------------------
-- Table structure for user_feedback
-- ----------------------------
DROP TABLE IF EXISTS `user_feedback`;
CREATE TABLE `user_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标题',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '反馈内容',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户反馈';

-- ----------------------------
-- Table structure for user_friends
-- ----------------------------
DROP TABLE IF EXISTS `user_friends`;
CREATE TABLE `user_friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `touid` int(11) NOT NULL COMMENT '对方用户id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户好友表';

-- ----------------------------
-- Table structure for user_money_log
-- ----------------------------
DROP TABLE IF EXISTS `user_money_log`;
CREATE TABLE `user_money_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `action` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '方式 recharge:充值  red_packet:红包  shopping：购物  tixian:提现',
  `money` bigint(11) NOT NULL DEFAULT '0' COMMENT '金额',
  `leftmoney` bigint(11) NOT NULL DEFAULT '0' COMMENT '剩余金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  `type` tinyint(4) NOT NULL COMMENT '流水类型 1：收入 -1支出',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户余额记录表';

-- ----------------------------
-- Table structure for user_points_log
-- ----------------------------
DROP TABLE IF EXISTS `user_points_log`;
CREATE TABLE `user_points_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户ID',
  `action` enum('get','login','topic') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `points` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_account` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户积分记录';

-- ----------------------------
-- Table structure for user_verify
-- ----------------------------
DROP TABLE IF EXISTS `user_verify`;
CREATE TABLE `user_verify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '验证码',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '手机号',
  `uid` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `deviceid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '设备ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `type` smallint(6) NOT NULL COMMENT '验证码类型1注册2密码重置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COMMENT='验证码';
