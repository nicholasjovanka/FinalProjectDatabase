-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 07, 2020 at 08:06 PM
-- Server version: 10.4.10-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bookstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `Item_Id` int(10) UNSIGNED NOT NULL,
  `Category` varchar(100) NOT NULL,
  `Item_Name` varchar(100) NOT NULL,
  `Price_Per_Item` int(10) UNSIGNED NOT NULL,
  `Quantity` int(10) UNSIGNED NOT NULL,
  `Store_Id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`Item_Id`, `Category`, `Item_Name`, `Price_Per_Item`, `Quantity`, `Store_Id`) VALUES
(1, 'Pencil', 'Faber Castle', 5000, 40, 1),
(2, 'Pencil', 'Faber Castle', 5000, 10, 2);

-- --------------------------------------------------------

--
-- Table structure for table `owner`
--

CREATE TABLE `owner` (
  `Owner_Id` int(10) UNSIGNED NOT NULL,
  `Owner_Name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `owner`
--

INSERT INTO `owner` (`Owner_Id`, `Owner_Name`) VALUES
(1, 'Rino Santoso'),
(3, 'Nicholas Jovanka');

-- --------------------------------------------------------

--
-- Table structure for table `ownership`
--

CREATE TABLE `ownership` (
  `Ownership_Id` int(10) UNSIGNED NOT NULL,
  `Owner_Id` int(10) UNSIGNED NOT NULL,
  `Store_Id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ownership`
--

INSERT INTO `ownership` (`Ownership_Id`, `Owner_Id`, `Store_Id`) VALUES
(1, 1, 1),
(4, 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `Staff_Id` int(10) UNSIGNED NOT NULL,
  `Staff_Name` varchar(100) NOT NULL,
  `Store_Id` int(10) UNSIGNED DEFAULT NULL,
  `Owner_Id` int(10) UNSIGNED DEFAULT NULL,
  `Job_Type` varchar(100) NOT NULL,
  `Salary` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`Staff_Id`, `Staff_Name`, `Store_Id`, `Owner_Id`, `Job_Type`, `Salary`) VALUES
(1, 'Nendi', 2, 3, 'Laborer', 2200000),
(2, 'Ali', 2, 3, 'Driver, Laborer', 2000000),
(3, 'Nano', 1, 1, 'Laborer', 2400000);

-- --------------------------------------------------------

--
-- Table structure for table `stores`
--

CREATE TABLE `stores` (
  `Store_Id` int(10) UNSIGNED NOT NULL,
  `Location` varchar(100) NOT NULL,
  `Store_Name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stores`
--

INSERT INTO `stores` (`Store_Id`, `Location`, `Store_Name`) VALUES
(1, 'Cikarang', 'Store1'),
(2, 'Lippo', 'Store2');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `Supplier_Id` int(10) UNSIGNED NOT NULL,
  `Supplier_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`Supplier_Id`, `Supplier_name`) VALUES
(1, 'Supplier1'),
(2, 'Supp');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `Transaction_Id` int(10) UNSIGNED NOT NULL,
  `Item_Id` int(10) UNSIGNED NOT NULL,
  `Transaction_Type_Id` int(10) UNSIGNED NOT NULL,
  `Date` datetime NOT NULL,
  `Supplier_Id` int(10) UNSIGNED DEFAULT NULL,
  `Quantity` int(10) UNSIGNED NOT NULL,
  `Store_Id` int(10) UNSIGNED NOT NULL,
  `Store_Id2` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`Transaction_Id`, `Item_Id`, `Transaction_Type_Id`, `Date`, `Supplier_Id`, `Quantity`, `Store_Id`, `Store_Id2`) VALUES
(3, 1, 1, '2020-01-07 15:01:23', 1, 120, 1, NULL),
(4, 1, 2, '2020-01-07 15:01:47', NULL, 70, 1, NULL),
(5, 1, 3, '2020-01-07 15:01:28', NULL, 10, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `transaction_type`
--

CREATE TABLE `transaction_type` (
  `Transaction_Type_Id` int(10) UNSIGNED NOT NULL,
  `Transaction_Type` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction_type`
--

INSERT INTO `transaction_type` (`Transaction_Type_Id`, `Transaction_Type`) VALUES
(1, 'Buy'),
(2, 'Sell'),
(3, 'Transfer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`Item_Id`),
  ADD KEY `Store_Id` (`Store_Id`);

--
-- Indexes for table `owner`
--
ALTER TABLE `owner`
  ADD PRIMARY KEY (`Owner_Id`);

--
-- Indexes for table `ownership`
--
ALTER TABLE `ownership`
  ADD PRIMARY KEY (`Ownership_Id`),
  ADD KEY `Owner_Id` (`Owner_Id`),
  ADD KEY `Store_Id` (`Store_Id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`Staff_Id`),
  ADD KEY `Store_Id` (`Store_Id`),
  ADD KEY `Owner_Id` (`Owner_Id`);

--
-- Indexes for table `stores`
--
ALTER TABLE `stores`
  ADD PRIMARY KEY (`Store_Id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`Supplier_Id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`Transaction_Id`),
  ADD KEY `Item_Id` (`Item_Id`),
  ADD KEY `Transaction_Type_Id` (`Transaction_Type_Id`),
  ADD KEY `Supplier_Id` (`Supplier_Id`),
  ADD KEY `Store_Id` (`Store_Id`),
  ADD KEY `Store_Id2` (`Store_Id2`);

--
-- Indexes for table `transaction_type`
--
ALTER TABLE `transaction_type`
  ADD PRIMARY KEY (`Transaction_Type_Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `Item_Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `owner`
--
ALTER TABLE `owner`
  MODIFY `Owner_Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `ownership`
--
ALTER TABLE `ownership`
  MODIFY `Ownership_Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `Staff_Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `stores`
--
ALTER TABLE `stores`
  MODIFY `Store_Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `Supplier_Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `Transaction_Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `transaction_type`
--
ALTER TABLE `transaction_type`
  MODIFY `Transaction_Type_Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`Store_Id`) REFERENCES `stores` (`Store_Id`);

--
-- Constraints for table `ownership`
--
ALTER TABLE `ownership`
  ADD CONSTRAINT `ownership_ibfk_1` FOREIGN KEY (`Owner_Id`) REFERENCES `owner` (`Owner_Id`),
  ADD CONSTRAINT `ownership_ibfk_2` FOREIGN KEY (`Store_Id`) REFERENCES `stores` (`Store_Id`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`Store_Id`) REFERENCES `stores` (`Store_Id`),
  ADD CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`Owner_Id`) REFERENCES `owner` (`Owner_Id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`Item_Id`) REFERENCES `item` (`Item_Id`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`Transaction_Type_Id`) REFERENCES `transaction_type` (`Transaction_Type_Id`),
  ADD CONSTRAINT `transaction_ibfk_3` FOREIGN KEY (`Supplier_Id`) REFERENCES `supplier` (`Supplier_Id`),
  ADD CONSTRAINT `transaction_ibfk_4` FOREIGN KEY (`Store_Id`) REFERENCES `stores` (`Store_Id`),
  ADD CONSTRAINT `transaction_ibfk_5` FOREIGN KEY (`Store_Id2`) REFERENCES `stores` (`Store_Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
