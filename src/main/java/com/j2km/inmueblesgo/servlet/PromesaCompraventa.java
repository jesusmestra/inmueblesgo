/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.servlet;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.service.NegociacionRepository;
import com.j2km.inmueblesgo.service.PromesaService;
import com.j2km.inmueblesgo.service.TerceroRepository;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.inject.Inject;
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

@WebServlet(name = "PromesaCompraventa", urlPatterns = {"/PromesaCompraventa"})
public class PromesaCompraventa extends HttpServlet {
    
    @Inject private NegociacionRepository nr;   
    @Inject private PromesaService ps;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        NegociacionEntity negociacion =  nr.findBy(Long.parseLong(id));      
        
        response.setHeader("Content-disposition", "attachment; filename=promesa_compraventa"+id+".docx");                
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");                
        response.getOutputStream().write(ps.generar(negociacion));
            
        
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
