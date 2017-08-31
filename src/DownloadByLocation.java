import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/downloadbylocation")
public class DownloadByLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet resultSet = null;
		Statement statement = null;
		PrintWriter fileWriter = null;
		try {
			boolean parameterAdded = false;
			String sqlCriteria = "";
			String criteria = "Location:";
			ButterflyConnection butterflyConnection = new ButterflyConnection();
			statement = butterflyConnection.getConnection().createStatement();
			String sql = "SELECT sight_date,sight_latitude,sight_longitude,sight_location,sight_state,sight_country," +
					"tagged_butterflies.species,tagged_butterflies.tag_id FROM sighted_butterflies JOIN tagged_butterflies" +
					" ON sighted_butterflies.tag_id = tagged_butterflies.tag_id";
			if (request.getParameter("location") != null && request.getParameter("location").trim().length() > 0) {
				sqlCriteria += " WHERE sight_location='" + request.getParameter("location").trim() + "'";
				criteria += " " + request.getParameter("location").trim();
				parameterAdded = true;
			}
			if (request.getParameter("state") != null && request.getParameter("state").trim().length() > 0) {
				if (parameterAdded) {
					sqlCriteria += " AND sight_state='" + request.getParameter("state").trim() + "'";
				}
				else {
					sqlCriteria += " WHERE sight_state='" + request.getParameter("state").trim() + "'";
					parameterAdded=true;
				}
				criteria += " " + request.getParameter("state").trim();
			}
			if (request.getParameter("country") != null && request.getParameter("country").trim().length() > 0) {
				if (parameterAdded) {
					sqlCriteria += " AND sight_country='" + request.getParameter("country").trim() + "'";
				}
				else {
					sqlCriteria += " WHERE sight_country='" + request.getParameter("country").trim() + "'";
				}
				criteria += " " + request.getParameter("country").trim();
			}
			sql += sqlCriteria + " ORDER BY sight_date";
			resultSet = statement.executeQuery(sql);
			if (!resultSet.next()) {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<font color=\"red\">No matching records found!</font><br><br>");
				request.getRequestDispatcher("downloadbylocation.jsp").include(request, response);
				return;
			}
			DecimalFormat latitudeFormatter = new DecimalFormat("+00.0000000;-0");
			DecimalFormat longitudeFormatter = new DecimalFormat("+000.0000000;-0");
			String minDate = null;
			String maxDate = null;
			ArrayList<String> matchingRecords = new ArrayList<>();
			StringBuilder recordBuilder = new StringBuilder();
			int recordCount = 0;
			do {
				recordCount++;
				if (minDate == null)
					minDate = resultSet.getString(1);
				maxDate = resultSet.getString(1);
				recordBuilder.append(maxDate + " 00:00:00 ");
				recordBuilder.append(latitudeFormatter.format(resultSet.getDouble(2)));
				recordBuilder.append(longitudeFormatter.format(resultSet.getDouble(3)) + " ");
				recordBuilder.append(String.format("%-30s", resultSet.getString(4)));
				recordBuilder.append(String.format("%-30s", resultSet.getString(5)));
				recordBuilder.append(String.format("%-30s", resultSet.getString(6)));
				recordBuilder.append(String.format("%-20s", resultSet.getString(7)));
				recordBuilder.append(String.format("%06d", resultSet.getInt(8)));
				matchingRecords.add(recordBuilder.toString());
				recordBuilder.setLength(0);
			} while(resultSet.next());
			DateFormat dateFormat = new SimpleDateFormat("YYYYMMDD-HHMMSS");
			String fileName = "DownloadByLocation " + dateFormat.format(new Date()) + ".txt";
			OutputStream out = response.getOutputStream();
			response.setContentType("text/plain");
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileName);
			response.setHeader(headerKey, headerValue);
			out.write(("HD           " + minDate + " " + maxDate + "      " + criteria).getBytes(Charset.forName("utf-8")));
			out.write(String.format("%n").getBytes(Charset.forName("utf-8")));
			for (String line: matchingRecords) {
				out.write((line + "\n").getBytes(Charset.forName("utf-8")));
				out.write(String.format("%n").getBytes(Charset.forName("utf-8")));
			}
			out.write((String.format("TR %06d", recordCount)).getBytes(Charset.forName("utf-8")));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { if (resultSet != null) resultSet.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    if (fileWriter != null) fileWriter.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
