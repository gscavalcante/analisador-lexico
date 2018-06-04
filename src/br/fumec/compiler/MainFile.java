package br.fumec.compiler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainFile {

	public static void main(String[] args) {
		if (args.length < 0) {
			throw new RuntimeException("É necessário passar o nome do arquivo como paramêtro para o sistema.");
		}

		String filename = args[0];
		LexicalScanner lx = new LexicalScanner();

		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);

			String line;
			int i = 0;

			while ((line = br.readLine()) != null) {
				if (!lx.validate(line)) {
					System.out.println("Line " + (i + 1) + " is invalid.");
				}
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
