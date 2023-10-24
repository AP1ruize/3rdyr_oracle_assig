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
 * Servlet implementation class BookSearchServlet
 */

@WebServlet("/BookSearchServlet")
public class BookSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		BookDAO bookDAO=new BookDAO();
		//��ȡ����ѯ�Ĺؼ���
		String input=(String)request.getParameter("q");
		ArrayList<BookInfo> bookList=bookDAO.getBookAll(input);
		if(bookList.size()==0)
		{
			request.setAttribute("msg","��������0�����������������ƴд�Ƿ���ȷ���������ټ���������");
		}
		
			request.setAttribute("bookList", bookList);
			int uid=Integer.parseInt(request.getParameter("user"));
			request.setAttribute("user",uid);
			//�鵽��������ת����index.jsp�н���չʾ
			request.getRequestDispatcher("/search.jsp").forward(request, response);
		
	}

}
