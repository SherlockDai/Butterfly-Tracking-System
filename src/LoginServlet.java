import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nixyteam.LocationBean;
import nixyteam.UserBean;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<font color=\"red\">Please go back to the homepage and login properly!</font><br><br>");
		out.println("<a href=\"index.jsp\">Home Page.jsp</a>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			ButterflyConnection butterflyConnection = new ButterflyConnection();
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String sql = "select user_id,email,first_name,last_name,street_address,"+
					"city,state,zip_code,country,phone,administrator,disabled" +
					" from users where email=? and password=PASSWORD(?)";
			preparedStatement = butterflyConnection.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if (resultSet.getInt(12) == 1) {
					out.println("<font color=\"red\">This user has been disabled by an administrator!</font><br><br>");
					request.getRequestDispatcher("index.jsp").include(request, response);
					return;
				}
				HttpSession session = request.getSession(true);
				UserBean user = new UserBean();
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
				user.setAdministrator((resultSet.getInt(11) == 1)  ? true : false);
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(600);
				sql = "SELECT tag_date,tag_location,tag_state,tag_country,tag_latitude,tag_longitude FROM tagged_butterflies";
				statement = butterflyConnection.getConnection().createStatement();
				resultSet.close();
				resultSet = statement.executeQuery(sql);
				ArrayList<LocationBean> taggingLocations = new ArrayList<LocationBean>();
				LocationBean location;
				while (resultSet.next()) {
					location = new LocationBean();
					location.setDate(resultSet.getString(1));	
					location.setLocation(resultSet.getString(2));
					location.setState(resultSet.getString(3));
					location.setCountry(resultSet.getString(4));
					location.setLatitude(resultSet.getDouble(5));
					location.setLongitude(resultSet.getDouble(6));
					taggingLocations.add(location);
				}
				session.setAttribute("tagginglocations", taggingLocations);
				sql = "SELECT sight_date,sight_location,sight_state,sight_country,sight_latitude,sight_longitude FROM sighted_butterflies";
				resultSet.close();
				resultSet = statement.executeQuery(sql);
				ArrayList<LocationBean> sightingLocations = new ArrayList<LocationBean>();
				while (resultSet.next()) {
					location = new LocationBean();
					location.setDate(resultSet.getString(1));	
					location.setLocation(resultSet.getString(2));
					location.setState(resultSet.getString(3));
					location.setCountry(resultSet.getString(4));
					location.setLatitude(resultSet.getDouble(5));
					location.setLongitude(resultSet.getDouble(6));
					sightingLocations.add(location);
				}
				session.setAttribute("dashboard", sightingLocations);
				out.println("<font color=\"red\">You have successfully logged in!<br></font>");
				request.getRequestDispatcher("dashboard.jsp").include(request, response);
			}
			else {
				out.println("<font color=\"red\">Username or password incorrect, please try again!</font><br>");
				request.getRequestDispatcher("login.jsp").include(request, response);
			}
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
		    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		}
	}
}
