/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import java.awt.event.KeyEvent;
import javax.swing.JLabel;


public class Pantalla extends javax.swing.JFrame {

    PanelSnake panel;
    JLabel labelPuntaje;
    
    public Pantalla() { 
        initComponents();
        this.setSize(800, 850);
        this.setLocationRelativeTo(null);
        
        labelPuntaje = new JLabel("Puntaje: 0");
        labelPuntaje.setBounds(650, 20, 150, 30);
        labelPuntaje.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        add(labelPuntaje);
        
        panel = new PanelSnake(770, 20, labelPuntaje);
        this.add(panel);
        panel.setBounds(10, 10, 770, 770);
        panel.setOpaque(false);

        PanelFondo fondo = new PanelFondo(770, 20);
        this.add(fondo);
        fondo.setBounds(10, 10, 770, 770);
        
        this.requestFocus(true);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 825, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                panel.cambiarDireccion("iz");
                break;
            case KeyEvent.VK_RIGHT:
                panel.cambiarDireccion("de");
                break;
            case KeyEvent.VK_UP:
                panel.cambiarDireccion("ar");
                break;
            case KeyEvent.VK_DOWN:
                panel.cambiarDireccion("ab");
                break;
            default:
                break;
        }
    }//GEN-LAST:event_formKeyPressed

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla().setVisible(true);
                try {
                    new java.io.FileWriter("ranking.txt", false).close(); // Limpia el archivo
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
