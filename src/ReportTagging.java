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
@WebServlet("/tagbutterfly")
public class ReportTagging extends HttpServlet {
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
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		PreparedStatement preparedStatement = null;
		if (session == null || session.getAttribute("user") == null) {
			doGet(request, response); 
			return;
		}
		else {
			try {
				UserBean user = (UserBean)session.getAttribute("user");
				response.setContentType("text/html");
				out = response.getWriter();
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("tagbutterfly.jsp");
				ButterflyConnection butterflyConnection = new ButterflyConnection();
				ButterflyBean tagging = new ButterflyBean();
				tagging.setTagID(Integer.parseInt(request.getParameter("number")));
				tagging.setUserID(user.getUserID());
				String year = request.getParameter("year");
				String month = request.getParameter("month");
				String day = request.getParameter("day");
				if (month.length() == 1)
					month = "0" + month;
				if (day.length() == 1)
					day = "0" + day;
				tagging.setDate(year + month + day);
				tagging.setButterflyLocation(request.getParameter("location"));
				tagging.setButterflyState(request.getParameter("state"));
				tagging.setButterflyCountry(request.getParameter("country"));
				tagging.setLatitude(Double.parseDouble(request.getParameter("latitude")));
				tagging.setLongitude(Double.parseDouble(request.getParameter("longitude")));
				tagging.setSpecies(request.getParameter("species"));
				String sql = "insert into tagged_butterflies values (?, ?, cast(? as DATE), ?, ?, ?, ?, ?, ?)";
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
				preparedStatement.executeUpdate();
				out.println("<font color=\"red\">Update Successful!</font><br>");
				requestDispatcher.include(request, response);
			} catch (SQLException e) {
				out.println("<font color=\"red\">" + e.getMessage()+ "</font><br><br>");
				out.println("<a href=\"index.jsp\">Home Page</a>");;
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
}
