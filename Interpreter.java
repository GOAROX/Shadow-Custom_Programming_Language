//package Main;

import java.util.*;

//2 while loops, inner one goes till ';' outer one goes till end of arraylist.

public class Interpreter {

	ArrayList<String[]> lexed;
	int index = 0;
	Map<String, Object> var = new HashMap<String, Object>();

	public Interpreter(ArrayList<String[]> lexed) {
		this.lexed = lexed;
	}
	
	public void process() {
		while(index < lexed.size()) {
			//System.out.println(var);
			if((lexed.get(index))[1].equals("String")) {
				addString();				
			}
			else if((lexed.get(index))[1].equals("int")) {
				addInt();
			}
			else if((lexed.get(index))[0].equals("PRINT")) {
				printcmd();
			}
			else if((lexed.get(index))[0].equals("GET")) {
				getcmd();
			}
			else if((lexed.get(index))[0].equals("IF")) {
				//for now only have comp between 2 operators. taht should make the process easier. 
				//make a cmp function, that ret
				//uns true or false.
				ifcmd();
			}
			else if((lexed.get(index))[0].equals("ELSE")) {
				while(!(lexed.get(++index))[0].equals("END")) {}
				index+=2;
			}
			else if((lexed.get(index))[0].equals("WHILE")) {
				whilecmd();
			}
			else if((lexed.get(index))[0].equals("END")) {
				index+=3;
			}
			else if((lexed.get(index))[0].equals("ID")) {
				ismath();
			}
			else {
				index++;
			}
		}
		System.out.println(var);
	}

	private void whilecmd() {
		String first = (lexed.get(++index))[1];
		String op = (lexed.get(++index))[1];
		String second = (lexed.get(++index))[1];
		ArrayList<String[]> whilecode = new ArrayList<>();
		boolean check;
		check = checkcondition(first, op, second);
		index = index+4;
		while(!(lexed.get(index))[0].equals("END")) {
			if((lexed.get(index))[0].equals("IF")) {
				while(!(lexed.get(index))[0].equals("END")) {
					if((lexed.get(index))[0].equals("IF")) {
						while(!(lexed.get(index))[0].equals("END")) {
							String temp[] = {(lexed.get(index))[0], (lexed.get(index))[1]};
							whilecode.add(temp);
							index++;
						}
						String temp[] = {(lexed.get(index))[0], (lexed.get(index))[1]};
						index++;
					}
					String temp[] = {(lexed.get(index))[0], (lexed.get(index))[1]};
					whilecode.add(temp);
					index++;
				}
				String temp[] = {(lexed.get(index))[0], (lexed.get(index))[1]};
				index++;
			}
			String temp[] = {(lexed.get(index))[0], (lexed.get(index))[1]};
			whilecode.add(temp);
			index++;
		}
		index = index + 3;
		
		while(check) {
			whileprocess(whilecode);
			check = checkcondition(first, op, second);
		}
	}

