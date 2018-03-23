package ecs160.visitor.astvisitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class ContextChecker extends ASTVisitor{
	public List<String> conMethodList = new ArrayList<>();

	public boolean visit(MethodDeclaration node) {
		conMethodList.add(node.getName().toString());
		return false;
	}
}
