package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ProductoController;

/**
 * Servlet implementation class ServletPeliculaAlquilar
 */
@WebServlet("/ServletProductoAlquilar")
public class ServletProductoAlquilar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProductoAlquilar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProductoController producto = new ProductoController();
		
		int id = Integer.parseInt(request.getParameter("id"));
		String username = request.getParameter("username");
		
		String productoStr = producto.alquilar(id,username);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(productoStr);
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}