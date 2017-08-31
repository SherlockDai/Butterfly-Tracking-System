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
import nixyteam.ButterflyBean;

/**
 * Servlet implementation class FetchSightings
 */
@WebServlet("/sightings")
public class FetchSightings extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			ButterflyConnection butterflyConnection = new ButterflyConnection();
			statement = butterflyConnection.getConnection().createStatement();
			resultSet = statement.executeQuery("SELECT * FROM sighted_butterflies");
			response.setContentType("text/html");
			HttpSession session = request.getSession(true);
			ArrayList<ButterflyBean> sightings = new ArrayList<ButterflyBean>();
			ButterflyBean sighting;
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("sightings.jsp");
			while(resultSet.next()) {
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
			}
			session.setAttribute("sightings", sightings);
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
