/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.7.9-log : Database - cloud_db01
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cloud_db01` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cloud_db01`;

/*Table structure for table `dept` */

DROP TABLE IF EXISTS `dept`;

CREATE TABLE `dept` (
  `dept_no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门编号主键',
  `dept_name` varchar(30) DEFAULT NULL COMMENT '部门名称',
  `db_source` varchar(30) DEFAULT 'DATABASE()' COMMENT '所在数据库',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(2) DEFAULT '0' COMMENT '删除标识',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`dept_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Data for the table `dept` */

insert  into `dept`(`dept_no`,`dept_name`,`db_source`,`create_time`,`update_time`,`del_flag`,`version`) values 
(8,'人事部','cloud_db01','2019-03-02 11:30:15','2019-03-02 13:20:30',0,3),
(9,'行政部','cloud_db01','2019-03-02 11:30:15',NULL,0,1),
(10,'市场部','cloud_db01','2019-03-02 11:30:15',NULL,0,1),
(11,'销售部','cloud_db01','2019-03-02 11:30:15',NULL,0,1),
(12,'财务部','cloud_db01','2019-03-02 11:30:15',NULL,0,1),
(13,'研发部','cloud_db01','2019-03-02 11:30:15',NULL,0,1),
(14,'设备部','cloud_db01','2019-03-02 11:30:15',NULL,0,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
