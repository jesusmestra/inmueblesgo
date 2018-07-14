package com.j2km.inmueblesgo.configuracion;

import com.j2km.inmueblesgo.domain.Configuracion;
import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.EstadoNegociacionEntity;
import com.j2km.inmueblesgo.domain.EstadoProyectoEntity;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.PermisoEntity;
import com.j2km.inmueblesgo.domain.RolEntity;
import com.j2km.inmueblesgo.domain.TipoFuenteInformacionEntity;
import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;
import com.j2km.inmueblesgo.domain.TipoPropiedad;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.ConfiguracionRepository;
import com.j2km.inmueblesgo.service.EmpresaRepository;
import com.j2km.inmueblesgo.service.EncriptarService;
import com.j2km.inmueblesgo.service.EstadoInmuebleRepository;
import com.j2km.inmueblesgo.service.EstadoNegociacionRepository;
import com.j2km.inmueblesgo.service.EstadoProyectoRepository;
import com.j2km.inmueblesgo.service.InmuebleRepository;
import com.j2km.inmueblesgo.service.OfertaRepository;
import com.j2km.inmueblesgo.service.PermisoRepository;
import com.j2km.inmueblesgo.service.ProyectoRepository;
import com.j2km.inmueblesgo.service.RolRepository;
import com.j2km.inmueblesgo.service.TerceroRepository;
import com.j2km.inmueblesgo.service.TipoFuenteInformacionRepository;
import com.j2km.inmueblesgo.service.TipoIdentificacionRepository;
import com.j2km.inmueblesgo.service.TipoPlantaDetalleRepository;
import com.j2km.inmueblesgo.service.TipoPropiedadRepository;
import com.j2km.inmueblesgo.service.UsuarioRepository;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;


@Startup
@Singleton
public class Inicio implements Serializable{
    
    //private static org.slf4j.Logger logger = LoggerFactory.getLogger(Inicio.class);
 
    @Inject private UsuarioRepository usuarioService;
    @Inject private RolRepository rolService;
    @Inject private PermisoRepository permisoService;
    @Inject private TipoIdentificacionRepository tipoIdentificacionService;
    @Inject private TerceroRepository terceroService;
    @Inject private EmpresaRepository empresaService;
    @Inject private ProyectoRepository proyectoService;
    @Inject private OfertaRepository ofertaService;
    @Inject private EstadoInmuebleRepository estadoInmuebleService;
    @Inject private EstadoNegociacionRepository estadoNegociacionService;
    @Inject private EstadoProyectoRepository estadoProyectoService;
    @Inject private TipoFuenteInformacionRepository tipoFuenteInformacionService;
    @Inject private EncriptarService encriptarService;
    @Inject private ConfiguracionRepository configuracionRepository;
    @Inject private InmuebleRepository inmuebleRepository;
    @Inject private TipoPlantaDetalleRepository tipoPlantaDetalleRepository;
    @Inject private TipoPropiedadRepository tipoPropiedadRepository;

    private void iniciarTipoFuenteInformacion() {
        String[] fuentes = {
            Constantes.TFI_VALLA,
            Constantes.TFI_PERIODICO,
            Constantes.TFI_PAGINA_WEB,
            Constantes.TFI_REDES_SOCIALES,
            Constantes.TFI_REFERIDO,
            Constantes.TFI_OTRA
        };

        TipoFuenteInformacionEntity tfi;

        for (String fuente : fuentes) {
            tfi = tipoFuenteInformacionService.findOptionalByNombre(fuente);

            if (tfi == null) {
                tfi = new TipoFuenteInformacionEntity();
                tfi.setNombre(fuente);
                tfi = tipoFuenteInformacionService.saveAndFlush(tfi);
            }
        }
    }

    private void iniciarTiposIdentificacion() {
        String[] nombres = {
            Constantes.TI_NIT,
            Constantes.TI_CEDULA,
            Constantes.TI_PASAPORTE
        };
        String[] abreviaturas = {
            Constantes.TI_NIT_ABREVIATURA,
            Constantes.TI_CEDULA_ABREVIATURA,
            Constantes.TI_PASAPORTE_ABREVIATURA
        };

        TipoIdentificacionEntity tipoIdentificacionEntity;
        for (int i = 0; i < nombres.length; i++) {
            tipoIdentificacionEntity = tipoIdentificacionService.findOptionalByNombre(nombres[i]);

            if (tipoIdentificacionEntity == null) {
                tipoIdentificacionEntity = new TipoIdentificacionEntity();
                tipoIdentificacionEntity.setAbreviatura(abreviaturas[i]);
                tipoIdentificacionEntity.setNombre(nombres[i]);
                tipoIdentificacionEntity = tipoIdentificacionService.saveAndFlush(tipoIdentificacionEntity);
            }
        }
    }

