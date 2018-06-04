package br.fumec.compiler;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		LexicalScanner lx = new LexicalScanner();
		
		int i = 0;
		while (sc.hasNextLine()) {
			if (! lx.validate(sc.nextLine())) {
				System.out.println("Line " + (i + 1) + " is invalid.");
			}
			i++;
		}
		
		sc.close();
	}
	
}
