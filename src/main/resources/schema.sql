-- DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `name` char(18)  NOT NULL,
  `id` char(18) NOT NULL ,
  `depart` char(50) NOT NULL,
  `phone` char(15) NOT NULL,
  `temperature` char(20) NOT NULL ,
  `gmt_create` date NOT NULL,
  PRIMARY KEY (`id`,`gmt_create`)
);
