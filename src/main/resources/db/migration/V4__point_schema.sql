CREATE TABLE `points` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `point` SMALLINT NOT NULL,
  `description` TEXT NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20),
  `updated_by` bigint(20),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `subjects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tags_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT IGNORE INTO subjects(name) VALUES('数学');
INSERT IGNORE INTO subjects(name) VALUES('语文');
INSERT IGNORE INTO subjects(name) VALUES('作文');
INSERT IGNORE INTO subjects(name) VALUES('口语');
INSERT IGNORE INTO subjects(name) VALUES('写字');
INSERT IGNORE INTO subjects(name) VALUES('英语');
INSERT IGNORE INTO subjects(name) VALUES('体育');
INSERT IGNORE INTO subjects(name) VALUES('美术');
INSERT IGNORE INTO subjects(name) VALUES('音乐');
INSERT IGNORE INTO subjects(name) VALUES('围棋');
INSERT IGNORE INTO subjects(name) VALUES('道德与法治');
INSERT IGNORE INTO subjects(name) VALUES('心理/安全');
INSERT IGNORE INTO subjects(name) VALUES('社团');
INSERT IGNORE INTO subjects(name) VALUES('劳动');


CREATE TABLE `point_subject` (
  `point_id` bigint(20) NOT NULL,
  `subject_id` bigint(20) NOT NULL,
  PRIMARY KEY (`point_id`,`subject_id`),
  KEY `fk_point_subject_sub_id` (`subject_id`),
  CONSTRAINT `fk_point_subject_sub_id` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`),
  CONSTRAINT `fk_point_subject_point_id` FOREIGN KEY (`point_id`) REFERENCES `points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
