package com.finder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class ModelFinder {
	
	private static String modelName=null;
	private static String sentence=null; 
	private static String modelDir=null;
	private static String tokenName=null;
	
public static void readConfig(String[] args) {
		
	Options options = new Options();

    Option method = new Option("method", "method", true, "Method name[person|location|date|time|money|organization|percentage]");
    method.setRequired(true);
    options.addOption(method);

    Option text = new Option("text", "text", true, "[text]");
    text.setRequired(true);
    options.addOption(text);

    Option modelDirectory = new Option("modelDir", "modelDir", true, "Mention the model directory location");
    modelDirectory.setRequired(false); 
	options.addOption(modelDirectory);
	
	Option tokenModelName = new Option("tokenName", "help", true, "Token name to be used. Default is en-token.bin");
	tokenModelName.setRequired(false); 
	options.addOption(tokenModelName);
	
	Option help = new Option("help", "help", true, "shows the script usage help text");
	help.setRequired(false); 
	options.addOption(help);
	
    
    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd = null;

    try {
        cmd = parser.parse(options, args);
    } catch (org.apache.commons.cli.ParseException e) {
        System.out.println(e.getMessage());
        formatter.printHelp("ModelFinder", options);

        System.exit(1);
    }

    modelName = cmd.getOptionValue("method");
    sentence = cmd.getOptionValue("text");

    
    if(modelDir == null) {
    	modelDir = "model";
    	if(CommonUtil.createDir(modelDir)){ 
    		System.out.println("Directory create at "+new File(modelDir).getPath());
    	}else {
    		System.out.println("Could not create the directory");
    	}
    }
    else {
    	if(CommonUtil.createDir(modelDir)){ 
    		System.out.println("Directory create at "+new File(modelDir).getPath());
    	}else {
    	System.out.println("Could not create the directory");
    	}
    }
	if(tokenName == null) {
		tokenName = "en-token.bin";
	}
   
	}
	

	public static void main(String[] args) throws IOException {
		
		readConfig(args);
		
		
		//Checking the model file
		File modelFile = new File(modelDir+"\\"+modelName);
		System.out.println(String.format("Checking if %s model is present",modelName));
		if(!modelFile.exists()) {
			System.out.println(String.format("%s model not present. So downloading the it.",modelName));
			CommonUtil.downloadModel("http://opennlp.sourceforge.net/models-1.5/"+modelName, new File(modelDir+"\\"+modelName));	
			
		}else {
			System.out.println("Model already present");
		}
		
		// Checking the token file
		File tokenNameFile = new File(modelDir+"\\"+tokenName);
		System.out.println(String.format("Checking if %s token is present",tokenName));
		if(!tokenNameFile.exists()) {
			System.out.println(String.format("%s model not present. So downloading the it.",tokenName));
			CommonUtil.downloadModel("http://opennlp.sourceforge.net/models-1.5/"+tokenName, new File(modelDir+"\\"+tokenName));	
			
		}else {
			System.out.println("Model already present");
		}
		
		InputStream inputStreamTokenizer = new FileInputStream(modelDir + "\\" + tokenName);
        TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);
        TokenizerME tokenizer = new TokenizerME(tokenModel);
        String tokens[] = tokenizer.tokenize(sentence);
		try {
			Span nameSpans[] = CommonUtil.modelCall(tokens, modelDir, tokenName, modelName);
			CommonUtil.display(nameSpans, tokens);
		} catch (Exception e) {
			e.printStackTrace();
		}

		

	}


}
