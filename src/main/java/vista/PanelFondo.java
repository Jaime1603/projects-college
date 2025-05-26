/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {

    Color colorfondo = Color.GREEN;
    int tamMax, tam, cantidad, res;

    public PanelFondo(int tamMax, int cantidad) {
        this.tamMax = tamMax;
        this.cantidad = cantidad;
        this.tam = tamMax / cantidad;
        this.res = tamMax % cantidad;
    }
    @Override
    public void paint(Graphics pintor) {
        super.paint(pintor);
        pintor.setColor(colorfondo);
        pintor.fillRect(0, 0, getWidth(), getHeight());
    }

}
