package compilerDesign;

import java.util.*;

public class Production {
	private Variable lhs;
	private List<Variable> rhs;
	
	public Production(Variable left,List<Variable> right) {
		lhs=left;
		if(right.size()>1&&right.contains(new Variable("eps"))) {
			right.remove(new Variable("eps"));
		}
		rhs=new ArrayList<Variable>();
		rhs.addAll(right);
	}
	
	public Production() {
		rhs=new ArrayList<Variable>();
	}
	public Production(Production p) {
		lhs=p.getLeft();
		rhs=new ArrayList<Variable>();
		rhs.addAll(p.getRight());
		if(rhs.size()>1&&rhs.contains(new Variable("eps"))) rhs.remove(new Variable("eps"));
	}
	
	public Variable getLeft() {
		return lhs;
	}
	
	public List<Variable> getRight() {
		return rhs;
	}
	
	public void setLeft(Variable str) {
		lhs.setName(str.getName());
	}
	
	public void addToRight(Variable str) {
		//if(rhs.size()>0&&!str.equals(new Variable("eps")))
			rhs.add(str);
	}
	
	public void setRight(List<Variable> str) {
		rhs.addAll(str);
	}
	
	public String toString() {
		String str="";
		for(Variable v:rhs) {
			str=str+v.getName();
		}
		return str;
	}
	
	public boolean equals(Object o) {
		if(o==this) return true;
		else if(!(o instanceof Production)) return false;
		else {
			Production temp=(Production)o;
			if(this.getLeft().equals(temp.getLeft())&&this.getRight().equals(temp.getRight())) return true;
			else return false;
		}
	}
}
