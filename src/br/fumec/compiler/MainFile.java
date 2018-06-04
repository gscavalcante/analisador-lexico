package br.fumec.compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainFile {
	
	public static void main(String[] args) {
		if (args.length < 0) {
			throw new RuntimeException("É necessário passar o nome do arquivo como paramêtro para o sistema.");
		}
		
		String file_input = args[0];
		String file_output = getFileOutputName(args);
		
		LexicalScanner lx = new LexicalScanner();

		BufferedReader br = null;
		FileReader fr = null;
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fr = new FileReader(file_input);
			br = new BufferedReader(fr);
			
			fw = new FileWriter(file_output);
			bw = new BufferedWriter(fw);

			String line;
			int i = 0, errorCount = 0;

			while ((line = br.readLine()) != null) {
				if (!lx.validate(line)) {
					bw.write("Line " + (i + 1) + " is invalid.\n");
					bw.newLine();
					errorCount++;
				}
				i++;
			}
			
			printSystemExit(errorCount);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
				
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

    private static String getFileOutputName(String[] args) {
		if (args.length > 1) {
			return args[1];
		}

        return "saida.txt";
    }

    private static void printSystemExit(int errorCount) {
        if (errorCount > 0) {
            System.out.println("A análise terminou com " + errorCount + " erro(s).");
        } else {
            System.out.println("A análise terminou e não foi encontrado nenhum erro.");
        }
    }

}
