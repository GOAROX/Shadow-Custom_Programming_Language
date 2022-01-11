README FILE 
		CS 4337 Project part 2
		SRIDHAR KARUMURI (SSK190000)

	LIST OF FILES:
		MAIN.JAVA:
			INITIALIZES AND RUNS LEXER FOLLOWED BY PARSER
		LEXER.JAVA:
			POPULATES MAP, RUNS THE ENTIRE LEXER PROCESSING, IDENTIFIES TOKEN AND TOKENIZES INPUT LINE. PRINTS RESULTING LEXEMES TO OUTPUT1.TXT.
			THEN SENDS ARRAYLIST FILLED WITH SAME VALUES AS RESULT TO PARSER
		PARSER.JAVA:
			GOES THROUGH EVERY INDEX VALUE OF THE ARRAYLIST PASSED TO IT AND CHECKS FOR MISTAKES OR IRREGULARITIES IN SYNTAX. IT CHECKS EVERY SINGLE SITUATION
			AND PRINTS AN APPROPRIATE ERROR MESSAGE TO CONSOLE ABOUT THE ERROR.
		INTERPRETER.JAVA:
			GOES THROUGH EVERY LINE IN THE CODE AND INTERPRETS THE LANGUAGE. THIS FILE ALSO EXECUTES THE CODE. IT HAS VARIOUS FUNCTRIONS THAT 
			HELP RUN DIFFERENT COMMANDS. THE WHILE LOOP HAS ITS OWN SET.
		input1.txt:
			Input file i used to test and file program takes in as input(PLEASE MODIFY THIS FILE TO TEST, PROGRAM DOES NOT ACCEPT FILE NAMES)
		


	INSTRUCTIONS TO COMPILE AND RUN:
		1. change to this directory in shell(in cs1 or cs2)
		2. javac Lexer.java
		3. javac Parser.java
		4. javac Interpreter.java
		4. javac Main.java
		5. java Main # now it will process input from input1.txt which is in current folder and print output to console

	Other Information:
		Right now the lexer can only process information seperated by space, ;, (,),[, or ]. 
		As these are the delimiters for the tokenizer. Pls keep this in mind while giving input values.
		Example: i=0; must be written as i = 0;
		The change done to the program was the addition of an array functionality. The parser allows a decleration or assignment statement to 
		have a {} with legal values inside.
		The while loop is a little messed up please look at Write-Up document for more details.
	
		