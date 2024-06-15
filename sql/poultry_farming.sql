/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : poultry_farming

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 15/06/2024 21:11:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for animal
-- ----------------------------
DROP TABLE IF EXISTS `animal`;
CREATE TABLE `animal`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `animalName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '畜禽名称',
  `animalSex` tinyint(0) NULL DEFAULT 1 COMMENT '性别,1雌,2雄',
  `animalDate` date NULL DEFAULT NULL COMMENT '出生日期',
  `animalStatus` tinyint(0) NULL DEFAULT 1 COMMENT '健康状态,0死亡,1健康,2不健康',
  `animalWeight` double NULL DEFAULT NULL COMMENT '体重',
  `isBreed` tinyint(0) NULL DEFAULT 0 COMMENT '是否怀孕,0未怀孕1怀孕',
  `feedsId` int(0) NOT NULL COMMENT '饲料id',
  `drugId` int(0) NOT NULL COMMENT '疫苗id',
  `userId` int(0) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`, `feedsId`, `drugId`, `userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '畜禽' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of animal
-- ----------------------------
INSERT INTO `animal` VALUES (1, '母鸡', 1, '2023-10-31', 0, 45, 0, 1, 6, 1);
INSERT INTO `animal` VALUES (2, '羊', 1, '2023-10-10', 0, 100, 1, 2, 6, 1);
INSERT INTO `animal` VALUES (4, '小羊1', 1, '2023-10-31', 2, 21, 0, 2, 6, 1);

-- ----------------------------
-- Table structure for breed
-- ----------------------------
DROP TABLE IF EXISTS `breed`;
CREATE TABLE `breed`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `breedName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '繁育基地名称',
  `breedImg` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '繁育基地图片',
  `breedKind` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '繁育种类',
  `breedCount` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '繁育数量',
  `breedStartdate` date NULL DEFAULT NULL COMMENT '繁育开始天数',
  `breedTitle` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '繁育所遇问题题目',
  `breedContent` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '繁育所遇问题内容',
  `figure` tinyint(0) NULL DEFAULT 1 COMMENT '是否解决问题,0未解决,1解决',
  `userId` int(0) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`, `userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '繁育' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of breed
-- ----------------------------
INSERT INTO `breed` VALUES (2, '等风基地', NULL, '羊', '120', '2023-10-17', '感冒', '2只繁育母羊有点小感冒', 1, 2);
INSERT INTO `breed` VALUES (3, '小羊繁育基地', '9cbeae9a-e2a2-48ea-9986-9e12ab6d3685.jpg', '小白羊羔', '10', '2023-10-01', '有问题', '有问题', 1, 1);
INSERT INTO `breed` VALUES (4, '小鸡繁育基地', '1a4a9b80-21b5-4c27-829a-fb1459081f4e.jpg', '鸡', '20', '2023-10-01', '', '', 1, 1);
INSERT INTO `breed` VALUES (5, '小鸭子繁育基地', 'a59d9601-35b0-4990-9221-d0daa947bde6.jpg', '白鸭', '20', '2023-10-01', '死亡', '不知道问题', 0, 1);

