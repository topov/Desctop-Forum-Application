-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 15, 2016 at 06:10 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `forum`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `doubleName`(IN `var` VARCHAR(255))
BEGIN
   SET @cust_name = var;
   IF(!EXISTS(
    SELECT *
    FROM accounts
    WHERE accounts.username=@cust_name))
   THEN SELECT * FROM accounts ; 
   END IF; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `doubleNames`(IN username VARCHAR(255), IN passwordd VARCHAR(255), IN firstname VARCHAR(255), IN lastname VARCHAR(255), IN town VARCHAR(255), IN mail VARCHAR(255))
BEGIN
  SET @name = username;
  SET @pass=passwordd;
  SET @fname=firstname;
  SET @lname=lastname;
  SET @city=town;
  SET @email=mail;

   IF(!EXISTS(
    SELECT *
    FROM accounts
    WHERE accounts.username=@name))
   THEN INSERT INTO accounts (`username`,`password`,`firstname`,`lastname`,`town`,`mail`,`id`)
    VALUES (@name,@pass,@fname,@lname,@city,@email,NULL);
   END IF; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `logCheck`(IN user VARCHAR (255), IN pass VARCHAR (255))
BEGIN 
IF((SELECT `password` from `accounts` WHERE `username`=user)=pass)	
THEN SELECT 1;
ELSE SELECT 0;
END IF;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `username` varchar(25) NOT NULL,
  `password` varchar(30) NOT NULL,
  `firstname` varchar(25) NOT NULL,
  `lastname` varchar(40) NOT NULL,
  `town` varchar(20) NOT NULL,
  `mail` varchar(50) NOT NULL,
  `bornDate` date DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`username`, `password`, `firstname`, `lastname`, `town`, `mail`, `bornDate`, `id`) VALUES
('knoxville', 'check', 'emil', 'topov', 'zlatograd', 'emil.topov@abv.bg', '1994-04-01', 1),
('eclipse', 'check', 'ivan', 'todorov', 'pazardjik', 'ivo@mail.bg', '1987-03-01', 3),
('pedro', '020202', 'Pedro', 'Suarez', 'Barcelona', 'pedro@pederasa.com', NULL, 4),
('ivanivanov', 'ivoparola', 'Ivan', 'Bijev', 'Vraca', 'vanko@gmail.com', NULL, 6),
('topcheto', 'fake', 'emo', 'topv', 'erwr', 'ewrwe@afds.bg', NULL, 7),
('borkobla', 'hahahaha', 'aarewerw', 'erwer', 'wrewr', 'erwwerew', NULL, 8);

-- --------------------------------------------------------

--
-- Table structure for table `answers`
--

CREATE TABLE IF NOT EXISTS `answers` (
  `content` text NOT NULL,
  `answDate` date DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=35 ;

--
-- Dumping data for table `answers`
--

INSERT INTO `answers` (`content`, `answDate`, `id`, `account_id`, `post_id`) VALUES
('Ima li kacheni ocenki po pik 3 momcheta ?', '2015-05-08', 1, 1, 1),
('nqma brat .... mn kofti', '2015-05-09', 2, 3, 1),
('ti seriali li gledash be  ? :D', '2015-05-11', 3, 4, 3),
('shte gi publikuvat skoro !', '2015-05-07', 4, 1, 1),
('ko stava', NULL, 5, 1, 2),
('hahahah', NULL, 6, 3, 1),
('cvxvx', NULL, 16, 3, 2),
('malelei', NULL, 17, 3, 2),
('takova e brat', NULL, 18, 1, 2),
('begai !', NULL, 21, 3, 3),
('Az sam ivailo', NULL, 22, 6, 3),
('az sam emo', NULL, 23, 1, 3),
('proba', NULL, 24, 3, 3),
('napisah nov otgovor', NULL, 25, 3, 4),
('pozdravleniq', NULL, 26, 1, 4),
('az sam pedro', NULL, 27, 4, 4),
('zdrasti', NULL, 28, 1, 2),
('havi', NULL, 29, 3, 3),
('ERWRWRW', NULL, 30, 3, 2),
('?? ?? ?????? ? ?? ???? : )', NULL, 31, 7, 4),
('ne stava da pisha na kirilica sorry', NULL, 32, 7, 4),
('Welcome pich : )', NULL, 33, 3, 4),
('thanks', NULL, 34, 7, 4);

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
  `content` text NOT NULL,
  `read` tinyint(1) NOT NULL,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`content`, `read`, `sender_id`, `receiver_id`, `id`) VALUES
('zdravei', 0, 1, 3, 1),
('zdravei', 1, 1, 3, 2),
('drastiiiiiii kOk e a?', 1, 3, 1, 3),
('kak e pich?', 1, 3, 3, 4),
('syobshtenie ot eclipse do knoxville', 1, 3, 1, 5),
('syobshtenie ot eclipse do knoxville', 1, 3, 1, 6),
('syobshtenie ot eclipse do pedro', 1, 3, 4, 7),
('kakvo pravish eclipse?', 1, 1, 3, 8),
('zdrasti pedro kak si?', 1, 3, 4, 9),
('dsasdsa', 1, 1, 3, 10),
('zdrasti da te pitam za neshto ?', 1, 7, 3, 11);

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE IF NOT EXISTS `posts` (
  `theme` tinytext NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`theme`, `id`, `account_id`) VALUES
('PIK3-OCENKI', 1, 1),
('problem s ocenkite', 2, 1),
('seriali po btv', 3, 1),
('napravih nova tema', 4, 3);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
