package proyecto2;

import com.itextpdf.text.BaseColor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static proyecto2.principal.mod2;

public class clase2 extends JPanel implements Runnable {

    Document document;
    public int m=0;
    
    public int[] datos;
     public  int arrt[] = new int[1000];
    int arreglo[];
    public int arreglo2[];
    public int[] dinicial;
    public JFreeChart barchart;
    public JLabel title1;
    public Tiempo tiempo;
    public static int tiempoclae2;
    public int texecute;
    public int pasos;
    PdfPTable ppa;
    PdfPTable ppaa;
    public DefaultTableModel mod;
    JTable tabla;

    public DefaultTableModel mod2;
    JTable tabla2;

    public clase2(int[] _datos, String title) {
        mod2 = new DefaultTableModel();

        mod2.addColumn("Datos desordenados");//agrego los titulos de mi tabla 
        tabla2 = new JTable(mod2);
        JScrollPane scroll2 = new JScrollPane(tabla2);

        mod = new DefaultTableModel(); // defino el modelo de mi tabla

        mod.addColumn("Datos iniciales");//agrego los titulos de mi tabla 

        tabla = new JTable(mod);
        JScrollPane scroll = new JScrollPane(tabla);

        this.datos = new int[1];
        this.datos = _datos;
        if (this.datos != null) {
            this.dinicial = _datos.clone();// con esto genero una nueva instancia que ocuparan los datos declarados en el objeto 
        }
        //ordeno lo datos iniciales de carga
        setBounds(10, 120, 760, 400); //defino el tamaño y posicion del panel 
        setLayout(new BorderLayout());//defino el estilo de layout que tendra mi panel 
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tiempo = new Tiempo();
        add(tiempo, BorderLayout.PAGE_END);

        title1 = new JLabel("pasos = 0");
        title1.setFont(new Font("Ubuntu", Font.BOLD, 20));
        title1.setForeground(Color.black);
        title1.setPreferredSize(new Dimension(760, 30));
        add(title1, BorderLayout.PAGE_START);//le indica visualizarse al inicio 

        title1.setVisible(true);
        barchart = ChartFactory.createBarChart(
                title, "", "Valor", new DefaultCategoryDataset()//definimos desde cero el data set
                ,
                 PlotOrientation.VERTICAL, true, true, true);//
        ChartPanel chartPanel = new ChartPanel(barchart);
        chartPanel.setPreferredSize(new Dimension(760, 400));
        add(chartPanel, BorderLayout.CENTER);
        setVisible(true);

    }

    clase2() {

    }
    public void graficar(int[] datos) {
        CategoryPlot plot = (CategoryPlot) barchart.getPlot();
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();//esto es lo que vamos a llenar
        for (int k = 0; k < datos.length; k++) {
            dataset.addValue(datos[k], String.valueOf(k), "Datos");

        }
        plot.setDataset(dataset);
    }

