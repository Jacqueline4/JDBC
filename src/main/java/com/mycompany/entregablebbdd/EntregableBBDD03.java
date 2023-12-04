/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.entregablebbdd;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Desarrolla una aplicación Java que utilice JDBC para interactuar con una base
 * de datos MariaDB que contiene dos tablas relacionadas en una relación de
 * muchos a muchos. Las tablas se denominan "Estudiantes" y "Cursos". Cada tabla
 * debe contener los siguientes atributos:
 *
 * La aplicación debe ser capaz de realizar las siguientes operaciones:
 *
 * Establecer una conexión a la base de datos MariaDB. En caso de que no exista
 * alguna de las tablas, la cree. Insertar nuevos estudiantes y cursos en las
 * tablas respectivas. Registrar inscripciones de estudiantes en cursos.
 * Consultar la lista de estudiantes inscritos en un curso específico. Consultar
 * los cursos en los que un estudiante particular está inscrito. Actualizar
 * información de estudiantes o cursos. Eliminar registros de estudiantes,
 * cursos o inscripciones. Asegúrate de manejar excepciones adecuadamente, crear
 * las relaciones entre las tablas y aislar cada una de las consultas en un
 * método.
 *
 * @author jacqueline
 *
 */
public class EntregableBBDD03 {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String opcion = "";
        Estudiantes a= new Estudiantes();
        Cursos c= new Cursos();
        Tablas t = new Tablas();
        Inscripciones i = new Inscripciones();
        ArrayList<String> alC;
        ArrayList<String> alE;
        ArrayList<Estudiantes> alEstudiantes;
        ArrayList<Cursos> alCursos;
        int opcionN = 0;
        boolean entradaValida = false;
        String nombre;
        String direccion;
        String email;
        int edad = 0;
        String nombreCurso;
        String descripcion;
        int creditos = 0;
        int idEstudiante = 0;
        int idCurso = 0;
        LocalDate l = LocalDate.now();
        int idI = 0;

