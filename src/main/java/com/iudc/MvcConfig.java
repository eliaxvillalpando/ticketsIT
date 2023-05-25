
package com.iudc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.context.annotation.*;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
 
@Configuration
public class MvcConfig implements WebMvcConfigurer {
 
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
     
        registry.addViewController("/login").setViewName("login");
         
    }
    
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory("Perifericos/", registry);
		
	}
    
    private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
		Path path = Paths.get(pathPattern);
		String absolutePath = path.toFile().getAbsolutePath();
		
		String logicalPath = pathPattern.replace("../", "") + "/**";
				
		registry.addResourceHandler(logicalPath)
			.addResourceLocations("file:" + absolutePath + "/");		
	}


    
}