-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: animalsys
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rules`
--

DROP TABLE IF EXISTS `rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rules` (
  `idrules` int(11) NOT NULL AUTO_INCREMENT,
  `have` varchar(128) DEFAULT NULL,
  `not_have` varchar(128) DEFAULT NULL,
  `conclution` varchar(128) DEFAULT NULL,
  `is_use` int(2) NOT NULL,
  PRIMARY KEY (`idrules`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rules`
--

LOCK TABLES `rules` WRITE;
/*!40000 ALTER TABLE `rules` DISABLE KEYS */;
INSERT INTO `rules` VALUES (1,'有毛发',NULL,'哺乳动物',1),(2,'有奶',NULL,'哺乳动物',1),(3,'有羽毛',NULL,'鸟',1),(4,'会飞 会下蛋',NULL,'鸟',1),(5,'吃肉',NULL,'食肉动物',1),(6,'有犬齿 有爪 眼盯前方',NULL,'食肉动物',1),(7,'哺乳动物 有蹄',NULL,'有蹄类动物',1),(8,'哺乳动物 反刍动物',NULL,'有蹄类动物',1),(9,'哺乳动物 食肉动物 黄褐色 暗斑点',NULL,'金钱豹',1),(10,'哺乳动物 食肉动物 黄褐色 黑色条纹',NULL,'虎',1),(11,'有蹄类动物 长脖子 长腿 暗斑点',NULL,'长颈鹿',1),(12,'有蹄类动物 黑色条纹',NULL,'斑马',1),(13,'鸟 长脖子 长腿 不会飞 黑白二色',NULL,'鸵鸟',1),(14,'鸟 会游泳 不会飞 黑白二色',NULL,'企鹅',1),(15,'鸟 善飞',NULL,'信天翁',1);
/*!40000 ALTER TABLE `rules` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-22 18:01:28
