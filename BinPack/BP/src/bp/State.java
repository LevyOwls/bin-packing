package bp;

import java.util.ArrayList;



public class State {

	
	ArrayList<Objeto>lista;
	ArrayList<Mochila>mochilas;
	ArrayList<Action>acciones;
	
	
	public State(ArrayList<Objeto> lista, ArrayList<Mochila> mochilas) {
		this.lista = lista;
		this.mochilas = mochilas;
	}
	
	
	//falta comprobar que los objetos no sean superior a la mochila
	public ArrayList<Action>  get_actions (State maletas){ //
		
		
		acciones=new ArrayList<Action>();
		Action accion;
		accion=new Action(0,0);
		int i=0;
		int j=0;
		ArrayList<Integer>posNE;//guarda la posicion de los objetos no enteros
		posNE= new ArrayList <Integer>();
		int posM=0;//mochila mas vacia
		int posO=0;//objeto mas pesado
		
		
		for (i=0; i<maletas.mochilas.size();i++) {
			for (j=0; j<maletas.lista.size();j++) {
				if (maletas.lista.get(j).peso%1!=0) {
					if ((maletas.mochilas.get(i).pesoMax-maletas.mochilas.get(i).currentP+maletas.lista.get(j).peso)%1==0) {//agrega un objeto no entero si al agregarlo la mochila que queda se vuelve un numero entero
						accion.setObjPos(j);
						accion.setMochilaPos(i);
						acciones.add(accion);     
					}
					else if ((maletas.mochilas.get(i).pesoMax-maletas.mochilas.get(i).currentP)%1!=0) {
						accion.setObjPos(j);
						accion.setMochilaPos(i);
						acciones.add(accion); //agrega a la mochila el objeto si esta no es entero
					}
					else {
						posNE.add(j);
					}
				}
				if ((maletas.mochilas.get(i).pesoMax-maletas.mochilas.get(i).currentP)%1==0) {
					accion.setObjPos(j);
					accion.setMochilaPos(i);
					acciones.add(accion); //agrega un objeto a la mochila si este y la mochila son enteros
				}
				if (maletas.lista.get(j).peso>maletas.lista.get(posO).peso) {
					posO=j;
				}
				
			}
			if (maletas.mochilas.get(i).currentP<maletas.mochilas.get(posM).currentP) {
				posM=i;
			}
			
		}
		/*for (i=0; j<posNE.size();i++) {
			for (j=0; j<posNE.size()-1;j++) {
				if ((maletas.lista.get(i).peso+maletas.lista.get(j).peso)%1==0) {
					//hay que agregar un Action que soporte mas de un objeto a la vez, o agregamos ambos objetos como separado como accion
				}
			}
		}*/
		accion.setMochilaPos(posM);
		accion.setObjPos(posO);
		acciones.add(accion);
		
		
		return acciones;
	}
	
	
	
	
	public State transition(State maleta, Action accion) {
		
		
		State newMaleta;
		newMaleta = new State(maleta.lista,maleta.mochilas);
		if (newMaleta.mochilas.get(accion.mochilaPos)==null) {
			//newMaleta.mochilas.add(mochila)
		}
		newMaleta.mochilas.get(accion.mochilaPos).items.add(newMaleta.lista.get(accion.objPos));
		
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
	

}
