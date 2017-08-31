import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nixyteam.UserBean;

/**
 * Servlet implementation class ReportTagging
 */
@WebServlet("/deletesighting")
public class DeleteSighting extends HttpServlet {
	private static final long serialVersionUID = -1838207374663783715L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<font color=\"red\">Only administrators can perform this action!</font><br><br>");
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
		UserBean user = (UserBean)session.getAttribute("user");
		if (!user.isAdministrator()) {
			out.println("<font color=\"red\">Only administrators can perform this action!</font><br><br>");
			out.println("<a href=\"index.jsp\">Dashboard</a>");
		}
		else {
			try {
				response.setContentType("text/html");
				out = response.getWriter();
				int sightID = Integer.parseInt(request.getParameter("number"));
				String sql = "DELETE FROM sighted_butterflies WHERE sight_id=?";
				preparedStatement = new ButterflyConnection().getConnection().prepareStatement(sql);
				preparedStatement.setInt(1, sightID);
				int rowsUpdated = preparedStatement.executeUpdate();
				if (rowsUpdated == 1)
					out.println("<font color=\"red\">Sighting number " + sightID + " was removed successfully!</font><br>");
				else
					out.println("<font color=\"red\">The sighting number " + sightID + " does not exist!</font><br>");
				request.getRequestDispatcher("updatesighting.jsp").include(request, response);
			} catch (SQLException e) {
				out.println("<font color=\"red\">" + e.getMessage()+ "</font><br><br>");
				out.println("<a href=\"dashboard.jsp\">Dashboard</a>");
			} catch (Exception e) {
				out.println("<font color=\"red\">" + e.getMessage() + "!</font><br><br>");
				out.println("<a href=\"dashboard.jsp\">Dashboard</a>");
				e.printStackTrace();
			} finally {
			    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			}
		}
	}
}