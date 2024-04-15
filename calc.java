package graficos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculadora {

    public static void main(String[] args) {

        MarcoCalculadora miMarco = new MarcoCalculadora();
        
        miMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        miMarco.setVisible(true);
    }
}
class MarcoCalculadora extends JFrame{
    
    public MarcoCalculadora() {

        //CREAMOS EL MARCO DE LA CALCULADORA CON LAS DIMENSIONES
        setTitle("Calculadora Científica");
        setBounds(500, 300, 450, 300);

        //INSTANCIAMOS LA CLASE Y AÑADIMOS LA LAMINA
        LaminaCalculadora miLamina = new LaminaCalculadora();
        add(miLamina);
    }
}
class LaminaCalculadora extends JPanel {
    
    private JButton pantalla;
    private boolean principio;
    private double resultado;
    private String operacionAnterior;
    private boolean radianes;

    public LaminaCalculadora() {

        setLayout(new BorderLayout());
        
        pantalla = new JButton("0");
        
        pantalla.setEnabled(false);
        add(pantalla, BorderLayout.NORTH);

        //AGREGAMOS FILAS Y COLUMNAS CON GRID
        JPanel laminaBotones = new JPanel();
        laminaBotones.setLayout(new GridLayout(5,6));
        
        ActionListener insertar = new InsertaNumero();
        
        ActionListener orden = new AccionOrden();

        //AGREGAMOS LOS BOTONES
        String[] botones = { "7", "8", "9", "/", "ln", "4", "5", "6", "*", "tan", "1", "2", "3", "-", "sin", ".", "0", "=", "+", "cos", "RAD", "DEG", "C" };

        //BUCLE FOR PARA CREAR Y ASIGNAR LA FUNCIONALIDAD A LOS BOTONES
        for (int i = 0; i < botones.length; i++) {
            JButton boton = new JButton(botones[i]);
            
            if (botones[i].equals("/") || botones[i].equals("*") || botones[i].equals("-") || botones[i].equals("+") || botones[i].equals("=") || botones[i].equals("sin") || botones[i].equals("cos") || botones[i].equals("tan") || botones[i].equals("log") || botones[i].equals("C") || botones[i].equals("Rad") || botones[i].equals("Deg")) {
                boton.addActionListener(orden);
            } else {
                boton.addActionListener(insertar);
            }
            
            laminaBotones.add(boton);
        }
        add(laminaBotones, BorderLayout.CENTER);
            
        principio = true;
        resultado = 0;
        operacionAnterior = "=";
        radianes = true;
    }

    private class InsertaNumero implements ActionListener {

        //ALMACENAMOS EL TEXTO DE ENTRADA EN LA VARIABLE ENTRADA
        public void actionPerformed(ActionEvent e) {
            String entrada = e.getActionCommand();

            if (principio) {
                pantalla.setText("");
                principio = false;
            }
            pantalla.setText(pantalla.getText() + entrada);
        }
    }

    private class AccionOrden implements ActionListener {
        
        public void actionPerformed(ActionEvent e) {
            
            String operacion = e.getActionCommand();

            //AGREGAMOS FUNCIONALIDAD A BOTONES ESPECIALES
            if (operacion.equals("C")) {
                resultado = 0;
                pantalla.setText("0");
                principio = true;
                operacionAnterior = "=";
            } else if (operacion.equals("Rad")) {
                radianes = true;
            } else if (operacion.equals("Deg")) {
                radianes = false;
            } else {
                calcular(Double.parseDouble(pantalla.getText()));
                operacionAnterior = operacion;
                principio = true;
            }
        }

        //OPERACIONES MATEMATICAS
        public void calcular(double x) {

            if (!radianes) {
                x = Math.toRadians(x);
            }

            if (operacionAnterior.equals("+")) {
                resultado += x;
            } else if (operacionAnterior.equals("-")) {
                resultado -= x;
            } else if (operacionAnterior.equals("*")) {
                resultado *= x;
            } else if (operacionAnterior.equals("/")) {
                resultado /= x;
            } else if (operacionAnterior.equals("=")) {
                resultado = x;
            } else if (operacionAnterior.equals("sin")) {
                resultado = Math.sin(x);
            } else if (operacionAnterior.equals("cos")) {
                resultado = Math.cos(x);
            } else if (operacionAnterior.equals("tan")) {
                resultado = Math.tan(x);
            } else if (operacionAnterior.equals("log")) {
                resultado = Math.log(x);
            }

            pantalla.setText("" + resultado);
        }
    }
}
