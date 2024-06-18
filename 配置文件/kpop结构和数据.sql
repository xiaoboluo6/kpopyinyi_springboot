/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80034
Source Host           : localhost:3306
Source Database       : kpop

Target Server Type    : MYSQL
Target Server Version : 80034
File Encoding         : 65001

Date: 2024-06-18 16:19:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for inter_message
-- ----------------------------
DROP TABLE IF EXISTS `inter_message`;
CREATE TABLE `inter_message` (
  `id` char(19) NOT NULL COMMENT '评论ID',
  `member_id` char(19) NOT NULL COMMENT '用户id',
  `nickname` varchar(50) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `reply` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '0 表示待处理  1 表示已经处理',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '0表示正常显示 1表示封禁    留言封禁后再也不会解封',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';

-- ----------------------------
-- Records of inter_message
-- ----------------------------
INSERT INTO `inter_message` VALUES ('1802978030161907713', '1802977890504183809', 'donk66', 'default/default.jpg', '安智煐！！', null, '0', '0', '2024-06-18 16:13:55', '2024-06-18 16:13:55');
INSERT INTO `inter_message` VALUES ('1802978263038054401', '1802977890504183809', 'donk66', 'default/default.jpg', 'kep7er的shoting star很好听', '小编马上更新哦！', '1', '0', '2024-06-18 16:14:50', '2024-06-18 16:15:16');
INSERT INTO `inter_message` VALUES ('1802978687560339458', '1802978548359794689', 'NMIXX大发', 'default/default.jpg', 'KYUJIN KYUJIN KYUJIN', null, '0', '0', '2024-06-18 16:16:32', '2024-06-18 16:16:32');

-- ----------------------------
-- Table structure for kpop_banner
-- ----------------------------
DROP TABLE IF EXISTS `kpop_banner`;
CREATE TABLE `kpop_banner` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT 'ID',
  `title` varchar(20) DEFAULT '' COMMENT '标题',
  `image_url` varchar(500) NOT NULL DEFAULT '' COMMENT '图片地址',
  `song_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '链接地址',
  `is_show` int NOT NULL DEFAULT '0' COMMENT '0不显示 1显示',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='首页banner表';

-- ----------------------------
-- Records of kpop_banner
-- ----------------------------
INSERT INTO `kpop_banner` VALUES ('1802971193236779010', '致我的思春期', '2024-06-18/202406181546217e63277a742c4f788fa65da10be5ab08.png', '1802970579719155714', '1', '0', '0', '2024-06-18 15:46:45', '2024-06-18 15:46:45');
INSERT INTO `kpop_banner` VALUES ('1802976931849895938', 'Seoul轮播图', '2024-06-18/20240618160903f0c37d9cad10461089b85f12631be936.png', '1802966460379951106', '1', '0', '0', '2024-06-18 16:09:33', '2024-06-18 16:09:33');

-- ----------------------------
-- Table structure for kpop_category
-- ----------------------------
DROP TABLE IF EXISTS `kpop_category`;
CREATE TABLE `kpop_category` (
  `id` char(19) NOT NULL COMMENT '类别ID',
  `name` varchar(50) NOT NULL COMMENT '类别名称',
  `sort` tinyint DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='歌手类别表';

-- ----------------------------
-- Records of kpop_category
-- ----------------------------
INSERT INTO `kpop_category` VALUES ('1742095963781443585', '男团', '0', '0', '2024-01-02 16:10:19', '2024-02-23 22:49:38');
INSERT INTO `kpop_category` VALUES ('1742105698513498114', '女团', '0', '0', '2024-01-02 16:49:00', '2024-01-02 16:49:00');
INSERT INTO `kpop_category` VALUES ('1742823029036408833', '男solo', '2', '0', '2024-01-04 16:19:25', '2024-01-04 16:19:30');
INSERT INTO `kpop_category` VALUES ('1742823507593912322', '女solo', '2', '0', '2024-01-04 16:21:19', '2024-01-04 16:21:19');

-- ----------------------------
-- Table structure for kpop_singer
-- ----------------------------
DROP TABLE IF EXISTS `kpop_singer`;
CREATE TABLE `kpop_singer` (
  `id` char(19) NOT NULL COMMENT '歌手ID',
  `category_id` char(19) NOT NULL COMMENT '所属类别ID',
  `name` varchar(50) NOT NULL COMMENT '歌手名称',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像地址',
  `description` varchar(500) DEFAULT NULL,
  `popularity` tinyint NOT NULL DEFAULT '0' COMMENT '默认热度为0',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='歌手表';

-- ----------------------------
-- Records of kpop_singer
-- ----------------------------
INSERT INTO `kpop_singer` VALUES ('1802947634514882561', '1742823507593912322', '脸红的思春期(安智煐)', '2024-06-18/2024061814114045d56f7b02074d3aaae44b890c87310a.png', '脸红的思春期（볼빨간 사춘기、BOL4），韩国女子演唱组合，由Shofar Music于2016年4月22日推出，由安智煐一位成员组成。2020年4月2日，禹智润退出，成员安智煐一人以组合名义活动，站长最爱没有之一。', '2', '0', '2024-06-18 14:13:08', '2024-06-18 14:13:08');
INSERT INTO `kpop_singer` VALUES ('1802947996579786753', '1742095963781443585', '&TEAM', '2024-06-18/2024061814142954a4614f3fcc4a2e971337f440f207c4.png', '&TEAM是HYBE旗下HYBE LABELS JAPAN推出的男子组合，九名成员包括古贺祐大（K）、副队长 [28]村田风雅（FUMA）、王奕翔（NICHOLAS）、队长边义州（EJ）、中耒田悠真（YUMA）、朝仓穣（JO）、重田美琉爱（HARUA）、高山浬希（TAKI）、宏田力（MAKI）。', '0', '0', '2024-06-18 14:14:34', '2024-06-18 14:14:34');
INSERT INTO `kpop_singer` VALUES ('1802951685403045889', '1742105698513498114', 'MAMAMOO', '2024-06-18/202406181428478865fb5e58cf4a5e849a9cda4be4045b.png', 'MAMAMOO（마마무），是Rainbow Bridge World于2014年6月19日推出的韩国女子演唱组合，由金容仙（颂乐）、文星伊（玟星）、丁辉人（辉人）、安惠真（华莎）四名成员组成。组合名字中的“MAMA”是不分国家和语言所有新生儿学会的第一个词汇，“MOO”为婴儿牙牙学语的拟声词，“MAMAMOO”意为期待超越国籍和语言，为大众带来原始的本能性的音乐。', '0', '0', '2024-06-18 14:29:14', '2024-06-18 14:29:14');
INSERT INTO `kpop_singer` VALUES ('1802952527870947329', '1742105698513498114', 'izone', '2024-06-18/202406181432093d587c3215804b209c6936ce2dfb3886.png', 'Z*ONE是由Off The Record于2018年10月29日推出的韩日女子演唱组合，由张员瑛（C位）、宫胁咲良、曺柔理、崔叡娜、安宥真、矢吹奈子、权恩妃（队长）、姜惠元、本田仁美、金采源、金珉周、李彩演12位成员组成。', '0', '0', '2024-06-18 14:32:35', '2024-06-18 14:32:35');
INSERT INTO `kpop_singer` VALUES ('1802953101790146561', '1742105698513498114', 'Dreamcatcher', '2024-06-18/20240618143421567dd79a5cc6452fa5c708baf588ff07.png', 'DREAMCATCHER（드림캐쳐）是韩国Dreamcatcher Company于2017年1月13日推出的中韩女子组合，由金旼祉（祉攸、지유、Ji-U）、金宝拉（秀雅 、수아、SUA）、李始娟（시연）、韩东（한동）、金裕贤（유현）、李愈彬（多美、다미、Dami）、李佳泫（가현）七名成员组成。', '0', '0', '2024-06-18 14:34:51', '2024-06-18 14:34:51');
INSERT INTO `kpop_singer` VALUES ('1802953539348328450', '1742105698513498114', 'STAYC', '2024-06-18/202406181436059e6511c7bb6142328a3920d81c45382d.png', 'STAYC（스테이씨）是由High Up Entertainment于2020年11月12日推出的韩国女子演唱团体，由队长裴秀珉（SUMIN）、朴莳恩（SIEUN）、李彩煐（ISA）、尹势银（SEEUN）、沈姿润（YOON）、张叡恩（J）6名成员组成。', '0', '0', '2024-06-18 14:36:36', '2024-06-18 14:36:36');
INSERT INTO `kpop_singer` VALUES ('1802953927396945922', '1742105698513498114', 'ITZY', '2024-06-18/202406181437486345b89744d2401fb4028d8fce89291c.png', 'ITZY（있지）是JYP Entertainment于2019年2月12日推出的韩国女子演唱组合，由黄礼志（Yeji）、崔智秀（Lia）、申留真（Ryujin）、李彩领（Chaeryeong）、申有娜（Yuna）五位成员组成 。', '0', '0', '2024-06-18 14:38:08', '2024-06-18 14:38:08');
INSERT INTO `kpop_singer` VALUES ('1802954425046921217', '1742105698513498114', 'aespa', '2024-06-18/2024061814393651fcd2dde73c4d37b428e6d92ca87821.png', 'aespa（에스파）是韩国SM娱乐有限公司于2020年11月17日推出的女子演唱组合，由刘知珉（KARINA）、内永枝利（GISELLE）、金旼炡（WINTER）、宁艺卓（NINGNING） 四名成员组成，同时团体也拥有四名AI成员。', '0', '0', '2024-06-18 14:40:07', '2024-06-18 14:40:07');
INSERT INTO `kpop_singer` VALUES ('1802955589293436929', '1742105698513498114', 'Red Velvet', '2024-06-18/20240618144423bc87ae1e8ea14d618f7d98833163a4f9.png', 'Red Velvet（레드벨벳 、レッドベルベット）是韩国SM娱乐有限公司于2014年8月推出的女子演唱组合，由裴珠泫、姜涩琪、孙承完、朴秀荣和金艺琳五人组成。', '0', '0', '2024-06-18 14:44:44', '2024-06-18 14:44:44');
INSERT INTO `kpop_singer` VALUES ('1802956966304411649', '1742105698513498114', 'kep1er', '2024-06-18/202406181449378db11c77002c46d29fcfd82a9a838a53.png', 'Kep1er（케플러）是Wake One Entertainment、Swing Entertainment于2021年10月22日推出的韩国女子演唱组合，由金采炫（C位）、休宁巴伊叶、崔有真（队长）、金多娟、徐永恩、江崎光、沈小婷七位成员组成，组合名称结合了“Kep”和“1”两个部分，“Kep”代表抓住大家的梦想，“1”代表团结成为一体，并成为第一。', '0', '0', '2024-06-18 14:50:13', '2024-06-18 14:50:13');
INSERT INTO `kpop_singer` VALUES ('1802957854091767809', '1742105698513498114', 'OH MY GIRL', '2024-06-18/202406181453129924dacc08794de1af73a3a8cbf2e72c.png', 'OH MY GIRL（오마이걸）是WM Entertainment于2015年4月20日推出的韩国女子演唱组合，现由崔效定、金美贤、柳諟我、玄胜熙、裴有彬、崔乂园6名成员组成。', '0', '0', '2024-06-18 14:53:44', '2024-06-18 14:53:44');
INSERT INTO `kpop_singer` VALUES ('1802958877099290625', '1742105698513498114', 'Weki Meki', '2024-06-18/20240618145720e3a825f330d6473882f8eb42092e881f.png', 'Weki Meki（위키미키），是Fantagio Entertainment于2017年8月8日推出的韩国女子演唱组合，由池秀娟、郑海琳、磪有情、金度延、李书静、金秀经、姜昭恩、卢孝静八位成员组成。团名中的Weki的含义“是手中握有能了解彼此的钥匙的8位极具个性的少女”，而Meki是“相遇之后获得了开启崭新世界的钥匙的少女”，而其缩写是“WEME”则代表了“我成为我们”、“8位少女结成一个团体”的双重意义。', '0', '0', '2024-06-18 14:57:48', '2024-06-18 14:57:48');
INSERT INTO `kpop_singer` VALUES ('1802959229089476609', '1742105698513498114', 'fromis_9', '2024-06-18/20240618145910278bd799e57a4d128a45dbed882c7826.png', 'fromis_9（프로미스나인）是Stone Music Entertainment于2017年推出的韩国女子音乐团体，出道时由李赛纶、宋河英、朴池原、卢知宣、李瑞渊、李采映、李娜炅、白知宪、张圭悧（已退出）9位成员组成。', '0', '0', '2024-06-18 14:59:12', '2024-06-18 14:59:12');
INSERT INTO `kpop_singer` VALUES ('1802960498814349314', '1742095963781443585', 'GHOST9', '2024-06-18/20240618150413fcd95cf294404f56a56684ff82c2e881.png', 'GHOST9（고스트나인）是Maroo Entertainment于2020年9月23日推出的韩国男子演唱组合，由孙准亨、金秀炫（SHIN）、崔俊圣、李江诚、帕西特·瓦塔尼雅布拉莫（Prince）、李优珍、李津宇七名成员组成。', '0', '0', '2024-06-18 15:04:15', '2024-06-18 15:04:15');
INSERT INTO `kpop_singer` VALUES ('1802961437331812353', '1742095963781443585', 'TEMPEST', '2024-06-18/20240618150724abb00fd59ee741eab715780c3956f642.png', 'TEMPEST（템페스트）是乐华娱乐（YUEHUA Entertainment）于2022年3月2日推出的韩国男子演唱组合，由吴玉兴（Hanbin）、安炯燮（HyeongSeop）、具本奕（Hyuk）、崔炳燮（EunChan）、李义雄（队长、Lew）、宋在原（HwaRang）、金台来（TaeRae）7名成员组成。', '0', '0', '2024-06-18 15:07:59', '2024-06-18 15:07:59');

-- ----------------------------
-- Table structure for kpop_singer_favourite
-- ----------------------------
DROP TABLE IF EXISTS `kpop_singer_favourite`;
CREATE TABLE `kpop_singer_favourite` (
  `id` char(19) NOT NULL COMMENT '歌手喜爱推荐表',
  `singer_id` char(19) NOT NULL COMMENT '歌手id',
  `is_deleted` tinyint DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_singer_id` (`singer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='歌手喜爱推荐表';

-- ----------------------------
-- Records of kpop_singer_favourite
-- ----------------------------

-- ----------------------------
-- Table structure for kpop_song
-- ----------------------------
DROP TABLE IF EXISTS `kpop_song`;
CREATE TABLE `kpop_song` (
  `id` char(19) NOT NULL COMMENT '歌曲ID',
  `name` varchar(50) NOT NULL COMMENT '歌曲名称',
  `singer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '歌手名字(如果有多位则进行拼接)',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `popularity` int NOT NULL DEFAULT '0' COMMENT '默认流行度为0',
  `release_time` datetime DEFAULT NULL COMMENT '发行时间',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 0表示编辑状态 1表示已经发布',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='歌曲表';

-- ----------------------------
-- Records of kpop_song
-- ----------------------------
INSERT INTO `kpop_song` VALUES ('1802962969938882562', 'You(=I)', '脸红的思春期(安智煐)', '2024-06-18/2024061815140187601c6180384e6d9ee34328ff49acd0.png', '0', '2016-08-29 12:00:00', '1', '0', '2024-06-18 15:14:04', '2024-06-18 15:14:36');
INSERT INTO `kpop_song` VALUES ('1802965335035604993', '你曾是我的世界', '脸红的思春期(安智煐)', '2024-06-18/202406181523255ea2ff5e92b045fc9526ef9ecc6a9aa3.png', '0', '2021-10-26 12:00:00', '1', '0', '2024-06-18 15:23:28', '2024-06-18 15:24:17');
INSERT INTO `kpop_song` VALUES ('1802966460379951106', 'Seoul', '脸红的思春期(安智煐)', '2024-06-18/20240618152746c821fb6ffb534feda6022f36805a823e.png', '0', '2022-04-20 12:00:00', '1', '0', '2024-06-18 15:27:56', '2024-06-18 15:29:02');
INSERT INTO `kpop_song` VALUES ('1802970579719155714', '致我的思春期', '脸红的思春期(安智煐)', '2024-06-18/202406181544155462683bd2934ab79829ba7fd4d194dc.png', '0', '2019-08-17 12:00:00', '1', '0', '2024-06-18 15:44:18', '2024-06-18 15:44:56');

-- ----------------------------
-- Table structure for kpop_song_singer
-- ----------------------------
DROP TABLE IF EXISTS `kpop_song_singer`;
CREATE TABLE `kpop_song_singer` (
  `id` char(19) NOT NULL COMMENT '歌曲歌手一对多关联表ID',
  `song_id` char(19) NOT NULL COMMENT '歌曲id',
  `singer_id` char(19) NOT NULL COMMENT '歌手id',
  `is_deleted` tinyint DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_song_id` (`song_id`) USING BTREE,
  KEY `idx_singer_id` (`singer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='歌曲歌手关联表';

-- ----------------------------
-- Records of kpop_song_singer
-- ----------------------------
INSERT INTO `kpop_song_singer` VALUES ('1802962970400256002', '1802962969938882562', '1802947634514882561', '0', '2024-06-18 15:14:04', '2024-06-18 15:14:04');
INSERT INTO `kpop_song_singer` VALUES ('1802965498420523009', '1802965335035604993', '1802947634514882561', '0', '2024-06-18 15:24:07', '2024-06-18 15:24:07');
INSERT INTO `kpop_song_singer` VALUES ('1802966696166944770', '1802966460379951106', '1802947634514882561', '0', '2024-06-18 15:28:53', '2024-06-18 15:28:53');
INSERT INTO `kpop_song_singer` VALUES ('1802970580365078529', '1802970579719155714', '1802947634514882561', '0', '2024-06-18 15:44:19', '2024-06-18 15:44:19');

-- ----------------------------
-- Table structure for kpop_song_transliteration
-- ----------------------------
DROP TABLE IF EXISTS `kpop_song_transliteration`;
CREATE TABLE `kpop_song_transliteration` (
  `id` char(19) NOT NULL COMMENT '音译ID',
  `song_id` char(19) NOT NULL COMMENT '歌曲ID',
  `transliteration` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '音译',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_song_id` (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='KPOP歌曲音译表';

-- ----------------------------
-- Records of kpop_song_transliteration
-- ----------------------------
INSERT INTO `kpop_song_transliteration` VALUES ('1802963077220790274', '1802962969938882562', '<p>趴波一呀<br />哦呢愣 安对大沟 麻哈鸡嘛<br />哦呢蛮科门 内给都 沟 gi会了却<br />撒浪斯咯给 呜嫩 沟都<br />耶be给 麻哈嫩 沟都 玛尼哟涩被搜<br />泼沟西泼都 插么拉钩 哈吉吗<br />Baby 男 穷 哦机 噗力嫩 沟都马甲<br />哦嫩都 内 怒木 哟gi咯 八大内<br />no哇诶 dei一特<br />泼沟西坡都<br />美一 谷 掐沟 一搜都<br />纳嫩 你噶 啊纳局gi蛮 哈miong<br />瞎乐乐乐乐 no嘎 Yeah<br />Baby You You You Yeah Yeah<br />You You You Yeah Yeah<br />Baby 男囧 哟西噗力嫩 沟都 马甲<br />哦嫩都 mo对给 dei勒 搜<br />八大内 no哇诶 dei一忒<br />撒浪哈大沟 美一沟 piohiong 莫忒都<br />纳嫩 内噶 啊纳jugi蛮 哈miong<br />瞎乐乐乐乐 no嘎 Yeah<br />Baby You You You Yeah Yeah<br />You You You Yeah Yeah<br />You You You Yeah Yeah<br />You You You Yeah<br />搜图勒 内 妈咪<br />no诶给 答额嘎 bua<br />撒西 搜加嫩 沟都<br />玛尼 都力nen 纳嘚<br />哦呢都 呸我嫩 沟<br />弄 都 他妈嘎都沟<br />一贼 内噶 弄 大妈搜<br />哈给 五go 一都沟 Yeah<br />You Woo Yeah<br />You You You Yeah Yeah<br />科嘚勒 大妈搜<br />嘛恩米 都ke 撒浪米 dui miong<br />科嘚 内噗 嘛内<br />沟 啊纳 qu累哟<br />You You You Yeah Yeah<br />You You You Yeah Yeah<br />You You You Yeah Yeah<br />You You You Yeah Yeah</p>', '0', '2024-06-18 15:14:30', '2024-06-18 15:14:33');
INSERT INTO `kpop_song_transliteration` VALUES ('1802965373929385986', '1802965335035604993', '<p>o neul man ga chi it ge hae jwo <br />ma ji ma gi ran ge nu gu bo da <br />si reun geol al ja na <br />han beon man na reul ggok a na jwo <br />geu reo myeon neo do da si dda ddeu tae jil gga <br />ni gye jeo reul da sa rang ha neun geon <br />swip ji a nat ji man <br />u ri ga geu ryeo on si gan deu reun <br />nae gen jeon bu yeo seo <br />neo neun nae se sang i eo seo <br />geu mu eot gwa do ba ggul su eob neun <br />geu se sang i mu neo ji myeon <br />eo ddeo ke sa ra gal su it ge seo <br />a reum da wot deon sun gan deu ri <br />sa ra jyeo ga neun geol bo myeon seo <br />na neun han cha meul ul go i seot deon geon man ga ta <br />cham o rae do neol man na wan neun de <br />u ri ui si ga neun eon je bu teo <br />meom chwo jyeo in neun ji <br />sa rang han dan geu heun han mal jo cha do <br />a jik do neo e gen eo ryeo un il il gga <br />ni gye jeo reul da sa rang ha neun geon <br />swip ji a nat ji man <br />u ri ga geu ryeo on si gan deu reun <br />nae gen jeon bu yeo seo <br />neo neun nae se sang i eo seo <br />geu mu eot gwa do ba ggul su eob neun <br />geu se sang i mu neo ji myeon <br />eo ddeo ke sa ra gal su it ge seo <br />a reum da wot deon sun gan deu ri <br />sa ra jyeo ga neun geol bo myeon seo <br />na neun han cha meul ul go i seot deon geon man ga ta <br />a reum dap deon bam ha neu ri <br />nae ge u seo ju eot deon ni nun bi chi <br />hoe saek bi cheu ro chil hae seo <br />nu na pi ja ggu heu ryeo jyeo <br />dwi do ra geo reo ga neun geu gi re <br />a deu kaet deon u ri chu eo gi <br />han sun gan sa ra jyeo da heu teo jyeo <br />neo neun nae se sang i eo seo <br />geu mu eot gwa do ba ggul su eob neun <br />geu se sang i mu neo ji myeon <br />eo ddeo ke sa ra gal su it ge seo <br />a reum da wot deon sun gan deu ri <br />sa ra jyeo ga neun geol bo myeon seo <br />na neun han cha meul ul go i seot deon geon man ga ta <br />neol gi da ri go i seul geot ga ta </p>', '0', '2024-06-18 15:23:37', '2024-06-18 15:24:15');
INSERT INTO `kpop_song_transliteration` VALUES ('1802966477337522177', '1802966460379951106', '<p>Seoul cheo eum man nan nat seon do si e seo <br />sa ram deul sa i e bi chi na neun <br />gi ta reul men meot jin so nyeon gwa yeah<br />sa rang e bba jin love story<br />deu reo bol lae ji na chi ge do <br />so seol ga teun i ya gi il geo ya <br />Maybe someday<br />ggo chi pi ddeo reo ji neun geu na re <br />u ri ga man nan geu go se seo <br />meol li seo gi ta so ri ga deul li myeon na <br />neo reul wi hae no rael bu reul geo ya <br />City of light<br />bi chi ga deu kan i do si e seo <br />nu neul ban jja gi myeo ggu meul ggu neun <br />ma i keu reul jwin meot jin so nyeo wa <br />sa rang e bba jin love story<br />Maybe someday<br />ggo chi pi ddeo reo ji neun geu na re <br />u ri ga man nan geu go se seo <br />meol li seo gi ta so ri ga deul li myeon na <br />neo reul wi hae no rael bu reul geo ya <br />eon jen ga sa rang haet deon geu do si e seo <br />nae mok so ril gi eo kan da myeon <br />geu ddae u ri man na ja my seoul<br />Someday<br />neo wa na ggum ggwo wat deon geu na re <br />seo ro ga ma ju han go se seo <br />hwan ha ge mi so ji eul su it da myeon na <br />neo reul wi hae no rae hae <br />Someday geu ddae geu na re <br />u ri ga man nan geu go se seo <br />meol li seo ni mok so ri ga deul li myeon na <br />neo reul wi hae no rael bu reul geo ya </p>', '0', '2024-06-18 15:28:00', '2024-06-18 15:29:00');
INSERT INTO `kpop_song_transliteration` VALUES ('1802970725848707074', '1802970579719155714', '<p>&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;민정이&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;<br />那嫩 汉得 内卡 一 sae三诶 萨拉几kir 吧类so<br />wuon sae三一 no木那 看m看咩 咩一r 吧么 日r don 耐r<br />掐拉力 内噶 萨拉几miyon 吗额米 piu耐r噶<br />目睹噶 耐r 吧啦不嫩 西so你 no木那 图六我<br />啊冷m大b给 啊冷m大b don 哥 西jio日r 那 那怕so<br />撒浪吧特r 素 哦b so don 内噶 no木那 洗咯so<br />onm妈嫩 阿爸嫩 哒 那满 吧啦不嫩得<br />内 吗额门 哥隆 给 啊您得 掐古满 莫咯满 噶<br />哦do剋*4<br />&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;민정이&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;<br />洗噶你 呀gi啦嫩 马力 内给 囧买r 吗to啦古<br />哈鲁噶 七拿miyon 七耐r素路 do 那阿几to啦古<br />肯得 卡个门 no木 haen不卡mion 图 啊怕呜r噶 bua<br />内噶 噶亲 一 haen不特日r 怒共噶噶 卡就该r噶 bua<br />啊冷m大wuon 那冷m大b don 哥 gi哦gi 那 那怕so<br />啊喷 满啃 吗怕嘿都 萨拉唧唧日r 阿拉so<br />亲故德冷 撒浪m德冷 哒 那满 吧啦不嫩得<br />内 木色本 歌龙 给 啊您得 掐古满 莫咯满 噶<br />&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;민정이&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;<br />克雷度 难 哦jio miyon<br />内噶 一 sae三诶 白r跟 脾气拉肚 对r噶 bua<br />哦jio miyon 哥 木凳 那pe么r 内地古so啦度<br />掐r给 比起r 内不r噶 bua<br />普gi海r 素噶 哦b so<br />哈鲁度 满m piu你 讲m德r 素噶 哦b don 内噶<br />一咯剋啦度 一咯so 普六谷 哈miyon<br />内噶 耐r 掐架句r噶 bua<br />&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;민정이&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;&minus;<br />啊啊啊啊啊啊啊啊啊～哎<br />啊啊啊啊啊啊啊～哎<br />啊啊啊啊啊啊啊啊啊～哎<br />啊啊啊啊啊啊啊～哎<br />哦r吗那 哦r吗那 啊怕色r噶<br />哦r吗那 哦r吗那 啊怕色r噶<br />哦r吗那 哦r吗那 哦r吗那 怕类色r噶</p>', '0', '2024-06-18 15:44:53', '2024-06-18 15:44:53');

-- ----------------------------
-- Table structure for manage_admin
-- ----------------------------
DROP TABLE IF EXISTS `manage_admin`;
CREATE TABLE `manage_admin` (
  `id` char(19) NOT NULL COMMENT '管理员id',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '真实姓名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 普通管理员admin 1是超级管理员super_admin',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员表';

-- ----------------------------
-- Records of manage_admin
-- ----------------------------
INSERT INTO `manage_admin` VALUES ('1760205003435417602', '13861960190', 'e10adc3949ba59abbe56e057f20f883e', '张明鉴', 'https://ts1.cn.mm.bing.net/th/id/R-C.9554ab64073eaa3732934a516a804c8b?rik=eKTVaw7K04LUvw&riu=http%3a%2f%2fpic.616pic.com%2fys_b_img%2f00%2f08%2f10%2fcwqWnfsDXU.jpg&ehk=e5wIUw6y254vcjn3r47Sq6TbzqGTHiZjiWQKQhjVwAk%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1', '1', '0', '2024-02-21 15:29:11', '2024-02-21 15:29:11');
INSERT INTO `manage_admin` VALUES ('1760299179493748737', '15896226579', 'e10adc3949ba59abbe56e057f20f883e', '黄静辉', 'https://ts1.cn.mm.bing.net/th/id/R-C.9554ab64073eaa3732934a516a804c8b?rik=eKTVaw7K04LUvw&riu=http%3a%2f%2fpic.616pic.com%2fys_b_img%2f00%2f08%2f10%2fcwqWnfsDXU.jpg&ehk=e5wIUw6y254vcjn3r47Sq6TbzqGTHiZjiWQKQhjVwAk%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1', '0', '0', '2024-02-21 21:43:24', '2024-02-21 21:43:24');
INSERT INTO `manage_admin` VALUES ('1760693239479971842', '13862855998', 'e10adc3949ba59abbe56e057f20f883e', '张勤', 'https://ts1.cn.mm.bing.net/th/id/R-C.9554ab64073eaa3732934a516a804c8b?rik=eKTVaw7K04LUvw&riu=http%3a%2f%2fpic.616pic.com%2fys_b_img%2f00%2f08%2f10%2fcwqWnfsDXU.jpg&ehk=e5wIUw6y254vcjn3r47Sq6TbzqGTHiZjiWQKQhjVwAk%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1', '0', '0', '2024-02-22 23:49:15', '2024-02-22 23:49:15');

-- ----------------------------
-- Table structure for ucenter_member
-- ----------------------------
DROP TABLE IF EXISTS `ucenter_member`;
CREATE TABLE `ucenter_member` (
  `id` char(19) NOT NULL COMMENT '会员id',
  `openid` varchar(128) DEFAULT NULL COMMENT '微信openid',
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint unsigned DEFAULT NULL COMMENT '性别 0 未设置/秘密 , 1 女，2 男',
  `age` tinyint unsigned DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `sign` varchar(100) DEFAULT NULL COMMENT '用户签名',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会员表';

-- ----------------------------
-- Records of ucenter_member
-- ----------------------------
INSERT INTO `ucenter_member` VALUES ('1802977890504183809', null, '13861960190', 'e10adc3949ba59abbe56e057f20f883e', 'donk66', '0', null, 'default/default.jpg', null, '0', '2024-06-18 16:13:21', '2024-06-18 16:13:21');
INSERT INTO `ucenter_member` VALUES ('1802978548359794689', null, '15896226579', 'e10adc3949ba59abbe56e057f20f883e', 'NMIXX大发', '0', null, 'default/default.jpg', null, '0', '2024-06-18 16:15:58', '2024-06-18 16:15:58');
