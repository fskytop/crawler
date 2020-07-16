CREATE TABLE `photos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `path` varchar(100) NOT NULL,
  `host` varchar(60) NOT NULL,
  `url` varchar(280),
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20),
  `updated_by` bigint(20),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_photos_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tags_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT IGNORE INTO tags(name) VALUES('TAG_DEVELOPER');
INSERT IGNORE INTO tags(name) VALUES('TAG_DEVOPS');
INSERT IGNORE INTO tags(name) VALUES('TAG_CONTAINER');
INSERT IGNORE INTO tags(name) VALUES('TAG_SECURITY');
INSERT IGNORE INTO tags(name) VALUES('TAG_LANGUAGE');
INSERT IGNORE INTO tags(name) VALUES('TAG_SYSTEM');
INSERT IGNORE INTO tags(name) VALUES('TAG_FRAMEWORK');
INSERT IGNORE INTO tags(name) VALUES('TAG_TOOL');
INSERT IGNORE INTO tags(name) VALUES('TAG_COVID_19');
INSERT IGNORE INTO tags(name) VALUES('TAG_PRACTICE');
INSERT IGNORE INTO tags(name) VALUES('TAG_TDD');
INSERT IGNORE INTO tags(name) VALUES('TAG_AGILE');
INSERT IGNORE INTO tags(name) VALUES('TAG_DESIGN');
INSERT IGNORE INTO tags(name) VALUES('TAG_CLOUD');
INSERT IGNORE INTO tags(name) VALUES('TAG_MICROSERVICES');
INSERT IGNORE INTO tags(name) VALUES('TAG_AI');
INSERT IGNORE INTO tags(name) VALUES('TAG_UNKNOWN');

CREATE TABLE `photo_tags` (
  `photo_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`photo_id`,`tag_id`),
  KEY `fk_photo_tags_tag_id` (`tag_id`),
  CONSTRAINT `fk_photo_tags_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`),
  CONSTRAINT `fk_photo_tags_photo_id` FOREIGN KEY (`photo_id`) REFERENCES `photos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
