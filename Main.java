//package Main;

import java.io.IOException;
//import Lexer.java;

public class Main {

	public static void main(String[] args) {
		
		Lexer myLexer = new Lexer("input1.txt", "output1.txt");
		myLexer.populateMap();
		try {
			myLexer.process();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(myLexer.getLexed().toString());
		
		Parser myParser = new Parser(myLexer.getLexed());
		int check = myParser.process();
		if(check == 0) {
			Interpreter myInter = new Interpreter(myLexer.getLexed());
			myInter.process();
		}

	}

}
