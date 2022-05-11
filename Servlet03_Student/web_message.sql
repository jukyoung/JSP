
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

select * from tbl_post;

create table tbl_std(
  id number primary key
  , name varchar2(50) not null
  , korean number not null
  , english number not null
  , math number not null
);
create sequence seq_student
 start with 1
 increment by 1
 nomaxvalue
 nocycle;
 
select * from tbl_std;

drop table tbl_std;

drop sequence seq_student;
 commit;
 