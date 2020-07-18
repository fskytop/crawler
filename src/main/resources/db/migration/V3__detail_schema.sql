CREATE TABLE `details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(120) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

ALTER TABLE photos 
ADD COLUMN detail_id bigint(20)
AFTER id;
