create database `miniServer` default character set utf8 collate utf8_general_ci;
use miniServer;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `accountId` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '账号Id',
  `updatedAt` bigint(20) DEFAULT '0' COMMENT '更新时间戳',
  `idCard` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '正式姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '账号密码',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`accountId`, `updatedAt`, `idCard`, `name`, `password`, `username`)
VALUES
	(X'646476',111,X'646476',X'646476',X'646476',X'646476');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


