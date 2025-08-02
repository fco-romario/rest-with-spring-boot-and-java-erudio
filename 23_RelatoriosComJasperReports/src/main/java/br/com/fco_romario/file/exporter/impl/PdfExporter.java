package br.com.fco_romario.file.exporter.impl;

import br.com.fco_romario.data.dto.PersonDTO;
import br.com.fco_romario.exception.FileNotFoundException;
import br.com.fco_romario.file.exporter.contract.FileExporter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PdfExporter implements FileExporter {

    @Override
    public Resource exportFile(List<PersonDTO> people) throws Exception {
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
}
