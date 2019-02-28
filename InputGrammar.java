package compilerDesign;

import java.util.*;
import java.io.*;

public class InputGrammar {
	private List<Variable> vars;
	private List<Production> prods;
	private int terminals;
	private int prodSize;
	private int nonterms;
	
	public InputGrammar() {
		vars=new ArrayList<Variable>();
		prods=new ArrayList<Production>();
		terminals=prodSize=nonterms=0;
	}
	
	private List<Variable> genProduction(String str) {
		List<Variable> util=new ArrayList<Variable>();
		if(str=="eps") {
			int x=vars.indexOf(new Variable("eps"));
			util.add(vars.get(x));
			return util;
		}
		String buffer="";
		for(int i=0;i<str.length();i++) {
			if(str.charAt(i)!='.') buffer+=str.charAt(i);
			else {
				Variable tem=new Variable(buffer);
				int x=vars.indexOf(tem);
				util.add(vars.get(x));
				buffer="";
			}
		}
		int x=vars.indexOf(new Variable(buffer));
		util.add(vars.get(x));
		return util;
	}
	
	public void getInput(int term,int nonterm,int prod) throws IOException {
		System.out.println("Enter the Terminals:--->");
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		for(int i=0;i<term;i++) {
			vars.add(new Variable(in.readLine()));
			vars.get(vars.size()-1).setTerminal();
		}
		vars.add(new Variable("eps"));
		vars.get(vars.size()-1).setEpsilon();
		System.out.println("Enter the NonTerminals:--->");
		for(int i=0;i<nonterm;i++) {
			vars.add(new Variable(in.readLine()));
		}
		System.out.println("Enter the Productions:--->");
		for(int i=0;i<prod;i++) {
			String str=in.readLine();
			int j;
			for(j=0;j<str.length();j++) {
				if(str.charAt(j)==' ') break;
			}
			String left=str.substring(0, j);
			Variable vartemp=new Variable(left);
			int x=vars.indexOf(vartemp);
			//System.out.println(vars.get(x).getName()+" at index "+x+" remainder: "+str.substring(j+1, str.length()));
			Production temp=new Production(vars.get(x),this.genProduction(str.substring(j+1, str.length())));
			prods.add(temp);
		}
		
		this.nonterms=nonterm;
		this.prodSize=prod;
		this.terminals=term;
	}
	
	public void printProduction() {
		for(int i=terminals+1;i<=terminals+nonterms;i++) {
			Variable temp=vars.get(i);
			System.out.print(temp.getName()+"->");
			int count = 0;
			for(Production p:prods) {
				if(p.getLeft().equals(temp)) {
					if(count>0) System.out.print("|");
					for(Variable v:p.getRight()) {
						System.out.print(v.getName());
					}
					count++;
				}
			}
			System.out.println();
		}
	}
	
	public int getProdSize() {
		return prodSize;
	}
	
	public int numberTerminals() {
		return terminals+1;
	}
	
	public int numberNonTerminals() {
		return nonterms;
	}
	
	public List<Production> getProductions(){
		return prods;
	}
	
	public List<Variable> getVariables(){
		return vars;
	}
}
