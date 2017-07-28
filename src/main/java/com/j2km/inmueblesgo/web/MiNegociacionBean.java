package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.EstadoNegociacionEntity;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionHistoricoEntity;
import com.j2km.inmueblesgo.domain.NegociacionTerceroEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.EstadoInmuebleRepository;
import com.j2km.inmueblesgo.service.EstadoNegociacionRepository;
import com.j2km.inmueblesgo.service.InmuebleRepository;
import com.j2km.inmueblesgo.service.NegociacionHistoricoRepository;
import com.j2km.inmueblesgo.service.NegociacionRepository;
import com.j2km.inmueblesgo.service.NegociacionTerceroRepository;
import com.j2km.inmueblesgo.service.OfertaRepository;
import com.j2km.inmueblesgo.service.PermisoService;
import com.j2km.inmueblesgo.service.PlanPagoRepository;
import com.j2km.inmueblesgo.service.RolService;
import com.j2km.inmueblesgo.service.TerceroRepository;
import com.j2km.inmueblesgo.service.UsuarioRepository;
import com.j2km.inmueblesgo.web.util.MessageFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.security.RolesAllowed;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named("miNegociacionBean")
@ViewScoped
public class MiNegociacionBean implements Serializable {

    private InmuebleEntity inmueble;
    private NegociacionEntity negociacion;

    private List<TerceroEntity> allTerceroList;
    private List<OfertaEntity> allOfertaList;
    private List<PlanPagoEntity> allPlanPagosListNegociacion;
    private Long inmuebleId;
     private NegociacionHistoricoEntity negociacionHistoricoEntity;
    private EstadoNegociacionEntity estadoNegociacionEntity;
    private EstadoNegociacionEntity radicado;

    private List<NegociacionTerceroEntity> allListNegociacionTercero;

    @Inject
    private NegociacionRepository negociacionService;

    @Inject
    private TerceroRepository terceroService;

    @Inject
    private OfertaRepository ofertaService;

    @Inject
    private PlanPagoRepository planPagoService;

    @Inject
    private EstadoInmuebleRepository estadoInmuebleService;

    @Inject
    private InmuebleRepository inmuebleService;

    @Inject
    private NegociacionTerceroRepository negociacionTerceroService;

    @Inject
    private RolService rolService;

    @Inject
    private UsuarioRepository usuarioService;

    @Inject
    private PermisoService permisoService;

    @Inject
    private NegociacionHistoricoRepository negociacionHistoricoService;

    @Inject
    private EstadoNegociacionRepository estadoNegociacionService;

   

    public Long getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(Long inmuebleId) {
        this.inmuebleId = inmuebleId;
    }

    public NegociacionHistoricoEntity getNegociacionHistoricoEntity() {
        return negociacionHistoricoEntity;
    }

    public void setNegociacionHistoricoEntity(NegociacionHistoricoEntity negociacionHistoricoEntity) {
        this.negociacionHistoricoEntity = negociacionHistoricoEntity;
    }

    public InmuebleEntity getInmueble() {        
        return inmueble;
    }

    public void setInmueble(InmuebleEntity inmueble) {
        this.inmueble = inmueble;
    }

