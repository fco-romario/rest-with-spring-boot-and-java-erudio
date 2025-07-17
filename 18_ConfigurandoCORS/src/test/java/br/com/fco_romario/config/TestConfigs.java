package br.com.fco_romario.config;

public interface TestConfigs {
    int SERVER_PORT = 8888;

    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";

    String ORIGIN_EXEMPLO = "http://outra-origem-exemplo.com";
    String ORIGIN_EXEMPLO_NAO_AUTORIZADA = "http://origem-exemplo-nao-autorizada.com";
    String ORIGIN_LOCALHOST = "http://localhost:8080";

}
