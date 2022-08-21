package com.javatar.fileuploader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.ResourceResolver;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceResolver resolver = new ReactResourceResolver();
        registry.addResourceHandler("/**")
                .resourceChain(true)
                .addResolver(resolver);

        // Can try to play with
        // registry.addResourceHandler("/**")
        //        .addResourceLocations("classpath:/static/");
        // But this option can't map every path to index.html
        // Can try https://stackoverflow.com/a/42998817/1032167
        // to resolve this, but then you loose /api/** => rest
        // and to be honest it is some regex madness, so
        // it was easier for me to setup custom resource resolver
    }

}