/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.panel;

import entidades.CategoriaMovimiento;
import entidades.Movimiento;
import entidades.TipoCategoria;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import presentacion.Eventos;
import presentacion.Modelo;

/**
 *
 * @author rfcas
 */
public class PanelReporteCategoria extends javax.swing.JPanel {

    private Modelo modelo;
    private ControladorReporteCategoria controlador;

    private ComboBoxModel tipoCategoriaModel;
    private ComboBoxModel categoriaModel;
    private TableModel reporteModel;

    /**
     * Creates new form PanelReporteCategoria
     */
    public PanelReporteCategoria(Modelo modelo) {
        super();
        this.controlador = new ControladorReporteCategoria(this);
        this.modelo = modelo;
        initModels();
        initComponents();
        this.jComboBoxTipoCategoria.setSelectedIndex(0);
    }

    private void initModels() {
        this.tipoCategoriaModel = new TipoCategoriaModel2(modelo);
        this.categoriaModel = new CategoriaModel2(modelo);
        this.reporteModel = new ReporteCategoriaTableModel(modelo);
    }

    public void refrescar(CategoriaMovimiento categoria) {
        ((ReporteCategoriaTableModel) this.reporteModel).setCategoria(categoria);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBoxTipoCategoria = new javax.swing.JComboBox<>();
        jComboBoxCategoria = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jComboBoxTipoCategoria.setModel(this.tipoCategoriaModel);
        jPanel1.add(jComboBoxTipoCategoria);

        jComboBoxCategoria.setModel(this.categoriaModel);
        jComboBoxTipoCategoria.addActionListener((CategoriaModel2) categoriaModel);
        jPanel1.add(jComboBoxCategoria);

        jButton1.setText("Calcular");
        jButton1.setActionCommand(Eventos.CALCULAR_REPORTE_CATEGORIA.toString());
        jButton1.addActionListener(controlador);
        jPanel1.add(jButton1);

        add(jPanel1);

        jPanel2.setLayout(new javax.swing.OverlayLayout(jPanel2));

        jTable1.setModel(this.reporteModel);
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1);

        add(jPanel2);
    }// </editor-fold>


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<CategoriaMovimiento> jComboBoxCategoria;
    private javax.swing.JComboBox<TipoCategoria> jComboBoxTipoCategoria;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public TableModel getReporteModel() {
        return reporteModel;
    }

    public JComboBox<CategoriaMovimiento> getjComboBoxCategoria() {
        return jComboBoxCategoria;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public ControladorReporteCategoria getControlador() {
        return controlador;
    }

}

class TipoCategoriaModel2 extends DefaultComboBoxModel<TipoCategoria> {

    private Modelo modelo;

    public TipoCategoriaModel2(Modelo modelo) {
        super(modelo.getListadoTipoCategoria().toArray(new TipoCategoria[0]));
        this.modelo = modelo;
    }

}

class CategoriaModel2 extends DefaultComboBoxModel<CategoriaMovimiento> implements ActionListener {

    private Modelo modelo;

    public CategoriaModel2(Modelo modelo) {
        super();
        this.modelo = modelo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox jComboBoxTipoCategoria = (JComboBox) e.getSource();
        TipoCategoria tipoCategoria = (TipoCategoria) jComboBoxTipoCategoria.getSelectedItem();
        //
        this.removeAllElements();
        for (CategoriaMovimiento categoriaMovimiento : this.modelo.getListadoCategoriaMovimiento(tipoCategoria)) {
            this.addElement(categoriaMovimiento);
        }
    }

}

class ReporteCategoriaTableModel extends AbstractTableModel {

    private static final String[] COLUMNAS = new String[]{"#", "Cuenta", "Fecha", "Descripción", "Valor COP"};

    private Modelo modelo;

    private CategoriaMovimiento categoria;

    private List<Movimiento> movimientos;

    public ReporteCategoriaTableModel(Modelo modelo) {
        super();
        this.modelo = modelo;
        this.categoria = null;
        this.movimientos = null;
    }

    public void setCategoria(CategoriaMovimiento categoria) {
        this.categoria = categoria;
        //
        this.fireTableDataChanged();
    }

    private List<Movimiento> getMovimientos() {
        if (this.movimientos == null) {
            this.movimientos = modelo.obtenerMovimientoPorCategoria(categoria);
        }
        return this.movimientos;
    }

    @Override
    public int getRowCount() {
        return this.getMovimientos().size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int col) {
        return ReporteCategoriaTableModel.COLUMNAS[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movimiento movimiento = this.getMovimientos().get(rowIndex);
        switch (columnIndex) {
            case 0: {
                NumberFormat numberFormat = new DecimalFormat("0000000");
                return numberFormat.format(movimiento.getId());
            }
            case 1:
                return movimiento.getCuenta().getNombre();
            case 2: {
                return movimiento.getFecha();
            }
            case 3:
                return movimiento.getDescripcion();
            case 4: {
                NumberFormat numberFormat = new DecimalFormat("0.##");
                return numberFormat.format(movimiento.getValor());
            }
            default:
                return null;
        }
    }

    @Override
    public void fireTableDataChanged() {
        this.movimientos = null;
        super.fireTableDataChanged(); //To change body of generated methods, choose Tools | Templates.
    }

}
