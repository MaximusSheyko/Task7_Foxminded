package com.foxminded.Task7_SQL.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReaderResources {
    
    public static List<String> readFile(String fileName) {
	List<String> lines = new ArrayList<String>();
	try {
	    lines = Files.readAllLines(new File(Objects.requireNonNull(ClassLoader
	    	.getSystemClassLoader()
	    	.getResource(fileName)
	    	.getFile()))
	    	.toPath()
	    	.toAbsolutePath());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	return lines;
    }
}
