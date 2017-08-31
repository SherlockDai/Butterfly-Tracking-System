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
@WebServlet("/detailedsightings")
public class FetchDetailedSightings extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			ButterflyConnection butterflyConnection = new ButterflyConnection();
			statement = butterflyConnection.getConnection().createStatement();
			String sql = "SELECT * FROM sighted_butterflies JOIN (" +
					"SELECT user_id,email,first_name,last_name,street_address," +
					"city,state,zip_code,country,phone from users" +
					") users ON sighter_id = user_id";
			resultSet = statement.executeQuery(sql);
			response.setContentType("text/html");
			HttpSession session = request.getSession(true);
			ArrayList<ButterflyBean> detailedSightings = new ArrayList<ButterflyBean>();
			ButterflyBean detailedSighting;
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("detailedsightings.jsp");
			while(resultSet.next()) {
				detailedSighting = new ButterflyBean();
				detailedSighting.setSightID(resultSet.getInt(1));
				detailedSighting.setTagID(resultSet.getInt(2));
				detailedSighting.setUserID(resultSet.getInt(3));
				detailedSighting.setDate(resultSet.getString(4));
				detailedSighting.setButterflyLocation(resultSet.getString(5));
				detailedSighting.setButterflyState(resultSet.getString(6));
				detailedSighting.setButterflyCountry(resultSet.getString(7));
				detailedSighting.setLatitude(resultSet.getDouble(8));
				detailedSighting.setLongitude(resultSet.getDouble(9));
				detailedSighting.setUserID(resultSet.getInt(10));
				detailedSighting.setEmail(resultSet.getString(11));
				detailedSighting.setFirstName(resultSet.getString(12));
				detailedSighting.setLastName(resultSet.getString(13));
				detailedSighting.setAddress(resultSet.getString(14));
				detailedSighting.setUserCity(resultSet.getString(15));
				detailedSighting.setUserState(resultSet.getString(16));
				detailedSighting.setZipCode(resultSet.getString(17));
				detailedSighting.setUserCountry(resultSet.getString(18));
				detailedSighting.setPhone(resultSet.getString(19));
				detailedSightings.add(detailedSighting);
			}
			session.setAttribute("detailedsightings", detailedSightings);
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
