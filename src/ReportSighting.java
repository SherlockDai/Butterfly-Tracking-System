import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
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
@WebServlet("/sightbutterfly")
public class ReportSighting extends HttpServlet {
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
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("sightbutterfly.jsp");
		PreparedStatement preparedStatement = null;
		if (session == null || session.getAttribute("user") == null) {
			doGet(request, response);
			return;
		}
		try {
			UserBean user = (UserBean)session.getAttribute("user");
			ButterflyConnection butterflyConnection = new ButterflyConnection();
			ButterflyBean sighting = new ButterflyBean();
			sighting.setTagID(Integer.parseInt(request.getParameter("number")));
			sighting.setUserID(user.getUserID());
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String day = request.getParameter("day");
			if (month.length() == 1)
				month = "0" + month;
			if (day.length() == 1)
				day = "0" + day;
			sighting.setDate(year + month + day);
			sighting.setButterflyLocation(request.getParameter("location"));
			sighting.setButterflyState(request.getParameter("state"));
			sighting.setButterflyCountry(request.getParameter("country"));
			sighting.setLatitude(Double.parseDouble(request.getParameter("latitude")));
			sighting.setLongitude(Double.parseDouble(request.getParameter("longitude")));
			String sql = "insert into sighted_butterflies (tag_id,sighter_id,sight_date,sight_location,sight_state"+
							",sight_country,sight_latitude,sight_longitude)" +
							" values (?, ?, cast(? as DATE), ?, ?, ?, ?, ?)";
			preparedStatement =	butterflyConnection.getConnection().prepareStatement(sql);
			preparedStatement.setInt(1, sighting.getTagID());
			preparedStatement.setInt(2, sighting.getUserID());
			preparedStatement.setString(3, sighting.getDate());
			preparedStatement.setString(4, sighting.getButterflyLocation());
			preparedStatement.setString(5, sighting.getButterflyState());
			preparedStatement.setString(6, sighting.getButterflyCountry());
			preparedStatement.setDouble(7, sighting.getLatitude());
			preparedStatement.setDouble(8, sighting.getLongitude());
			preparedStatement.executeUpdate();
			out.println("<font color=\"red\">Update Successful!</font><br>");
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
		    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
		}
	}
}