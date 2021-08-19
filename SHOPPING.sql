-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 02, 2021 at 02:42 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 7.4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `SHOPPING`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblCategories`
--

CREATE TABLE `tblCategories` (
  `id` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblCategories`
--

INSERT INTO `tblCategories` (`id`, `category_name`) VALUES
(1, 'Laptop'),
(2, 'PC'),
(3, 'Case'),
(4, 'Mouse'),
(5, 'Keyboard'),
(6, 'Earphone');

-- --------------------------------------------------------

--
-- Table structure for table `tblPasswordResets`
--

CREATE TABLE `tblPasswordResets` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created` datetime NOT NULL DEFAULT current_timestamp(),
  `available` bit(1) NOT NULL DEFAULT b'1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblPasswordResets`
--

INSERT INTO `tblPasswordResets` (`id`, `email`, `token`, `created`, `available`) VALUES
(1, 'huywoaytai@gmail.com', '301751dfe3611edba76f46b8eac83b9e8494', '2021-07-26 12:19:57', b'1'),
(2, 'huywoaytai@gmail.com', '301751dfe3611edba76f46b8eac83b9e1026', '2021-07-26 12:20:50', b'0'),
(3, 'huywoaytai@gmail.com', '301751dfe3611edba76f46b8eac83b9e4867', '2021-07-26 18:19:07', b'1'),
(4, 'huywoaytai@gmail.com', '301751dfe3611edba76f46b8eac83b9e6406', '2021-07-26 20:01:09', b'1'),
(5, 'huywoaytai@gmail.com', '301751dfe3611edba76f46b8eac83b9e5332', '2021-07-26 20:01:49', b'0'),
(6, 'huywoaytai@gmail.com', '301751dfe3611edba76f46b8eac83b9e8110', '2021-07-30 09:49:16', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `tblProducts`
--

CREATE TABLE `tblProducts` (
  `id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL DEFAULT '',
  `price` decimal(5,2) NOT NULL,
  `image_url` varchar(255) NOT NULL DEFAULT '',
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblProducts`
--

INSERT INTO `tblProducts` (`id`, `product_name`, `price`, `image_url`, `category_id`) VALUES
(1, 'Laptop Simpon', '123.00', 'https://www.erevollution.com/public/upload/citizen/48526.jpg', 2),
(2, 'RTX1080', '99.00', 'https://h2hglobal.org/wp-content/uploads/2020/11/che_pham_lam_sach_rang.jpg', 2),
(3, 'loi chinh ta - cay vl', '99.00', 'https://h2hglobal.org/wp-content/uploads/2020/11/che_pham_lam_sach_rang.jpg', 5);

-- --------------------------------------------------------

--
-- Table structure for table `tblUsers`
--

CREATE TABLE `tblUsers` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `hash_password` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblUsers`
--

INSERT INTO `tblUsers` (`id`, `email`, `hash_password`) VALUES
(1, 'huywoaytai@gmail.com', '$2y$10$fbGfACt4iS5eaGPot/vZ.uAMURaB.asEVgsnybbi2J4mdFsDA4lpC'),
(4, 'huy12345678ho@gmail.com', '$2y$10$ZV5sT/i2iAUsnXDadMwi3.5OJ9sNbyXUyDnCVPpSc5/5vwJFcjKk2'),
(5, 'huywoay@gmail.com', '$2y$10$iQUr8OoYnBkJmqGa2O0NhOwHUX7VxCFwclaMjw0tns.wr6BPomVBy'),
(6, 'huywoayta9i@gmail.com', '$2y$10$ZAOGebuWmC1n5p.GNK13zexZo7Yhp3ulHJGN7tioFUHqKRBJxKMWm');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblCategories`
--
ALTER TABLE `tblCategories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tblPasswordResets`
--
ALTER TABLE `tblPasswordResets`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `token` (`token`);

--
-- Indexes for table `tblProducts`
--
ALTER TABLE `tblProducts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `tblUsers`
--
ALTER TABLE `tblUsers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblCategories`
--
ALTER TABLE `tblCategories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tblPasswordResets`
--
ALTER TABLE `tblPasswordResets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tblProducts`
--
ALTER TABLE `tblProducts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tblUsers`
--
ALTER TABLE `tblUsers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tblProducts`
--
ALTER TABLE `tblProducts`
  ADD CONSTRAINT `tblProducts_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `tblCategories` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
