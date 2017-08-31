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
import nixyteam.FeedbackBean;

/**
 * Servlet implementation class FetchSightings
 */
@WebServlet("/feedbacks")
public class FetchFeedbacks extends HttpServlet {
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
			resultSet = statement.executeQuery("SELECT feedback_date,user_name,title,comment,rating FROM feedback ORDER BY feedback_date DESC");
			response.setContentType("text/html");
			HttpSession session = request.getSession(true);
			ArrayList<FeedbackBean> feedbacks = new ArrayList<FeedbackBean>();
			FeedbackBean feedback;
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewfeedback.jsp");
			while(resultSet.next()) {
				feedback = new FeedbackBean();
				feedback.setDateTime(resultSet.getString(1));
				feedback.setUserName(resultSet.getString(2));				
				feedback.setTitle(resultSet.getString(3));
				feedback.setComment(resultSet.getString(4));
				feedback.setRating(resultSet.getInt(5));
				feedbacks.add(feedback);
			}
			session.setAttribute("feedbacks", feedbacks);
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