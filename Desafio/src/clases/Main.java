package clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main 
{
	private static float capacidad;
	
	public static void main(String args[]) throws FileNotFoundException
	{
		//LEE EL ARCHIVO DE TEXTO CON LOS OBJETOS
		ArrayList objetos=read();
		//SE ORDENA DE MAYOR A MENOR PESO
		Collections.sort(objetos);
		Collections.reverse(objetos);
		
		//CREA EL ESTADO INICIAL
		State inicial=new State(capacidad);
		
		//MIENTRAS QUEDEN OBJETOS QUE ALMACENAR EN LAS MALETAS
		while(objetos.size()!=0)
		{
			//GENERAR, REVISAR Y SELECCIONAR MEJOR ESTADO PARA SEGUIR
			ArrayList posibles=inicial.generarEstados(objetos);
			inicial = evaluarEstado(posibles);
			borrarObjeto(objetos, inicial);
		}
		
		inicial.showTime();
	}
	
	
	public static void borrarObjeto(ArrayList objetos, State objeto)
	{ 
		Maleta maleta = objeto.getActual();
		float borrar = maleta.last();
		for(int i = 0 ; i < objetos.size(); i++)
		{
			if((float)objetos.get(i) == borrar)
			{
				objetos.remove(i);
				break;
			}
		}
	}
	public static State evaluarEstado(ArrayList posibles)
	{
		State mejor_estado = (State)posibles.get(0);
		State aux;
		Maleta maleta, mejor_maleta=mejor_estado.getActual();
		//Si la maleta solo contiene un elemento, este debe ser el mas grande
		//Si el peso de la maleta es decimal, se recomienda tomar uno decimal
		//(y contiene mas de un estado)
		//Si al añadir ese objeto la maleta alcanza su capacidad maxima, se elige ese.
		for ( int i = 1 ; i < posibles.size() ; i++)
		{
			aux = (State)posibles.get(i);
			maleta = aux.getActual();
			if(maleta.getDisponible() < mejor_maleta.getDisponible())
			{
				mejor_estado = aux;
				mejor_maleta = maleta;
			}
		}
		
		
		return mejor_estado;
	}
	
	//*******************************************
	/**************GETS Y SETS******************/
	//*******************************************
	public static float getCapacidad() {
		return capacidad;
	}


	public static void setCapacidad(float temp) {
		capacidad = temp;
	}
	
	//*****************************************
	/************FUNCIONES
	 * @throws FileNotFoundException ********************/
	//*****************************************
	
	public static ArrayList read() throws FileNotFoundException
	{
		//SE CREA LA LISTA
		ArrayList objetos=new ArrayList();
		
		//LECTURA DEL ARCHIVO DE TEXTO
		File archivo = new File ("src/extras/objetos");
		
		Scanner scan = new Scanner(archivo);
		//VARIABLE QUE RECORRE EL DOCUMENTO DE TEXTO
		float temp;
		//FLAG PARA LA CAPACIDAD DE LA MALETA
		int aux=0;
		while(scan.hasNextFloat())
		{
			temp=scan.nextFloat();
			//SI ES EL PRIMER NUMERO DEL ARCHIVO
			if (aux==0)
			{
				setCapacidad(temp);
				aux++;
			}
			//EN CASO DE QUE SEAN LOS PESOS
			else
			{
				objetos.add(temp);
			}
		}
		
		//System.out.println("capacidad de cada maleta: "+capacidad);
		//System.out.println("Objetos ingresados");
		//System.out.println(objetos);
		
		return objetos;
	}
}














