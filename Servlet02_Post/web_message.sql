
-- tbl_msg / nickname varchar2(50) message varchar2(500)

create table tbl_msg(
  nickname varchar2(50)
  , message varchar2(500)
);

commit;

select * from tbl_msg;

create table tbl_post(
  id varchar2(50)
  , post varchar2(500)
);

commit;