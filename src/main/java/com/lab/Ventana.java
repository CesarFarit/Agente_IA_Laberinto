package com.lab;

import javax.swing.JFrame;

public class Ventana extends JFrame {

    Logica panel = new Logica();

    public Ventana() {
        this.setTitle("Laberinto");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        add(panel);
    }

}
