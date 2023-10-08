package springboot.flowershop_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import springboot.flowershop_be.filters.AuthFilter;

@SpringBootApplication
public class FlowershopBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowershopBeApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/flowers/*");
		registrationBean.addUrlPatterns("/api/categories/*");
		return registrationBean;
	}
}