    public void setNegociacion(NegociacionEntity negociacion) {
        this.negociacion = negociacion;
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

    public void onLoad() throws IOException {
        this.inmueble = inmuebleService.findBy(inmuebleId);
        this.radicado = estadoNegociacionService.findOptionalByNombre("RADICADO");
        //this.allTerceroList = terceroService.findAllTerceroEntities();
        this.allOfertaList = ofertaService.findAll();

        this.negociacion = negociacionService.findOptionalByInmueble(this.inmueble);
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

            Double porcentaje = 1D;
            
            this.negociacion.setFecha(Calendar.getInstance().getTime());
            this.negociacion.setValorTotal(this.negociacion.getValorMetroCuadrado() * this.inmueble.getArea() + this.negociacion.getValorIncremento());
            if (this.inmueble.getProyecto().getOferta() != null) {
                this.negociacion.setOferta(this.inmueble.getProyecto().getOferta());
                this.negociacion.setNumeroCuotas(this.negociacion.getOferta().getNumeroCuotas());
                this.negociacion.setPorcentaje(this.inmueble.getProyecto().getOferta().getPorcentaje());
                porcentaje = this.negociacion.getOferta().getPorcentaje();
            }

            allPlanPagosListNegociacion = new ArrayList<PlanPagoEntity>();
            Double inicial = this.negociacion.getValorSeparacion();
            Double valorInmueble = this.inmueble.getValorTotal();
            
            Double valorPorcentaje = (valorInmueble * porcentaje) / 100;
            /*Double valorRestante = valorPorcentaje - inicial;

            int numeroCuotas = this.negociacion.getOferta().getNumeroCuotas();

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

            this.negociacionHistoricoEntity = new NegociacionHistoricoEntity();
            this.negociacionHistoricoEntity.setEstadoNegociacion(radicado);
            
            */

        } else {
            // SE REPITE CON LO DE SI ES IGUAL A NULL
            this.allPlanPagosListNegociacion = planPagoService.findByNegociacion(this.negociacion);
            this.allListNegociacionTercero = negociacionTerceroService.findByNegociacion(this.negociacion);
            this.negociacionHistoricoEntity
                    = negociacionHistoricoService.findByNegociacionAndEstado(this.negociacion, radicado);

        }
        //return ruta;

    }
/*
    public boolean informacionUsuario() {
        boolean respuesta = false;
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        Principal userPrincipal = request.getUserPrincipal();
        List<RolEntity> allRoles = new ArrayList();
        allRoles = rolService.findAllRolEntities();
        List<PermisoEntity> userRoles = new ArrayList();
        UsuarioEntity usuario = usuarioService.findByLogin(userPrincipal.getName());

        for (RolEntity role : allRoles) {
            if (request.isUserInRole(role.getNombre())) {
                userRoles.add(permisoService.findByUsuarioAndRol(usuario, role));
            }
        }

        for (PermisoEntity permiso : userRoles) {
            String nombreRol = permiso.getRol().getNombre();

            if (nombreRol.equals("ADMIN")) {
                respuesta = true;
            }
        }

        return respuesta;
    }
*/
    public void onLoadView() {

        if (this.negociacion == null) {
            // REDIRECT PARA ALGUN LADO
            System.err.println("NO TRAE NEGOCIACION....");
        } else {

            this.inmueble = this.negociacion.getInmueble();
            this.allPlanPagosListNegociacion = planPagoService.findByNegociacion(this.negociacion);
            this.allListNegociacionTercero = negociacionTerceroService.findByNegociacion(this.negociacion);
        }
    }
    
    public void onCambioValorCuota(){
        int i = 0;
        Double valorSumadoCuotas = 0.0;
        
        for (PlanPagoEntity entidad : allPlanPagosListNegociacion) {
            if (allPlanPagosListNegociacion.get(0) == entidad){
                if(!entidad.getValorPactado().equals(negociacion.getValorSeparacion())){
                    negociacion.setValorSeparacion(entidad.getValorPactado());
                }
            }
            valorSumadoCuotas = valorSumadoCuotas + entidad.getValorPactado();
        }
        
        
        Double porcentaje = 0.0;
       
        System.err.println(valorSumadoCuotas.intValue());
        System.err.println(negociacion.getValorTotal().intValue());
        porcentaje = (valorSumadoCuotas * 100) / negociacion.getValorTotal();
        System.err.println(porcentaje.intValue());
        negociacion.setPorcentaje(porcentaje);
        
    }

