package io.swagger.util;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(Config.LoadType.MERGE)
@Sources({
		"file:src/test/resources/config/project.properties"
})
public interface WebServiceConfig extends Config{
	
	@Key("base.url")
	String baseURL();
}
