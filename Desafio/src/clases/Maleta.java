package clases;

import java.util.ArrayList;

public class Maleta 
{
	private ArrayList objetos;	//LISTA DE PESOS
	private float disponible;		//ESPACIO DISPONIBLE
	private float total;			//CAPACIDAD MAXIMA
	private float actual;			//ESPACIO OCUPADO
	
	//CONSTRUCTOR
	public Maleta(float total)
	{
		//SE INICIALIZA EL ARRAYLIST COMO UNA MALETA VACIA
		objetos=new ArrayList();
		//SE ESTABLECE CAPACIDAD TOTAL DE LA MALETA
		setTotal(total);
		//MALETA VACIA POR LO TANTO EL ESPACIO OCUPADO ES NULO
		setActual(0);
		//SE ESTABLECE EL ESPACIO DISPONIBLE COMO EL TOTAL
		setDisponible();
	}
	//*******************************************
	/**************GETS Y SETS******************/
	//*******************************************
	public float getDisponible() {
		return disponible;
	}
	public void setDisponible() 
	{
		this.disponible = this.total-this.actual;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public float getActual() {
		return actual;
	}
	public void setActual(float actual) {
		this.actual = actual;
	}
	//*****************************************
	/************FUNCIONES********************/
	//*****************************************
	
	
	public boolean add(float peso)
	{
		//VERIFICA SI LA MALETA PUEDE CONTENER EL OBJETO INGRESADO
		if (getDisponible()<peso)
		{
			//EL PESO DEL OBJETO ES MAYOR AL ESPACIO DISPONIBLE EN LA MALETA, POR LO TANTO RETORNA FALSE
			return false;
		}
		//EN CASO DE QUE EL OBJETO PUEDA SER INGRESADO A LA MALETA, SE ANIADE AL ARRAYLIST Y SE ACTUALIZAN LAS VARIABLES DE LA MALETA
		else
		{
			objetos.add(peso);
			//SE ACTUALIZA EL PESO ACTUAL
			setActual(getActual()+peso);
			//Y EL PESO DISPONIBLE
			setDisponible();
		}
		
		return true;
	}
	
	public Maleta clone()
	{
		//SE CREA UNA MALETA IDENTICA A LA ORIGINAL
		Maleta nueva=new Maleta(this.getTotal());
		//SE COPIA EL VALOR DE SUS VARIABLES
		nueva.setActual(getActual());
		nueva.setDisponible();
		
		int i;
		//SE INGRESA UNA COPIA DE LOS ELEMENTOS A LA NUEVA MALETA
		for (i=0;i<objetos.size();i++)
		{
			nueva.add((float)objetos.get(i));
		}
		//SE RETORNA LA COPIA DE LA MALETA
		return nueva;
	}
	
	public float last()
	{
		return (float)objetos.get(objetos.size()-1);
	}

	public void mostrarContenido()
	{
		System.out.println(objetos);
	}
}

































