INSERT INTO `companies` (`id`, `created`, `date`, `updated`, `business_key`, `domain`, `email`, `name`, `version`) VALUES
(1, '2014-12-18 00:00:00', '2014-12-18 00:00:00', '2014-12-18 00:00:00', 'reimbursementRequest', 'www1.rushucloud.com', 'dev@dev.com', 'dc=www', 0),
(2, '2015-01-01 00:00:00', '2015-01-01 00:00:00', '2015-01-01 00:00:00', 'reimbursementRequest', 'www2.rushucloud.com', 'test@test.com', 'dc=123,dc=57,dc=78,dc=65', 0);

INSERT INTO `items` (`id`, `created`, `date`, `updated`, `owner`, `amount`, `category`,`place`, `invoices`, `name`, `notes`, `participant_ids`, `type`, `vendors`, `used`) VALUES
(1, '2014-12-19 16:24:28', '2014-12-19 08:00:00', '2014-12-19 16:24:28', 'employee1', 99, '1', '北京','1', '测试条目000', '说明。。。', NULL, 'CostConsumed', '1', b'0');

INSERT INTO `categories` (`id`, `created`, `date`, `updated`, `icon`, `label`,`name`, `parent_id`) VALUES
(1, '2014-12-26 12:23:32', NULL, '2014-12-26 12:23:32', '00', '总分类','总分类', NULL),
(2, '2014-12-26 12:25:36', NULL, '2014-12-26 12:25:36', '01', '礼品','礼品', 1),
(3, '2014-12-26 13:55:39', NULL, '2014-12-26 13:55:39', '02', '交通','交通', 1),
(4, '2014-12-26 13:55:39', NULL, '2014-12-26 13:55:39', '02', '公共交通','公共交通', 3),
(5, '2014-12-26 13:55:39', NULL, '2014-12-26 13:55:39', '02', '打车','打车', 3),
(6, '2014-12-26 13:55:39', NULL, '2014-12-26 13:55:39', '02', '飞机','飞机', 3),
(7, '2014-12-26 13:55:39', NULL, '2014-12-26 13:55:39', '02', '火车','火车', 3),
(8, '2014-12-26 13:55:39', NULL, '2014-12-26 13:55:39', '02', '话费','话费', 1),
(9, '2014-12-26 13:55:39', NULL, '2014-12-26 13:55:39', '02', '餐饮','美食', 1);

INSERT INTO user(id, name, login, password) values (1,'API_test_user','foo','bar');

INSERT INTO `expenses`(`id`,`created`,`date`,`updated`,`owner`,`amount`,`deleted`,`item_ids`,`manager_id`,`name`,`participant_ids`,`pid`,`status`) VALUES ( '1', '2015-02-06 16:52:47', '2015-02-06 08:00:00', '2015-02-06 16:52:49', 'employee1', '99', '0', '1', 'manager1', '测试报告ABC', 'employee0', '52501', 'Submitted' );
