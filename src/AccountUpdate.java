import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nixyteam.UserBean;

/**
 * Servlet implementation class ReportTagging
 */
@WebServlet("/accountupdate")
public class AccountUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<font color=\"red\">Login properly to perform this action!</font><br><br>");
		out.println("<a href=\"index.jsp\">Home Page.jsp</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		Statement statement = null;
		if (session == null || session.getAttribute("user") == null){
			doGet(request, response);
			return;
		}
		try {
			ButterflyConnection butterflyConnection = new ButterflyConnection();
			UserBean user = (UserBean)session.getAttribute("user");
			// The code below was written before UserBean class was written, if it ain't broke don't fix it!
			String email = request.getParameter("email").trim();
			String password = request.getParameter("password").trim();
			String firstName = request.getParameter("firstName").trim();
			String lastName = request.getParameter("lastName").trim();
			String streetAddress = request.getParameter("streetAddress").trim();
			String city = request.getParameter("city").trim();
			String state = request.getParameter("state").trim();
			String zipCode = request.getParameter("zipCode").trim();
			String country = request.getParameter("country").trim();
			String phone = request.getParameter("phone").trim();
			if (email.isEmpty() && password.isEmpty() && firstName.isEmpty() && lastName.isEmpty() && streetAddress.isEmpty() && city.isEmpty()
					&& state.isEmpty() && zipCode.isEmpty() && country.isEmpty() && phone.isEmpty())
				request.getRequestDispatcher("account.jsp").forward(request, response);
			statement = butterflyConnection.getConnection().createStatement();
			String sql;
			String sqlCondition = " where user_id = " + ((UserBean)session.getAttribute("user")).getUserID();
			if (!email.isEmpty()) {
				sql = "update users set email = '"+ email + "'" +  sqlCondition;
				statement.addBatch(sql);
			}
			if (!password.isEmpty()) {
				sql = "update users set password = PASSWORD('"+ password + "')" + sqlCondition;
				statement.addBatch(sql);
			}
			if (!firstName.isEmpty()) {
				sql = "update users set first_name = '"+ firstName + "'" + sqlCondition;
				statement.addBatch(sql);
			}
			if (!lastName.isEmpty()) {
				sql = "update users set last_name = '"+ lastName + "'" +  sqlCondition;
				statement.addBatch(sql);
			}
			if (!streetAddress.isEmpty()) {
				sql = "update users set street_address = '"+ streetAddress + "'" +  sqlCondition;
				statement.addBatch(sql);
			}
			if (!city.isEmpty()) {
				sql = "update users set city = '"+ city + "'" +  sqlCondition;
				statement.addBatch(sql);
			}
			if (!state.isEmpty()) {
				sql = "update users set state = '"+ state + "'" +  sqlCondition;
				statement.addBatch(sql);
			}
			if (!zipCode.isEmpty()) {
				sql = "update users set zip_code = '"+ zipCode + "'" +  sqlCondition;
				statement.addBatch(sql);
			}
			if (!country.isEmpty()) {
				sql = "update users set country = '"+ country + "'" +  sqlCondition;
				statement.addBatch(sql);
			}
			if (!phone.isEmpty()) {
				sql = "update users set phone = '"+ phone + "'" +  sqlCondition;
				statement.addBatch(sql);
			}
			statement.executeBatch();
			// Refresh User Session Information
			if (!email.isEmpty()) user.setEmail(email);
			if (!firstName.isEmpty()) user.setFirstName(firstName);
			if (!lastName.isEmpty()) user.setLastName(lastName);
			if (!streetAddress.isEmpty()) user.setAddress(streetAddress);
			if (!city.isEmpty()) user.setUserCity(city);
			if (!state.isEmpty()) user.setUserState(state);
			if (!zipCode.isEmpty()) user.setZipCode(zipCode);
			if (!country.isEmpty()) user.setUserCountry(country);
			if (!phone.isEmpty()) user.setPhone(phone);
			out.println("<font color=\"red\">Update Successful!</font><br><br>");
			request.getRequestDispatcher("account.jsp").include(request, response);	
		} catch (SQLIntegrityConstraintViolationException e) {
			out.println("<font color=\"red\">This email is already in use!</font><br><br>");
			request.getRequestDispatcher("account.jsp").include(request, response);
		} catch (SQLException e) {
			out.println("<font color=\"red\">" + e.getMessage()+ "</font><br><br>");
			out.println("<a href=\"index.jsp\">Home Page</a>");
			e.printStackTrace();
		}catch (Exception e) {
			out.println("<font color=\"red\">" + e.getMessage() + "!</font><br><br>");
			out.println("<a href=\"index.jsp\">Home Page</a>");
			e.printStackTrace();
		} finally {
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		}
	}
}