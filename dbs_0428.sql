CREATE TABLE USERINFO(
    USERID NUMBER,
    PASSWORD VARCHAR2(20) NOT NULL,
    NICKNAME VARCHAR2(30),
    EXP NUMBER NOT NULL,
    CREDIT NUMBER NOT NULL,
    ADMIN NUMBER NOT NULL,
    PRIMARY KEY(USERID));
CREATE SEQUENCE USERID_SEQUENCE
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCACHE;
CREATE TRIGGER USERID_AUTOINCREASE
    BEFORE INSERT ON USERINFO
    FOR EACH ROW
    BEGIN
    SELECT USERID_SEQUENCE.NEXTVAL INTO :NEW.USERID FROM DUAL;
    END;
/
CREATE TABLE BOOKINFO(
    BOOKID NUMBER,
    BOOKNAME VARCHAR2(100) NOT NULL,
    PRESS VARCHAR2(50),
    AUTHOR VARCHAR2(100) NOT NULL,
    TYPE VARCHAR2(30),
    INTRO VARCHAR2(1000),
    PIC BLOB,
    PRIMARY KEY(BOOKID));
CREATE SEQUENCE BOOKID_SEQUENCE
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCACHE;
CREATE TRIGGER BOOKID_AUTOINCREASE
    BEFORE INSERT ON BOOKINFO
    FOR EACH ROW
    BEGIN
    SELECT BOOKID_SEQUENCE.NEXTVAL INTO :NEW.BOOKID FROM DUAL;
    END;
/
CREATE TABLE LOAN(
    USERID NUMBER NOT NULL,
    BOOKID NUMBER NOT NULL,
    AMOUNT NUMBER NOT NULL,
    LOANDATE DATE NOT NULL,
    LOANTIME TIMESTAMP NOT NULL,
    DURATION NUMBER NOT NULL);

CREATE TABLE STOCK(
    BOOKID NUMBER NOT NULL,
    AMOUNT NUMBER NOT NULL,
    REMAIN NUMBER NOT NULL);
CREATE TABLE NOTICE(
    ADMINID NUMBER NOT NULL,
    EDITDATE DATE NOT NULL,
    EDITTIME TIMESTAMP NOT NULL,
    TITLE VARCHAR2(40) NOT NULL,
    TEXT VARCHAR2(1000) NOT NULL,
    STATUS NUMBER NOT NULL);
CREATE TABLE FAVORITE(
    USERID NUMBER NOT NULL,
    BOOKID NUMBER NOT NULL,
    TIME TIMESTAMP NOT NULL);
CREATE TABLE PREORDER(
    USERID NUMBER NOT NULL,
    BOOKID NUMBER NOT NULL,
    TIME TIMESTAMP NOT NULL);

create view booksearch as 
select bi.bookid bookid, bi.bookname bookname, bi.press press, 
bi.author author, bi.type type, bi.pic pic, st.amount amount, 
st.remain remain 
from bookinfo bi left join stock st on bi.bookid=st.bookid 
/

alter table stock add constraint fk_stock_bookid
foreign key (bookid)
references bookinfo(bookid);

alter table preorder add constraint fk_preorder_userid 
foreign key (userid) references userinfo(userid);

alter table preorder add constraint fk_preorder_bookid 
foreign key (bookid) references bookinfo(bookid);

alter table loan add constraint fk_loan_userid 
foreign key (userid) references userinfo(userid);

alter table loan add constraint fk_loan_bookid 
foreign key (bookid) references bookinfo(bookid);

alter table notice add constraint fk_notice_userid 
foreign key (adminid) references userinfo(userid);

alter table favorite add constraint fk_favorite_userid 
foreign key (userid) references userinfo(userid);

alter table favorite add constraint fk_favorite_bookid 
foreign key (bookid) references bookinfo(bookid);

