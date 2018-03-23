package ecs160.visitor.astvisitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class PrivateConstructor extends ASTVisitor {
	
	/* Private member that holds results of visit.
	   Needed because we need to return a boolean in gradeA()
	   accept() does not return a boolean */
	private static boolean result = false;
	
	// Get the private boolean result
	public boolean getBool() {
		return result;
	}
	
	// Takes in a MethodDeclaration ASTNode
	public boolean visit(MethodDeclaration node)
	{
		// Create list of modifiers (ex: private, public, static, etc.)
		List<ASTNode> mods = (List<ASTNode>) node.modifiers();
		if (node.isConstructor() == true) { // Check if the node is a constructor
			if (mods.size() == 1) { // Check how many modifiers it has (Should only have 1, which is "private")
				if (mods.get(0).toString().equals("private")) { // Check that the 1 modifier is "private"
					if (node.getReturnType2() == null) { // Check that the private constructor does not return anything. Might not need to check since isConstructor checks for you. Might as well be safe!!
						result = true;
					}
				}
			}
		}
		return false;
	}
}