        try {
            t.crearTablas();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        do {
            opcion = menu(sc);
            try {
                opcionN = Integer.parseInt(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Valor introducido no válido, por favor introduzca un número: ");
                opcion = menu(sc);
            }

            switch (opcionN) {

                case 1: {

                    System.out.println("Introduce nombre: ");
                    nombre = sc.nextLine();
                    while (!entradaValida) {
                        System.out.println("Introduce edad: ");
                        try {
                            edad = Integer.parseInt(sc.nextLine());
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número: ");

                        }
                    }
                    entradaValida = false;

                    System.out.println("Introduce dirección: ");
                    direccion = sc.nextLine();
                    System.out.println("Introduce email: ");
                    email = sc.nextLine();
                    a = new Estudiantes();
                   
                    a.setNombre(nombre);
                    a.setEdad(edad);
                    a.setDireccion(direccion);
                    a.setEmail(email);
                    
                    try {

                        System.out.println("Filas insertadas: " + a.insertEstudiante(a));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                case 2: {

                    System.out.println("Introduce nombre del curso: ");
                    nombreCurso = sc.nextLine();
                    System.out.println("Introduce descripcion: ");
                    descripcion = sc.nextLine();

                    while (!entradaValida) {
                        System.out.println("Introduce creditos: ");
                        try {
                            creditos = Integer.parseInt(sc.nextLine());
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número.");
                        }
                    }
                    entradaValida = false;
                    c = new Cursos();
                    
                    c.setNombreCurso(nombreCurso);
                    c.setDescripcion(descripcion);
                    c.setCreditos(creditos);

                    try {
                        System.out.println("Filas insertadas: " + c.insertCurso(c));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }

                case 3: {

                    try {
                        do {
                            System.out.println("Introduce el nombre del estudiante: ");
                            nombre = sc.nextLine();
                            alEstudiantes = a.consultarEstudiantes(nombre);
                            if (alEstudiantes.size() < 1) {
                                System.out.println("El estudiante introducido no esta insertado");
                                a.mostrarEstudiantes();
                            }

                        } while (alEstudiantes.size() < 1);

                    } catch (SQLException ex) {
                        throw ex;
                    }

                    try {
                        do {
                            System.out.println("Introduce el nombre del curso: ");
                            nombreCurso = sc.nextLine();
                            alCursos = c.consultarCursos(nombreCurso);
                            if (alCursos.size() < 1) {
                                System.out.println("El curso introducido no esta insertado");
                                c.mostrarCursos();
                            }
                        } while (alCursos.size() < 1);

                    } catch (SQLException ex) {
                        throw ex;
                    }

                    while (!entradaValida) {
                        System.out.println("Introduce id del estudiante: ");
                        try {
                            idEstudiante = Integer.parseInt(sc.nextLine());
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número: ");

                        }
                    }
                    entradaValida = false;
                    while (!entradaValida) {
                        System.out.println("Introduce id del curso: ");
                        try {
                            idCurso = Integer.parseInt(sc.nextLine());
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número: ");

                        }
                    }
                    entradaValida = false;
                    i = new Inscripciones();
                    i.setId_estudiante(idEstudiante);
                    i.setId_curso(idCurso);
                    i.setFecha(l);
                    try {
                        System.out.println("Filas insertadas: " + i.registrarInscripcion(idEstudiante, idCurso, l));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                case 4: {

                    System.out.println("Introduce nombre del curso: ");
                    nombreCurso = sc.nextLine();

                    try {
                        alE = i.getEstudiantes(nombreCurso);
                        System.out.println("En el curso estan inscritos los siguientes estudiantes: ");
                        for (String es : alE) {
                            System.out.println(es);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                case 5: {
                    String nombreE;
                    System.out.println("Introduce nombre del estudiante: ");
                    nombreE = sc.nextLine();
//                    i = new Inscripciones();
                    try {
                        alC = i.getCursos(nombreE);
                        System.out.println("Esta inscrito en los siguientes cursos: ");
                        for (String cu : alC) {
                            System.out.println(cu);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                case 6: {

                    System.out.println("Introduce el nombre del estudiante: ");
                    nombre = sc.nextLine();
                    try {
                        alEstudiantes = a.consultarEstudiantes(nombre);
                        for (Estudiantes estudiante : alEstudiantes) {
                            System.out.println(estudiante);
                        }

                    } catch (SQLException ex) {
                        throw ex;
                    }

                    System.out.println("Introduce nombre actualizado: ");
                    nombre = sc.nextLine();
                    while (!entradaValida) {
                        System.out.println("Introduce edad actualizada: ");
                        try {
                            edad = Integer.parseInt(sc.nextLine());
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número: ");

                        }
                    }
                    entradaValida = false;
                    System.out.println("Introduce dirección actualizada: ");
                    direccion = sc.nextLine();
                    System.out.println("Introduce email actualizado: ");
                    email = sc.nextLine();

                    while (!entradaValida) {
                        System.out.println("Introduce id: ");
                        try {
                            idEstudiante = Integer.parseInt(sc.nextLine());
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número: ");

                        }
                    }
                    entradaValida = false;
                    a = new Estudiantes();
                    a.setNombre(nombre);
                    a.setEdad(edad);
                    a.setDireccion(direccion);
                    a.setEmail(email);
                    try {
                        System.out.println("Filas actualizadas: " + a.updateEstudiante(a, idEstudiante));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                case 7: {

                    System.out.println("Introduce el nombre del curso: ");
                    nombreCurso = sc.nextLine();
                    try {
                        alCursos = c.consultarCursos(nombreCurso);
                        for (Cursos curso : alCursos) {
                            System.out.println(curso);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    System.out.println("Introduce nombre del curso actualizado: ");
                    nombreCurso = sc.nextLine();
                    System.out.println("Introduce descripcion actualizada: ");
                    descripcion = sc.nextLine();
                    while (!entradaValida) {
                        System.out.println("Introduce créditos actualizados: ");
                        try {
                            creditos = Integer.parseInt(sc.nextLine());
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número: ");

                        }
                    }
                    entradaValida = false;
                    while (!entradaValida) {
                        System.out.println("Introduce id: ");
                        try {
                            idCurso = Integer.parseInt(sc.nextLine());
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número: ");

                        }
                    }
                    entradaValida = false;
                    c = new Cursos();
                    c.setNombreCurso(nombreCurso);
                    c.setDescripcion(descripcion);
                    c.setCreditos(creditos);
                    try {
                        System.out.println("Filas actualizadas: " + c.updateCurso(c, idCurso));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }

                case 8: {

                    System.out.println("Introduce el nombre del estudiante: ");
                    nombre = sc.nextLine();
                    try {
                        alEstudiantes = a.consultarEstudiantes(nombre);
                        for (Estudiantes estudiante : alEstudiantes) {
                            System.out.println(estudiante);
                        }
                    } catch (SQLException ex) {
                        throw ex;
                    }
                    while (!entradaValida) {
                        System.out.println("Introduce id del estudiante: ");
                        try {
                            idEstudiante = Integer.parseInt(sc.nextLine());
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número: ");
                        }
                    }
                    entradaValida = false;
                    try {
                        System.out.println("Filas borradas: " + a.deleteEstudiante(idEstudiante));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                case 9: {

                    System.out.println("Introduce el nombre del curso: ");
                    nombre = sc.nextLine();
                    try {
                        alCursos = c.consultarCursos(nombre);
                        for (Cursos curso : alCursos) {
                            System.out.println(curso);
                        }
                    } catch (SQLException ex) {
                        throw ex;
                    }
                    while (!entradaValida) {
                        System.out.println("Introduce id del curso: ");
                        try {
                            idCurso = Integer.parseInt(sc.nextLine());
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número: ");

                        }
                    }
                    entradaValida = false;
                    try {

                        System.out.println("Filas borradas: " + c.deleteCurso(idCurso));

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                case 10: {

                    i.mostrarInscripciones();

                    while (!entradaValida) {
                        System.out.println("Introduce id de la inscripcion: ");
                        String input = sc.nextLine();

                        try {
                            idI = Integer.parseInt(input);
                            entradaValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor introducido no válido, por favor introduzca un número.");
                        }
                    }
                    entradaValida = false;
                    try {
                        System.out.println("Filas borradas: " + i.deleteInscripcion(idI));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                case 11: {

                    try {
                        System.out.println("\t\t Tabla Estudiantes");
                        a.mostrarEstudiantes();
                        System.out.println("\n \t\t Tabla Cursos");
                        c.mostrarCursos();
                        System.out.println("\n \t\t Tabla Inscripciones");
                        i.mostrarInscripciones();
                        System.out.println("\n");

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                case 12: {
                    System.out.print("Saliendo");
                    break;
                }
                default: {
                    System.out.print("Opcion incorrecta");
                }
            }
        } while (opcionN != 12);

    }

    public static String menu(Scanner sc) {
        String opcion;
        do {
            System.out.println("-------- MENU --------");
            System.out.println("1 - Insertar Estudiante");
            System.out.println("2 - Insertar Curso");
            System.out.println("3 - Registrar inscripciones");
            System.out.println("4 - Consultar lista de estudiantes por curso");
            System.out.println("5 - Consultar cursos de un estudiante");
            System.out.println("6 - Actualizar estudiante");
            System.out.println("7 - Actualizar curso");
            System.out.println("8 - Borrar estudiante");
            System.out.println("9 - Borrar curso");
            System.out.println("10 - Borrar inscripcion");
            System.out.println("11 - Mostrar tablas");
            System.out.println("12 - Salir");

            opcion = sc.nextLine();

        } while (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3") && !opcion.equals("4")
                && !opcion.equals("5") && !opcion.equals("6") && !opcion.equals("7") && !opcion.equals("8")
                && !opcion.equals("9") && !opcion.equals("10") && !opcion.equals("11")
                && !opcion.equals("12"));

        return opcion;
    }
}
