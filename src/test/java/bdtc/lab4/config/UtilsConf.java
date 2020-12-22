package bdtc.lab4.config;


import bdtc.lab4.dao.TestServiceRepository;
import bdtc.lab4.utils.EntityUtils;
import org.apache.ignite.Ignite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UtilsConf {
    @Bean
    EntityUtils entityUtils(TestServiceRepository testServiceRepository, Ignite ignite){
        return new EntityUtils(testServiceRepository, ignite);
    }
}
