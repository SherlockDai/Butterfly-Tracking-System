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
@WebServlet("/updatetagging")
public class UpdateTagging extends HttpServlet {
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
				int oldTagID = Integer.parseInt(request.getParameter("oldnumber"));
				String sql = "SELECT * FROM tagged_butterflies WHERE tag_id=" + oldTagID;
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
					String newSpecies = (request.getParameter("species") != null)? request.getParameter("species").trim() : null;
					ButterflyBean tagging = new ButterflyBean();
					tagging.setTagID(resultSet.getInt(1));
					tagging.setUserID(resultSet.getInt(2));
					tagging.setDate(resultSet.getString(3));
					tagging.setButterflyLocation(resultSet.getString(4));
					tagging.setButterflyState(resultSet.getString(5));
					tagging.setButterflyCountry(resultSet.getString(6));
					tagging.setLatitude(resultSet.getDouble(7));
					tagging.setLongitude(resultSet.getDouble(8));
					tagging.setSpecies(resultSet.getString(9));
					if (newTagID != 0)
						tagging.setTagID(newTagID);
					if (newTaggerID != 0)
						tagging.setUserID(newTaggerID);
					if (newDate != null && newDate.length() > 0)
						tagging.setDate(newDate);
					if (newLocation != null && newLocation.length() > 0)
						tagging.setButterflyLocation(newLocation);
					if (newState != null  && newState.length() > 0)
						tagging.setButterflyState(newState);
					if (newCountry != null  && newCountry.length() > 0)
						tagging.setButterflyCountry(newCountry);
					if (newLatitude < 375)
						tagging.setLatitude(newLatitude);
					if (newLongitude < 375)
						tagging.setLongitude(newLongitude);
					if (newSpecies != null  && newSpecies.length() > 0)
						tagging.setSpecies(newSpecies);
					sql = "UPDATE tagged_butterflies " +
						" SET tag_id=?,tagger_id=?,tag_date=?,tag_location=?,tag_state=?" +
						",tag_country=?,tag_latitude=?,tag_longitude=?,species=? WHERE tag_id=?";
					preparedStatement = butterflyConnection.getConnection().prepareStatement(sql);
					preparedStatement.setInt(1, tagging.getTagID());
					preparedStatement.setInt(2, tagging.getUserID());
					preparedStatement.setString(3, tagging.getDate());
					preparedStatement.setString(4, tagging.getButterflyLocation());
					preparedStatement.setString(5, tagging.getButterflyState());
					preparedStatement.setString(6, tagging.getButterflyCountry());
					preparedStatement.setDouble(7, tagging.getLatitude());
					preparedStatement.setDouble(8, tagging.getLongitude());
					preparedStatement.setString(9, tagging.getSpecies());
					preparedStatement.setInt(10, oldTagID);
					preparedStatement.executeUpdate();
					out.println("<font color=\"red\">Update Successfull!</font><br>");
				}
				else {
					out.println("<font color=\"red\">The old tag number \"" + oldTagID + "\" does not exist</font><br>");
				}
				request.getRequestDispatcher("updatetagging.jsp").include(request, response);
			} catch (SQLIntegrityConstraintViolationException e) {
				out.println("<font color=\"red\">New Tag ID or User ID not available!</font><br><br>");
				request.getRequestDispatcher("updatetagging.jsp").include(request, response);
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
			    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			}
		}
	}
}
