DROP SCHEMA IF EXISTS `employee_directory`;

CREATE SCHEMA `employee_directory`;

use `employee_directory`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user_details`;

CREATE TABLE `user_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `mail` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `user_detail_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `FK_DETAIL_idx` (`user_detail_id`),
  CONSTRAINT `FK_DETAIL` FOREIGN KEY (`user_detail_id`)
  REFERENCES `user_details` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,

  UNIQUE KEY `authorities_idx_1` (`username`, `authority`),

  CONSTRAINT `authorities_ibfk_1`
  FOREIGN KEY (`username`)
  REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `posts`;

CREATE TABLE `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  `content` MEDIUMTEXT  NOT NULL,
  `created_at` DATETIME NOT NULL,

  PRIMARY KEY (`id`),
  KEY `FK_USERNAME_idx` (`username`),
  CONSTRAINT `FK_USERNAME` FOREIGN KEY (`username`)
  REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `specializations`;

CREATE TABLE `specializations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `likes` int(11) DEFAULT NULL,
  `dislikes` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `FK_USERNAME_idx` (`username`),
  CONSTRAINT `FK_USERNAME` FOREIGN KEY (`username`)
  REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `appointments`;

CREATE TABLE `appointments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scheduled_datetime` DATETIME NOT NULL,
  `duration_minutes` int(11) NOT NULL,
  `state` varchar(50) NOT NULL,
  `tutor` varchar(50) NOT NULL,
  `student` varchar(50) NOT NULL,

  PRIMARY KEY (`id`),
  KEY `FK_TUTOR_idx` (`tutor`),
  CONSTRAINT `FK_TUTOR` FOREIGN KEY (`tutor`)
  REFERENCES `users` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  KEY `FK_STUDENT_idx` (`student`),
  CONSTRAINT `FK_STUDENT` FOREIGN KEY (`student`)
  REFERENCES `users` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `avaibility`;

CREATE TABLE `avaibility` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hour_begin` TIME NOT NULL,
  `hour_end` TIME NOT NULL,
  `username` varchar(50) NOT NULL,
  `day` varchar(50) NOT NULL,

  PRIMARY KEY (`id`),
  KEY `FK_USERNAME_idx` (`username`),
  CONSTRAINT `FK_USERNAME` FOREIGN KEY (`username`)
  REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
