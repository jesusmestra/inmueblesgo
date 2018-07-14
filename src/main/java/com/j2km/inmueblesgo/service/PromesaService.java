package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.Archivo;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.web.ApplicationBean;
import com.j2km.inmueblesgo.web.util.NumeroALetras;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

public class PromesaService implements Serializable {
    
    @Inject private NegociacionRepository nr;
    @Inject private TerceroRepository terceroRepository;
    @Inject private PlanPagoRepository planPagoRepository;
    @Inject private ApplicationBean applicationBean;
    
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat fechaLarga = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
    private DecimalFormat formatea = new DecimalFormat("###,###.##");
    
    public byte[] generar(NegociacionEntity negociacion) {        
        List<TerceroEntity> clientes = terceroRepository.findByNegociacion(negociacion);
        Archivo archivo = negociacion.getInmueble().getProyecto().getPromesa();
        
        NumeroALetras numeroALetras = new NumeroALetras();
        
        List<PlanPagoEntity> planPagoList = planPagoRepository.findByNegociacionOrderByFechaPactadaAsc(negociacion);
        
        if (archivo == null){
            return null;
        }        
        try {            
            
            String strClientes = "";    
            
            String direccionCliente="";
            String barrioCliente="";
            String ciudadCliente="";
            String telefonoCliente="";
            String emailCliente="";
            
            int contador = 0;
            for (TerceroEntity cliente : clientes) {
                strClientes +=cliente.getNombreCompleto();
                strClientes +=" con "+cliente.getTipoIdentificacion().getNombre().toLowerCase();
                strClientes +=" número "+cliente.getIdentificacion();
                strClientes +=" de "+cliente.getLugarExpedicion().getNombre()+ ", ";
                
                if(contador == 0){
                    if(cliente.getDireccion() != null)
                        direccionCliente = cliente.getDireccion();
                    
                    if(cliente.getBarrio() != null)
                        barrioCliente = cliente.getBarrio();
                    
                    if(cliente.getLugarResidencia() != null)
                        ciudadCliente = cliente.getLugarResidencia().getNombre();
                    
                    if(cliente.getTelefono()!= null)
                        telefonoCliente = cliente.getTelefono();
                    
                    if(cliente.getCelular()!= null)
                        telefonoCliente += " " +cliente.getCelular();
                    
                    if(cliente.getEmail()!= null)
                        emailCliente = cliente.getEmail();
                }
                contador++;
            }
            
            String strPlanPago = "";
            for (PlanPagoEntity pagoEntity: planPagoList){
                strPlanPago+="el "+fechaLarga.format(pagoEntity.getFechaPactada())
                            +" la suma de "+numeroALetras.numero_a_letras(pagoEntity.getValorPactado()).toUpperCase()+" PESOS" 
                            +" (\\$"+formatea.format(pagoEntity.getValorPactado())+")"
                            +"; ";                            
            }
            
            String temp = new String(Files.readAllBytes(Paths.get(archivo.getRutaFisica())));
            System.err.println(negociacion.getValorTotal());
            temp = temp
                    .replaceAll("xxempresaxx", negociacion.getInmueble().getProyecto().getEmpresa().getNombre())
                    .replaceAll("xxnitxx", negociacion.getInmueble().getProyecto().getEmpresa().getNit())
                    .replaceAll("xxcompradoresxx",strClientes)
                    .replaceAll("xxnumeroxx", negociacion.getInmueble().getNumero())
                    .replaceAll("xxtorrenumeroxx", negociacion.getInmueble().getPiso().getTorre().getNumero())                    
                    .replaceAll("xxareaxx", formatea.format(negociacion.getInmueble().getArea()))
                    .replaceAll("xxvalorinmueblexx", formatea.format(negociacion.getValorTotal()))
                    .replaceAll("xxletrasvalorinmueblexx", numeroALetras.numero_a_letras(negociacion.getValorTotal()).toUpperCase())
                    .replaceAll("xxvalornegociacionxx", formatea.format(negociacion.getValorPorcentaje()))
                    .replaceAll("xxletrasvalornegociacionxx", numeroALetras.numero_a_letras(negociacion.getValorPorcentaje()).toUpperCase())
                    .replaceAll("xxplandepagoxx", strPlanPago)
                    .replaceAll("xxpagobancoxx", formatea.format(negociacion.getValorTotal()-negociacion.getValorPorcentaje()))
                    .replaceAll("xxletraspagobancoxx", numeroALetras.numero_a_letras(negociacion.getValorTotal()-negociacion.getValorPorcentaje()).toUpperCase())
                    .replaceAll("xxdireccionclientexx", direccionCliente)
                    .replaceAll("xxbarrioclientexx", barrioCliente)
                    .replaceAll("xxciudadclientexx", ciudadCliente)
                    .replaceAll("xxtelefonoclientexx", telefonoCliente)
                    .replaceAll("xxemailclientexx", emailCliente);
           
            return temp.getBytes("ISO-8859-1");
            

        } catch (FileNotFoundException ex ) {
            System.err.println(ex.getMessage());
            Logger.getLogger(PromesaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            Logger.getLogger(PromesaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PromesaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
