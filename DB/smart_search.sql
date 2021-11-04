-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 03, 2021 at 11:01 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smart_search`
--

-- --------------------------------------------------------

--
-- Table structure for table `hardwares`
--

CREATE TABLE `hardwares` (
  `Hardware_Id` int(10) NOT NULL,
  `Project_Id` int(10) NOT NULL,
  `Diameter` varchar(20) NOT NULL,
  `Pitch` varchar(20) NOT NULL,
  `B` varchar(20) NOT NULL,
  `K` varchar(20) NOT NULL,
  `DK` varchar(20) NOT NULL,
  `A` varchar(20) NOT NULL,
  `S` varchar(20) NOT NULL,
  `Total_Length` varchar(20) NOT NULL,
  `Head` varchar(20) NOT NULL,
  `Socket` varchar(20) NOT NULL,
  `Type` varchar(20) NOT NULL,
  `Quan` int(20) NOT NULL,
  `Name` varchar(60) GENERATED ALWAYS AS (concat('D',`Diameter`,'xP',`Pitch`,'xB',`B`,'xK',`K`,' ','xDK',`DK`,' ','xA',`A`,' ','xS',`S`,' ','xL',`Total_Length`,' ','xH',`Head`,' ','xS',`Socket`,' ','xTh',`Type`,' ')) VIRTUAL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hardwares`
--

INSERT INTO `hardwares` (`Hardware_Id`, `Project_Id`, `Diameter`, `Pitch`, `B`, `K`, `DK`, `A`, `S`, `Total_Length`, `Head`, `Socket`, `Type`, `Quan`) VALUES
(17, 1, 'M18', '2.50', '42.00', '8.00', '33.00', '90.00', '12.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 1),
(19, 1, 'M22', '2.50', '56.00', '13.10', '36.00', '60.00', '14.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 5),
(20, 1, 'M24', '3.00', '60.00', '14.00', '39.00', '60.00', '14.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 8),
(21, 1, 'M3', '0.25', '18.00', '1.86', '6.72', '90.00', '2.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 2),
(22, 1, 'M4', '0.50', '20.00', '2.48', '8.96', '90.00', '2.50', '12.00', 'Conical', 'Inner hex', 'Shoulder', 5),
(23, 1, 'M5', '0.50', '22.00', '3.10', '10.20', '90.00', '3.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 5),
(24, 1, 'M6', '0.50', '24.00', '3.72', '13.44', '90.00', '4.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 4),
(25, 1, 'M8', '0.70', '28.00', '4.96', '17.92', '90.00', '5.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 3),
(26, 1, 'M10', '0.70', '32.00', '6.20', '22.40', '90.00', '6.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 3),
(27, 1, 'M12', '0.70', '36.00', '7.44', '26.80', '90.00', '8.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 2),
(28, 1, 'M14', '0.70', '40.00', '8.40', '30.88', '90.00', '10.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 1),
(29, 1, 'M16', '0.70', '44.00', '8.80', '33.60', '90.00', '10.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 2),
(30, 1, 'M18', '0.70', '42.00', '8.00', '33.00', '90.00', '12.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 1),
(31, 1, 'M20', '0.70', '52.00', '10.16', '40.32', '90.00', '12.00', '12.00', 'Conical', 'Inner hex', 'Shoulder', 1);

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE `project` (
  `Project_Id` int(10) NOT NULL,
  `Project_Name` varchar(50) NOT NULL,
  `Project_Zone` varchar(40) NOT NULL,
  `Project_Scope` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`Project_Id`, `Project_Name`, `Project_Zone`, `Project_Scope`) VALUES
(1, 'Project A', 'Zone A', 'Scope A');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hardwares`
--
ALTER TABLE `hardwares`
  ADD PRIMARY KEY (`Hardware_Id`),
  ADD KEY `Project_Id` (`Project_Id`);

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`Project_Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hardwares`
--
ALTER TABLE `hardwares`
  MODIFY `Hardware_Id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `Project_Id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `hardwares`
--
ALTER TABLE `hardwares`
  ADD CONSTRAINT `hardwares_ibfk_1` FOREIGN KEY (`Project_Id`) REFERENCES `project` (`Project_Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
