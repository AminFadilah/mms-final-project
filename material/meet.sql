-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 13, 2023 at 08:52 AM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `final-project`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_attendance`
--

CREATE TABLE `tb_attendance` (
  `id` int(11) NOT NULL,
  `meeting` int(11) DEFAULT NULL,
  `participant` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_attendance`
--

INSERT INTO `tb_attendance` (`id`, `meeting`, `participant`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 2, 1),
(5, 2, 2),
(6, 3, 1),
(7, 3, 2),
(8, 4, 1),
(9, 4, 2),
(10, 4, 3),
(11, 5, 1),
(12, 5, 2),
(13, 5, 4),
(14, 5, 5),
(15, 5, 8);

-- --------------------------------------------------------

--
-- Table structure for table `tb_meeting`
--

CREATE TABLE `tb_meeting` (
  `id` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `end_meeting` datetime NOT NULL,
  `is_online` bit(1) DEFAULT NULL,
  `link_room` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `start_meeting` datetime NOT NULL,
  `initiator_id` int(11) NOT NULL,
  `note_taker_id` int(11) NOT NULL,
  `room_id` int(11) DEFAULT NULL,
  `status_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_meeting`
--

INSERT INTO `tb_meeting` (`id`, `description`, `end_meeting`, `is_online`, `link_room`, `name`, `start_meeting`, `initiator_id`, `note_taker_id`, `room_id`, `status_id`) VALUES
(1, 'Rapat pemegang saham', '2023-08-15 21:00:00', b'0', NULL, 'Studio Pillar', '2023-08-15 18:00:00', 1, 2, 1, 1),
(2, 'Rapat dengan client', '2023-08-11 11:45:00', b'1', 'zoom-meeting/12345', 'Client-Pejanten', '2023-08-11 11:30:00', 1, 2, NULL, 2),
(3, 'Pertemuan pertama dengan Pak Miles Karnadi.', '2023-08-11 15:00:00', b'0', NULL, 'Client-Antapani', '2023-08-11 12:00:00', 2, 1, 2, 2),
(4, '-', '2023-08-11 15:15:00', b'0', NULL, 'Client-Denpasar', '2023-08-11 15:00:00', 1, 2, 2, 3),
(5, 'Rancang bangun', '2023-08-18 15:15:00', b'0', NULL, 'client-bandung', '2023-08-18 13:15:00', 2, 1, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_mom`
--

CREATE TABLE `tb_mom` (
  `id` int(11) NOT NULL,
  `discussion` varchar(255) NOT NULL,
  `result` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_mom`
--

INSERT INTO `tb_mom` (`id`, `discussion`, `result`) VALUES
(2, 'Konsep rancangan bangunan', 'Scandinavina'),
(3, 'Hasil', 'Menerapkan Manajemen Perancangan\r\nKonsep Rancangan\r\nPra-rancangan Arsitektur/ Schematic Design\r\nPengembangan Rancangan Arsitektur\r\nMembuat Gambar Kerja');

-- --------------------------------------------------------

--
-- Table structure for table `tb_participant`
--

CREATE TABLE `tb_participant` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `is_internal` bit(1) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_participant`
--

INSERT INTO `tb_participant` (`id`, `email`, `is_internal`, `name`, `phone`) VALUES
(1, 'Maheswara@gmail.com', b'1', 'Giandra Maheswara Mahameru', '0852362453333'),
(2, 'Kelompokmcc2@gmail.com', b'1', 'Jad El Rio', '0852337475785'),
(3, 'Ajiwira@gmail.com', b'1', 'Ardiman Ajiwira', '0852236234237'),
(4, 'Mikarnadi@gmail.com', b'0', 'Miles Kano Karnadi', '085233777888'),
(5, 'hardian@ymail.com', b'0', 'Hardian Wijaya', '085111222111'),
(6, 'Galendion@ymail.com', b'0', 'Galendion Rakesha', '086567765543'),
(7, 'Raditya@ymail.com', b'0', 'Raditya Pradana', '086234123234'),
(8, 'Ertio@ymail.com', b'1', 'Ertio Vijendra', '085234456654');

-- --------------------------------------------------------

--
-- Table structure for table `tb_privilege`
--

CREATE TABLE `tb_privilege` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_privilege`
--

INSERT INTO `tb_privilege` (`id`, `name`) VALUES
(1, 'CREATE_USER'),
(2, 'READ_USER'),
(3, 'UPDATE_USER'),
(4, 'DELETE_USER'),
(5, 'CREATE_ADMIN'),
(6, 'READ_ADMIN'),
(7, 'UPDATE_ADMIN'),
(8, 'DELETE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `tb_role`
--

CREATE TABLE `tb_role` (
  `id` int(11) NOT NULL,
  `name` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_role`
--

INSERT INTO `tb_role` (`id`, `name`) VALUES
(1, 'USER'),
(2, 'ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `tb_role_privilege`
--

CREATE TABLE `tb_role_privilege` (
  `role_id` int(11) NOT NULL,
  `privilege_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_role_privilege`
--

INSERT INTO `tb_role_privilege` (`role_id`, `privilege_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8);

-- --------------------------------------------------------

--
-- Table structure for table `tb_room`
--

CREATE TABLE `tb_room` (
  `id` int(11) NOT NULL,
  `capacity` int(11) NOT NULL,
  `is_available` bit(1) NOT NULL,
  `location` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_room`
--

INSERT INTO `tb_room` (`id`, `capacity`, `is_available`, `location`, `name`) VALUES
(1, 3, b'1', 'Universitas Ciputra', 'Room 3.17'),
(2, 5, b'1', 'Universitas Ciputra', 'Room 3.18'),
(3, 10, b'1', 'Universitas Ciputra', 'Room 3.20');

-- --------------------------------------------------------

--
-- Table structure for table `tb_status`
--

CREATE TABLE `tb_status` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_status`
--

INSERT INTO `tb_status` (`id`, `name`) VALUES
(1, 'created'),
(2, 'done'),
(3, 'cancelled'),
(4, 'meeting updated'),
(5, 'mom created'),
(6, 'mom updated');

-- --------------------------------------------------------

--
-- Table structure for table `tb_tracking_history`
--

CREATE TABLE `tb_tracking_history` (
  `id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `meeting` int(11) NOT NULL,
  `pic` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_tracking_history`
--

INSERT INTO `tb_tracking_history` (`id`, `date`, `meeting`, `pic`, `status`) VALUES
(1, '2023-08-11 11:20:44', 1, 1, 1),
(2, '2023-08-11 11:25:45', 2, 1, 1),
(3, '2023-08-11 11:31:10', 3, 2, 1),
(4, '2023-08-11 15:29:59', 2, 1, 2),
(5, '2023-08-11 15:29:59', 3, 2, 2),
(6, '2023-08-11 15:51:31', 4, 1, 1),
(7, '2023-08-11 15:51:35', 4, 1, 2),
(8, '2023-08-12 19:49:06', 3, 1, 5),
(12, '2023-08-12 19:51:28', 4, 1, 3),
(13, '2023-08-12 20:02:05', 2, 2, 5),
(14, '2023-08-12 20:02:53', 2, 2, 6),
(15, '2023-08-13 13:51:04', 5, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_user`
--

CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_user`
--

INSERT INTO `tb_user` (`id`, `password`, `username`) VALUES
(1, '$2a$12$PlsO6Yr3YU.y5eg/qj.mg.DS.gbGbaYj.gOxvccFw5JF3qRxj.Bou', 'ares'),
(2, '$2a$12$17RqcgNJX5oPbbDNR2h31u5hffvC7AP34lWyhE2V2h6f8g2VP2ewS', 'jad'),
(3, '$2a$12$y7z1c3Ahc/lHAgopYBOZSeHB5qPoe7kR/tYm1Q70g2grhbB/.QSRC', 'ajiwira'),
(8, '$2a$12$py88U4.LpbfITmFQzx5RPOBktuqK7JXe713rfERLP4.d0BO4ji0EK', 'ertio');

-- --------------------------------------------------------

--
-- Table structure for table `tb_user_role`
--

CREATE TABLE `tb_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_user_role`
--

INSERT INTO `tb_user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 1),
(3, 2),
(8, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_attendance`
--
ALTER TABLE `tb_attendance`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKs5x4jb4s4s24i8gr60l1n9oxm` (`meeting`),
  ADD KEY `FK73d90h4k05o7ad5g4r0hgb58g` (`participant`);

--
-- Indexes for table `tb_meeting`
--
ALTER TABLE `tb_meeting`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKamynaa60apg4l3e7nfmiiutt8` (`initiator_id`),
  ADD KEY `FKie740fj4en5kduqkfwbklvjde` (`note_taker_id`),
  ADD KEY `FK16sm1t4r8t0gt0er54momnvaq` (`room_id`),
  ADD KEY `FKf8ruys1pvhuuwd27wl7e95k4j` (`status_id`);

--
-- Indexes for table `tb_mom`
--
ALTER TABLE `tb_mom`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_participant`
--
ALTER TABLE `tb_participant`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_privilege`
--
ALTER TABLE `tb_privilege`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_role`
--
ALTER TABLE `tb_role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_role_privilege`
--
ALTER TABLE `tb_role_privilege`
  ADD KEY `FK6gvpbopw3c17inmv17usj8kes` (`privilege_id`),
  ADD KEY `FKsncdml4xq9xf0opvvrxcycq7y` (`role_id`);

--
-- Indexes for table `tb_room`
--
ALTER TABLE `tb_room`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_status`
--
ALTER TABLE `tb_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_tracking_history`
--
ALTER TABLE `tb_tracking_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKflarv9arr7ji47oh8biv2gne2` (`meeting`),
  ADD KEY `FKsw678s0pl79q6pwhpsi698ow0` (`pic`),
  ADD KEY `FK43dfs9mk3nan93tmlmjrlq2pg` (`status`);

--
-- Indexes for table `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_4wv83hfajry5tdoamn8wsqa6x` (`username`);

--
-- Indexes for table `tb_user_role`
--
ALTER TABLE `tb_user_role`
  ADD KEY `FKea2ootw6b6bb0xt3ptl28bymv` (`role_id`),
  ADD KEY `FK7vn3h53d0tqdimm8cp45gc0kl` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_attendance`
--
ALTER TABLE `tb_attendance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `tb_meeting`
--
ALTER TABLE `tb_meeting`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tb_participant`
--
ALTER TABLE `tb_participant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tb_privilege`
--
ALTER TABLE `tb_privilege`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tb_role`
--
ALTER TABLE `tb_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_room`
--
ALTER TABLE `tb_room`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tb_status`
--
ALTER TABLE `tb_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tb_tracking_history`
--
ALTER TABLE `tb_tracking_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_attendance`
--
ALTER TABLE `tb_attendance`
  ADD CONSTRAINT `FK73d90h4k05o7ad5g4r0hgb58g` FOREIGN KEY (`participant`) REFERENCES `tb_participant` (`id`),
  ADD CONSTRAINT `FKs5x4jb4s4s24i8gr60l1n9oxm` FOREIGN KEY (`meeting`) REFERENCES `tb_meeting` (`id`);

--
-- Constraints for table `tb_meeting`
--
ALTER TABLE `tb_meeting`
  ADD CONSTRAINT `FK16sm1t4r8t0gt0er54momnvaq` FOREIGN KEY (`room_id`) REFERENCES `tb_room` (`id`),
  ADD CONSTRAINT `FKamynaa60apg4l3e7nfmiiutt8` FOREIGN KEY (`initiator_id`) REFERENCES `tb_participant` (`id`),
  ADD CONSTRAINT `FKf8ruys1pvhuuwd27wl7e95k4j` FOREIGN KEY (`status_id`) REFERENCES `tb_status` (`id`),
  ADD CONSTRAINT `FKie740fj4en5kduqkfwbklvjde` FOREIGN KEY (`note_taker_id`) REFERENCES `tb_participant` (`id`);

--
-- Constraints for table `tb_mom`
--
ALTER TABLE `tb_mom`
  ADD CONSTRAINT `FK7n5diygvxw3ywkxoobhv90rm7` FOREIGN KEY (`id`) REFERENCES `tb_meeting` (`id`);

--
-- Constraints for table `tb_role_privilege`
--
ALTER TABLE `tb_role_privilege`
  ADD CONSTRAINT `FK6gvpbopw3c17inmv17usj8kes` FOREIGN KEY (`privilege_id`) REFERENCES `tb_privilege` (`id`),
  ADD CONSTRAINT `FKsncdml4xq9xf0opvvrxcycq7y` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`);

--
-- Constraints for table `tb_tracking_history`
--
ALTER TABLE `tb_tracking_history`
  ADD CONSTRAINT `FK43dfs9mk3nan93tmlmjrlq2pg` FOREIGN KEY (`status`) REFERENCES `tb_status` (`id`),
  ADD CONSTRAINT `FKflarv9arr7ji47oh8biv2gne2` FOREIGN KEY (`meeting`) REFERENCES `tb_meeting` (`id`),
  ADD CONSTRAINT `FKsw678s0pl79q6pwhpsi698ow0` FOREIGN KEY (`pic`) REFERENCES `tb_participant` (`id`);

--
-- Constraints for table `tb_user`
--
ALTER TABLE `tb_user`
  ADD CONSTRAINT `FK2nh2s99uwaod4cuml0lary5bi` FOREIGN KEY (`id`) REFERENCES `tb_participant` (`id`);

--
-- Constraints for table `tb_user_role`
--
ALTER TABLE `tb_user_role`
  ADD CONSTRAINT `FK7vn3h53d0tqdimm8cp45gc0kl` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `FKea2ootw6b6bb0xt3ptl28bymv` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
