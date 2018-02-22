/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.controller.factura;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FacturaCuController implements Initializable {

    @FXML
    private ComboBox<?> cbCliente;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TableView<?> tvServicios;
    @FXML
    private TableColumn<?, ?> tcId;
    @FXML
    private TableColumn<?, ?> tcName;
    @FXML
    private TableColumn<?, ?> tcDesc;
    @FXML
    private TableColumn<?, ?> tcPrice;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private ComboBox<?> cbServicio;
    @FXML
    private Button btnOkServ;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
