/*!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19  Distrib 10.11.8-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: magazinedb.clm6muacw4a1.us-east-2.rds.amazonaws.com    Database: magazinedb
-- ------------------------------------------------------
-- Server version	11.4.4-MariaDB-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ad_has_categories`
--

DROP TABLE IF EXISTS `ad_has_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_has_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Ad` int(11) NOT NULL,
  `FK_Category` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Category` (`FK_Category`),
  KEY `FK_Ad` (`FK_Ad`),
  CONSTRAINT `ad_has_categories_ibfk_2` FOREIGN KEY (`FK_Category`) REFERENCES `categories` (`id`),
  CONSTRAINT `ad_has_categories_ibfk_3` FOREIGN KEY (`FK_Ad`) REFERENCES `ads` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_has_categories`
--

LOCK TABLES `ad_has_categories` WRITE;
/*!40000 ALTER TABLE `ad_has_categories` DISABLE KEYS */;
INSERT INTO `ad_has_categories` VALUES
(1,1,10),
(2,2,9),
(3,3,9),
(4,4,10),
(5,4,5),
(6,5,10),
(7,5,5),
(8,6,10);
/*!40000 ALTER TABLE `ad_has_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_has_labels`
--

DROP TABLE IF EXISTS `ad_has_labels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_has_labels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Ad` int(11) NOT NULL,
  `FK_Label` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `adds_has_labels_ibfk_2` (`FK_Label`),
  KEY `FK_Ad` (`FK_Ad`),
  CONSTRAINT `ad_has_labels_ibfk_1` FOREIGN KEY (`FK_Ad`) REFERENCES `ads` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_has_labels`
--

LOCK TABLES `ad_has_labels` WRITE;
/*!40000 ALTER TABLE `ad_has_labels` DISABLE KEYS */;
INSERT INTO `ad_has_labels` VALUES
(1,1,4),
(2,1,3),
(3,1,6),
(4,2,3),
(5,3,5),
(6,4,2),
(7,4,5),
(8,4,4),
(9,4,3),
(10,4,1),
(11,4,6),
(12,5,4),
(13,5,6),
(14,5,1),
(15,5,5),
(16,5,2),
(17,6,6),
(18,6,4),
(19,6,3),
(20,6,2),
(21,6,5),
(22,6,1);
/*!40000 ALTER TABLE `ad_has_labels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_types`
--

DROP TABLE IF EXISTS `ad_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` enum('TEXT','IMAGE','VIDEO') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_types`
--

LOCK TABLES `ad_types` WRITE;
/*!40000 ALTER TABLE `ad_types` DISABLE KEYS */;
INSERT INTO `ad_types` VALUES
(1,'TEXT'),
(2,'IMAGE'),
(3,'VIDEO');
/*!40000 ALTER TABLE `ad_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ads`
--

DROP TABLE IF EXISTS `ads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ads` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Period` int(11) NOT NULL,
  `FK_Ad_Types` int(11) NOT NULL,
  `FK_User` int(11) NOT NULL,
  `content` text DEFAULT NULL,
  `path` varchar(100) DEFAULT NULL,
  `is_enabled` tinyint(1) DEFAULT 1,
  `number_views` int(11) DEFAULT 0,
  `date_created` date NOT NULL,
  `date_ended` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Period` (`FK_Period`),
  KEY `FK_User` (`FK_User`),
  KEY `FK_Ad_Types` (`FK_Ad_Types`),
  CONSTRAINT `ads_ibfk_1` FOREIGN KEY (`FK_Period`) REFERENCES `periods` (`id`),
  CONSTRAINT `ads_ibfk_3` FOREIGN KEY (`FK_User`) REFERENCES `users` (`id`),
  CONSTRAINT `ads_ibfk_4` FOREIGN KEY (`FK_Ad_Types`) REFERENCES `ad_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ads`
--

LOCK TABLES `ads` WRITE;
/*!40000 ALTER TABLE `ads` DISABLE KEYS */;
INSERT INTO `ads` VALUES
(1,5,1,7,'test',NULL,1,0,'2025-04-02','2025-04-01'),
(2,6,2,7,'images/47252e03-0063-401a-a603-4c3abaaeabe9',NULL,1,0,'2025-04-01','2025-04-01'),
(3,7,2,7,'images/47252e03-0063-401a-a603-4c3abaaeabe9',NULL,1,6,'2025-04-01','2025-04-04'),
(4,6,2,7,'images/5494eedb-3ab4-48c0-9e9c-54e96df88914',NULL,1,6,'2025-04-03','2025-04-03'),
(5,8,1,7,'mi anuncio en un texto',NULL,1,4,'2025-04-03','2025-04-03'),
(6,5,2,7,'images/2894a53a-cda1-40a1-af80-69d855d4bb61',NULL,1,1,'2025-04-03','2025-04-03');
/*!40000 ALTER TABLE `ads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES
(8,'amor'),
(9,'amor propio'),
(10,'categoria 1'),
(11,'categoria 2'),
(12,'categoria 3'),
(5,'ciencia'),
(6,'cultura'),
(7,'ficción');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Suscription` int(11) NOT NULL,
  `FK_Magazine` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `date_created` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `FK_Suscription` (`FK_Suscription`),
  KEY `FK_Magazine` (`FK_Magazine`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`FK_Suscription`) REFERENCES `suscription` (`id`),
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`FK_Magazine`) REFERENCES `magazines` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES
(2,4,3,'Es una excelente revista :D','2025-04-03 08:06:16'),
(3,4,3,'El contenido es de calidad','2025-04-03 08:06:38');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Magazine` int(11) NOT NULL,
  `path` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Magazine` (`FK_Magazine`),
  CONSTRAINT `documents_ibfk_1` FOREIGN KEY (`FK_Magazine`) REFERENCES `magazines` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES
(1,1,'documents/acaffdb6-75dc-4bb1-9463-01fe0d218258'),
(2,3,'documents/0b9f3b6a-42a6-4419-b251-f04f3015e477'),
(3,4,'documents/fbccac76-e989-44ad-b8b9-5cf7c335ed25'),
(4,5,'documents/208ff802-5bb8-43e7-92b0-1848747ecb17'),
(5,6,'documents/1f6c0c3a-9f49-46ec-9fa6-4f987d661347'),
(6,7,'documents/4b8109fd-b681-40c1-b2bf-dd6b81fbf12f'),
(7,8,'documents/79327252-03f2-40d2-9039-066e8e1f14fb'),
(8,9,'documents/5925ff22-809a-4d6e-85d8-cf350557af7d'),
(9,9,'documents/1531cced-9754-40ae-bcc0-f54b1092e134'),
(10,1,'documents/62d8ccbe-3572-4a9b-8324-5dfd7f14c022'),
(11,1,'documents/8474abb9-1b76-4034-b844-bfc4fe421d30');
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES
(1,'1','create table user','SQL','V1__create_table_user.sql',-214990734,'magazinedba','2025-03-23 03:04:37',341,1),
(2,'2','create auxiliary tables user','SQL','V2__create_auxiliary_tables_user.sql',637593392,'magazinedba','2025-03-23 03:04:39',507,1),
(3,'3','create has tables user','SQL','V3__create_has_tables_user.sql',1574473376,'magazinedba','2025-03-23 03:04:42',621,1),
(4,'4','create view tables user','SQL','V4__create_view_tables_user.sql',350276737,'magazinedba','2025-03-23 03:04:44',470,1),
(5,'5','create table magazine','SQL','V5__create_table_magazine.sql',1901947302,'magazinedba','2025-03-23 03:04:46',318,1),
(6,'6','create auxiliary tables magazine','SQL','V6__create_auxiliary_tables_magazine.sql',-1450524904,'magazinedba','2025-03-23 03:04:48',417,1),
(7,'7','create auxiliary tables','SQL','V7__create_auxiliary_tables.sql',-602219727,'magazinedba','2025-03-23 03:04:50',411,1),
(8,'8','create table adds','SQL','V8__create_table_adds.sql',-115313997,'magazinedba','2025-03-23 03:04:52',327,1),
(9,'9','create has tables','SQL','V9__create_has_tables.sql',720268064,'magazinedba','2025-03-23 03:04:54',427,1),
(10,'10','create table suscription','SQL','V10__create_table_suscription.sql',-560913589,'magazinedba','2025-03-23 03:04:56',323,1),
(11,'11','create auxiliaty tables','SQL','V11__create_auxiliaty_tables.sql',-741174613,'magazinedba','2025-03-23 03:04:58',346,1),
(12,'12','update tables','SQL','V12__update_tables.sql',-925557324,'magazinedba','2025-03-24 20:44:49',515,1),
(13,'13','create roles','SQL','V13__create_roles.sql',-1944743965,'magazinedba','2025-03-26 05:07:33',533,1),
(14,'14','create modules pages','SQL','V14__create_modules_pages.sql',1712396371,'magazinedba','2025-03-26 19:30:10',392,1),
(15,'15','insert role has pages','SQL','V15__insert_role_has_pages.sql',1684095411,'magazinedba','2025-03-26 19:37:14',310,1),
(16,'16','rename tables attributes','SQL','V16__rename_tables_attributes.sql',-1958535439,'magazinedba','2025-03-30 00:48:34',2156,1),
(17,'17','rename period column','SQL','V17__rename_period_column.sql',-855935227,'magazinedba','2025-03-30 04:06:05',522,1),
(18,'18','update ads pages path','SQL','V18__update_ads_pages_path.sql',674512368,'magazinedba','2025-03-30 21:05:39',503,1),
(19,'19','create maga has labels','SQL','V19__create_maga_has_labels.sql',419752609,'magazinedba','2025-03-31 01:18:13',554,1),
(20,'20','insert ad types periods categories','SQL','V20__insert_ad_types_periods_categories.sql',-1889700636,'magazinedba','2025-03-31 04:35:44',533,1),
(21,'21','update ads content type','SQL','V21__update_ads_content_type.sql',-1659271445,'magazinedba','2025-04-02 05:41:59',436,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `labels`
--

DROP TABLE IF EXISTS `labels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `labels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `labels`
--

LOCK TABLES `labels` WRITE;
/*!40000 ALTER TABLE `labels` DISABLE KEYS */;
INSERT INTO `labels` VALUES
(5,'amor'),
(1,'ciencia'),
(2,'drama'),
(4,'entretenimiento'),
(3,'ficcion'),
(6,'tecnologia');
/*!40000 ALTER TABLE `labels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `magazine_has_categories`
--

DROP TABLE IF EXISTS `magazine_has_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `magazine_has_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Magazine` int(11) NOT NULL,
  `FK_Category` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Magazine` (`FK_Magazine`),
  KEY `FK_Category` (`FK_Category`),
  CONSTRAINT `magazine_has_categories_ibfk_1` FOREIGN KEY (`FK_Magazine`) REFERENCES `magazines` (`id`),
  CONSTRAINT `magazine_has_categories_ibfk_2` FOREIGN KEY (`FK_Category`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazine_has_categories`
--

LOCK TABLES `magazine_has_categories` WRITE;
/*!40000 ALTER TABLE `magazine_has_categories` DISABLE KEYS */;
INSERT INTO `magazine_has_categories` VALUES
(1,7,9),
(2,8,5),
(3,8,9),
(19,1,9),
(20,1,10),
(21,9,10),
(22,9,11);
/*!40000 ALTER TABLE `magazine_has_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `magazine_has_labels`
--

DROP TABLE IF EXISTS `magazine_has_labels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `magazine_has_labels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Magazine` int(11) NOT NULL,
  `FK_Label` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Magazine` (`FK_Magazine`),
  KEY `FK_Label` (`FK_Label`),
  CONSTRAINT `magazine_has_labels_ibfk_1` FOREIGN KEY (`FK_Magazine`) REFERENCES `magazines` (`id`),
  CONSTRAINT `magazine_has_labels_ibfk_2` FOREIGN KEY (`FK_Label`) REFERENCES `labels` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazine_has_labels`
--

LOCK TABLES `magazine_has_labels` WRITE;
/*!40000 ALTER TABLE `magazine_has_labels` DISABLE KEYS */;
INSERT INTO `magazine_has_labels` VALUES
(1,7,4),
(2,8,6),
(3,8,4),
(15,1,3),
(16,1,6),
(20,9,6),
(21,9,3),
(22,9,4);
/*!40000 ALTER TABLE `magazine_has_labels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `magazines`
--

DROP TABLE IF EXISTS `magazines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `magazines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `FK_User` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `can_comment` tinyint(1) DEFAULT 1,
  `can_like` tinyint(1) DEFAULT 1,
  `can_subscribe` tinyint(1) DEFAULT 1,
  `type` enum('PAID','FREE') DEFAULT 'FREE',
  `price` decimal(10,2) DEFAULT 0.00,
  `date_created` timestamp NULL DEFAULT current_timestamp(),
  `is_enabled` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `FK_User` (`FK_User`),
  CONSTRAINT `magazines_ibfk_1` FOREIGN KEY (`FK_User`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazines`
--

LOCK TABLES `magazines` WRITE;
/*!40000 ALTER TABLE `magazines` DISABLE KEYS */;
INSERT INTO `magazines` VALUES
(1,'Revista 1',1,'Castors',0,1,1,'FREE',0.00,'2025-03-26 10:27:32',1),
(3,'Revista 2',1,'Revista sobre como hacer un glosario en ipc',1,1,1,'PAID',10.00,'2025-03-26 22:20:51',1),
(4,'Revista 3',6,'Revista muy buena para aprender de comida',0,0,0,'FREE',0.00,'2025-03-26 23:29:03',1),
(5,'test',7,'test',1,1,0,'FREE',0.00,'2025-03-28 00:26:55',1),
(6,'TESET',7,'TEST',1,1,1,'FREE',0.00,'2025-03-28 15:56:11',1),
(7,'Revista 5',6,'Como ser mejor desarrollador',0,1,1,'FREE',0.00,'2025-03-31 12:28:50',1),
(8,'Revista 6',6,'Como ser mejor desarrollador 1.0',0,1,0,'FREE',0.00,'2025-03-31 12:43:00',1),
(9,'Revista 7',6,'Como ser mejor desarrollador 2.0',1,1,1,'PAID',15.00,'2025-03-31 12:56:07',1);
/*!40000 ALTER TABLE `magazines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modules`
--

DROP TABLE IF EXISTS `modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `path` varchar(50) NOT NULL,
  `is_enabled` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modules`
--

LOCK TABLES `modules` WRITE;
/*!40000 ALTER TABLE `modules` DISABLE KEYS */;
INSERT INTO `modules` VALUES
(1,'user','/user',1),
(2,'magazines','/magazines',1),
(3,'editor','/editor',1),
(4,'admin','/admin',1);
/*!40000 ALTER TABLE `modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pages`
--

DROP TABLE IF EXISTS `pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `FK_Module` int(11) NOT NULL,
  `path` varchar(50) NOT NULL,
  `is_enabled` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `FK_Module` (`FK_Module`),
  CONSTRAINT `pages_ibfk_1` FOREIGN KEY (`FK_Module`) REFERENCES `modules` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pages`
--

LOCK TABLES `pages` WRITE;
/*!40000 ALTER TABLE `pages` DISABLE KEYS */;
INSERT INTO `pages` VALUES
(1,'Mi Perfil',1,'/profile',1),
(2,'Mis suscripciones',1,'/my-subscriptions',1),
(3,'Mi Cartera',1,'/my-bill',1),
(4,'Comprar anuncio',1,'/buy-ad',1),
(5,'Mis anuncios',1,'/my-ads',1),
(6,'Buscar Revistas',2,'/search',1),
(7,'Ver Revista',2,'/view',1),
(8,'Nueva revista',3,'/new-magazine',1),
(9,'Editar revista',3,'/update-magazine',1),
(10,'Mis revistas',3,'/my-magazines',1),
(11,'Ajustes',4,'/settings',1),
(12,'Administrar Anuncios',4,'/manage-ads',1);
/*!40000 ALTER TABLE `pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periods`
--

DROP TABLE IF EXISTS `periods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `periods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` enum('DAILY','THREEDAYS','ONEWEEK','TWOWEEKS') DEFAULT 'DAILY',
  `cost` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periods`
--

LOCK TABLES `periods` WRITE;
/*!40000 ALTER TABLE `periods` DISABLE KEYS */;
INSERT INTO `periods` VALUES
(5,'DAILY',5.00),
(6,'THREEDAYS',10.00),
(7,'ONEWEEK',15.00),
(8,'TWOWEEKS',20.00);
/*!40000 ALTER TABLE `periods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES
(1,'ADMINISTRATOR'),
(3,'EDITOR'),
(2,'USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_has_pages`
--

DROP TABLE IF EXISTS `roles_has_pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles_has_pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Role` int(11) NOT NULL,
  `FK_Page` int(11) NOT NULL,
  `can_created` tinyint(1) DEFAULT 1,
  `can_update` tinyint(1) DEFAULT 1,
  `can_delete` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `FK_Role` (`FK_Role`),
  KEY `FK_Page` (`FK_Page`),
  CONSTRAINT `roles_has_pages_ibfk_1` FOREIGN KEY (`FK_Role`) REFERENCES `roles` (`id`),
  CONSTRAINT `roles_has_pages_ibfk_2` FOREIGN KEY (`FK_Page`) REFERENCES `pages` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_has_pages`
--

LOCK TABLES `roles_has_pages` WRITE;
/*!40000 ALTER TABLE `roles_has_pages` DISABLE KEYS */;
INSERT INTO `roles_has_pages` VALUES
(1,2,1,1,1,1),
(2,2,2,1,1,1),
(3,2,3,1,1,1),
(4,2,4,1,1,1),
(5,2,5,1,1,1),
(6,2,6,1,1,1),
(7,2,7,1,1,1),
(8,3,8,1,1,1),
(9,3,9,1,1,1),
(10,3,10,1,1,1),
(11,1,11,1,1,1),
(12,1,12,1,1,1);
/*!40000 ALTER TABLE `roles_has_pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suscription`
--

DROP TABLE IF EXISTS `suscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suscription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_like` tinyint(1) DEFAULT 0,
  `FK_User` int(11) NOT NULL,
  `FK_Magazine` int(11) NOT NULL,
  `date_created` date NOT NULL,
  `date_ended` date NOT NULL,
  `is_enabled` tinyint(1) DEFAULT 1,
  `pay` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_User` (`FK_User`),
  KEY `FK_Magazine` (`FK_Magazine`),
  CONSTRAINT `suscription_ibfk_1` FOREIGN KEY (`FK_User`) REFERENCES `users` (`id`),
  CONSTRAINT `suscription_ibfk_2` FOREIGN KEY (`FK_Magazine`) REFERENCES `magazines` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suscription`
--

LOCK TABLES `suscription` WRITE;
/*!40000 ALTER TABLE `suscription` DISABLE KEYS */;
INSERT INTO `suscription` VALUES
(4,1,5,3,'2025-04-01','2025-05-01',NULL,10.00),
(5,1,5,1,'2025-04-01','2025-05-01',NULL,0.00),
(6,0,5,7,'2025-04-03','2025-05-03',NULL,0.00),
(7,0,5,9,'2025-04-03','2025-05-03',NULL,15.00),
(8,0,6,9,'2025-04-03','2025-05-03',NULL,15.00);
/*!40000 ALTER TABLE `suscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_hobbies`
--

DROP TABLE IF EXISTS `user_has_hobbies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_hobbies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_User` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_User` (`FK_User`),
  CONSTRAINT `user_has_hobbies_ibfk_1` FOREIGN KEY (`FK_User`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_hobbies`
--

LOCK TABLES `user_has_hobbies` WRITE;
/*!40000 ALTER TABLE `user_has_hobbies` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_has_hobbies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_information`
--

DROP TABLE IF EXISTS `user_has_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_User` int(11) NOT NULL,
  `photo_path` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `age` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `current_balance` decimal(10,2) DEFAULT 0.00,
  PRIMARY KEY (`id`),
  KEY `FK_User` (`FK_User`),
  CONSTRAINT `user_has_information_ibfk_1` FOREIGN KEY (`FK_User`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_information`
--

LOCK TABLES `user_has_information` WRITE;
/*!40000 ALTER TABLE `user_has_information` DISABLE KEYS */;
INSERT INTO `user_has_information` VALUES
(1,4,'','',0,'',0.00),
(2,5,'images/3cd4368f-8959-4732-86e1-d8692d307121','Frontend Hernandez',24,'Soy un aficionado a las revistas',65.00),
(3,6,'','',0,'',-15.00),
(4,7,'images/ca3ec0cf-e720-4da7-8e1d-9edf6737a490','',0,'',5000.00);
/*!40000 ALTER TABLE `user_has_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_labels`
--

DROP TABLE IF EXISTS `user_has_labels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_labels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_User` int(11) NOT NULL,
  `FK_Label` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_User` (`FK_User`),
  KEY `FK_Label` (`FK_Label`),
  CONSTRAINT `user_has_labels_ibfk_1` FOREIGN KEY (`FK_User`) REFERENCES `users` (`id`),
  CONSTRAINT `user_has_labels_ibfk_2` FOREIGN KEY (`FK_Label`) REFERENCES `labels` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_labels`
--

LOCK TABLES `user_has_labels` WRITE;
/*!40000 ALTER TABLE `user_has_labels` DISABLE KEYS */;
INSERT INTO `user_has_labels` VALUES
(7,5,5),
(8,5,2),
(9,5,1);
/*!40000 ALTER TABLE `user_has_labels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_roles`
--

DROP TABLE IF EXISTS `user_has_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FK_User` int(11) NOT NULL,
  `FK_Role` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_User` (`FK_User`),
  KEY `FK_Role` (`FK_Role`),
  CONSTRAINT `user_has_roles_ibfk_1` FOREIGN KEY (`FK_User`) REFERENCES `users` (`id`),
  CONSTRAINT `user_has_roles_ibfk_2` FOREIGN KEY (`FK_Role`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_roles`
--

LOCK TABLES `user_has_roles` WRITE;
/*!40000 ALTER TABLE `user_has_roles` DISABLE KEYS */;
INSERT INTO `user_has_roles` VALUES
(1,4,2),
(2,5,2),
(3,6,3),
(4,7,2),
(5,1,3);
/*!40000 ALTER TABLE `user_has_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `auth_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
(1,'prueba1','23195a8f635fdb1ea9e47591edc5d0ceb820a38b5a126a51532df73a5f9e086514aaefeedc508b5ba99bca1bb5af4b0c35417d8274a452204c0758837901704e5e','prueba1@gmail.com','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjb2RlbmJ1Z3MiLCJleHAiOjE3NDM3MjQ4OTAsImVtYWlsIjoicHJ1ZWJhMUBnbWFpbC5jb20iLCJ1c2VybmFtZSI6InBydWViYTEifQ.IxZxi8CjxQgaP5ifK-lCYhbTk9tPUHNULlBntFY3rI0'),
(4,'test23','b907fdb2c867f091a2103a87d460f4ac37bc10553b884497a7e24e85e9526bf0f7a09623fd49460d047b8c25701a3330c40acc868f3660d3fc88f672a3e18dc802','prueba122@gmail.com',NULL),
(5,'frontend','1d870d073a11712d36478bfd2122ae879d9782a1b08c5d6dd2b6865d162fa82405016b5dc57903a6861fbd9da02ea18986bfc38508e7ce9126f0aa54aa766ec4e9','frontend@gmail.com','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjb2RlbmJ1Z3MiLCJleHAiOjE3NDM3MzI0MTMsImVtYWlsIjoiZnJvbnRlbmRAZ21haWwuY29tIiwidXNlcm5hbWUiOiJmcm9udGVuZCJ9.ZFVaIAzxQqKiJwe2E9auz7btDm47mSxA4H8k8PBuq88'),
(6,'danimo','2688f17cd2c1f09864e1fa1a05b5177ed60e3b0c2e826988d2be5569e92ec2f60ef62c49ae263e3b7a86ba611db481ec6850f6507f02d97c27b6553a46b9e58cfd','danielmoralesxicara@gmail.com','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjb2RlbmJ1Z3MiLCJleHAiOjE3NDM3MzMxNjUsImVtYWlsIjoiZGFuaWVsbW9yYWxlc3hpY2FyYUBnbWFpbC5jb20iLCJ1c2VybmFtZSI6ImRhbmltbyJ9.zuXprL44xTAEddassjqrZy3mdjAvMXvxZVtiMBnEjzg'),
(7,'isaac03483','630bdd42f895b69010b8a4af5fd6996774ba7d24395dcfa84d2fee7567eaa0e46126f3411ad55d95de0688cc393e023f282af2c1529dfa5641757bbad90e66bcc4','levihernandez2501@gmail.com','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjb2RlbmJ1Z3MiLCJleHAiOjE3NDM2NjYzNDIsImVtYWlsIjoibGV2aWhlcm5hbmRlejI1MDFAZ21haWwuY29tIiwidXNlcm5hbWUiOiJpc2FhYzAzNDgzIn0.qJAIvZmKTeL7HXepvWdt6Riq3DbQIsPlEWxvWbCwqPU');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-03 21:30:24
