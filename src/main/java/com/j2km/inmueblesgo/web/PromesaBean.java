package com.j2km.inmueblesgo.web;


import com.j2km.inmueblesgo.service.PromesaService;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("promesaBean")
@ViewScoped
public class PromesaBean implements Serializable {

    @Inject private PromesaService promesaService;
    
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(PromesaBean.class.getName());
    
    public void generar(){
        //promesaService.generar();
    }

}
