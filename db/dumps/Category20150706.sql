-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: vk_spider
-- ------------------------------------------------------
-- Server version	5.6.24-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `category`
--
USE vk_spider;
DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id`        INT(11)      NOT NULL AUTO_INCREMENT,
  `name`      VARCHAR(45)  NOT NULL,
  `image_url` VARCHAR(255) NOT NULL DEFAULT 'http://localhost:8080/img/categories/nophoto.png',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 47
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1, 'Авіація|Aviation', 'http://localhost:8080/img/categories/aviation.png'),
  (2, 'Авто|Auto', 'http://localhost:8080/img/categories/car.png'),
  (3, 'Арт|Art', 'http://localhost:8080/img/categories/art.png'),
  (4, 'Архітектура|Architecture', 'http://localhost:8080/img/categories/architecture.png'),
  (5, 'Бізнес|Business', 'http://localhost:8080/img/categories/business.png'),
  (6, 'Відео|Video', 'http://localhost:8080/img/categories/video.png'),
  (7, 'Гаджети|Gadgets', 'http://localhost:8080/img/categories/gadgets.png'),
  (8, 'Дівчата|Girls', 'http://localhost:8080/img/categories/girls.png'),
  (9, 'Діти|Children', 'http://localhost:8080/img/categories/children.png'),
  (10, 'Дизайн|Design', 'http://localhost:8080/img/categories/design.png'),
  (11, 'Їда та рецепти|Food And Recipes', 'http://localhost:8080/img/categories/eating.png'),
  (12, 'Тварини|Animals', 'http://localhost:8080/img/categories/animals.png'),
  (13, 'Здоров\'я|Health', 'http://localhost:8080/img/categories/health.png'),
  (14, 'Знаменитості|Celebrity', 'http://localhost:8080/img/categories/celebrity.png'),
  (15, 'Ігри|Game', 'http://localhost:8080/img/categories/game.png'),
  (16, 'Інтернет|Internet', 'http://localhost:8080/img/categories/internet.png'),
  (17, 'Інтерєр|Interior', 'http://localhost:8080/img/categories/interior.png'),
  (18, 'Історія|History', 'http://localhost:8080/img/categories/history.png'),
  (19, 'Картинки|Pictures', 'http://localhost:8080/img/categories/image.png'),
  (20, 'Кіно|Movie', 'http://localhost:8080/img/categories/movie.png'),
  (21, 'Книги|Books', 'http://localhost:8080/img/categories/books.png'),
  (22, 'Культура|Culture', 'http://localhost:8080/img/categories/culture.png'),
  (23, 'Мода|Fashion', 'http://localhost:8080/img/categories/fashion.png'),
  (24, 'Мото|Motorcycles', 'http://localhost:8080/img/categories/moto.png'),
  (25, 'Музика|Music', 'http://localhost:8080/img/categories/music.png'),
  (26, 'Мультфільми|Cartoons', 'http://localhost:8080/img/categories/cartoons.png'),
  (27, 'Наука|Science', 'http://localhost:8080/img/categories/science.png'),
  (28, 'Новини|News', 'http://localhost:8080/img/categories/news.png'),
  (29, 'Навчання|Education', 'http://localhost:8080/img/categories/education.png'),
  (30, 'Суспільство|Society', 'http://localhost:8080/img/categories/society.png'),
  (31, 'Зброя|Weapon', 'http://localhost:8080/img/categories/weapon.png'),
  (32, 'Політика|Politics', 'http://localhost:8080/img/categories/politics.png'),
  (33, 'Природа|Nature', 'http://localhost:8080/img/categories/nature.png'),
  (34, 'Психологія|Psychology', 'http://localhost:8080/img/categories/psychology.png'),
  (35, 'Подорожі|Travel', 'http://localhost:8080/img/categories/travel.png'),
  (36, 'Розваги|Entertainment', 'http://localhost:8080/img/categories/entertainment.png'),
  (37, 'Реклама|Advertising', 'http://localhost:8080/img/categories/myadvertising.png'),
  (38, 'Спорт|Sport', 'http://localhost:8080/img/categories/sport.png'),
  (39, 'Технології|Technology', 'http://localhost:8080/img/categories/technology.png'),
  (40, 'Фітнес|Fitness', 'http://localhost:8080/img/categories/fitness.png'),
  (41, 'Фото|Photo', 'http://localhost:8080/img/categories/photo.png'),
  (42, 'Хобі|Hobby', 'http://localhost:8080/img/categories/hobby.png'),
  (43, 'Шопінг|Shopping', 'http://localhost:8080/img/categories/shopping.png'),
  (44, 'Економіка|Economy', 'http://localhost:8080/img/categories/economy.png'),
  (45, 'Еротика|Erotica', 'http://localhost:8080/img/categories/erotica.png'),
  (46, 'Гумор|Humor', 'http://localhost:8080/img/categories/humor.png');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2015-07-06 16:18:00
