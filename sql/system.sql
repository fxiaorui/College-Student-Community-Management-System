# DROP DATABASE IF EXISTS cscms;
# CREATE DATABASE cscms;
USE Engling;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id          BIGINT(20) AUTO_INCREMENT COMMENT 'ID',
    student_id  CHAR(10) COMMENT '学号',
    password    VARCHAR(60) COMMENT '密码',
    name        VARCHAR(32) COMMENT '姓名',
    sex         CHAR(1) COMMENT '性别',
    email       VARCHAR(64) COMMENT '邮箱',
    major       VARCHAR(32) COMMENT '专业',
    class_id       VARCHAR(32) COMMENT '班级',
    status      CHAR(1) COMMENT '启用状态(0禁用,1启用)',
    create_time DATETIME COMMENT '创建时间',
    create_by   BIGINT(20) COMMENT '创建人',
    update_time DATETIME COMMENT '更新时间',
    update_by   BIGINT(20) COMMENT '更新人',
    PRIMARY KEY (id)
) COMMENT '用户表';

INSERT INTO user (id, student_id, password, name, sex,email, major, class_id, status, create_time, create_by, update_time,
                  update_by)
VALUES (1, 'admin', '$2a$10$Qw25I9FUTOVIK.V4kNPAZe47oS.EzvPgzTXm0d0CwgS7LAOfWM9d6', '超级用户', '无', '无', '无', '无',1, NULL, NULL,
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
VALUES (1, 'ROOT', '所有权限', 99, NULL, NULL, NULL, NULL);

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
       (20, 1, 115);

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
       (111, '新增用户', 'users/add'),
       (112, '删除用户', 'users/del'),
       (113, '修改用户', 'users/edit'),
       (114, '查询用户', 'users/detail'),
       (115, '查询用户列表', 'users/list');
