package edu.ucam.restcrud

import org.springframework.boot.SpringApplication

class TestRestCrudApplication {

    static void main(String[] args) {
        SpringApplication.from(RestCrudApplication::main).with(TestcontainersConfiguration).run(args)
    }

}
