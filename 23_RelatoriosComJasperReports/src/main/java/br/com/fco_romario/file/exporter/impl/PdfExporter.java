package br.com.fco_romario.file.exporter.impl;

import br.com.fco_romario.data.dto.PersonDTO;
import br.com.fco_romario.exception.FileNotFoundException;
import br.com.fco_romario.file.exporter.contract.PersonExporter;
import br.com.fco_romario.services.QRCodeService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PdfExporter implements PersonExporter {

    @Autowired
    private QRCodeService qrCodeService;

    @Override
    public Resource exportPeople(List<PersonDTO> people) throws Exception {
        InputStream InputStream = getClass().getResourceAsStream("/templates/people.jrxml");
        if(InputStream == null) {
                throw new FileNotFoundException("Template file not found: /templates/people.jrxml");
        }

        JasperReport JasperReport = JasperCompileManager.compileReport(InputStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(people);

        Map<String, Object> parameters = new HashMap<>();
        //parameters.put("title", "Exemplo title como parametro");

        JasperPrint jasperPrint = JasperFillManager.fillReport(JasperReport, parameters, dataSource);

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    @Override
    public Resource exportPerson(PersonDTO person) throws Exception {
        InputStream mainTemplateStream = getClass().getResourceAsStream("/templates/person.jrxml");
        if(mainTemplateStream == null) {
            throw new FileNotFoundException("Template file not found: /templates/person.jrxml");
        }

        InputStream subTemplateStream = getClass().getResourceAsStream("/templates/books.jrxml");
        if(subTemplateStream == null) {
            throw new FileNotFoundException("Template file not found: /templates/books.jrxml");
        }

        JasperReport mainJasperReport = JasperCompileManager.compileReport(mainTemplateStream);
        JasperReport subJasperReport = JasperCompileManager.compileReport(subTemplateStream);

        InputStream qrCodeStream = qrCodeService.generateQRCode(person.getProfileUrl(), 200, 200);

        JRBeanCollectionDataSource subDataSource = new JRBeanCollectionDataSource(person.getBooks());

        String path = getClass().getResource("/templates/books.jasper").getPath();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SUB_REPORT_DATA_SOURCE", subDataSource);
        parameters.put("BOOK_SUB_REPORT", subJasperReport);
        parameters.put("SUB_REPORT_DIR", path);//passa para dentro do person.jrxml onde esta o sub relatório books.jrxml
        parameters.put("QR_CODE_IMAGE", qrCodeStream);

        JRBeanCollectionDataSource mainDataSource = new JRBeanCollectionDataSource(Collections.singleton(person));


        JasperPrint jasperPrint = JasperFillManager.fillReport(mainJasperReport, parameters, mainDataSource);
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }
}
