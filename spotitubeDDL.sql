CREATE DATABASE `spotitube` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `spotitube`;

CREATE TABLE `user` (
  `user` varchar(45) NOT NULL,
  `pass` varchar(45) NOT NULL,
  PRIMARY KEY (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `track` (
  `idtrack` int(11) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `performer` varchar(45) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `album` varchar(45) DEFAULT NULL,
  `playcount` varchar(45) DEFAULT NULL,
  `publicationDate` date DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `offlineAvailable` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idtrack`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `playlist` (
  `idplaylist` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `owner` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idplaylist`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `trackinplaylist` (
  `idtrack` int(11) NOT NULL,
  `idplaylist` int(11) NOT NULL,
  PRIMARY KEY (`idtrack`,`idplaylist`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
