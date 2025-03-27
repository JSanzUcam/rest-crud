package edu.ucam.restcrud

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration)
@SpringBootTest
class RestCrudApplicationTests {

    @Test
    void contextLoads() {
    }

}
