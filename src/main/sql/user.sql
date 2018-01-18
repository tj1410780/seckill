--用户登录表
use seckill;

CREATE TABLE user(
`user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户Id',
`user_name` varchar(20) NOT NULL COMMENT '用户名',
`password` varchar(20) NOT NULL COMMENT '密码',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
PRIMARY KEY(user_id),
UNIQUE KEY uni_user_name(user_name),
UNIQUE KEY uni_user_phone(user_phone)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录表';



--增加唯一索引（该列的值不能重复）
alter table user add unique(user_name);
alter table user add unique(user_phone);
--删除索引
alter table user drop index user_name;