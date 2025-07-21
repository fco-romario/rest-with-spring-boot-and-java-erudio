package br.com.fco_romario.integrationtests.controllers.withyaml;

import br.com.fco_romario.config.TestConfigs;
import br.com.fco_romario.integrationtests.controllers.withyaml.mapper.YAMLMapper;
import br.com.fco_romario.integrationtests.dto.PersonDTO;
import br.com.fco_romario.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//Indica que os testes não serão executados em ordem aleatória (padrão do JUnit), mas seguirão uma ordem específica.
class PersonControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YAMLMapper objectMapper;

    private static PersonDTO person;

    @BeforeAll  //uma instância para todo a class diferente do @BeforeEach
    static void setUp() {
        objectMapper = new YAMLMapper();
        person = new PersonDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_EXEMPLO)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL)) // Mostra log to que esta vindo
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) // Mostra log to que esta indo
                .build();

        var createdPerson = given().config(
                RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )
            .spec(specification)
            .contentType(MediaType.APPLICATION_YAML_VALUE) //enviando xml
            .accept(MediaType.APPLICATION_YAML_VALUE) //recebendo xmnl
                .body(person, objectMapper) //objectMapperpara serializar com nosso mapper YML
            .when()
                .post()
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
            .extract()
                .body()
                    .as(PersonDTO.class, objectMapper);// para deserializar com nosso mapper YML

        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus",createdPerson.getFirstName());
        assertEquals("Torvalds",createdPerson.getLastName());
        assertEquals("Helsinki - Finland",createdPerson.getAddress());
        assertEquals("Male",createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        person.setLastName("Benedict Torvalds");

        var createdPerson = given().config(
                RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )
            .spec(specification)
            .contentType(MediaType.APPLICATION_YAML_VALUE)
            .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(person, objectMapper)
            .when()
                .put()
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
            .extract()
                .body()
                    .as(PersonDTO.class, objectMapper);// para deserializar com nosso mapper YML

        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus",createdPerson.getFirstName());
        assertEquals("Benedict Torvalds",createdPerson.getLastName());
        assertEquals("Helsinki - Finland",createdPerson.getAddress());
        assertEquals("Male",createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {
        var createdPerson = given().config(
                RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )
            .spec(specification)
            .contentType(MediaType.APPLICATION_YAML_VALUE)
            .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", person.getId())//o nome do parametro é o mesmo que indica no controller
            .when()
                .get("{id}")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
            .extract()
                .body()
                    .as(PersonDTO.class, objectMapper);// para deserializar com nosso mapper YML

        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus",createdPerson.getFirstName());
        assertEquals("Benedict Torvalds",createdPerson.getLastName());
        assertEquals("Helsinki - Finland",createdPerson.getAddress());
        assertEquals("Male",createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());
    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {
        var createdPerson = given().config(
                RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )
            .spec(specification)
            .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", person.getId())//o nome do parametro é o mesmo que indica no controller
            .when()
                .patch("{id}")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
            .extract()
                .body()
                    .as(PersonDTO.class, objectMapper);// para deserializar com nosso mapper YML

        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus",createdPerson.getFirstName());
        assertEquals("Benedict Torvalds",createdPerson.getLastName());
        assertEquals("Helsinki - Finland",createdPerson.getAddress());
        assertEquals("Male",createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());
    }

    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {

        given(specification)
                .pathParam("id", person.getId())
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {
        var response = given(specification)
            .accept(MediaType.APPLICATION_YAML_VALUE)
                .when()
            .get()
                .then()
            .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
            .extract()
                .body()
                    .as(PersonDTO[].class, objectMapper);// para deserializar com nosso mapper YML

        List<PersonDTO> people = Arrays.asList(response);

        PersonDTO personOne =  people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Ayrton",personOne.getFirstName());
        assertEquals("Senna",personOne.getLastName());
        assertEquals("São Paulo - Brasil",personOne.getAddress());
        assertEquals("Male",personOne.getGender());
        assertTrue(personOne.getEnabled());

        PersonDTO personFour =  people.get(4);

        assertNotNull(personFour.getId());
        assertTrue(personFour.getId() > 0);

        assertEquals("Muhamamd",personFour.getFirstName());
        assertEquals("Ali",personFour.getLastName());
        assertEquals("Kentucky - US",personFour.getAddress());
        assertEquals("Male",personFour.getGender());
        assertTrue(personFour.getEnabled());
    }

    private void mockPerson() {
        person.setFirstName("Linus");
        person.setLastName("Torvalds");
        person.setAddress("Helsinki - Finland");
        person.setGender("Male");
        person.setEnabled(true);
    }
}
