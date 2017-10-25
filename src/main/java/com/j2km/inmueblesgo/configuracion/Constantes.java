/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.configuracion;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author jk
 */

public class Constantes {
    
    //Estados de los proyectos
    public static final String PROYECTO_ACTIVO = "ACTIVO";
    public static final String PROYECTO_INACTIVO = "INACTIVO";
    
    //Roles en el sistema
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_VENDEDOR = "VENDEDOR";
    public static final String ROLE_GERENTE = "GERENTE";
    
    //Estados de inmuebles
    public static final String INMUEBLE_DISPONIBLE = "DISPONIBLE";
    public static final String INMUEBLE_SEPARADO = "SEPARADO";
    public static final String INMUEBLE_VENDIDO = "VENDIDO";
    
    //Tipos de identificacion
    public static final String TI_NIT = "NIT";
    public static final String TI_NIT_ABREVIATURA = "NIT";
    public static final String TI_CEDULA = "CEDULA";
    public static final String TI_CEDULA_ABREVIATURA = "CC";
    public static final String TI_PASAPORTE = "PASAPORTE";
    public static final String TI_PASAPORTE_ABREVIATURA = "PAS";
    
    //Tipos de Fuentes de información, 
    public static final String TFI_VALLA = "VALLA";
    public static final String TFI_PERIODICO = "PERIODICO";
    public static final String TFI_PAGINA_WEB = "PAGINA WEB";
    public static final String TFI_REDES_SOCIALES = "REDES SOCIALES";
    public static final String TFI_REFERIDO = "REFERIDO";
    public static final String TFI_OTRA = "OTRA";
    
    //Estados de la negociacion
    public static final String NEGOCIACION_RADICADA = "RADICADA";
    public static final String NEGOCIACION_APROBADA = "APROBADA";
    public static final String NEGOCIACION_RECHAZADA = "RECHAZADA";
    public static final String NEGOCIACION_FINALIZADA = "FINALIZADA";
    public static final String NEGOCIACION_ANULADA = "ANULADA"; // ES APROBADO PERO QUEDA MAL EN LA NEGOCIACION
    
    
}