-- ----------------------------
-- Table structure for drug
-- ----------------------------
DROP TABLE IF EXISTS `drug`;
CREATE TABLE `drug`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `drugName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品名称',
  `drugImg` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品图片',
  `category` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品分类',
  `specification` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品规格',
  `drugCount` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品数量',
  `batchNumber` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品批号',
  `manufactureDate` date NULL DEFAULT NULL COMMENT '生产日期',
  `expirationDate` date NULL DEFAULT NULL COMMENT '有效期限',
  `supplier` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商',
  `buyerCount` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '采购数量',
  `buyerPrice` decimal(10, 2) NULL DEFAULT NULL COMMENT '采购价格',
  `userName` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '采购人员名称',
  `userId` int(0) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`, `userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '药品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of drug
-- ----------------------------
INSERT INTO `drug` VALUES (1, '阿莫西林胶囊', 'e13bb63c-2fb7-49c3-80b3-d79a2de5d182.jpg', '抗生素', '盒', '22', '123456', '2023-10-01', '2024-01-01', '我的供应商', '40', 5200.00, '等风', 1);
INSERT INTO `drug` VALUES (2, '阿莫西林', '23dfa0f1-1902-43ba-b77b-8410ad011172.jpg', '抗生素', '盒', '10', '111', '2023-10-04', '2024-01-01', '供应商', '20', 5200.20, '等风来', 1);
INSERT INTO `drug` VALUES (6, '天花疫苗', 'ae9755fd-6958-43aa-86f6-a43e680c8850.jpg', '疫苗', '121ml/瓶', '121', '1212', '2023-10-14', '2023-10-23', '我的供应商', '1111', 3333.00, '等风', 1);

-- ----------------------------
-- Table structure for fang
-- ----------------------------
DROP TABLE IF EXISTS `fang`;
CREATE TABLE `fang`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fangName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '放养名称',
  `fangKind` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '放养种类',
  `fangCount` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '放养数量',
  `x` double NOT NULL COMMENT 'x轴坐标',
  `y` double NOT NULL COMMENT 'y轴坐标',
  `userId` int(0) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`, `userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '放养' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of fang
-- ----------------------------
INSERT INTO `fang` VALUES (1, '养鸡', '鸡', '2121', 470.0996093750024, 479.0190498836596, 1);
INSERT INTO `fang` VALUES (2, '小猪养殖', '猪', '4234', 386.42773437500125, 479.0190498836596, 1);
INSERT INTO `fang` VALUES (3, '养鱼', '鱼', '12', 393.810546875001, 541.6016341445642, 1);
INSERT INTO `fang` VALUES (4, '养鸭', '鸭', '1231', 608.6503906250011, 444.994960262796, 1);
INSERT INTO `fang` VALUES (5, '我的养殖场', '荷兰进口小猪', '12', 387.9042968750006, 497.7298612963604, 1);

-- ----------------------------
-- Table structure for feeds
-- ----------------------------
DROP TABLE IF EXISTS `feeds`;
CREATE TABLE `feeds`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `feedName` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饲料名称',
  `feedCount` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饲料数量,吨为单位',
  `feedPrice` decimal(10, 2) NULL DEFAULT NULL COMMENT '饲料价格',
  `feedOrigin` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饲料产地',
  `feedStatus` tinyint(0) NULL DEFAULT NULL COMMENT '饲料状态,0缺乏,1少量,2较多',
  `feedPlace` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饲料存放位置',
  `feedDate` date NULL DEFAULT NULL COMMENT '饲料存储时间',
  `describle` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饲料描述',
  `userId` int(0) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '饲料' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feeds
-- ----------------------------
INSERT INTO `feeds` VALUES (1, '小米', '4', 405.00, '湖南省永州市', 2, '仓库第二层', '2023-10-27', '具有高蛋白质和脂肪含量', 1);
INSERT INTO `feeds` VALUES (2, '鱼油', '2', 405.00, '湖南省永州市', 2, '仓库第二层', '2023-10-11', '具有高蛋白质和脂肪含量', 1);
INSERT INTO `feeds` VALUES (3, '鱼油', '45', 405.00, '湖南省永州市', 1, '仓库第二层', '2023-10-11', '具有高蛋白质和脂肪含量', 1);
INSERT INTO `feeds` VALUES (4, '鱼油', '2', 405.00, '湖南省永州市', 1, '我的仓库', '2023-10-11', '具有高蛋白质和脂肪含量', 1);
INSERT INTO `feeds` VALUES (5, '糠料', '1', 45.00, '湖南省永州市', 1, '仓库第二层', '2023-10-11', '具有高蛋白质和脂肪含量', 1);
INSERT INTO `feeds` VALUES (6, '鱼油', '6', 405.00, '湖南省永州市', 0, '仓库第二层', '2023-10-11', '具有高蛋白质和脂肪含量', 1);
INSERT INTO `feeds` VALUES (8, '小麦', '1', 405.00, '湖南省永州市', 1, '仓库第二层', '2023-10-11', '具有高蛋白质和脂肪含量', 1);

-- ----------------------------
-- Table structure for leftpower
-- ----------------------------
DROP TABLE IF EXISTS `leftpower`;
CREATE TABLE `leftpower`  (
  `id` int(0) NOT NULL COMMENT '左侧导航栏id',
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导航栏标题',
  `path` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导航栏路径',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导航栏图标',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '导航栏父结点id',
  `is_leaf` tinyint(0) NULL DEFAULT NULL COMMENT '是否为叶子结点,0否,1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '左侧导航栏' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of leftpower
-- ----------------------------
INSERT INTO `leftpower` VALUES (1, '首页', '/index', 'HomeFilled', 0, 0);
INSERT INTO `leftpower` VALUES (2, '畜禽管理', '/animal-manage', 'User', 0, 0);
INSERT INTO `leftpower` VALUES (3, '畜禽列表', '/animal-manage/list', 'List', 2, 1);
INSERT INTO `leftpower` VALUES (4, '添加畜禽', '/animal-manage/addanimal', 'List', 2, 1);
INSERT INTO `leftpower` VALUES (5, '饲料管理', '/feed-manage', 'Key', 0, 0);
INSERT INTO `leftpower` VALUES (7, '添加饲料', '/feed-manage/addfeed', 'List', 5, 1);
INSERT INTO `leftpower` VALUES (8, '防疫健康', '/epidemic-manage', 'User', 0, 0);
INSERT INTO `leftpower` VALUES (9, '防疫药品', '/epidemic-manage/drug', 'List', 8, 1);
INSERT INTO `leftpower` VALUES (10, '医疗器械', '/epidemic-manage/medical', 'List', 8, 1);
INSERT INTO `leftpower` VALUES (11, '防疫列表', '/epidemic-manage/list', 'List', 8, 1);
INSERT INTO `leftpower` VALUES (12, '繁育管理', '/breed-manage', 'Key', 0, 0);
INSERT INTO `leftpower` VALUES (13, '繁育列表', '/breed-manage/list', 'List', 12, 1);
INSERT INTO `leftpower` VALUES (14, '放养管理', '/fangyang-manage', 'User', 0, 0);
INSERT INTO `leftpower` VALUES (15, '放养列表', '/fangyang-manage/list', 'List', 14, 1);
INSERT INTO `leftpower` VALUES (16, '添加放养', '/fangyang-manage/addfang', 'List', 14, 1);
INSERT INTO `leftpower` VALUES (17, '物资管理', '/supplies-manage', 'HomeFilled', 0, 0);
INSERT INTO `leftpower` VALUES (18, '物资列表', '/supplies-manage/list', 'List', 17, 1);
INSERT INTO `leftpower` VALUES (19, '添加物资', '/supplies-manage/addsupplies', 'List', 17, 1);
INSERT INTO `leftpower` VALUES (20, '系统管理', '/system-manage', 'Key', 0, 0);
INSERT INTO `leftpower` VALUES (21, '用户信息', '/system-manage/users', 'List', 20, 1);
INSERT INTO `leftpower` VALUES (22, '用户注册', '/system-manage/userigister', 'List', 20, 1);

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `powerId` int(0) NOT NULL DEFAULT 2 COMMENT '权限id',
  `userId` int(0) NOT NULL COMMENT '用户id',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `is_default` tinyint(0) NULL DEFAULT 0 COMMENT '是否为默认',
  `status` tinyint(0) NULL DEFAULT 1 COMMENT '0异常,1正常',
  PRIMARY KEY (`id`, `userId`, `powerId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES (1, 'admin', '123456', 1, 1, '12345678998', 1, 1);
