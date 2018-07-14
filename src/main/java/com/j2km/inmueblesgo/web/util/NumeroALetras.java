/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.web.util;

public class NumeroALetras {
    
    private final String MONEDA_SINGULAR = "peso";
    private final String MONEDA_PLURAL = "pesos";

    private final String CENTIMOS_SINGULAR = "centavo";
    private final String CENTIMOS_PLURAL = "centavos";

    private final double MAX_NUMERO = 999999999999d;
    
    private final String[] UNIDADES = {
            "cero", "uno", "dos", "tres", "cuatro", "cinco", 
            "seis", "siete", "ocho", "nueve"
    };
    
    private final String[] DECENAS = {
        "diez", "once", "doce", "trece", "catorce", "quince", 
        "dieciseis", "diecisiete", "dieciocho", "diecinueve"
    };
    
    private final String[] DIEZ_DIEZ = {
        "cero", "diez", "veinte", "treinta", "cuarenta",
        "cincuenta", "sesenta", "setenta", "ochenta", "noventa"
    };
    
    private final String[] CIENTOS = {
        "_", "ciento", "doscientos", "trescientos", "cuatroscientos",
        "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"
    };

    public NumeroALetras() {
    
    }
    
    private String leer_decenas(int numero){
        String resultado = "";
        if(numero < 10) return UNIDADES[numero];
        
        int decena = numero / 10;
        int unidad = numero % 10;
    
        if (numero <= 19) resultado = DECENAS[unidad];
        else if (numero <= 29) resultado = "veinti"+ UNIDADES[unidad];
        else{
            resultado = DIEZ_DIEZ[decena];
            if (unidad > 0)
                resultado = resultado +" y "+ UNIDADES[unidad];
        }
        return resultado;
    }
    
    private String leer_centenas(int numero){
        String resultado = "";
        int centena = numero / 100;
        int decena = numero % 100;
    
        if (numero == 0) resultado = "cien";
        else{
            resultado = CIENTOS[centena];
            if (decena > 0) resultado = resultado + " " +leer_decenas(decena);
        }        
        return resultado;
    }
    
    private String leer_miles(int numero){
        int millar = numero / 1000;
        int centena = numero % 1000;
    
        String resultado = "";
        
        if (millar == 1) resultado = "";
        if ((millar >= 2) && (millar <= 9))
            resultado = UNIDADES[millar];
        else if((millar >= 10) && (millar <= 99))
            resultado = leer_decenas(millar);
        else if ((millar >= 100) && (millar <= 999))
            resultado = leer_centenas(millar);
        
        resultado = resultado+" mil";
        if (centena > 0)
            resultado = resultado+ " "+leer_centenas(centena);
        
        return resultado;
    }
    
    private String leer_millones(int numero){
        int millon = numero / 1000000;
        int millar = numero % 1000000;
        
        String resultado = "";
        if (millon == 1) resultado = " un millon ";
        if ((millon >= 2) && (millon <= 9))
            resultado = UNIDADES[millon];
        else if ((millon >= 10) && (millon <= 99))
            resultado = leer_decenas(millon);
        else if ((millon >= 100) && (millon <= 999))
            resultado = leer_centenas(millon);
        if (millon > 1)
            resultado = resultado +" millones";
        if ((millar > 0) && (millar <= 999))
            resultado = resultado + " "+ leer_centenas(millar);
        else if ((millar >= 1000) && (millar <= 999999))
            resultado = resultado + " "+ leer_miles(millar);
        
        return resultado;        
    }
    
    private String leer_millardos(int numero){
        int millardo = numero / 1000000;
        int millon = numero % 1000000;
        
        return leer_miles(millardo) +" millones "+ leer_millones(millon);
    }
    
    public String numero_a_letras(Double numero) throws Exception{        
        String letras_decimal = "";
        String resultado = "";
        
        int numero_entero = (int)numero.longValue();
        if (numero_entero > MAX_NUMERO)
            throw new Exception("numero mayor que el máximo entero");
        if(numero_entero < 0)
            return "menos "+ numero_a_letras(Math.abs(numero));
        
        int parte_decimal = (int)(Math.round((Math.abs(numero) - Math.abs(numero_entero)) * 100));
        if (parte_decimal > 9)
            letras_decimal = "punto "+numero_a_letras((double)parte_decimal);
        else if (parte_decimal > 0)
            letras_decimal = "punto cero "+ numero_a_letras((double)parte_decimal);
        if (numero_entero <= 99)
            resultado = leer_decenas(numero_entero);
        else if (numero_entero <= 999)
            resultado = leer_centenas(numero_entero);
        else if (numero_entero <= 999999)
            resultado = leer_miles(numero_entero);
        else if (numero_entero <= 999999999)
            resultado = leer_millones(numero_entero);
        else
            resultado = leer_millardos(numero_entero);
        
        resultado = resultado.replaceAll("uno mil", "un mil");
        resultado = resultado.trim();
        resultado = resultado.replaceAll(" _ ", " ");
        resultado = resultado.replaceAll("  ", " ");
        if (parte_decimal > 0)
            resultado = resultado + " "+letras_decimal;
        
        return resultado;
    }
    
    
    

    
}