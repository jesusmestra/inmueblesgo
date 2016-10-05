package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.service.EstadoInmuebleService;
import com.j2km.inmueblesgo.service.InmuebleService;
import com.j2km.inmueblesgo.service.NegociacionService;
import com.j2km.inmueblesgo.service.OfertaService;
import com.j2km.inmueblesgo.service.PlanPagoService;
import com.j2km.inmueblesgo.service.TerceroService;
import com.j2km.inmueblesgo.web.util.MessageFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("miNegociacionBean")
@ViewScoped
public class MiNegociacionBean implements Serializable {

    private InmuebleEntity inmueble;
    private NegociacionEntity negociacion;

    private List<TerceroEntity> allTerceroList;
    private List<OfertaEntity> allOfertaList;
    private List<PlanPagoEntity> allPlanPagosListNegociacion;

    @Inject
    private NegociacionService negociacionService;

    @Inject
    private TerceroService terceroService;

    @Inject
    private OfertaService ofertaService;

    @Inject
    private PlanPagoService planPagoService;

    @Inject
    private EstadoInmuebleService estadoInmuebleService;

    @Inject
    private InmuebleService inmuebleService;

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

    public List<TerceroEntity> getAllTerceroList() {
        return allTerceroList;
    }

    public void setAllTerceroList(List<TerceroEntity> allTerceroList) {
        this.allTerceroList = allTerceroList;
    }

    public List<OfertaEntity> getAllOfertaList() {
        return allOfertaList;
    }

    public void setAllOfertaList(List<OfertaEntity> allOfertaList) {
        this.allOfertaList = allOfertaList;
    }

    public List<PlanPagoEntity> getAllPlanPagosListNegociacion() {
        return allPlanPagosListNegociacion;
    }

    public void setAllPlanPagosListNegociacion(List<PlanPagoEntity> allPlanPagosListNegociacion) {
        this.allPlanPagosListNegociacion = allPlanPagosListNegociacion;
    }

    /**
     * ****************
     */
    public void onLoad() {

        this.allTerceroList = terceroService.findAllTerceroEntities();
        this.allOfertaList = ofertaService.findAllOfertaEntities();

        this.negociacion = negociacionService.findByInmueble(this.inmueble);

        System.out.println("Iniciando");
        if (this.negociacion == null) {
            this.negociacion = new NegociacionEntity();
            this.negociacion.setInmueble(this.inmueble);
            
            this.negociacion.setValorMetroCuadrado(this.inmueble.getValorMetroCuadrado());
            this.negociacion.setValorSeparacion(this.inmueble.getValorSeparacion());

            if (this.inmueble.getIncremento() != null) {
                this.negociacion.setValorIncremento(this.inmueble.getIncremento());
            } else {
                this.negociacion.setValorIncremento(0D);
            }

            this.negociacion.setFecha(Calendar.getInstance().getTime());
            this.negociacion.setValorTotal(this.negociacion.getValorMetroCuadrado() * this.inmueble.getArea() + this.negociacion.getValorIncremento());
            if (this.inmueble.getProyecto().getOferta() != null) {
                this.negociacion.setOferta(this.inmueble.getProyecto().getOferta());
                this.negociacion.setNumeroCuotas(this.negociacion.getOferta().getNumeroCuotas());
                this.negociacion.setPorcentaje(this.inmueble.getProyecto().getOferta().getPorcentaje());
            }

            allPlanPagosListNegociacion = new ArrayList<PlanPagoEntity>();
            System.out.println("creada la negociacion.....");
            Double inicial =  this.negociacion.getValorSeparacion();
            Double valorInmueble = this.inmueble.getValorTotal();
            Double porcentaje = this.negociacion.getOferta().getPorcentaje();
            Double valorPorcentaje = (valorInmueble * porcentaje) / 100;
            Double valorRestante = valorPorcentaje - inicial;

            int numeroCuotas = this.negociacion.getOferta().getNumeroCuotas();

            Double valorCuotas = 0.0;
            if (numeroCuotas > 0) {
                valorCuotas = (valorRestante / numeroCuotas);
            }

            numeroCuotas = numeroCuotas + 1;

            Calendar cal = Calendar.getInstance();
            System.out.println("entrada....." + this.negociacion);
            System.out.println("fecha sel....." + this.negociacion.getFecha());
            System.out.println("inmueble....." + this.negociacion.getInmueble());
            cal.setTime(this.negociacion.getFecha());

            for (int i = allPlanPagosListNegociacion.size(); i < numeroCuotas; i++) {
                PlanPagoEntity entidad = new PlanPagoEntity();
                allPlanPagosListNegociacion.add(entidad);
            }

            int i = 0;
            for (PlanPagoEntity entidad : allPlanPagosListNegociacion) {
                if (i == 0) {
                    entidad.setValorPactado(inicial);
                    entidad.setFechaPactada(cal.getTime());
                } else {
                    cal.add(Calendar.MONTH, 1);
                    entidad.setFechaPactada(cal.getTime());
                    entidad.setValorPactado(valorCuotas);
                }
                entidad.setNegociacion(this.negociacion);
                entidad.setNumeroCuota(i);
                i = i + 1;
            }
        } else {
            this.allPlanPagosListNegociacion = planPagoService.findAllPlanPagoByNegociacion(this.negociacion);
        }

    }

