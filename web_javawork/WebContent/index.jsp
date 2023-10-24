<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="DAO.*"%>
<%@ page import="entity.*"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="oracle.sql.BLOB"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.*,java.io.IOException"%>

<%
//新书速览封面图加载
BookDAO bookDAO=new BookDAO();
ArrayList<String> path=new ArrayList<String>();
ArrayList<BookInfo> bookList=bookDAO.getBookNew();	
session.setAttribute("bookListNew", bookList);
if(bookList!=null)
{
for(int i=0;i<bookList.size();i++)
{
	BookInfo book=bookList.get(i);
	path.add("covers/"+book.getBookID()+".jpg");   //imgs为WebContent下的一个文件夹；将某本书的图片写入该路径下
	String filepath=this.getServletContext().getRealPath(path.get(i));
	//System.out.println(filepath);
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
}
}
session.setAttribute("pathListNew", path);
%>
<%
//公告展示
NoticeDAO noticeDAO=new NoticeDAO();
ArrayList<Notice> noticeList=noticeDAO.getNotice();
session.setAttribute("noticeList", noticeList);
%>

<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="https://fonts.googleapis.com/css?family=Raleway:100,200,300,400,500,600,700,800,900&display=swap" rel="stylesheet">

    <title>圖書館</title>


    <!-- Additional CSS Files -->
    <link rel="stylesheet" type="text/css" href="assets2/css/bootstrap.min.css">

    <link rel="stylesheet" type="text/css" href="assets2/css/font-awesome.css">

    <link rel="stylesheet" href="assets2/css/templatemo-breezed.css">

    <link rel="stylesheet" href="assets2/css/owl-carousel.css">

    <link rel="stylesheet" href="assets2/css/lightbox.css">

    </head>
    <%	int uid;
    	if(request.getAttribute("user")!=null){
    	UserInfo u=(UserInfo)request.getAttribute("user");
    	uid=u.getUserID();
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
    <body>
    
    <!-- ***** Preloader Start ***** -->
    <div id="preloader">
        <div class="jumper">
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>  
    <!-- ***** Preloader End ***** -->
    
    
    <!-- ***** Header Area Start ***** -->
    <header class="header-area header-sticky">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <nav class="main-nav">
                        <!-- ***** Logo Start ***** -->
                        <a href="index.html" class="logo">
                           	 圖書館
                        </a>
                        <!-- ***** Logo End ***** -->
                        <!-- ***** Menu Start ***** -->
                        <ul class="nav">
                            <li class="scroll-to-section"><a href="#top" class="active">首页</a></li>
                            <li class="scroll-to-section"><a href="#about">新闻动态</a></li>
                           	<li class="scroll-to-section"><a href="#testimonials">新书速览</a></li>
                           <!-- <li class="scroll-to-section"><a href="#contact-us">联系我们</a></li> -->
                            <li class="submenu">
                                <a href="javascript:;">个人中心</a>
                                <ul>
                                    <li><a href="homepage.jsp?user=<%=uid%>" >个人主页</a></li>
                                    <li><a href="info.jsp?user=<%=uid%>" >个人信息</a></li>
                                    <li><a href="orderinfo.jsp?user=<%=uid%>">个人预约</a></li>
                                    <li><a href="favorite.jsp?user=<%=uid%>">收藏夹</a></li>
                                    <li><a href="security.jsp?user=<%=uid%>">安全设置</a></li>
                                    <li><a href="loaninfo.jsp?user=<%=uid%>">个人借阅</a></li>
                                </ul>
                            </li>
                             
                          <div class="search-icon">
                                <a href="#search"><i class="fa fa-search"></i></a>
                          </div>   
                        </ul> 
                        <a class='menu-trigger'>
                            <span>Menu</span>
                        </a>
                        <!-- ***** Menu End ***** -->
                    </nav>
                </div>
            </div>
        </div>
    </header>
    <!-- ***** Header Area End ***** -->
    
    <!-- ***** Search Area ***** -->
    <div id="search">
        <button type="button" class="close">×</button>
        <form id="contact" action="BookSearchServlet" method="get" target="_blank">
            <fieldset>
                <input type="search" name="q" placeholder="请输入书名或作者" aria-label="Search through site content">
                <input type="hidden" name="user" value=<%=uid%>>
            </fieldset>
            <fieldset>
                <button type="submit" class="main-button">Search</button>
            </fieldset>
        </form>
    </div>

    <!-- ***** Main Banner Area Start ***** -->
    <div class="main-banner header-text" id="top">
        <div class="Modern-Slider">
          <!-- Item -->
          <div class="item">
            <div class="img-fill">
                <img src="imgs/homebg1.JPG" alt="">
                <div class="text-content">
                 <!--
                  <h3>Welcome To Breezed</h3>
                  <h5>New Bootstrap Template</h5>
                  <a href="#" class="main-stroked-button">Learn More</a>
                  <a href="#" class="main-filled-button">Get It Now</a>
                  -->
                </div>
            </div>
          </div>
          <!-- // Item -->
          <!-- Item -->
          <div class="item">
            <div class="img-fill">
                <img src="imgs/homebg2.JPG" alt="">
                <div class="text-content">
                <!--
                  <h3>Integrated Marketing Media</h3>
                  <h5>Best Digital Marketing</h5>
                  <a href="#" class="main-stroked-button">Read More</a>
                  <a href="#" class="main-filled-button">Take Action</a>
                 -->
                </div>
            </div>
          </div>
          <!-- // Item -->
          <!-- Item -->
          <div class="item">
            <div class="img-fill">
                <img src="imgs/homebg3.JPG" alt="">
                <div class="text-content">
                <!--
                  <h3>High Performance</h3>
                  <h5>Robust and Speedy</h5>
                  <a href="#" class="main-stroked-button">Learn More</a>
                  <a href="#" class="main-filled-button">Get It Now</a>
                 -->
                </div>
            </div>
          </div>
          <!-- // Item -->
        </div>
    </div>
    <div class="scroll-down scroll-to-section"><a href="#about"><i class="fa fa-arrow-down"></i></a></div>
    <!-- ***** Main Banner Area End ***** -->

    <!-- ***** About Area Starts ***** -->
    <section class="section" id="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-xs-12">
                    <div class="left-text-content">
                        <div class="section-heading">
                            <h3>最新消息</h3>
                           	<hr>
                        </div>
                        <div class="row">
                            <div class="col-md-6 col-sm-6">
                                <div class="service-item">
                                    <img src="assets2/images/service-item-01.png" alt="">
                                    <h4>公告新闻</h4>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6">
                                <div class="service-item">
                                    <img src="assets2/images/service-item-01.png" alt="">
                                    <h4>即时更新</h4>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6">
                                <div class="service-item">
                                    <img src="assets2/images/contact-info-03.png" alt="">
                                    <h4>Bulletin</h4>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6">
                                <div class="service-item">
                                    <img src="assets2/images/contact-info-03.png" alt="">
                                    <h4>Up-to-date</h4>
                                </div>
                            </div>
                            
                        </div>                
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-xs-12">
                     <div class="right-text-content">
                        <p>
                        <%
                        for(int k=0;k<noticeList.size();k++)
                        {
                        %>
                        <%if(noticeList!=null)out.print(noticeList.get(k).getEditDate()); %>
                        &emsp;
                        <i class="fa fa-play"></i> 
                        &emsp;
                        <a rel="nofollow noopener" href="notice.jsp?k=<%out.print(k);%>" target="_blank">
                        <%if(noticeList!=null)out.print(noticeList.get(k).getTitle()); %></a>  
                        <br>
                         <%} %>
                         
                        </p>
                    	</div> 
                </div>  
            </div>
        </div>
    </section>
    <!-- ***** About Area Ends ***** -->

   

    


    <!-- ***** Projects Area Starts ***** -->
    <section class="section" id="projects">
      <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <div class="section-heading">
                    <h6>文学图书奖系列</h6>
                    <br>
                    <h3>如梦似真<br>&nbsp;读者<br>&nbsp;&nbsp;您在哪里<br>&emsp;&emsp;——木心</h3>
                </div>
                <div class="filters">
                    <ul>
                        <li class="active" data-filter="*">全部</li>
                        <li data-filter=".des">艾略特奖/The T.S. Eliot Prize</li>
                        <li data-filter=".dev">布克奖/The Man Booker Prize</li>
                        <li data-filter=".gra">龚古尔奖/Le prix Goncourt</li>
                        <li data-filter=".tsh">普利策小说奖/ The Pulitzer Prize for Fiction</li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-9">
                <div class="filters-content">
                    <div class="row grid">
                        <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 all des">
                          <div class="item">
                            <a href="imgs/艾略特奖1.jpg" data-lightbox="image-1" data-title="Our Projects"><img src="imgs/艾略特奖1.jpg" alt="" width=280px; height=340px;></a>
                          </div>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 all dev">
                          <div class="item">
                            <a href="imgs/布克奖1.jpg" data-lightbox="image-1" data-title="Our Projects"><img src="imgs/布克奖1.jpg" alt="" width=280px; height=340px;></a>
                          </div>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 all gra">
                          <div class="item">
                            <a href="imgs/龚古尔奖1.jpg" data-lightbox="image-1" data-title="Our Projects"><img src="imgs/龚古尔奖1.jpg" alt="" width=280px; height=340px;></a>
                          </div>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 all tsh">
                          <div class="item">
                            <a href="imgs/普利策1.jpg" data-lightbox="image-1" data-title="Our Projects"><img src="imgs/普利策1.jpg" alt="" width=280px; height=340px;></a>
                          </div>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 all dev">
                          <div class="item">
                            <a href="imgs/布克奖2.jpg" data-lightbox="image-1" data-title="Our Projects"><img src="imgs/布克奖2.jpg" alt="" width=280px; height=340px;></a>
                          </div>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 all des">
                          <div class="item">
                            <a href="imgs/艾略特奖2.jpg" data-lightbox="image-1" data-title="Our Projects"><img src="imgs/艾略特奖2.jpg" alt="" width=280px; height=340px;></a>
                          </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
      </div>
    </section>
    <!-- ***** Projects Area Ends ***** -->

    <!-- ***** Testimonials Starts ***** -->
    <section class="section" id="testimonials">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="section-heading">
                        <h2>新书速览</h2>
                    </div>
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 mobile-bottom-fix-big" data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                    <div class="owl-carousel owl-theme">
                    <%for(int j=0;j<bookList.size();j++){ %>
                        <div class="item author-item">
                            <div class="member-thumb">
                                <img src=<%=path.get(j)%> alt="" height="450" width="370">
                                <div class="hover-effect">
                                    <div class="hover-content">
                                        <ul>
                                            <li><a href="#" name="col" id=<%=bookList.get(j).getBookID()%> onclick="collect(this.id)"><i class="fa fa-bookmark-o"></i></a></li>
                                            <li><a href="#" name="pre" id=<%=bookList.get(j).getBookID()%> onclick="preorder(this.id)"><i class="fa fa-calendar"></i></a></li>
                                            <li><a href="details.jsp?j=<%out.print(j);%>" target="_blank"><i class="fa fa-th-list"></i></a></li>
                                           <!-- <li><a href="#"><i class="fa fa-rss"></i></a></li>  -->
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <h4>.0<%out.print(j+1);%> <%out.print(bookList.get(j).getBookName());%></h4>
                            <span><%out.print(bookList.get(j).getAuthor());%></span>
                        </div>     
                     <%} %>
                    </div>
                </div>
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
                	    		f.action='index.jsp#col';
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
                    	    		f.action='index.jsp#pre';
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
            </div>
        </div>
    </section>
    <!-- ***** Testimonials Ends ***** -->
    
    

    <!-- jQuery -->
    <script src="assets2/js/jquery-2.1.0.min.js"></script>

    <!-- Bootstrap -->
    <script src="assets2/js/popper.js"></script>
    <script src="assets2/js/bootstrap.min.js"></script>

    <!-- Plugins -->
    <script src="assets2/js/owl-carousel.js"></script>
    <script src="assets2/js/scrollreveal.min.js"></script>
    <script src="assets2/js/waypoints.min.js"></script>
    <script src="assets2/js/jquery.counterup.min.js"></script>
    <script src="assets2/js/imgfix.min.js"></script> 
    <script src="assets2/js/slick.js"></script> 
    <script src="assets2/js/lightbox.js"></script> 
    <script src="assets2/js/isotope.js"></script> 
    
    <!-- Global Init -->
    <script src="assets2/js/custom.js"></script>

    <script>

        $(function() {
            var selectedClass = "";
            $("p").click(function(){
            selectedClass = $(this).attr("data-rel");
            $("#portfolio").fadeTo(50, 0.1);
                $("#portfolio div").not("."+selectedClass).fadeOut();
            setTimeout(function() {
              $("."+selectedClass).fadeIn();
              $("#portfolio").fadeTo(50, 1);
            }, 500);
                
            });
        });  
    </script>

  </body>
</html>