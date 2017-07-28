package com.j2km.inmueblesgo.configuracion;

import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.EstadoNegociacionEntity;
import com.j2km.inmueblesgo.domain.EstadoProyectoEntity;
import com.j2km.inmueblesgo.domain.PermisoEntity;
import com.j2km.inmueblesgo.domain.RolEntity;
import com.j2km.inmueblesgo.domain.TipoFuenteInformacionEntity;
import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.EmpresaRepository;
import com.j2km.inmueblesgo.service.EstadoInmuebleRepository;
import com.j2km.inmueblesgo.service.EstadoNegociacionRepository;
import com.j2km.inmueblesgo.service.EstadoProyectoRepository;
import com.j2km.inmueblesgo.service.OfertaRepository;
import com.j2km.inmueblesgo.service.PermisoRepository;
import com.j2km.inmueblesgo.service.ProyectoRepository;
import com.j2km.inmueblesgo.service.RolRepository;
import com.j2km.inmueblesgo.service.TerceroRepository;
import com.j2km.inmueblesgo.service.TipoFuenteInformacionRepository;
import com.j2km.inmueblesgo.service.TipoIdentificacionRepository;
import com.j2km.inmueblesgo.service.UsuarioRepository;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class Inicio {

    @Inject
    private UsuarioRepository usuarioService;

    @Inject
    private RolRepository rolService;

    @Inject
    private PermisoRepository permisoService;

    @Inject
    private TipoIdentificacionRepository tipoIdentificacionService;

    @Inject
    private TerceroRepository terceroService;

    @Inject
    private EmpresaRepository empresaService;

    @Inject
    private ProyectoRepository proyectoService;

    @Inject
    private OfertaRepository ofertaService;

    @Inject
    private EstadoInmuebleRepository estadoInmuebleService;

    @Inject
    private EstadoNegociacionRepository estadoNegociacionService;

    @Inject
    private EstadoProyectoRepository estadoProyectoService;
    
    @Inject
    private TipoFuenteInformacionRepository tipoFuenteInformacionService;
    
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
            
            if(tfi == null){
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
            Constantes.ROLE_VENDEDOR
        };

        RolEntity rolEntity;

        for (String rol : roles) {
            rolEntity = rolService.findOptionalByNombre(rol);
            if (rolEntity == null) {
                rolEntity = new RolEntity();
                rolEntity.setNombre(rol);
                rolService.save(rolEntity);
            }
        }

        UsuarioEntity usuario = usuarioService.findOptionalByLogin("admin");

        if (usuario == null) {
            usuario = new UsuarioEntity();
            usuario.setLogin("admin");
            usuario.setPassword("admin");
            usuario = usuarioService.save(usuario);
        }

        UsuarioEntity usuarioVendedor = usuarioService.findOptionalByLogin("vendedor");

        if (usuarioVendedor == null) {
            usuarioVendedor = new UsuarioEntity();
            usuarioVendedor.setLogin("vendedor");
            usuarioVendedor.setPassword("vendedor");
            usuarioVendedor = usuarioService.save(usuarioVendedor);
        }

        RolEntity rolAdmin = rolService.findOptionalByNombre(Constantes.ROLE_ADMIN);
        PermisoEntity permiso = permisoService.findOptionalByUsuarioAndRol(usuario, rolAdmin);
        if (permiso == null) {
            permiso = new PermisoEntity();
            permiso.setUsuario(usuario);
            permiso.setRol(rolAdmin);
            permisoService.save(permiso);
        }

        RolEntity rolVendedor = rolService.findOptionalByNombre(Constantes.ROLE_VENDEDOR);

        PermisoEntity permisoVendedor = permisoService.findOptionalByUsuarioAndRol(usuarioVendedor, rolVendedor);

        if (permisoVendedor == null) {
            permisoVendedor = new PermisoEntity();
            permisoVendedor.setUsuario(usuarioVendedor);
            permisoVendedor.setRol(rolVendedor);
            permisoService.save(permisoVendedor);
        }
    }

    @PostConstruct
    public void iniciar() {

        iniciarEstadosProyecto();
        iniciarEstadosInmuebles();
        iniciarSeguridad();
        iniciarTiposIdentificacion();
        iniciarTipoFuenteInformacion();

        /*EstadoNegociacionEntity estadoNegociacion = estadoNegociacionService.findByCodigo("01");

        if (estadoNegociacion == null) {
            estadoNegociacion = new EstadoNegociacionEntity();
            estadoNegociacion.setCodigo("01");
            estadoNegociacion.setNombre("Radicado");
            estadoNegociacionService.save(estadoNegociacion);
        }

        estadoNegociacion = estadoNegociacionService.findByCodigo("02");

        if (estadoNegociacion == null) {
            estadoNegociacion = new EstadoNegociacionEntity();
            estadoNegociacion.setCodigo("02");
            estadoNegociacion.setNombre("Aprobado");
            estadoNegociacionService.save(estadoNegociacion);
        }

        estadoNegociacion = estadoNegociacionService.findByCodigo("03");

        if (estadoNegociacion == null) {
            estadoNegociacion = new EstadoNegociacionEntity();
            estadoNegociacion.setCodigo("03");
            estadoNegociacion.setNombre("Rechazado");
            estadoNegociacionService.save(estadoNegociacion);
        }

        estadoNegociacion = estadoNegociacionService.findByCodigo("04");

        if (estadoNegociacion == null) {
            estadoNegociacion = new EstadoNegociacionEntity();
            estadoNegociacion.setCodigo("04");
            estadoNegociacion.setNombre("Finalizado");
            estadoNegociacionService.save(estadoNegociacion);
        }

        // ES APROBADO PERO QUEDA MAL EN LA NEGOCIACION
        estadoNegociacion = estadoNegociacionService.findByCodigo("05");

        if (estadoNegociacion == null) {
            estadoNegociacion = new EstadoNegociacionEntity();
            estadoNegociacion.setCodigo("05");
            estadoNegociacion.setNombre("Anulado");
            estadoNegociacionService.save(estadoNegociacion);
        }

        

        EstadoInmuebleEntity estadoInmueble = estadoInmuebleService.findByCodigo("01");

        if (estadoInmueble == null) {
            estadoInmueble = new EstadoInmuebleEntity();
            estadoInmueble.setCodigo("01");
            estadoInmueble.setNombre("Disponible");
            estadoInmuebleService.save(estadoInmueble);
        }

        estadoInmueble = estadoInmuebleService.findByCodigo("02");

        if (estadoInmueble == null) {
            estadoInmueble = new EstadoInmuebleEntity();
            estadoInmueble.setCodigo("02");
            estadoInmueble.setNombre("Separado");
            estadoInmuebleService.save(estadoInmueble);
        }

        estadoInmueble = estadoInmuebleService.findByCodigo("03");

        if (estadoInmueble == null) {
            estadoInmueble = new EstadoInmuebleEntity();
            estadoInmueble.setCodigo("03");
            estadoInmueble.setNombre("Vendido");
            estadoInmuebleService.save(estadoInmueble);
        }

        

        /*TerceroEntity representante = terceroService.findOptionalByIdentificacion("11000531");

        if (representante == null) {

            representante = new TerceroEntity();
            representante.setIdentificacion("11000531");
            representante.setNombres("Juan Manuel");
            representante.setApellido1("Kelsy");
            representante.setApellido2("Romero");
            representante.setDireccion("Centro");
            representante.setTipoIdentificacion(tipoIdentificacionService.findByAbrebiatura("CC"));
            representante.setEmail("jkelsy@gmail.com");
            representante.setTelefono("7810010");
            representante.setUsuario(usuario);
            terceroService.save(representante);
        }

        representante = terceroService.findOptionalByIdentificacion("10766753");

        if (representante == null) {

            representante = new TerceroEntity();
            representante.setIdentificacion("10766753");
            representante.setNombres("Jesus");
            representante.setApellido1("Mestra");
            representante.setApellido2("Polo");
            representante.setDireccion("Centro");
            representante.setTipoIdentificacion(tipoIdentificacionService.findByAbrebiatura("CC"));
            representante.setEmail("jesusmestra@gmail.com");
            representante.setTelefono("7810010");
            terceroService.save(representante);
        }

        EmpresaEntity empresa = empresaService.findByNit("999999988");

        if (empresa == null) {
            empresa = new EmpresaEntity();
            empresa.setNit("999999988");
            empresa.setNombre("Sistem Kelsy");
            empresa.setDireccion("Centro");
            empresa.setEmail("admin@sistemkelsy.com.co");
            empresa.setEmailHost("NA");
            empresa.setEmailPass("NA");
            empresa.setEmailPort("80");
            //empresa.setPoblado(poblado);
            empresa.setTelefono("7810010");
            empresa.setRepresentante(terceroService.findOptionalByIdentificacion("11000531"));
            empresaService.save(empresa);
        }

        empresa = empresaService.findByNit("999999999");

        if (empresa == null) {
            empresa = new EmpresaEntity();
            empresa.setNit("999999999");
            empresa.setNombre("Sistem Mestra");
            empresa.setDireccion("Centro");
            empresa.setEmail("admin@sistemmestra.com.co");
            empresa.setEmailHost("NA");
            empresa.setEmailPass("NA");
            empresa.setEmailPort("80");
            //empresa.setPoblado(poblado);
            empresa.setTelefono("7810010");
            empresa.setRepresentante(terceroService.findOptionalByIdentificacion("10766753"));
            empresaService.save(empresa);
        }

        OfertaEntity oferta = ofertaService.findByNombre("Plan 30/70 18");

        if (oferta == null) {
            oferta = new OfertaEntity();
            oferta.setNombre("Plan 30/70 18");
            oferta.setNumeroCuotas(18);
            oferta.setPeriodicidad("30");
            oferta.setPorcentaje(30.0);
            oferta.setValorSeparacion(5000000.0);
            ofertaService.save(oferta);
        }

        oferta = ofertaService.findByNombre("Plan 40/70 12");

        if (oferta == null) {
            oferta = new OfertaEntity();
            oferta.setNombre("Plan 40/70 12");
            oferta.setNumeroCuotas(12);
            oferta.setPeriodicidad("30");
            oferta.setPorcentaje(40.0);
            oferta.setValorSeparacion(10000000.0);
            ofertaService.save(oferta);
        }

        ProyectoEntity proyecto = proyectoService.findByCodigo("P001");
        if (proyecto == null) {
            proyecto = new ProyectoEntity();
            proyecto.setCodigo("P001");
            proyecto.setNombre("CONJUNTO CERRADO KORANDO");
            proyecto.setEmpresa(empresaService.findByNit("999999988"));
            proyecto.setOferta(ofertaService.findByNombre("Plan 40/70 12"));
            proyecto.setEstadoProyecto(estadoProyecto);
            proyectoService.save(proyecto);
        }

        proyecto = proyectoService.findByCodigo("P002");
        if (proyecto == null) {
            proyecto = new ProyectoEntity();
            proyecto.setCodigo("P002");
            proyecto.setNombre("TORRES IMAN");
            proyecto.setEmpresa(empresaService.findByNit("999999999"));
            proyecto.setOferta(ofertaService.findByNombre("Plan 30/70 18"));
            proyecto.setEstadoProyecto(estadoProyecto);
            proyectoService.save(proyecto);
        }

        proyecto = proyectoService.findByCodigo("P003");
        if (proyecto == null) {
            proyecto = new ProyectoEntity();
            proyecto.setCodigo("P003");
            proyecto.setNombre("BUHO TORRES");
            proyecto.setEmpresa(empresaService.findByNit("999999988"));
            proyecto.setOferta(ofertaService.findByNombre("Plan 40/70 12"));
            proyecto.setEstadoProyecto(estadoProyecto);
            proyectoService.save(proyecto);
        }

        proyecto = proyectoService.findByCodigo("P004");
        if (proyecto == null) {
            proyecto = new ProyectoEntity();
            proyecto.setCodigo("P004");
            proyecto.setNombre("CONJUNTO CERRADO HP");
            proyecto.setEmpresa(empresaService.findByNit("999999999"));
            proyecto.setOferta(ofertaService.findByNombre("Plan 30/70 18"));
            proyecto.setEstadoProyecto(estadoProyecto);
            proyectoService.save(proyecto);
        }*/
    }
}
