package bdtc.lab4.config;

import bdtc.lab4.controller.TestServiceController;
import bdtc.lab4.dao.TestServiceRepository;
import bdtc.lab4.model.PersonEntity;
import bdtc.lab4.service.TestBusinessLogicService;
import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@Configuration
@Import(IgniteConf.class)
public class ServiceConf {
    @Bean
    TestServiceRepository testServiceRepository(Ignite ignite, CacheConfiguration<UUID, PersonEntity> personCacheConf){
        return new TestServiceRepository(ignite, personCacheConf);
    }

    @Bean
    TestBusinessLogicService testBusinessLogicService(TestServiceRepository testServiceRepository){
        return new TestBusinessLogicService(testServiceRepository);
    }

    @Bean
    TestServiceController testServiceController(TestBusinessLogicService testBusinessLogicService){
        return new TestServiceController(testBusinessLogicService);
    }
}
