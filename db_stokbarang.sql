-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 29 Jan 2024 pada 17.42
-- Versi server: 10.4.17-MariaDB
-- Versi PHP: 7.4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_stokbarang`
--

DELIMITER $$
--
-- Prosedur
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `totalbiaya` ()   SELECT (hargaawal*stok1) AS totalbiaya
FROM tb_stokbarang$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `totalhargachart` ()   BEGIN
SELECT 
SUM(tb_chart.jumlahbarang*tb_chart.harga) AS totalharga
FROM tb_chart;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_chart`
--

CREATE TABLE `tb_chart` (
  `idtransaksi` int(20) NOT NULL,
  `idbarang` int(20) NOT NULL,
  `pembeli` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `kontak` varchar(50) NOT NULL,
  `namabarang` varchar(50) NOT NULL,
  `harga` int(50) NOT NULL,
  `jumlahbarang` int(10) NOT NULL,
  `totalharga` int(50) NOT NULL,
  `tanggaltransaksi` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_chart`
--

INSERT INTO `tb_chart` (`idtransaksi`, `idbarang`, `pembeli`, `alamat`, `kontak`, `namabarang`, `harga`, `jumlahbarang`, `totalharga`, `tanggaltransaksi`) VALUES
(56, 1003, 'a', 'a', '1', 'a', 15, 5, 75, '2023-09-09');

--
-- Trigger `tb_chart`
--
DELIMITER $$
CREATE TRIGGER `cancel` AFTER DELETE ON `tb_chart` FOR EACH ROW BEGIN
UPDATE tb_stokbarang SET
stokbarang = stokbarang + OLD.jumlahbarang
WHERE idbarang = OLD.idbarang;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `cancel2` AFTER DELETE ON `tb_chart` FOR EACH ROW BEGIN
DELETE FROM tb_transaksi
WHERE idbarang = OLD.idbarang;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `stokhabis` AFTER INSERT ON `tb_chart` FOR EACH ROW BEGIN
DELETE FROM tb_stokbarang
WHERE stokbarang = 0
AND
idbarang = NEW.idbarang;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_login`
--

CREATE TABLE `tb_login` (
  `id` int(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `hak_akses` enum('penuh','terbatas') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_login`
--

INSERT INTO `tb_login` (`id`, `username`, `password`, `hak_akses`) VALUES
(1, 'direktur', '1234', 'penuh'),
(2, 'admin gudang', '5678', 'terbatas');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_stokbarang`
--

CREATE TABLE `tb_stokbarang` (
  `idbarang` int(100) NOT NULL,
  `supplier` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `kontak` varchar(50) NOT NULL,
  `namabarang` varchar(50) NOT NULL,
  `hargaawal` int(30) NOT NULL,
  `harga` int(30) NOT NULL,
  `stokbarang` int(30) NOT NULL,
  `stok1` int(30) NOT NULL,
  `totalawal` int(30) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_stokbarang`
--

INSERT INTO `tb_stokbarang` (`idbarang`, `supplier`, `alamat`, `kontak`, `namabarang`, `hargaawal`, `harga`, `stokbarang`, `stok1`, `totalawal`, `tanggal`) VALUES
(1, 'SENTOSA STEEL', 'BANDAR LAMPUNG', '0885 8850 5080', 'HOLLOW STAIN 1X1', 80000, 85000, 0, 20, 1600000, '2022-10-28'),
(2, 'SENTOSA STEEL', 'BANDAR LAMPUNG ', '0885 8850 5080', 'HOLLOW STAIN 1X2', 85000, 90000, 10, 20, 1700000, '2022-10-28'),
(3, 'SENTOSA STEEL', 'BANDAR LAMPUNG ', '0885 8850 5080', 'HOLLOW STAIN 2X2', 90000, 96000, 18, 20, 1800000, '2022-10-28'),
(6, 'TOKO JAYA ABADI', 'BANDAR LAMPUNG ', '0856 5889 1422', 'HOLLOW STAIN 2X4', 135000, 146000, 18, 20, 2700000, '2022-10-28'),
(7, 'MUARA STEEL ', 'WAY HALIM', '0856 5888 9025', 'HOLLOW STAIN 4X4', 182000, 195000, 18, 20, 3640000, '2022-10-28'),
(8, 'MUARA STEEL ', 'WAY HALIM', '0856 5888 9025', 'HOLLOW STAIN 4X6', 225000, 245000, 18, 20, 4500000, '2022-10-28'),
(10, 'WESI JAYA ANUGRAH', 'PADANG CERMIN', '0852 5588 7000', 'PIPA ULIR STAIN 5/8\"', 60000, 67000, 30, 35, 2100000, '2022-10-28'),
(11, 'WESI JAYA ANUGRAH', 'PADANG CERMIN', '0852 5588 7000', 'PIPA ULIR STAIN 3/4\"', 73000, 77500, 30, 35, 2555000, '2022-10-28'),
(12, 'WESI JAYA ANUGRAH', 'PADANG CERMIN', '0852 5588 7000', 'PIPA ULIR STAIN 1\"', 93000, 100500, 30, 35, 3255000, '2022-10-28'),
(13, 'AGUNG STEEL PERKASA ', 'KEMILING', '0853 7885 4344', 'PIPA ULIR STAIN 1 1/4\"', 111000, 120000, 25, 25, 2775000, '2022-10-29'),
(14, 'AGUNG STEEL PERKASA ', 'KEMILING', '0853 7885 4344', 'PIPA ULIR STAIN 1 1/2\"', 140000, 150000, 25, 25, 3500000, '2022-10-29'),
(15, 'AGUNG STEEL PERKASA ', 'KEMILING', '0853 7885 4344', 'PIPA ULIR STAIN 2\"', 200000, 214000, 25, 25, 5000000, '2022-10-29'),
(16, 'ANUGRAH MANDIRI', 'KALIANDA', '0852 2250 8080', 'PLAT MIRROR 0.8MM', 670000, 700000, 10, 10, 6700000, '2022-10-29'),
(17, 'ANUGRAH MANDIRI', 'KALIANDA', '0852 2250 8080', 'PLAT MIRROR 1MM', 815000, 860000, 10, 10, 8150000, '2022-10-29'),
(20, 'MUARA STEEL ', 'WAY HALIM', '0856 5888 9025', 'HOLLOW STAIN 4X6', 270000, 290000, 20, 20, 5400000, '2022-10-28'),
(1001, 'MUARA STEEL ', 'WAY HALIM', '0856 5888 9025', 'HOLLOW STAIN 3X3', 225000, 245000, 20, 20, 4500000, '2022-10-28'),
(1002, 'a', 'a', '1', 'c', 20, 25, 30, 30, 600, '2023-09-08'),
(1003, 'a', 'a', '1', 'a', 10, 15, 15, 20, 200, '2023-09-06'),
(1004, 'a', 'a', '1', 'b', 15, 20, 20, 20, 300, '2023-09-07'),
(1005, 'a', 'a', '1', 'c', 20, 25, 20, 20, 400, '2023-09-08'),
(1006, 'b', 'b', '2', 'd', 25, 30, 20, 20, 500, '2023-09-09'),
(1007, 'b', 'b', '2', 'e', 30, 35, 20, 20, 600, '2023-09-09'),
(1008, 'b', 'b', '2', 'f', 35, 40, 20, 20, 700, '2023-09-09'),
(1009, 'b', 'b', '2', 'f', 35, 40, 20, 20, 700, '2023-09-09');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_transaksi`
--

CREATE TABLE `tb_transaksi` (
  `idtransaksi` int(20) NOT NULL,
  `pembeli` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `kontak` varchar(50) NOT NULL,
  `idbarang` int(20) NOT NULL,
  `namabarang` varchar(100) NOT NULL,
  `harga` int(50) NOT NULL,
  `jumlahbarang` int(10) NOT NULL,
  `totalharga` int(50) NOT NULL,
  `tanggaltransaksi` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_transaksi`
--

INSERT INTO `tb_transaksi` (`idtransaksi`, `pembeli`, `alamat`, `kontak`, `idbarang`, `namabarang`, `harga`, `jumlahbarang`, `totalharga`, `tanggaltransaksi`) VALUES
(8, 'Cholis Steel', '29 Banjarsari', '-', 2, 'HOLLOW STAIN 1X2', 90000, 2, 180000, '2022-11-01'),
(9, 'Berkah Steel', 'Punggur', '-', 3, 'HOLLOW STAIN 2X2', 96000, 2, 192000, '2022-11-02'),
(10, 'Berkah Steel', 'Punggur', '-', 7, 'HOLLOW STAIN 4X4', 195000, 2, 390000, '2022-11-02'),
(34, 'Cholis Steel', '29 Banjarsari', '-', 2, 'HOLLOW STAIN 1X2', 90000, 8, 720000, '2023-04-04'),
(55, 'a', 'a', '1', 1, 'HOLLOW STAIN 1X1', 85000, 5, 425000, '2023-08-28'),
(56, 'a', 'a', '1', 1003, 'a', 15, 5, 75, '2023-09-09');

--
-- Trigger `tb_transaksi`
--
DELIMITER $$
CREATE TRIGGER `chart` AFTER INSERT ON `tb_transaksi` FOR EACH ROW BEGIN
INSERT INTO tb_chart SET
idtransaksi = NEW.idtransaksi,
idbarang = NEW.idbarang, 
pembeli = NEW.pembeli,
alamat = NEW.alamat,
kontak = NEW.kontak,
namabarang = NEW.namabarang,
harga = NEW.harga,
jumlahbarang = NEW.jumlahbarang,
totalharga = NEW.totalharga,
tanggaltransaksi = NEW.tanggaltransaksi;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `transaksi` AFTER INSERT ON `tb_transaksi` FOR EACH ROW BEGIN
UPDATE tb_stokbarang SET
stokbarang = stokbarang - NEW.jumlahbarang
WHERE idbarang = NEW.idbarang;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_validasistok`
--

CREATE TABLE `tb_validasistok` (
  `idbarang` int(100) NOT NULL,
  `supplier` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `kontak` varchar(50) NOT NULL,
  `namabarang` varchar(50) NOT NULL,
  `hargaawal` int(30) NOT NULL,
  `harga` int(30) NOT NULL,
  `stokbarang` int(30) NOT NULL,
  `stok1` int(30) NOT NULL,
  `totalawal` int(30) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_validasistok`
--

INSERT INTO `tb_validasistok` (`idbarang`, `supplier`, `alamat`, `kontak`, `namabarang`, `hargaawal`, `harga`, `stokbarang`, `stok1`, `totalawal`, `tanggal`) VALUES
(1, 'a', 'a', '1', 'a', 10, 15, 20, 20, 200, '2023-09-06'),
(2, 'a', 'a', '1', 'b', 15, 20, 20, 20, 300, '2023-09-07'),
(3, 'a', 'a', '1', 'c', 20, 25, 20, 20, 400, '2023-09-08'),
(6, 'b', 'b', '2', 'd', 25, 30, 20, 20, 500, '2023-09-09'),
(7, 'b', 'b', '2', 'e', 30, 35, 20, 20, 600, '2023-09-09'),
(8, 'b', 'b', '2', 'f', 35, 40, 20, 20, 700, '2023-09-09');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_chart`
--
ALTER TABLE `tb_chart`
  ADD PRIMARY KEY (`idtransaksi`),
  ADD KEY `idbarang` (`idbarang`);

--
-- Indeks untuk tabel `tb_login`
--
ALTER TABLE `tb_login`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tb_stokbarang`
--
ALTER TABLE `tb_stokbarang`
  ADD PRIMARY KEY (`idbarang`);

--
-- Indeks untuk tabel `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  ADD PRIMARY KEY (`idtransaksi`),
  ADD KEY `idbarang` (`idbarang`);

--
-- Indeks untuk tabel `tb_validasistok`
--
ALTER TABLE `tb_validasistok`
  ADD PRIMARY KEY (`idbarang`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_chart`
--
ALTER TABLE `tb_chart`
  MODIFY `idtransaksi` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT untuk tabel `tb_login`
--
ALTER TABLE `tb_login`
  MODIFY `id` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `tb_stokbarang`
--
ALTER TABLE `tb_stokbarang`
  MODIFY `idbarang` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1010;

--
-- AUTO_INCREMENT untuk tabel `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  MODIFY `idtransaksi` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT untuk tabel `tb_validasistok`
--
ALTER TABLE `tb_validasistok`
  MODIFY `idbarang` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tb_chart`
--
ALTER TABLE `tb_chart`
  ADD CONSTRAINT `tb_chart_ibfk_1` FOREIGN KEY (`idbarang`) REFERENCES `tb_stokbarang` (`idbarang`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tb_chart_ibfk_2` FOREIGN KEY (`idtransaksi`) REFERENCES `tb_transaksi` (`idtransaksi`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ketidakleluasaan untuk tabel `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  ADD CONSTRAINT `tb_transaksi_ibfk_1` FOREIGN KEY (`idbarang`) REFERENCES `tb_stokbarang` (`idbarang`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ketidakleluasaan untuk tabel `tb_validasistok`
--
ALTER TABLE `tb_validasistok`
  ADD CONSTRAINT `tb_validasistok_ibfk_1` FOREIGN KEY (`idbarang`) REFERENCES `tb_stokbarang` (`idbarang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
