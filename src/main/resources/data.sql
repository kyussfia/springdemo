INSERT INTO `springdemo`.`groups` (id, name) values (1, "ADMIN");
INSERT INTO `springdemo`.`groups` (id, name) values (2, "EDITOR");
INSERT INTO `springdemo`.`groups` (id, name) values (3, "USER");

INSERT INTO `springdemo`.`roles` (id, name) values (1, "ADMIN");
INSERT INTO `springdemo`.`roles` (id, name) values (2, "EDITOR");
INSERT INTO `springdemo`.`roles` (id, name) values (3, "USER");

INSERT INTO `springdemo`.`group_role` (group_id, role_id) values (1, 1);
INSERT INTO `springdemo`.`group_role` (group_id, role_id) values (1, 2);
INSERT INTO `springdemo`.`group_role` (group_id, role_id) values (1, 3);
INSERT INTO `springdemo`.`group_role` (group_id, role_id) values (2, 2);
INSERT INTO `springdemo`.`group_role` (group_id, role_id) values (3, 3);

-- Create user 'Admin' with pwd 'Admin'
INSERT INTO `springdemo`.`users` (last_login, password, username) VALUES (NULL, "$2a$10$rndMDrTXLQ/fUsWtm0OPW.OFfX3ctn9/4Yl5McYjEwrngIYHAsyja", "Admin");
INSERT INTO `springdemo`.`user_group` (user_id, group_id) SELECT id, 1 FROM `springdemo`.`users` WHERE username = "Admin";

-- Create user 'User 1' with pwd 'User 1'
INSERT INTO `springdemo`.`users` (last_login, password, username) VALUES (NULL, "$2a$10$pgVg62bXYdAn9.pHGcsVgeyc2cCWYZw8H8C8TnmvfLF89L6PEq7C.", "User 1");
INSERT INTO `springdemo`.`user_group` (user_id, group_id) SELECT id, 2 FROM `springdemo`.`users` WHERE username = "User 1";
INSERT INTO `springdemo`.`user_group` (user_id, group_id) SELECT id, 3 FROM `springdemo`.`users` WHERE username = "User 1";

-- Create user 'User 2' with pwd 'User 2'
INSERT INTO `springdemo`.`users` (last_login, password, username) VALUES (NULL, "$2a$10$p.xLKuMcUHNG63Cesvng0ej5t4Z1SqoVrnJEGIvdWnAeXu3WMVQOO", "User 2");
INSERT INTO `springdemo`.`user_group` (user_id, group_id) SELECT id, 2 FROM `springdemo`.`users` WHERE username = "User 2";

-- Create user 'User 3' with pwd 'User 3'
INSERT INTO `springdemo`.`users` (last_login, password, username) VALUES (NULL, "$2a$10$pxHjI2lLINoUru0Xgh2Jbey7Z5M1m8pJB0fVgv79VwnwSXIwOw5J6", "User 3");
INSERT INTO `springdemo`.`user_group` (user_id, group_id) SELECT id, 3 FROM `springdemo`.`users` WHERE username = "User 3";