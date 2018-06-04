package br.fumec.compiler;

public class LexicalScanner {

	private final static String NUMBER = "[0-9]";
	private final static String LETTER = "[a-zA-Z]";
	private final static String OPERATOR = "[+\\-*/]";
	private final static String SPACE = "[ \t]";

	private boolean search(char character, String str) {
		return String.valueOf(character).matches(str);
	}

	public boolean validate(String phrase) {
		try {
			return validateWithoutVerify(phrase);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	private boolean validateWithoutVerify(String phrase) {
		int i = 0;
		String state = "Q14";
		
		if (phrase == null || phrase.equals("")) {
			return true;
		}

		while (i < phrase.length()) {

			switch (state) {
			case "Q14":
				while (search(phrase.charAt(i), SPACE)) {
					i++;
					
					if (isNextPositionEmpty(phrase, i)) {
						return true;
					}
				}
				if (phrase.charAt(i) == '/') {
					state = "Q1";
					i++;
				} else if (search(phrase.charAt(i), LETTER)) {
					state = "Q30";
					i++;
				} else {
					return false;
				}
				break;
			case "Q1":
				if (phrase.charAt(i) == '/') {
					state = "Q5";
					i++;
				} else if (phrase.charAt(i) == '*') {
					state = "Q2";
					i++;
				} else {
					return false;
				}
				break;
			case "Q2":
				while (phrase.charAt(i) != '*') {
					i++;
				}
				state = "Q3";
				i++;
				break;
			case "Q3":
				while (phrase.charAt(i) == '*') {
					i++;
				}
				if (phrase.charAt(i) != '/') {
					state = "Q2";
					i++;
				} else {
					state = "Q10";
					i++;

					if (isNextPositionEmpty(phrase, i)) {
						return true;
					}
				}
				break;
			case "Q10":
				while (search(phrase.charAt(i), SPACE)) {
					i++;
				}
				if (isBeginCommentBlock(phrase, i)) {
					state = "E6";
					i += 2;
				} else if (search(phrase.charAt(i), LETTER)) {
					state = "Q30";
					i++;
				} else if (phrase.charAt(i) == '/') {
					state = "Q1";
					i++;
				} else {
					return isNextPositionEmpty(phrase, i);
				}
				
				break;
			case "Q5": // Q5
				return true;
			case "Q30":
				while (search(phrase.charAt(i), LETTER) || search(phrase.charAt(i), NUMBER)) {
					i++;
				}
				if (phrase.charAt(i) == '/') {
					state = "E0";
					i++;
				} else if (phrase.charAt(i) == '=') {
					state = "VARIAVEL";
					i++;
				} else if (search(phrase.charAt(i), SPACE)) {
					state = "Q8";
					i++;
				} else {
					return false;
				}
				break;
			case "Q0":
				if (phrase.charAt(i) == '*') {
					i++;
					if (phrase.charAt(i) == '/') {
						state = "Q8";
						i++;
					}
				} else {
					i++;
				}
				break;
			case "Q8":
				while (search(phrase.charAt(i), SPACE)) {
					i++;
				}
				if (search(phrase.charAt(i), LETTER)) {
					state = "Q16";
					i++;
				} else if (phrase.charAt(i) == '=') {
					state = "VARIAVEL";
					i++;
				} else if (phrase.charAt(i) == '/') {
					state = "E0";
					i++;
				} else {
					return false;
				}
				break;
			case "Q16":
				while (search(phrase.charAt(i), LETTER) || search(phrase.charAt(i), NUMBER)) {
					i++;
				}
				if (phrase.charAt(i) == '=') {
					state = "VARIAVEL";
					i++;
				} else if (phrase.charAt(i) == ',') {
					state = "Q17";
					i++;
				} else {
					return false;
				}
				break;
			case "Q17":
				while (search(phrase.charAt(i), SPACE)) {
					i++;
				}
				if (search(phrase.charAt(i), LETTER)) {
					state = "Q16";
					i++;
				} else if (phrase.charAt(i) == '/') {
					state = "E1";
					i++;
				} else {
					return false;
				}
				break;
			case "Q18":
				if (phrase.charAt(i) == '*') {
					i++;
					if (phrase.charAt(i) == '/') {
						state = "Q17";
						i++;
					}
				} else {
					i++;
				}
				break;
			case "VARIAVEL":
				while (search(phrase.charAt(i), SPACE)) {
					i++;
				}
				if (phrase.charAt(i) == '/') {
					state = "E2";
					i++;
				} else if (search(phrase.charAt(i), LETTER)) {
					state = "Q13";
					i++;
				} else if (search(phrase.charAt(i), NUMBER)) {
					state = "Q7";
					i++;
				} else {
					return false;
				}
				break;
			case "Q4":
				if (phrase.charAt(i) == '*') {
					i++;
					if (phrase.charAt(i) == '/') {
						state = "VARIAVEL";
						i++;
					}
				} else {
					i++;
				}
				break;
			case "Q13":
				while (search(phrase.charAt(i), LETTER) || search(phrase.charAt(i), NUMBER)) {
					i++;
				}
				if (isBeginCommentBlock(phrase, i)) {
					state = "E3";
					i++;
				} else if (phrase.charAt(i) == ';') {
					state = "Q10";
					i++;

					if (isNextPositionEmpty(phrase, i)) {
						return true;
					}
				} else if (search(phrase.charAt(i), OPERATOR)) {
					state = "Q12";
					i++;
				} else if (search(phrase.charAt(i), SPACE)) {
					state = "Q11";
					i++;
				} else {
					return false;
				}
				break;
			case "Q12":
				while (search(phrase.charAt(i), SPACE)) {
					i++;
				}
				if (isBeginCommentBlock(phrase, i)) {
					state = "E7";
					i += 2;
				} else if (search(phrase.charAt(i), LETTER)) {
					state = "Q13";
					i++;
				} else if (search(phrase.charAt(i), NUMBER)) {
					state = "Q7";
					i++;
				} else {
					return false;
				}
				break;
			case "Q7":
				while (search(phrase.charAt(i), NUMBER)) {
					i++;
				}
				if (search(phrase.charAt(i), OPERATOR)) {
					state = "Q12";
					i++;
				} else if (phrase.charAt(i) == ';') {
					state = "Q10";
					i++;

					if (isNextPositionEmpty(phrase, i)) {
						return true;
					}
				} else if (phrase.charAt(i) == '.') {
					state = "Q19";
					i++;
				} else if (phrase.charAt(i) == '/') {
					state = "E4";
					i++;
				} else if (search(phrase.charAt(i), SPACE)) {
					state = "Q11";
					i++;
				} else {
					return false;
				}
				break;
			case "Q19":
				while (search(phrase.charAt(i), NUMBER)) {
					i++;
				}
				if (phrase.charAt(i) == '/') {
					state = "E4";
					i++;
				} else if (search(phrase.charAt(i), SPACE)) {
					state = "Q11";
					i++;
				} else if (phrase.charAt(i) == ';') {
					state = "Q10";
					i++;

					if (isNextPositionEmpty(phrase, i)) {
						return true;
					}
				} else {
					return false;
				}
				break;
			case "Q15":
				if (phrase.charAt(i) == '*') {
					state = "E5";
					i++;
				} else {
					i++;
				}
				break;
			case "Q11":
				while (search(phrase.charAt(i), SPACE)) {
					i++;
				}
				if (phrase.charAt(i) == ';') {
					state = "Q10";
					i++;

					if (isNextPositionEmpty(phrase, i)) {
						return true;
					}
				} else if (search(phrase.charAt(i), OPERATOR)) {
					state = "Q12";
					i++;
				} else if (phrase.charAt(i) == '/') {
					state = "E4";
					i++;
				}
				break;
			case "E0":
				if (phrase.charAt(i) == '*') {
					state = "Q0";
					i++;
				} else {
					return false;
				}
				break;
			case "E1":
				if (phrase.charAt(i) == '*') {
					state = "Q18";
					i++;
				} else {
					return false;
				}
				break;
			case "E2":
				if (phrase.charAt(i) == '*') {
					state = "Q4";
					i++;
				} else {
					return false;
				}
				break;
			case "E3":				
				if (isEndCommentBlock(phrase, i)) {
					state = "Q13";
					i += 2;
				} else {
					i++;
				}
				break;
			case "E4":
				if (phrase.charAt(i) == '*') {
					state = "Q15";
					i++;
				} else {
					return false;
				}
				break;
			case "E5":
				if (phrase.charAt(i) == '/') {
					state = "Q11";
					i++;
				} else {
					state = "Q15";
					i++;
				}
				break;
			case "E6":
				if (isEndCommentBlock(phrase, i)) {
					state = "Q10";
					i += 2;
					
					if (isNextPositionEmpty(phrase, i)) {
						return true;
					}
				} else {
					i++;
				}
				break;
			case "E7":
				if (isEndCommentBlock(phrase, i)) {
					state = "Q12";
					i += 2;
				} else {
					i++;
				}
				break;
			default:
				return false;
			}

		}

		return false;
	}
	
	private boolean isNextPositionEmpty(String phrase, int position) {
		return (position + 1) >= phrase.length();
	}

	private boolean isBeginCommentBlock(String phrase, int i) {
		return phrase.charAt(i) == '/' && (i + 1) < phrase.length() && phrase.charAt(i + 1) == '*';
	}

	private boolean isEndCommentBlock(String phrase, int i) {
		return phrase.charAt(i) == '*' && (i + 1) < phrase.length() && phrase.charAt(i + 1) == '/';
	}
}
