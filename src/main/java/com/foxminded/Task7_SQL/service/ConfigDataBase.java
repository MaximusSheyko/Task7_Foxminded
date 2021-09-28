package com.foxminded.Task7_SQL.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ConfigDataBase {
    public static Properties getConfig() {
	var properties = new Properties();
	
	try (var config = new FileInputStream((new File(Objects
	    	.requireNonNull(ClassLoader.getSystemClassLoader()
	    		.getResource("config_db.properties")
	    		.getFile()))))){
	    properties.load(config);    
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	return properties;
    }
}
