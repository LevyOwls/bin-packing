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
			
			
		}
		
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














