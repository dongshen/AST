package com.ast.Util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BinaryTreeTest {

	@Test
	public void testDepthOrderTraversal() {
		int[] arr = intitalTree();
		BinaryTree tree = new BinaryTree(arr);
		String ret = tree.depthOrderTraversal();
		assertEquals("13,65,97,22,25,4,28,5,37,32,",ret);

	}

	@Test
	public void testLevelOrderTraversal() {
		int[] arr = intitalTree();
		BinaryTree tree = new BinaryTree(arr);
		
		String ret = tree.levelOrderTraversal();
		
		assertEquals("13,65,5,97,25,37,22,4,28,32,",ret);
	}

	/** 
     *                  13
     *                 /  \
     *               65    5
     *              /  \    \
     *             97  25   37
     *            /    /\   /
     *           22   4 28 32
     **/
	private int[] intitalTree() {
		int[] arr = { 0, 13, 65, 5, 97, 25, 0, 37, 22, 0, 4, 28, 0, 0, 32, 0 };
		return arr;
	}
}
