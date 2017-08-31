import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
@WebServlet("/sendfile")
public class SendFile extends HttpServlet {
	private static final long serialVersionUID = 2461149238420275134L;

	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		FileInputStream fileInputStream = null;
        try {
			OutputStream out = response.getOutputStream();
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("file") == null) {
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}
			File inputFile = (File)session.getAttribute("file");
			fileInputStream = new FileInputStream(inputFile);
			response.setContentType("text/plain");
			response.setContentLength((int) inputFile.length());
			System.out.println(inputFile.length());
			// forces download
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", inputFile.getName());
			
			
			response.setHeader(headerKey, headerValue);
			// obtains response's output stream

			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
			    out.write(buffer, 0, bytesRead);
			    System.out.println("written to file");
			}
			System.out.println(inputFile.getAbsolutePath());
			out.flush();
//			inputFile.deleteOnExit();
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    if (fileInputStream != null) fileInputStream.close();
		}
    }
	
	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}