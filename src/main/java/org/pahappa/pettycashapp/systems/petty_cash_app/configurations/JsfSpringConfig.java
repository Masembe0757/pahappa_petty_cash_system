package org.pahappa.pettycashapp.systems.petty_cash_app.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.jsf.el.SpringBeanFacesELResolver;

@Configuration
public class JsfSpringConfig {

    @Bean
    public SpringBeanFacesELResolver springBeanFacesELResolver() {
        return new SpringBeanFacesELResolver();
    }
}
