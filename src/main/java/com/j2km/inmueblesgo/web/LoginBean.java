package com.j2km.inmueblesgo.web;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named("loginBean")
@ViewScoped
public class LoginBean implements Serializable {
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/pages/main.xhtml?faces-redirect=true";        
    }  
}
