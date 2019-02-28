package compilerDesign;

//import java.util.*;

public class ParsingTableObjects {
	Variable nonterminal;
	Variable terminal;
	Production prod;
	public ParsingTableObjects(Variable nt,Variable term,Production pr) {
		nonterminal=nt;
		terminal=term;
		prod=pr;
	}
	
	public ParsingTableObjects() {
		nonterminal=new Variable();
		terminal=new Variable();
		prod=new Production();
	}
	
	public ParsingTableObjects(Variable nt,Variable term) {
		nonterminal=nt;
		terminal=term;
		prod=new Production();
	}
	
	public Variable getNT() {
		return nonterminal;
	}
	
	public Variable getT() {
		return terminal;
	}
	
	public Production getP() {
		return prod;
	}
	
	public void setNonTerm(Variable n) {
		nonterminal=n;
	}
	
	public void setTerm(Variable t) {
		terminal=t;
	}
	
	public void setProd(Production p) {
		prod=p;
	}
	
	public String toString() {
		return "T["+nonterminal.getName()+","+terminal.getName()+"]= "+nonterminal.getName()+"->"+prod;
	}
	
	public boolean equals(Object o) {
		if(o==this) return true;
		else if(!(o instanceof ParsingTableObjects)) return false;
		ParsingTableObjects pto=(ParsingTableObjects)o;
		if(this.nonterminal.equals(pto.getNT())&&this.terminal.equals(pto.getT())) return true;
		return false;
	}
}
