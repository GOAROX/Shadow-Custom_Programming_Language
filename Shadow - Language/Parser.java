//package Main;

import java.io.*; 
import java.util.*;
//import Lexer.java;

public class Parser {
	
	ArrayList<String[]> lexed;
	int currentline = 1;
	int index = 0;
	
	public Parser(ArrayList<String[]> lexed) {
		this.lexed = lexed;
	}
	
	public void updateLine() {
		currentline+=1;
	}
	
	public void checkNewline() {
		if((lexed.get(index))[0].equals("NXTLN")) {
			updateLine();
			index++;
			//System.out.println("curr line: "+currentline);
		}
	}
	
	public void parseError(String msg) {
		System.out.println("Parse Error " + msg + " at line: " + currentline);
	}
	
	public String parseMath() {		//called when ASIGN is found, without a String or int before it.
		if((lexed.get(--index))[0].equals("ID")) {
			if((lexed.get(++index))[0].equals("ASSIGN")) {
				if((lexed.get(++index))[0].equals("ID") || (lexed.get(index))[0].equals("NUM")) {
					if((lexed.get(++index))[0].equals("ADD") || (lexed.get(index))[0].equals("MULT") || (lexed.get(index))[0].equals("DIVD") || (lexed.get(index))[0].equals("MDLS") || (lexed.get(index))[0].equals("NEG")) {
						if((lexed.get(++index))[0].equals("ID") || (lexed.get(index))[0].equals("NUM")) {
							if((lexed.get(++index))[0].equals("SMCL")) {
								return "true";
							}
							else {
								return "Missing semicolon";
							}
						}
						else {
							return "Missing second ID";
						}
					}
					else {
						return "Expected Operator";
					}
				}
				else if((lexed.get(index))[0].equals("NEG")) {
					if((lexed.get(++index))[0].equals("ID") || (lexed.get(index))[0].equals("NUM")) {
						if((lexed.get(++index))[0].equals("SMCL")) {
							return "true";
						}
						else {
							return "Missing semicolon";
						}
					}
					else {
						return "Expected Value";
					}
				}
				else {
					return "Expected Value";
				}
			}
			else {
				return "Expected Value";
			}
		}
		return "ERROR";
	}
	
	public String parseGet() {
		if((lexed.get(index))[0].equals("GET")) {
			if((lexed.get(++index))[0].equals("ID")) {
				if((lexed.get(++index))[0].equals("SMCL")) {
					return "true";
				}
				else {
					return "Missing semicolon";
				}
			}
			else {
				return "Expected Value";
			}
		}
		return "ERROR";
	}
	
	public String parsePrint() {
		if((lexed.get(index))[0].equals("PRINT")) {
			if((lexed.get(++index))[0].equals("ID") || (lexed.get(index))[0].equals("STR")) {
				if((lexed.get(++index))[0].equals("SMCL")) {
					return "true";
				}
				else {
					return "Missing semicolon";
				}
			}
			else {
				return "Expected Print Value";
			}
		}
		return "ERROR";
	}
	
	public String parseCompare() {
		do {
			if(!(lexed.get(++index))[0].equals("NOT")) {
				--index;
			}
			if((lexed.get(++index))[0].equals("ID") || (lexed.get(index))[0].equals("NUM")) {
				if((lexed.get(++index))[0].equals("GTEQ") || (lexed.get(index))[0].equals("LTEQ") || (lexed.get(index))[0].equals("EQAL") || (lexed.get(index))[0].equals("NTEQ") || (lexed.get(index))[0].equals("GRTN") || (lexed.get(index))[0].equals("LSTN")) {
					if((lexed.get(++index))[0].equals("ID") || (lexed.get(index))[0].equals("NUM")) {
						continue;
					}
					else {
						return "Missing second value";
					}
				}
				else {
					return "Expected Comparision Operator";
				}
			}
			return "ERROR";
		}while((lexed.get(++index))[0].equals("AND") || (lexed.get(index))[0].equals("OR"));
		//--index;
		//System.out.println((lexed.get(index))[0]);
		checkNewline();
		return "true";
	}
	
