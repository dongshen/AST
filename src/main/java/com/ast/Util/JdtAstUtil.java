package com.ast.Util;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class JdtAstUtil {
	/**
	 * get compilation unit of source code
	 * 
	 * @param javaFilePath
	 * @return CompilationUnit
	 */
	public static CompilationUnit getCompilationUnit(char[] input) {

		// Java编程规范（Java Language Specification，简写为JLS），此参数为常量，例如AST.JLS3
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		
		// 方法setSource()针对不同形式的源代码作为参数而进行了重载，主要分类为字符数组形式（char[]）和JavaModel形式（ICompilationUnit、IClassFile等）。
		astParser.setSource(input);
		
		/**
		 * 其中Kind of Construct是所需解析的代码的类型，包括： 
		 * K_COMPILATION_UNIT：编译单元，即一个Java文件
		 * K_CLASS_BODY_DECLARATIONS：类的声明 
		 * K_EXPRESSION：单个表达式
		 * K_STATEMENTS：语句块
		 * 
		 */
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		// createAST()方法的参数类型为IProgressMonitor，用于对AST的转换进行监控，不需要的话就填个null即可
		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));

		return result;
	}

}