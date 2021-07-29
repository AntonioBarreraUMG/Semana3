/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tarea;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author jeant
 */
public class ClsComisiones {
    
    public static String[][] comisiones = new String[3][6];
    public static Scanner t = new Scanner(System.in);
    public static NumberFormat formatoCantidad = NumberFormat.getCurrencyInstance(new Locale("es","GT"));
    
    private static final int NOMBRE = 0;
    private static final int ENERO = 1;
    private static final int FEBRERO = 2;
    private static final int MARZO = 3;
    private static final int TOTAL = 4;
    private static final int PROMEDIO = 5;
    private static final int MESES = 3;
    
    //Retorna en formato de moneda local el argumento numérico que recibe.
    public static String cambiarFormato(float cantidad){
        String cantidadStr = String.valueOf(cantidad);
        double nuevaCantidad = Double.parseDouble(cantidadStr);
        return formatoCantidad.format(nuevaCantidad);
    }
    
    //Carga la matriz con información ingresada por consola.
    public static void cargarInformacion(int fila){
        System.out.println("* Registro numero " + (fila+1));
        System.out.println("Ingrese nombre: ");
        comisiones[fila][NOMBRE] = t.nextLine();
        System.out.println("Ingrese Enero: ");
        comisiones[fila][ENERO] = t.nextLine();
        System.out.println("Ingrese Febrero: ");
        comisiones[fila][FEBRERO] = t.nextLine();
        System.out.println("Ingrese Marzo: ");
        comisiones[fila][MARZO] = t.nextLine();
        System.out.println("\n");
    } 
    
    //Imprime los elementos del argumento recibido (matriz) con un decorado de bordes.
    //Utiliza la función de cambio de formato de cantidades numéricas a monetarias.
    public static void imprimirDecorado(String[][] ma){
        System.out.println("TABLA DE DATOS:");
        for (int x = 0; x < ma.length; x++) { 
            System.out.print("|");
            for (int y = 0; y < ma[x].length; y++) {
                if (y > 0){
                    float cantidad = Float.parseFloat(ma[x][y]);
                    System.out.print(cambiarFormato(cantidad));
                }
                else{
                    System.out.print(ma[x][y]);
                }
                if (y != ma[x].length - 1) {
                    System.out.print("\t");
                }
            }
            System.out.println("|");
        }
        float totalVentas = calculoGranTotal(comisiones);
        float promedioMeses = calculoPromedioMeses(comisiones); 
        System.out.println("\n*Total de ventas: " + formatoCantidad.format(totalVentas));
        System.out.println("\n*Promedio por mes: " + formatoCantidad.format(promedioMeses));
    }
    
    //Calcula e ingresa en la matriz los totales de ventas de cada persona durante el período.
    public static void calculoTotalPersona(String[][] ma){
        for (int i = 0; i < ma.length; i++) {
            ma[i][TOTAL] = String.valueOf(Float.parseFloat(ma[i][ENERO])
                            + Float.parseFloat(ma[i][FEBRERO])
                            + Float.parseFloat(ma[i][MARZO]));
        }
    } 
    
    //Calcula e ingresa en la matriz los promedios de ventas mensuales de cada persona.
    public static void calculoPromedioPersona(String[][] ma){
        calculoTotalPersona(ma);
        for (int i = 0; i < ma.length; i++) {
            ma[i][PROMEDIO] = String.valueOf(Float.parseFloat(ma[i][TOTAL])/MESES);
        }
    }
    
    //Retorna un arreglo con los totales de ventas de cada uno de los meses.
    public static float[] calculoTotalMes(String[][] ma){
        float[] meses = new float[3];
        for (int i = 0; i < meses.length; i++) {
            meses[0] += Float.parseFloat(ma[i][ENERO]);
            meses[1] += Float.parseFloat(ma[i][FEBRERO]);
            meses[2] += Float.parseFloat(ma[i][MARZO]);
        }
        return meses;
    }
    