INSERT INTO `login` VALUES (2, 'aaa', '123456', 2, 2, '12345678999', 0, 1);

-- ----------------------------
-- Table structure for medical
-- ----------------------------
DROP TABLE IF EXISTS `medical`;
CREATE TABLE `medical`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `medicalName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '器械名称',
  `medicalType` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '器械类型',
  `medicalFirm` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '器械厂商',
  `medicalCount` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '器械数量',
  `medicalPrice` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '器械价格',
  `buyTime` date NULL DEFAULT NULL COMMENT '购买时间',
  `isLend` tinyint(0) NULL DEFAULT 0 COMMENT '是否借出,1借出,0未借出',
  `lendUserId` int(0) NULL DEFAULT NULL COMMENT '借出用户id',
  `lendTime` date NULL DEFAULT NULL COMMENT '借出时间',
  `lendCount` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '借出数量',
  `returnCount` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '归还数量',
  `isMar` tinyint(0) NULL DEFAULT NULL COMMENT '是否损坏,0损坏,1未损坏',
  `userId` int(0) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`, `userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医疗器械' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of medical
-- ----------------------------
INSERT INTO `medical` VALUES (1, '血压计', '诊断类', '常德', '12', '600', '2023-10-27', 0, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `medical` VALUES (2, '手术刀', '治疗类', '武汉', '20', '600', '2023-10-30', 1, 2, '2023-10-31', '10', '10', 0, 1);
INSERT INTO `medical` VALUES (3, '输液器', '治疗类', '武汉', '10', '10000', '2023-10-30', 1, 2, '2023-10-31', '5', '5', 1, 1);
INSERT INTO `medical` VALUES (5, '针管', '治疗类', '绥阳', '121', '12000.1', '2023-10-08', 0, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `medical` VALUES (6, '小号针头', '注射类', '武汉', '12', '100', '2023-10-22', 1, 2, '2023-10-31', '22', '12', 1, 1);

-- ----------------------------
-- Table structure for power
-- ----------------------------
DROP TABLE IF EXISTS `power`;
CREATE TABLE `power`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `powerName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `powers` json NULL COMMENT '拥有权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of power
-- ----------------------------
INSERT INTO `power` VALUES (1, '管理员', '[\"/index\", \"/animal-manage/list\", \"/animal-manage/addanimal\", \"/feed-manage/addfeed\", \"/epidemic-manage/drug\", \"/epidemic-manage/medical\", \"/epidemic-manage/list\", \"/breed-manage/list\", \"/fangyang-manage/list\", \"/fangyang-manage/addfang\", \"/supplies-manage/list\", \"/supplies-manage/addsupplies\", \"/system-manage/users\", \"/system-manage/userigister\"]');
INSERT INTO `power` VALUES (2, '普通员工', '[\"/index\", \"/animal-manage/list\", \"/animal-manage/addanimal\", \"/epidemic-manage/list\"]');
INSERT INTO `power` VALUES (3, '物资管理员', '[\"/index\", \"/animal-manage/list\", \"/animal-manage/addanimal\"]');
INSERT INTO `power` VALUES (4, '放养员工', '[\"/index\", \"/fangyang-manage/list\", \"/fangyang-manage/addfang\"]');

