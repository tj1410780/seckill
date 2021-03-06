--数据库初始化脚本

--创建数据库
CREATE DATABASE seckill;

--使用数据库
use seckill;

--创建秒杀库存表
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '库存数量',
`start_time` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '秒杀开启时间',
`end_time` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '秒杀结束时间',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

--初始化数据
insert into 
	seckill(name,number,start_time,end_time)
values
	('1000元秒杀iPhone6',100,'2018-01-05 00:00:00','2018-01-06 00:00:00'),
	('500元秒杀ipad2',200,'2018-01-07 00:00:00','2018-01-09 00:00:00'),
	('300元秒杀小米4',300,'2018-01-10 00:00:00','2018-01-12 00:00:00'),
	('600元秒杀红米note',500,'2018-01-06 00:00:00','2018-01-08 00:00:00');
	
--秒杀成功明细表
--用户登录认证相关信息
CREATE TABLE success_killed(
`seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
`state` tinyint NOT NULL COMMENT '状态标示：0：无效    1：成功     2：已付款',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(seckill_id,user_phone),
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
	
	
	