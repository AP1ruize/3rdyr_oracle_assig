<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.ArrayList"%>
 <%@page import="entity.BookInfo" %>
 <%@page import="java.io.*"%>
 <%@page import="DAO.*"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"href="css/search.css"/>
<link rel="stylesheet" type="text/css" href="assets2/css/font-awesome.css">
<link href="https://fonts.googleapis.com/css?family=Raleway:100,200,300,400,500,600,700,800,900&display=swap" rel="stylesheet">
<title>检索</title>
</head>
<body>
	<%
	int uid=0;
	if(request.getAttribute("user")!=null){
	uid=Integer.parseInt(request.getAttribute("user").toString());
	}
    else{
    	uid=Integer.parseInt(request.getParameter("user"));
    }
	if(request.getParameter("bookid")!=null){
		int bid=Integer.parseInt(request.getParameter("bookid"));
		BookDAO bdao=new BookDAO();
		bdao.collect(uid, bid);

	}
	if(request.getParameter("prebookid")!=null){
		int bid=Integer.parseInt(request.getParameter("prebookid"));
		Preorder pre=new Preorder();
		pre.order(uid, bid);

	}
	%>
	<div id="normal-search">
	
	<form action="BookSearchServlet" align="center">
	<input class="search-input" type="text" name="q" placeholder="请输入书名或作者">
	<input class="submit-input" type="image" value="搜索" src="imgs/search.png">
	<input type="hidden" name="user" value=<%=uid%>>
	</form>
	</div>
	<div id="search">
	<div id="senior-search">
	<h3>高级检索</h3>
	<form class="fuzzy-search" action="FuzzySearchServlet">
		
		书名<br><input class="input-box" type="text" name="bookname"><br><br>
		作者<br><input class="input-box" type="text" name="author" ><br><br>
		类型
			<br><input class="select-box" type="radio" name="type" value="小说" >小说
			<br><input class="select-box" type="radio" name="type" value="传记">传记
			<br><input class="select-box" type="radio" name="type" value="诗集">诗集
			<br><input class="select-box" type="radio" name="type" value="戏剧">戏剧
			<br><input class="select-box" type="radio" name="type" value="艺术">艺术
			<br><input class="search-box" type="submit" value="搜索" >
			<input type="hidden" name="user" value=<%=uid%>>
	</form>
	</div>
	<div id="search-result">
	<table align="center" border="0" cellspacing="0" cellpadding="0">
		<!--  
		<tr>
			<th>图片</th>
			<th>编号</th>
			<th>名称</th>
			<th>作者</th>
			<th>出版社</th>
			<th>类型</th>
			<th>介绍</th>
		</tr>
		-->
		<%
		//把数据取出来
		ArrayList<BookInfo> bookList=new ArrayList<BookInfo>();
		if(request.getAttribute("bookList")!=null){
		bookList=(ArrayList<BookInfo>)request.getAttribute("bookList");
		}
		else{
		bookList=(ArrayList<BookInfo>)session.getAttribute("bookList");
		}
		session.setAttribute("bookList", bookList);
		ArrayList<String> path=new ArrayList<String>();
		
		if(bookList!=null&&bookList.size()>0)
		{
			for(int i=0;i<bookList.size();i++)
			{
				BookInfo book=bookList.get(i);
				path.add("covers/"+book.getBookID()+".jpg"); 
				String filepath=this.getServletContext().getRealPath(path.get(i));
				System.out.println("search.jsp:filePath:"+filepath);
				try
				{
					InputStream inputStream;
					inputStream = book.getPic().getBinaryStream();
					File fileOutput = new File(filepath);
					FileOutputStream fo = new FileOutputStream(fileOutput);
					int c;
					while ((c = inputStream.read()) != -1) 
						fo.write(c);
					fo.close();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
		%>
		<tr>
			<td width="60" height="125"align="center"><p><%out.print(i+1); %></p></td>
			<td width="200" height="125" align="center"><img alt="" src=<%=path.get(i)%>></td>
			<td class="search-info"width="200" height="125">
			<h3><a href="details.jsp?i=<%out.print(i);%>" target="_blank"><%=book.getBookName()%></a></h3>
			<br>
			<p><%=book.getAuthor()%>&emsp;著<br>
			<%=book.getType()%><br>
			<%=book.getPress()%></p>
			</td>
			<td class="search-icon" width="60" height="125">
			<ul>
			<li><a href="#" id=<%=book.getBookID()%> onclick="collect(this.id)"><i class="fa fa-bookmark-o" title="收藏"></i></a></li>
			<li><a href="#" id=<%=book.getBookID()%> onclick="preorder(this.id)"><i class="fa fa-calendar" title="预约"></i></a></li>
			</ul>
			</td>
		</tr>
		<tr>
			<td width="60"><hr></td>
			<td width="200"><hr></td>
			<td width="200"><hr></td>
			<td width="60" ><hr></td>
		</tr>
		<%}} else%>
		<tr>
			<%if(request.getAttribute("msg")!=null)out.print(request.getAttribute("msg")); %>
		</tr>
	</table>
	<script>
	function collect(bookid){
		<%
		for(int j=0;j<bookList.size();j++)
		{
			int bid=bookList.get(j).getBookID();
			BookDAO bdao=new BookDAO();
		
		%>
		if(bookid=="<%=bid%>")
		{
			<%int f=bdao.iscollect(uid,bid);%>
			if("<%=f%>"=="1"){
			alert("已收藏！");
			}
			else if("<%=f%>"=="0"){
			alert("收藏成功!");
			var f=document.createElement('form');
    		f.style.display='none';
    		f.action='search.jsp';
    	 	f.method='post';        	
    		f.innerHTML='<input type="hidden" name="bookid" value='+bookid+'><input type="hidden" name="user" value="<%=uid%>">';
    	 	document.body.appendChild(f);
        	f.submit();
			}
		}
		<%
		}
		%>
	}
	function preorder(bookid){
		<%
		for(int j=0;j<bookList.size();j++)
		{
			int bid=bookList.get(j).getBookID();
			Preorder pre=new Preorder();
		
		%>
		if(bookid=="<%=bid%>")
		{
			<%int f=pre.isorder(uid,bid);%>
			if("<%=f%>"=="0"){
				alert("预约人数已满！");
			}
			else if("<%=f%>"=="1"){
				alert("预约成功！");
				var f=document.createElement('form');
	    		f.style.display='none';
	    		f.action='search.jsp';
	    	 	f.method='post';        	
	    		f.innerHTML='<input type="hidden" name="prebookid" value='+bookid+'><input type="hidden" name="user" value="<%=uid%>">';
	    	 	document.body.appendChild(f);
	        	f.submit();
			}
			else if("<%=f%>"=="2"){
				alert("已预约！");
			}
		}
		<%
		}
		%>
	}
	</script>
	<%session.setAttribute("pathList", path); %>
	</div>
	</div>
</body>
</html>