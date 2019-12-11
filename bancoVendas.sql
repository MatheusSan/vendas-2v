-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           10.4.10-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Copiando estrutura do banco de dados para venda
CREATE DATABASE IF NOT EXISTS `venda` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `venda`;

-- Copiando estrutura para tabela venda.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `sobrenome` varchar(255) NOT NULL,
  `situacao` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela venda.clientes: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` (`id`, `nome`, `sobrenome`, `situacao`) VALUES
	(1, 'Ana', 'Silva', 1),
	(2, 'Rafael', 'Dias', 1),
	(3, 'Matheus', 'Santos', 0),
	(4, 'Andressa', 'Fiss', 1);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;

-- Copiando estrutura para tabela venda.itens
CREATE TABLE IF NOT EXISTS `itens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) NOT NULL,
  `id_produto` int(11) NOT NULL,
  `quantidade` decimal(10,2) NOT NULL,
  `total_item` decimal(10,2) NOT NULL,
  `situacao` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pedido` (`id_pedido`),
  KEY `id_produto` (`id_produto`),
  CONSTRAINT `itens_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id`),
  CONSTRAINT `itens_ibfk_2` FOREIGN KEY (`id_produto`) REFERENCES `produtos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela venda.itens: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `itens` DISABLE KEYS */;
INSERT INTO `itens` (`id`, `id_pedido`, `id_produto`, `quantidade`, `total_item`, `situacao`) VALUES
	(12, 22, 3, 2.00, 6.40, 0),
	(13, 22, 6, 2.00, 5.00, 1),
	(14, 23, 2, 3.00, 29.40, 1),
	(15, 23, 6, 1.00, 2.50, 1),
	(16, 24, 6, 1.00, 2.50, 1),
	(17, 24, 7, 2.00, 11.80, 1);
/*!40000 ALTER TABLE `itens` ENABLE KEYS */;

-- Copiando estrutura para tabela venda.pedidos
CREATE TABLE IF NOT EXISTS `pedidos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pagamento` varchar(255) NOT NULL,
  `estado` varchar(255) NOT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date NOT NULL,
  `situacao` varchar(255) NOT NULL DEFAULT '',
  `id_cliente` int(11) NOT NULL,
  `total_pedido` decimal(10,2) NOT NULL,
  `notaFiscal` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela venda.pedidos: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
INSERT INTO `pedidos` (`id`, `pagamento`, `estado`, `data_criacao`, `data_modificacao`, `situacao`, `id_cliente`, `total_pedido`, `notaFiscal`) VALUES
	(1, 'Credito', 'faturado', '2019-11-07', '2019-11-12', '0', 1, 125.65, 1),
	(22, 'à vista', 'faturado', '2019-12-04', '2019-12-04', '1', 3, 11.40, 22),
	(23, 'à vista', 'Aberto', '2019-12-05', '2019-12-05', '1', 3, 31.90, 0),
	(24, 'à vista', 'Aberto', '2019-12-05', '2019-12-05', '1', 2, 14.30, 0);
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;

-- Copiando estrutura para tabela venda.produtos
CREATE TABLE IF NOT EXISTS `produtos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `situacao` tinyint(1) NOT NULL,
  `quantidade` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela venda.produtos: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
INSERT INTO `produtos` (`id`, `nome`, `valor`, `descricao`, `situacao`, `quantidade`) VALUES
	(1, 'Café\r\n', 12.80, 'Café em pó tradicional Igaçu lata 400g\r\n', 1, 85),
	(2, 'Erva Mate', 9.80, 'Erva Mate Pura Folha 1kg', 1, 50),
	(3, 'Chá Preto', 3.20, 'Prenda', 1, 52),
	(4, 'Arroz\n', 10.90, 'Arroz Camil branco polido tipo 1 pacote 5kg\n', 1, 87),
	(5, 'Feijão', 6.80, 'Feijão Tordilho pacote 1kg', 0, 71),
	(6, 'Fone de ouvido', 2.50, 'para ouvir', 1, 13),
	(7, 'Batata', 5.90, 'BatataDoce', 1, 398),
	(8, 'Mochila', 80.55, 'Carregar_Coisas', 1, 100);
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
