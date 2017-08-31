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
@WebServlet("/taggings")
public class FetchTaggings extends HttpServlet {
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
			ButterflyConnection butterflyonnection = new ButterflyConnection();
			statement = butterflyonnection.getConnection().createStatement();
			resultSet = statement.executeQuery("SELECT * FROM tagged_butterflies");
			HttpSession session = request.getSession(true);
			ArrayList<ButterflyBean> taggings = new ArrayList<ButterflyBean>();
			ButterflyBean tagging;
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("butterflies.jsp");
			while(resultSet.next()) {
				tagging = new ButterflyBean();
				tagging.setTagID(resultSet.getInt(1));
				tagging.setUserID(resultSet.getInt(2));
				tagging.setDate(resultSet.getString(3));
				tagging.setButterflyLocation(resultSet.getString(4));
				tagging.setButterflyState(resultSet.getString(5));
				tagging.setButterflyCountry(resultSet.getString(6));
				tagging.setLatitude(resultSet.getDouble(7));
				tagging.setLongitude(resultSet.getDouble(8));
				tagging.setSpecies(resultSet.getString(9));
				taggings.add(tagging);
			}
			session.setAttribute("taggings", taggings);
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
