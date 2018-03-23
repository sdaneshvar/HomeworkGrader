package ecs160.visitor.astvisitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleType;

public class PublicStaticMethod extends ASTVisitor {
	/* Private member that holds results of visit.
	   Needed because we need to return a boolean in gradeA()
	   accept() does not return a boolean */
	private static boolean result = false;
	static String className;
	
	// Public constructor to save parameter "className" so we can check instance is of type of class 
	public PublicStaticMethod(String c) {
		className = c;
	}

	// Get the private boolean result
	public boolean getBool() {
		return result;
	}
	
	// Takes in a MethodDeclaration ASTNode
	public boolean visit(MethodDeclaration node)
	{
		// Create list of modifiers (ex: private, public, static, etc.)
		List<ASTNode> mods = (List<ASTNode>) node.modifiers();
		
		if (node.isConstructor() == false) { // Check if the node is a constructor
				if (mods.get(0).toString().equals("public") && mods.get(mods.size()-1).toString().equals("static")) { // Check that the 1 modifier is "private"
					if (((SimpleType)(node.getReturnType2())).getName().toString().equals(className)) { // Check that the private constructor does not return anything. Might not need to check since isConstructor checks for you. Might as well be safe!!
						result = true;
					}
				}
		}
	
		return false;
	}
	

}
