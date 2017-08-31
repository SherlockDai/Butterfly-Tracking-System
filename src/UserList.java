import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nixyteam.UserBean;

/**
 * Servlet implementation class FetchSightings
 */
@WebServlet("/userlist")
public class UserList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		Statement statement = null;
		ResultSet resultSet = null;
		response.setContentType("text/html");
		if (session == null || !((UserBean)session.getAttribute("user")).isAdministrator()) {
			out.println("<font color=\"red\">Only administrators can see the user list!</font><br><br>");
			out.println("<a href=\"index.jsp\">Home Page</a>");
		}
		try {
			ButterflyConnection butterflyConnection = new ButterflyConnection();
			statement = butterflyConnection.getConnection().createStatement();
			String sql = "SELECT user_id,email,first_name,last_name,street_address,city," +
					"state,zip_code,country,phone,administrator,disabled FROM users";
			resultSet = statement.executeQuery(sql);
			ArrayList<UserBean> users = new ArrayList<UserBean>();
			UserBean user;
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("userlist.jsp");
			while(resultSet.next()) {
				user = new UserBean();
				user.setUserID(resultSet.getInt(1));
				user.setEmail(resultSet.getString(2));
				user.setFirstName(resultSet.getString(3));
				user.setLastName(resultSet.getString(4));
				user.setAddress(resultSet.getString(5));
				user.setUserCity(resultSet.getString(6));
				user.setUserState(resultSet.getString(7));
				user.setZipCode(resultSet.getString(8));
				user.setUserCountry(resultSet.getString(9));
				user.setPhone(resultSet.getString(10));
				user.setAdministrator((resultSet.getInt(11) == 1) ? true : false);
				user.setDisabled((resultSet.getInt(12) == 1) ? true : false);
				users.add(user);
			}
			session.setAttribute("users", users);
			requestDispatcher.include(request, response);
		} catch (SQLException e) {
			out.println("<font color=\"red\">" + e.getMessage()+ "</font><br><br>");
			out.println("<a href=\"index.jsp\">Home Page</a>");
			e.printStackTrace();
		}catch (Exception e) {
			out.println("<font color=\"red\">" + e.getMessage() + "!</font><br><br>");
			out.println("<a href=\"index.jsp\">Home Page</a>");
			e.printStackTrace();
		} finally {
		    try { if (resultSet != null) resultSet.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