	public String parseString() {
		//boolean good = false;
		if((lexed.get(index))[1].equals("String")) {
			if((lexed.get(++index))[0].equals("ID")) {
				if((lexed.get(++index))[0].equals("ASSIGN")) {
					//System.out.println((lexed.get(index+1))[0]);
					if((lexed.get(++index))[0].equals("STR")) {
						if((lexed.get(++index))[0].equals("SMCL")) {
							return "true";
						}
						else {
							return "Missing semicolon";
						}
					}
					else if((lexed.get(index))[0].equals("OPCR")) {
						do {
							//System.out.println((lexed.get(index+1))[0]);
							if(!(lexed.get(++index))[0].equals("STR")) {
								return "Missing string value";
							}
							//System.out.println((lexed.get(index+1))[0]);
						}while((lexed.get(++index))[0].equals("COMA"));
						if((lexed.get(index))[0].equals("CLCR")) {
							if((lexed.get(++index))[0].equals("SMCL")) {
								return "true";
							}
							else {
								return "Missing semicolon";
							}
						}
						else {
							return "Missing '}' Symbol";
						}
					}
					else {
						return "Missing string value";
					}
				}
				else {
					return "Missing '=' operator";
				}
			}
			else {
				return "Expected Value";
			}
		}
		return "ERROR";
	}
	
	public String parseInt() {
		//boolean good = false;
		if((lexed.get(index))[1].equals("int")) {
			if((lexed.get(++index))[0].equals("ID")) {
				if((lexed.get(++index))[0].equals("ASSIGN")) {
					if (!(lexed.get(++index))[0].equals("NEG"))
						index--;
					if((lexed.get(++index))[0].equals("NUM")) {
						if((lexed.get(++index))[0].equals("SMCL")) {
							return "true";
						}
						else {
							return "Missing semicolon";
						}
					}
					else if((lexed.get(index))[0].equals("OPCR")) {
						do {
							//System.out.println((lexed.get(index+1))[0]);
							if(!(lexed.get(++index))[0].equals("NUM")) {
								return "Missing string value";
							}
							//System.out.println((lexed.get(index+1))[0]);
						}while((lexed.get(++index))[0].equals("COMA"));
						if((lexed.get(index))[0].equals("CLCR")) {
							if((lexed.get(++index))[0].equals("SMCL")) {
								return "true";
							}
							else {
								return "Missing semicolon";
							}
						}
						else {
							return "Missing '}' Symbol";
						}
					}
					else {
						return "Missing integer value";
					}
				}
				else {
					if((lexed.get(index))[0].equals("SMCL")) {
						return "true";
					}
					else {
						return "Missing semicolon";
					}
					//return "Missing '=' operator";
				}
			}
			else {
				return "Expected Value";
			}
		}
		return "ERROR";
	}
	
