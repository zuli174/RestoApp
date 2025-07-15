-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 15, 2025 at 05:38 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `resto`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbdetailtransaksi`
--

CREATE TABLE `tbdetailtransaksi` (
  `iddetailtransaksi` int(20) NOT NULL,
  `idtransaksi` varchar(15) NOT NULL,
  `idmenu` varchar(6) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `totalharga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbmeja`
--

CREATE TABLE `tbmeja` (
  `idmeja` varchar(6) NOT NULL,
  `nomormeja` varchar(15) NOT NULL,
  `kategori` varchar(15) NOT NULL,
  `status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbmeja`
--

INSERT INTO `tbmeja` (`idmeja`, `nomormeja`, `kategori`, `status`) VALUES
('MJ0001', '1', 'Family', 'Terisi'),
('MJ0002', '2', 'Family', 'Terisi'),
('MJ0003', '1', 'Singgle', 'Terisi'),
('MJ0011', '11', 'Berdua', 'Terisi'),
('MJ0012', '12', 'Berdua', 'Kosong'),
('MJ0036', '15', 'Family', 'Terisi'),
('MJ0037', '12', 'Family', 'Kosong');

-- --------------------------------------------------------

--
-- Table structure for table `tbmenu`
--

CREATE TABLE `tbmenu` (
  `idmenu` varchar(6) NOT NULL,
  `namamenu` varchar(20) NOT NULL,
  `kategori` varchar(15) NOT NULL,
  `harga` int(11) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbmenu`
--

INSERT INTO `tbmenu` (`idmenu`, `namamenu`, `kategori`, `harga`, `stok`) VALUES
('MN0001', 'Mie Goreng', 'Makanan', 15000, 44),
('MN0002', 'Cireng', 'Makanan', 2000, 95),
('MN0003', 'Puding', 'Makanan', 10000, 85),
('MN0004', 'kopi', 'Minuman', 15000, 4);

-- --------------------------------------------------------

--
-- Table structure for table `tbpegawai`
--

