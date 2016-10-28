/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.configuracion;

import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.EstadoNegociacionEntity;
import com.j2km.inmueblesgo.domain.EstadoProyectoEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.PermisoEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.domain.RolEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.EmpresaService;
import com.j2km.inmueblesgo.service.EstadoInmuebleService;
import com.j2km.inmueblesgo.service.EstadoNegociacionService;
import com.j2km.inmueblesgo.service.EstadoProyectoService;
import com.j2km.inmueblesgo.service.OfertaService;
import com.j2km.inmueblesgo.service.PermisoService;
import com.j2km.inmueblesgo.service.ProyectoService;
import com.j2km.inmueblesgo.service.RolService;
import com.j2km.inmueblesgo.service.TerceroService;
import com.j2km.inmueblesgo.service.TipoIdentificacionService;
import com.j2km.inmueblesgo.service.UsuarioService;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class Inicio {

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private RolService rolService;

    @Inject
    private PermisoService permisoService;

    @Inject
    private TipoIdentificacionService tipoIdentificacionService;

    @Inject
    private TerceroService terceroService;

    @Inject
    private EmpresaService empresaService;

    @Inject
    private ProyectoService proyectoService;

    @Inject
    private OfertaService ofertaService;

    @Inject
    private EstadoInmuebleService estadoInmuebleService;

    @Inject
    private EstadoNegociacionService estadoNegociacionService;

    @Inject
    private EstadoProyectoService estadoProyectoService;

    @PostConstruct
    public void iniciar() {

        EstadoProyectoEntity estadoProyecto = estadoProyectoService.findByCodigo("01");

        if (estadoProyecto == null) {
            estadoProyecto = new EstadoProyectoEntity();
            estadoProyecto.setCodigo("01");
            estadoProyecto.setNombre("Activo");
            estadoProyectoService.save(estadoProyecto);
        }

        estadoProyecto = estadoProyectoService.findByCodigo("02");

        if (estadoProyecto == null) {
            estadoProyecto = new EstadoProyectoEntity();
            estadoProyecto.setCodigo("02");
            estadoProyecto.setNombre("Inactivo");
            estadoProyectoService.save(estadoProyecto);
        }

        EstadoNegociacionEntity estadoNegociacion = estadoNegociacionService.findByCodigo("01");

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

        RolEntity rol = rolService.findByNombre("ADMIN");

        if (rol == null) {
            rol = new RolEntity();
            rol.setNombre("ADMIN");
            rolService.save(rol);
        }

        UsuarioEntity usuario = usuarioService.findByLogin("admin");

        if (usuario == null) {
            usuario = new UsuarioEntity();
            usuario.setLogin("admin");
            usuario.setPassword("admin");
            usuarioService.save(usuario);
        }

        PermisoEntity permiso = permisoService.findByUsuarioAndRol(usuario, rol);

        if (permiso == null) {
            permiso = new PermisoEntity();
            permiso.setUsuario(usuario);
            permiso.setRol(rol);
            permisoService.save(permiso);
        }

        RolEntity rolVendedor = rolService.findByNombre("VENDEDOR");

        if (rolVendedor == null) {
            rolVendedor = new RolEntity();
            rolVendedor.setNombre("VENDEDOR");
            rolService.save(rolVendedor);
        }

        UsuarioEntity usuarioVendedor = usuarioService.findByLogin("vendedor");

        if (usuarioVendedor == null) {
            usuarioVendedor = new UsuarioEntity();
            usuarioVendedor.setLogin("vendedor");
            usuarioVendedor.setPassword("vendedor");
            usuarioService.save(usuarioVendedor);
        }

        PermisoEntity permisoVendedor = permisoService.findByUsuarioAndRol(usuarioVendedor, rolVendedor);

        if (permisoVendedor == null) {
            permisoVendedor = new PermisoEntity();
            permisoVendedor.setUsuario(usuarioVendedor);
            permisoVendedor.setRol(rolVendedor);
            permisoService.save(permisoVendedor);
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

        TipoIdentificacionEntity tipoIdentificacion = tipoIdentificacionService.findByAbrebiatura("CC");

        if (tipoIdentificacion == null) {
            tipoIdentificacion = new TipoIdentificacionEntity();
            tipoIdentificacion.setAbreviatura("CC");
            tipoIdentificacion.setCodigo("01");
            tipoIdentificacion.setNombre("Cedula de Ciudadania");
            tipoIdentificacionService.save(tipoIdentificacion);
        }

        tipoIdentificacion = tipoIdentificacionService.findByAbrebiatura("NIT");

        if (tipoIdentificacion == null) {
            tipoIdentificacion = new TipoIdentificacionEntity();
            tipoIdentificacion.setAbreviatura("NIT");
            tipoIdentificacion.setCodigo("02");
            tipoIdentificacion.setNombre("Nit");
            tipoIdentificacionService.save(tipoIdentificacion);
        }

        tipoIdentificacion = tipoIdentificacionService.findByAbrebiatura("RC");

        if (tipoIdentificacion == null) {
            tipoIdentificacion = new TipoIdentificacionEntity();
            tipoIdentificacion.setAbreviatura("RC");
            tipoIdentificacion.setCodigo("03");
            tipoIdentificacion.setNombre("Registro Civil");
            tipoIdentificacionService.save(tipoIdentificacion);
        }

        tipoIdentificacion = tipoIdentificacionService.findByAbrebiatura("PAS");

        if (tipoIdentificacion == null) {
            tipoIdentificacion = new TipoIdentificacionEntity();
            tipoIdentificacion.setAbreviatura("PAS");
            tipoIdentificacion.setCodigo("04");
            tipoIdentificacion.setNombre("Pasaporte");
            tipoIdentificacionService.save(tipoIdentificacion);
        }

        TerceroEntity representante = terceroService.findByIdentificacion("11000531");

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
            terceroService.save(representante);
        }

        representante = terceroService.findByIdentificacion("10766753");

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
            empresa.setRepresentante(terceroService.findByIdentificacion("11000531"));
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
            empresa.setRepresentante(terceroService.findByIdentificacion("10766753"));
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
        }

    }
}
