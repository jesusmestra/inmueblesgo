/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.configuracion;

import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.Permiso;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.domain.Rol;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;
import com.j2km.inmueblesgo.domain.Usuario;
import com.j2km.inmueblesgo.service.EmpresaService;
import com.j2km.inmueblesgo.service.EstadoInmuebleService;
import com.j2km.inmueblesgo.service.OfertaService;
import com.j2km.inmueblesgo.service.PermisoFacade;
import com.j2km.inmueblesgo.service.ProyectoService;
import com.j2km.inmueblesgo.service.RolFacade;
import com.j2km.inmueblesgo.service.TerceroService;
import com.j2km.inmueblesgo.service.TipoIdentificacionService;
import com.j2km.inmueblesgo.service.UsuarioFacade;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;


@Startup
@Singleton
public class Inicio {
    
    @Inject
    private UsuarioFacade usuarioFacade;

    @Inject
    private RolFacade rolFacade;

    @Inject
    private PermisoFacade permisoFacade;

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
    
    @PostConstruct
    public void iniciar() {
        
        Rol rol = rolFacade.findByNombre("ADMIN");

        if (rol == null) {
            rol = new Rol();
            rol.setNombre("ADMIN");
            rolFacade.create(rol);
        }

        Usuario usuario = usuarioFacade.findByLogin("admin");

        if (usuario == null) {
            usuario = new Usuario();
            usuario.setLogin("admin");
            usuario.setPassword("admin");
            usuarioFacade.create(usuario);
        }

        Permiso permiso = permisoFacade.findByUsuarioAndRol(usuario, rol);

        if (permiso == null) {
            permiso = new Permiso();
            permiso.setUsuario(usuario);
            permiso.setRol(rol);
            permisoFacade.create(permiso);
        }

        EstadoInmuebleEntity estadoInmueble = estadoInmuebleService.findByCodigo("01");
        
        if (estadoInmueble == null){
            estadoInmueble = new EstadoInmuebleEntity();
            estadoInmueble.setCodigo("01");
            estadoInmueble.setNombre("Disponible");
            estadoInmuebleService.save(estadoInmueble);
        }
        
        estadoInmueble = estadoInmuebleService.findByCodigo("02");
        
        if (estadoInmueble == null){
            estadoInmueble = new EstadoInmuebleEntity();
            estadoInmueble.setCodigo("02");
            estadoInmueble.setNombre("Separado");
            estadoInmuebleService.save(estadoInmueble);
        }
        
        estadoInmueble = estadoInmuebleService.findByCodigo("03");
        
        if (estadoInmueble == null){
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
        
        if (oferta == null){
            oferta = new OfertaEntity();
            oferta.setNombre("Plan 30/70 18");
            oferta.setNumeroCuotas(18);
            oferta.setPeriodicidad("30");
            oferta.setPorcentaje(30.0);
            oferta.setValorSeparacion(5000000.0);
            ofertaService.save(oferta);
        }
        
        oferta = ofertaService.findByNombre("Plan 40/70 12");
        
        if (oferta == null){
            oferta = new OfertaEntity();
            oferta.setNombre("Plan 40/70 12");
            oferta.setNumeroCuotas(12);
            oferta.setPeriodicidad("30");
            oferta.setPorcentaje(40.0);
            oferta.setValorSeparacion(10000000.0);
            ofertaService.save(oferta);
        }
        
        
        
        ProyectoEntity proyecto = proyectoService.findByCodigo("P001");
        if (proyecto == null){
            proyecto = new ProyectoEntity();
            proyecto.setCodigo("P001");
            proyecto.setNombre("CONJUNTO CERRADO KORANDO");
            proyecto.setEmpresa(empresaService.findByNit("999999988"));
            proyecto.setOferta(ofertaService.findByNombre("Plan 40/70 12"));
            proyectoService.save(proyecto);
        }

        proyecto = proyectoService.findByCodigo("P002");
        if (proyecto == null){
            proyecto = new ProyectoEntity();
            proyecto.setCodigo("P002");
            proyecto.setNombre("TORRES IMAN");
            proyecto.setEmpresa(empresaService.findByNit("999999999"));
            proyecto.setOferta(ofertaService.findByNombre("Plan 30/70 18"));
            proyectoService.save(proyecto);
        }
        
        proyecto = proyectoService.findByCodigo("P003");
        if (proyecto == null){
            proyecto = new ProyectoEntity();
            proyecto.setCodigo("P003");
            proyecto.setNombre("BUHO TORRES");
            proyecto.setEmpresa(empresaService.findByNit("999999988"));
            proyecto.setOferta(ofertaService.findByNombre("Plan 40/70 12"));
            proyectoService.save(proyecto);
        }

        proyecto = proyectoService.findByCodigo("P004");
        if (proyecto == null){
            proyecto = new ProyectoEntity();
            proyecto.setCodigo("P004");
            proyecto.setNombre("CONJUNTO CERRADO HP");
            proyecto.setEmpresa(empresaService.findByNit("999999999"));
            proyecto.setOferta(ofertaService.findByNombre("Plan 30/70 18"));
            proyectoService.save(proyecto);
        }
     
    }
}
