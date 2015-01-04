package com.ast.visitor;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.junit.Test;

import com.ast.Util.FileUtil;
import com.ast.Util.JdtAstUtil;

public class Demo2VisitorTest {

	static Logger logger = Logger.getLogger(Demo2VisitorTest.class);

	@Test
	public void testVisitFieldDeclaration() {
		String path = "ClassDemo2.java";
		char[] output = null;
		try {
			output = FileUtil.convertFileToArray(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CompilationUnit unit = JdtAstUtil.getCompilationUnit(output);
		AST rootNode = unit.getAST();

		// check package info
		PackageDeclaration packageDeclaration = unit.getPackage();
		assertEquals("package com.ast;\n", packageDeclaration.toString());
		assertEquals("com.ast", packageDeclaration.getName().toString());

		// check import
		List importDeclarations = unit.imports();

		for (Object object : importDeclarations) {
			ImportDeclaration importDec = (ImportDeclaration) object;
			logger.debug(importDec);
		}

		ImportDeclaration importDec = (ImportDeclaration) importDeclarations.get(0);
		assertEquals("org.junit.Assert.assertEquals", importDec.getName().toString());
		assertEquals("import org.junit.Assert.assertEquals;\n", importDec.toString());
		assertEquals(false, importDec.isOnDemand());
		// 如果是静态导入：import static java.lang.Math.*; ==> true;
		// assertEquals(true, importDec.isStatic());

		importDec = (ImportDeclaration) importDeclarations.get(1);
		assertEquals("java.util", importDec.getName().toString());
		assertEquals("import java.util.*;\n", importDec.toString());
		// 如果是：import java.util.*; ==> true
		assertEquals(true, importDec.isOnDemand());
		assertEquals(false, importDec.isStatic());

		// check class public
		List typeDeclarations = unit.types();
		TypeDeclaration clazz = (TypeDeclaration) typeDeclarations.get(0);
		assertEquals("ClassDemo2", clazz.getName().toString());
		/**
		 * if is not public,maybe have several class for(Object object :
		 * typeDeclarations){ TypeDeclaration clazzNode =(TypeDeclaration)
		 * object;}
		 */

		// check comments
		// 注释文件从头开始，每遇到一个@则视为一个TagElement,而每个TagElement都是由optinonalTagName
		// （@author）和多个TextElement，每换一次行则视为一个TextElement。
		// a: doc注释，这个注释可以在javadoc里面体现出来，格式：
		// b: 块注释，与类注释不同的是：类注释开头是：/** 而块注释是：/*
		// c: 行注释 //后面跟注释内容
		Javadoc classdoc = clazz.getJavadoc(); // 得到类上面的注释javadoc
		List targs = classdoc.tags(); // 得到TagElement集合
		for (Object object : targs) {
			TagElement tagElement = (TagElement) object;
			String optionalTagName = tagElement.getTagName(); // 得到注释名如：@author
			logger.debug(optionalTagName);
			List textElements = tagElement.fragments(); // 得到注释的内容集合：List
														// TextElement
			for (Object object2 : textElements) {
				TextElement textElement = (TextElement) object2;
				logger.debug(textElement.getText()); // 得到一个TextElement的内容
			}
		}

		// check class info
		logger.debug("Class Name:" + clazz.getName());
		logger.debug("Class isInterface:" + clazz.isInterface());

		for (Object obj : clazz.modifiers()) { // 得到修辞符如：public,static,abstract,
			Modifier modifier = (Modifier) obj;
			modifier.getKeyword();
			logger.debug("moidfier=" + modifier.toString());
		}

		// 得到类型信息 pulic class MyAdapter<T extends Entity> 中的<T extends Entity>
		for (Object object : clazz.typeParameters()) {
			TypeParameter typeParameter = (TypeParameter) object;
			SimpleName genericName = typeParameter.getName(); // T
			for (Object object2 : typeParameter.typeBounds()) { // Entity
				SimpleType superGrnericType = (SimpleType) object2;
				String superGrnericName = superGrnericType.getName().getFullyQualifiedName();
			}
		}
		// extends Adapter<Entity>
		Type superType = clazz.getSuperclassType();
		logger.debug(superType);

		// implements Adapter<Entity>, BaseBean{}
		List interfaceType = clazz.superInterfaceTypes();
		for (Object obj : interfaceType) {
			ParameterizedType parameterizedType = (ParameterizedType) obj;
			for (Object obj1 : parameterizedType.typeArguments()) {
				SimpleType simpleType = (SimpleType) obj1;
				simpleType.getName().getFullyQualifiedName();
			}
		}

	}
}
