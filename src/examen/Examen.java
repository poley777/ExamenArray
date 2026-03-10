package examen;

import java.util.Arrays;

import java.util.Scanner;

public class Examen {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		char[][] sala = new char[6][10];
		
		//Inicializar sala a "L"
		for (int i = 0; i < 6; i++) {
			
			for (int j = 0; j < 10; j++) {
				sala[i][j] = 'L';
			}
		}
		
		
		int opcion;

			//Hacemos el menú para el usuario
	        do {

	            System.out.println("\nTEATRO");
	            System.out.println("1 Mostrar sala");
	            System.out.println("2 Reservar asiento suelto");
	            System.out.println("3 Reservar grupo en una fila");
	            System.out.println("4 Confirmar reservas");
	            System.out.println("5 Cancelar reservas");
	            System.out.println("6 Estadísticas");
	            System.out.println("7 Salir");

	            System.out.println("Elige una opción:");
	            opcion = sc.nextInt();
	            sc.nextLine();

	            switch (opcion) {

	                case 1:
	                    pintarSala(sala);
	                    break;

	                case 2:

	                	//Hacemos un try/catch para ver si podemos reservar algún asiento
	                    try {

	                    	//Pedimos al usuario que introduzca fila y columna de la entrada
	                        System.out.println("Introduce fila de la entrada:");
	                        int fila = sc.nextInt();
	                        
	                        System.out.println("Introduce columna de la entrada:");
	                        int col = sc.nextInt();

	                        if (reservarAsiento(sala, fila, col)) {
	                            System.out.println("Asiento reservado.");
	                        } else {
	                            System.out.println("No se pudo reservar.");
	                        }

	                    } catch (Exception e) {
	                        System.out.println("Entrada incorrecta.");
	                    }

	                    break;

	                case 3:

	                	//Preguntamos fila
	                    System.out.println("Fila:");
	                    int fila = sc.nextInt();

	                    //Preguntamos el número de personas
	                    System.out.println("Número de personas:");
	                    int personas = sc.nextInt();

	                    //Implementamos la función reservarGrupoEnFila
	                    int[] bloque = reservarGrupoEnFila(sala, fila, personas);

	                    //Si el bloque es not null se indicará la reserva hecha en fila
	                    if (bloque != null) {
	                        System.out.println("Reservado desde columna " + bloque[0] + " hasta " + bloque[1]);
	                    //En caso contrario se dirá que no se puede al usuario
	                    } else {
	                        System.out.println("No hay hueco para el grupo.");
	                    }

	                    break;

	                case 4:
	                	//Implementamos la función confirmarReservas e imprimimos el número de reservas confirmadas
	                    confirmarReservas(sala);
	                    System.out.println("Reservas confirmadas.");
	                    break;

	                case 5:
	                	//Implementamos la función cancelarReservas y mostramos el número de reservas canceladas
	                    cancelarReservas(sala);
	                    System.out.println("Reservas canceladas.");
	                    break;

	                case 6:
	                	//Implementamos la función mostrarEstadisticas
	                    mostrarEstadisticas(sala);
	                    break;

	            }
	        //El menú se repite hasta que el usuario eliga la opcion salir
	        } while (opcion != 7);

	}
	
	/*
	 * Función que muestra la sala por consola
	 * @param sala, array de carácteres que indica como pueden estar los asientos de la sala
	 * @return no devuelve nada imprime por consola la sala en formato de tabla
	 */
	
	static void pintarSala(char[][] sala) {
		
        System.out.print("   ");

        //Recorremos el número de columnas para hacer el encabezado 
        for (int i = 0; i < sala[0].length; i++) {
        	
            System.out.print(i + " ");
        }

        //Salto de línea
        System.out.println();

        //Recorremos el número de filas poniendo el número de fila en cada una
        for (int i = 0; i < sala.length; i++) {

            System.out.print(i + "  ");

            //Recorremos el array poniendo el estado del asiento en cada una
            for (int j = 0; j < sala[i].length; j++) {
            	
                System.out.print(sala[i][j] + " ");
            }

            System.out.println();
        }
	}
	
	/*
	 * Función que indica si la posición es válida de la fila y columna en el array sala
	 * @param sala, array de carácteres que indica como pueden estar los asientos de la sala
	 * @param fila, fila del array sala
	 * @param columna, columna del array sala
	 * @return resultado (true o false) de la posición
	 */
	
    public static boolean esPosicionValida(char[][] sala, int fila, int col) {
    	
    	//Inicializamos una variable resultado para la posición 
    	boolean resultado;
    	
    	//Hacemos el cálculo que indica el resultado para que la posición sea válida
    	resultado = (fila >= 0 && fila < sala.length && col >= 0 && col < sala[0].length);

    	return resultado;
    }
    
    /*
     * Función que cuenta el numero de asientos en un estado concreto ("L", "R" u "O")
     * @param sala, array de carácteres que indica como pueden estar los asientos de la sala
     * @param estado, carácter que indica el estado de un asiento
     * @return contador, contador de cuantos asientos hay en un estado concreto
     */
    
    public static int contarEstado(char[][] sala, char estado) {

    	//Creamos un contador para el estado de los asientos
        int contador = 0;

        //Recorremos el array 
        for (int i = 0; i < sala.length; i++) {
        	
            for (int j = 0; j < sala[i].length; j++) {

            	//Si la posición indica el estado que estamos buscando incrementa el contador
                if (sala[i][j] == estado) {
                    contador++;
                }
            }
        }
        return contador;
    }
    
    /*
     * Función que reserva asientos en caso de que se pueda y sino devuelve false
     * @param sala, array de carácteres que indica como pueden estar los asientos de la sala
     * @param fila, fila del array sala
     * @param col, columna del array sala
     * @return resultado (true o false) de la reserva a hacer
     */
    
    public static boolean reservarAsiento(char[][] sala, int fila, int col) {
    	
    	//Creamos un boolean resultado para saber si se puede reservar asiento
    	boolean resultado = false;

    	//Si la posición no es válida resultado pasa a ser false
        if (!esPosicionValida(sala, fila, col)) {
        	
            resultado = false;
            
        //Del contrario el asiento libre ("L") pasará a ser reservado ("R") Y resultado será true
        } else if (sala[fila][col] == 'L') {

            sala[fila][col] = 'R';
            resultado = true;

        }
        return resultado;
    }
    
    /*
     * Función que reserva grupo de asientos en una fila concreta, si no se puede devuelve null
     * @param sala, array de carácteres que indica como pueden estar los asientos de la sala
     * @param fila, fila del array sala
     * @param personas, número de personas que se quieren sentar juntas
     * @return bloque, array de dos posiciones con la columna de inicio y fin del bloque reservado o null si no se ha podido reservar
     */
    public static int[] reservarGrupoEnFila(char[][] sala, int fila, int personas) {

    	//Si la fila no se encuentra 
        if (fila < 0 || fila >= sala.length) {
            return null;
        }

        //Creamos un contador el número de èrsonas
        int contador = 0;

        //Recorremos el número de columnas 
        for (int col = 0; col < sala[fila].length; col++) {

        	//Si el asiento esta libre incrementa el contador
            if (sala[fila][col] == 'L') {

                contador++;

                //Si contador es igual al número de personas declaramos la columna de inicio
                if (contador == personas) {

                    int inicio = col - personas + 1;

                    //Recorremos la fila reservando cada asiento
                    for (int k = inicio; k <= col; k++) {
                        sala[fila][k] = 'R';
                    }

                    //Devolvemos el array desde columna de inicio hasta el fin del bloque reservado
                    return new int[]{inicio, col};
                }

            } else {

                contador = 0;
            }
        }
        return null;
    }
    
    /*
     * Función que confirma las reservas, cambiando el estado de "R" a "O"
     * @param sala, array de carácteres que indica como pueden estar los asientos de la sala
     * @return no devuelve nada, cambia el estado de los asientos reservados a ocupados
     */
    public static void confirmarReservas(char[][] sala) {

    	//Recorremos el array sala para encontrar asientos reservados
        for (int i = 0; i < sala.length; i++) {
        	
            for (int j = 0; j < sala[i].length; j++) {

            	//Si la posicion esta reservada ("R"), pasa a ser ocupada ("O")
                if (sala[i][j] == 'R') {
                    sala[i][j] = 'O';
                }
            }
        }
    }
    
    /*
	 * Función que cancela las reservas, cambiando el estado de "R" a "L"
	 * @param sala, array de carácteres que indica como pueden estar los asientos de la sala
	 * @return no devuelve nada, cambia el estado de los asientos reservados a libres
	 */
    
    public static void cancelarReservas(char[][] sala) {

    	//Recorremos el array sala para encontrar asientos reservados 
        for (int i = 0; i < sala.length; i++) {
        	
            for (int j = 0; j < sala[i].length; j++) {

            	//Si la posición esta reservada ("R"), pasa a ser libre ("L")
                if (sala[i][j] == 'R') {
                    sala[i][j] = 'L';
                }

            }
        }

    }
    
    /*
	 * Función que muestra estadísticas de la sala, como el número de asientos libres,
	 * reservados y ocupados, el porcentaje de ocupación y la fila con más asientos ocupados
	 * @param sala, array de carácteres que indica como pueden estar los asientos de la sala
	 * @return no devuelve nada, imprime por consola las estadísticas de la sala
	 *
	 */

    public static void mostrarEstadisticas(char[][] sala) {

    	//Implementamos la funcion contarEstado para tener el número de asientos libres, reservados y ocupados
        int libres = contarEstado(sala, 'L');
        int reservados = contarEstado(sala, 'R');
        int ocupados = contarEstado(sala, 'O');

        //Calculamos el total de asientos
        int total = sala.length * sala[0].length;

        //Calculamos el porcentaje de ocupación real respecto del total
        double porcentaje = (ocupados * 100.0) / total;

        //Inicializamos las variable filas máximas y máximos ocupados
        int filaMax = -1;
        int maxOcupados = -1;

        for (int i = 0; i < sala.length; i++) {

        	//Creamos un contador para el número de asientos ocupados
            int cont = 0;

            for (int j = 0; j < sala[i].length; j++) {

            	//Si posición esta ocupada ("0") incrementa el contador
                if (sala[i][j] == 'O') {
                    cont++;
                }

            }

            //Creamos una condición para saber saber en que fila encontramos los mayores asientos ocupados
            if (cont > maxOcupados) {

                maxOcupados = cont;
                filaMax = i;
            }
        }

        //Mostramos los asientos libre, reservados y ocupados
        System.out.println("Libres: " + libres);
        System.out.println("Reservados: " + reservados);
        System.out.println("Ocupados: " + ocupados);

        //Mostramos el porcentaje de ocupación
        System.out.println("Porcentaje ocupación: " + porcentaje + "%");

        //Mostramos las filas con más ocupados
        System.out.println("Fila con más ocupados: " + filaMax);

    }
}















