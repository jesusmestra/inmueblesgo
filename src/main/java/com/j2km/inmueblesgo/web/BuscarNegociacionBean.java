package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.NegociacionTerceroEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.service.NegociacionTerceroRepository;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named("buscarNegociacionBean")
@ViewScoped
public class BuscarNegociacionBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(BuscarNegociacionBean.class.getName());
    
    @Inject private NegociacionTerceroRepository negociacionTerceroRepository;
    
    //tercero utilizado para buscar las negociaciones por tercero
    private TerceroEntity tercero;
    private List<NegociacionTerceroEntity> negociacionList;

    public void inicio(){
        tercero = new TerceroEntity();
    }
    public void seleccionarTercero(SelectEvent event) {
        tercero = (TerceroEntity) event.getObject();
        negociacionList = negociacionTerceroRepository.findByTercero(tercero);    
    }

    public TerceroEntity getTercero() {
        return tercero;
    }

    public void setTercero(TerceroEntity tercero) {
        this.tercero = tercero;
    }

    public List<NegociacionTerceroEntity> getNegociacionList() {
        return negociacionList;
    }

    public void setNegociacionList(List<NegociacionTerceroEntity> negociacionList) {
        this.negociacionList = negociacionList;
    }

}
