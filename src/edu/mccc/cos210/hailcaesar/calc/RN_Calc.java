package edu.mccc.cos210.hailcaesar.calc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RN_Calc {
	private String lastOutput = "0";
	private RuleForChar operatorRule = new RuleForChar(1);
	private RuleForChar numeralRule = new RuleForChar(2);
	private RuleForChar openGroupingRule = new RuleForChar(3);
	private RuleForChar closedGroupingRule = new RuleForChar(4);
	private RuleForChar variableRule = new RuleForChar(5);
	private static Map<Character, Integer> nums = new HashMap<>();
	static {
		nums.put('I', 1);
		nums.put('V', 5);
		nums.put('X', 10);
		nums.put('L', 50);
		nums.put('C', 100);
		nums.put('D', 500);
		nums.put('M', 1000);
	}
	public static void main(String[] args) {
		new RN_Calc().standAlone();
	}
	private void standAlone() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String s = "";
			while ((s = br.readLine()) != null) {
				System.out.println(calc(s));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}
	public String calc(String s) { 
		String finale = "Error";
		s = catchErrors(s);
		if (!catchErrors(s).equals("Error")) {
			s = parseString(s);
			s = hatPrecedence(s);
			s = calcString(s);
			if (Integer.parseInt(s) < 1 || Integer.parseInt(s) > 3999) {
	            finale = "Error";
	            lastOutput = "0";
	        } else {
	        	finale = decimalToRoman(s);
	        }
		} else {
			lastOutput = "0";
		}
		return finale;
	}
	private String calcString(String s) {
		if (s.equals("0")) {
			return "0";
		}
		s = infixToPostfix(s);
		int result = evaluatePostfix(s);
		String finalAnswer = Integer.toString(result);
		lastOutput = decimalToRoman(finalAnswer);
		return finalAnswer;
	}
	private String hatPrecedence(String s) {
		if (!s.contains("^")) {
			return s;
		}
		Pattern parenthesisP = Pattern.compile("\\(([\\d+-^*]+?)\\)");
		Matcher parenthesisM = parenthesisP.matcher(s);
		int pIndex = 0;
		while (parenthesisM.find(pIndex)) {
			String current = parenthesisM.group(1);
			current = hatPrecedence(current);
			current = calcString(current);
			s = s.replace(parenthesisM.group(0), current);
			pIndex = parenthesisM.end();
		}
		parenthesisP = Pattern.compile("\\(([\\d+-^*()]+)\\)");
		parenthesisM = parenthesisP.matcher(s);
		pIndex = 0;
		while (parenthesisM.find(pIndex)) {
			String current = parenthesisM.group(1);
			current = hatPrecedence(current);
			current = calcString(current);
			s = s.replace(parenthesisM.group(0), current);
			pIndex = parenthesisM.end();
		}
		Pattern exponentPattern = Pattern.compile("([\\d]+(?:\\^[\\d]+)+)+");
        Matcher exponentMatcher = exponentPattern.matcher(s);
        int expIndex = 0;
        while (exponentMatcher.find(expIndex)) {
        	ArrayList<Integer> numbers = new ArrayList<>();
        	Pattern digitPattern = Pattern.compile("[\\d]+");
        	Matcher digitMatcher = digitPattern.matcher(exponentMatcher.group(0));
        	int digitIndex = 0;
        	while (digitMatcher.find(digitIndex)) {
        		numbers.add(Integer.parseInt(digitMatcher.group(0)));
        		digitIndex = digitMatcher.end();
        	}
        	int sum = 0;
        	for (int i = numbers.size() - 2; i >= 0; i--) {
        		sum = (int) Math.pow(numbers.get(i), numbers.get(i + 1));
        		numbers.add(i, sum);
        	}
        	s = s.replace((exponentMatcher.group(0)), Integer.toString(sum));
        	expIndex = exponentMatcher.end();
        }
        return s;
	}
	private String parseString(String s) {
		s = s.replaceAll("\\$", lastOutput);
        Pattern term = Pattern.compile("[IVXLCDM]+");
        Matcher mterm = term.matcher(s);
        while (mterm.find()) {
        	if (checkNumeralOrder(mterm.group(0))) {
        		s = s.replaceFirst(mterm.group(0), romanToDecimal(mterm.group(0)));
        	} else {
        		s = "0";
        	}
        }
        return s;
    }
	private String infixToPostfix(String s) {
		Stack<Character> st = new Stack<Character>();
		String postfix = "";
		char ch[] = s.toCharArray();
		for (int i = 0; i < ch.length; i++) {
		   	char c = ch[i];
		   	if (c != '+' && c != '-' && c != '*' && c != '(' && c != ')') {
		   		if (Character.isDigit(c)) {
		   			postfix += c;
		   		}
		   		if (i < ch.length - 1 && Character.isDigit(ch[i + 1])) {
		   			continue;
		   		}
		   		postfix += ".";
		   	} else if (c == '(') {
		   		st.push(c);
		    } else if (c == ')') {
		    	while (!st.isEmpty()) {
		    		char t = st.pop();
		    	    if (t != '(') {  
		    	    	postfix += t;		    	
		    		} else {
		    		   	break;
		    		}	
		    	}
		    } else if (c == '+' ||c == '-' ||c == '*') {
		    	if (st.isEmpty()) {
		    	    st.push(c);  
		    	} else {
		    		while (!st.isEmpty()) {
		    			char t = st.pop();
		    			if (t == '(') {
		    				st.push(t);
		    				break;
		    			} else if (t == '+' || t == '-' || t == '*') {
		    				if (getPriority(t) <  getPriority(c)) {
		    					st.push(t);
		    					break;
		    				} else {
		    					postfix += t;
		    				}
		    			}
		    		}
		    		st.push(c);
		    	}
		    }
		}
		while (!st.isEmpty()) {
		   	postfix += st.pop();
		}
		return postfix;
	}
	private int getPriority(char c) {
	    if (c == '+' || c == '-') {
	    	return 1;
	    } else {
	    	return 2;
	    } 
	}
	private int evaluatePostfix(String s) {
		Stack<Integer> numStack = new Stack<Integer>();
		StringBuilder sbuild = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (!Chars.isOperator(s.charAt(i))) {
				while (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
					sbuild.append(s.charAt(i++));
				}
				numStack.push(Integer.parseInt(sbuild.toString()));
				sbuild.delete(0, sbuild.length());
			} else if (s.charAt(i) == '.') {
				continue;
			} else if (Chars.isOperator(s.charAt(i))) {
				int num2 = numStack.pop();
				int num1 = numStack.pop();
				int result = performOperation(s.charAt(i), num1, num2);
				numStack.push(result);
			}
		}
		return numStack.pop();
	}
	private int performOperation(char op, int num1, int num2) {
		if (op == '+') {
			return num1 + num2;
		} else if (op == '-') {
			return num1 - num2;
		} else {
			return num1 * num2;
		}
	}
	private static String decimalToRoman(String s) {
		if (s.equals("0") || s.equals("Error")) {
			return "0";
		}
		int num = Integer.parseInt(s);
	    int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
	    String[] romanLiterals = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
	    StringBuilder build = new StringBuilder();
	    for (int i=0;i<values.length;i++) {
            while (num >= values[i]) {
                num -= values[i];
                build.append(romanLiterals[i]);
            }
        }
        String roman = build.toString();
    	return roman;
	}
	private static String romanToDecimal(String s) {
		if (s.length() < 1) {
			return "0";
		}
		char max = 'I';
		int maxStart = 0;
		int maxEnd = Integer.MAX_VALUE;
		int maxCount = 1;
		char[] arr = s.toCharArray();
		for (char c : arr) {
			if (nums.get(c) > nums.get(max)) {
				max = c;
			}
		}
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == max) {
				maxStart = i;
				maxEnd = i;
				if (i+1 < arr.length) {
					if (arr[i+1] == max) {
						maxEnd = i+1;
						maxCount++;
						if (i+2 < arr.length) {
							if (arr[i+2] == max) {
								maxEnd = i+2;
								maxCount++;
							}
						}
					}
					break;
				}
			}
		}
		int base = nums.get(max) * maxCount;
		String subtract = "";
		int sublength = maxStart - 0;
		if (sublength > 0) {
			StringBuilder subsb = new StringBuilder();
			for (int i = 0; i < maxStart; i++) {
				subsb.append(arr[i]);
			}
			subtract = subsb.toString();
		}
		String add = "";
		int addlength = arr.length - maxEnd;
		if (addlength > 0) {
			StringBuilder addsb = new StringBuilder();
			for (int i = maxEnd + 1; i < arr.length; i++) {
				addsb.append(arr[i]);
			}
			add = addsb.toString();
		}
		int result = base - Integer.parseInt(romanToDecimal(subtract)) + Integer.parseInt(romanToDecimal(add)); 
		return Integer.toString(result);
	}
	public String catchErrors(String s) {
		s = spaceInsideNumeral(s);
		if (isInputEmpty(s) || !isDollarUseCorrect(s) || improperParenthesis(s)) {
			s = "Error";
		} else {
			char temp;
			for (int i = 0; i < s.length(); i++) {
				temp = s.charAt(i);
				if (!Chars.isOperator(temp) && !Chars.isNumeral(temp) && !Chars.isVariable(temp) && !Chars.isOpenGrouping(temp) &&
						!Chars.isClosedGrouping(temp)) {
					s = "Error";
					break;
				}
				if (i == 0 || i == s.length() - 1) {
					if (Chars.isOperator(temp) && !operatorRule.isCharIndexValid(i)) {
						s = "Error";
						break;
					} else {
						if (Chars.isNumeral(temp) && !numeralRule.isCharIndexValid(i)) {
							s = "Error";
							break;
						} else {
							if (Chars.isVariable(temp) && !variableRule.isCharIndexValid(i)) {
								s = "Error";
								break;
							} else {
								if (Chars.isOpenGrouping(temp) && !openGroupingRule.isCharIndexValid(i)) {
									s = "Error";
									break;
								} else {
									if (Chars.isClosedGrouping(temp) && !closedGroupingRule.isCharIndexValid(i)) {
										s = "Error";
										break;
									}
								}
							}
						}
					} 
				} else {
					if (Chars.isOperator(s.charAt(i)) && !operatorRule.isCharValid(s.charAt(i - 1), s.charAt(i + 1))) {
						s = "Error";
						break;
					} else {
						if (Chars.isNumeral(s.charAt(i)) && !numeralRule.isCharValid(s.charAt(i - 1), s.charAt(i + 1))) {
							s = "Error";
							break;
						} else {
							if (Chars.isVariable(s.charAt(i)) && !variableRule.isCharValid(s.charAt(i - 1), s.charAt(i + 1))) {
								s = "Error";
								break;
							} else {
								if (Chars.isOpenGrouping(s.charAt(i)) && !openGroupingRule.isCharValid(s.charAt(i - 1), s.charAt(i + 1))) {
									s = "Error";
									break;
								} else {
									if (Chars.isClosedGrouping(s.charAt(i)) && !closedGroupingRule.isCharValid(s.charAt(i - 1), s.charAt(i + 1))) {
										s = "Error";
										break;
									}
								}
							}
						}
					} 
				}
			}
		}
		return s;
	}
	private boolean improperParenthesis(String s) { 
		boolean errorFound = false;
		Stack<Character> pStack = new Stack<Character>();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				pStack.push(s.charAt(i));
			} else {
				if (s.charAt(i) == ')' && pStack.isEmpty()) {
					errorFound = true;
					break;
				} else {
					if (s.charAt(i) == ')' && !pStack.isEmpty()) {
						pStack.pop();
					}
				}
			}
			if (i == s.length() - 1 && !pStack.isEmpty()) {
				errorFound = true;
			}	
		}
		return errorFound;
	}
	private boolean isDollarUseCorrect(String s) {
		boolean errorFound = false;
		boolean isUsed = false;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '$') {
				if (!isUsed) {
					isUsed = true;
				} else {
					errorFound = true;
					break;
				}
			}
		}
		return !errorFound; 
	}
	private String spaceInsideNumeral(String s) { 
		s = s.trim();
		char placeholder = 0;
		int placeholderIndex = 0;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				placeholder = s.charAt(i);
				placeholderIndex = 0;
				continue;
			}
			if (Character.isLetter(placeholder) && Character.isLetter(s.charAt(i)) && i - placeholderIndex > 1) {
				s = "Error";
				break;
			} else {
				if (Character.isLetter(placeholder) && Character.isWhitespace(s.charAt(i))) {
					continue;
				}
			}
			placeholder = s.charAt(i);
			placeholderIndex = i;
		}
		if (!s.equals("Error")) {
			s = s.toUpperCase().replaceAll("\\s", "");
		}
		return s; 
	}
	private boolean isInputEmpty(String s) {
		boolean errorFound = false;
		if (s.equals("")) {
			errorFound = true;
		}
		return errorFound;
	}
	public boolean checkNumeralOrder(String s) {
		boolean errorFound = false;
		String numberToCheck = romanToDecimal(s);
		if (!(Integer.parseInt(numberToCheck) >= 1 && Integer.parseInt(numberToCheck) <= 3999)) { 
			errorFound = true;
		}
		if (!s.equals(decimalToRoman(romanToDecimal(s)))) { 
			errorFound = true;
		}
		return !errorFound; 
	}
	public static class Chars {
		private static char[] operators = {'+', '-', '*', '^'};
		private static char[] numerals = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
		private static char[] variables = {'$'};
		private static char[] openGroupings = {'('};
		private static char[] closeGroupings = {')'};
		public static boolean isOpenGrouping (char c) {
			boolean valid = false;
			for (int i = 0; i < openGroupings.length; i++) {
				if (c == openGroupings[i]) {
					valid = true;
				}
			}
			return valid;
		}
		public static boolean isClosedGrouping(char c) {
			boolean valid = false;
			for (int i = 0; i < closeGroupings.length; i++) {
				if (c == closeGroupings[i]) {
					valid = true;
				}
			}
			return valid;
		}
		public static boolean isOperator(char c) {
			boolean valid = false;
			for (int i = 0; i < operators.length; i++) {
				if (c == operators[i]) {
					valid = true;
				}
			}
			return valid;
		}
		public static boolean isVariable(char c) {
			boolean valid = false;
			for (int i = 0; i < variables.length; i++) {
				if (c == variables[i]) {
					valid = true;
				}
			}
			return valid;
		}
		public static boolean isNumeral(char c) {
			boolean valid = false;
			for (int i = 0; i < numerals.length; i++) {
				if (c == numerals[i]) {
					valid = true;
				}
			}
			return valid;
		}
		public static char[] getOperators() {
			return operators;
		}
		public static char[] getOpenGroupings() {
			return openGroupings;
		}
		public static char[] getClosedGroupings() {
			return closeGroupings;
		}
		public static char[] getVariables() {
			return variables;
		}
		public static char[] getNumerals() {
			return numerals;
		}
	}
	private class RuleForChar {
		char[] operators = Chars.getOperators();
		char[] numerals = Chars.getNumerals();
		char[] variables = Chars.getVariables();
		char[] openGroupings = Chars.getOpenGroupings();
		char[] closeGroupings = Chars.getClosedGroupings();
		int charType = 0;
		char[] charactersAfter = null;
		char[] charactersBefore = null;
		boolean allowedIndexZero, allowedIndexEnd;
		private RuleForChar(int type) {
			charType = type;
			switch (charType) {
			case 1:
				makeOperator();
				break;
			case 2:
				makeRomanNumeral();
				break;
			case 3:
				makeOpenGrouping();
				break;
			case 4:
				makeClosedGrouping();
				break;
			case 5:
				makeVariable();
				break;
			default:
				charType = 0;
				System.out.println("That value is not implemented yet!");
			}
		}
		private void makeOperator() {
			charactersBefore = null;
			charactersAfter = null;
			allowedIndexZero = allowedIndexEnd = false;
			addValidCharBefore(numerals);
			addValidCharBefore(closeGroupings);
			addValidCharBefore(variables);
			addValidCharAfter(numerals);
			addValidCharAfter(variables);
			addValidCharAfter(openGroupings);
		}
		private void makeRomanNumeral() {
			charactersBefore = null;
			charactersAfter = null;
			allowedIndexZero = allowedIndexEnd = true;
			addValidCharBefore(numerals);
			addValidCharBefore(openGroupings);
			addValidCharBefore(operators);
			addValidCharAfter(numerals);
			addValidCharAfter(operators);
			addValidCharAfter(closeGroupings);
		}
		private void makeOpenGrouping() {
			charactersBefore = null;
			charactersAfter = null;
			allowedIndexZero = true;
			allowedIndexEnd = false;
			addValidCharBefore(openGroupings);
			addValidCharBefore(operators);
			addValidCharAfter(numerals);
			addValidCharAfter(variables);
			addValidCharAfter(openGroupings);
		}
		private void makeClosedGrouping() {
			charactersBefore = null;
			charactersAfter = null;
			allowedIndexZero = false;
			allowedIndexEnd = true;
			addValidCharBefore(numerals);
			addValidCharBefore(closeGroupings);
			addValidCharBefore(variables);
			addValidCharAfter(operators);
			addValidCharAfter(closeGroupings);
		}
		private void makeVariable() {
			charactersBefore = null;
			charactersAfter = null;
			allowedIndexZero = allowedIndexEnd = true;
			addValidCharBefore(operators);
			addValidCharBefore(openGroupings);
			addValidCharAfter(operators);
			addValidCharAfter(closeGroupings);
		}
		private void addValidCharBefore(char[] c) {
			if (charactersBefore == null) {
				charactersBefore = c;
			} else {
				char[] temp = new char[charactersBefore.length + c.length];
				for (int i = 0; i < charactersBefore.length; i++)  {
					temp[i] = charactersBefore[i];
				}
				for (int i = charactersBefore.length; i < charactersBefore.length + c.length; i++) {
					temp[i] = c[i - charactersBefore.length];
				}
				charactersBefore = temp;
			}
		}
		private void addValidCharAfter(char[] c) {
			if (charactersAfter == null) {
				charactersAfter = c;
			} else {
				char[] temp = new char[charactersAfter.length + c.length];
				for (int i = 0; i < charactersAfter.length; i++)  {
					temp[i] = charactersAfter[i];
				}
				for (int i = charactersAfter.length; i < charactersAfter.length + c.length; i++) {
					temp[i] = c[i - charactersAfter.length];
				}
				charactersAfter = temp;
			}
		}
		private boolean isCharValid(char before, char after) {
			boolean acceptableAfter = false;
			boolean acceptableBefore = false;
			if (!charactersAfter.equals(null) && !charactersBefore.equals(null)) { 
				for (int i = 0; i < charactersAfter.length; i++) {
					if (after == charactersAfter[i]) {
						acceptableAfter = true;
					}
				}
				for (int i = 0; i < charactersBefore.length; i++) {
					if (before == charactersBefore[i]) {
						acceptableBefore = true;
					}
				}
			}
			return (acceptableBefore && acceptableAfter);
		}
		private boolean isCharIndexValid(int index) {
            boolean acceptable = false;
            if (index == 0 && allowedIndexZero) {
                acceptable = true;
            } else {
                if (index > 0 && allowedIndexEnd) {
                    acceptable = true;
                }
            }
            return acceptable;
        }
	}
}