/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import webformgen.ConnectionManager;

/**
 *
 * @author lvbubi
 */
@WebServlet(name = "OpenPDF", urlPatterns = {"/OpenPDF"})
public class OpenPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
        Statement select;
        byte[] receivedPDF=null;
        String PDFKey="2";
        @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

        @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		performTask(request, response);
	}
        @Override
        public void init()throws ServletException{  
            try {
                select = new ConnectionManager().getConnection().createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(OpenPDF.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
	private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
            System.out.println(request.toString());
            PDFKey=request.getParameter("SelectedPDF");
                try {
                    ResultSet rs = select.executeQuery("SELECT adatok FROM GeneraltPDF WHERE id = "+PDFKey);
                    rs = select.getResultSet();
                    rs.next() ;
                    receivedPDF = rs.getBytes("adatok");
                } catch (SQLException ex) {
                    Logger.getLogger(OpenPDF.class.getName()).log(Level.SEVERE, null, ex);
                }
		String pdfFileName = "out.pdf";
                
		String contextPath = getServletContext().getRealPath(File.separator);     
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
		response.setContentLength(receivedPDF.length);

                ByteArrayInputStream bis = new ByteArrayInputStream(receivedPDF);
		OutputStream responseOutputStream = response.getOutputStream();
		int bytes;
		while ((bytes = bis.read()) != -1) {
			responseOutputStream.write(bytes);
		}
	}

}