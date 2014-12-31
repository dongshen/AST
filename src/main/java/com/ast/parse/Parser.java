package com.ast.parse;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.ast.visitor.DemoVisitor;

public class Parser {

	// use ASTParse to parse string
	public static void parseDemoVisitor(char[] input) {

		DemoVisitor visitor = new DemoVisitor();
		
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(input);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(visitor);
	}
}
