/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.controller.factura;

import app.logic.pojo.FacturaBean;
import app.logic.pojo.ServicioBean;
import app.logic.interfaces.FacturaManager;
import app.logic.imp.FacturaDAO;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FacturaMainController implements Initializable {

    private static final Logger logger = Logger.getLogger(FacturaMainController.class.getName());
    private FacturaManager facturaManager = new FacturaDAO();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private Stage stage;
    private Stage ownerStage;
    private ObservableList<FacturaBean> facturasData;

    @FXML
    private TableView<FacturaBean> tvFacturas;
    @FXML
    private TableColumn<FacturaBean, Integer> tcId;
    @FXML
    private TableColumn<FacturaBean, Integer> tcIdCliente;
    @FXML
    private TableColumn<FacturaBean, List<ServicioBean>> tcServicios;
    @FXML
    private TableColumn<FacturaBean, String> tcDate;
    @FXML
    private TableColumn<FacturaBean, Double> tcTotal;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void actionDelete(ActionEvent event) {

    }

    /**
     * Inicializa la stage
     *
     * @param root Elemento Parent del fxml
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("ServiTec - Facturas");
        //stage.getIcons().add(new Image(AppConstants.PATH_LOGO));
        stage.setResizable(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(ownerStage);
        stage.setOnShowing(this::handleWindowShowing);
        stage.setMaxWidth(700);
        stage.setMinWidth(700);
        stage.setMaxHeight(600);
        stage.setMinHeight(600);
        stage.show();

    }

    private void handleWindowShowing(WindowEvent event) {
        initTable();
        InitRowDoubleClickEvent();
    }

    /**
     * Establece owner stage
     *
     * @param ownerStage
     */
    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    /**
     * Conecta Stage a controlador
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initTable() {
        // Obtener Collection de Clientes
        facturasData = FXCollections.observableArrayList(facturaManager.getFacturas());

        // TableCellFactory
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcIdCliente.setCellValueFactory(new PropertyValueFactory<>("idcliente"));
        tcServicios.setCellValueFactory(new PropertyValueFactory<>("servicios"));

        // Custom rendering of the table cell.
        tcServicios.setCellFactory(column -> {
            return new TableCell<FacturaBean, List<ServicioBean>>() {
                @Override
                protected void updateItem(List<ServicioBean> item, boolean empty) {
                    super.updateItem(item, empty);
      
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        System.out.println(item.toString());
                        setText(item.toString());
                    }
                }
            };
        });

        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Placeholder en caso de no tener datos
        tvFacturas.setPlaceholder(new Label("Sin registros."));

        tvFacturas.setItems(facturasData);
    }

    /**
     * Accion al hacer doble click sobre row de la tabla
     */
    public void InitRowDoubleClickEvent() {
        tvFacturas.setRowFactory(tv -> {
            TableRow<FacturaBean> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        System.out.println(tvFacturas.getSelectionModel().getSelectedItem().getServicios().toString());
                        //loadCrearMod(tvFacturas.getSelectionModel().getSelectedItem());
                    } catch (Exception ex) {
                        logger.info("Error al cargar ventana modificar factura. (Double click event)");
                    }

                }
            });
            return row;
        });
    }

}
