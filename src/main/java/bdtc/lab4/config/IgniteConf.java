package bdtc.lab4.config;

import bdtc.lab4.model.PersonEntity;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpringBean;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.DiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.UUID;

@Configuration
public class IgniteConf {

    public static final UUID instanceName = UUID.randomUUID();

    @Value("${testservice.ignite.localAddr:127.0.0.1}")
    private String localAddr;

    @Value("${testservice.ignite.ipFinderAddresses:dummy}")
    private String ipFinderAddresses;

    @Value("${testservice.ignite.clientMode:false}")
    private boolean clientMode;

    @Value("${testservice.ignite.kuberMode:false}")
    private boolean kuberMode;

    @Value("${testservice.ignite.serviceName}")
    private String serviceName;

    @Value("${testservice.ignite.namespace}")
    private String namespace;

    @Value("${testservice.ignite.masterUrl}")
    private String masterUrl;

    @Bean
    public TcpDiscoveryIpFinder tcpDiscoveryIpFinder() {
        TcpDiscoveryMulticastIpFinder tcpDiscoveryIpFinder = new TcpDiscoveryMulticastIpFinder();
        tcpDiscoveryIpFinder.setLocalAddress(localAddr);
        if (!"dummy".equals(ipFinderAddresses)) {
            tcpDiscoveryIpFinder.setAddresses(Collections.singletonList(ipFinderAddresses));
        }

        return tcpDiscoveryIpFinder;
    }

    @Bean
    public DiscoverySpi discoverySpi(TcpDiscoveryIpFinder tcpDiscoveryIpFinder) {
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setLocalAddress(localAddr);
        discoverySpi.setIpFinder(tcpDiscoveryIpFinder);

        return discoverySpi;
    }

    @Bean
    public DiscoverySpi kuberDiscoverySpi() {
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        TcpDiscoveryKubernetesIpFinder ipFinder = new TcpDiscoveryKubernetesIpFinder();
        ipFinder.setServiceName(serviceName);
        ipFinder.setNamespace(namespace);
        ipFinder.setMasterUrl(masterUrl);
        discoverySpi.setIpFinder(ipFinder);

        return discoverySpi;
    }


    @Bean
    public IgniteConfiguration igniteConfiguration(DiscoverySpi discoverySpi, DiscoverySpi kuberDiscoverySpi) {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setIgniteInstanceName(instanceName.toString());
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        if (kuberMode){
            System.out.println("Ignite config - kuber");
            System.out.println(kuberDiscoverySpi);
            igniteConfiguration.setDiscoverySpi(kuberDiscoverySpi);
        }
        else {
            System.out.println("Ignite config - local");
            igniteConfiguration.setClientMode(clientMode);
            igniteConfiguration.setDiscoverySpi(discoverySpi);
            System.out.println(discoverySpi);
        }

        return igniteConfiguration;
    }

    @Bean
    public Ignite ignite(IgniteConfiguration configuration) {
        IgniteSpringBean igniteSpringBean = new IgniteSpringBean();
        igniteSpringBean.setConfiguration(configuration);

        return igniteSpringBean;
    }

    @Bean
    public CacheConfiguration<UUID, PersonEntity> ignitePersonCacheConfiguration() {
        CacheConfiguration<UUID, PersonEntity> personCacheCfg = new CacheConfiguration<>();
        personCacheCfg.setName("person");
        personCacheCfg.setCacheMode(CacheMode.PARTITIONED);
        personCacheCfg.setBackups(2);
        personCacheCfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        personCacheCfg.setIndexedTypes(UUID.class, PersonEntity.class);

        return personCacheCfg;
    }
}