-- ----------------------------
-- Table structure for register
-- ----------------------------
DROP TABLE IF EXISTS `register`;
CREATE TABLE `register`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(0) NULL DEFAULT 1 COMMENT '状态,0拒绝,1,待审核,2通过',
  `date` date NULL DEFAULT NULL COMMENT '注册日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '注册' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of register
-- ----------------------------
INSERT INTO `register` VALUES (1, 'bbb', '123456', 'pjx200301287434@sina.com', '15973968340', 1, '2023-10-10');
INSERT INTO `register` VALUES (2, 'ccc', '123456', 'pjx200301287434@sina.com', '14725836911', 1, '2023-10-10');
INSERT INTO `register` VALUES (3, 'ddd', '123456', 'pjx200301287434@sina.com', '13456789147', 0, '2023-10-10');
INSERT INTO `register` VALUES (5, 'fsfds32', 'dfadfasdf', 'pjx200301287434@sina.com', '15987369847', 0, '2023-10-11');
INSERT INTO `register` VALUES (9, 'pjx', '1234567', '1256564426@qq.com', '14725836944', 1, '2023-10-26');

-- ----------------------------
-- Table structure for supplies
-- ----------------------------
DROP TABLE IF EXISTS `supplies`;
CREATE TABLE `supplies`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `suppliesName` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物资名称',
  `suppliesUnit` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物资单位',
  `suppliesCount` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物资数量',
  `suppliesPrice` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物资价格',
  `provider` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商',
  `suppliesAttn` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `attnPhone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人电话',
  `isMar` tinyint(0) NULL DEFAULT 1 COMMENT '是否损坏0损坏,1未损坏',
  `marCount` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '损坏数量',
  `suppliesCause` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '损坏原因',
  `userId` int(0) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`, `userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '物资' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of supplies
-- ----------------------------
INSERT INTO `supplies` VALUES (1, '斗车', '千克', '3', '150', '衡阳供应商', '得瑟下发', '13456789369', 0, '3', '出问题了', 1);
INSERT INTO `supplies` VALUES (2, '斗车', '千克', '3', '150', '衡阳供应商', '得瑟下发', '13456789369', 1, NULL, '', 1);
INSERT INTO `supplies` VALUES (3, '斗车', '千克', '2', '150', '衡阳供应商', '得瑟下', '13456789369', 0, '1', '出问题了', 2);
INSERT INTO `supplies` VALUES (4, '挖机', '吨', '3', '452222', '恒大供应商', '二房东', '14789936955', 1, NULL, NULL, 2);
INSERT INTO `supplies` VALUES (5, '斗车', '千克', '3', '150', '衡阳供应商', '得瑟下发', '13456789369', 1, NULL, '', 1);
INSERT INTO `supplies` VALUES (17, '我的物资', '的撒法', '1', '3423', '打法', '撒打发', '14725836944', 1, NULL, '', 1);
INSERT INTO `supplies` VALUES (19, '的的物资', '胜多负少', '1', '24234', '打法阿萨', '爱的色放', '14725836945', 1, NULL, '', 1);
INSERT INTO `supplies` VALUES (20, '的的物资', '胜多负少', '2', '24123', '是否达到', '的发生', '17894561234', 0, '2', '出问题了', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(0) NULL DEFAULT NULL COMMENT '年龄',
  `gender` tinyint(0) NULL DEFAULT 0 COMMENT '性别,0男,1女',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '等风', 21, 0, '12345678998');
INSERT INTO `user` VALUES (2, '彭', 22, 1, '12345678999');

SET FOREIGN_KEY_CHECKS = 1;
