package br.fumec.compiler;

public class LexicalScanner {

	private final static String NUMBER = "[0-9]";
	private final static String LETTER = "[a-zA-Z]";
	private final static String OPERATOR = "[+\\-*/]";
	private final static String SPACE = "[ \t]";
	private final static String RESERVED_WORDS_FUNCTION = "(for|while|do|if|else|break|continue|case|switch|return|goto)";
	private final static String RESERVED_WORDS_TYPES = "(int|boolean|string|long|double)";

	public boolean validate(String phrase) {
		int i = 0;
		String state = "Q14";

		if (phrase == null || phrase.equals("")) {
			return true;
		}

		while (i < phrase.length()) {

			switch (state) {
			case "Q14":
				if (search(phrase.charAt(i), SPACE)) {
					if (isNextPositionEmpty(phrase, i)) {
						return true;
					}
					
					i++;
				} else if (phrase.charAt(i) == '/') {
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
					if (isNextPositionEmpty(phrase, i)) {
					    return true;
					}
					
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
				if (phrase.charAt(i) != '*') {
					i++;
				} else {
					state = "Q3";
					i++;
				}
				break;
			case "Q3":
				if (phrase.charAt(i) == '*') {
					i++;
				} else if (phrase.charAt(i) != '/') {
					state = "Q2";
					i++;
				} else {
					if (isNextPositionEmpty(phrase, i)) {
						return isEverythingCorrect(phrase);
					}
					
					state = "Q10";
					i++;
				}
				break;
			case "Q10":
				if (search(phrase.charAt(i), SPACE)) {					
				    i++;
				    
					if (isNextPositionEmpty(phrase, i)) {
					    return true;
					}
				} else if (isBeginCommentBlock(phrase, i)) {
					state = "E6";
					i += 2;
				} else if (search(phrase.charAt(i), LETTER)) {
					state = "Q30";
					i++;
				} else if (phrase.charAt(i) == '/') {
					state = "Q1";
					i++;
				} else {
					return false;
				}

				break;
			case "Q5":
				return isEverythingCorrect(phrase);
			case "Q30":
				if (search(phrase.charAt(i), LETTER) || search(phrase.charAt(i), NUMBER)) {
					i++;
				} else if (phrase.charAt(i) == '/') {
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
				if (isEndCommentBlock(phrase, i)) {
					state = "Q8";
					i += 2;
				} else {
					i++;
				}
				break;
			case "Q8":
				if (search(phrase.charAt(i), SPACE)) {
					i++;
				} else if (search(phrase.charAt(i), LETTER)) {
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
				if (search(phrase.charAt(i), LETTER) || search(phrase.charAt(i), NUMBER)) {
					i++;
				} else if (phrase.charAt(i) == '=') {
					state = "VARIAVEL";
					i++;
				} else if (phrase.charAt(i) == ',') {
					state = "Q17";
					i++;
				} else if (search(phrase.charAt(i), SPACE)) {
				    state = "Q21";
				    i++;
				} else {
					return false;
				}
				break;
			case "Q17":
				if (search(phrase.charAt(i), SPACE)) {
					i++;
				} else if (search(phrase.charAt(i), LETTER)) {
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
				if (isEndCommentBlock(phrase, i)) {
					state = "Q17";
					i += 2;
				} else {
					i++;
				}
				break;
			case "VARIAVEL":
				if (search(phrase.charAt(i), SPACE)) {
					i++;
				} else if (phrase.charAt(i) == '/') {
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
				if (isEndCommentBlock(phrase, i)) {
					state = "VARIAVEL";
					i += 2;
				} else {
					i++;
				}
				break;
			case "Q13":
				if (search(phrase.charAt(i), LETTER) || search(phrase.charAt(i), NUMBER)) {
					i++;
				} else if (isBeginCommentBlock(phrase, i)) {
					state = "E3";
					i++;
				} else if (phrase.charAt(i) == ';') {
					if (isNextPositionEmpty(phrase, i)) {
						return isEverythingCorrect(phrase);
					}
					
					state = "Q10";
					i++;
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
				if (search(phrase.charAt(i), SPACE)) {
					i++;
				} else if (isBeginCommentBlock(phrase, i)) {
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
				if (search(phrase.charAt(i), NUMBER)) {
					i++;
				} else if (search(phrase.charAt(i), OPERATOR)) {
					state = "Q12";
					i++;
				} else if (phrase.charAt(i) == ';') {
					if (isNextPositionEmpty(phrase, i)) {
						return isEverythingCorrect(phrase);
					}
					
					state = "Q10";
					i++;
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
				if (search(phrase.charAt(i), NUMBER)) {
					i++;
				} else if (phrase.charAt(i) == '/') {
					state = "E4";
					i++;
				} else if (search(phrase.charAt(i), SPACE)) {
					state = "Q11";
					i++;
				} else if (phrase.charAt(i) == ';') {
					if (isNextPositionEmpty(phrase, i)) {
						return isEverythingCorrect(phrase);
					}
					
					state = "Q10";
					i++;
				} else {
					return false;
				}
				break;
			case "Q15":
				if (isEndCommentBlock(phrase, i)) {
					state = "Q11";
					i += 2;
				} else {
					i++;
				}
				break;
			case "Q11":
				if (search(phrase.charAt(i), SPACE)) {
					i++;
				} else if (phrase.charAt(i) == ';') {
					if (isNextPositionEmpty(phrase, i)) {
						return isEverythingCorrect(phrase);
					}
					
					state = "Q10";
					i++;
				} else if (isBeginCommentBlock(phrase, i)) {
					state = "Q15";
					i += 2;
				} else if (search(phrase.charAt(i), OPERATOR)) {
					state = "Q12";
					i++;
				} else {
					return false;
				}
				break;
			case "Q21":
			    if (isBeginCommentBlock(phrase, i)) {
			        state = "Q22";
			        i += 2;
			    } else if (phrase.charAt(i) == '=') {
			        state = "VARIAVEL";
			        i++;
			    } else if (phrase.charAt(i) == ',') {
                    state = "Q17";
                    i++;
                } else {
                    return false;
                }
			    break;
			case "Q22":
			    if (isEndCommentBlock(phrase, i)) {
			        state = "Q21";
			        i += 2;
			    } else {
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
			case "E6":
				if (isEndCommentBlock(phrase, i)) {
					if (isNextPositionEmpty(phrase, i + 1)) {
						return isEverythingCorrect(phrase);
					}
					
					state = "Q10";
					i += 2;
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
	
	private boolean search(String word, String str) {
        return word.matches(str);
    }
    
    private boolean search(char character, final String str) {
        return String.valueOf(character).matches(str);
    }

	private boolean isNextPositionEmpty(final String phrase, int position) {
		return (position + 1) >= phrase.length();
	}

	private boolean isBeginCommentBlock(final String phrase, int i) {
		return phrase.charAt(i) == '/' && (i + 1) < phrase.length() && phrase.charAt(i + 1) == '*';
	}

	private boolean isEndCommentBlock(final String phrase, int i) {
		return phrase.charAt(i) == '*' && (i + 1) < phrase.length() && phrase.charAt(i + 1) == '/';
	}
	
	private boolean isEverythingCorrect(final String phrase) {
	    String tmpPhrase = phrase.replaceAll("((\\/\\/.*)|(\\/\\/*.*\\*\\/))", "").replaceAll(",", "").trim();
	    if (tmpPhrase.isEmpty()) {
	        return true;
	    }
	    
	    String[] expressions = tmpPhrase.split("(;)");
	    
	    for (String expression : expressions) {
	        String[] items = expression.trim().split(SPACE);
	        int i = 0;
	        
	        if (search(items[1], LETTER)) {
	            i = 1;
	            
	            if (search(items[0], RESERVED_WORDS_FUNCTION)) {
	                return false;
	            }
	        }
	        
	        for (; i < items.length; i++) {
	            String item = items[i];
	            if (search(item, RESERVED_WORDS_FUNCTION) || search(item, RESERVED_WORDS_TYPES)) {
	                return false;
	            }
	        }
	    }
	    
	    return true;
	}
}
