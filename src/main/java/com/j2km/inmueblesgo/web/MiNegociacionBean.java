package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.configuracion.Constantes;
import com.j2km.inmueblesgo.domain.EstadoNegociacionEntity;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.Kit;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionKit;
import com.j2km.inmueblesgo.domain.NegociacionTerceroEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.EstadoInmuebleRepository;
import com.j2km.inmueblesgo.service.EstadoNegociacionRepository;
import com.j2km.inmueblesgo.service.InmuebleRepository;
import com.j2km.inmueblesgo.service.KitRepository;
import com.j2km.inmueblesgo.service.NegociacionHistoricoRepository;
import com.j2km.inmueblesgo.service.NegociacionKitRepository;
import com.j2km.inmueblesgo.service.NegociacionRepository;
import com.j2km.inmueblesgo.service.NegociacionTerceroRepository;
import com.j2km.inmueblesgo.service.OfertaRepository;
import com.j2km.inmueblesgo.service.PermisoService;
import com.j2km.inmueblesgo.service.PlanPagoRepository;
import com.j2km.inmueblesgo.service.RolService;
import com.j2km.inmueblesgo.service.TerceroRepository;
import com.j2km.inmueblesgo.service.UsuarioRepository;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private EstadoNegociacionEntity estadoNegociacionEntity;

    private List<Kit> kitList;
    private List<Kit> selectedKits;

    private List<TerceroEntity> terceroList;

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
    @Inject
    private KitRepository kitRepository;
    @Inject
    private NegociacionKitRepository negociacionKitRepository;

    public Long getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(Long inmuebleId) {
        this.inmuebleId = inmuebleId;
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
        this.kitList = kitRepository.findByProyecto(this.inmueble.getProyecto());
        this.negociacion = negociacionService.findOptionalByInmueble(this.inmueble);

        if (this.negociacion != null) { //EXISTE LA NEGOCIACION

            this.terceroList = terceroService.findByNegociacion(this.negociacion);
            this.selectedKits = negociacionKitRepository.findByNegociacion(negociacion).stream().map(NegociacionKit::getKit).collect(Collectors.toList());
            this.allPlanPagosListNegociacion = planPagoService.findByNegociacion(this.negociacion);
        } else {
            this.negociacion = new NegociacionEntity();
            this.terceroList = new ArrayList<>();
            this.selectedKits = new ArrayList<>();
            this.allPlanPagosListNegociacion = new ArrayList<>();

            this.negociacion.setFecha(new Date());
            this.negociacion.setInmueble(this.inmueble);
            this.negociacion.setValorMetroCuadrado(this.inmueble.getValorMetroCuadrado());
            this.negociacion.setValorSeparacion(this.inmueble.getValorSeparacion());
            this.negociacion.setValorIncremento(this.inmueble.getIncremento());

            if (this.inmueble.getProyecto().getOferta() != null) {
                this.negociacion.setOferta(this.inmueble.getProyecto().getOferta());
                this.negociacion.setNumeroCuotas(this.negociacion.getOferta().getNumeroCuotas());
                this.negociacion.setPorcentaje(this.negociacion.getOferta().getPorcentaje());
            }

            if (this.negociacion.getValorIncremento() == null) {
                this.negociacion.setValorIncremento(0d);
            }
            this.negociacion.setValorTotal(this.negociacion.getValorIncremento() + (this.negociacion.getValorMetroCuadrado() * this.inmueble.getArea()));
            this.negociacion.setValorPorcentaje((this.negociacion.getValorTotal() * this.negociacion.getPorcentaje()) / 100);
        }

    }

    public void onCambioValorCuota() {
        //Por hacer        
    }

    public void onKitSeleccionado() {
        onCambioOfetra();
    }

    private void calcularValorTotal() {
        this.negociacion.setValorTotal(this.negociacion.getValorIncremento() + (this.negociacion.getValorMetroCuadrado() * this.inmueble.getArea()));

        for (Kit kit : selectedKits) {
            this.negociacion.setValorTotal(this.negociacion.getValorTotal() + kit.getValor());
        }
        this.negociacion.setValorPorcentaje((this.negociacion.getValorTotal() * this.negociacion.getPorcentaje()) / 100);
    }

    public void onCambioOfetra() {

        calcularValorTotal();

        if (this.negociacion.getFechaPrimeraCuota() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Debe seleccionar la fecha Inicial", "Debe seleccionar la fecha Inicial"));
            return;
        }

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

        cal.setTime(this.negociacion.getFechaPrimeraCuota());

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
                cal.roll(Calendar.MONTH, 1);
                entidad.setFechaPactada(cal.getTime());
                entidad.setValorPactado(valorCuotas);
            }
            entidad.setNegociacion(this.negociacion);
            entidad.setNumeroCuota(i);
            i = i + 1;
        }
    }

    public void onGrabarPlanPago() throws IOException {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Negociación guardada con éxito", "Negociación guardada con éxito");

        Double sumaPagos = 0.0;

        for (PlanPagoEntity plan : this.allPlanPagosListNegociacion) {
            sumaPagos = sumaPagos + plan.getValorPactado();
        }

        if (sumaPagos > negociacion.getValorTotal()) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en valores a negociar", "Error en valores a negociar");
        } else {
            this.negociacion.setInmueble(inmueble);
            inmueble.setEstadoInmueble(estadoInmuebleService.findOptionalByNombre(Constantes.INMUEBLE_SEPARADO));
            inmueble = inmuebleService.saveAndFlush(inmueble);

            if (this.negociacion.getId() == null) {
                UsuarioEntity usuario = usuarioService.findOptionalByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
                this.negociacion.setVendedor(usuario);
                this.negociacion.setEstadoNegociacion(estadoNegociacionService.findOptionalByNombre(Constantes.NEGOCIACION_RADICADA));
            }
            negociacionService.save(this.negociacion);

            /* ELIMINAMOS EL PLAN DE PAGO QUE EXISTIERA - OJO VALIDAR SI YA SE HA PAGADO*/
            List<PlanPagoEntity> lista = planPagoService.findByNegociacion(this.negociacion);
            if (lista != null) {
                for (PlanPagoEntity p : lista) {
                    planPagoService.attachAndRemove(p);
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

            List<NegociacionTerceroEntity> listaNegociacionTercero = negociacionTerceroService.findByNegociacion(this.negociacion);
            if (listaNegociacionTercero != null) {
                for (NegociacionTerceroEntity p : listaNegociacionTercero) {
                    negociacionTerceroService.attachAndRemove(p);
                }
            }
            if (this.terceroList != null) {
                NegociacionTerceroEntity negociacionTercero;
                for (TerceroEntity tercero : this.terceroList) {
                    negociacionTercero = new NegociacionTerceroEntity();
                    negociacionTercero.setNegociacion(negociacion);
                    negociacionTercero.setTercero(tercero);
                    negociacionTercero = negociacionTerceroService.save(negociacionTercero);
                }
            }

            List<NegociacionKit> temporalKits = negociacionKitRepository.findByNegociacion(negociacion);

            for (NegociacionKit nk : temporalKits) {
                negociacionKitRepository.attachAndRemove(nk);
            }

            if (this.selectedKits != null) {
                NegociacionKit nk;
                for (Kit kit : this.selectedKits) {
                    nk = new NegociacionKit();
                    nk.setNegociacion(negociacion);
                    nk.setKit(kit);
                    nk = negociacionKitRepository.saveAndFlush(nk);
                }
            }
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("Mensaje", facesMessage);
    }

    public void onCambioFecha() {

        onCambioOfetra();

    }

    public void seleccionarTercero(SelectEvent event) {
        this.terceroList.add((TerceroEntity) event.getObject());
    }

    public void eliminarTercero(TerceroEntity tercero) {
        System.err.println("eliminando");
        this.terceroList.remove(tercero);
    }

    public List<NegociacionTerceroEntity> getAllListNegociacionTercero() {
        return allListNegociacionTercero;
    }

    public void setAllListNegociacionTercero(List<NegociacionTerceroEntity> allListNegociacionTercero) {
        this.allListNegociacionTercero = allListNegociacionTercero;
    }

    public List<Kit> getKitList() {
        return kitList;
    }

    public void setKitList(List<Kit> kitList) {
        this.kitList = kitList;
    }

    public List<Kit> getSelectedKits() {
        return selectedKits;
    }

    public void setSelectedKits(List<Kit> selectedKits) {
        this.selectedKits = selectedKits;
    }

    public List<TerceroEntity> getTerceroList() {
        return terceroList;
    }

    public void setTerceroList(List<TerceroEntity> terceroList) {
        this.terceroList = terceroList;
    }

    public void eliminarCuota(PlanPagoEntity planPago) {
        if ((this.negociacion.getEstadoNegociacion() == null)
                || (this.negociacion.getEstadoNegociacion().getNombre().equals(Constantes.NEGOCIACION_RADICADA))) {
            allPlanPagosListNegociacion.remove(planPago);
        }

    }

}
