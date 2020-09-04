package clases;

import java.util.ArrayList;
import java.util.Collections;

//import jdk.nashorn.internal.ir.SetSplitState;

public class State 
{
	//LISTA DE MALETAS
	private ArrayList maletas;
	//CANTIDAD DE MALETAS EN LA LISTA
	private int cantidad;
	//SUMA DEL ESPACIO SOBRANTE DE TODAS LAS MALETAS DE LA LISTA
	private float espacioSobrante;
	//MALETA SOBRE LA CUAL SE ESTA TRABAJANDO ACTUALMENTE
	private Maleta actual;
	//CAPACIDAD DE CADA MALETA
	
	//CONSTRUCTOR ESTADO INICIAL
	public State(float capacidad)
	{
		//GENERA UNA LISTA DE MALETAS
		maletas=new ArrayList();
		//CREA UNA MALETA
		actual=new Maleta(capacidad);
		//INGRESA LA MALETA CREADA A LA LISTA
		maletas.add(actual);
		//SE ACTUALIZA EL TAMANIO DE LA LISTA Y EL ESPACIO DISPONIBLE
		setCantidad(maletas.size());
		//YA QUE ES UNA MALETA RECIEN CREADA Y ES EL ESTADO INICIAL, EST=CAPACIDAD
		setEspacioSobrante(capacidad);
	}
	public State()
	{
		
	}
	
	//*******************************************
	/**************GETS Y SETS******************/
	//*******************************************
	public ArrayList getMaletas()
	{
		return maletas;
	}
	public void setMaletas(ArrayList maletas)
	{
		this.maletas=maletas;
	}
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getEspacioSobrante() {
		return espacioSobrante;
	}

	public void setEspacioSobrante(float eST) {
		this.espacioSobrante = eST;
	}
	public void setEspacioSobrante()
	{
		int i;
		//VARIABLE TEMPORAL QUE RECORRE LAS MALETAS
		Maleta temp;
		//SUMA ACTUAL
		float sum=0;
		for (i=0;i<maletas.size();i++)
		{
			temp=(Maleta)maletas.get(i);
			sum+=temp.getDisponible();
		}
		//ASIGNA LA SUMA DE TODAS LAS MALETAS
		setEspacioSobrante(sum);
	}
	public Maleta getActual()
	{
		return actual;
	}
	public void setActual(Maleta m)
	{
		actual=m;
	}
	//*****************************************
	/************FUNCIONES********************/
	//*****************************************
	
	/**
	 * SE CREA UNA NUEVA MALETA PARA UBICAR EL OBJETO INGRESADO
	 * @param peso				PESO DEL OBJETO A INGRESAR
	 */
	public void nuevaMaleta(float peso)
	{
		//OBTIENE LA CAPACIDAD TOTAL DE LA MALETA ACTUAL
		Maleta nueva=new Maleta(actual.getTotal());
		//SE AGREGA EL PESO A LA MALETA
		nueva.add(peso);
		//SE ACTUALIZA LA MALETA ACTUAL
		this.actual=nueva;
		//SE INGRESA LA NUEVA ACTUAL AL ARRAYLIST
		maletas.add(nueva);
		
		//SE ACTUALIZA LA CANTIDAD DE MALETAS
		setCantidad(maletas.size());
		//SE ACTUALIZA EL ESPACIO SOBRANTE TOTAL
		setEspacioSobrante();
	}
	
	public ArrayList generarEstados(ArrayList objetos)
	{
		ArrayList estados=new ArrayList();
		
		int i;
		float temp;
		//SE GENERAN LOS ESTADOS CON TODOS LOS OBJETOS POSIBLES
		for (i=0;i<objetos.size();i++)
		{
			//SE OBTIENE EL PESO DEL OBJETO
			temp=(Float)objetos.get(i);
			//SE CREA UN ESTADO CLON
			State estado=clone();
			//SE INTENTA INGRESAR EL OBJETO A LA MALETA ACTUAL
			if (estado.getActual().add(temp))
			{
				//EN CASO QUE SE LOGRE AGREGAR SE INGRESA AL ARRAYLIST
				estados.add(estado);
			}
			//SI EL OBJETO NO CAE EN LA MALETA NO SE AGREGA A ESTADOS.
		}
		
		//SI EXISTE AL MENOS UN ESTADO POSIBLE SE RETORNA LA LISTA DE ESTADOS GENERADOS
		if (estados.size()>0)
		{
			return estados;
		}
		//EN CASO CONTRARIO, SE AGREGA EL OBJETO MAS GRANDE A LA SIGUIENTE MALETA
		State estado=clone();
		
		//SE OBTIENE EL ELEMENTO DE MAYOR PESO Y SE CREA UNA NUEVA MALETA
		estado.nuevaMaleta((float)objetos.get(0));
		estados.add(estado);
		return estados;
	}
	
	
	public State clone()
	{
		State nuevo=new State();
		ArrayList maletas=new ArrayList();
		int i;
		Maleta temp;
		//SE COPIAN LAS MALETAS
		for (i=0;i<this.maletas.size();i++)
		{
			temp=(Maleta)this.maletas.get(i);
			maletas.add(temp.clone());
		}
		//COPIA DE LAS MALETAS REALIZADA
		nuevo.setMaletas(maletas);
		//SE ACTUALIZAN LAS VARIABLES
		nuevo.setCantidad(maletas.size());
		nuevo.setEspacioSobrante();
		nuevo.setActual((Maleta)maletas.get(maletas.size()-1));
		return nuevo;
	}
	
	public void showTime()
	{
		int i;
		Maleta temp;
		
		for (i=0;i<maletas.size();i++)
		{
			temp=(Maleta)maletas.get(i);
			
			System.out.print("maleta "+(i+1)+": ");
			temp.mostrarContenido();
			System.out.println();
		}
	}
}
