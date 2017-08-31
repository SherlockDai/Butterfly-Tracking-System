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
import nixyteam.ButterflyBean;
import nixyteam.UserBean;

/**
 * Servlet implementation class ReportTagging
 */
@WebServlet("/updatesighting")
public class UpdateSighting extends HttpServlet {
	private static final long serialVersionUID = -1838207374663783715L;

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
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		ResultSet resultSet = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		if (session == null || session.getAttribute("user") == null) {
			doGet(request, response); 
		}
		else {
			try {
				UserBean user = (UserBean)session.getAttribute("user");
				response.setContentType("text/html");
				out = response.getWriter();
				ButterflyConnection butterflyConnection = new ButterflyConnection();
				int sightID = Integer.parseInt(request.getParameter("sightnumber"));
				String sql = "SELECT * FROM sighted_butterflies WHERE sight_id=" + sightID;
				statement = butterflyConnection.getConnection().createStatement();
				resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					if (!user.isAdministrator() && resultSet.getInt(2) != user.getUserID()) {
						out.println("<font color=\"red\">You can only change your own tagging!</font><br><br>");
						request.getRequestDispatcher("updatetagging.jsp").include(request, response);
						return;
					}
					int newTagID;
					try {
						newTagID = Integer.parseInt(request.getParameter("number"));
					} catch (Exception e) {
						newTagID = 0;
					}
					int newTaggerID;
					try {
						newTaggerID = Integer.parseInt(request.getParameter("tagger"));
					} catch (Exception e) {
						newTaggerID = 0;
					}
					String newDate = null;
					if (request.getParameter("year") != null && request.getParameter("month").trim().length() > 0) {
						String year, month, day;
						year = request.getParameter("year").trim();
						month = request.getParameter("month").trim();
						day = request.getParameter("day").trim();
						if (month.length() == 1)
							month = "0" + month;
						if (day.length() == 1)
							day = "0" + day;
						newDate = year + "-" + month + "-" + day;
					}
					String newLocation = (request.getParameter("location") != null)? request.getParameter("location").trim() : null;
					String newState = (request.getParameter("state") != null)? request.getParameter("state").trim() : null;					
					String newCountry = (request.getParameter("country") != null)? request.getParameter("country").trim() : null;	
					Double newLatitude;
					try {
						newLatitude = Double.parseDouble(request.getParameter("latitude"));
					} catch (Exception e) {
						newLatitude = 375.2015; // from CIS 375/Fall 2015 :)
					} 
					Double newLongitude;
					try {
						newLongitude = Double.parseDouble(request.getParameter("longitude"));
					} catch (Exception e) {
						newLongitude = 375.2015;
					}
					ButterflyBean sighting = new ButterflyBean();
					sighting.setTagID(resultSet.getInt(2));
					sighting.setUserID(resultSet.getInt(3));
					sighting.setDate(resultSet.getString(4));
					sighting.setButterflyLocation(resultSet.getString(5));
					sighting.setButterflyState(resultSet.getString(6));
					sighting.setButterflyCountry(resultSet.getString(7));
					sighting.setLatitude(resultSet.getDouble(8));
					sighting.setLongitude(resultSet.getDouble(9));
					if (newTagID != 0)
						sighting.setTagID(newTagID);
					if (newTaggerID != 0)
						sighting.setUserID(newTaggerID);
					if (newDate != null && newDate.length() > 0)
						sighting.setDate(newDate);
					if (newLocation != null && newLocation.length() > 0)
						sighting.setButterflyLocation(newLocation);
					if (newState != null  && newState.length() > 0)
						sighting.setButterflyState(newState);
					if (newCountry != null  && newCountry.length() > 0)
						sighting.setButterflyCountry(newCountry);
					if (newLatitude < 375)
						sighting.setLatitude(newLatitude);
					if (newLongitude < 375)
						sighting.setLongitude(newLongitude);
					sql = "UPDATE sighted_butterflies " +
						" SET tag_id=?,sighter_id=?,sight_date=?,sight_location=?,sight_state=?" +
						",sight_country=?,sight_latitude=?,sight_longitude=? WHERE sight_id=?";
					preparedStatement = butterflyConnection.getConnection().prepareStatement(sql);
					preparedStatement.setInt(1, sighting.getTagID());
					preparedStatement.setInt(2, sighting.getUserID());
					preparedStatement.setString(3, sighting.getDate());
					preparedStatement.setString(4, sighting.getButterflyLocation());
					preparedStatement.setString(5, sighting.getButterflyState());
					preparedStatement.setString(6, sighting.getButterflyCountry());
					preparedStatement.setDouble(7, sighting.getLatitude());
					preparedStatement.setDouble(8, sighting.getLongitude());
					preparedStatement.setInt(9, sightID);
					preparedStatement.executeUpdate();
					out.println("<font color=\"red\">Update Successfull!</font><br>");
				}
				else {
					out.println("<font color=\"red\">The sighting number \"" + sightID + "\" does not exist</font><br>");
				}
				request.getRequestDispatcher("updatesighting.jsp").include(request, response);
			} catch (SQLIntegrityConstraintViolationException e) {
				out.println("<font color=\"red\">Butterfly Tag ID or User ID is not valid!</font><br><br>");
				request.getRequestDispatcher("updatesighting.jsp").include(request, response);
			} catch (SQLException e) {
				out.println("<font color=\"red\">" + e.getMessage()+ "</font><br><br>");
				out.println("<a href=\"index.jsp\">Home Page</a>");
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
