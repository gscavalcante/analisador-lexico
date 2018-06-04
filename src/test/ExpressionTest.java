package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.fumec.compiler.LexicalScanner;

class ExpressionTest {
	
	@Test
	void addExpressionJustNumbers() {
		String exp = "i = 1 + 240;";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void addExpressionJustVariables() {
		String exp = "i = x + y;";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void blockOfComent() {
		String exp = "/* All line */";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void lineOfComent() {
		String exp = "// Some text";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void incompleteBlockOfCode() {
		String exp = "i = 1 +";
		assertFalse(new LexicalScanner().validate(exp));
	}
	
	@Test
	void commentOnMiddle() {
		String exp = "i = /* some comment */ i + 2;";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void blockOfCommentOnBeginWithExpressionIncomplete() {
		String exp = "/* some comment */ i = i +";
		assertFalse(new LexicalScanner().validate(exp));
	}
	
	@Test
	void codeWithLongVariables() {
		String exp = "abc = abc + def;";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void commentOnMiddle2() {
		String exp = "i = i + /* some comment */ 2;";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void commentOnMiddle3() {
		String exp = "i /* some comment */ = i + 2;";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void commentOfOnEOL() {
		String exp = "i = i + 2; /* some comment */";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void commentOnEOL() {
		String exp = "i = i + 2; // some comment";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void twoExpressions() {
		String exp = "i = i + 2; a = a * b;";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void differentSpace() {
		String exp = "a =i+2;";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void differentSpace2() {
		String exp = "a = i+2;";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void whiteSpace() {
		String exp = "                ";
		assertTrue(new LexicalScanner().validate(exp));
	}
	
	@Test
	void blankLine() {
		assertTrue(new LexicalScanner().validate(null));
	}

}
