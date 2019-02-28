package compilerDesign;

import java.util.*;

public class LRFreeGrammar {
	private List<Production> prodUtil;
	private List<Variable> varUtil;
	@SuppressWarnings("unused")
	private List<Production> prodFinal;
	private List<Variable> varFinal;
	private int termn;
	@SuppressWarnings("unused")
	private int nontermn;
	public LRFreeGrammar(InputGrammar ing) {
		prodUtil=new ArrayList<Production>();
		varUtil=new ArrayList<Variable>();
		prodFinal=new ArrayList<Production>();
		varFinal=new ArrayList<Variable>();
		prodUtil.addAll(ing.getProductions());
		varUtil.addAll(ing.getVariables());
		termn=ing.numberTerminals();
		nontermn=ing.numberNonTerminals();
	}
	
	public List<Production> getProds(){
		return prodUtil;
	}
	
	public List<Variable> getVars(){
		return varFinal;
	}
	
	public void genFirst() {
		for(Variable v:varFinal) {
			for(Production p:prodUtil) {
				if(p.getLeft().equals(v)) {
					if(p.getRight().get(0).isEpsilon()) v.addVarToFirst(p.getRight().get(0));
					else if(p.getRight().get(0).isTerminal()) v.addVarToFirst(p.getRight().get(0));
				}
			}
		}
		
		for(Variable v:varFinal) {
			for(Production p:prodUtil) {
				if(p.getLeft().equals(v)) {
					if(!p.getRight().get(0).isEpsilon()&&!p.getRight().get(0).isTerminal()) {
						for(int i=0;i<p.getRight().size();i++) {
							int x=varFinal.indexOf(p.getRight().get(i));
							if(x==-1) continue;
							if(varFinal.get(x).getFirst().contains(new Variable("eps"))) {
								Set<Variable> tempset=new HashSet<Variable>();
								tempset.addAll(varFinal.get(x).getFirst());
								tempset.remove(new Variable("eps"));
								v.addAllToFirst(tempset);
								if(i==p.getRight().size()-1)v.addVarToFirst(new Variable("eps"));
								
							}
							else if(!varFinal.get(x).getFirst().contains(new Variable("eps"))) {
								v.addAllToFirst(varFinal.get(x).getFirst());
								break;
							}
							
						}
					}
				}
				
			}
		}
		for(Variable v:varFinal) {
			for(Production p:prodUtil) {
				if(p.getLeft().equals(v)) {
					if(!p.getRight().get(0).isEpsilon()&&!p.getRight().get(0).isTerminal()) {
						for(int i=0;i<p.getRight().size();i++) {
							int x=varFinal.indexOf(p.getRight().get(i));
							if(x==-1) continue;
							if(varFinal.get(x).getFirst().contains(new Variable("eps"))) {
								Set<Variable> tempset=new HashSet<Variable>();
								tempset.addAll(varFinal.get(x).getFirst());
								tempset.remove(new Variable("eps"));
								v.addAllToFirst(tempset);
								if(i==p.getRight().size()-1)v.addVarToFirst(new Variable("eps"));
								
							}
							else if(!varFinal.get(x).getFirst().contains(new Variable("eps"))) {
								v.addAllToFirst(varFinal.get(x).getFirst());
								break;
							}
							
						}
					}
				}
				
			}
		}
	}
	
	public void printFirst() {
		System.out.println();
		System.out.println("Non-Terminals and their Firsts:---");
		for(Variable v:varFinal) {
			if(!v.isEpsilon()&&!v.isTerminal()) {
				System.out.print(v.getName()+" :-> {");
				int i=1;
				for(Variable vt:v.getFirst()) {
					if(i==1)System.out.print(vt.getName());
					else {
						System.out.print(","+vt.getName());
					}
					i++;
				}
				System.out.print("}\r\n");
			}
		}
	}
	
	public void printFollow() {
		System.out.println();
		System.out.println("Non-Terminals and their Follows:---");
		for(Variable v:varFinal) {
			if(!v.isEpsilon()&&!v.isTerminal()) {
				System.out.print(v.getName()+" :-> {");
				int i=1;
				for(Variable vt:v.getFollow()) {
					if(i==1)System.out.print(vt.getName());
					else {
						System.out.print(","+vt.getName());
					}
					i++;
				}
				System.out.print("}\r\n");
			}
		}
	}
	
