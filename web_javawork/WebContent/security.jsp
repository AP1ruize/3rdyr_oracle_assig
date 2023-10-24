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

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-style.css">
    <link rel="stylesheet" href="assets/css/owl.css">
	<link rel="stylesheet" type="text/css" href="style.css">
	<% int uid=Integer.parseInt(request.getParameter("user")); %>
<script language=javascript>  
    //判断输入密码的类型  
    function CharMode(iN){  
        if (iN>=48 && iN <=57) //数字  
            return 1;  
        if (iN>=65 && iN <=90) //大写  
            return 2;  
        if (iN>=97 && iN <=122) //小写  
            return 4;  
        else  
            return 8;   
    }
    //bitTotal函数  
    //计算密码模式  
    function bitTotal(num){  
        modes=0;  
        for (i=0;i<4;i++){  
            if (num & 1) modes++;  
            num>>>=1;  
        }
        return modes;  
    }
    //返回强度级别  
    function checkStrong(sPW){  
        if (sPW.length<6)  
            return 0; //密码太短，不检测级别
        Modes=0;  
        for (i=0;i<sPW.length;i++){  
            //密码模式  
            Modes|=CharMode(sPW.charCodeAt(i));  
        }
        return bitTotal(Modes);  
    }
  
    //显示颜色  
    function pwStrength(pwd){  
        Dfault_color="#eeeeee";        //默认颜色
        L_color=" #FFFF33";        //低强度的颜色，且只显示在最左边的单元格中
        M_color="#99CC33";        //中等强度的颜色，且只显示在左边两个单元格中
        H_color=" #33CC33";        //高强度的颜色，三个单元格都显示
        if (pwd==null||pwd==''){  
            Lcolor=Mcolor=Hcolor=Dfault_color;
        }  
        else{  
            S_level=checkStrong(pwd);  
            switch(S_level) {  
            case 0:  
                Lcolor=Mcolor=Hcolor=Dfault_color;
                break;
            case 1:  
                Lcolor=L_color;
                Mcolor=Hcolor=Dfault_color;
                break;  
            case 2:  
                Lcolor=Mcolor=M_color;  
                Hcolor=Dfault_color;  
                break;  
            default:  
                Lcolor=Mcolor=Hcolor=H_color;  
        }  
    }  
    document.getElementById("strength_L").style.background=Lcolor;  
    document.getElementById("strength_M").style.background=Mcolor;  
    document.getElementById("strength_H").style.background=Hcolor;  
    return;
	}
    function validate() {

        var pwd1 = document.getElementById("pwd").value;

        var pwd2 = document.getElementById("pwd1").value;

     	
       
    <!-- 对比两次输入的密码 -->
        if(pwd1==pwd2)
         {
        	if(pwd1==""){
        		document.getElementById("tishi").innerHTML="";
        	}
        	else{
            document.getElementById("tishi").innerHTML="<font color='green' size='5px'>√</font>";
        	}
            document.getElementById("button").disabled = false;
         }

   		 else {

            document.getElementById("tishi").innerHTML="<font color='red' size='2px'>两次密码不相同</font>";

            document.getElementById("button").disabled = true;

         }
        
    }
    function setting(){
    	var pwd2 = document.getElementById("pwd2").value;
    	var pwd = document.getElementById("pwd").value;
        var pwd1 = document.getElementById("pwd1").value;
        if(pwd2==""|| pwd==""||pwd1=="")
       	{
        	alert("输入密码不可为空！");
       	}
        else if(pwd!=pwd1){
        	alert("确认密码与输入密码不一致！");
        }
        else if(pwd2==pwd){
        	alert("新密码与原密码不能相同！");
        }
        <% 
        Reader reader=new Reader();
        String p=reader.getpass(uid);
        %>
        else if(pwd2!="<%=p%>"){
        	alert("原密码输入错误！");
        }
        else{
        	alert("密码修改成功！");
    	 	var f=document.createElement('form');
    		 f.style.display='none';
    		 f.action='upsuccess.jsp';
    	 	f.method='post';        	
    		 f.innerHTML='<input type="hidden" name="password" value='+pwd+'><input type="hidden" name="user" value="<%=uid%>">';
    	 	document.body.appendChild(f);
        	f.submit();
        }
   
    }
    
    
</script>
</head>
<style>
	#demo{
		background: #6666CC;
		color: #FFFFFF;
		width:200px;
	}
	#demo2{
	text-align:center;
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
                <a  class="cfont" href="index.jsp?user=<%=uid%>">返回首页</a>
              </div>
            </header>
			 <section >
			 <h2>
			 	安全设置
			 </h2>
			 </section>
			 </br></br>
			 <section>
			 	 &nbsp;&nbsp;&nbsp;现用密码：&nbsp;<input type="password" id="pwd2" name="pass1" placeholder="请输入现用密码"/> </br></br></br>
			 		输入新密码：&nbsp;<input type=password id="pwd" name="pass2" size=20  onkeyup="pwStrength(this.value)"  onBlur="pwStrength(this.value)" placeholder="字母，数字，6-20个字符"/>
			 		</br></br>
			 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 		<table width="250" border="0" bordercolor="#eeeeee" style='display:inline'>
                  		<tr align="center">
            				<td width="33%" height="18px" id="strength_L" bgcolor="#eeeeee">弱</td>  
            				<td width="33%" height="18px" id="strength_M" bgcolor="#eeeeee">中</td>  
            				<td width="34%" height="18px" id="strength_H" bgcolor="#eeeeee">强</td>    
        		        </tr>
    			   </table>
    			    </br></br></br>
					确认新密码：&nbsp;<input type="password" id="pwd1" name="pass3" placeholder="再次输入新密码" onkeyup="validate()"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span id="tishi"></span>
			 </section>
			 </br>
			 <div id="demo2">
			 <input id="demo" type="submit" value="确认修改"  onclick="setting()"/>
			 </div>
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
