package ecs160.visitor.astvisitors;

import java.io.File;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import ecs160.visitor.utilities.UtilReader;

public class StateCheckerVisitor {
	
	static CompilationUnit cu = (CompilationUnit) null;
	static CompilationUnit cu2 = (CompilationUnit) null;
	// LibraryBook
	public static String ContextName;
	// LibraryBook path
	public static String ContextPath;
	// LBState
	public static String AbstractName;
	// LBState path
		public static String AbstractPath;
		
	public StateCheckerVisitor (String string, String string2, String string3, String string4) {
		ContextPath = string;
		ContextName = string2;
		AbstractPath = string3;
		AbstractName = string4;
	}

	public static StateCheckerVisitor setUpGrader(String string, String string2, String string3, String string4) {
		StateCheckerVisitor visitor = new StateCheckerVisitor(string, string2, string3, string4);
		
		// Abstract File
		File file = new File(string3);
		String text = "";
		try {
			text = UtilReader.read(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	    	@SuppressWarnings("deprecation")
			ASTParser parser = ASTParser.newParser(AST.JLS8); //Create a parser for a version of the Java language (8 here)
	    	Map<String, String> options = JavaCore.getOptions(); //get the options for a type of Eclipse plugin that is the basis of Java plugins
	    	options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8); //Specify that we are on Java 8 and add it to the options...
	    	parser.setCompilerOptions(options); //forward all these options to our parser
	    	parser.setKind(ASTParser.K_COMPILATION_UNIT); //What kind of constructions will be parsed by this parser.  K_COMPILATION_UNIT means we are parsing whole files.
	    	parser.setResolveBindings(true); //Enable looking for bindings/connections from this file to other parts of the program.
	    	parser.setBindingsRecovery(true); //Also attempt to recover incomplete bindings (only can be set to true if above line is set to true).
	    	String[] classpath = { System.getProperty("java.home") + "/lib/rt.jar" }; //Link to your Java installation.
	    	parser.setEnvironment(classpath, new String[] { "" }, new String[] { "UTF-8" }, true);
	    	parser.setSource(text.toCharArray()); //Load in the text of the file to parse.
	    	parser.setUnitName(file.getAbsolutePath()); //Load in the absolute path of the file to parse
	    cu = (CompilationUnit) parser.createAST(null);
	    //CompilationUnit cu = (CompilationUnit) parser.createAST(null); //Create the tree and link to the root node.
	    
	    // Context File
	    File file2 = new File(string);
		String text2 = "";
		try {
			text2 = UtilReader.read(file2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	    	@SuppressWarnings("deprecation")
			ASTParser parser2 = ASTParser.newParser(AST.JLS8); //Create a parser for a version of the Java language (8 here)
	    	Map<String, String> options2 = JavaCore.getOptions(); //get the options for a type of Eclipse plugin that is the basis of Java plugins
	    	options2.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8); //Specify that we are on Java 8 and add it to the options...
	    	parser2.setCompilerOptions(options2); //forward all these options to our parser
	    	parser2.setKind(ASTParser.K_COMPILATION_UNIT); //What kind of constructions will be parsed by this parser.  K_COMPILATION_UNIT means we are parsing whole files.
	    	parser2.setResolveBindings(true); //Enable looking for bindings/connections from this file to other parts of the program.
	    	parser2.setBindingsRecovery(true); //Also attempt to recover incomplete bindings (only can be set to true if above line is set to true).
	    	String[] classpath2 = { System.getProperty("java.home") + "/lib/rt.jar" }; //Link to your Java installation.
	    	parser2.setEnvironment(classpath2, new String[] { "" }, new String[] { "UTF-8" }, true);
	    	parser2.setSource(text2.toCharArray()); //Load in the text of the file to parse.
	    	parser2.setUnitName(file2.getAbsolutePath()); //Load in the absolute path of the file to parse
	    cu2 = (CompilationUnit) parser2.createAST(null);
//	    cu.accept(new PrivateConstructor()); // Begin searching the tree for member declarations
    		//cu.accept(new MethodPrinter()); //Begin searching the tree for method declarations.
		
    		return visitor;
	}

	public boolean gradeA() {
		// Abstract parser
		AbstractChecker a = new AbstractChecker();
		cu.accept(a);
		
		// Context Parser
		ContextChecker b = new ContextChecker();
		cu2.accept(b);
		
		// Check if Abstract classes are in Context class
		for (String i : a.abMethodList) {
			if (!b.conMethodList.contains(i)) {
				return false;
			}
		}
		return true;
	}

	public int gradeB() {
		// Abstract parser
		AbstractChecker a = new AbstractChecker();
		cu.accept(a);
		
		// Context Parser
		ContextChecker b = new ContextChecker();
		cu2.accept(b);
		
		List<String> matches = new ArrayList<>();

		// Check if Abstract classes are in Context class
		for (String i : a.abMethodList) {
			if (!b.conMethodList.contains(i)) {
				return 0;
			}
			matches.add(i);
		}
		ConInAbstractChecker c = new ConInAbstractChecker(matches);
		cu2.accept(c);
		return c.count;

	}
}