	private void whileprocess(ArrayList<String[]> whilecode) {
		int whileindex = 0;
		while(whileindex < whilecode.size()) {
			//System.out.println(var);
			if((whilecode.get(whileindex))[1].equals("String")) {
				addString();				
			}
			else if((whilecode.get(whileindex))[1].equals("int")) {
				addInt();
			}
			else if((whilecode.get(whileindex))[0].equals("PRINT")) {
				whileindex = whileprintcmd(whilecode, whileindex);
			}
			else if((whilecode.get(whileindex))[0].equals("GET")) {
				getcmd();
			}
			else if((whilecode.get(whileindex))[0].equals("IF")) {
				ifcmd();
			}
			else if((whilecode.get(whileindex))[0].equals("ELSE")) {
				while(!(whilecode.get(++whileindex))[0].equals("END")) {}
				whileindex+=2;
			}
			else if((whilecode.get(whileindex))[0].equals("WHILE")) {
				whilecmd();
			}
			else if((whilecode.get(whileindex))[0].equals("END")) {
				whileindex+=3;
			}
			else if((whilecode.get(whileindex))[0].equals("ID")) {
				whileindex = whileismath(whilecode, whileindex);
			}
			else {
				whileindex++;
			}
		}
		
	}
	private int whileismath(ArrayList<String[]> whilecode, int whileindex) {
		String tochange = (whilecode.get(whileindex))[1];
		//System.out.println(tochange);
		whileindex++;
		if((whilecode.get(++whileindex))[0].equals("NEG")) {
			String temp = (whilecode.get(++whileindex))[1];
			int num = Integer.parseInt(temp);
			num = num * (-1);
			//System.out.println("This is num: " + num);
			var.put(tochange, num);
			whileindex = whileindex + 3;
		}
		else if((whilecode.get(whileindex))[0].equals("NUM")) {
			int first = Integer.parseInt((whilecode.get(whileindex))[1]);
			whileindex++;
			char op = (whilecode.get(whileindex))[1].charAt(0);
			whileindex++;
			if((whilecode.get(whileindex))[0].equals("NUM")) {
				int second = Integer.parseInt((whilecode.get(whileindex))[1]);
				switch(op) {
					case '+':
						var.put(tochange, first+second);
						break;
					case '-':
						var.put(tochange, first-second);
						break;
					case '*':
						var.put(tochange, first*second);
						break;
					case '/':
						var.put(tochange, first/second);
						break;
					case '%':
						var.put(tochange, first%second);
						break;
				}
			}
			else {
				int second = (int) var.get((whilecode.get(whileindex))[1]);
				switch(op) {
					case '+':
						var.put(tochange, first+second);
						break;
					case '-':
						var.put(tochange, first-second);
						break;
					case '*':
						var.put(tochange, first*second);
						break;
					case '/':
						var.put(tochange, first/second);
						break;
					case '%':
						var.put(tochange, first%second);
						break;
				}
			}
		}
		else if((whilecode.get(whileindex))[0].equals("ID")) {
			int first = (int) var.get((whilecode.get(whileindex))[1]);
			whileindex++;
			char op = (whilecode.get(whileindex))[1].charAt(0);
			whileindex++;
			if((whilecode.get(whileindex))[0].equals("NUM")) {
				int second = Integer.parseInt((whilecode.get(whileindex))[1]);
				switch(op) {
					case '+':
						var.put(tochange, first+second);
						break;
					case '-':
						var.put(tochange, first-second);
						break;
					case '*':
						var.put(tochange, first*second);
						break;
					case '/':
						var.put(tochange, first/second);
						break;
					case '%':
						var.put(tochange, first%second);
						break;
				}
			}
			else {
				int second = (int) var.get((whilecode.get(whileindex))[1]);
				switch(op) {
					case '+':
						var.put(tochange, first+second);
						break;
					case '-':
						var.put(tochange, first-second);
						break;
					case '*':
						var.put(tochange, first*second);
						break;
					case '/':
						var.put(tochange, first/second);
						break;
					case '%':
						var.put(tochange, first%second);
						break;
				}
			}
		}
		whileindex = whileindex+3;
		return whileindex;
		
	}
	
	private int whileprintcmd(ArrayList<String[]> whilecode, int whileindex) {
		if((whilecode.get(++whileindex))[0].equals("STR")) {
			String temp = (whilecode.get(whileindex))[1];
			String trim = temp.substring(1, temp.length() - 1);
			if(trim.equals("\n")){
				System.out.print("\n");
			}
			else
				System.out.println(trim);		
		}
		else {
			Object obj = var.get((whilecode.get(whileindex))[1]);
			if(obj instanceof Integer) {
				System.out.println(obj);
			}
			else {
				String temp = obj.toString();
				String trim = temp.substring(1, temp.length() - 1);
				System.out.println(trim);	
			}
			//System.out.println(var.get((lexed.get(index))[1]));
		}
		whileindex = whileindex + 3;
		return whileindex;
	}

	private boolean checkcondition(String first, String op, String second) {
		boolean check;
		if(first.charAt(0) < '0' || first.charAt(0) > '9') {
			if(second.charAt(0) < '0' || second.charAt(0) > '9') {
				check = compareit((int)var.get(first), (int)var.get(second), op);
			}
			else {
				check = compareit((int)var.get(first), Integer.parseInt(second), op);
			}
		}
		else {
			if(second.charAt(0) < '0' || second.charAt(0) > '9') {
				check = compareit(Integer.parseInt(first), (int)var.get(second), op);
			}
			else {
				check = compareit(Integer.parseInt(first), Integer.parseInt(second), op);
			}
		}
		return check;
	}

	private void ifcmd() {
		boolean check;
		if((lexed.get(++index))[0].equals("ID")) {
			String first = (lexed.get(index))[1];
			String op = (lexed.get(++index))[1];
			if((lexed.get(++index))[0].equals("ID")) {
				String second = (lexed.get(index))[1];
				check = compareit((int)var.get(first), (int)var.get(second), op);
			}
			else {
				int second = Integer.parseInt((lexed.get(index))[1]);
				check = compareit((int)var.get(first), second, op);
			}
		}
		else {
			int first = Integer.parseInt((lexed.get(index))[1]);
			String op = (lexed.get(++index))[1];
			if((lexed.get(++index))[0].equals("ID")) {
				String second = (lexed.get(index))[1];
				check = compareit(first, (int)var.get(second), op);
			}
			else {
				int second = Integer.parseInt((lexed.get(index))[1]);
				check = compareit(first, second, op);
			}
		}
		
		if(check) {
			index+=4;
		}
		else {
			while(!(lexed.get(++index))[0].equals("ELSE")) {}
			index+=2;			
		}		
	}
	