    //Calcula el monto total de ventas durante el período.
    public static float calculoGranTotal(String[][] ma){
        float[] meses = calculoTotalMes(ma);
        float total = 0;
        for (int i = 0; i < meses.length; i++) {
            total += meses[i];
        }
        return total;
    }
    
    //Calcula el promedio mensual de ventas durante el período.
    public static float calculoPromedioMeses(String[][] ma){
        return calculoGranTotal(ma)/MESES;
    }
    
    //Retorna una cadena que indica al mayor y menor vendedor del mes solicitado.
    //Dicha cadena está estructurada de la forma "Mayor vendedor/Menor vendedor."
    public static String vendedoresSobresalientesMes(String[][] ma, int mes){
        String nombreMayor = ma[0][NOMBRE];
        String nombreMenor = ma[0][NOMBRE];
        float totalMayor = Float.parseFloat(ma[0][ENERO]);
        float totalMenor = Float.parseFloat(ma[0][ENERO]);
        for (int i = 0; i < ma.length; i++) {
            if(Float.parseFloat(ma[i][mes]) > totalMayor){
                totalMayor = Float.parseFloat(ma[i][mes]);
                nombreMayor = ma[i][NOMBRE];
            }
            if(Float.parseFloat(ma[i][mes]) < totalMenor){
                totalMenor = Float.parseFloat(ma[i][mes]);
                nombreMenor = ma[i][NOMBRE];
            }
        }
        String resultado = nombreMayor + " con " + formatoCantidad.format(totalMayor) + 
                        "/" + nombreMenor + " con " + formatoCantidad.format(totalMenor);
        return resultado;
    }
    
    //Retorna una cadena con el nombre y el monto del mejor vendedor del período.
    public static String mejorVendedor(String[][] ma){
        String nombre = "";
        float total = 1;
        for (int i = 0; i < ma.length; i++) {
            if(Float.parseFloat(ma[i][TOTAL]) > total){
                total = Float.parseFloat(ma[i][TOTAL]);
                nombre = ma[i][NOMBRE];
            }
        }
        return "Nombre: " + nombre + "\nTotal: " + formatoCantidad.format(total);
    }
    
    //Recibe un nuevo dato que sustituirá al actual dentro de la matriz en la coordenada dada.
    public static void editarDato(String[][] ma, int fila, int columna, String nuevoDato){
        ma[fila-1][columna-1] = nuevoDato;
    }
    
    //Retorna la ubicación de una cantidad dentro de una matriz.
    public static String buscarCantidad(String[][] ma, float cantidad){
        String resultado = "";
        for (int i = 0; i < ma.length; i++) {
            if(Float.parseFloat(ma[i][ENERO]) == cantidad){
                resultado = "La venta fue realizada por " + ma[i][NOMBRE] 
                            + " en el mes de enero.";
            }
            if(Float.parseFloat(ma[i][FEBRERO]) == cantidad){
                resultado = "La venta fue realizada por " + ma[i][NOMBRE] 
                            + " en el mes de febrero.";
            }
            if(Float.parseFloat(ma[i][MARZO]) == cantidad){
                resultado = "La venta fue realizada por " + ma[i][NOMBRE] 
                            + " en el mes de marzo.";
            }
        }
        if(resultado == ""){
            resultado = "No se ha encontrado ninguna coincidencia.";
        }
        return resultado;
    }
    
    //Pide al usuario los datos necesarios para buscar al mayor y menor vendedor.
    public static void ejecutarVendedoresSobresalientes(String[][] ma){
        String mayor, menor;
        String[] sobresalientes = new String[2];
        System.out.println("Ingrese el numero del mes que desea evaluar <1-3>:");
        int mes = t.nextInt();
        System.out.println("Ingrese la opción que desea: <1>Mayor vendedor <2>Menor vendedor");
        int opcion = t.nextInt();
        String resultado = vendedoresSobresalientesMes(comisiones, mes);
        sobresalientes = resultado.split("/");
        mayor = sobresalientes[0];
        menor = sobresalientes[1];
        switch (opcion) {
            case 1:
                System.out.println("El mayor vendedor es " + mayor);
                break;
            case 2:
                System.out.println("El menor vendedor es " + menor);
                break;
            default:
                System.out.println("Ingrese una opcion valida.");
                break;
        }
    }
    
