//package Main;

import java.io.*; // Import this class to handle errors
import java.util.*;
import java.lang.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	FileWriter myWriter;
	File myInput;
	Map<String, String> tokenmap;
	Map<String, String> specialtokenmap;
	ArrayList<String[]> lexed;

	public Lexer(String input1, String output1) {
		try {
			myWriter = new FileWriter(output1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myInput = new File(input1);
		tokenmap = new HashMap<String, String>();
		specialtokenmap = new HashMap<String, String>();
		lexed = new ArrayList<>();
	}


	/**
	 * @return the lexed
	 */
	public ArrayList<String[]> getLexed() {
		return lexed;
	}

	public void populateMap() {

		tokenmap.put("SMCL", "[;]$");
		tokenmap.put("COMA", "[,]$");
		// vexpr
		specialtokenmap.put("GTEQ", "(?:>=)");
		specialtokenmap.put("LTEQ", "(?:<=)");
		specialtokenmap.put("EQAL", "(?:==)");
		specialtokenmap.put("NTEQ", "[!][=]");
		tokenmap.put("GRTN", "[>]");
		tokenmap.put("LSTN", "[<]");

		tokenmap.put("ASSIGN", "[=]");
		tokenmap.put("WHTSPC", "[ ]");

		// math
		tokenmap.put("ADD", "[+]");
		tokenmap.put("MULT", "[*]");
		tokenmap.put("DIVD", "\\/");
		tokenmap.put("MDLS", "[%]");

		// special
		tokenmap.put("OPCR", "\\{");
		tokenmap.put("CLCR", "\\}");
		tokenmap.put("OPBK", "[(]");
		tokenmap.put("CLBK", "[)]");
		tokenmap.put("NOT", "(?:not)");
		tokenmap.put("NEG", "[-]"); // SUBTRACT AND NEGATION

		// keywords
		specialtokenmap.put("PRINT", "\\bprint\\b");
		specialtokenmap.put("GET", "\\bget\\b");
		specialtokenmap.put("IF", "\\bif\\b");
		specialtokenmap.put("THEN", "\\bthen\\b");
		specialtokenmap.put("ELSE", "\\belse\\b");
		specialtokenmap.put("END", "\\bend\\b");
		specialtokenmap.put("WHILE", "\\bwhile\\b");
		specialtokenmap.put("DO", "\\bdo\\b");
		specialtokenmap.put("AND", "\\band\\b");
		specialtokenmap.put("OR", "\\bor\\b");

		tokenmap.put("NUM", "(-)?[0-9]+");
		tokenmap.put("ID", "[_a-zA-Z][_a-zA-Z0-9]*");
		specialtokenmap.put("STR", "\\\"(?:\\\\\\\\.|[^\\\"\\\\\\\\])*\\\"");
	}

	public void process() throws IOException {
		Scanner myReader = null;
		try {
			myReader = new Scanner(myInput);
			String token;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				//System.out.println(data);
				String carrier = "";
				int flag = 0;
				ArrayList<String> tokens = getTokens(data);
				for (int i=0; i<tokens.size(); i++) {
					String x = tokens.get(i);
					try {
						if(x.equals("\"")) {
							carrier = x;
							do {
								i++;
								x = tokens.get(i);
								carrier = carrier + x;
															
							}while(!x.equals("\""));
							x = carrier;
							//i--;
						}
					}
					catch(Exception e) {
						myWriter.write("ERROR IN CODE\n");
						break;
					}
					token = identify(x);
					if(!x.equals(" ")) {
						myWriter.write(formatString(token, x));
						String temp[] = {token, x};
						lexed.add(temp);
					}
				}
				myWriter.write(formatString("NXTLN", "\\n"));
				String temp[] = {"NXTLN", "\\n"};
				lexed.add(temp);
			}

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} finally {
			myReader.close();
			myWriter.close();
		}

	}

	public String formatString(String token, String lexeme) {
		return String.format("%-22s%-22s\n", token, lexeme);
	}

	public String identify(String lexeme) {
		
		for (Map.Entry<String, String> me : specialtokenmap.entrySet()) {
			Pattern pattern = Pattern.compile(me.getValue());
			Matcher matcher = pattern.matcher(lexeme);
			if (matcher.find()) {
				return me.getKey();
			}
		}
		for (Map.Entry<String, String> me : tokenmap.entrySet()) {
			Pattern pattern = Pattern.compile(me.getValue());
			Matcher matcher = pattern.matcher(lexeme);
			if (matcher.find()) {
				return me.getKey();
			}
		}
		return "ERROR";
	}

	public static ArrayList<String> getTokens(String str) {
		ArrayList<String> tokens = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(str, " -,;(){}\"", true);
		while (tokenizer.hasMoreElements()) {
			tokens.add(tokenizer.nextToken());
		}
		return tokens;
	}

}
