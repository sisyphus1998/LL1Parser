package compilerDesign;

import java.util.*;

public class LLOneParser {

	private List<Production> productions;
	private List<Variable> variables;
	private List<ParsingTableObjects> ptobjects;
	private Stack<Variable> varStack;
	
	public LLOneParser(LRFreeGrammar lrobj) {
		productions=new ArrayList<Production>();
		variables=new ArrayList<Variable>();
		ptobjects=new ArrayList<ParsingTableObjects>();
		varStack=new Stack<Variable>();
		productions.addAll(lrobj.getProds());
		variables.addAll(lrobj.getVars());
		this.makeParsingTable();
		varStack.push(new Variable("$"));
		varStack.push(variables.get(0));
	}
	
	private void makeParsingTable() {
		//System.out.println(variables.get(0).getName()+"\r\n");
		for(Production p:productions) {
			//System.out.println(p.getLeft().getName()+"->"+p);
			Set<Variable> terms=new HashSet<Variable>();
			terms.addAll(this.findTerminals(p.getLeft(), p.getRight()));
			for(Variable v:terms) {
				int index=variables.indexOf(p.getLeft());
				ParsingTableObjects temp=new ParsingTableObjects(variables.get(index),v,p);
				if(!ptobjects.contains(temp)) ptobjects.add(temp);
				else {
					System.out.println("Error while creating parsing table\r\nProduction conflict at T["+p.getLeft().getName()+","+v.getName()+"]");
					System.out.println("Previous production retained");
				}
			}
		}
	}
	
	public void printParsingTable() {
		System.out.println("\r\n----PARSING TABLE----\r\n");
		for(ParsingTableObjects pto:ptobjects) {
			System.out.println(pto);
		}
	}
	
	public boolean checkString(String src) {
		int index=0;
		int count=0;
		String[] arrayStr=src.split(":");
		while(true) {
			count++;
			System.out.print("Step "+count+":");
			for(int i=0;i<varStack.size();i++) {
				System.out.print(varStack.get(i).getName()+" ");
			}
			System.out.println();
			if(varStack.isEmpty()) {
				varStack.push(new Variable("$"));
				varStack.push(variables.get(0));
				return true;
			}
			Variable vt=varStack.peek();
			Variable tt=new Variable(arrayStr[index]);
			if(vt.equals(tt)) {
				varStack.pop();
				index++;
				continue;
			}
			List<Variable> templist=new ArrayList<Variable>();
			templist.addAll(this.varSequence(vt, tt));
			if(templist.isEmpty()) {
				varStack.clear();
				varStack.push(new Variable("$"));
				varStack.push(variables.get(0));
				return false;
			}
			templist.remove(new Variable("eps"));
			varStack.pop();
			for(int i=0;i<templist.size();i++) {
				varStack.push(templist.get(i));
			}
		}
	}
	
	private List<Variable> varSequence(Variable nt,Variable te){
		ParsingTableObjects newpto=new ParsingTableObjects(nt,te);
		int index=ptobjects.indexOf(newpto);
		List<Variable> newlist=new ArrayList<Variable>();
		if(index==-1) return newlist;
		newlist.addAll(ptobjects.get(index).getP().getRight());
		for(int i=0;i<(int)newlist.size()/2;i++) {
			Variable temp=newlist.get(i);
			newlist.remove(i);
			newlist.add(i, newlist.get(newlist.size()-1-i));
			newlist.remove(newlist.size()-1-i);
			newlist.add(newlist.size()-i, temp);
		}
		//newlist.remove(new Variable("eps"));
		
		return newlist;
	}
	
	private Set<Variable> findTerminals(Variable lhs,List<Variable> rhs) {
		Set<Variable> tempset=new HashSet<Variable>();
		int i;
		for(i=0;i<rhs.size();i++) {
			
			int x=variables.indexOf(rhs.get(i));
			if(rhs.get(i).equals(new Variable("eps"))) continue;
			if(x==-1) {
				tempset.add(rhs.get(i));
				break;
			}
			tempset.addAll(variables.get(x).getFirst());
			if(!tempset.contains(new Variable("eps"))) {
				break;
			}
			tempset.remove(new Variable("eps"));
		}
		if(i==rhs.size()) {
			int in=variables.indexOf(lhs);
			if(in!=-1)
			tempset.addAll(variables.get(in).getFollow());
		}
		return tempset;
	}
}
