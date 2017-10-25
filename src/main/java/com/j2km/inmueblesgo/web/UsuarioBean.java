/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.configuracion.Constantes;
import com.j2km.inmueblesgo.domain.PermisoEntity;
import com.j2km.inmueblesgo.domain.RolEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.EncriptarService;
import com.j2km.inmueblesgo.service.PermisoRepository;
import com.j2km.inmueblesgo.service.RolRepository;
import com.j2km.inmueblesgo.service.UsuarioRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import org.primefaces.event.SelectEvent;

@ViewScoped
@Named("usuarioBean")
public class UsuarioBean implements Serializable {

    private static final Logger logger = Logger.getLogger(UsuarioBean.class.getName());

    @Inject
    private UsuarioRepository usuarioService;
    @Inject
    private RolRepository rolService;
    @Inject
    private PermisoRepository permisoService;
    @Inject
    private EncriptarService encriptarService;

    private Boolean esAdmin;
    private String login;

    private List<UsuarioEntity> usuarioList;
    private UsuarioEntity usuario;
    private Boolean esVendedor;

    private String confirmarPassword;
    private String password;

    private List<RolEntity> rolesList;
    private List<RolEntity> selectedRoles;

    public List<RolEntity> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<RolEntity> rolesList) {
        this.rolesList = rolesList;
    }

    public List<RolEntity> getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(List<RolEntity> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }

    public List<UsuarioEntity> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<UsuarioEntity> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public boolean validar() {

        if (usuario.getLogin() == null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Debe ingresar el login del usuario",
                    "Debe ingresar el login del usuario"));
            return false;
        }

        if (password == null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Debe ingresar un password",
                    "Debe ingresar un password"));
            return false;
        }

        if (confirmarPassword == null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Debe ingresar un confirmar password",
                    "Debe ingresar un confirmar password"));
            return false;
        }

        if (!password.equals(confirmarPassword)) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Contreñas no coinciden, verificar",
                    "Error"));
            return false;
        }

        return true;
    }

    public void guardarFinal() {
        if (usuario.getId() == null) {
            this.guardar();
        } else {
            this.modificar();
        }
    }

    public boolean validarModificar() {

        if (usuario.getLogin() == null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Debe ingresar el login del usuario",
                    "Debe ingresar el login del usuario"));
            return false;
        }

        return true;
    }

    public void modificar() {
        if (!validarModificar()) {
            return;
        }

        if (password != null) {
            if (password.equals(confirmarPassword)) {
                usuario.setPassword(encriptarService.encriptar(password));
            } else {
                FacesMessage facesMsg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Contraseñas diferentes, no se pueden modificar",
                        "Contraseñas diferentes, no se pueden modificar");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
                return;
            }
        }

        List<PermisoEntity> permisos = permisoService.findByUsuario(usuario);

        for (PermisoEntity permiso : permisos) {
            permisoService.attachAndRemove(permiso);
        }

        PermisoEntity permiso;       
        usuario = usuarioService.save(usuario);

        for (RolEntity rol : selectedRoles) {
            permiso = new PermisoEntity();
            permiso.setRol(rol);
            permiso.setUsuario(usuario);
            permisoService.save(permiso);
        }

        FacesMessage facesMsg = new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Usuario Modificado Exitosamente",
                "Usuario Modificado Exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);

        reset();
    }

    public void guardar() {

        if (!validar()) {
            return;
        }

        try {
            PermisoEntity permiso;

            usuario.setPassword(encriptarService.encriptar(password));
            usuario = usuarioService.save(usuario);

            for (RolEntity rol : selectedRoles) {
                permiso = new PermisoEntity();
                permiso.setRol(rol);
                permiso.setUsuario(usuario);
                permisoService.save(permiso);
            }

            FacesMessage facesMsg = new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Usuario Guardado Exitosamente",
                    "Usuario Guardado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);

            reset();
        } catch (PersistenceException e) {
            FacesMessage facesMsg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Error al crear usuario",
                    "Error");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);

            logger.log(Level.SEVERE, "Error al guardar usuario", e);

            System.out.println("guardar usuario");
        }
    }

    public void reset() {
        usuario = new UsuarioEntity();
        esVendedor = false;
        esAdmin = false;
        usuarioList = usuarioService.findAll();
        rolesList = rolService.findAll();
        selectedRoles = new ArrayList<>();
        password = null;
        confirmarPassword = null;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public Boolean getEsVendedor() {
        return esVendedor;
    }

    public void setEsVendedor(Boolean esVendedor) {
        this.esVendedor = esVendedor;
    }

    public Boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public List<UsuarioEntity> getUsuarios() {
        return usuarioService.findAll();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void seleccionarUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
        esAdmin = usuarioService.esAdmin(this.usuario);
        esVendedor = usuarioService.esVendedor(this.usuario);

        selectedRoles = permisoService.findByUsuario(this.usuario)
                .stream()
                .map(PermisoEntity::getRol)
                .collect(Collectors.toList());
    }

    public Boolean admin(UsuarioEntity usuario) {
        return usuarioService.esAdmin(usuario);
    }

    public Boolean vendedor(UsuarioEntity usuario) {
        return usuarioService.esVendedor(usuario);
    }

    public void seleccionarTercero(SelectEvent event) {
        this.usuario.setTercero((TerceroEntity) event.getObject());

    }
    
    public List<RolEntity> rolesPorUsuario(UsuarioEntity usuario){
        return permisoService.findByUsuario(usuario)
                .stream()
                .map(PermisoEntity::getRol)
                .collect(Collectors.toList());
    }

}
