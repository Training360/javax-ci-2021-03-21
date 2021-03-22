package employees;

import employees.service.CreateEmployeeCommand;
import employees.service.EmployeeDto;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.with;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeesIT {

    @Autowired
    MockMvc mockMvc;


    @BeforeEach
    void init() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.requestSpecification =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON);
    }



    @Test
    void testCreate() {
        EmployeeDto employeeDto = with()
                //.body("{  \"name\": \"John Doe\" }")
                .body(new CreateEmployeeCommand("John Doe"))
                .post("/api/employees")
        .then()
            .statusCode(201)
                .assertThat()
                .extract().as(EmployeeDto.class);

        //.body("name", equalTo("John Doe"))
        //.log();

        assertEquals("John Doe", employeeDto.getName());
        assertTrue(employeeDto.getId() > 0);
    }

}