    //Ejecuta simultaneamente las funciones de "calculoTotalPersona" y "calculoPromedioPersona".
    public static void actualizarCalculos(String[][] ma){
        calculoTotalPersona(ma);
        calculoPromedioPersona(ma);
    }   
    
    //Pide al usuario la posición y el nuevo dato del elemento de la matriz a modificar.
    public static void ejecutarEditarDato(String[][] ma){
        System.out.println("\nIngrese el numero de fila que desea editar: ");
        int fila = t.nextInt();
        System.out.println("Ingrese el numero de columna que desea editar: ");
        int columna = t.nextInt();
        System.out.println("Ingrese el nuevo dato: ");
        t.nextLine();
        String nuevoDato = t.nextLine();
        editarDato(ma,fila,columna,nuevoDato);
    }
    
    //Función principal.
    public static void main(String[] args){
        int opcion = 0;
        //Despliegue de menú principal.
        while(opcion != 7){
            System.out.println("\n");
            System.out.println("Elija una opcion");
            System.out.println(" 1.Ingresar información."
                            + "\n 2.Mayor y Menor venta (por mes)."
                            + "\n 3.Mayor venta (general)."
                            + "\n 4.Editar datos."
                            + "\n 5.Buscar por cantidad."
                            + "\n 6.Mostrar tabla."
                            + "\n 7.Salir ");
            opcion = t.nextInt();
            System.out.println("\n");
            t.nextLine();
            
            switch(opcion){
                case 1 :
                    //Opción: Cargar información.
                    try{
                        for (int i = 0; i < comisiones.length; i++) {
                            cargarInformacion(i);
                        }
                        actualizarCalculos(comisiones);
                        System.out.println("Información ingresada exitosamente.");
                        }
                    catch(Exception e){
                        System.out.println("Los datos correspondiente a los meses "
                                        + "debe ser valores numericos.");
                    }
                    break;
                
                case 2 :
                    //Opción: Mayor y menor venta (por mes).
                    try{
                        ejecutarVendedoresSobresalientes(comisiones);
                    } 
                    catch (Exception e){
                        System.out.println("Los valores a ingresar deben ser numeros "
                                            + "enteros dentro del rango especificado.");
                    }
                    break;
                
                case 3 :
                    //Opción: Mayor venta.
                    String resultado = mejorVendedor(comisiones);
                    System.out.println("Mejor vendedor");
                    System.out.println(resultado);
                    break;
     
                case 4 :
                    //Opción: Editar dato.
                    try{
                        imprimirDecorado(comisiones);
                        ejecutarEditarDato(comisiones);
                        actualizarCalculos(comisiones);
                        System.out.println("Dato editado correctamente.");
                    }
                    catch(Exception e){
                        System.out.println("Intento fallido. Asegurese de haber "
                                        + "ingresado correctamente lo solicitado.");
                    }
                    break;
    
                case 5 :
                    //Opción: Buscar cantidad.
                    try{
                        System.out.println("Ingrese el monto de la venta que desea buscar: ");
                        String cantidadStr = t.nextLine();
                        float cantidad = Float.parseFloat(cantidadStr);
                        String respuesta = buscarCantidad(comisiones,cantidad);
                        System.out.println(respuesta);
                    }
                    catch (NumberFormatException e){
                        System.out.println("Debe ingresar un dato de coma flotante.");
                    }
                    break;

                case 6 :
                    //Opción: Imprimir tabla.
                    imprimirDecorado(comisiones);
                    break;
                    
                case 7 :
                    System.out.println("Cerrando programa.");
                    break;
                    
                default :
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }     
        }
    }
}