    public void onCambioOfetra() {

        this.valorTotal();

        allPlanPagosListNegociacion = new ArrayList<PlanPagoEntity>();
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

    public String onGrabarPlanPago() throws IOException {
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
                UsuarioEntity vendedor = usuarioService.findOptionalByLogin(
                        FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
                this.negociacion.setVendedor(vendedor);
                this.negociacion.setEstadoNegociacion(radicado);

                negociacionService.save(this.negociacion);

                this.negociacionHistoricoEntity.setNegociacion(this.negociacion);
                this.negociacionHistoricoEntity.setFecha(new java.util.Date());
                this.negociacionHistoricoEntity.setCreadoPor(vendedor);
                negociacionHistoricoService.save(this.negociacionHistoricoEntity);

            } else {
                negociacionService.save(this.negociacion);
                negociacionHistoricoService.save(this.negociacionHistoricoEntity);
            }

            /* ELIMINAMOS EL PLAN DE PAGO QUE EXISTIERA - OJO VALIDAR SI YA SE HA PAGADO*/
            List<PlanPagoEntity> lista = planPagoService.findByNegociacion(this.negociacion);
            if (lista != null) {
                for (PlanPagoEntity p : lista) {
                    planPagoService.remove(p);
                }
            }

            for (PlanPagoEntity plan : this.allPlanPagosListNegociacion) {
                if (plan.getValorPactado() != null || !plan.getValorPactado().isNaN()) {
                    sumaPagos = sumaPagos + plan.getValorPactado();
                    if (plan.getValorPactado() > 0) {
                        if (plan.getId() == null) {
                            planPagoService.save(plan);
                        } else {
                            planPagoService.save(plan);
                        }
                    }
                }
            }
            this.allPlanPagosListNegociacion = planPagoService.findByNegociacion(this.negociacion);
            EstadoInmuebleEntity estadoInmueble = estadoInmuebleService.findOptionalByNombre("SEPARADO");
            if (estadoInmueble != null) {
                this.negociacion.getInmueble().setEstadoInmueble(estadoInmueble);
                inmuebleService.save(this.negociacion.getInmueble());
            }

            /* GRABACION DE LOS TERCEROS.....
            /* ELIMINAMOS TERCEROS QUE EXISTIERA */
            List<NegociacionTerceroEntity> listaNegociacionTercero = negociacionTerceroService.findByNegociacion(this.negociacion);
            if (listaNegociacionTercero != null) {
                for (NegociacionTerceroEntity p : listaNegociacionTercero) {
                    negociacionTerceroService.remove(p);
                }
            }
            if (this.allListNegociacionTercero != null) {
                for (NegociacionTerceroEntity negociacionTercero : this.allListNegociacionTercero) {
                    if (negociacionTercero.getId() == null) {
                        if (negociacionTercero.getNegociacion() == null) {

                            negociacionTercero.setNegociacion(this.negociacion);
                        }
                        negociacionTerceroService.save(negociacionTercero);
                    } else {
                        negociacionTerceroService.save(negociacionTercero);
                    }

                }
            }

            //message = "message_successfully_created";
        }

        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("Mensaje", facesMessage);        
        return "/pages/vendedor/negociacionView.xhtml?faces-redirect=true&id="+ this.negociacion.getId().toString();
        
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

    public void seleccionarTercero(SelectEvent event) {
        System.err.println("REGRESO DEL DIALOGO");
        TerceroEntity terceroSel = (TerceroEntity) event.getObject();
        boolean puedeAgregarTercero = true;
        System.err.println("TERCERO SEL" + terceroSel);
        if (allListNegociacionTercero == null) {
            System.err.println("LISTA VACIA");
            allListNegociacionTercero = new ArrayList<NegociacionTerceroEntity>();
        } else {
            for (NegociacionTerceroEntity negociacionTerceroInstance : allListNegociacionTercero) {
                System.err.println("LISTA CON INFORMACION");
                if (negociacionTerceroInstance.getTercero().equals(terceroSel)) {
                    puedeAgregarTercero = false;
                }
            }
        }

        // SE VERIFICA QUE YA NO EXISTA EN LA LISTA DE TERCEROS
        if (puedeAgregarTercero) {
            System.err.println("EL SELECCIONADO NO ESTA EN LA LISTA ACTUAL");
            List<NegociacionTerceroEntity> negociacionTerceroListInstance = null;
            if (this.negociacion != null) {
                if (this.negociacion.getId() != null) {
                    negociacionTerceroListInstance = negociacionTerceroService.findByNegociacionAndTercero(this.negociacion, terceroSel);
                }
            }
            if (negociacionTerceroListInstance == null) {
                NegociacionTerceroEntity negociacionTerceroInstance = new NegociacionTerceroEntity();
                System.err.println("AGREGANDO LA NEGOCIACION");
                if (this.negociacion != null) {
                    negociacionTerceroInstance.setNegociacion(this.negociacion);
                }
                negociacionTerceroInstance.setTercero(terceroSel);
                allListNegociacionTercero.add(negociacionTerceroInstance);
            }
            System.err.println("TODA LA NEGOCIACION" + allListNegociacionTercero.size());

            // RELLENAR LA LISTA DE TERCEROS
        }

    }

    public List<NegociacionTerceroEntity> getAllListNegociacionTercero() {
        return allListNegociacionTercero;
    }

    public void setAllListNegociacionTercero(List<NegociacionTerceroEntity> allListNegociacionTercero) {
        this.allListNegociacionTercero = allListNegociacionTercero;
    }

}
