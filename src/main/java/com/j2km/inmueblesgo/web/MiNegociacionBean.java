package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.service.NegociacionService;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("miNegociacionBean")
@ViewScoped
public class MiNegociacionBean implements Serializable {

    private InmuebleEntity inmueble;
    private NegociacionEntity negociacion;
    
    private NegociacionService negociacionService;

    /**
     * **********************
     */
    public InmuebleEntity getInmueble() {
        return inmueble;
    }

    public void setInmueble(InmuebleEntity inmueble) {
        this.inmueble = inmueble;
    }

    public NegociacionEntity getNegociacion() {
        return negociacion;
    }
    
    
    
    
    /*******************/
    
    public void onLoad(){
        
        System.out.println("ONLOAD...");
        
        this.negociacion = null;
        
        //this.negociacion = negociacionService.findByInmueble(inmueble);
        if (this.negociacion == null){
            this.negociacion = new NegociacionEntity();
        }
        
    }

}
