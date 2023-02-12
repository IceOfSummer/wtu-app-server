DROP TABLE IF EXISTS commodity;
CREATE TABLE commodity(
                          commodity_id INT(11) NOT NULL AUTO_INCREMENT  COMMENT '商品id' ,
                          owner_id INT(11) NOT NULL   COMMENT '拥有者id' ,
                          name VARCHAR(30) NOT NULL   COMMENT '商品名称' ,
                          description VARCHAR(200) NOT NULL   COMMENT '商品描述' ,
                          create_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                          price DECIMAL(9,2) NOT NULL   COMMENT '价格' ,
                          preview_image VARCHAR(100) NOT NULL   COMMENT '预览图;实物预览,;填写URL地址' ,
                          images VARCHAR(200) NOT NULL   COMMENT '实物演示图;使用json的数组保存每个图片的url' ,
                          trade_location VARCHAR(20) NOT NULL   COMMENT '交易地点' ,
                          count INT NOT NULL   COMMENT '剩余数量' ,
                          status TINYINT   DEFAULT 0 COMMENT '状态;0:上架中，1：已下架' ,
                          auto_take_down BOOL   DEFAULT TRUE COMMENT '自动下架;当商品数量为0时，自动下架' ,
                          version INT(11)   DEFAULT 1 COMMENT '乐观锁' ,
                          PRIMARY KEY (commodity_id)
)  COMMENT = '商品;商品搜索时使用ElasticSearch搜索';


CREATE INDEX commodity_owner_index ON commodity(owner_id);

DROP TABLE IF EXISTS trade_stat;
CREATE TABLE trade_stat(
                           user_id INT(11) NOT NULL   COMMENT '用户id' ,
                           selling_count INT   DEFAULT 0 COMMENT '当前正在售卖的商品数量' ,
                           receive_count INT   DEFAULT 0 COMMENT '待收货的商品数量' ,
                           delivery_count INT   DEFAULT 0 COMMENT '待发货的商品数量' ,
                           PRIMARY KEY (user_id)
)  COMMENT = '交易统计';

DROP TABLE IF EXISTS user;
CREATE TABLE user(
                     user_id INT NOT NULL AUTO_INCREMENT  COMMENT '用户id' ,
                     username VARCHAR(25) NOT NULL   COMMENT '用户名' ,
                     email VARCHAR(30)    COMMENT '用户邮箱' ,
                     password VARCHAR(70) NOT NULL   COMMENT '用户密码' ,
                     status INT   DEFAULT 0 COMMENT '用户状态;0表示正常用户;其它数字根据需要设置，如1代表用户被封禁' ,
                     role VARCHAR(30) NOT NULL  DEFAULT 'ROLE_USER' COMMENT '用户权限;若有多种权限，用逗号分隔' ,
                     credit INT   DEFAULT 100 COMMENT '用户信誉' ,
                     nickname VARCHAR(20) NOT NULL   COMMENT '昵称;昵称' ,
                     wtu_id VARCHAR(12) NOT NULL   COMMENT '学号' ,
                     name VARCHAR(5) NOT NULL   COMMENT '真实姓名' ,
                     class_name VARCHAR(30) NOT NULL   COMMENT '班级信息' ,
                     PRIMARY KEY (user_id)
)  COMMENT = '用户表';


