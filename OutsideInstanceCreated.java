package ecs160.visitor.astvisitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

public class OutsideInstanceCreated extends ASTVisitor{
	public int count = 0;
	String className;

	public OutsideInstanceCreated(String c) {
		className = "new " + c + "()";
	}
	
	public boolean visit(ClassInstanceCreation node)
	{
		if (node.toString().equals(className) ) {
			count++;
			return false;
		}

		return true;		
	}
}
