import java.io.IOException;
import java.io.PrintWriter;
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
import nixyteam.ButterflyBean;

/**
 * Servlet implementation class FetchSightings
 */
@WebServlet("/customsightings")
public class CustomSightings extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			ButterflyConnection butterflyConnection = new ButterflyConnection();
			ButterflyBean sighting;
			ArrayList<ButterflyBean> sightings = new ArrayList<>();
			boolean parameterAdded = false;
			int tagID = 0;
			int sighterID = 0;
			String date = null; 
			String location = null;
			String state = null;
			String country = null;
			Double latitude = 375.2015; //CIS 375, Fall 2015!
			Double longitude = 375.2015;
			try { tagID = Integer.parseInt(request.getParameter("number"));
			} catch (Exception e) {	}
			try { sighterID = Integer.parseInt(request.getParameter("sighter"));
			} catch (Exception e) {	}
			if (request.getParameter("year") != null && request.getParameter("month").trim().length() > 0) {
				String year, month, day;
				year = request.getParameter("year").trim();
				month = request.getParameter("month").trim();
				day = request.getParameter("day").trim();
				if (month.length() == 1)
					month = "0" + month;
				if (day.length() == 1)
					day = "0" + day;
				date = year + "-" + month + "-" + day;
			}
			location = (request.getParameter("location") != null)? request.getParameter("location").trim() : null;
			state = (request.getParameter("state") != null)? request.getParameter("state").trim() : null;					
			country = (request.getParameter("country") != null)? request.getParameter("country").trim() : null;	
			try { latitude = Double.parseDouble(request.getParameter("latitude"));
			} catch (Exception e) { } 
			try { longitude = Double.parseDouble(request.getParameter("longitude"));
			} catch (Exception e) { } 
			StringBuilder sql = new StringBuilder("SELECT * FROM sighted_butterflies");
			if (tagID > 0) {
				sql.append(" WHERE tag_id=" + tagID);
				parameterAdded = true;
			}
			if (sighterID > 0) {
				if (parameterAdded) {
					sql.append(" AND sighter_id=" + sighterID);
				}
				else {
					sql.append(" WHERE sighter_id=" + sighterID);
					parameterAdded = true;
				}
			}
			if (date != null && date.length() > 0) {
				if (parameterAdded) {
					sql.append(" AND sight_date='" + date + "'");
				}
				else {
					sql.append(" WHERE sight_date='" + date + "'");
					parameterAdded = true;
				}
			}
			if (location != null && location.length() > 0) {
				if (parameterAdded) {
					sql.append(" AND sight_location='" + location + "'");
				}
				else {
					sql.append(" WHERE sight_location='" + location + "'");
					parameterAdded = true;
				}
			}
			if (state != null && state.length() > 0) {
				if (parameterAdded) {
					sql.append(" AND sight_state='" + state + "'");
				}
				else {
					sql.append(" WHERE sight_state='" + state + "'");
					parameterAdded = true;
				}
			}
			if (country != null && country.length() > 0) {
				if (parameterAdded) {
					sql.append(" AND sight_country='" + country + "'");
				}
				else {
					sql.append(" WHERE sight_country='" + country + "'");
					parameterAdded = true;
				}
			}
			if (latitude < 375) {
				if (parameterAdded) {
					sql.append(" AND (sight_latitude > " + (latitude - 0.1) + " AND sight_latitude < " + (latitude + 0.1) + ")");
				}
				else {
					sql.append(" WHERE (sight_latitude > " + (latitude - 0.1) + " AND sight_latitude < " + (latitude + 0.1) + ")");
					parameterAdded = true;
				}
			}
			if (longitude < 375) {
				if (parameterAdded) {
					sql.append(" AND (sight_longitude > " + (longitude - 0.1) + " AND sight_longitude < " + (longitude + 0.1) + ")");
				}
				else {
					sql.append(" WHERE (sight_longitude > " + (longitude - 0.1) + " AND sight_longitude < " + (longitude + 0.1) + ")");
				}
			}
			statement = butterflyConnection.getConnection().createStatement();
			resultSet = statement.executeQuery(sql.toString());
			if (!resultSet.next()) {
				out.println("<font color=\"red\">No matching records found, try again!</font><br><br>");
				request.getRequestDispatcher("customsightings.jsp").include(request, response);	
				return;
			}
			do {
				sighting = new ButterflyBean();
				sighting.setSightID(resultSet.getInt(1));
				sighting.setTagID(resultSet.getInt(2));
				sighting.setUserID(resultSet.getInt(3));
				sighting.setDate(resultSet.getString(4));
				sighting.setButterflyLocation(resultSet.getString(5));
				sighting.setButterflyState(resultSet.getString(6));
				sighting.setButterflyCountry(resultSet.getString(7));
				sighting.setLatitude(resultSet.getDouble(8));
				sighting.setLongitude(resultSet.getDouble(9));
				sightings.add(sighting);
			} while (resultSet.next());
			session.setAttribute("sightings", sightings);
			out.println("<font color=\"red\">Butterfly taggings matching your criteria:</font><br><br>");
			request.getRequestDispatcher("sightings.jsp").include(request, response);	
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
