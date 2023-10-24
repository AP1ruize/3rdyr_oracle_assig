<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.*"%>
<%
ArrayList<BookInfo> bookList=(ArrayList<BookInfo>)session.getAttribute("bookList");
ArrayList<String> pathList=(ArrayList<String>)session.getAttribute("pathList");
ArrayList<BookInfo> bookListNew=(ArrayList<BookInfo>)session.getAttribute("bookListNew");
ArrayList<String> pathListNew=(ArrayList<String>)session.getAttribute("pathListNew");
String str=request.getParameter("i");
String strNew=request.getParameter("j");
int i,j;
BookInfo book=null;
String path=null;
if(str!=null)
{
	i=Integer.parseInt(str);
	book=bookList.get(i);
	path=pathList.get(i);
}
else if(strNew!=null)
{
	j=Integer.parseInt(strNew);
	book=bookListNew.get(j);
	path=pathListNew.get(j);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>详情</title>
<link rel="stylesheet" type="text/css" href="assets/css/font-awesome.css">
<style type="text/css">
body{background-image:url(assets2/images/slide-02.jpg);}
#cover img{width:150px;height:200px;}
#cover {position:absolute;left:10%;top:20%}
#info{position:absolute;left:35%;top:24%;}
#info font{font-size:18px;}
#info p{font-size:15px;}
a:link {color:white;}   /* 未被访问的链接 */
a:visited {color:white;} /* 已被访问的链接 */
a:hover {color:blue;}   /* 鼠标指针移动到链接上 */
a:active {}  /* 正在被点击的链接 */
</style>
</head>
<body>

<div id="cover">
<h3><%=book.getBookName()%></h3>
<img alt="" src=<%=path%>>
<hr>
<a href="#"><i class="fa fa-bookmark-o" title="收藏"></i></a>
&nbsp;
<a href="#"><i class="fa fa-calendar" title="预约"></i></a>
</div>

<div id="info">
	<font>详细信息</font>
	<br>
	<p>
	<br>
	<b>编号：</b><%=book.getBookID()%><br><br>
	<b>作者：</b><%=book.getAuthor()%><br><br>
	<b>类型：</b><%=book.getType()%><br><br>
	<b>出版社：</b><%=book.getPress()%><br><br>
	<b>简介：</b><%=book.getIntro()%>
	</p>
</div>

</body>
</html>