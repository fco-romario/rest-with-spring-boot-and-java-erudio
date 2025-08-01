package br.com.fco_romario.file.exporter.impl;

import br.com.fco_romario.data.dto.PersonDTO;
import br.com.fco_romario.file.exporter.contract.FileExporter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PdfExporter implements FileExporter {

    @Override
    public Resource exportFile(List<PersonDTO> people) throws Exception {
        return null;
    }
}
