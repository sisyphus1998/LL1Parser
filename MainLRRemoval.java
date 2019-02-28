package compilerDesign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainLRRemoval {

	public static void main(String[] args) throws IOException {
		System.out.println("----PROGRAM TO REMOVE LEFT RECURSION FROM A CFG----");
		System.out.println("\r\n\r\n==****NOTES****==\r\nUSE \'.\' TO DENOTE CONCATENATION");
		System.out.println("ENTER THE PRODUCTIONS INDIVIDUALLY, WITHOUT USING \'|\'");
		System.out.println("ENTER PRODUCTIONS BY USING A SPACE IN BETWEEN LHS AND RHS");
		System.out.println("e.g.ENTER A->Bc|Ab AS \"A B.c\" AND \"A A.b\" SEPARATELY");
		System.out.println("\r\n----BEGIN----\r\n\r\n");
		BufferedReader inx=new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			System.out.println("1.ENTER CFG FOR REMOVAL OF LEFT RECURSION\r\n2.EXIT");
			int choice1=Integer.parseInt(inx.readLine());
			if(choice1==2) break;
			else {
				System.out.println("Enter the number of terminals in the grammar:-");
				int ter=Integer.parseInt(inx.readLine());
				System.out.println("Enter the number of NonTerminals in the grammar:-");
				int nont=Integer.parseInt(inx.readLine());
				System.out.println("Enter the number of productions in the grammar:--");
				int prod=Integer.parseInt(inx.readLine());
				InputGrammar obj=new InputGrammar();
				obj.getInput(ter, nont, prod);
				System.out.println("\r\nThe grammar you entered:---");
				obj.printProduction();
				System.out.println("\r\nThe grammar, without any left recursion:---");
				LRFreeGrammar objt=new LRFreeGrammar(obj);
				objt.removeGeneralLR();
				objt.printProds();
				objt.genFirst();
				objt.printFirst();
				objt.genFollow();
				objt.printFollow();
				LLOneParser ll1=new LLOneParser(objt);
				ll1.printParsingTable();
				while(true) {
					System.out.println("\r\nEnter String to check for acceptance,Enter \"exit\" to Exit");
					String str=inx.readLine();
					//String[] util=str.split(":");
					//for(int i=0;i<util.length;i++) System.out.println(util[i]);
					if(str.equals("exit")) break;
					if(ll1.checkString(str)) System.out.println("\r\nSTRING ACCEPTED");
					else System.out.println("\r\nSTRING REJECTED");
				}
			}
		}
	}

/* 
E E.+.T
E T
T T.*.F
T F
F id
F (.E.)
 * 
+
*
id
(
)
 * 
 * 
 * 
 * 
 * 
 *
 * 
 * 
 * */
}
