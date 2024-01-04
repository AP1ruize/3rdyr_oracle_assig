This project adopts a hybrid model of C/S mode and B/S mode. The library administrator uses a graphical interface program based on Java Swing, and the reader uses a Web client (developed using JSP) to obtain services. The Web server is Tomcat, and the database is Oracle 11g.

Overall Design
1) Database design
7 database tables were established, including user information table, book information table, inventory table, borrowing table, reservation table, announcement table, and favorites table.
The id in the book and reader information tables is the primary key of each table. The attributes of these two IDs used in other tables are foreign keys.
The specific information will be described in the detailed design. The E-R diagram is as follows:
![Annotation 2024-01-03 222155](https://github.com/AP1ruize/oracle_assig/assets/71389528/fd974c1c-3fe0-4f31-8ab4-98d119539311)
2) UI design
admin desktop program:
![Picture2](https://github.com/AP1ruize/oracle_assig/assets/71389528/975cfa18-4ce4-4e3f-ba5a-b448b5a48d57)
![Picture3](https://github.com/AP1ruize/oracle_assig/assets/71389528/672d4afe-3ed4-4f76-9844-d21204b06156)
web app for clients:
![Annotation 2024-01-03 232458](https://github.com/AP1ruize/oracle_assig/assets/71389528/428c7d8e-23d4-4d7b-b173-c7304603de6e)
![Annotation 2024-01-03 232530](https://github.com/AP1ruize/oracle_assig/assets/71389528/a03aa3d0-073d-4ceb-a4e5-65c3c28e92d4)
![Annotation 2024-01-03 232945](https://github.com/AP1ruize/oracle_assig/assets/71389528/d135e91a-ef55-47c0-9c54-4b50aac7d394)

Detailed Design
1) Database design
In the database design, many advanced functions of the database were in use. In addition to setting the added table in the user table space, primary key and foreign key constraints,
views are also used to simplify book queries (requiring data from two tables), and used blob storage The picture of the book (optional) uses triggers and sequences to realize the self-increment
of the id. These functions facilitate the implementation of business logic.
**User information table**
![image](https://github.com/AP1ruize/oracle_assig/assets/71389528/6dbfe9fd-8586-4482-8c89-0b33f6ab1223)
property    type      explanation
Userid	   Number	  user id (primary key)
Password	Varchar2	   password
Nickname	Varchar2	   nickname
Exp	       Number	       level
Credit	   Number	       credit
Admin	     Number	     is admin?（1-yes, 0-no）

This table also adds triggers for auto-increment, which will be mentioned later when describing triggers and sequences.


**Book information table (using BLOB)**
![image](https://github.com/AP1ruize/oracle_assig/assets/71389528/e79cc52e-422d-4878-b422-003ab0fd9ea1)
property    type      explanation
Bookid	   Number	 book id (primary key)
Bookname	Varchar2	   book name
Press	    Varchar2	     press
Author	  Varchar2	     author
Type	    Varchar2	     type
Intro	    Varchar2	  introduction
Pic	        Blob	       image

**Inventory information table**
 ![image](https://github.com/AP1ruize/oracle_assig/assets/71389528/dd3f0aae-efb9-49df-a2ef-3c7e266e3fe0)
(Note: The screenshot time of this table is different from that of the book information table, and the database content does not match, which does not mean that no foreign key has been added)
property    type      explanation
Bookid	   Number	   book id (foreign key)
Amount	   Number	   total amount of the book
Remain	   Number	   remain in inventory 

Statements that declare the foreign key :
alter table stock add constraint fk_stock_bookid
foreign key (bookid)
references bookinfo(bookid);

**table of reserve**
 ![image](https://github.com/AP1ruize/oracle_assig/assets/71389528/14e2c9ea-3a6e-493f-9236-44c0d16e7b6b)
(Note: The screenshot time of this table is different from that of the book information table, and the database content does not match, which does not mean that no foreign key has been added)
property    type      explanation
Userid	   Number	   user id (foreign key)
Bookid	   Number	   book id (foreign key)
Time	   Timestamp	  time of order

Statements that declare the foreign key :
alter table preorder add constraint fk_preorder_userid 
foreign key (userid) references userinfo(userid);

alter table preorder add constraint fk_preorder_bookid 
foreign key (bookid) references bookinfo(bookid);

**table of borrowing**
 ![image](https://github.com/AP1ruize/oracle_assig/assets/71389528/6edc4343-0f9e-4354-8cfe-d79d58a039d9)
(Note: The screenshot time of this table is different from that of the book information table, and the database content does not match, which does not mean that no foreign key has been added)
property    type      explanation
Userid	   Number	  user id (foreign key)
Bookid	   Number	  book id (foreign key)
Amount	   Number	      amount
Loantime	Timestamp	  time of borrowing
Duration	 Number	   borrowing period

Statements that declare the foreign key :
alter table loan add constraint fk_loan_userid 
foreign key (userid) references userinfo(userid);

alter table loan add constraint fk_loan_bookid 
foreign key (bookid) references bookinfo(bookid);

**table of announcement**
![image](https://github.com/AP1ruize/oracle_assig/assets/71389528/dbb02dda-6da2-4292-8744-4a0ac782e4e5)
property    type        explanation
Adminid	    Number	  user id of admin (foreign key)
Edittime	Timestamp	  time of editting
Title	    Varchar2	       title
Text	    Varchar2	      main text
Status	   Number	    visibility（0-invisible 1-visible）

Statements that declare the foreign key :
alter table notice add constraint fk_notice_userid 
foreign key (adminid) references userinfo(userid);

**table of favorites**
 ![image](https://github.com/AP1ruize/oracle_assig/assets/71389528/f6e99970-fc47-4b21-9da4-1eaaa1c8b262)
(Note: The screenshot time of this table is different from that of the book information table, and the database content does not match, which does not mean that no foreign key has been added)
property    type      explanation
Userid	   Number	   user id (foreign key)
Bookid	   Number	   book id (foreign key）
Time	   Timestamp	  time of editting

Statements that declare the foreign key :
alter table favorite add constraint fk_favorite_userid 
foreign key (userid) references userinfo(userid);

alter table favorite add constraint fk_favorite_bookid 
foreign key (bookid) references bookinfo(bookid);

Triggers & Sequences
Oracle does not have the primary key auto-increment feature like MySQL. In order to easily obtain new books and user IDs, 
sequences and triggers are used to implement the primary key auto-increment function.

Statements that create sequences:
CREATE SEQUENCE USERID_SEQUENCE
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
NOCACHE;
CREATE SEQUENCE BOOKID_SEQUENCE
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCACHE;
Statements that create triggers:
CREATE TRIGGER USERID_AUTOINCREASE
    BEFORE INSERT ON USERINFO
    FOR EACH ROW
    BEGIN
    SELECT USERID_SEQUENCE.NEXTVAL INTO :NEW.USERID FROM DUAL;
    END;
/
CREATE TRIGGER BOOKID_AUTOINCREASE
    BEFORE INSERT ON BOOKINFO
    FOR EACH ROW
    BEGIN
    SELECT BOOKID_SEQUENCE.NEXTVAL INTO :NEW.BOOKID FROM DUAL;
    END;
/

Views
When searching for books, the contents of two tables (book information table + inventory table) need to be displayed. 
In order to facilitate book searching, views are used to simplify the search statements.
Statements that create a view:
create view booksearch as
select bi.bookid bookid, bi.bookname bookname, bi.press press, bi.author author, bi.type type, bi.pic pic, st.amount amount,st.remain remain
from bookinfo bi left join stock st on bi.bookid=st.bookid
/

query： select * from booksearch where (filters)

To be continued...



