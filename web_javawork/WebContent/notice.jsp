<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.*"%>
<%@page import="DAO.*"%>

<%
ArrayList<Notice> noticeList=(ArrayList<Notice>)session.getAttribute("noticeList"); 
String str=request.getParameter("k");
int i=Integer.parseInt(str);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公告</title>
<style type="text/css">
body{background-image:url(assets2/images/slide-01.jpg)}
h2,h5,p{color:#fff}
</style>
</head>
<body>
<h2 align="center"><%out.print(noticeList.get(i).getTitle()); %></h2>
<h5 align="center"><%out.print(noticeList.get(i).getEditTime());%></h5>
<p align="center"><%out.print(noticeList.get(i).getText()); %></p>

</body>
</html>