package swatechgolf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync (proxyTargetClass=true)
public class SwaTechGolf {
	
	@Autowired private SwaTechGolfController _testController;
	
    public static void main(String[] args) {
        SpringApplication.run(SwaTechGolf.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
        	_testController.process(args);
        };
    }
}
