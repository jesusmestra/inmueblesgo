-- phpMyAdmin SQL Dump
-- version 4.0.6deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 17-09-2016 a las 06:59:07
-- Versión del servidor: 5.5.37-0ubuntu0.13.10.1
-- Versión de PHP: 5.5.3-1ubuntu2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `inmueblesgo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE IF NOT EXISTS `departamento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `dpto_codigo` varchar(255) DEFAULT NULL,
  `dpto_nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=34 ;

--
-- Volcado de datos para la tabla `departamento`
--

INSERT INTO `departamento` (`id`, `version`, `dpto_codigo`, `dpto_nombre`) VALUES
(1, 0, '05', 'ANTIOQUIA'),
(2, 0, '08', 'ATLANTICO'),
(3, 0, '11', 'BOGOTA, D.C.'),
(4, 0, '13', 'BOLIVAR'),
(5, 0, '15', 'BOYACA'),
(6, 0, '17', 'CALDAS'),
(7, 0, '18', 'CAQUETA'),
(8, 0, '19', 'CAUCA'),
(9, 0, '20', 'CESAR'),
(10, 0, '23', 'CORDOBA'),
(11, 0, '25', 'CUNDINAMARCA'),
(12, 0, '27', 'CHOCO'),
(13, 0, '41', 'HUILA'),
(14, 0, '44', 'LA GUAJIRA'),
(15, 0, '47', 'MAGDALENA'),
(16, 0, '50', 'META'),
(17, 0, '52', 'NARIÃ‘O'),
(18, 0, '54', 'NORTE DE SANTANDER'),
(19, 0, '63', 'QUINDIO'),
(20, 0, '66', 'RISARALDA'),
(21, 0, '68', 'SANTANDER'),
(22, 0, '70', 'SUCRE'),
(23, 0, '73', 'TOLIMA'),
(24, 0, '76', 'VALLE DEL CAUCA'),
(25, 0, '81', 'ARAUCA'),
(26, 0, '85', 'CASANARE'),
(27, 0, '86', 'PUTUMAYO'),
(28, 0, '88', 'ARCHIPIELAGO DE SAN ANDRES, PROVIDENCIA Y SANTA CATALINA'),
(29, 0, '91', 'AMAZONAS'),
(30, 0, '94', 'GUAINIA'),
(31, 0, '95', 'GUAVIARE'),
(32, 0, '97', 'VAUPES'),
(33, 0, '99', 'VICHADA');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
