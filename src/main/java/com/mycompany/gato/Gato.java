/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gato;

/**
 *
 * @author ERIBERTO
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gato extends JFrame implements ActionListener {

    private JButton[][] botones = new JButton[3][3];
    private boolean turnoX = true;
    private ModernPanel panel;

    public Gato() {
        setTitle("GATO E.C.");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panel = new ModernPanel();
        panel.setLayout(new GridLayout(3,3));
        panel.setOpaque(false);

        Font fuente = new Font("SansSerif", Font.BOLD, 100);

        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                botones[fila][col] = new JButton("");
                botones[fila][col].setFont(fuente);
                botones[fila][col].setFocusPainted(false);
                botones[fila][col].setBorderPainted(false);
                botones[fila][col].setContentAreaFilled(false);
                botones[fila][col].setForeground(Color.WHITE);
                botones[fila][col].addActionListener(this);
                panel.add(botones[fila][col]);
            }
        }

        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setFont(new Font("SansSerif", Font.BOLD, 18));
        reiniciar.setBackground(new Color(30,30,30));
        reiniciar.setForeground(Color.WHITE);
        reiniciar.setFocusPainted(false);
        reiniciar.addActionListener(e -> reiniciarJuego());

        add(panel, BorderLayout.CENTER);
        add(reiniciar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // PANEL MODERNO CON FONDO DEGRADADO Y LÍNEAS
    class ModernPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g);

            // Fondo degradado
            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(20,20,40),
                    getWidth(), getHeight(), new Color(60,0,90)
            );
            g2.setPaint(gp);
            g2.fillRect(0,0,getWidth(),getHeight());

            g2.setStroke(new BasicStroke(6));
            g2.setColor(Color.WHITE);

            int w = getWidth();
            int h = getHeight();

            g2.drawLine(w/3, 0, w/3, h);
            g2.drawLine(2*w/3, 0, 2*w/3, h);
            g2.drawLine(0, h/3, w, h/3);
            g2.drawLine(0, 2*h/3, w, 2*h/3);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();

        if (!boton.getText().equals("")) return;

        if (turnoX) {
            boton.setForeground(new Color(0,255,200));
            boton.setText("X");
        } else {
            boton.setForeground(new Color(255,80,80));
            boton.setText("O");
        }

        turnoX = !turnoX;

        verificarGanador();
    }

    private void verificarGanador() {
        String ganador = "";

        for (int i = 0; i < 3; i++) {
            if (!botones[i][0].getText().equals("") &&
                botones[i][0].getText().equals(botones[i][1].getText()) &&
                botones[i][1].getText().equals(botones[i][2].getText())) {
                ganador = botones[i][0].getText();
                resaltarFila(i);
            }

            if (!botones[0][i].getText().equals("") &&
                botones[0][i].getText().equals(botones[1][i].getText()) &&
                botones[1][i].getText().equals(botones[2][i].getText())) {
                ganador = botones[0][i].getText();
                resaltarColumna(i);
            }
        }

        if (!botones[0][0].getText().equals("") &&
            botones[0][0].getText().equals(botones[1][1].getText()) &&
            botones[1][1].getText().equals(botones[2][2].getText())) {
            ganador = botones[0][0].getText();
            resaltarDiagonal1();
        }

        if (!botones[0][2].getText().equals("") &&
            botones[0][2].getText().equals(botones[1][1].getText()) &&
            botones[1][1].getText().equals(botones[2][0].getText())) {
            ganador = botones[0][2].getText();
            resaltarDiagonal2();
        }

        if (!ganador.equals("")) {
            mostrarGanador(ganador);
        }
    }

    private void resaltarFila(int fila) {
        for (int col = 0; col < 3; col++)
            botones[fila][col].setForeground(Color.YELLOW);
    }

    private void resaltarColumna(int col) {
        for (int fila = 0; fila < 3; fila++)
            botones[fila][col].setForeground(Color.YELLOW);
    }

    private void resaltarDiagonal1() {
        botones[0][0].setForeground(Color.YELLOW);
        botones[1][1].setForeground(Color.YELLOW);
        botones[2][2].setForeground(Color.YELLOW);
    }

    private void resaltarDiagonal2() {
        botones[0][2].setForeground(Color.YELLOW);
        botones[1][1].setForeground(Color.YELLOW);
        botones[2][0].setForeground(Color.YELLOW);
    }

    private void mostrarGanador(String ganador) {
        JDialog dialog = new JDialog(this, "GANADOR", true);
        dialog.setSize(300,200);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(30,30,30));
        dialog.setLayout(new BorderLayout());

        JLabel mensaje = new JLabel(" GANA " + ganador + " ", SwingConstants.CENTER);
        mensaje.setFont(new Font("SansSerif", Font.BOLD, 30));
        mensaje.setForeground(Color.GREEN);

        dialog.add(mensaje, BorderLayout.CENTER);

        Timer timer = new Timer(2000, e -> {
            dialog.dispose();
            reiniciarJuego();
        });

        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
    }

    private void reiniciarJuego() {
        for (int fila = 0; fila < 3; fila++)
            for (int col = 0; col < 3; col++)
                botones[fila][col].setText("");

        turnoX = true;
    }

    public static void main(String[] args) {
        new Gato();
    }
}