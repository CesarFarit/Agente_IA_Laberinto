package com.lab;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.PriorityQueue;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Logica extends JPanel {

    // Laberinto: 0 = libre, 1 = pared, 2 = inicio, 3 = fin, 4 = ratón en camino
    // MAPEO DEL LABERINTO
    private final int[][] relleno = {
            { 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0 },
            { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0 },
            { 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0 },
            { 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0 },
            { 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0 },
            { 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0 },
            { 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0 },
            { 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0 },
            { 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0 },
            { 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1 },
            { 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3 }
    };
    private final int filas = relleno.length;
    private final int columnas = relleno[0].length;

    private java.util.List<Nodo> camino = new ArrayList<>();
    private int pasoActual = 0;

    private BufferedImage imagenPared, imagenCamino, uriel, queso, cueva;

    public Logica() {
        this.setBackground(Color.GREEN);
        try {
            imagenPared = ImageIO.read(getClass().getResource("/img/block.png"));
            imagenCamino = ImageIO.read(getClass().getResource("/img/camino.png"));
            uriel = ImageIO.read(getClass().getResource("/img/urielFat.png"));
            queso = ImageIO.read(getClass().getResource("/img/queso1.png"));
            cueva = ImageIO.read(getClass().getResource("/img/cueva.png"));
            System.out.println("CARGO LA IMAGEN");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("NEL MI SKIBIDI");
        }
        buscarCamino();
        iniciarAnimacion();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int ancho = getWidth();
        int alto = getHeight();

        int anchoCelda = ancho / columnas;
        int altoCelda = alto / filas;

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {

                int x = columna * anchoCelda;
                int y = fila * altoCelda;

                if (fila < relleno.length && columna < relleno[0].length) {
                    switch (relleno[fila][columna]) {
                        case 0 -> g.setColor(Color.WHITE);
                        case 1 -> g.setColor(Color.BLACK);
                        case 2 -> g.setColor(Color.GREEN);
                        case 3 -> g.setColor(Color.RED);
                        case 4 -> g.setColor(Color.CYAN);
                    }
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                if (relleno[fila][columna] == 1 && imagenPared != null) {
                    g.drawImage(imagenPared, x, y, anchoCelda, altoCelda, this);

                } else if (relleno[fila][columna] == 0 && imagenCamino != null) {
                    g.drawImage(imagenCamino, x, y, anchoCelda, altoCelda, this);

                } else if (relleno[fila][columna] == 4 && uriel != null) {
                    g.drawImage(uriel, x, y, anchoCelda, altoCelda, this);
                } else if (relleno[fila][columna] == 3 && queso != null) {
                    g.drawImage(queso, x, y, anchoCelda, altoCelda, this);
                } else if (relleno[fila][columna] == 2 && cueva != null) {
                    g.drawImage(cueva, x, y, anchoCelda, altoCelda, this);
                } else {
                    g.fillRect(x, y, anchoCelda, altoCelda);
                }
                // g.setColor(Color.GRAY);
                // g.drawRect(x, y, anchoCelda, altoCelda);
            }
        }
    }

    private void buscarCamino() {
        Nodo inicio = null, fin = null;

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (relleno[f][c] == 2)
                    inicio = new Nodo(f, c, 0, null);
                if (relleno[f][c] == 3)
                    fin = new Nodo(f, c, Integer.MAX_VALUE, null);
            }
        }

        if (inicio == null || fin == null)
            return;

        boolean[][] visitado = new boolean[filas][columnas];
        PriorityQueue<Nodo> cola = new PriorityQueue<>();
        cola.add(inicio);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();

            if (visitado[actual.fila][actual.col])
                continue;
            visitado[actual.fila][actual.col] = true;

            if (relleno[actual.fila][actual.col] == 3) {
                reconstruirCamino(actual);
                return;
            }

            int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
            for (int[] d : dir) {
                int nf = actual.fila + d[0];
                int nc = actual.col + d[1];

                if (esValido(nf, nc) && !visitado[nf][nc]) {
                    cola.add(new Nodo(nf, nc, actual.distancia + 1, actual));
                }
            }
        }
    }

    private void reconstruirCamino(Nodo nodo) {
        while (nodo != null) {
            camino.add(0, nodo);
            nodo = nodo.anterior;
        }
    }

    private boolean esValido(int f, int c) {
        return f >= 0 && f < filas && c >= 0 && c < columnas && relleno[f][c] != 1;
    }

    private void iniciarAnimacion() {
        Timer timer = new Timer(200, e -> {
            if (pasoActual < camino.size()) {
                if (pasoActual > 0) {
                    Nodo anterior = camino.get(pasoActual - 1);
                    if (relleno[anterior.fila][anterior.col] == 4) {
                        relleno[anterior.fila][anterior.col] = 0;
                    }
                }

                Nodo paso = camino.get(pasoActual);
                if (relleno[paso.fila][paso.col] == 0) {
                    relleno[paso.fila][paso.col] = 4;
                }

                pasoActual++;
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(this, "LABERINTO RESUELTO :D");
            }
        });
        timer.start();
    }

    // DJIKSTRA
    private static class Nodo implements Comparable<Nodo> {
        int fila, col, distancia;
        Nodo anterior;

        public Nodo(int fila, int col, int distancia, Nodo anterior) {
            this.fila = fila;
            this.col = col;
            this.distancia = distancia;
            this.anterior = anterior;
        }

        @Override
        public int compareTo(Nodo o) {
            return Integer.compare(this.distancia, o.distancia);
        }
    }
}
