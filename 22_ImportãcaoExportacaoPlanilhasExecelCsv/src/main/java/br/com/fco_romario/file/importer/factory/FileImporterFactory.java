package br.com.fco_romario.file.importer.factory;

import br.com.fco_romario.exception.BadRequestException;
import br.com.fco_romario.file.importer.contract.FileImporter;
import br.com.fco_romario.file.importer.impl.CsvImporter;
import br.com.fco_romario.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component //Para poder injetar esta classe em outras classes quer for utilizar
public class FileImporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext context; // faz com que o spring crie o bean e nao precise usar new.

    public FileImporter getImporter(String fileName) throws Exception {
        if(fileName.endsWith(".xlsx")) {
            return context.getBean(XlsxImporter.class);
            //return new XlsxImporter();
        } else if(fileName.endsWith(".csv")){
            return context.getBean(CsvImporter.class);
            //return new CsvImporter();
        } else {
            throw new BadRequestException("Invalid file format!");
        }
    }
}
