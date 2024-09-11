DROP DATABASE IF EXISTS cscms;
CREATE DATABASE cscms;
USE cscms;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id          BIGINT(20) AUTO_INCREMENT COMMENT 'ID',
    username    VARCHAR(32) COMMENT '用户名',
    password    VARCHAR(60) COMMENT '密码',
    nickname    VARCHAR(32) COMMENT '昵称',
    sex         CHAR(1) COMMENT '性别(0男,1女)',
    status      CHAR(1) COMMENT '启用状态(0禁用,1启用)',
    create_time DATETIME COMMENT '创建时间',
    create_by   BIGINT(20) COMMENT '创建人',
    update_time DATETIME COMMENT '更新时间',
    update_by   BIGINT(20) COMMENT '更新人',
    PRIMARY KEY (id)
) COMMENT '用户表';

INSERT INTO user (id, username, password, nickname, sex, status, create_time, create_by, update_time,
                  update_by)
VALUES (1, 'admin', '$2a$10$i1qOMS9z5Zrkek8iTasns.pO/Uids98/AjkwfJkbMUgleT0z3PZHe', '超级用户', '0', 1, NULL, NULL,
        NULL, NULL);

# 用户-角色关联表
DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
    id      BIGINT(20) AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT(20) COMMENT '用户ID',
    role_id BIGINT(20) COMMENT '角色ID',
    PRIMARY KEY (id)
) COMMENT '用户-角色关联表';

INSERT INTO user_role (id, user_id, role_id)
VALUES (1, 1, 1);

# 角色表
DROP TABLE IF EXISTS role;
CREATE TABLE role
(
    id          BIGINT(20) AUTO_INCREMENT COMMENT 'ID',
    name        VARCHAR(30) COMMENT '角色名',
    comment     varchar(255) COMMENT '备注',
    sort        INT(3) COMMENT '序号',
    create_time DATETIME COMMENT '创建时间',
    create_by   BIGINT(20) COMMENT '创建人',
    update_time DATETIME COMMENT '更新时间',
    update_by   BIGINT(20) COMMENT '更新人',
    PRIMARY KEY (id)
) COMMENT '角色表';

INSERT INTO role (id, name, comment, sort, create_time, create_by, update_time, update_by)
VALUES (1, 'ROOT', '所有权限', 99, NULL, NULL, NULL, NULL),
       (2, '管理员', '管理权限', 98, NULL, NULL, NULL, NULL),
       (3, '用户', '无操作权限, 仅有基础登录权限', 97, NULL, NULL, NULL, NULL);

# 角色-权限关联表
DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission
(
    id            BIGINT(20) AUTO_INCREMENT COMMENT 'ID',
    role_id       BIGINT(20) COMMENT '角色ID',
    permission_id BIGINT(20) COMMENT '权限ID',
    PRIMARY KEY (id)
) COMMENT '角色-权限关联表';

INSERT INTO role_permission (id, role_id, permission_id)
VALUES (1, 2, 1),
       (10, 1, 1),
       (11, 1, 101),
       (12, 1, 102),
       (13, 1, 103),
       (14, 1, 104),
       (15, 1, 105),
       (16, 1, 111),
       (17, 1, 112),
       (18, 1, 113),
       (19, 1, 114),
       (20, 1, 115),
       (21, 1, 121),
       (22, 1, 122),
       (23, 1, 123),
       (24, 1, 124),
       (25, 1, 125),
       (26, 1, 131),
       (27, 1, 132),
       (28, 1, 133),
       (29, 1, 134),
       (30, 1, 135),
       (31, 1, 141),
       (32, 1, 142),
       (33, 1, 143),
       (34, 1, 144),
       (35, 1, 145),
       (36, 1, 151),
       (37, 1, 152),
       (38, 1, 153),
       (39, 1, 154),
       (40, 1, 155),
       (41, 1, 161),
       (42, 1, 162),
       (43, 1, 163),
       (44, 1, 164),
       (45, 1, 165),
       (46, 1, 171),
       (47, 1, 172),
       (48, 1, 173),
       (49, 1, 174),
       (50, 1, 175),
       (51, 1, 501),
       (52, 1, 502),
       (53, 1, 503),
       (54, 1, 504),
       (55, 1, 505),
       (56, 1, 511),
       (57, 1, 512),
       (58, 1, 513),
       (59, 1, 514),
       (60, 1, 515),

       (101, 2, 105),
       (102, 2, 115),
       (103, 2, 125),
       (104, 2, 131),
       (105, 2, 132),
       (106, 2, 133),
       (107, 2, 134),
       (108, 2, 135),
       (109, 2, 141),
       (110, 2, 142),
       (111, 2, 143),
       (112, 2, 144),
       (113, 2, 145),
       (114, 2, 151),
       (115, 2, 152),
       (116, 2, 153),
       (117, 2, 154),
       (118, 2, 155),
       (119, 2, 161),
       (120, 2, 162),
       (121, 2, 163),
       (122, 2, 164),
       (123, 2, 165),
       (124, 2, 171),
       (125, 2, 172),
       (126, 2, 173),
       (127, 2, 174),
       (128, 2, 175),
       (129, 2, 501),
       (130, 2, 502),
       (131, 2, 503),
       (132, 2, 504),
       (133, 2, 505),
       (134, 2, 511),
       (135, 2, 512),
       (136, 2, 513),
       (137, 2, 514),
       (138, 2, 515);