CREATE TABLE `tbpegawai` (
  `idpegawai` varchar(6) NOT NULL,
  `namapegawai` varchar(25) NOT NULL,
  `jk` varchar(15) NOT NULL,
  `tempatlahir` varchar(15) NOT NULL,
  `tanggallahir` date NOT NULL,
  `alamat` varchar(45) NOT NULL,
  `nohp` varchar(13) NOT NULL,
  `jenispekerjaan` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbpegawai`
--

INSERT INTO `tbpegawai` (`idpegawai`, `namapegawai`, `jk`, `tempatlahir`, `tanggallahir`, `alamat`, `nohp`, `jenispekerjaan`) VALUES
('P0001', 'Indah', 'p', 'Bandung', '2004-04-03', 'Ngamprah', '081382740210', 'admin'),
('P0002', 'Rangga', 'l', 'Cimahi', '2004-11-27', 'Cimahi ', '088273927491', 'admin'),
('P0003', 'Dina', 'p', 'Bandung', '2000-05-15', 'Padalarang ', '083864293013', 'manager');

-- --------------------------------------------------------

--
-- Table structure for table `tbpelanggan`
--

CREATE TABLE `tbpelanggan` (
  `idpelanggan` varchar(6) NOT NULL,
  `namapelanggan` varchar(25) NOT NULL,
  `jk` varchar(15) NOT NULL,
  `tempatlahir` varchar(15) NOT NULL,
  `tanggallahir` date NOT NULL,
  `alamat` varchar(11) NOT NULL,
  `nohp` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbpelanggan`
--

INSERT INTO `tbpelanggan` (`idpelanggan`, `namapelanggan`, `jk`, `tempatlahir`, `tanggallahir`, `alamat`, `nohp`) VALUES
('PL0001', 'Putri', 'p', 'Bandung', '2003-02-20', 'Cimahi no 1', '08139027402'),
('PL0002', 'Alan', 'l', 'Cimahi', '2010-07-20', 'Cimahi no ', '08178002000');

-- --------------------------------------------------------

--
-- Table structure for table `tbpengguna`
--

CREATE TABLE `tbpengguna` (
  `idpengguna` varchar(6) NOT NULL,
  `idpegawai` varchar(6) NOT NULL,
  `namapengguna` varchar(25) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `level` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbpengguna`
--

INSERT INTO `tbpengguna` (`idpengguna`, `idpegawai`, `namapengguna`, `username`, `password`, `level`) VALUES
('PG0001', 'P0001', 'Indah', 'Indah', 'indah1111', 'Kasir'),
('PG0002', 'P0002', 'Rangga', 'angga', '123', 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `tbtransaksi`
--

CREATE TABLE `tbtransaksi` (
  `idtransaksi` varchar(15) NOT NULL,
  `tanggal` date NOT NULL,
  `idpengguna` varchar(6) NOT NULL,
  `idpelanggan` varchar(6) NOT NULL,
  `idmeja` varchar(6) NOT NULL,
  `totalbayar` int(11) NOT NULL,
  `bayar` int(11) NOT NULL,
  `kembali` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbtransaksi`
--

INSERT INTO `tbtransaksi` (`idtransaksi`, `tanggal`, `idpengguna`, `idpelanggan`, `idmeja`, `totalbayar`, `bayar`, `kembali`) VALUES
('TR0001', '2024-08-22', 'PG0002', 'PL0001', 'MJ0001', 45000, 50000, 5000),
('TR0002', '2024-08-22', 'PG0002', 'PL0001', 'MJ0001', 10000, 10000, 0),
('TR0003', '2024-08-22', 'PG0002', 'PL0001', 'MJ0002', 10000, 10000, 0),
('TR0004', '2024-08-22', 'PG0002', 'PL0001', 'MJ0001', 15000, 20000, 5000),
('TR0005', '2024-08-22', 'PG0002', 'PL0001', 'MJ0001', 30000, 40000, 10000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbdetailtransaksi`
--
ALTER TABLE `tbdetailtransaksi`
  ADD PRIMARY KEY (`iddetailtransaksi`),
  ADD KEY `idtransaksi` (`idtransaksi`),
  ADD KEY `idmenu` (`idmenu`);

--
-- Indexes for table `tbmeja`
--
ALTER TABLE `tbmeja`
  ADD PRIMARY KEY (`idmeja`);

--
-- Indexes for table `tbmenu`
--
ALTER TABLE `tbmenu`
  ADD PRIMARY KEY (`idmenu`);

--
-- Indexes for table `tbpegawai`
--
ALTER TABLE `tbpegawai`
  ADD PRIMARY KEY (`idpegawai`);

--
-- Indexes for table `tbpelanggan`
--
ALTER TABLE `tbpelanggan`
  ADD PRIMARY KEY (`idpelanggan`);

--
-- Indexes for table `tbpengguna`
--
ALTER TABLE `tbpengguna`
  ADD PRIMARY KEY (`idpengguna`),
  ADD KEY `fk_idpegawai` (`idpegawai`);

--
-- Indexes for table `tbtransaksi`
--
ALTER TABLE `tbtransaksi`
  ADD PRIMARY KEY (`idtransaksi`),
  ADD KEY `idpengguna` (`idpengguna`),
  ADD KEY `idpelanggan` (`idpelanggan`),
  ADD KEY `idmeja` (`idmeja`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbdetailtransaksi`
--
ALTER TABLE `tbdetailtransaksi`
  ADD CONSTRAINT `tbdetailtransaksi_ibfk_1` FOREIGN KEY (`idtransaksi`) REFERENCES `tbtransaksi` (`idtransaksi`),
  ADD CONSTRAINT `tbdetailtransaksi_ibfk_2` FOREIGN KEY (`idmenu`) REFERENCES `tbmenu` (`idmenu`);

--
-- Constraints for table `tbpengguna`
--
ALTER TABLE `tbpengguna`
  ADD CONSTRAINT `fk_idpegawai` FOREIGN KEY (`idpegawai`) REFERENCES `tbpegawai` (`idpegawai`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbpengguna_ibfk_1` FOREIGN KEY (`idpegawai`) REFERENCES `tbpegawai` (`idpegawai`);

--
-- Constraints for table `tbtransaksi`
--
ALTER TABLE `tbtransaksi`
  ADD CONSTRAINT `tbtransaksi_ibfk_1` FOREIGN KEY (`idpengguna`) REFERENCES `tbpengguna` (`idpengguna`),
  ADD CONSTRAINT `tbtransaksi_ibfk_2` FOREIGN KEY (`idpelanggan`) REFERENCES `tbpelanggan` (`idpelanggan`),
  ADD CONSTRAINT `tbtransaksi_ibfk_3` FOREIGN KEY (`idmeja`) REFERENCES `tbmeja` (`idmeja`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
