package com.javatar.fileuploader.config;


import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ReactResourceResolver implements ResourceResolver {
    // this is the same directory you are using in package.json
    // "build-spring": "react-scripts build &&  mv build static && mv static ../src/main/resources",
    // example REACT_DIR/index.html
    private static final String REACT_DIR = "/static/";

    // this is directory inside REACT_DIR for react static files
    // example REACT_DIR/REACT_STATIC_DIR/js/
    // example REACT_DIR/REACT_STATIC_DIR/css/
    private static final String REACT_STATIC_DIR = "static";

    private Resource index = new ClassPathResource(REACT_DIR + "index.html");
    private List<String> rootStaticFiles = Arrays.asList("favicon.io",
            "asset-manifest.json", "manifest.json", "service-worker.js");

    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath,
                                    List<? extends Resource> locations, ResourceResolverChain chain) {
        return resolve(requestPath, locations);
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        Resource resolvedResource = resolve(resourcePath, locations);
        if (resolvedResource == null) {
            return null;
        }
        try {
            return resolvedResource.getURL().toString();
        } catch (IOException e) {
            return resolvedResource.getFilename();
        }
    }

    private Resource resolve(String requestPath, List<? extends Resource> locations) {

        if (requestPath == null) return null;

        if (rootStaticFiles.contains(requestPath)
                || requestPath.startsWith(REACT_STATIC_DIR)) {
            return new ClassPathResource(REACT_DIR + requestPath);
        } else
            return index;
    }

}