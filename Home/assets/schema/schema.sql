CREATE TABLE t_category(
	 _id integer 		primary key autoincrement,
	 category_name		TEXT		not null,
	 category_order		INTEGER 	default 0,
	 category_icon		INTEGER
);
CREATE TABLE t_app(
	 _id integer primary key autoincrement,
	 app_name		text 	not null,
	 app_package	text 	not null,
	 category_id	integer not null,
	 app_icon		integer,
	 app_order		integer default 0,
	 app_flg		integer default 0,
	 start_times	integer default 0
);

INSERT INTO t_category (_id,category_name,category_order,category_icon) VALUES(1,"系统软件",1,0);
INSERT INTO t_category (_id,category_name,category_order,category_icon) VALUES(2,"游戏",2,0);
INSERT INTO t_category (_id,category_name,category_order,category_icon) VALUES(3,"其他APP",3,0);