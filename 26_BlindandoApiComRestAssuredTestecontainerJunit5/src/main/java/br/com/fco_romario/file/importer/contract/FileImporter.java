package br.com.fco_romario.file.importer.contract;

import br.com.fco_romario.data.dto.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {
    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
