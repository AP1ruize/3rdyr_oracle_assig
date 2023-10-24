<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="elements.LoanTable"%>
<%@ page import="elements.Book"%>
<%@ page import=" java.text.SimpleDateFormat"%>
<%@ page import="elements.Book"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>

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
		function cancel(duration){
			if(parseInt(duration)<60)
			{
				alert("续借成功！");
			}
			else
			{
				alert("续借失败，已续借一次！");
			}
		}
	</script>
  </head>

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
						LoanTable loan=new LoanTable();
            			if(request.getParameter("bookid")!=null){
           	 				String idstr=request.getParameter("bookid");
			 				int id = Integer.parseInt(idstr);
			 				String durstr=request.getParameter("duration");
			 				int dur = Integer.parseInt(durstr);
			 				if(dur<60)	loan.renew(id, uid);
            			}
						loan.getLoaninfo(uid);
						int num=loan.getTuples().size();
			%>
			<h2>
			 	当前借阅（<%=num%>）
			</h2><hr /> 
			</br></br>
			 <section>
				<table>
					<tr>
						<th>图书编号</th>
						<th>书名</th>
						<th>借阅日期</th>
						<th>归还截止日期</th>
						<th></th>
					</tr>
					<%
						for(int i=0;i<num;++i)
						{
							int bookid=loan.getTuples().get(i).getBid();
							Book b=new Book();
							b.getBookInfo(bookid);
							String bname=b.getName();
							
							Timestamp tstamp=loan.getTuples().get(i).getLoanTime();   //借阅时间戳
							String time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tstamp);   //借阅时间
							int dur=loan.getTuples().get(i).getDuration(); 
							String deadline="";
							Date dt=new Date(tstamp.getTime());
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(dt);
							calendar.add(Calendar.DATE,dur); 
							dt=calendar.getTime();              //截止时间
							Timestamp dstamp=new Timestamp(dt.getTime());  //截止日期时间戳
							Timestamp currentstamp = new Timestamp(System.currentTimeMillis());//当前时间戳
							Date cdt = new Date(currentstamp.getTime());
							
							if(cdt.getTime()<dt.getTime())  //未超时
							{
								deadline=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dstamp);							
							}
							else
							{
								deadline="已逾期";
							}
							
					%>
					<tr>
						<td><%=bookid%></td>
						<td><%=bname%></td>
						<td><%=time%></td>
						<td><%=deadline%></td>
						<td>
							<form action="loaninfo.jsp" method="post" >
			 		 				<input type="hidden" name="bookid" value=<%=bookid%>>
			 		 				<input type="hidden" name="duration" value=<%=dur%>>
			 		 				<input type="hidden" name="user" value=<%=uid%>>
			 		 				<input type="submit" id=<%=dur%> value="续借"  onclick="cancel(this.id)"/>
			 		 		</form>
						</td>
					</tr>
					<%
						}
					%>
				</table>
			 </section>
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
