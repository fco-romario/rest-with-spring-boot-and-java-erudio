package br.com.fco_romario.file.importer.impl;

import br.com.fco_romario.data.dto.PersonDTO;
import br.com.fco_romario.file.importer.contract.FileImporter;

import java.io.InputStream;
import java.util.List;

public class XlsxImporter implements FileImporter {

    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        return List.of();
    }
}
