-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 17, 2025 at 03:40 PM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `springinvoicemanager2`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `user_id` int NOT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_role` varchar(20) NOT NULL DEFAULT 'admin',
  UNIQUE KEY `unique_user_name` (`user_name`(100))
) ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`user_id`, `user_email`, `user_name`, `user_password`, `user_role`) VALUES
(1999, 'achrafsaadalii@gmail.com', 'Ach_2003', '$2a$10$zDg0I6r6Hk7uN7xCQxQqUO5Zj1d0pFOpH8kP6VvW6c7fU0b4xXj1G\n', 'admin'),
(1133, 'achrafsaadali@gmail.com', 'Achraf__2003', '$2a$10$rr.DRS3ifzK4rC2bhWMW4uN/F1zEW6YarawG6sfY8dheAHdosTRci', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `user_id` int NOT NULL,
  `user_email` varchar(200) NOT NULL,
  `user_name` varchar(200) NOT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_role` varchar(20) NOT NULL DEFAULT 'client',
  `client_code` varchar(200) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_email` (`user_email`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `client_code` (`client_code`),
  UNIQUE KEY `user_name_2` (`user_name`),
  UNIQUE KEY `user_name_3` (`user_name`),
  UNIQUE KEY `unique_user_name` (`user_name`(100))
) ;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`user_id`, `user_email`, `user_name`, `user_password`, `user_role`, `client_code`) VALUES
(1168, 'achrafsaadaliii@gmail.com', 'Achraf__2003', '$2a$10$t7R5OZv1gzGVIgqlJOA7X.cfG4S89YZaD6HmQo0JGKfnz3f/ZUlpq', 'client', 'Cl-2003');

-- --------------------------------------------------------

--
-- Table structure for table `comptable`
--

DROP TABLE IF EXISTS `comptable`;
CREATE TABLE IF NOT EXISTS `comptable` (
  `user_id` int NOT NULL,
  `user_email` varchar(200) NOT NULL,
  `user_name` varchar(200) NOT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_role` varchar(20) NOT NULL DEFAULT 'comptable',
  `comptable_code` varchar(200) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_email` (`user_email`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `comptable_code` (`comptable_code`),
  UNIQUE KEY `user_name_2` (`user_name`),
  UNIQUE KEY `unique_user_name` (`user_name`(100))
) ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
