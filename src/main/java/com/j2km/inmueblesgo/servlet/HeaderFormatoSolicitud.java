package com.j2km.inmueblesgo.servlet;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jk
 */
public class HeaderFormatoSolicitud implements PdfPageEvent{

    private NegociacionEntity negociacion = null;
    
    public HeaderFormatoSolicitud(NegociacionEntity negociacion) {
        this.negociacion = negociacion;
    }
    
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            cb.beginText();
            cb.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED), 22);
            
            cb.showTextAligned(Element.ALIGN_LEFT, "FORMATO DE SOLICITUD DE INMUEBLE", document.leftMargin(), 720, 0);
            
            cb.endText();            
            Image img = Image.getInstance(negociacion.getInmueble().getProyecto().getEmpresa().getLogo().getRutaFisica());
            img.scaleToFit(1000, 50);
            img.setBorder(100);
            img.setAbsolutePosition(new Float(document.getPageSize().getWidth() - document.rightMargin() - img.getScaledWidth()) , 710);
            document.add(img);
            /*cb.moveTo(document.leftMargin(),685)
            cb.lineTo(new Float(document.pageSize.width - document.leftMargin()),685)
            cb.moveTo(document.leftMargin(),683)
            cb.lineTo(new Float(document.pageSize.width - document.leftMargin()),683)
            cb.stroke()
            //verificar si se dibuja el membrete
            /*if(membrete){
            Image img = Image.getInstance(cee.empresa.imagen)
            img.scaleToFit(1000, 50)
            img.setBorder(100)
            img.setAbsolutePosition(new Float(document.pageSize.width - document.rightMargin() - img.scaledWidth) , 686)
            document.add(img)
            cb.moveTo(document.leftMargin(),685)
            cb.lineTo(new Float(document.pageSize.width - document.leftMargin()),685)
            cb.moveTo(document.leftMargin(),683)
            cb.lineTo(new Float(document.pageSize.width - document.leftMargin()),683)
            cb.stroke()
            }*/
        } catch (DocumentException ex) {
            Logger.getLogger(HeaderFormatoSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HeaderFormatoSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onParagraph(PdfWriter writer, Document document, float paragraphPosition) {
       
    }

    @Override
    public void onParagraphEnd(PdfWriter writer, Document document, float paragraphPosition) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onChapterEnd(PdfWriter writer, Document document, float paragraphPosition) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onSection(PdfWriter writer, Document document, float paragraphPosition, int depth, Paragraph title) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onSectionEnd(PdfWriter writer, Document document, float paragraphPosition) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
