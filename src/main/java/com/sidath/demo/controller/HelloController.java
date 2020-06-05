package com.sidath.demo.controller;

import com.sidath.demo.Model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/demo")
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping(path = "/hello", produces = "application/json")
    public ResponseEntity<Person> getHelloPerson() {

        //Construction the person object
        Person person = new Person();
        person.setGreeting("Hi");
        person.setName("Sidath");

        //Logging
        logger.info("API Invoked");

        //Constructing and returning the response
        return ResponseEntity.ok().body(person);

    }
}