    public void onCambioOfetra() {
        System.out.println("Cambio oferta.....");
        
        this.valorTotal();

        allPlanPagosListNegociacion = new ArrayList<PlanPagoEntity>();
        System.out.println("creada la negociacion.....");
        Double inicial = this.negociacion.getValorSeparacion();
        Double valorInmueble = this.negociacion.getValorTotal();
        Double porcentaje = this.negociacion.getPorcentaje();
        Double valorPorcentaje = (valorInmueble * porcentaje) / 100;
        Double valorRestante = valorPorcentaje - inicial;

        int numeroCuotas = this.negociacion.getNumeroCuotas();

        Double valorCuotas = 0.0;
        if (numeroCuotas > 0) {
            valorCuotas = (valorRestante / numeroCuotas);
        }

        numeroCuotas = numeroCuotas + 1;

        Calendar cal = Calendar.getInstance();        
        cal.setTime(this.negociacion.getFecha());

        for (int i = allPlanPagosListNegociacion.size(); i < numeroCuotas; i++) {
            PlanPagoEntity entidad = new PlanPagoEntity();
            allPlanPagosListNegociacion.add(entidad);
        }

        int i = 0;
        for (PlanPagoEntity entidad : allPlanPagosListNegociacion) {
            if (i == 0) {
                entidad.setValorPactado(inicial);
                entidad.setFechaPactada(cal.getTime());
            } else {
                cal.add(Calendar.MONTH, 1);
                entidad.setFechaPactada(cal.getTime());
                entidad.setValorPactado(valorCuotas);
            }
            entidad.setNegociacion(this.negociacion);
            entidad.setNumeroCuota(i);
            i = i + 1;
        }
    }

    public String onGrabarPlanPago() {

        String message;
        message = "message_successfully_created";
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, MessageFactory.getMessageString(message), null);

        Double sumaPagos = 0.0;

        for (PlanPagoEntity plan : this.allPlanPagosListNegociacion) {
            sumaPagos = sumaPagos + plan.getValorPactado();
        }

        if (sumaPagos > inmueble.getValorTotal()) {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Datos no grabados"));
            // ERORRO
            message = "message_error_suma_pago_pactado";
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageFactory.getMessageString(message), null);
        } else {

            this.negociacion.setInmueble(inmueble);
            if (this.negociacion.getId() == null) {
                negociacionService.save(this.negociacion);
            } else {
                negociacionService.update(this.negociacion);
            }

            /* ELIMINAMOS EL PLAN DE PAGO QUE EXISTIERA - OJO VALIDAR SI YA SE HA PAGADO*/
            List<PlanPagoEntity> lista = planPagoService.findAllPlanPagoByNegociacion(this.negociacion);
            if (lista != null) {
                for (PlanPagoEntity p : lista) {
                    planPagoService.delete(p);
                }
            }

            for (PlanPagoEntity plan : this.allPlanPagosListNegociacion) {
                sumaPagos = sumaPagos + plan.getValorPactado();
                if (plan.getValorPactado() > 0) {
                    if (plan.getId() == null) {
                        planPagoService.save(plan);
                    } else {
                        planPagoService.update(plan);
                    }
                }
            }

            this.allPlanPagosListNegociacion = planPagoService.findAllPlanPagoByNegociacion(this.negociacion);
            EstadoInmuebleEntity estadoInmueble = estadoInmuebleService.findByNombre("Separado");
            if (estadoInmueble != null) {
                System.out.println("Actualizando estado inmueble");
                this.negociacion.getInmueble().setEstadoInmueble(estadoInmueble);
                inmuebleService.update(this.negociacion.getInmueble());
            }

            message = "message_successfully_created";
        }

        FacesContext.getCurrentInstance().addMessage("Mensaje", facesMessage);
        return null;
    }

    public void onCambioFecha() {

        if (allPlanPagosListNegociacion != null) {

            Calendar cal = new GregorianCalendar();
            cal.setTime(this.negociacion.getFecha());
            int i = 0;

            for (PlanPagoEntity entidad : this.allPlanPagosListNegociacion) {
                if (entidad != null) {
                    if (i == 0) {
                        entidad.setFechaPactada(cal.getTime());
                    } else {
                        cal.add(Calendar.DAY_OF_YEAR, 30);
                        entidad.setFechaPactada(cal.getTime());
                    }
                    i = i + 1;
                }
            }

        }
    }

    public void onAgregarFilaPago() {
        PlanPagoEntity entidad = new PlanPagoEntity();
        entidad.setNegociacion(this.negociacion);
        this.allPlanPagosListNegociacion.add(entidad);

    }

    public void valorTotal() {
        this.negociacion.setValorTotal(this.negociacion.getValorIncremento() + (this.negociacion.getValorMetroCuadrado() * this.inmueble.getArea()));
    }

}
