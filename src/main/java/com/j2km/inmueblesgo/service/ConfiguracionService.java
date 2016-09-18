/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;

@Stateless
public class ConfiguracionService{
     @PersistenceContext(unitName = "InmueblesDS")
    private EntityManager em;

    @Inject
    private DepartamentoService df;

    @Inject
    private MunicipioService mf;

    @Inject
    private PobladoService pf;

    public void cargarDivipola(Part divipola) {
        
        System.out.println("Inicianco el cargue...");
        
        if (df.findAllDepartamentoEntities().isEmpty()) {
            System.out.println("En blanco..."+divipola);
            try (InputStream is = divipola.getInputStream()) {
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
                df.save(departamento);

                MunicipioEntity municipio = new MunicipioEntity();
                municipio.setCodigo(codMpio);
                municipio.setNombre(recordInicial[4]);
                municipio.setDepartamento(departamento);

                mf.save(municipio);

                PobladoEntity poblado = new PobladoEntity();
                poblado.setCodigo(recordInicial[2]);
                poblado.setNombre(recordInicial[5]);
                poblado.setMunicipio(municipio);
                pf.save(poblado);

                while ((line = br.readLine()) != null) {
                    record = line.split(";");
                    if(!codDpto.equals(record[0])){
                        codDpto = record[0];
                        departamento = new DepartamentoEntity();
                        departamento.setCodigo(codDpto);
                        departamento.setNombre(record[3]);
                        df.save(departamento);
                    }
                    
                    if(!codMpio.equals(record[1])){
                        codMpio = record[1];
                        municipio = new MunicipioEntity();
                        municipio.setCodigo(codMpio);
                        municipio.setNombre(record[4]);
                        municipio.setDepartamento(departamento);
                        mf.save(municipio);
                    }
                    
                    poblado = new PobladoEntity();
                    poblado.setCodigo(record[2]);
                    poblado.setNombre(record[5]);
                    poblado.setMunicipio(municipio);
                    pf.save(poblado);
                }

            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        }

    }
}
