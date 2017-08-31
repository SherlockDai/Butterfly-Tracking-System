import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nixyteam.UserBean;

/**
 * Servlet implementation class ReportTagging
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<font color=\"red\">This method is not supported for security reasons!</font><br><br>");
		out.println("<a href=\"index.jsp\">Home Page.jsp</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		PreparedStatement preparedStatement = null;
		try {
			ButterflyConnection butterflyConnection = new ButterflyConnection();
			UserBean user = new UserBean();
			user.setEmail(request.getParameter("email").trim());
			String password = request.getParameter("password").trim();
			user.setFirstName(request.getParameter("firstName").trim());
			user.setLastName(request.getParameter("lastName").trim());
			user.setAddress(request.getParameter("streetAddress").trim());
			user.setUserCity(request.getParameter("city").trim());
			user.setUserState(request.getParameter("state").trim());
			user.setZipCode(request.getParameter("zipCode").trim());
			user.setUserCountry(request.getParameter("country").trim());
			user.setPhone(request.getParameter("phone").trim());
			String sql = "INSERT INTO users (email,password,first_name,last_name,street_address,city,state,zip_code,country,phone)" +
					" VALUES (?,PASSWORD(?),?,?,?,?,?,?,?,?)";
			preparedStatement = butterflyConnection.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			preparedStatement.setString(5, user.getAddress());
			preparedStatement.setString(6, user.getUserCity());
			preparedStatement.setString(7, user.getUserState());
			preparedStatement.setString(8, user.getZipCode());
			preparedStatement.setString(9, user.getUserCountry());
			preparedStatement.setString(10, user.getPhone());
			preparedStatement.executeUpdate();
			out.println("<font color=\"red\">Registration Successfull, you can login now!</font><br><br>");
			request.getRequestDispatcher("login.jsp").include(request, response);	
		} catch (SQLIntegrityConstraintViolationException e) {
			out.println("<font color=\"red\">This email is already in use, please try again with a different email!</font><br><br>");
			request.getRequestDispatcher("register.jsp").include(request, response);
		} catch (SQLException e) {
			out.println("<font color=\"red\">" + e.getMessage()+ "</font><br><br>");
			out.println("<a href=\"index.jsp\">Home Page</a>");
			e.printStackTrace();
		}catch (Exception e) {
			out.println("<font color=\"red\">" + e.getMessage() + "!</font><br><br>");
			out.println("<a href=\"index.jsp\">Home Page</a>");
			e.printStackTrace();
		} finally {
		    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
		}
	}
}