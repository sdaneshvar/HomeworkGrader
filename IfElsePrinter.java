package ecs160.visitor.astvisitors;

import org.eclipse.jdt.core.dom.ASTVisitor;

import org.eclipse.jdt.core.dom.IfStatement;

public class IfElsePrinter extends ASTVisitor {
	String className;
	int count = 0;
	
	public IfElsePrinter (String c) {
		className = c;
	}
	
	public boolean visit(IfStatement node)
	{
		FirstInstanceCreated InstCount = new FirstInstanceCreated(className);
		node.accept(InstCount);
		count = InstCount.count;
		if (count > 1) {
			return false;
		}
		return true; //True here allows us to recursively unwind an if statement with multiple blocks.
	}
}
