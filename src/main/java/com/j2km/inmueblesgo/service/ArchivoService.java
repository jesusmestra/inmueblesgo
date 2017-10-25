/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.Archivo;
import com.j2km.inmueblesgo.web.ApplicationBean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLConnection;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jk
 */
@Named
public class ArchivoService implements Serializable{
    
    @Inject private ArchivoRepository archivoRepository;   
    @Inject private ApplicationBean application;
    
    public Archivo upload(String carpeta, String tipo, UploadedFile uploaded){        
        Archivo archivo = new Archivo();
        archivo.setExtension(FilenameUtils.getExtension(uploaded.getFileName()));
        archivo.setNombreOrigen(uploaded.getFileName());
        archivo = archivoRepository.saveAndFlush(archivo);
            
        InputStream input = null;
        OutputStream output = null;        
        try {
            input = uploaded.getInputstream();
            
            archivo.setContentType(URLConnection.guessContentTypeFromStream(input));
            archivo.setNombre(tipo+"_"+archivo.getId().toString()+"."+archivo.getExtension());
            archivo.setRutaFisica(application.getRutaFisicaArchivos()+File.separator+carpeta+File.separator+archivo.getNombre());
            archivo.setRutaWeb(carpeta+File.separator+archivo.getNombre());
            archivo = archivoRepository.saveAndFlush(archivo);
            output = new FileOutputStream(new File(application.getRutaFisicaArchivos()+File.separator+carpeta, archivo.getNombre()));
            IOUtils.copy(input, output);
            
        }catch (IOException e) {            
            System.err.println("Error" + e.getMessage());
            return null;
        }finally{
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);            
        }        
        return archivo;
    }
    
}
