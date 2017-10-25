package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.web.ApplicationBean;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLConnection;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.poi.util.IOUtils;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.FilenameUtils;

@Stateless
public class ConfiguracionService{
    @PersistenceContext(unitName = "InmueblesDS")
    private EntityManager em;

    @Inject private DepartamentoRepository df;
    @Inject private MunicipioRepository mf;
    @Inject private PobladoRepository pf;    
    @Inject private ApplicationBean ap;

    public void cargarDivipola(UploadedFile divipola) {        
        
        System.out.println("Inicianco el cargue...");
        
        if (df.count() == 0) {
            System.out.println("En blanco..."+divipola);
            try (InputStream is = divipola.getInputstream()) {
                System.out.println("Lectura de archivo...");
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "ISO-8859-3"));
                String line;
                String[] record;

                br.readLine();//Descartar la fila donde están los encabezados.

                String[] recordInicial = br.readLine().split(";");
                String codDpto = recordInicial[0];
                String codMpio = recordInicial[1];

                DepartamentoEntity departamento = new DepartamentoEntity();
                departamento.setCodigo(codDpto);
                departamento.setNombre(recordInicial[3]);
                departamento = df.saveAndFlush(departamento);

                MunicipioEntity municipio = new MunicipioEntity();
                municipio.setCodigo(codMpio);
                municipio.setNombre(recordInicial[4]);
                municipio.setDepartamento(departamento);

                municipio = mf.saveAndFlush(municipio);

                PobladoEntity poblado = new PobladoEntity();
                poblado.setCodigo(recordInicial[2]);
                poblado.setNombre(recordInicial[5]);
                poblado.setMunicipio(municipio);
                poblado = pf.saveAndFlush(poblado);

                while ((line = br.readLine()) != null) {
                    record = line.split(";");
                    if(!codDpto.equals(record[0])){
                        codDpto = record[0];
                        departamento = new DepartamentoEntity();
                        departamento.setCodigo(codDpto);
                        departamento.setNombre(record[3]);
                        departamento = df.saveAndFlush(departamento);
                    }
                    
                    if(!codMpio.equals(record[1])){
                        codMpio = record[1];
                        municipio = new MunicipioEntity();
                        municipio.setCodigo(codMpio);
                        municipio.setNombre(record[4]);
                        municipio.setDepartamento(departamento);
                        municipio = mf.saveAndFlush(municipio);
                    }
                    
                    poblado = new PobladoEntity();
                    poblado.setCodigo(record[2]);
                    poblado.setNombre(record[5]);
                    poblado.setMunicipio(municipio);
                    poblado = pf.saveAndFlush(poblado);
                }

            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        }
    }    
    
    public String copiarArchivo(UploadedFile archivo, String nuevoNombre, String carpeta) throws FileNotFoundException, IOException {
        String resultado = "";

        System.err.println("Grabando el archivo");
        
        String filePath = ap.rutaCarpeta() + File.separator + carpeta;
        InputStream input = archivo.getInputstream();
        String fileType = URLConnection.guessContentTypeFromStream(input);
        String extension = FilenameUtils.getExtension(archivo.getFileName());        
        
        String filename = nuevoNombre +"." +extension;
        OutputStream output = new FileOutputStream(new File(filePath, filename));

        try {
            IOUtils.copy(input, output);
            resultado = filename;
        } catch (IOException e) {
            System.err.println("Error" + e);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
        return resultado;
    }
    
    
}
