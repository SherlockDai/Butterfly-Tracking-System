import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@WebServlet("/processbatch")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "C:/uploads";
	ButterflyConnection butterflyConnection = new ButterflyConnection();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<font color=\"red\">Please go back to the homepage and login properly!</font><br><br>");
		out.println("<a href=\"index.jsp\">Home Page.jsp</a>");
	}
	
@Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			doGet(request, response); 
			return;
		}
		if(ServletFileUpload.isMultipartContent(request)){
			BufferedReader bufferedReader = null;
			try {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				String fileName = null ;
				for(FileItem item : multiparts){
					if(!item.isFormField()){
						fileName = UPLOAD_DIRECTORY + File.separator + new File(item.getName()).getName();
						item.write( new File(fileName));
					}
				} //File uploaded successfully
				
				bufferedReader = new BufferedReader(new FileReader(fileName));
				
				ArrayList<String> sightings = new ArrayList<>();
				ArrayList<String> taggings = new ArrayList<>();
				String line = null, date = null;
				int recordsFound = 0, sequence = 0;
				boolean keepGoing = true, headerFound = false, trailerRecord = false;
	            while((line = bufferedReader.readLine()) != null && keepGoing) {
	            	if (!headerFound && line.length() > 1) {
	            		if (line.substring(0,2).equalsIgnoreCase("HD")) {
	            			String[] header = line.split(" ", 3);
	            			headerFound = true;
	            			if (header.length != 3 || !checkSequence(header)) {
	            				out.println("<font color=\"red\">File sequence number or date invalid!</font><br><br>");
	            				request.getRequestDispatcher("/uploadbatch.jsp").include(request, response);
	            				bufferedReader.close();
	            				return;
	            			}
	            			else {
	            		   		date = header[2].trim();
	            		   		sequence = Integer.parseInt(header[1]);
	            			}
	            		}
	            	}
	            	if (headerFound && line.length() > 2) {
	            		if (line.substring(0,1).equalsIgnoreCase("S")) {
	            			sightings.add(line);
	            			recordsFound++;
	            		}
	            		else if (line.substring(0,2).equalsIgnoreCase("TR")) {
	            			trailerRecord = checkTrailerRecord(line.split(" ", 2), recordsFound);
	            			keepGoing = false;
	            		}
	            		else if (line.substring(0,1).equalsIgnoreCase("T")) {
	            			taggings.add(line);
	            			recordsFound++;
	            		}
	            		else {/*Ignore*/}
	            	}
	            } 
	            bufferedReader.close();
	            if (!headerFound)
	            	throw new Exception("Header not found or Invalid File");
	            int recordsProcessed = processTaggings(taggings);
	            recordsProcessed += processSightings(sightings);
				try { bufferedReader.close(); } catch (Exception e) {/*Ignore*/}
				boolean sequenceUpdated = (recordsProcessed > 0) ? updateSequence (sequence, date) : false;
				out.println("<br><br><br>");
				if (!sequenceUpdated)
	            	out.println("<font color=\"red\">The sequence number and date were not updated!</font><br><br>");
	            out.println("<font color=\"blue\">The batch file was processed!</font><br><br>");
	            out.println("<font color=\"blue\">" + recordsProcessed + " of " + recordsFound + " were processed successfully!</font><br><br>");
	            if (trailerRecord)
	            	out.println("<font color=\"blue\">Trailer Record matched records found</font><br><br>");
	            else
	            	out.println("<font color=\"red\">Trailer Record was missing or did not match records found</font><br><br>");
				request.getRequestDispatcher("/uploadreport.jsp").include(request, response);
			} catch (Exception e) {
				out.println("<font color=\"red\">Failed upload due to " + e.getMessage() + "</font><br><br>");
			    request.getRequestDispatcher("/uploadbatch.jsp").include(request, response);
			    return;
			} finally {
				try { if (bufferedReader != null) bufferedReader.close(); } catch (Exception e) {/*Ignore*/}
			}
		} else {
			out.println("<font color=\"red\">This servelet is for file upload only!</font><br><br>");
		    request.getRequestDispatcher("/uploadbatch.jsp").include(request, response);
       }
   }
   
   private boolean checkSequence(String[] header) {
	   	PreparedStatement preparedStatement = null;
	   	ResultSet resultSet = null;
	   	try {
	   		String date = header[2].trim();
	   		int sequence = Integer.parseInt(header[1].trim());
	   		String sql = "SELECT * FROM batchsequence WHERE sequence = ? and date <= ? and ? <= NOW()";
	   		preparedStatement = butterflyConnection.getConnection().prepareStatement(sql);
	   		preparedStatement.setInt(1, sequence - 1);
	   		preparedStatement.setString(2, date);
	   		preparedStatement.setString(3, date);
	   		resultSet = preparedStatement.executeQuery();
	   		if (resultSet.next())
			   return true;
	   		else
			   return false;
	   } catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
	    	try { if (resultSet != null) resultSet.close(); } catch (Exception e) {/*Ignore*/};
	    	try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {/*Ignore*/};
		}
	}
   
   	private boolean checkTrailerRecord(String[] trailer, int recordsFound) {
   		try {
   			int trailerRecord = Integer.parseInt(trailer[1].trim());
   			if (trailerRecord != recordsFound)
   				return false;
   			else
   				return true;
	   } catch (Exception e) {
		   return false;
		}
   	}
   
   	private int processTaggings(ArrayList<String> taggings) {
   		PreparedStatement preparedStatement = null;
   		int recordsProcessed = 0;
   		final int MAXIMUM_BATCH_SIZE = 100;
   		int batchCount = 0;
   		try {
   			String sql = "INSERT IGNORE INTO tagged_butterflies values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			   	preparedStatement = butterflyConnection.getConnection().prepareStatement(sql);
			   	for (String line: taggings) {
			   		try {
			   		int taggerID = Integer.parseInt(line.substring(2, 12).trim());
					   	String date = line.substring(12, 22).trim();
					   	Double latitude = Double.parseDouble(line.substring(32, 43).trim());
					   	Double longitude = Double.parseDouble(line.substring(43, 55).trim());
					   	if (latitude > 90 || latitude < -90)
					   		throw new Exception("Invalid latitude!");
					   	if (longitude > 180 || longitude < -180)
					   		throw new Exception("Invalid longitude!");
					   	String city = line.substring(55, 85).trim();
					   	String state = line.substring(85, 115).trim();
					   	String country = line.substring(115, 144).trim();
					   	String species = line.substring(144, 164).trim();
					   	int tagID = Integer.parseInt(line.substring(164).trim());
					   	preparedStatement.setInt(1, tagID);
					   	preparedStatement.setInt(2, taggerID);
					   	preparedStatement.setString(3, date);
					   	preparedStatement.setString(4, city);
					   	preparedStatement.setString(5, state);
					   	preparedStatement.setString(6, country);
					   	preparedStatement.setDouble(7, latitude);
					   	preparedStatement.setDouble(8, longitude);
					   	preparedStatement.setString(9, species);
					   	preparedStatement.addBatch();
					   	if (++batchCount % MAXIMUM_BATCH_SIZE == 0) {
					   		int[] processed = preparedStatement.executeBatch();
					   		for (int i = 0; i < processed.length; i++)
					   			recordsProcessed += processed[i];
					   	}
			   		} catch (Exception e) {
			   			e.printStackTrace();
			   		} 
			   	}
			   	int[] processed = preparedStatement.executeBatch();
		   		for (int i = 0; i < processed.length; i++)
		   			recordsProcessed += processed[i];
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {/*Ignore*/}
		}
   		return recordsProcessed;
   	}
   
   	private int processSightings(ArrayList<String> sightings) {
   		PreparedStatement preparedStatement = null;
   		int recordsProcessed = 0;
   		final int MAXIMUM_BATCH_SIZE = 100;
   		int batchCount = 0;
   		try {
			String sql = "insert into sighted_butterflies (tag_id,sighter_id,sight_date,sight_location,sight_state"+
					",sight_country,sight_latitude,sight_longitude)" +
					" values (?, ?, cast(? as DATE), ?, ?, ?, ?, ?)";
			   	preparedStatement = butterflyConnection.getConnection().prepareStatement(sql);
			   	for (String line: sightings) {
			   		try {
			   		int sighterID = Integer.parseInt(line.substring(2, 12).trim());
					   	String date = line.substring(12, 22).trim();
					   	Double latitude = Double.parseDouble(line.substring(32, 43).trim());
					   	Double longitude = Double.parseDouble(line.substring(43, 55).trim());
					   	if (latitude > 90 || latitude < -90)
					   		throw new Exception("Invalid latitude!");
					   	if (longitude > 180 || longitude < -180)
					   		throw new Exception("Invalid longitude!");
					   	String city = line.substring(55, 85).trim();
					   	String state = line.substring(85, 115).trim();
					   	String country = line.substring(115, 144).trim();
					   	int tagID = Integer.parseInt(line.substring(164).trim());
					   	preparedStatement.setInt(1, tagID);
					   	preparedStatement.setInt(2, sighterID);
					   	preparedStatement.setString(3, date);
					   	preparedStatement.setString(4, city);
					   	preparedStatement.setString(5, state);
					   	preparedStatement.setString(6, country);
					   	preparedStatement.setDouble(7, latitude);
					   	preparedStatement.setDouble(8, longitude);
					   	preparedStatement.addBatch();
					   	if (++batchCount % MAXIMUM_BATCH_SIZE == 0) {
					   		int[] processed = preparedStatement.executeBatch();
					   		for (int i = 0; i < processed.length; i++)
					   			recordsProcessed += processed[i];
					   	}
			   		} catch (Exception e) {
			   			e.printStackTrace();
			   		} 
			   	}
			   	int[] processed = preparedStatement.executeBatch();
		   		for (int i = 0; i < processed.length; i++)
		   			recordsProcessed += processed[i];
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {/*Ignore*/}
		}
   		return recordsProcessed;
   	}
   	
   	private boolean updateSequence (int sequence, String date) {
   		PreparedStatement preparedStatement = null;
   		try {
			String sql = "UPDATE batchsequence SET sequence=?, date=?";
			preparedStatement = butterflyConnection.getConnection().prepareStatement(sql);
			preparedStatement.setInt(1, sequence);
			preparedStatement.setString(2, date);
			int processed = preparedStatement.executeUpdate();
			return (processed == 1)? true: false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {/*Ignore*/}
		}
   	}
}