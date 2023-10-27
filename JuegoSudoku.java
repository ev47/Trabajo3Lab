/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class JuegoSudoku extends JFrame {
    final int ROWS = 9;
    final int COLUMNS = 9;
    int numerosColocados = 0;

    Casilla tablero[][];
    JPanel panelTablero;

    ArrayList<Casilla> cuad1;
    ArrayList<Casilla> cuad2;
    ArrayList<Casilla> cuad3;
    ArrayList<Casilla> cuad4;
    ArrayList<Casilla> cuad5;
    ArrayList<Casilla> cuad6;
    ArrayList<Casilla> cuad7;
    ArrayList<Casilla> cuad8;
    ArrayList<Casilla> cuad9;

    public JuegoSudoku() {
        // Instanciar arreglos
        // <editor-fold>
        cuad1 = new ArrayList<Casilla>();
        cuad2 = new ArrayList<Casilla>();
        cuad3 = new ArrayList<Casilla>();
        cuad4 = new ArrayList<Casilla>();
        cuad5 = new ArrayList<Casilla>();
        cuad6 = new ArrayList<Casilla>();
        cuad7 = new ArrayList<Casilla>();
        cuad8 = new ArrayList<Casilla>();
        cuad9 = new ArrayList<Casilla>();
        

        // </editor-fold>
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelTablero = new JPanel();
        add(panelTablero);
        panelTablero.setLayout(new GridLayout(ROWS, COLUMNS));
        tablero = new Casilla[ROWS][COLUMNS];
        llenarTablero();
        generarNumerosAleatorios();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void guardarEnCuadrante(Casilla casilla) {
        int row = casilla.getRow();
        int col = casilla.getColumn();
        ArrayList<Casilla> cuadrante = getCuadranteFromPos(row, col);
        cuadrante.add(casilla);

        if (cuadrante == cuad1 || cuadrante == cuad3 || cuadrante == cuad5 || cuadrante == cuad7 || cuadrante == cuad9) {
            casilla.getLabel().setBackground(Color.blue);
        }

    }

    private boolean esValidoFila(int num, int row) {
        for (int c = 0; c < COLUMNS; c++) {
            if (tablero[row][c].getCurrentNumber() == num) {
                return false;
            }
        }

        return true;
    }

    private boolean esValidoColumna(int num, int col) {
        for (int r = 0; r < COLUMNS; r++) {
            if (tablero[r][col].getCurrentNumber() == num) {
                return false;
            }
        }

        return true;
    }

    private Casilla getCasillaFromPos(int row, int col) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                Casilla casilla = tablero[r][c];
                if (casilla.getRow() == row && casilla.getColumn() == col) {
                    return casilla;
                }
            }
        }

        return null;
    }

    private ArrayList<Casilla> getCuadranteFromPos(int row, int col) {
        if (row <= 2) {
            if (col <= 2) {
                return cuad1;
            } else if (col <= 5) {
                return cuad2;
            } else {
                return cuad3;
            }

        } else if (row <= 5) {
            if (col <= 2) {
                return cuad4;

            } else if (col <= 5) {
                return cuad5;
            } else {
                return cuad6;

            }
        } else {
            if (col <= 2) {
                return cuad7;

            } else if (col <= 5) {
                return cuad8;
            } else {
                return cuad9;

            }
        }
    }

    private boolean esValidoCuadrante(int num, int row, int col) {
        ArrayList<Casilla> cuad = getCuadranteFromPos(row, col);

        for (Casilla casilla : cuad) {
            if (casilla.getCurrentNumber() == num) {
                return false;
            }
        }
        return true;
    }

    private void llenarTablero() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Casilla casillaClickeada = null;
                for (int r = 0; r < ROWS; r++) {
                    for (int c = 0; c < COLUMNS; c++) {
                        if (tablero[r][c].getLabel() == e.getSource()) {
                            casillaClickeada = tablero[r][c];
                            break;
                        }
                    }
                }
                
                if (casillaClickeada.canChange()) {
                    administrarClick(casillaClickeada);
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede cambiar el valor de las casillas prederminadas.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }    
            }
        };

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                tablero[r][c] = new Casilla(r, c);
                tablero[r][c].getLabel().addMouseListener(mouseAdapter);
                panelTablero.add(tablero[r][c].getLabel());
                guardarEnCuadrante(tablero[r][c]);
            }
        }

    }

    private void administrarClick(Casilla casillaClickeada) {
        int n;
        do {
            
            try{
                n = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero que desea anexar en esta casilla [Ingrese 0 para vaciar los contenidos]"));
                
                if (n <= 0 || n >= 10) {
                    JOptionPane.showMessageDialog(null, "Error. Ingrese UNICAMENTE un numero entre 1 y 9");

                }
                
                if (n == 0) {
                    casillaClickeada.setCurrentNumber(n);
                    return;
                }
                
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error. Ingrese UNICAMENTE un numero entre 1 y 9");
                n = -1;
            }
        } while (n <= 0 || n>=10);
        
        if (esNumeroValido(n, casillaClickeada.getColumn(), casillaClickeada.getRow())) {
            casillaClickeada.setCurrentNumber(n);
            numerosColocados++;
            verificarWin();
        } else {
            JOptionPane.showMessageDialog(this, "ERROR. El numero esta repetido.", "ERROR", JOptionPane.ERROR_MESSAGE);
        };
        
        
    }

    private boolean esNumeroValido(int num, int col, int row) {
        return (esValidoCuadrante(num, row, col)
                && esValidoFila(num, row)
                && esValidoColumna(num, col));

    }

    private void generarNumerosAleatorios() {
        Random random = new Random();
        int numerosPendientes = 20;
        boolean esValido;
        
        // iterar cada columna y generar una cantidad aleatoria en esa columna
        for (int c = 0; c < COLUMNS; c++) {
            System.out.println("Numeros pendientes por Generar: " + numerosPendientes);
            int cantidadNumerosEnColumna = random.nextInt(2, 5);
            
            if (numerosPendientes - cantidadNumerosEnColumna < 0) {
                cantidadNumerosEnColumna = numerosPendientes;
            }
            
            System.out.println("Cantidad de numeros a generar en columna " + c + ": " + cantidadNumerosEnColumna);
            System.out.println("Restante: " + (numerosPendientes - cantidadNumerosEnColumna));
            
            // Iterar n veces para generar la cantidad de numeros en esa columna
            for (int i = 0; i < cantidadNumerosEnColumna; i++) {
                do {
                    System.out.println("Generando #" + i + " para la columna: " + c);
                    int filaSeleccionada = random.nextInt(0, 9);
                    int numeroGenerado = random.nextInt(1, 10);
                    
                    esValido = (esValidoCuadrante(numeroGenerado, filaSeleccionada, c)
                            && esValidoFila(numeroGenerado, filaSeleccionada)
                            && esValidoColumna(numeroGenerado, c)) && tablero[filaSeleccionada][c].getCurrentNumber() == -1;

                    System.out.println((esValido) ?"VALIDO":"INVALIDO");
                    
                    if (esValido) {
                        tablero[filaSeleccionada][c].setCurrentNumber(numeroGenerado);
                        tablero[filaSeleccionada][c].setChange(false);
                        numerosPendientes--;
                        System.out.println("--------");
                        numerosColocados++; // contador para ver si el tablero esta lleno y determinar si se gano
                    }

                } while (!esValido);

            }

        }
        
        System.out.println("NUmeros que fueron colocados: " + numerosColocados);
        
        System.out.println("[generarNumerosAleatorios] Numeros que quedaron pendientes de colocar: " + numerosPendientes);
    }    
    private void verificarWin() {
        if (numerosColocados == 81) {            
            JOptionPane.showMessageDialog(null, "¡Enhorabuena, monsieur/madame! Ganasteis el desafio de resolver el Sodoku.");
            dispose();
        }      
    }
    public static void main(String[] args) {
        new JuegoSudoku();
    }
}