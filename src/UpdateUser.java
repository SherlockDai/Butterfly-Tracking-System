import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
@WebServlet("/updateuser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = -1838207374663783715L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<font color=\"red\">Only administrators can access this page!</font><br><br>");
		out.println("<a href=\"index.jsp\">Home Page.jsp</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		ResultSet resultSet = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		if (session == null || session.getAttribute("user") == null) {
			doGet(request, response);
			return;
		}
		UserBean admin = (UserBean)session.getAttribute("user");
		if (!admin.isAdministrator()) {
			out.println("<font color=\"red\">Only administrators can perform this action!</font><br><br>");
			out.println("<a href=\"index.jsp\">Dashboard</a>");
		}
		else {
			try {
				ButterflyConnection butterflyConnection = new ButterflyConnection();
				response.setContentType("text/html");
				out = response.getWriter();
				int userID = Integer.parseInt(request.getParameter("number"));
				if (userID == 1) {
					out.println("<font color=\"red\">Only root user can update this account!</font><br>");
					request.getRequestDispatcher("usermanagement.jsp").include(request, response);
					return;
				}
				String sql = "select email,first_name,last_name,street_address,"+
						"city,state,zip_code,country,phone,administrator,disabled" +
						" from users where user_id=" + userID;
				statement = butterflyConnection.getConnection().createStatement();
				resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					String email = (request.getParameter("email") != null)? request.getParameter("email").trim() : null;
					String password = (request.getParameter("password") != null)? request.getParameter("password").trim() : null;
					String firstName = (request.getParameter("firstName") != null)? request.getParameter("firstName").trim() : null;
					String lastName = (request.getParameter("lastName") != null)? request.getParameter("lastName").trim() : null;
					String address = (request.getParameter("address") != null)? request.getParameter("address").trim() : null;
					String city = (request.getParameter("city") != null)? request.getParameter("city").trim() : null;
					String state = (request.getParameter("state") != null)? request.getParameter("state").trim() : null;
					String zipCode = (request.getParameter("zipCode") != null)? request.getParameter("zipCode").trim() : null;
					String country = (request.getParameter("country") != null)? request.getParameter("country").trim() : null;
					String phone = (request.getParameter("phone") != null)? request.getParameter("phone").trim() : null;
					String administrator = (request.getParameter("administrator") != null)? request.getParameter("administrator") : null;
					String disabled = (request.getParameter("disabled") != null)? request.getParameter("disabled") : null;
					UserBean user = new UserBean();
					user.setUserID(userID);
					user.setEmail(resultSet.getString(1));
					user.setFirstName(resultSet.getString(2));
					user.setLastName(resultSet.getString(3));
					user.setAddress(resultSet.getString(4));
					user.setUserCity(resultSet.getString(5));
					user.setUserState(resultSet.getString(6));
					user.setZipCode(resultSet.getString(7));
					user.setUserCountry(resultSet.getString(8));
					user.setPhone(resultSet.getString(9));
					user.setAdministrator((resultSet.getInt(10) == 1)  ? true : false);
					user.setDisabled((resultSet.getInt(11) == 1)  ? true : false);
					if (email != null  && email.length() > 0)
						user.setEmail(email);
					if (firstName != null  && firstName.length() > 0)
						user.setFirstName(firstName);
					if (lastName != null  && lastName.length() > 0)
						user.setLastName(lastName);
					if (address != null  && address.length() > 0)
						user.setAddress(address);
					if (city != null  && city.length() > 0)
						user.setUserCity(city);
					if (state != null  && state.length() > 0)
						user.setUserState(state);
					if (zipCode != null  && zipCode.length() > 0)
						user.setZipCode(zipCode);
					if (country != null  && country.length() > 0)
						user.setUserCountry(country);
					if (phone != null  && phone.length() > 0)
						user.setPhone(phone);
					if (administrator != null) {
						if (administrator.equals("true"))
							user.setAdministrator(true);
						else if (administrator.equals("false"))
							user.setAdministrator(false);
					}
					if (disabled != null) {
						if (disabled.equals("true"))
							user.setDisabled(true);
						else if (disabled.equals("false"))
							user.setDisabled(false);
					}
					sql = "UPDATE users SET email=?,first_name=?,last_name=?,street_address=?,city=?" +
						",state=?,zip_code=?,country=?,phone=?,administrator=?,disabled=? WHERE user_id=?";
					preparedStatement = butterflyConnection.getConnection().prepareStatement(sql);
					preparedStatement.setString(1, user.getEmail());
					preparedStatement.setString(2, user.getFirstName());
					preparedStatement.setString(3, user.getLastName());
					preparedStatement.setString(4, user.getAddress());
					preparedStatement.setString(5, user.getUserCity());
					preparedStatement.setString(6, user.getUserState());
					preparedStatement.setString(7, user.getZipCode());
					preparedStatement.setString(8, user.getUserCountry());
					preparedStatement.setString(9, user.getPhone());
					preparedStatement.setInt(10, (user.isAdministrator()) ? 1 : 0);
					preparedStatement.setInt(11, (user.isDisabled()) ? 1 : 0);
					preparedStatement.setInt(12, user.getUserID());
					preparedStatement.executeUpdate();
					out.println("<font color=\"red\">Update Successfull!</font><br>");
					if (password != null  && password.length() > 0) {
						sql = "UPDATE users SET password=PASSWORD('" + password + "') WHERE user_id=" + userID;
						statement.executeUpdate(sql);
					}
				}
				else {
					out.println("<font color=\"red\">The user ID \"" + userID + "\" does not exist</font><br>");
				}
				request.getRequestDispatcher("usermanagement.jsp").include(request, response);
			} catch (SQLIntegrityConstraintViolationException e) {
				out.println("<font color=\"red\">The email address specified is already in use!</font><br><br>");
				request.getRequestDispatcher("usermanagement.jsp").include(request, response);
			} catch (SQLException e) {
				out.println("<font color=\"red\">" + e.getMessage()+ "</font><br><br>");
				out.println("<a href=\"dashboard.jsp\">Dashboard</a>");
			}catch (Exception e) {
				out.println("<font color=\"red\">" + e.getMessage() + "!</font><br><br>");
				out.println("<a href=\"index.jsp\">Home Page</a>");
				e.printStackTrace();
			} finally {
			    try { if (resultSet != null) resultSet.close(); } catch (Exception e) {};
			    try { if (statement != null) statement.close(); } catch (Exception e) {};
			    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			}
		}
	}
}
