/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.servlet;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

@WebServlet(name = "FormatoSolicitudInmueble", urlPatterns = {"/FormatoSolicitudInmueble"})
public class FormatoSolicitudInmueble extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        String jasperUser = request.getParameter("jasperUser");
        String jasperPassword = request.getParameter("jasperPassword");
        String jasperUrl = request.getParameter("jasperUrl");
        
        Client client = ClientBuilder
                .newClient()
                .register(HttpAuthenticationFeature.basic(jasperUser, jasperPassword));
        try {
            
            InputStream stream = client.target(jasperUrl+"/formato_solicitud_inmueble.pdf?negociacion_id="+id)
                    .request(MediaType.APPLICATION_OCTET_STREAM)
                    .get(InputStream.class);
                        
            byte[] pdfasbytes = IOUtils.toByteArray(stream);
                response.setHeader("Content-disposition", "attachment; filename=formato_solicitud_inmueble.pdf");                
                response.setContentType("application/pdf;charset=UTF-8");                
                response.getOutputStream().write(pdfasbytes);
            
        } catch (IOException ex) {
            System.err.println("error impresion servlet: "+ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
