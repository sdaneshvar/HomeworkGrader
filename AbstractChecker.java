package ecs160.visitor.astvisitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;


public class AbstractChecker extends ASTVisitor {
	
	public List<String> abMethodList = new ArrayList<>();
	
	public boolean visit(MethodDeclaration node) {
		abMethodList.add(node.getName().toString());
		return false;
	}
}
