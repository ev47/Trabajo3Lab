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
      final int FILAS = 9;
    final int COLUMNAS=9;
    int numerosColocados=0;

    Casilla tablero[][];
    JPanel panelTablero;

    ArrayList<Casilla> cuadrante1;
    ArrayList<Casilla> cuadrante2;
    ArrayList<Casilla> cuadrante3;
    ArrayList<Casilla> cuadrante4;
    ArrayList<Casilla> cuadrante5;
    ArrayList<Casilla> cuadrante6;
    ArrayList<Casilla> cuadrante7;
    ArrayList<Casilla> cuadrante8;
    ArrayList<Casilla> cuadrante9;

    public JuegoSudoku() {
        // Instanciar arreglos
        // <editor-fold>
        cuadrante1 = new ArrayList<Casilla>();
        cuadrante2 = new ArrayList<Casilla>();
        cuadrante3 = new ArrayList<Casilla>();
        cuadrante4 = new ArrayList<Casilla>();
        cuadrante5 = new ArrayList<Casilla>();
        cuadrante6 = new ArrayList<Casilla>();
        cuadrante7 = new ArrayList<Casilla>();
        cuadrante8 = new ArrayList<Casilla>();
        cuadrante9 = new ArrayList<Casilla>();
        

        // </editor-fold>
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelTablero = new JPanel();
        add(panelTablero);
        panelTablero.setLayout(new GridLayout(FILAS, COLUMNAS));
        tablero = new Casilla[FILAS][COLUMNAS];
        llenarTablero();
        generarNumerosAleatorios();
        setVisible(true);
        setLocationRelativeTo(null);
    }
    private void guardarEnCuadrante(Casilla casilla) {
        int fil = casilla.getFila();
        int col = casilla.getColumna();
        ArrayList<Casilla> cuadrante = getCuadranteFromPos(fil, col);
        cuadrante.add(casilla);
        if (cuadrante == cuadrante1 || cuadrante == cuadrante3 || cuadrante == cuadrante5 || cuadrante == cuadrante7 || cuadrante == cuadrante9) {
            casilla.getLabel().setBackground(Color.blue);
        }

    }

    private boolean esValidoFila(int num, int row) {
        for (int c = 0; c < COLUMNAS; c++) {
            if (tablero[row][c].getnumeroActual()== num) {
                return false;
            }
        }

        return true;
    }

    private boolean esValidoColumna(int num, int col) {
        for (int r = 0; r < COLUMNAS; r++) {
            if (tablero[r][col].getnumeroActual() == num) {
                return false;
            }
        }

        return true;
    }

    private Casilla getCasillaPosicion(int row, int col) {
        for (int r = 0; r < FILAS; r++) {
            for (int c = 0; c < COLUMNAS; c++) {
                Casilla casilla = tablero[r][c];
                if (casilla.getFila() == row && casilla.getColumna() == col) {
                    return casilla;
                }
            }
        }

        return null;
    }

    private ArrayList<Casilla> getCuadranteFromPos(int fila, int columna) {
        if (fila <= 2) {
            if (columna <= 2) {
                return cuadrante1;
            } else if (columna <= 5) {
                return cuadrante2;
            } else {
                return cuadrante3;
            }

        } else if (fila <= 5) {
            if (columna <= 2) {
                return cuadrante4;

            } else if (columna <= 5) {
                return cuadrante5;
            } else {
                return cuadrante6;

            }
        } else {
            if (columna <= 2) {
                return cuadrante7;

            } else if (columna <= 5) {
                return cuadrante8;
            } else {
                return cuadrante9;

            }
        }
    }

    private boolean esValidoCuadrante(int num, int row, int col) {
        ArrayList<Casilla> cuadran = getCuadranteFromPos(row, col);

        for (Casilla casilla : cuadran) {
            if (casilla.getnumeroActual()== num) {
                return false;
            }
        }
        return true;
    }

    private void llenarTablero() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Casilla casillapresionada = null;
                for (int r = 0; r < FILAS; r++) {
                    for (int c = 0; c < COLUMNAS; c++) {
                        if (tablero[r][c].getLabel() == e.getSource()) {
                            casillapresionada = tablero[r][c];
                            break;
                        }
                    }
                }
          
                if (casillapresionada.puedeCambiar()) {
                    ControlarClick(casillapresionada);
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede cambiar el valor de las casillas prederminadas.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }    
            }
        };

        for (int r=0;r < FILAS;r++) {
            for (int c = 0; c < COLUMNAS; c++) {
                tablero[r][c] = new Casilla(r, c);
                tablero[r][c].getLabel().addMouseListener(mouseAdapter);
                panelTablero.add(tablero[r][c].getLabel());
                guardarEnCuadrante(tablero[r][c]);
            }
        }

    }

    private void ControlarClick(Casilla casillapresionada) {
        int n;
        do {
            
            try{
                n= Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero que desea anexar en esta casilla [Ingrese 0 para vaciar los contenidos]"));                
                if (n <= 0 || n >= 10) {
                    JOptionPane.showMessageDialog(null, "Error. Ingrese UNICAMENTE un numero entre 1 y 9");

                }
                
                if (n == 0) {
                    casillapresionada.setnumeroActual(n);
                    return;
                }                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error. Ingrese UNICAMENTE un numero entre 1 y 9");
                n = -1;
            }
        } while (n <= 0 || n>=10);
        
        if (esNumeroValido(n, casillapresionada.getColumna(), casillapresionada.getFila())) {
            casillapresionada.setnumeroActual(n);
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
        for (int j=0;j<COLUMNAS; j++) {
            System.out.println("Numeros pendientes por Generar: " + numerosPendientes);
            int cantidadNumerosEnColumna = random.nextInt(2, 5);
            
            if (numerosPendientes - cantidadNumerosEnColumna < 0) {
                cantidadNumerosEnColumna = numerosPendientes;
            }
            
            System.out.println("Cantidad de numeros a generar en columna " + j + ": " + cantidadNumerosEnColumna);
            System.out.println("Restante: " + (numerosPendientes - cantidadNumerosEnColumna));
            
            // Iterar n veces para generar la cantidad de numeros en esa columna
            for (int i = 0; i < cantidadNumerosEnColumna; i++) {
                do {
                    System.out.println("Generando #" + i + " para la columna: " + j);
                    int filaSeleccionada = random.nextInt(0, 9);
                    int numeroGenerado = random.nextInt(1, 10);
                    
                    esValido = (esValidoCuadrante(numeroGenerado, filaSeleccionada, j)
                            && esValidoFila(numeroGenerado, filaSeleccionada)
                            && esValidoColumna(numeroGenerado, j)) && tablero[filaSeleccionada][j].getnumeroActual()== -1;

                    System.out.println((esValido) ?"VALIDO":"INVALIDO");
                    
                    if (esValido) {
                        tablero[filaSeleccionada][j].setnumeroActual(numeroGenerado);
                        tablero[filaSeleccionada][j].setPuedeCambiar(false);
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
    private void verificarGanador() {
        if (numerosColocados == 81) {            
            JOptionPane.showMessageDialog(null, "¡Enhorabuena, Ganastes el desafio de resolver el Sodoku.");
            dispose();
        }      
    }  
    public static void main(String[] args) {
        new JuegoSudoku();
    }
}