# 权限表
DROP TABLE IF EXISTS permission;
CREATE TABLE permission
(
    id    BIGINT(20) AUTO_INCREMENT COMMENT 'ID',
    name  VARCHAR(30) COMMENT '权限名',
    value VARCHAR(64) COMMENT '权限值',
    PRIMARY KEY (id)
) COMMENT '权限表';

INSERT INTO permission (id, name, value)
VALUES (1, '修改密码', 'users/rePassword'),
       (101, '新增角色', 'roles/add'),
       (102, '删除角色', 'roles/del'),
       (103, '修改角色', 'roles/edit'),
       (104, '查询角色', 'roles/detail'),
       (105, '查询角色列表', 'roles/list'),
       (111, '新增科室', 'depts/add'),
       (112, '删除科室', 'depts/del'),
       (113, '修改科室', 'depts/edit'),
       (114, '查询科室', 'depts/detail'),
       (115, '查询科室列表', 'depts/list'),
       (121, '新增用户', 'users/add'),
       (122, '删除用户', 'users/del'),
       (123, '修改用户', 'users/edit'),
       (124, '查询用户', 'users/detail'),
       (125, '查询用户列表', 'users/list'),
       (131, '新增项目类别', 'project/categories/add'),
       (132, '删除项目类别', 'project/categories/del'),
       (133, '修改项目类别', 'project/categories/edit'),
       (134, '查询项目类别', 'project/categories/detail'),
       (135, '查询项目类别列表', 'project/categories/list'),
       (141, '新增套餐类别', 'combination/categories/add'),
       (142, '删除套餐类别', 'combination/categories/del'),
       (143, '修改套餐类别', 'combination/categories/edit'),
       (144, '查询套餐类别', 'combination/categories/detail'),
       (145, '查询套餐类别列表', 'combination/categories/list'),
       (151, '新增项目', 'projects/add'),
       (152, '删除项目', 'projects/del'),
       (153, '修改项目', 'projects/edit'),
       (154, '查询项目', 'projects/detail'),
       (155, '查询项目列表', 'projects/list'),
       (161, '新增项目结果', 'project/results/add'),
       (162, '删除项目结果', 'project/results/del'),
       (163, '修改项目结果', 'project/results/edit'),
       (164, '查询项目结果', 'project/results/detail'),
       (165, '查询项目结果列表', 'project/results/list'),
       (171, '新增套餐', 'combinations/add'),
       (172, '删除套餐', 'combinations/del'),
       (173, '修改套餐', 'combinations/edit'),
       (174, '查询套餐', 'combinations/detail'),
       (175, '查询套餐列表', 'combinations/list'),

       (501, '登记', 'registers/add'),
       (502, '取消登记', 'registers/cancel'),
       (503, '查询登记详情', 'registers/detail'),
       (504, '查询登记列表', 'registers/list'),
       (505, '打印指引单', 'registers/printGuide'),
       (511, '分检登记', 'checks/register/add'),
       (512, '取消分检登记', 'checks/register/cancel'),
       (513, '查询项目详情', 'checks/item/detail'),
       (514, '检查结果录入', 'checks/result/entry'),
       (515, '打印体检报告', 'reports/print');

