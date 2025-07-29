package br.com.fco_romario.integrationtests.controllers.withXml;

import br.com.fco_romario.config.TestConfigs;
import br.com.fco_romario.integrationtests.dto.PersonDTO;
import br.com.fco_romario.integrationtests.dto.wrappers.xmlYaml.person.PagedModelPerson;
import br.com.fco_romario.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//Indica que os testes não serão executados em ordem aleatória (padrão do JUnit), mas seguirão uma ordem específica.
class PersonControllerXmlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static XmlMapper objectMapper;

    private static PersonDTO person;

    @BeforeAll  //uma instância para todo a class diferente do @BeforeEach
    static void setUp() {
        objectMapper = new XmlMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);//Desabilita a falha quando existem propriedades desconhecidas no JSON. No caso, os Links

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

        var content = given(specification)
            .contentType(MediaType.APPLICATION_XML_VALUE) //enviando xml
            .accept(MediaType.APPLICATION_XML_VALUE) //recebendo xmnl
                .body(person)
            .when()
                .post()
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
            .extract()
                .body()
                    .asString();

        PersonDTO createdPerson =  objectMapper.readValue(content, PersonDTO.class);
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

        var content = given(specification)
            .contentType(MediaType.APPLICATION_XML_VALUE)
            .accept(MediaType.APPLICATION_XML_VALUE)
                .body(person)
            .when()
                .put()
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
            .extract()
                .body()
                    .asString();

        PersonDTO createdPerson =  objectMapper.readValue(content, PersonDTO.class);
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
        var content = given(specification)
            .contentType(MediaType.APPLICATION_XML_VALUE)
            .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", person.getId())//o nome do parametro é o mesmo que indica no controller
            .when()
                .get("{id}")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
            .extract()
                .body()
                    .asString();

        PersonDTO createdPerson =  objectMapper.readValue(content, PersonDTO.class);
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
        var content = given(specification)
            .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", person.getId())//o nome do parametro é o mesmo que indica no controller
            .when()
                .patch("{id}")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
            .extract()
                .body()
                    .asString();

        PersonDTO createdPerson =  objectMapper.readValue(content, PersonDTO.class);
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
        var content = given(specification)
            .accept(MediaType.APPLICATION_XML_VALUE)
                .queryParam("page", 3, "size", 12, "direction", "asc")
                .when()
            .get()
                .then()
            .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
            .extract()
                .body()
                    .asString();

        PagedModelPerson wrapper = objectMapper.readValue(content, PagedModelPerson.class);
        List<PersonDTO> people = wrapper.getContent();

        PersonDTO personOne =  people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Allin",personOne.getFirstName());
        assertEquals("Emmot",personOne.getLastName());
        assertEquals("7913 Lindbergh Way",personOne.getAddress());
        assertEquals("Male",personOne.getGender());
        assertFalse(personOne.getEnabled());

        PersonDTO personFour =  people.get(4);

        assertNotNull(personFour.getId());
        assertTrue(personFour.getId() > 0);

        assertEquals("Alonso",personFour.getFirstName());
        assertEquals("Luchelli",personFour.getLastName());
        assertEquals("9 Doe Crossing Avenue",personFour.getAddress());
        assertEquals("Male",personFour.getGender());
        assertFalse(personFour.getEnabled());
    }

    @Test
    @Order(7)
    void findByNameTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("firstName", "and")
                .queryParam("page", 0, "size", 12, "direction", "asc")
                .when()
                .get("findByPeopleName/{firstName}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PagedModelPerson wrapper = objectMapper.readValue(content, PagedModelPerson.class);
        List<PersonDTO> people = wrapper.getContent();

        PersonDTO personOne =  people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Alessandro",personOne.getFirstName());
        assertEquals("McFaul",personOne.getLastName());
        assertEquals("5 Lukken Plaza",personOne.getAddress());
        assertEquals("Male",personOne.getGender());
        assertTrue(personOne.getEnabled());

        PersonDTO personFour =  people.get(4);

        assertNotNull(personFour.getId());
        assertTrue(personFour.getId() > 0);

        assertEquals("Brandyn",personFour.getFirstName());
        assertEquals("Grasha",personFour.getLastName());
        assertEquals("96 Mosinee Parkway",personFour.getAddress());
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
