package com.j2km.inmueblesgo.web;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Named("loginBean")
@ViewScoped
public class LoginBean implements Serializable {
    
    public void logout() throws IOException, ServletException{
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();        
        
        try {
            request.logout();
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath());             
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Wylogowywanie nie powiodło się"));
        }
    }  
    
    public void informacionSession(FacesContext facesContext){

    }   
}
