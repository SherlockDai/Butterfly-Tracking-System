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

import nixyteam.LocationBean;

/**
 * Servlet implementation class FetchSightings
 */
@WebServlet("/butterflyroute")
public class ButterflyRoute extends HttpServlet {
	private static final long serialVersionUID = -5946162527484474040L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = new ButterflyConnection().getConnection().createStatement();
			int butterflyID = Integer.parseInt(request.getParameter("number"));
			String sql = "SELECT tag_date,tag_location,tag_state,tag_country,tag_latitude,tag_longitude FROM tagged_butterflies WHERE tag_ID=" + butterflyID;
			resultSet = statement.executeQuery(sql);
			response.setContentType("text/html");
			HttpSession session = request.getSession(true);
			ArrayList<LocationBean> locations = new ArrayList<LocationBean>();
			LocationBean location;
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("showroute.jsp");
			if (!resultSet.next()) {
				out.println("<font color=\"red\">No match found for the specified butterfly tag ID!</font><br><br>");
				request.getRequestDispatcher("butterflyroute.jsp").include(request, response);
				return;
			}
			location = new LocationBean();
			location.setDate(resultSet.getString(1));	
			location.setLocation(resultSet.getString(2));
			location.setState(resultSet.getString(3));
			location.setCountry(resultSet.getString(4));
			location.setLatitude(resultSet.getDouble(5));
			location.setLongitude(resultSet.getDouble(6));
			locations.add(location);
			sql = "SELECT sight_date,sight_location,sight_state,sight_country,sight_latitude,sight_longitude FROM sighted_butterflies WHERE tag_ID=" + butterflyID;
			resultSet.close();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				location = new LocationBean();
				location.setDate(resultSet.getString(1));	
				location.setLocation(resultSet.getString(2));
				location.setState(resultSet.getString(3));
				location.setCountry(resultSet.getString(4));
				location.setLatitude(resultSet.getDouble(5));
				location.setLongitude(resultSet.getDouble(6));
				locations.add(location);
			}
			session.setAttribute("locations", locations);
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