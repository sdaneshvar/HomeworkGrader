package ecs160.visitor.astvisitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class PrivateStaticInstance extends ASTVisitor {
	/* Private member that holds results of visit.
	   Needed because we need to return a boolean in gradeA()
	   accept() does not return a boolean */
	private static boolean result = false;
	static String className;
	
	// Public constructor to save parameter "className" so we can check instance is of type of class 
	public PrivateStaticInstance(String c) {
		className = c;
	}

	// Get the private boolean result
	public boolean getBool() {
		return result;
	}
	
	public boolean visit(FieldDeclaration node) {
		List<VariableDeclarationFragment> frags = (List<VariableDeclarationFragment>)node.fragments();
		List<ASTNode> mods = (List<ASTNode>)node.modifiers();

		if (mods.size() == 2) {
			if (mods.get(0).toString().equals("private") && mods.get(1).toString().equals("static")) {
				if (node.getType().toString().equals(className)) {
					result = true;
				}
			}
		}
		return false;
	}

}
