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
@WebServlet("/detailedtaggings")
public class FetchDetailedTaggings extends HttpServlet {
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
			String sql = "SELECT * FROM tagged_butterflies JOIN (" +
						"select user_id,email,first_name,last_name,street_address,city,state" +
						",zip_code,country,phone,administrator,disabled from users" +
						") users on tagger_id = user_id";
			resultSet = statement.executeQuery(sql);
			response.setContentType("text/html");
			HttpSession session = request.getSession(true);
			ArrayList<ButterflyBean> detailedTaggings = new ArrayList<ButterflyBean>();
			ButterflyBean detailedTagging;
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("detailedtaggings.jsp");
			while(resultSet.next()) {
				detailedTagging = new ButterflyBean();
				detailedTagging.setTagID(resultSet.getInt(1));
				detailedTagging.setUserID(resultSet.getInt(2));
				detailedTagging.setDate(resultSet.getString(3));
				detailedTagging.setButterflyLocation(resultSet.getString(4));
				detailedTagging.setButterflyState(resultSet.getString(5));
				detailedTagging.setButterflyCountry(resultSet.getString(6));
				detailedTagging.setLatitude(resultSet.getDouble(7));
				detailedTagging.setLongitude(resultSet.getDouble(8));
				detailedTagging.setSpecies(resultSet.getString(9));
				detailedTagging.setUserID(resultSet.getInt(10));
				detailedTagging.setEmail(resultSet.getString(11));
				detailedTagging.setFirstName(resultSet.getString(12));
				detailedTagging.setLastName(resultSet.getString(13));
				detailedTagging.setAddress(resultSet.getString(14));
				detailedTagging.setUserCity(resultSet.getString(15));
				detailedTagging.setUserState(resultSet.getString(16));
				detailedTagging.setZipCode(resultSet.getString(17));
				detailedTagging.setUserCountry(resultSet.getString(18));
				detailedTagging.setPhone(resultSet.getString(19));
				detailedTaggings.add(detailedTagging);
			}
			session.setAttribute("detailedtaggings", detailedTaggings);
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