	public void genFollow() {
		varFinal.get(0).addVarToFollow(new Variable("$"));
		for(Production p:prodUtil) {
			if(!p.getRight().get(p.getRight().size()-1).isTerminal()) {
				int x=varFinal.indexOf(p.getRight().get(p.getRight().size()-1));
				int x2=varFinal.indexOf(p.getLeft());
				varFinal.get(x).addAllToFollow(varFinal.get(x2).getFollow());
			}
		}
		for(Production p:prodUtil) {
			for(int i=0;i<p.getRight().size();i++) {
				if(!p.getRight().get(i).isTerminal()) {
					Set<Variable> tempset=new HashSet<Variable>();
					int j;
					for(j=i+1;j<p.getRight().size();j++) {
						int x=varFinal.indexOf(p.getRight().get(j));
						if(x==-1) {
							tempset.add(p.getRight().get(j));
						}
						else tempset.addAll(varFinal.get(x).getFirst());
						if(!tempset.contains(new Variable("eps"))) {
							int id=varFinal.indexOf(p.getRight().get(i));
							varFinal.get(id).addAllToFollow(tempset);
							break;
						}
						else {
							tempset.remove(new Variable("eps"));
						}
						
						
					}
					if(j==p.getRight().size()) {
						int x1=varFinal.indexOf(p.getLeft());
						int id=varFinal.indexOf(p.getRight().get(i));
						varFinal.get(id).addAllToFollow(tempset);
						varFinal.get(id).addAllToFollow(varFinal.get(x1).getFollow());
					}
				}
			}
		}
	}
	
//	private void printSingleProd(Production p) {
//		System.out.print(p.getLeft().getName()+"->");
//		for(Variable v:p.getRight()) {
//			System.out.print(v.getName());
//		}
//		System.out.println();
//	}
	private void removeImmediateLR(Variable var) {
		Variable newVar=new Variable(var.getName()+"\'");
		if(!varFinal.contains(var)) varFinal.add(var);
		boolean flag=false;
		List<Production> newlistone=new ArrayList<Production>();
		for(int k=0;k<prodUtil.size();k++) {
			Production p=prodUtil.get(k);
			if(p.getLeft().equals(var)) {
				if(var.equals(p.getRight().get(0))) {
					//prodUtil.remove(p);
					newlistone.add(p);
					flag=true;
					List<Variable> temp=new ArrayList<Variable>();
					temp.addAll(p.getRight());
					temp.remove(0);
					temp.add(newVar);
					if(!varFinal.contains(newVar)) varFinal.add(newVar);
					if(!prodUtil.contains(new Production(newVar,temp))) {
						prodUtil.add(new Production(newVar,temp));
					}
 				}
			}
		}
		prodUtil.removeAll(newlistone);
		
		Variable epVar=new Variable("eps");
		epVar.setEpsilon();
		if(!varFinal.contains(epVar)) varFinal.add(epVar);
		Production ptemp=new Production(newVar,new ArrayList<Variable>());
		ptemp.addToRight(epVar);
		if(!prodUtil.contains(ptemp)) prodUtil.add(ptemp);
		if(flag) {
			List<Production> newlist=new ArrayList<Production>();
			for(int j=0;j<prodUtil.size();j++) {
				Production p=prodUtil.get(j);
				if(p.getLeft().equals(var)&&!var.equals(p.getRight().get(0))) {
					if(!newVar.equals(p.getRight().get(p.getRight().size()-1)))newlist.add(p);
					//System.out.println("removing: ");
					//this.printSingleProd(p);
					List<Variable> temp=new ArrayList<Variable>();
					temp.addAll(p.getRight());
					if(!temp.contains(newVar))temp.add(newVar);
					//System.out.println("adding production");
					//for(Variable b:temp) {
					//	System.out.print(b.getName());
					//}
					//System.out.println();
					if(!prodUtil.contains(new Production(var,temp))) {
						prodUtil.add(new Production(var,temp));
					}
				}
			}
			prodUtil.removeAll(newlist);
		}
	}
	
	public void removeGeneralLR() {
		for(int i=0;i<varUtil.size();i++) {
			Variable iter=varUtil.get(i);
			if(!iter.isEpsilon()&&!iter.isTerminal()) {
				for(int j=termn;j<i;j++) {
					Variable iterloop=varUtil.get(j);
					if(!iterloop.isEpsilon()&&!iterloop.isTerminal()) {
						for(int l=0;l<prodUtil.size();l++) {
							Production p=prodUtil.get(l);
							if(p.getLeft().equals(iter)&&iterloop.equals(p.getRight().get(0))) {
								prodUtil.remove(p);
								List<Variable> temp=new ArrayList<Variable>();
								temp.addAll(p.getRight());
								temp.remove(0);
								for(int k=0;k<prodUtil.size();k++) {
									Production pi=prodUtil.get(k);
									if(pi.getLeft().equals(iterloop)) {
										List<Variable> temp2=new ArrayList<Variable>();
										temp2.addAll(pi.getRight());
										temp2.addAll(temp);
										Production pnew=new Production(iter,temp2);
										if(!prodUtil.contains(pnew)) {
											prodUtil.add(pnew);
										}
									}
								}
							
							
							}
						
						
						}
					}
					
				
				
				}
			
			this.removeImmediateLR(iter);
			}
			
		}
	
	
	}
	
	public void printProds() {
		for(Variable v:varFinal) {
			if(!v.isEpsilon()&&!v.isTerminal()) {
				System.out.print(v.getName()+"->");
				int count=0;
				for(Production p:prodUtil) {
					if(p.getLeft().equals(v)) {
						if(count>0)System.out.print("|");
						for(Variable x:p.getRight()) {
							System.out.print(x.getName());
						}
						count++;
					}
				}
				System.out.println();
			}
		}
	}
}