CREATE UNIQUE INDEX username_unique_index ON user(username);
CREATE UNIQUE INDEX wtu_id_unique_index ON user(wtu_id);

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`(
                        order_id INT NOT NULL AUTO_INCREMENT  COMMENT '订单id' ,
                        commodity_id INT NOT NULL   COMMENT '商品id' ,
                        buyer_id INT NOT NULL   COMMENT '顾客id' ,
                        seller_id INT NOT NULL   COMMENT '卖家id' ,
                        create_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                        remark VARCHAR(50)    COMMENT '备注;这个字段为买家一开始锁定时提供的备注' ,
                        count INT NOT NULL   COMMENT '数量' ,
                        status INT NOT NULL  DEFAULT 0 COMMENT '订单状态;0:交易中；1: 交易成功；2：交易失败' ,
                        finished_time DATETIME    COMMENT '完成时间' ,
                        buyer_remark VARCHAR(50)    COMMENT '买家备注;这个字段为买家确认后提供的备注' ,
                        seller_remark VARCHAR(50)    COMMENT '卖家备注;这个字符为卖家确认后提供的备注' ,
                        PRIMARY KEY (order_id)
)  COMMENT = '订单表';

DROP TABLE IF EXISTS user_trade;
CREATE TABLE user_trade(
                           user_id INT NOT NULL   COMMENT '用户id' ,
                           order_id INT NOT NULL   COMMENT '订单id' ,
                           type TINYINT NOT NULL   COMMENT '交易类型;0为买，1为卖' ,
                           trade_uid INT NOT NULL   COMMENT '对面的uid' ,
                           trade_name VARCHAR(25) NOT NULL   COMMENT '对方称呼' ,
                           PRIMARY KEY (user_id,order_id)
)  COMMENT = '用户交易记录表';

DROP TABLE IF EXISTS community_message;
CREATE TABLE community_message(
                                  id INT NOT NULL AUTO_INCREMENT  COMMENT '消息id' ,
                                  pid INT NOT NULL  DEFAULT 0 COMMENT '父评论的id' ,
                                  author INT NOT NULL   COMMENT '作者id' ,
                                  title VARCHAR(30)    COMMENT '标题' ,
                                  content VARCHAR(10000) NOT NULL   COMMENT '消息内容' ,
                                  create_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP() COMMENT '创建时间' ,
                                  `like` INT   DEFAULT 0 COMMENT '点赞数量' ,
                                  dislike INT   DEFAULT 0 COMMENT '踩数量' ,
                                  reply_to INT    COMMENT '回复目标;如果该消息为评论消息，则该属性指出用户的这条评论是给谁的' ,
                                  reply_count INT   DEFAULT 0 COMMENT '当前消息的回复数量;二级回复没有回复数量，仅对根消息和一级回复设置' ,
                                  content_preview VARCHAR(35) NOT NULL   COMMENT '内容预览' ,
                                  PRIMARY KEY (id)
)  COMMENT = '社区消息';


CREATE INDEX pid_index ON community_message(pid);

DROP TABLE IF EXISTS feedback_record;
CREATE TABLE feedback_record(
                                uid INT NOT NULL   COMMENT '用户id' ,
                                message_id INT NOT NULL   COMMENT '消息id' ,
                                `like` BOOL    COMMENT '是否点赞;null为没有表态，true为赞，false为踩' ,
                                PRIMARY KEY (uid,message_id)
)  COMMENT = '社区消息反馈表';

DROP TABLE IF EXISTS user_auth;
CREATE TABLE user_auth(
                          uid INT NOT NULL   COMMENT '用户id' ,
                          jwt_id INT NOT NULL  DEFAULT 1 COMMENT 'jwt令牌的最新id' ,
                          version INT NOT NULL  DEFAULT 0 COMMENT '乐观锁' ,
                          PRIMARY KEY (uid)
)  COMMENT = '用户认证表';

DROP TABLE IF EXISTS event_remind;
CREATE TABLE event_remind(
                             event_remind_id INT NOT NULL AUTO_INCREMENT  COMMENT '消息id' ,
                             receiver_id INT NOT NULL   COMMENT '接收者id' ,
                             source_id INT NOT NULL   COMMENT '事件源;如评论ID、文章ID' ,
                             source_type INT NOT NULL   COMMENT '事件源类型' ,
                             source_content VARCHAR(30)    COMMENT '事件源内容' ,
                             state BOOL   DEFAULT FALSE COMMENT '是否已读' ,
                             sender_id INT NOT NULL   COMMENT '操作者id' ,
                             create_time TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP() COMMENT '创建时间' ,
                             target_content VARCHAR(30) NOT NULL   COMMENT '目标的内容;若对文章或评论点赞则在这里提供文章或评论的标题或内容' ,
                             PRIMARY KEY (event_remind_id)
)  COMMENT = '事件提醒';


CREATE INDEX receiver_index ON event_remind(receiver_id);

