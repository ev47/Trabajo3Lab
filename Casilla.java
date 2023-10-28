/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import javax.swing.*;
import java.awt.Color;

public class Casilla {
    private int fila;
    private int columna;
    JLabel label;
    
    private boolean puedecambiar=true;
    
    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setOpaque(true);
    }
    
    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public JLabel getLabel() {
        return label;
    }
    
    public int getnumeroActual() {
        if (label.getText().equals("")) return -1;        
        return Integer.parseInt(label.getText());
    }
    
    public void setnumeroActual(int num) {
        if (num == 0) {
            label.setText("");
            return;
        }        
        label.setText("" + num);
    }  
    public boolean puedeCambiar() {
        return puedecambiar;
    }
    
    public void setPuedeCambiar(boolean puedecambiar) {
        this.puedecambiar = puedecambiar;
    }
    
}
