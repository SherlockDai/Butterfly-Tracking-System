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
import nixyteam.FeedbackBean;
import nixyteam.UserBean;

/**
 * Servlet implementation class ReportTagging
 */
@WebServlet("/submitfeedback")
public class SubmitFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		PreparedStatement preparedStatement = null;
		FeedbackBean feedback = new FeedbackBean();
		feedback.setUserName("Guest");
		if (session != null && session.getAttribute("user") != null) {
			UserBean user = (UserBean)session.getAttribute("user");
			feedback.setUserName(user.getFirstName() + " " + user.getLastName());
		}
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("feedback.jsp");
			ButterflyConnection butterflyConnection = new ButterflyConnection();
			feedback.setTitle(request.getParameter("title"));
			feedback.setComment(request.getParameter("comment"));
			feedback.setRating(Integer.parseInt(request.getParameter("rating")));
			String sql = "INSERT INTO feedback (user_name,title,comment,rating) values (?, ?, ?, ?)";
			preparedStatement = butterflyConnection.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, feedback.getUserName());
			preparedStatement.setString(2, feedback.getTitle());
			preparedStatement.setString(3, feedback.getComment());
			preparedStatement.setInt(4, feedback.getRating());
			preparedStatement.executeUpdate();
			out.println("<font color=\"red\">Feedback submitted successfully!</font><br>");
			requestDispatcher.include(request, response);
		} catch (SQLException e) {
			out.println("<font color=\"red\">" + e.getMessage()+ "</font><br><br>");
			out.println("<a href=\"index.jsp\">Home Page</a>");;
		}catch (Exception e) {
			out.println("<font color=\"red\">" + e.getMessage() + "!</font><br><br>");
			out.println("<a href=\"index.jsp\">Home Page</a>");
			e.printStackTrace();
		} finally {
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
		}
	}
}
