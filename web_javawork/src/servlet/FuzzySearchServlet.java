package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.BookDAO;
import entity.BookInfo;

/**
 * Servlet implementation class FuzzySearchServlet
 */
@WebServlet("/FuzzySearchServlet")
public class FuzzySearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FuzzySearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String bookname=request.getParameter("bookname");
		String author=request.getParameter("author");
		String type=request.getParameter("type");
		BookDAO bookDAO=new BookDAO();
		ArrayList<BookInfo> bookList=bookDAO.getBookFuzzy(bookname, author, type);
		if(bookList==null||bookList.size()==0)
		{
			request.setAttribute("msg","检索返回0条结果，建议检查您的拼写是否正确");
		}
		
			request.setAttribute("bookList", bookList);
			int uid=Integer.parseInt(request.getParameter("user"));
			request.setAttribute("user",uid);
			//查到所有数据转发到index.jsp中进行展示
			request.getRequestDispatcher("/search.jsp").forward(request, response);
	}

}
