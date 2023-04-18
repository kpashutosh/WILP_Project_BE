package wilp.project.airlineecommerce;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AirlineEcommerceApplication extends SpringBootServletInitializer {
	
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AirlineEcommerceApplication.class);
    }

    public static void main(String[] args) {
    	new AirlineEcommerceApplication().configure(new SpringApplicationBuilder(AirlineEcommerceApplication.class)).run(args);
    }

}
