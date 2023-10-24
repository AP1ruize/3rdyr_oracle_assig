<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="DAO.Preorder"%>
<%@ page import=" java.text.SimpleDateFormat"%>
<%@ page import="elements.Book"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="oracle.sql.BLOB"%>
<%@ page import="java.io.*,java.io.IOException"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700" rel="stylesheet">

    <title>Ramayana - Simple HTML Page</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!--
Ramayana CSS Template
https://templatemo.com/tm-529-ramayana
-->

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-style.css">
    <link rel="stylesheet" href="assets/css/owl.css">
	<link rel="stylesheet" type="text/css" href="style.css">
<script language=javascript>  
function cancel(){
	alert("取消成功！");
}
    
</script>
</head>
<style>
	
</style>
<body class="is-preload">

    <!-- Wrapper -->
    <div id="wrapper">

      <!-- Main -->
        <div id="main">
          <div class="inner">
            <!-- Header -->
             <header id="header">
              <div class="logo">
                <a  class="cfont" href="index.jsp?user=<%=request.getParameter("user")%>">返回首页</a>
              </div>
            </header>
            <%
            int uid=Integer.parseInt(request.getParameter("user"));
             Preorder p=new Preorder();
            if(request.getParameter("bookid")!=null){
           	 	String idstr=request.getParameter("bookid");
			 	int id = Integer.parseInt(idstr);
			 	p.cancelorder(uid, id);
            }
            %>
			 <h2>
			 	预约信息
			 </h2>
			  <hr /> 
			 </br></br>
			 <section>
			 	<table>
			 		<tr>
			 			<th></th>
			 			<th>书名</th>
			 			<th>作者</th>
			 			<th>预约时间</th>
			 			<th></th>
			 		</tr>
			 <%
			 	p.getOrderinfo(uid);
			 	for(int i=0;i<p.getOrders().size();++i){
			 		String time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(p.getOrders().get(i).getTime());
			 		int id=p.getOrders().get(i).getBookid();
			 		Book book=new Book();
			 		book.getBookInfo(id);
			 		String path="imgs/"+id+".jpg";
			 		String filepath=this.getServletContext().getRealPath(path);
					try{
					InputStream inputStream;
					inputStream = book.getPic().getBinaryStream();
					File fileOutput = new File(filepath);
					FileOutputStream fo = new FileOutputStream(fileOutput);
					int c;
					while ((c = inputStream.read()) != -1) fo.write(c);
					fo.close();
					}catch(Exception e)
					{
						e.printStackTrace();
					}
			 %>
			 	 	<tr>
			 	 		<td style="width:150px"><img src=<%=path%> height="100" width="75"></td>
			 	 		<td><%=book.getName() %></td>
			 	 		<td><%=book.getAuthor() %></td>
			 	 		<td><%=time%></td> 
			 	 		<td >
			 		 		<form action="orderinfo.jsp" method="post" >
			 		 				<input type="submit" value="取消预约"  onclick="cancel()"/>
			 		 				<input type="hidden" name="bookid" value=<%=id%>>
			 		 				<input type="hidden" name="user" value=<%=uid%>>
			 		 		</form>
			 		 	</td>
			 	 	</tr>
			 <%
			 	}
			 %>
			 	 </table>
			 </section>
			 </br>
			 
			</div>
		</div>

      <!-- Sidebar -->
        <div id="sidebar">

          <div class="inner">

            <!-- Search Box -->
            <section id="search" class="alt">
              <form method="get" action="#">
                <input type="text" name="search" id="search" placeholder="Search..." />
              </form>
            </section>
              
            <!-- Menu -->
             <nav id="menu">
              <ul class="cfont2">
                <li><a href="homepage.jsp?user=<%=uid%>">Homepage</a></li>
                <li><a href="info.jsp?user=<%=uid%>">个人信息</a></li>
                <li><a href="orderinfo.jsp?user=<%=uid%>">个人预约</a></li>
                <li><a href="favorite.jsp?user=<%=uid%>">收藏夹</a></li>
                <li><a href="security.jsp?user=<%=uid%>">安全设置</a></li>
                <li><a href="loaninfo.jsp?user=<%=uid%>">个人借阅</a></li>
               
              </ul>
            </nav>

            <!-- Featured Posts -->
            <div class="featured-posts">
              <div class="heading">
                <h2>Featured Posts</h2>
              </div>
              <div class="owl-carousel owl-theme">
                <a href="#">
                  <div class="featured-item">
                    <img src="assets/images/featured_post_01.jpg" alt="featured one">
                    <p>Aliquam egestas convallis eros sed gravida. Curabitur consequat sit.</p>
                  </div>
                </a>
                <a href="#">
                  <div class="featured-item">
                    <img src="assets/images/featured_post_01.jpg" alt="featured two">
                    <p>Donec a scelerisque massa. Aliquam non iaculis quam. Duis arcu turpis.</p>
                  </div>
                </a>
                <a href="#">
                  <div class="featured-item">
                    <img src="assets/images/featured_post_01.jpg" alt="featured three">
                    <p>Suspendisse ac convallis urna, vitae luctus ante. Donec sit amet.</p>
                  </div>
                </a>
              </div>
            </div>

            <!-- Footer -->
             <footer id="footer">
              <p class="copyright"></p>
            </footer>
            
          </div>
        </div>

    </div>
    <!-- Scripts -->
  <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <script src="assets/js/browser.min.js"></script>
    <script src="assets/js/breakpoints.min.js"></script>
    <script src="assets/js/transition.js"></script>
    <script src="assets/js/owl-carousel.js"></script>
    <script src="assets/js/custom.js"></script>

</body>

</html>
