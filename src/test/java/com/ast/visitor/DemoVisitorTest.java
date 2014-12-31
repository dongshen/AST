package com.ast.visitor;

import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import com.ast.Util.FileUtil;
import com.ast.Util.JdtAstUtil;

public class DemoVisitorTest {

	@Test
	public void testVisitFieldDeclaration() {
		String path = "ClassDemo.java";
		char[] output = null;
		try {
			output = FileUtil.convertFileToArray(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		CompilationUnit comp = JdtAstUtil.getCompilationUnit(output);
		DemoVisitor visitor = new DemoVisitor();
		comp.accept(visitor);
	}

}
