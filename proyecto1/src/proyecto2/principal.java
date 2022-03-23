package proyecto2;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.entity.StandardEntityCollection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class principal {

    public static DefaultTableModel mod2;
    JTable tabla2;
    public int arreglo[];
    public JFrame pantalla;
    public int[] dataset;
    public JLabel file;
    public JLabel title;
    public JTextField json;
    public boolean iniciars;
    public int second;
    public int tpasos;
    public clase2 grafica;
    public JButton iniciar;
    public JButton generar;
    public static JScrollPane scroll;
    public Object[] newr;
    public DefaultTableModel mod;
    JTable tabla;

    public principal() {
        newr = new Object[1000];
        mod = new DefaultTableModel(); // defino el modelo de mi tabla
        mod.addColumn("datos");//agrego los titulos de mi tabla 
        tabla = new JTable(mod);
        scroll = new JScrollPane(tabla);

        iniciars = true;
        second = 0;
        tpasos = 0;
        pantalla = new JFrame("PRACTICA 2");
        pantalla.setSize(990, 650);
        pantalla.setBackground(Color.white);
        pantalla.setLayout(null);
        pantalla.setLocationRelativeTo(null);//centre la ventana
        pantalla.setDefaultCloseOperation(EXIT_ON_CLOSE);
        pantalla.addWindowListener(new WindowAdapter() {
            public void WindowClosing(WindowEvent WindowEnvent) {
                System.exit(0);
            }
        });

        file = new JLabel("", JLabel.LEFT);
        file.setFont(new Font("Ubuntu", Font.BOLD, 20));
        file.setBounds(10, 20, 760, 30);
        file.setForeground(Color.black);
        file.setBackground(Color.LIGHT_GRAY);
        file.setVisible(true);

        JLabel titulo1 = new JLabel("TITULO: ", JLabel.LEFT);
        titulo1.setFont(new Font("Ubuntu", Font.BOLD, 20));

        titulo1.setForeground(Color.BLACK);
        titulo1.setBounds(10, 60, 760, 30);
        titulo1.setVisible(true);

        title = new JLabel("", JLabel.LEFT);
        title.setFont(new Font("Ubuntu", Font.BOLD, 20));
        title.setForeground(Color.black);
        title.setBounds(100, 60, 250, 30);
        title.setBackground(Color.LIGHT_GRAY);
        title.setVisible(true);

        grafica = new clase2(dataset, title.getText());

        JButton cargar = new JButton("Examinar");// creo mi boton 
        cargar.setBounds(800, 20, 140, 30);//le doy posicion a mi boton 
        // cargar.setBackground(Color.white);
        cargar.setFont(new Font("Ubuntu", Font.BOLD, 15));
        cargar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cargar.setVisible(true);
        cargar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    String aux = "";
                    String texto = "";
                    JFileChooser j = new JFileChooser();
                    j.showSaveDialog(null);
                    File abre = j.getSelectedFile();
                    if (abre != null) {
                        file.setText(j.getSelectedFile().getAbsolutePath().toUpperCase());
                        FileReader archivos = new FileReader(abre);
                        BufferedReader lee = new BufferedReader(archivos);
                        while ((aux = lee.readLine()) != null) {
                            texto += aux + "\n";}
                        lee.close();
                        Object jsonObyeto = JSONValue.parse(texto);
                        JSONObject obyeto = (JSONObject) jsonObyeto;

                        Object jsontitleobyeto = obyeto.get("title");
                        title.setText(jsontitleobyeto.toString().toUpperCase());
                        Object jsonarrayobyeto = obyeto.get("dataset");
                        JSONArray arrayobyeto = (JSONArray) jsonarrayobyeto;

                        int tam = 0;
                        for (Object obyeto_inarray : arrayobyeto) {
                            tam++;
                        }
                        dataset = new int[tam];
                        int index = 0;
                        for (Object obyeto_inarray : arrayobyeto) {
                            dataset[index] = Integer.parseInt(obyeto_inarray.toString());
                            index++;
                        }

                    }
                    pantalla.remove(grafica);
                    grafica = new clase2(dataset, title.getText());
                    pantalla.add(grafica);
                    iniciar.setVisible(true);

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });

        iniciar = new JButton("Ordenar");
        iniciar.setBounds(800, 60, 140, 30);
        // iniciar.setBackground(Color.white);
        iniciar.setFont(new Font("Ubuntu", Font.BOLD, 15));
        iniciar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        iniciar.setVisible(false);
        iniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    new Thread(grafica).start();
                    iniciar.setVisible(false);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });

        pantalla.add(file);
        pantalla.add(grafica);
        pantalla.add(title);
        pantalla.add(titulo1);
        pantalla.add(cargar);
        pantalla.add(iniciar);
        pantalla.setVisible(true);

    }

    public void generarpng() {

    }

}