	private boolean compareit(int a, int b, String op) {
		switch (op) {
		case ">":
			if(a>b)
				return true;
			break;
		case "<":
			if(a<b)
				return true;
			break;
		case "==":
			if(a==b)
				return true;
			break;
		case "<=":
			if(a<=b)
				return true;
			break;
		case ">=":
			if(a>=b)
				return true;
			break;
		case "!=":
			if(a!=b)
				return true;
			break;
		}
		return false;
	}

	private void ismath() {
		String tochange = (lexed.get(index))[1];
		//System.out.println(tochange);
		index++;
		if((lexed.get(++index))[0].equals("NEG")) {
			String temp = (lexed.get(++index))[1];
			int num = Integer.parseInt(temp);
			num = num * (-1);
			System.out.println("This is num: " + num);
			var.put(tochange, num);
			index = index + 3;
		}
		else if((lexed.get(index))[0].equals("NUM")) {
			int first = Integer.parseInt((lexed.get(index))[1]);
			index++;
			char op = (lexed.get(index))[1].charAt(0);
			index++;
			if((lexed.get(index))[0].equals("NUM")) {
				int second = Integer.parseInt((lexed.get(index))[1]);
				switch(op) {
					case '+':
						var.put(tochange, first+second);
						break;
					case '-':
						var.put(tochange, first-second);
						break;
					case '*':
						var.put(tochange, first*second);
						break;
					case '/':
						var.put(tochange, first/second);
						break;
					case '%':
						var.put(tochange, first%second);
						break;
				}
			}
			else {
				int second = (int) var.get((lexed.get(index))[1]);
				switch(op) {
					case '+':
						var.put(tochange, first+second);
						break;
					case '-':
						var.put(tochange, first-second);
						break;
					case '*':
						var.put(tochange, first*second);
						break;
					case '/':
						var.put(tochange, first/second);
						break;
					case '%':
						var.put(tochange, first%second);
						break;
				}
			}
		}
		else if((lexed.get(index))[0].equals("ID")) {
			int first = (int) var.get((lexed.get(index))[1]);
			index++;
			char op = (lexed.get(index))[1].charAt(0);
			index++;
			if((lexed.get(index))[0].equals("NUM")) {
				int second = Integer.parseInt((lexed.get(index))[1]);
				switch(op) {
					case '+':
						var.put(tochange, first+second);
						break;
					case '-':
						var.put(tochange, first-second);
						break;
					case '*':
						var.put(tochange, first*second);
						break;
					case '/':
						var.put(tochange, first/second);
						break;
					case '%':
						var.put(tochange, first%second);
						break;
				}
			}
			else {
				int second = (int) var.get((lexed.get(index))[1]);
				switch(op) {
					case '+':
						var.put(tochange, first+second);
						break;
					case '-':
						var.put(tochange, first-second);
						break;
					case '*':
						var.put(tochange, first*second);
						break;
					case '/':
						var.put(tochange, first/second);
						break;
					case '%':
						var.put(tochange, first%second);
						break;
				}
			}
		}
		index = index+3;
		
	}

	private void getcmd() {
		 Scanner myObj = new Scanner(System.in);
		 //System.out.println("enter number: ");
		 int i = myObj.nextInt();
		 //System.out.println((lexed.get(index + 1))[1] + i);
		 var.put((lexed.get(++index))[1], i);
		 index = index + 3;
	}

	private void printcmd() {
		if((lexed.get(++index))[0].equals("STR")) {
			String temp = (lexed.get(index))[1];
			String trim = temp.substring(1, temp.length() - 1);
			if(trim.equals("\n")){
				System.out.print("\n");
			}
			else
				System.out.println(trim);			
		}
		else {
			Object obj = var.get((lexed.get(index))[1]);
			if(obj instanceof Integer) {
				System.out.println(obj);
			}
			else {
				String temp = obj.toString();
				String trim = temp.substring(1, temp.length() - 1);
				System.out.println(trim);	
			}
			//System.out.println(var.get((lexed.get(index))[1]));
		}
		index = index + 3;
		
	}

	private void addString() {
		var.put((lexed.get(++index))[1], (lexed.get(index+2))[1]);
		index = index + 5;
	}
	
	private void addInt() {
		if((lexed.get(index+3))[0].equals("NEG")) {
			Integer temp = Integer.parseInt((lexed.get(index+4))[1]);
			temp = -1 * temp;
			var.put((lexed.get(++index))[1], temp);
			index = index + 6;
			return;
		}
		var.put((lexed.get(++index))[1], Integer.parseInt((lexed.get(index+2))[1]));
		index = index + 5;
	}
}
