package ecs160.visitor.astvisitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class ConInAbstractChecker extends ASTVisitor{
	public static List<String> m = new ArrayList<>();
	public int count = 0;
	
	public ConInAbstractChecker(List<String> matches) {
		m = matches;
	}
	
	public boolean visit(MethodInvocation node) {
		if (m.contains(node.getName().toString())) {
			count++;
		}
		return false;
	}
}