    private void iniciarEstadosNegociacion() {
        String[] estados = {
            Constantes.NEGOCIACION_RADICADA,
            Constantes.NEGOCIACION_APROBADA,
            Constantes.NEGOCIACION_RECHAZADA,
            Constantes.NEGOCIACION_ANULADA,
            Constantes.NEGOCIACION_FINALIZADA
        };

        String[] codigos = {"01", "02", "03", "04", "05"};

        EstadoNegociacionEntity estadoNegociacion;
        int i = 0;
        for (String estado : estados) {
            estadoNegociacion = estadoNegociacionService.findOptionalByNombre(estado);

            if (estadoNegociacion == null) {
                estadoNegociacion = new EstadoNegociacionEntity();
                estadoNegociacion.setCodigo(codigos[i]);
                estadoNegociacion.setNombre(estado);
                estadoNegociacionService.save(estadoNegociacion);
            }
            i++;
        }
    }

    private void iniciarEstadosProyecto() {
        String[] estados = {Constantes.PROYECTO_ACTIVO,
            Constantes.PROYECTO_INACTIVO};

        String[] codigos = {"01", "02"};

        EstadoProyectoEntity estadoProyecto;
        int i = 0;
        for (String estado : estados) {
            estadoProyecto = estadoProyectoService.findOptionalByNombre(estado);

            if (estadoProyecto == null) {
                estadoProyecto = new EstadoProyectoEntity();
                estadoProyecto.setCodigo(codigos[i]);
                estadoProyecto.setNombre(estado);
                estadoProyectoService.save(estadoProyecto);
            }
            i++;
        }
    }

    private void iniciarEstadosInmuebles() {
        String[] estados = {
            Constantes.INMUEBLE_DISPONIBLE,
            Constantes.INMUEBLE_SEPARADO,
            Constantes.INMUEBLE_VENDIDO
        };

        String[] codigos = {"01", "02", "03"};

        EstadoInmuebleEntity estadoInmueble;
        int i = 0;
        for (String estado : estados) {
            estadoInmueble = estadoInmuebleService.findOptionalByNombre(estado);

            if (estadoInmueble == null) {
                estadoInmueble = new EstadoInmuebleEntity();
                estadoInmueble.setCodigo(codigos[i]);
                estadoInmueble.setNombre(estado);
                estadoInmuebleService.save(estadoInmueble);
            }
            i++;
        }
    }

    private void iniciarSeguridad() {

        String[] roles = {
            Constantes.ROLE_ADMIN,
            Constantes.ROLE_VENDEDOR,
            Constantes.ROLE_GERENTE
        };

        UsuarioEntity usuario = usuarioService.findOptionalByLogin("admin");

        if (usuario == null) {
            usuario = new UsuarioEntity();
            usuario.setLogin("admin");
            usuario.setActivo(Boolean.TRUE);
            usuario.setPassword(encriptarService.encriptar("admin"));
            usuario = usuarioService.saveAndFlush(usuario);
        }

        RolEntity rolEntity;
        PermisoEntity permiso;

        for (String rol : roles) {
            rolEntity = rolService.findOptionalByNombre(rol);
            if (rolEntity == null) {
                rolEntity = new RolEntity();
                rolEntity.setNombre(rol);
                rolEntity = rolService.saveAndFlush(rolEntity);
            }
            permiso = permisoService.findOptionalByUsuarioAndRol(usuario, rolEntity);

            if (permiso == null) {
                permiso = new PermisoEntity();
                permiso.setUsuario(usuario);
                permiso.setRol(rolEntity);
                permisoService.save(permiso);
            }
        }        
    }
    
    private void asigarDetalleTipoPlanta(){
        List<InmuebleEntity> inmuebleList = inmuebleRepository.findAll();
        
        for(InmuebleEntity inmueble: inmuebleList){
            
        }
    }
    
    private void iniciarConfiguracion(){
        Configuracion configuracion;
        if(configuracionRepository.count() == 0){
            configuracion = new Configuracion();
            configuracion.setUrlReporte("http://localhost:8080/jasperserver/rest_v2/reports/inmuebles");
            configuracion.setUsuarioReporte("jasperadmin");
            configuracion.setPasswordReporte("jasperadmin");
            configuracion = configuracionRepository.saveAndFlush(configuracion);
        }        
    }

    private void iniciarLogs(){       
       //por hacer
    }
    
    private void iniciarTipoPropiedad(){
        TipoPropiedad tp = tipoPropiedadRepository.findOptionalByDescripcion("CASA");
        if(tp == null){
            tp = new TipoPropiedad();
            tp.setDescripcion("CASA");
            tipoPropiedadRepository.saveAndFlush(tp);
        }
        
        tp = tipoPropiedadRepository.findOptionalByDescripcion("APARTAMENTO");
        if(tp == null){
            tp = new TipoPropiedad();
            tp.setDescripcion("APARTAMENTO");
            tipoPropiedadRepository.saveAndFlush(tp);
        }
    }
    
    @PostConstruct
    public void iniciar() {        
        iniciarLogs();
        iniciarTipoPropiedad();
        iniciarEstadosProyecto();
        iniciarEstadosInmuebles();
        iniciarSeguridad();
        iniciarTiposIdentificacion();
        iniciarTipoFuenteInformacion();
        iniciarEstadosNegociacion();
        iniciarConfiguracion();
    }
}
