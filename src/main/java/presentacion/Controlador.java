/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Estudiantes
 */
public class Controlador implements ActionListener {

    private final Vista vista;

    public Controlador(Vista aThis) {
        vista = aThis;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Eventos evento = Eventos.valueOf(actionEvent.getActionCommand());
        if (evento != null) {
            switch (evento) {
                case CALCULAR_REPORTE_INGVSEGR: {
                    this.vista.getModelo().calcularReporteIngVsEgr();
                }
                break;
            }
        }
    }

}
