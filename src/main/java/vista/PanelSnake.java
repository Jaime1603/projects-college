/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author gilja
 */
public class PanelSnake extends JPanel {

    Color colorSnake = Color.BLUE;
    Color manzana = Color.RED;
    int tamMax, tam, cantidad, res;
    List<int[]> snake = new ArrayList<>();
    int[] comida = new int[2];
    String direccion = "de";
    String direccionProxima = "de";

    JLabel labelPuntaje;
    int puntaje = 0;
    String nombreJugador = "";

    Thread hilo;
    Caminante camino;

    public PanelSnake(int tamMax, int cantidad, JLabel labelPuntaje) {
        this.tamMax = tamMax;
        this.cantidad = cantidad;
        this.tam = tamMax / cantidad;
        this.res = tamMax % cantidad;
        this.labelPuntaje = labelPuntaje;
        this.nombreJugador = JOptionPane.showInputDialog(this, "Ingrese su nombre");
        if (this.nombreJugador == null || this.nombreJugador.trim().isEmpty()) {
            System.exit(0);
        }
        int[] a = {cantidad / 2 - 1, cantidad / 2 - 1};
        int[] b = {cantidad / 2, cantidad / 2 - 1};
        snake.add(a);
        snake.add(b);
        generarComida();

        camino = new Caminante(this);
        hilo = new Thread(camino);
        hilo.start();
    }

    @Override
    public void paint(Graphics pintor) {
        super.paint(pintor);
        pintor.setColor(colorSnake);
        for (int[] par : snake) {
            pintor.fillRect(res / 2 + par[0] * tam, res / 2 + par[1] * tam, tam - 1, tam - 1);
        }

        pintor.setColor(manzana);
        pintor.fillRect(res / 2 + comida[0] * tam, res / 2 + comida[1] * tam, tam - 1, tam - 1);

    }

    public void avanzar() {
        igualarDir();
        int[] ultimo = snake.get(snake.size() - 1);
        int agregarX = 0;
        int agregarY = 0;
        switch (direccion) {
            case "de":
                agregarX = 1;
                break;
            case "iz":
                agregarX = -1;
                break;
            case "ar":
                agregarY = -1;
                break;
            case "ab":
                agregarY = 1;
                break;

        }

        int nuevoX = ultimo[0] + agregarX;
        int nuevoY = ultimo[1] + agregarY;

        if (nuevoX < 0 || nuevoX >= cantidad || nuevoY < 0 || nuevoY >= cantidad) {
            perder();
            return;
        }

        int[] nuevo = {nuevoX, nuevoY};
        boolean existe = false;
        for (int i = 0; i < snake.size(); i++) {
            if (nuevo[0] == snake.get(i)[0] && nuevo[1] == snake.get(i)[1]) {
                existe = true;
                break;
            }
        }
        if (existe) {
            JOptionPane.showMessageDialog(this, "Has perdido ");
            perder();
        } else {
            if (nuevo[0] == comida[0] && nuevo[1] == comida[1]) {
                snake.add(nuevo);
                generarComida();
                puntaje += 50;
                labelPuntaje.setText("Puntaje: " + puntaje);
            } else {
                snake.add(nuevo);
                snake.remove(0);
            }
        }
    }

    public void perder() {
        camino.parar(); // detiene el hilo
        guardarPuntaje();

        int opcion = JOptionPane.showOptionDialog(
                this,
                "Has perdido. ¿Deseas reiniciar?",
                "Fin del juego",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Reiniciar", "Salir", "Siguiente participante"},
                "Reiniciar");

        if (opcion == JOptionPane.YES_OPTION) {
            reiniciarJuego();
        } else if (opcion == JOptionPane.NO_OPTION) {
            mostarTop();
            System.exit(0);
        } else if (opcion == JOptionPane.CANCEL_OPTION) {
            siguienteParticipante();
        }
    }

    public void reiniciarJuego() {
        snake.clear();
        int[] a = {cantidad / 2 - 1, cantidad / 2 - 1};
        int[] b = {cantidad / 2, cantidad / 2 - 1};
        snake.add(a);
        snake.add(b);
        direccion = "de";
        direccionProxima = "de";
        generarComida();

        camino = new Caminante(this);
        hilo = new Thread(camino);
        hilo.start();
        puntaje = 0;
        labelPuntaje.setText("Puntaje: 0");
    }

    public void siguienteParticipante() {
        snake.clear();
        int[] a = {cantidad / 2 - 1, cantidad / 2 - 1};
        int[] b = {cantidad / 2, cantidad / 2 - 1};
        snake.add(a);
        snake.add(b);
        direccion = "de";
        direccionProxima = "de";
        puntaje = 0;
        labelPuntaje.setText("Puntaje: 0");

        nombreJugador = JOptionPane.showInputDialog(this, "Ingrese el nombre del nuevo jugador: ");
        if (nombreJugador == null || nombreJugador.trim().isEmpty()) {
            System.exit(0);
        }
        camino = new Caminante(this);
        hilo = new Thread(camino);
        hilo.start();
    }

    public void generarComida() {
        boolean existe = false;
        int a = (int) (Math.random() * cantidad);
        int b = (int) (Math.random() * cantidad);
        for (int[] par : snake) {
            if (par[0] == a && par[1] == b) {
                existe = true;
                generarComida();
                break;
            }
        }
        if (!existe) {
            this.comida[0] = a;
            this.comida[1] = b;
        }
    }

    public void cambiarDireccion(String dir) {
        if ((this.direccion.equals("de") || this.direccion.equals("iz")) && (dir.equals("ar") || dir.equals("ab"))) {
            this.direccionProxima = dir;
        }
        if ((this.direccion.equals("ar") || this.direccion.equals("ab")) && (dir.equals("iz") || dir.equals("de"))) {
            this.direccionProxima = dir;
        }
    }

    public void igualarDir() {
        this.direccion = direccionProxima;
    }

    public void guardarPuntaje() {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("Ranking.txt", true);
            fw.write(nombreJugador + " - " + puntaje + " Puntos \n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostarTop() {
        java.util.List<String> ranking = new java.util.ArrayList<>();
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("Ranking.txt"));
            String linea;
            while ((linea = br.readLine()) != null) {
                ranking.add(linea);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        ranking.sort((a, b) -> {
            int puntosA = Integer.parseInt(a.replaceAll("[^0-9]", ""));
            int puntosB = Integer.parseInt(b.replaceAll("[^0-9]", ""));
            return Integer.compare(puntosB, puntosA);
        });

        StringBuilder top = new StringBuilder("Top 3 Puntajes \n");
        for (int i = 0; i < Math.min(3, ranking.size()); i++) {
            top.append((i + 1)).append(". ").append(ranking.get(i)).append("\n");
        }

        JOptionPane.showMessageDialog(this, top.toString(), "Ranking", JOptionPane.INFORMATION_MESSAGE);
    }
}
