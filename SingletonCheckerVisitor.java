package ecs160.visitor.astvisitors;

import java.io.File;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import ecs160.visitor.utilities.UtilReader;

public class SingletonCheckerVisitor {
	
	static CompilationUnit cu = (CompilationUnit) null;
	public static String className;
	
	public SingletonCheckerVisitor (String s) {
		className = s;
	}
	
	public static SingletonCheckerVisitor setUpGrader(String f, String c) {
		SingletonCheckerVisitor visitor = new SingletonCheckerVisitor(c);
		File file = new File(f);
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

//	    cu.accept(new PrivateConstructor()); // Begin searching the tree for member declarations
    		//cu.accept(new MethodPrinter()); //Begin searching the tree for method declarations.
		
    		return visitor;
	}

	public boolean gradeA() {
		PrivateConstructor p = new PrivateConstructor();
		cu.accept(p);
		return p.getBool();
	}

	public boolean gradeB() {
		PublicStaticMethod p = new PublicStaticMethod(className);
		cu.accept(p);
		return p.getBool();
	}

	public boolean gradeC() {
		PrivateStaticInstance p = new PrivateStaticInstance(className);
		cu.accept(p);
		return p.getBool();
	}

	public boolean gradeD() {
		ReturnsOne p = new ReturnsOne(className);
		cu.accept(p);
		if (p.counter != 1) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