	public void prog(String str) {
		//System.out.println("In Prog");
		int count = 1;
		while(!(lexed.get(index))[0].equals(str)) {
			//System.out.println("Curr token: " + index + " " + (lexed.get(index))[1]);
			if(count++ >= 1000) {
				parseError("Missing Clause");
				System.exit(1);
			}
			if((lexed.get(index))[1].equals("String")) {
				String msg = parseString();
				if(!msg.equals("true")) {
					parseError(msg);
					System.exit(1);
				}
				index++;
				checkNewline();
			}
			else if((lexed.get(index))[1].equals("int")) {
				String msg = parseInt();
				if(!msg.equals("true")) {
					parseError(msg);
					System.exit(1);
				}
				index++;
				checkNewline();
			}
			else if((lexed.get(index))[0].equals("GET")) {
				String msg = parseGet();
				if(!msg.equals("true")) {
					parseError(msg);
					System.exit(1);
				}
				index++;
				checkNewline();
			}
			else if((lexed.get(index))[0].equals("PRINT")) {
				String msg = parsePrint();
				if(!msg.equals("true")) {
					parseError(msg);
					System.exit(1);
				}
				index++;
				checkNewline();
			}
			else if((lexed.get(index))[0].equals("ID")) { 
				if((lexed.get(++index))[0].equals("ASSIGN")) {
					//System.out.println("Found Assign");
					String msg = parseMath();
					if(!msg.equals("true")) {
						parseError(msg);
						System.exit(1);
					}
					index++;
					checkNewline();
				}
			}
			else if((lexed.get(index))[0].equals("IF")) { 
				if(parseCompare().equals("true")) {
					if((lexed.get(index))[0].equals("THEN")) {
						index++;
						checkNewline();
						prog("ELSE");
						System.out.println("In Else");
						index++;
						checkNewline();
						prog("END");
						if((lexed.get(++index))[0].equals("SMCL")) {
							
						}
						else {
							parseError("Missing semicolon");
							return;
						}
						index++;
						checkNewline();
					}
					else {
						parseError("Missing Then Clause");
						return;
					}
				}
				else {
					parseError("Faulty Comparision Statement");
				}
			}
			else if((lexed.get(index))[0].equals("WHILE")) { 
				if(parseCompare().equals("true")) {
					if((lexed.get(index))[0].equals("DO")) {
						index++;
						checkNewline();
						prog("END");
						if((lexed.get(++index))[0].equals("SMCL")) {
							
						}
						else {
							parseError("Missing semicolon");
							return;
						}
						index++;
						checkNewline();
					}
					else {
						parseError("Missing Do Clause");
						return;
					}
				}
				else {
					parseError("Faulty Comparision Statement");
				}
			}
		}
	}
	
	public int process() {
		while(index < lexed.size()) {
			if((lexed.get(index))[1].equals("String")) {
				String msg = parseString();
				if(!msg.equals("true")) {
					parseError(msg);
					return 1;
				}
				index++;
				checkNewline();
			}
			else if((lexed.get(index))[1].equals("int")) {
				String msg = parseInt();
				if(!msg.equals("true")) {
					parseError(msg);
					return 1;
				}
				index++;
				checkNewline();
			}
			else if((lexed.get(index))[0].equals("GET")) {
				String msg = parseGet();
				if(!msg.equals("true")) {
					parseError(msg);
					return 1;
				}
				index++;
				checkNewline();
			}
			else if((lexed.get(index))[0].equals("PRINT")) {
				String msg = parsePrint();
				if(!msg.equals("true")) {
					parseError(msg);
					return 1;
				}
				index++;
				checkNewline();
			}
			else if((lexed.get(index))[0].equals("ID")) { 
				if((lexed.get(++index))[0].equals("ASSIGN")) {
					//System.out.println("Found Assign");
					String msg = parseMath();
					if(!msg.equals("true")) {
						parseError(msg);
						return 1;
					}
					index++;
					checkNewline();
				}
			}
			else if((lexed.get(index))[0].equals("IF")) { 
				if(parseCompare().equals("true")) {
					if((lexed.get(index))[0].equals("THEN")) {
						index++;
						checkNewline();
						prog("ELSE");
						System.out.println("In Else");
						index++;
						checkNewline();
						prog("END");
						if((lexed.get(++index))[0].equals("SMCL")) {
							
						}
						else {
							parseError("Missing semicolon");
							return 1;
						}
						index++;
						checkNewline();
					}
					else {
						parseError("Missing Then Clause");
						return 1;
					}
				}
				else {
					parseError("Faulty Comparision Statement");
				}
			}
			else if((lexed.get(index))[0].equals("WHILE")) { 
				if(parseCompare().equals("true")) {
					if((lexed.get(index))[0].equals("DO")) {
						index++;
						checkNewline();
						prog("END");
						if((lexed.get(++index))[0].equals("SMCL")) {
							
						}
						else {
							parseError("Missing semicolon");
							return 1;
						}
						index++;
						checkNewline();
					}
					else {
						parseError("Missing Do Clause");
						return 1;
					}
				}
				else {
					parseError("Faulty Comparision Statement");
				}
			}			
		}
		System.out.println("Valid Program");
		return 0;
	}

}
