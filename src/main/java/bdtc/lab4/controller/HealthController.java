package bdtc.lab4.controller;

import bdtc.lab4.controller.model.Health;
import bdtc.lab4.service.TestBusinessLogicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public class HealthController {

    private TestBusinessLogicService testBusinessLogicService;

    public HealthController(TestBusinessLogicService testBusinessLogicService) {
        this.testBusinessLogicService = testBusinessLogicService;
    }

    @GetMapping(path = {"/health"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Health> getHealth() {
        testBusinessLogicService.processCheck();
        return new ResponseEntity<>(new Health(), HttpStatus.OK);
    }

}
