/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.servlet;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.service.NegociacionRepository;
import com.j2km.inmueblesgo.service.PlanPagoRepository;
import com.j2km.inmueblesgo.service.TerceroRepository;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SolicitudInmuebleItext", urlPatterns = {"/SolicitudInmuebleItext"})
public class SolicitudInmuebleItext extends HttpServlet {
   
    @Inject private NegociacionRepository nr;
    @Inject private TerceroRepository terceroRepository;
    @Inject private PlanPagoRepository planPagoRepository;
    
    private BaseColor grisOscuro = new BaseColor(210,210,210);
    private BaseColor grisClaro = new BaseColor(240,240,240);  
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat formatea = new DecimalFormat("###,###.##");
    
    private PdfPCell celda(String texto, BaseColor color, int alineacion, float fontSize) {
        Font font = new Font();
        font.setSize(fontSize);
        PdfPCell tempo = new PdfPCell(new Phrase(texto, font));
        tempo.setBackgroundColor(color);        
        tempo.setHorizontalAlignment(alineacion);
        tempo.setBorderWidth(1f);
        return tempo;
    }
    
    private void tablaPlanPago(Document document, List<PlanPagoEntity> planPago) throws DocumentException{
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingAfter(5f);
        PdfPCell celda = celda("PLAN DE PAGO",grisOscuro, Element.ALIGN_CENTER,10f);
        celda.setColspan(7);
        table.addCell(celda);
        
        table.addCell(celda("NRO", grisClaro, Element.ALIGN_CENTER,8f));
        table.addCell(celda("FECHA", grisClaro, Element.ALIGN_CENTER,8f));
        table.addCell(celda("VALOR", grisClaro, Element.ALIGN_CENTER,8f));
        table.addCell(celda("", grisClaro, Element.ALIGN_CENTER,8f));
        table.addCell(celda("NRO", grisClaro, Element.ALIGN_CENTER,8f));
        table.addCell(celda("FECHA", grisClaro, Element.ALIGN_CENTER,8f));
        table.addCell(celda("VALOR", grisClaro, Element.ALIGN_CENTER,8f));
        
        int contador = 1;
        
        for (PlanPagoEntity planPagoEntity : planPago) {
            table.addCell(celda(planPagoEntity.getNumeroCuota().toString(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            table.addCell(celda(dateFormat.format(planPagoEntity.getFechaPactada()),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            table.addCell(celda(formatea.format(planPagoEntity.getValorPactado()),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            if((contador%2)!=0){
                table.addCell(celda("",BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            }
            
            contador++;
        }
        table.completeRow();
        document.add(table);
    }
        
    private void tableClientes(Document document, List<TerceroEntity> clientes) throws DocumentException{        
        PdfPTable tempo;
        for (TerceroEntity cliente : clientes) {
            tempo = new PdfPTable(5);            
            tempo.setWidthPercentage(100);          
            tempo.addCell(celda("TIPO ID",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda("IDENTIFICACION",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda("LUGAR EXPEDICION",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda("SEXO",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda("ESTADO CIVIL",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getTipoIdentificacion().getAbreviatura(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getIdentificacion(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getLugarExpedicion().getNombre(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getSexo(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getEstadoCivil(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));   
            document.add(tempo);
            
            //////////////////////////////
            tempo = new PdfPTable(4);
            tempo.setWidthPercentage(100);       
            tempo.addCell(celda("NOMBRE Y APELLIDOS",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda("TELÉFONO",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda("CELULAR",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda("EMAIL",grisClaro, Element.ALIGN_CENTER, 8f));            
            tempo.addCell(celda(cliente.getNombres() + " "+cliente.getApellido1()+" "+cliente.getApellido2(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getTelefono(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getCelular(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getEmail(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));           
            document.add(tempo);
            
            //////////////////////////////
            tempo = new PdfPTable(4);
            tempo.setWidthPercentage(100);
            tempo.setSpacingAfter(4f);
            tempo.addCell(celda("CIUDAD RESIDENCIA",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda("DIRECCIÓN",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda("BARRIO",grisClaro, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda("OCUPACIÓN",grisClaro, Element.ALIGN_CENTER, 8f));            
            tempo.addCell(celda(cliente.getLugarResidencia().getNombre(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getDireccion(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getBarrio(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));
            tempo.addCell(celda(cliente.getOcupacion(),BaseColor.WHITE, Element.ALIGN_CENTER, 8f));           
            document.add(tempo);
        }        
    }
    
    private void tablaCondiciones(Document document) throws DocumentException{
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingAfter(5f);
        PdfPCell celda = celda("CONDICIONES DE LA OFERTA COMERCIAL",grisOscuro, Element.ALIGN_CENTER, 10f);   
        table.addCell(celda);        
        table.addCell(celda("Las siguientes condiciones son esenciales para el perfeccionamiento de la firma de la promesa de compraventa.\n\n " +
"1. Una vez recibido por parte del ofertante, el valor mínimo como aceptación de la oferta, relacionado en el formato de solicitud de " +
"inmueble, como señal de intención de compra y separación del bien inmueble de su interés, el ofertado cuenta con un término máximo de 15 " +
"días hábiles para estudiar y suscribir la promesa de compraventa respectiva, que será enviada a la dirección de correo electrónico, " +
"referenciada en el respectivo formulario.\n\n" +
"2. Si el ofertado, por cualquier motivo se niega expresamente a firmar la promesa o no allega a nuestras oficinas dentro del término anterior " +
"la promesa de compraventa suscrita, se entenderá ésta omisión, como decisión tácita de no querer continuar con la compra del inmueble, y el " +
"inmueble escogido será puesto a disposición de terceros interesados, además, se procederá por parte del ofertante hacer devolución del " +
"dinero entregado por el ofertado, una vez realizado los descuentos respectivos de un 50% del salario mínimo mensual legal vigente. Así " +
"mismo, no habrá lugar a reconocimiento de intereses por el tiempo que el ofertado se encuentre en mora de solicitar la devolución del dinero " +
"entregado.\n\n" +
"3. Si el ofertado firma la promesa dentro del término otorgado o posterior a este, por voluntad de ambas partes, se procederá a partir de la " +
"fecha de la firma, conforme al tenor de la respectiva promesa y se hacen las partes acreedores de los derechos y obligaciones presentes en " +
"ella.",BaseColor.WHITE, Element.ALIGN_JUSTIFIED, 8f));
       
        table.completeRow();
        document.add(table);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        
        NegociacionEntity negociacion =  nr.findBy(Long.parseLong(id));
        List<TerceroEntity> clientes = terceroRepository.findByNegociacion(negociacion);
        List<PlanPagoEntity> planPago = planPagoRepository.findByNegociacion(negociacion);
        response.setContentType("application/pdf");
        try {
            
            Document document = new Document(PageSize.LETTER);
            document.setMargins(20, 20, 80, 30);
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            writer.setPageEvent(new HeaderFormatoSolicitud(negociacion));    
            document.open();

            PdfPTable table = new PdfPTable(3);
            
            table.setWidthPercentage(100);
            table.addCell(celda("PROYECTO",grisOscuro,Element.ALIGN_CENTER,10f));
            table.addCell(celda("INMUEBLE",grisOscuro,Element.ALIGN_CENTER,10f));
            table.addCell(celda("VALOR",grisOscuro,Element.ALIGN_CENTER,10f));
            
            table.addCell(celda(negociacion.getInmueble().getProyecto().getNombre(),BaseColor.WHITE, Element.ALIGN_CENTER,10f));
            table.addCell(celda(negociacion.getInmueble().getNumero(),BaseColor.WHITE, Element.ALIGN_CENTER,10f));
            table.addCell(celda(formatea.format(negociacion.getValorTotal()),BaseColor.WHITE, Element.ALIGN_CENTER,10f));

            document.add(table);
            
            table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell(celda("VENDEDOR(A)",grisOscuro, Element.ALIGN_CENTER,10f));
            table.addCell(celda(
                    negociacion.getVendedor().getTercero().getNombres()
                    +" "+negociacion.getVendedor().getTercero().getApellido1()
                    +" "+negociacion.getVendedor().getTercero().getApellido2(),
                    BaseColor.WHITE, Element.ALIGN_CENTER,10f
            ));
            table.addCell(celda("FECHA SOLICITUD", grisOscuro, Element.ALIGN_CENTER,10f));
                        
            table.addCell(celda(dateFormat.format(negociacion.getFecha()),BaseColor.WHITE, Element.ALIGN_CENTER,10f));
            document.add(table);
            
            table = new PdfPTable(1);
            table.setSpacingBefore(5f);             
            table.setWidthPercentage(100);
            table.addCell(celda("CLIENTE(S)",grisOscuro, Element.ALIGN_CENTER,10f));
            document.add(table);
            
            tableClientes(document, clientes);            
            
            tablaPlanPago(document, planPago);   
            
            table = new PdfPTable(1);
            table.setSpacingAfter(5f);
            table.setWidthPercentage(100);
            table.addCell(celda("OBSERVACION",grisOscuro, Element.ALIGN_CENTER, 10f));
            table.addCell(celda(negociacion.getObservacion(),BaseColor.WHITE, Element.ALIGN_JUSTIFIED, 8f));
            document.add(table);
            
            tablaCondiciones(document); 
            
            table = new PdfPTable(2);
            table.setSpacingBefore(1f);
            table.setWidthPercentage(100);
            PdfPCell celda = celda("FIRMAS",grisOscuro, Element.ALIGN_CENTER,10f);
            celda.setColspan(2);
            table.addCell(celda);
            
            for (TerceroEntity cliente : clientes) {
                celda = celda("\n\n\n\n"+cliente.getNombreCompleto(), BaseColor.WHITE, Element.ALIGN_CENTER, 8f);
                table.addCell(celda);
            }
            table.completeRow();
            document.add(table);
            
            
           
            document.close();

        } catch (DocumentException ex) {
            Logger.getLogger(SolicitudInmuebleItext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
