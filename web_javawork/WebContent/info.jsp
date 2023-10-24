<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="elements.Reader"%>

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
  </head>
<style>
	#demo{
	background:  #F7F7F7;
	width:600px;
	height:300px;
	font-size:15px;
	}
	#demo1{
	width:600px;
	height:50px;
	display: table-cell;
    vertical-align:middle;
	}
	#demo2{
	width:600px;
	height:50px;
	display: table-cell;
    vertical-align:middle
	}
	#demo3{
	width:600px;
	height:50px;
	display: table-cell;
    vertical-align:middle
	}
	#demo4{
	width:600px;
	height:50px;
	display: table-cell;
    vertical-align:middle
	}
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
            <h2>
			 	个人信息
			</h2>
			<%
			int uid=Integer.parseInt(request.getParameter("user"));
			Reader reader=new Reader();
		    reader =reader.getUserInfo(uid);
			%>
			</br></br></br></br>
			<section>
			<div id="demo">
			<div id="demo1">
				账号
				&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
				<%=reader.getId() %> 
			 </div><hr />
			 <div id="demo3">
			 	
				姓名
				&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
				<%=reader.getNickname() %>
			 	
			 </div><hr />
			 <div id="demo2">
			 	
				 用户等级
				 &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
				<%=reader.getExp()%>
			 	
			</div><hr />
			 <div id="demo4">
			 	
				 当前余额
				  &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
				<%=reader.getCredit() %>¥
			 	
			 </div>
			 </div>
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


  </body>

</html>
