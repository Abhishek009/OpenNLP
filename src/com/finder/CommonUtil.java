package com.finder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class CommonUtil {
	
	public static boolean createDir(String modelDir) {
		
		File dir = new File(modelDir);
    	boolean isCreated = dir.mkdir();
    	if(isCreated) {
    		
    		return true;
    	}else {
    		
    		return false;
    	}
		
	}

	public static void display(Span[] nameSpans, String[] tokens) {

		if (nameSpans.length==0) {
			System.out.println("Nothing to display");
		}else {
			for (Span s : nameSpans)
				System.out.println(s.toString() + "  " + tokens[s.getStart()]);
		}

	}
	
	public static Span[] modelCall(String tokens[], String modelDir, String tokenName, String nameModel)
			throws Exception {
		System.out.println(String.format("===Calling %s model===",nameModel));
		// Loading the NER-person model
		InputStream inputStreamNameFinder = new FileInputStream(modelDir + "\\" + nameModel);
		
		TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
		// Instantiating the NameFinderME class
		NameFinderME nameFinder = new NameFinderME(model);
		
		// Finding the names in the sentence
		Span nameSpans[] = nameFinder.find(tokens);
		
		return nameSpans;
	}

	public static void downloadModel(String url, File destination) {

		URL website = null;
		try {
			website = new URL(url);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		ReadableByteChannel rbc = null;
		try {
			rbc = Channels.newChannel(website.openStream());
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(destination);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		try {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
