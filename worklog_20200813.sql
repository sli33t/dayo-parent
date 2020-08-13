/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 8.0.20 : Database - worklog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`worklog` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `worklog`;

/*Table structure for table `clientdetails` */

DROP TABLE IF EXISTS `clientdetails`;

CREATE TABLE `clientdetails` (
  `appId` varchar(256) NOT NULL,
  `resourceIds` varchar(256) DEFAULT NULL,
  `appSecret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `grantTypes` varchar(256) DEFAULT NULL,
  `redirectUrl` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int DEFAULT NULL,
  `refresh_token_validity` int DEFAULT NULL,
  `additionalInformation` varchar(4096) DEFAULT NULL,
  `autoApproveScopes` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `clientdetails` */

/*Table structure for table `oauth_access_token` */

DROP TABLE IF EXISTS `oauth_access_token`;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_access_token` */

/*Table structure for table `oauth_approvals` */

DROP TABLE IF EXISTS `oauth_approvals`;

CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NULL DEFAULT NULL,
  `lastModifiedAt` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_approvals` */

/*Table structure for table `oauth_client_details` */

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int DEFAULT NULL,
  `refresh_token_validity` int DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`autoapprove`) values ('myapp','order','{bcrypt}$2a$10$Y8OCt2WQRwBIw8pHpRDQGO9hSdi848Y3FTu419B6.M7/79fGmoKyu','all','password,refresh_token',NULL,'ADMIN',10800,2592000,NULL,NULL);

/*Table structure for table `oauth_client_token` */

DROP TABLE IF EXISTS `oauth_client_token`;

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_client_token` */

/*Table structure for table `oauth_code` */

DROP TABLE IF EXISTS `oauth_code`;

CREATE TABLE `oauth_code` (
  `CODE` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_code` */

/*Table structure for table `oauth_refresh_token` */

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_refresh_token` */

/*Table structure for table `tb_area` */

DROP TABLE IF EXISTS `tb_area`;

CREATE TABLE `tb_area` (
  `AREA_ID` varchar(32) NOT NULL COMMENT '区域编号',
  `AREA_NAME` varchar(50) NOT NULL COMMENT '区域名称',
  `DELETE_FLAG` smallint DEFAULT NULL COMMENT '删除标志',
  `DELETE_DATE` date DEFAULT NULL COMMENT '删除日期',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `ROW_VERSION` smallint DEFAULT NULL COMMENT '行版本号',
  PRIMARY KEY (`AREA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_area` */

insert  into `tb_area`(`AREA_ID`,`AREA_NAME`,`DELETE_FLAG`,`DELETE_DATE`,`CREATE_TIME`,`ROW_VERSION`) values ('1284041314236665858','北京',NULL,NULL,'2020-07-17 16:25:00',0),('1284041314236665859','天津',NULL,NULL,'2020-07-17 16:25:00',0),('1284041314236665860','广州',NULL,NULL,'2020-07-17 16:25:00',0),('1284041314236665861','深圳',NULL,NULL,'2020-07-17 16:25:00',0);

/*Table structure for table `tb_customer` */

DROP TABLE IF EXISTS `tb_customer`;

CREATE TABLE `tb_customer` (
  `CUSTOMER_ID` varchar(32) NOT NULL,
  `CUSTOMER_NAME` varchar(200) NOT NULL,
  `TEL_NO` varchar(20) DEFAULT NULL,
  `ADDRESS` varchar(500) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `AREA_ID` varchar(32) DEFAULT NULL,
  `VERSION_ID` varchar(32) DEFAULT NULL,
  `DELETE_FLAG` smallint DEFAULT NULL,
  `DELETE_DATE` date DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `ROW_VERSION` smallint DEFAULT NULL,
  PRIMARY KEY (`CUSTOMER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_customer` */

insert  into `tb_customer`(`CUSTOMER_ID`,`CUSTOMER_NAME`,`TEL_NO`,`ADDRESS`,`EMAIL`,`AREA_ID`,`VERSION_ID`,`DELETE_FLAG`,`DELETE_DATE`,`CREATE_TIME`,`ROW_VERSION`) values ('1284385508197154818','北京A客户','18801031913','北京市朝阳区国贸桥','sss@163.com','1284041314236665858',NULL,0,NULL,'2020-07-18 16:04:31',5),('1284391147455655938','北京B客户','18801031911','北京市海淀区万泉河路','aaa@163.com','1284041314236665858','1284684807057510500',0,NULL,'2020-07-18 15:59:47',2);

/*Table structure for table `tb_devtask` */

DROP TABLE IF EXISTS `tb_devtask`;

CREATE TABLE `tb_devtask` (
  `DEVTASK_ID` varchar(32) NOT NULL COMMENT '开发任务编号',
  `FEEDBACK_ID` bigint NOT NULL COMMENT '客户反馈编号',
  `DEVELOP_USER_ID` varchar(32) NOT NULL COMMENT '开发人编号',
  `RECEIVED` smallint DEFAULT NULL COMMENT '已接收',
  `RECEIVE_TIME` datetime DEFAULT NULL COMMENT '接收时间',
  `RECEIVE_DATE` date DEFAULT NULL COMMENT '接收日期',
  `FINISHED` smallint DEFAULT NULL COMMENT '开发完成',
  `FINISH_DATE` date DEFAULT NULL COMMENT '完成日期',
  `FINISH_TIME` datetime DEFAULT NULL COMMENT '完成时间',
  `FEEDBACK_TIME` datetime DEFAULT NULL COMMENT '客户反馈时间',
  `CREATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '创建人编号',
  `TASK_TIME` datetime DEFAULT NULL COMMENT '分配任务时间',
  `TASK_DATE` date DEFAULT NULL COMMENT '分配任务日期',
  `PLAN_HOUR` decimal(18,2) DEFAULT NULL COMMENT '计划工时',
  `REAL_HOUR` decimal(18,2) DEFAULT NULL COMMENT '实际工时',
  `FINISH_TEXT` text COMMENT '完成内容',
  `TASK_TEXT` text COMMENT '分配内容',
  `IMPORT_USER_ID` varchar(32) DEFAULT NULL COMMENT '引入人',
  `IS_PROBLEM` smallint DEFAULT NULL COMMENT '是否是问题',
  `NEED_TEST` smallint DEFAULT NULL COMMENT '是否需要测试',
  `TEST_RECEIVED` smallint DEFAULT NULL COMMENT '测试是否接手',
  PRIMARY KEY (`DEVTASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_devtask` */

insert  into `tb_devtask`(`DEVTASK_ID`,`FEEDBACK_ID`,`DEVELOP_USER_ID`,`RECEIVED`,`RECEIVE_TIME`,`RECEIVE_DATE`,`FINISHED`,`FINISH_DATE`,`FINISH_TIME`,`FEEDBACK_TIME`,`CREATE_USER_ID`,`TASK_TIME`,`TASK_DATE`,`PLAN_HOUR`,`REAL_HOUR`,`FINISH_TEXT`,`TASK_TEXT`,`IMPORT_USER_ID`,`IS_PROBLEM`,`NEED_TEST`,`TEST_RECEIVED`) values ('1285201935938383874',13,'1284348006799261697',NULL,NULL,NULL,NULL,NULL,NULL,'2020-07-20 12:59:39','1283591768750784513','2020-07-20 21:16:31','2020-07-20','11.55',NULL,NULL,'按反馈修改',NULL,NULL,NULL,NULL),('1285202162019758082',4,'1283591768750784513',NULL,NULL,NULL,2,'2020-07-20','2020-07-20 22:59:35','2020-07-19 16:31:23','1283591768750784513','2020-07-20 21:17:27','2020-07-20','1.00',NULL,'开发退回','按要求开发',NULL,NULL,NULL,NULL),('1285375535404412929',12,'1283591768750784513',NULL,NULL,NULL,1,'2020-07-21','2020-07-21 09:20:13','2020-07-19 20:58:11','1283591768750784513','2020-07-21 08:46:22','2020-07-21','11.00','12.00','已经按需求进行开发','按需求开发',NULL,NULL,NULL,NULL),('1285407385120210946',11,'1283591768750784513',NULL,NULL,NULL,2,'2020-07-21','2020-07-21 14:17:12','2020-07-19 20:38:13','1283591768750784513','2020-07-21 10:52:56','2020-07-21','1.00',NULL,'开发退回','1',NULL,NULL,NULL,NULL),('1285407425003847682',10,'1283591768750784513',NULL,NULL,NULL,1,'2020-07-21','2020-07-21 11:26:11','2020-07-19 20:37:00','1283591768750784513','2020-07-21 10:53:06','2020-07-21','11.00','13.00','22','1','1284348006799261697',1,NULL,NULL),('1285407451952250882',7,'1283591768750784513',NULL,NULL,NULL,2,'2020-07-21','2020-07-21 14:17:06','2020-07-19 20:26:28','1283591768750784513','2020-07-21 10:53:12','2020-07-21','11.00',NULL,'开发退回','1',NULL,NULL,NULL,NULL),('1285429919089811457',6,'1284348006799261697',NULL,NULL,NULL,0,'2020-07-21','2020-07-21 12:22:55','2020-07-19 20:25:08','1283591768750784513','2020-07-21 12:22:29','2020-07-21','1.00','2.00','11','2','1283591768750784513',1,NULL,NULL),('1285458206155468802',3,'1283591768750784513',NULL,NULL,NULL,1,'2020-07-21','2020-07-21 17:10:05','2020-07-19 16:30:00','1284348006799261697','2020-07-21 14:14:53','2020-07-21','10.00','14.00','243','请按要求进行开发','1284348006799261697',1,1,NULL),('1285458349139292162',4,'1283591768750784513',NULL,NULL,NULL,1,'2020-07-21','2020-07-21 17:09:37','2020-07-19 16:31:23','1284348006799261697','2020-07-21 14:15:27','2020-07-21','10.00','16.00','324','再次开发','1284348006799261697',1,1,1),('1285458670267789313',2,'1283591768750784513',NULL,NULL,NULL,2,'2020-07-21','2020-07-21 14:17:08','2020-07-19 16:25:27','1284348006799261697','2020-07-21 14:16:35','2020-07-21','22.00','0.00','开发退回','1',NULL,NULL,NULL,NULL),('1285462390682468353',7,'1283591768750784513',NULL,NULL,NULL,0,'2020-07-21','2020-07-21 16:52:02','2020-07-19 20:26:28','1283591768750784513','2020-07-21 14:31:25','2020-07-21','20.00','15.00','11','1','1284348006799261697',1,1,NULL);

/*Table structure for table `tb_feedback` */

DROP TABLE IF EXISTS `tb_feedback`;

CREATE TABLE `tb_feedback` (
  `FEEDBACK_ID` bigint NOT NULL AUTO_INCREMENT,
  `PROBLEM_TYPE` smallint DEFAULT NULL,
  `FEEDBACK_TYPE` smallint DEFAULT NULL,
  `PRIORITY` smallint DEFAULT NULL,
  `REQUIRE_DATE` date DEFAULT NULL,
  `PROBLEM_TEXT` text,
  `CREATE_USER_ID` varchar(32) DEFAULT NULL,
  `CUSTOMER_ID` varchar(32) NOT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFY_USER_ID` varchar(32) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  `ROW_VERSION` smallint DEFAULT NULL,
  `FINISHED` smallint DEFAULT NULL,
  `FINISH_DATE` date DEFAULT NULL,
  `FINISH_TIME` datetime DEFAULT NULL,
  `STATUS` smallint DEFAULT NULL,
  PRIMARY KEY (`FEEDBACK_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `tb_feedback` */

insert  into `tb_feedback`(`FEEDBACK_ID`,`PROBLEM_TYPE`,`FEEDBACK_TYPE`,`PRIORITY`,`REQUIRE_DATE`,`PROBLEM_TEXT`,`CREATE_USER_ID`,`CUSTOMER_ID`,`CREATE_TIME`,`MODIFY_USER_ID`,`MODIFY_TIME`,`ROW_VERSION`,`FINISHED`,`FINISH_DATE`,`FINISH_TIME`,`STATUS`) values (1,1,1,1,'2020-07-22','登录报错','1283591768750784513','1284385508197154818','2020-07-19 16:19:11',NULL,NULL,NULL,NULL,NULL,NULL,1),(2,0,1,1,'2020-07-22','增加一个查询按钮','1283591768750784513','1284391147455655938','2020-07-19 16:25:27','1283591768750784513','2020-07-19 20:28:54',1,NULL,NULL,NULL,3),(3,0,1,1,'2020-07-22','增加一个查询按钮','1283591768750784513','1284391147455655938','2020-07-19 16:30:00',NULL,NULL,0,NULL,NULL,NULL,5),(4,1,1,1,'2020-07-22','增加一个按钮','1283591768750784513','1284391147455655938','2020-07-19 16:31:23',NULL,NULL,0,NULL,NULL,NULL,6),(5,1,1,1,'2020-07-22','111','1283591768750784513','1284385508197154818','2020-07-19 16:32:22',NULL,NULL,NULL,NULL,NULL,NULL,1),(6,0,0,0,'2020-07-22','增加一个查询按钮','1283591768750784513','1284391147455655938','2020-07-19 20:25:08',NULL,NULL,0,1,'2020-07-21','2020-07-21 12:22:55',5),(7,0,0,0,'2020-07-22','增加一个查询按钮','1283591768750784513','1284391147455655938','2020-07-19 20:26:28',NULL,NULL,0,1,'2020-07-21','2020-07-21 16:39:43',5),(8,0,0,0,'2020-07-23','','1283591768750784513','1284385508197154818','2020-07-19 20:32:35',NULL,NULL,0,NULL,NULL,NULL,1),(9,NULL,1,2,'2020-07-23','','1283591768750784513','1284385508197154818','2020-07-19 20:33:04',NULL,NULL,0,NULL,NULL,NULL,1),(10,1,1,2,'2020-07-23','2222','1283591768750784513','1284391147455655938','2020-07-19 20:37:00','1283591768750784513','2020-07-20 09:07:04',1,1,'2020-07-21','2020-07-21 11:26:11',5),(11,1,1,3,'2020-07-23','88','1283591768750784513','1284391147455655938','2020-07-19 20:38:13','1283591768750784513','2020-07-19 20:58:27',2,NULL,NULL,NULL,3),(12,0,1,3,'2020-07-23','1122','1283591768750784513','1284391147455655938','2020-07-19 20:58:11',NULL,NULL,0,1,'2020-07-21','2020-07-21 09:20:16',5),(13,0,0,0,'2020-07-20','增加一个按钮吧','1283591768750784513','1284391147455655938','2020-07-20 12:59:39','1283591768750784513','2020-07-20 13:03:43',1,NULL,NULL,NULL,2);

/*Table structure for table `tb_file` */

DROP TABLE IF EXISTS `tb_file`;

CREATE TABLE `tb_file` (
  `FILE_ID` varchar(32) NOT NULL,
  `FILE_NAME` varchar(200) NOT NULL,
  `FILE_URL` varchar(200) NOT NULL,
  `DELETE_FLAG` smallint DEFAULT NULL,
  `DELETE_DATE` date DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `USER_ID` varchar(32) DEFAULT NULL,
  `FILE_TYPE` smallint DEFAULT NULL,
  PRIMARY KEY (`FILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_file` */

insert  into `tb_file`(`FILE_ID`,`FILE_NAME`,`FILE_URL`,`DELETE_FLAG`,`DELETE_DATE`,`CREATE_TIME`,`USER_ID`,`FILE_TYPE`) values ('202007182032051684','测试附件2.docx','http://www.baidu.com',0,'2020-07-18','2020-07-18 20:32:04','1283591768750784513',2),('202007191136353043','可研状态.xlsx','http://www.baidu.com',0,NULL,'2020-07-19 11:36:35','1283591768750784513',2);

/*Table structure for table `tb_role` */

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `ROLE_ID` varchar(32) NOT NULL COMMENT '岗位编号',
  `ROLE_NAME` varchar(40) NOT NULL COMMENT '岗位名称',
  `ROLE_TYPE` smallint DEFAULT NULL COMMENT '岗位类型',
  `ROW_VERSION` smallint DEFAULT NULL COMMENT '行版本号',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETE_FLAG` smallint DEFAULT NULL COMMENT '删除标志',
  `DELETE_DATE` date DEFAULT NULL COMMENT '删除日期',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_role` */

insert  into `tb_role`(`ROLE_ID`,`ROLE_NAME`,`ROLE_TYPE`,`ROW_VERSION`,`CREATE_TIME`,`DELETE_FLAG`,`DELETE_DATE`) values ('1283777552439054337','开发',1,0,'2020-07-16 22:56:33',0,NULL),('1284348070603014145','开发部经理',0,0,'2020-07-18 12:43:36',0,NULL),('1284348103343751169','测试工程师',1,0,'2020-07-18 12:43:44',0,NULL);

/*Table structure for table `tb_testtask` */

DROP TABLE IF EXISTS `tb_testtask`;

CREATE TABLE `tb_testtask` (
  `TESTTASK_ID` varchar(32) NOT NULL COMMENT '测试编号',
  `FEEDBACK_ID` bigint NOT NULL COMMENT '客反单号',
  `DEVTASK_ID` varchar(32) DEFAULT NULL COMMENT '开发编号',
  `TEST_USER_ID` varchar(32) DEFAULT NULL COMMENT '测试人员',
  `FEEDBACK_TIME` datetime DEFAULT NULL COMMENT '客反时间',
  `TEST_ARRANGE` smallint DEFAULT NULL COMMENT '分配测试',
  `TESTTASK_TIME` datetime DEFAULT NULL COMMENT '测试分配时间',
  `TESTTASK_DATE` date DEFAULT NULL COMMENT '测试分配日期',
  `RECEIVED` smallint DEFAULT NULL COMMENT '测试接收',
  `RECEIVE_TIME` datetime DEFAULT NULL COMMENT '测试接收时间',
  `RECEIVE_DATE` date DEFAULT NULL COMMENT '测试接收日期',
  `FINISHED` smallint DEFAULT NULL COMMENT '测试完成',
  `FINISH_TIME` datetime DEFAULT NULL COMMENT '测试完成时间',
  `FINISH_DATE` date DEFAULT NULL COMMENT '测试完成日期',
  `PLAN_HOUR` double(18,2) DEFAULT NULL COMMENT '计划测试工时',
  `REAL_HOUR` double(18,2) DEFAULT NULL COMMENT '实际测试工时',
  `TEST_TEXT` text COMMENT '测试内容',
  `IS_PROBLEM` smallint DEFAULT NULL COMMENT '是否是问题',
  PRIMARY KEY (`TESTTASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_testtask` */

insert  into `tb_testtask`(`TESTTASK_ID`,`FEEDBACK_ID`,`DEVTASK_ID`,`TEST_USER_ID`,`FEEDBACK_TIME`,`TEST_ARRANGE`,`TESTTASK_TIME`,`TESTTASK_DATE`,`RECEIVED`,`RECEIVE_TIME`,`RECEIVE_DATE`,`FINISHED`,`FINISH_TIME`,`FINISH_DATE`,`PLAN_HOUR`,`REAL_HOUR`,`TEST_TEXT`,`IS_PROBLEM`) values ('1285502179259539457',4,'1285458349139292162','1283591768750784513',NULL,0,NULL,NULL,NULL,NULL,NULL,2,'2020-07-22 11:37:01','2020-07-22',11.00,NULL,'测试退回',NULL),('1285502297459220482',3,'1285458206155468802',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `USER_ID` varchar(32) NOT NULL COMMENT '用户编号',
  `USER_NAME` varchar(20) DEFAULT NULL COMMENT '用户名称',
  `TEL_NO` varchar(20) NOT NULL COMMENT '电话号码',
  `EMAIL` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `PASSWORD` varchar(32) DEFAULT NULL COMMENT '密码',
  `USER_TYPE` smallint NOT NULL COMMENT '用户类型',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETE_FLAG` smallint DEFAULT NULL COMMENT '删除标志',
  `DELETE_DATE` date DEFAULT NULL COMMENT '删除日期',
  `ROW_VERSION` smallint DEFAULT NULL COMMENT '行版本号',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`USER_ID`,`USER_NAME`,`TEL_NO`,`EMAIL`,`PASSWORD`,`USER_TYPE`,`CREATE_TIME`,`DELETE_FLAG`,`DELETE_DATE`,`ROW_VERSION`) values ('1283591768750784513','林彬','18801031984','sli33t@163.com','951927B2573A9324287C8A56242C7136',0,'2020-07-16 13:03:58',0,NULL,2),('1283950663524450305','ss','18801031985','sli33t1@163.com','794B7EA6845E2D6543628E095A7C1F8A',0,'2020-07-17 10:24:26',0,NULL,0),('1284348006799261697','林2','18801031986','sli33t111@163.com','E6E64E4AA5DF3402CA7C795519EBD88D',1,'2020-07-18 12:43:21',0,NULL,0),('1284382882000908290','ss2','18801031981','18801031981@11.com','8C0F24A4ADC0BA4A1677D180DFD894FF',1,'2020-07-18 15:01:55',0,NULL,0);

/*Table structure for table `tb_user_role` */

DROP TABLE IF EXISTS `tb_user_role`;

CREATE TABLE `tb_user_role` (
  `ID` varchar(32) NOT NULL,
  `USER_ID` varchar(32) NOT NULL,
  `ROLE_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_user_role` */

insert  into `tb_user_role`(`ID`,`USER_ID`,`ROLE_ID`) values ('1284016078069977089','1283950663524450305','1283777552439054337');

/*Table structure for table `tb_version` */

DROP TABLE IF EXISTS `tb_version`;

CREATE TABLE `tb_version` (
  `VERSION_ID` varchar(32) NOT NULL,
  `VERSION_NAME` varchar(50) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `DELETE_FLAG` smallint DEFAULT NULL,
  `DELETE_DATE` date DEFAULT NULL,
  PRIMARY KEY (`VERSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_version` */

insert  into `tb_version`(`VERSION_ID`,`VERSION_NAME`,`CREATE_TIME`,`DELETE_FLAG`,`DELETE_DATE`) values ('1284684807057510500','1.0版本','2020-07-19 11:00:00',0,NULL),('1284684807057510501','2.0版本','2020-07-19 11:00:00',0,NULL);

/*Table structure for table `tb_workhour` */

DROP TABLE IF EXISTS `tb_workhour`;

CREATE TABLE `tb_workhour` (
  `WORKHOUR_ID` varchar(32) NOT NULL,
  `USER_ID` varchar(32) DEFAULT NULL,
  `DEVELOP_USER` varchar(32) DEFAULT NULL,
  `WORK_COUNT` decimal(18,2) DEFAULT NULL,
  `PLAN_HOUR` decimal(18,2) DEFAULT NULL,
  `REAL_HOUR` decimal(18,2) DEFAULT NULL,
  `DELAY_HOUR` decimal(18,2) DEFAULT NULL,
  PRIMARY KEY (`WORKHOUR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_workhour` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
