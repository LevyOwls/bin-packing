package bp;

import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Random;



public class State {

	
	ArrayList<Objeto>lista;
	ArrayList<Mochila>mochilas;
	ArrayList<Action>acciones;
	
	
	public State(ArrayList<Objeto> lista, ArrayList<Mochila> mochilas) {
		this.lista = lista;
		this.mochilas = mochilas;
	}
	
	
	
	public ArrayList<Action>  get_actions (State maletas){ //
		
		
		acciones=new ArrayList<Action>();
		Action accion;
		accion=new Action(0,0);
		int i=0;
		int j=0;
		
		
		
		if (!maletas.lista.isEmpty()) {
			for (i=0; i<maletas.mochilas.size();i++) {
				for (j=0; j<maletas.lista.size();j++) {
					if (maletas.mochilas.get(i).pesoMax-maletas.mochilas.get(i).currentP>maletas.lista.get(j).peso) {
						accion.setMochilaPos(i);
						accion.setObjPos(j);
						if (!maletas.existeAcc(accion,acciones,maletas.lista)) {//verifica que no se agregara un objeto similar antes
							
							acciones.add(accion);
						}
					}
					
				}
				
				
			}
			
			if (acciones.isEmpty()) {
				
				accion.setMochilaPos(i);
				for (j=0; j<maletas.lista.size();j++) {
					if (maletas.mochilas.get(i-1).pesoMax-maletas.mochilas.get(i-1).currentP>maletas.lista.get(j).peso) {
						
						accion.setObjPos(j);
						acciones.add(accion);
						
					}
					
				}
			}
		
		}
		
		return acciones;
	}
	
	
	public boolean existeAcc (Action accion , ArrayList<Action> acciones,ArrayList <Objeto> lista) {
		boolean existe=false;
		int i=0;
		
		for (i=0;i<acciones.size();i++) { //si la mochila es la misma y el peso de los objetos es el mismo, ese objeto ya se coloco
			if (accion.mochilaPos==acciones.get(i).mochilaPos && lista.get(accion.objPos).peso==lista.get(acciones.get(i).objPos).peso) {
				existe=true;
			}
			
		}
		
		
		
		return existe;
	}
	
	public State transition(State maleta, Action accion) {
		

		State newMaleta;
		
		newMaleta = new State(maleta.lista,maleta.mochilas);
		if (newMaleta.mochilas.get(accion.mochilaPos)==null) {
			Mochila mochi;
			mochi=new Mochila(0,0,null);
			
			newMaleta.mochilas.add(mochi);
		}
		
		if (newMaleta.mochilas.get(accion.mochilaPos).items==null) {
			newMaleta.mochilas.get(accion.mochilaPos).items= new ArrayList<Objeto>();
		}
	
		newMaleta.mochilas.get(accion.mochilaPos).items.add(newMaleta.lista.get(accion.objPos));
		
		newMaleta.mochilas.get(accion.mochilaPos).currentP=newMaleta.mochilas.get(accion.mochilaPos).currentP+newMaleta.lista.get(accion.objPos).peso;
		//eliminar objeto de newMaleta.lista
		newMaleta.lista.remove(accion.objPos);
		
		//agregar el objeto creando una funcion optima
		
		
		
		return newMaleta;

		
	}
	
	
	
	public boolean is_final_state(State maleta) {
		
		boolean fin=false;
		
		if (maleta.lista.isEmpty()) {
			fin=true;
		}
		
		
		return fin;
	}
	
	//arreglar(?)
	public void anchura (State inicial) {
		Queue <State> S=new ArrayDeque<>();
		int i=0;
		
		
		S.add(inicial);
		int count=0;
		int count2=0;
		
		while (!S.isEmpty()) {
			State s= S.element();
			S.remove();
			count2++;
			
			s.acciones=s.get_actions(s);
			
			if (s.is_final_state(s)) {
				count++;
				System.out.println("tamaño mochila n°"+count+" "+s.mochilas.get(0).pesoMax+" cantidad de mochilas "+s.mochilas.size());
				
			}
			
			for (i=0;i<s.acciones.size();i++) {
				State ss=s.transition(s,s.acciones.get(i));
				S.add(ss);
				
			}
			
			
		}
		System.out.println(" cantidad de finales "+count+" cantidad de estados "+count2);
		
	}
	
