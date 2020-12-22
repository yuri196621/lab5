package bdtc.lab4.controller;

import bdtc.lab4.controller.model.Person;
import bdtc.lab4.model.PersonEntity;
import bdtc.lab4.service.TestBusinessLogicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
public class TestServiceController {

    private TestBusinessLogicService testBusinessLogicService;

    public TestServiceController(TestBusinessLogicService testBusinessLogicService) {
        this.testBusinessLogicService = testBusinessLogicService;
    }

    @PostMapping(path = {"/create"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonEntity> createPerson(@RequestBody Person person) {
        PersonEntity personEntity = testBusinessLogicService.processCreate(person);
        return new ResponseEntity<>(personEntity, HttpStatus.OK);
    }

    @GetMapping(path = {"/get/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonEntity> getPerson(@PathVariable String id) {
        PersonEntity personEntity = testBusinessLogicService.processGet(id);
        return new ResponseEntity<>(personEntity, HttpStatus.OK);
    }

    @GetMapping(path = {"/get/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonEntity>> getAll() {
        List<PersonEntity> personEntities = testBusinessLogicService.processGetAll();
        return new ResponseEntity<>(personEntities, HttpStatus.OK);
    }
}