    @Override
    public void run() {
        Thread time_running = new Thread(tiempo);
        texecute = 0;
        time_running.start();
        graficar(datos);

        pasos = 0;

        try {
            
            int n = datos.length;
        
            arreglo = new int[n];
            arreglo2 = new int[n];
            int temp = 0;
            for (int i = 0; i < n; i++) {
                arreglo2[i] = datos[i];
                Object[] value = {arreglo2[i]};
                
                mod2.addRow(value);
            }
         
           
            
            
            for (int i = 0; i < (n - 1); i++) {
                for (int j = 0; j < (n - 1); j++) {
                    if (datos[j] > datos[j + 1]) {
                        temp = datos[j];
                        datos[j] = datos[j + 1];
                        datos[j + 1] = temp;
                        Thread.sleep(70);

                        graficar(datos);
                        // System.out.println(datos[j+1]);

//          arreglo[j]=datos[j];
//    Object[]values = {arreglo[j] };
//         mod.addRow(values);
                        pasos++;
                        title1.setText("pasos:" + pasos);
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                arreglo[i] = datos[i];
                Object[] values = {arreglo[i]};
                mod.addRow(values);
            }
            int ss = 0;
            texecute = tiempo.tiempo;
            time_running.interrupt();
            try {
                final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
                final File file1 = new File("char.png");

                ChartUtilities.saveChartAsPNG(file1, barchart, 500, 600, info);
            } catch (Exception ex) {

            }

            Calendar fecha = Calendar.getInstance();
            int segundo = fecha.get(Calendar.SECOND);
            int minuto = fecha.get(Calendar.MINUTE);
            int hora = fecha.get(Calendar.HOUR);
            int año = fecha.get(Calendar.YEAR);
            int dia = fecha.get(Calendar.DAY_OF_YEAR);
            int mes = fecha.get(Calendar.MONTH);
            String a = Integer.toString(año);
            String d = Integer.toString(dia);
            String me = Integer.toString(mes);
            String h = Integer.toString(hora);
            String m = Integer.toString(minuto);
            String s = Integer.toString(segundo);
            Tiempo fs = new Tiempo();
            document = new Document();
            try {
                Paragraph parrafo = new Paragraph();
                parrafo.setAlignment(Paragraph.ALIGN_LEFT);
                parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.BLACK));
                parrafo.add("\n Nombre: Cristian Daniel Gomez Escobar ");

                Paragraph parraf = new Paragraph();
                parraf.setAlignment(Paragraph.ALIGN_LEFT);
                parraf.setFont(FontFactory.getFont("Tahoma", 15, Font.BOLD, BaseColor.BLACK));
                parraf.add("\n Carnet: 202107190 ");

                Paragraph parr = new Paragraph();
                parr.setAlignment(Paragraph.ALIGN_LEFT);
                parr.setFont(FontFactory.getFont("Tahoma", 15, Font.BOLD, BaseColor.BLACK));
                parr.add("\n Pasos:" + pasos);

                Paragraph parra = new Paragraph();
                parra.setAlignment(Paragraph.ALIGN_LEFT);
                parra.setFont(FontFactory.getFont("Tahoma", 15, Font.BOLD, BaseColor.BLACK));
                parra.add("\n Tiempo:" + tiempoclae2 + ":" + texecute + "\n");

                String ruta = System.getProperty("user.home");
                PdfWriter.getInstance(document, new FileOutputStream(ruta + "/Desktop/" + a + d + me + h + m + s + ".pdf"));
                Image header = Image.getInstance(ruta + "/Desktop/proyectos neatbeans/proyecto1/char.png");
                header.scaleToFit(500, 400);
                header.setAlignment(Chunk.ALIGN_CENTER);

                Paragraph par = new Paragraph();
                par.setAlignment(Paragraph.ALIGN_CENTER);
                par.add("\n ");
                par.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.GREEN));

                ppaa = new PdfPTable(1);
                ppaa.addCell("\nDatos de entrada");
                for (int i = 0; i < tabla2.getRowCount(); i++) {
                    String idd = tabla2.getValueAt(i, 0).toString();
                    ppaa.addCell(idd);

                }

                Paragraph pa = new Paragraph();
                pa.setAlignment(Paragraph.ALIGN_LEFT);
                pa.add("\n ");
                pa.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.GREEN));

                ppa = new PdfPTable(1);
                ppa.addCell("\nDatos ordenados");
                for (int i = 0; i < tabla.getRowCount(); i++) {
                    String id = tabla.getValueAt(i, 0).toString();
                    ppa.addCell(id);

                }

                document.open();
                document.add(parrafo);
                document.add(parraf);
                document.add(parr);
                document.add(parra);
                document.add(header);

                document.add(par);
                document.add(par);
                document.add(par);
                document.add(par);
                document.add(par);
                document.add(par);
                document.add(par);
                document.add(ppaa);
                document.add(pa);
                document.add(ppa);

            } catch (Exception exx) {

            }
            document.close();

            html();
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public void html() {

        Calendar fecha = Calendar.getInstance();
        int segundo = fecha.get(Calendar.SECOND);
        int minuto = fecha.get(Calendar.MINUTE);
        int hora = fecha.get(Calendar.HOUR);
        int año = fecha.get(Calendar.YEAR);
        int dia = fecha.get(Calendar.DAY_OF_YEAR);
        int mes = fecha.get(Calendar.MONTH);
        String a = Integer.toString(año);
        String d = Integer.toString(dia);
        String me = Integer.toString(mes);
        String h = Integer.toString(hora);
        String m = Integer.toString(minuto);
        String s = Integer.toString(segundo);

        try {
            String ruta = System.getProperty("user.home");
            File f = new File(ruta + "/Desktop/" + a + d + me + h + m + s + ".html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            String html = "<div><h1>Reporte html</h1><p>Nombre: Cristsian Daniel Gomez Escobar</p>"
                    + "<p>Carnet: 202107190 </p>"
                    + "<p>Pasos: " + pasos + "</p>"
                    + "<p>Tiempo: " + tiempoclae2 + ":" + texecute + "</p>"
                    + "<img src=file:///C:/Users/USER/Desktop/proyectos%20neatbeans/proyecto1/char.png >"
                    + "<table border=1>"
                    + "<tr>"
                    + "<td>Datos de entrada</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>Datos de en</td>"
                    + "</tr>"
                    + "</table>"
                    + "</div>";
            bw.write(html);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
 