	public void bestF (State inicial) {
		ArrayList <State> S;
		S=new ArrayList<State>();
		int i=0;
		int count=0;
		
		S.add(inicial);
		
		
		while (!S.isEmpty()) {
			State s= S.get(0);
			S.remove(0);
			
			
			s.acciones=s.get_actions(s);
			
			if (s.is_final_state(s)) {
				count++;
			}
			for (i=0;i<s.acciones.size();i++) {
				State ss=s.transition(s,s.acciones.get(i));
				
				S=ss.ordenO(S,ss);
			}
			
		}
		System.out.println("cantidad de soluciones "+count);
		
	}
	
	
	public ArrayList <State> ordenO( ArrayList <State>S,State ss) {
		ArrayList <State> s;
		s=new ArrayList <State>();
		s.addAll(S);
		
		int i=0;
		int j=0;
		
		double cont=0;
		double cont2=0;
		
		for (i=0;i<s.size();i++) {
			cont=0;
			cont2=0;
			for (j=0;j<ss.mochilas.size();j++) {
				cont=ss.mochilas.get(j).currentP+cont;
				cont2=s.get(i).mochilas.get(j).currentP+cont2;
				
				
			}
			cont=ss.mochilas.get(0).pesoMax*mochilas.size()-cont;
			cont2=s.get(i).mochilas.get(0).pesoMax*mochilas.size()-cont2;
			//compara los pesos de las mochilas en el estado, prioriza estados que tienen las mochilas completas
			if (cont<cont2) {//lo coloca al encontrar la posicion
				s.add(i, ss);
				break;
			}
			
		}
		if (s.size()==S.size()) {//lo coloca al final si no fue colocado
			s.add(ss);
		}
		
		return s;
	}
	
	
	public static void main(String arg[]) {
		
		State initial;
		Mochila mochila;
		ArrayList <Mochila>maletas;
		maletas=new ArrayList<Mochila>();
		ArrayList <Objeto>lis;
		lis=new ArrayList<Objeto>();
		Objeto obj;

		
		Random r=new Random(System.currentTimeMillis());
		Random r2=new Random(System.currentTimeMillis());
		
		int x=r.nextInt(10);
		double pesom=0;
		double pesoobj=0;
		int y=0;
		int z=0;
		int i=0;
		int j=0;
		
		z=r.nextInt(40)+10;
		
		for (i=0;i<=x;i++) {
			pesoobj=(Math.round(r2.nextDouble()*100)/100d)+r.nextInt(9)+1;
			while (pesoobj>z ) {
				pesoobj=(Math.round(r2.nextDouble()*100)/100d)+r.nextInt(9)+1;
				
			}
			obj=new Objeto(pesoobj);
			y=r.nextInt(10);
			for (j=0;j<=y;j++) {
				lis.add(obj);
			}
			
		}
		for (i=0;i<lis.size();i++) {
			pesom=lis.get(i).peso+pesom;
		}
		
		pesom=pesom/z;
		
		for (i=0;i<pesom;i++) {
			mochila=new Mochila(z,0,null);
			maletas.add(mochila);
		}
		initial=new State(lis,maletas);
		
		
		System.out.println("objetos de la lista ");
		for (i=0;i<initial.lista.size();i++) {
			System.out.println(" objeto"+(i+1)+" "+initial.lista.get(i).peso);
			
		}
		System.out.println("tamaño mochila "+initial.mochilas.get(0).pesoMax+" cantidad de mochilas "+initial.mochilas.size());
		
		
		
		initial.anchura(initial);
		
		
	}
	

}
