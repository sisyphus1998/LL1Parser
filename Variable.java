package compilerDesign;

import java.util.*;

public class Variable {
	private String name;
	private Set<Variable> First;
	private Set<Variable> Follow;
	private boolean isTerminal=false;
	private boolean isEpsilon=false;
	
	public Variable(String n) {
		name=n;
		
		First=new HashSet<Variable>();
		Follow=new HashSet<Variable>();
		if(n.equals("eps")) {
			isEpsilon=true;
			isTerminal=true;
		}
		if(n.equals("$")) isTerminal=true;
	}
	
	public Variable() {
		First=new HashSet<Variable>();
		name=new String();
		Follow=new HashSet<Variable>();
	}
	
	Variable(Variable v){
		name=v.getName();
		First.clear();
		Follow.clear();
		First.addAll(v.getFirst());
		Follow.addAll(v.getFollow());
	}
	
	public boolean isTerminal() {
		return isTerminal;
	}
	
	public void setTerminal() {
		isTerminal=true;
		First.add(this);
	}
	
	public boolean isEpsilon() {
		return isEpsilon;
	}
	
	public void setEpsilon() {
		isEpsilon=true;
	}
	
	public int hashCode() {
		return name.hashCode();
	}
	
	public boolean equals(Object o) {
		if(o==this) return true;
		else if(!(o instanceof Variable)) return false;
		else {
			Variable temp=(Variable)o;
			if(temp.getName().equals(this.getName())) return true;
			else return false;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name=newName;
	}
	
	public void addVarToFirst(Variable add) {
		First.add(add);
	}
	
	public void addAllToFirst(Set<Variable> add) {
		First.addAll(add);
	}
	
	public void addVarToFollow(Variable add) {
		Follow.add(add);
	}
	
	public void addAllToFollow(Set<Variable> add) {
		Follow.addAll(add);
	}
	
	public Set<Variable> getFollow(){
		return Follow;
	}
	
	public Set<Variable> getFirst(){
		return First;
	}
}
