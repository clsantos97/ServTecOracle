package app.logic.pojo;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import app.logic.pojo.ServicioBean;

/**
 *
 * @author Carlos
 */
public class FacturaBean {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty idcliente;
    private SimpleObjectProperty<List<ServicioBean>> servicios;
    private SimpleStringProperty date;
    private SimpleDoubleProperty total;
    
    public FacturaBean(Integer id, Integer idcliente,List<ServicioBean> servicios, String date, Double total){
        this.id=new SimpleIntegerProperty(id);
        this.idcliente=new SimpleIntegerProperty(idcliente);
        this.servicios = new SimpleObjectProperty<List<ServicioBean>>(servicios);
        this.date = new SimpleStringProperty(date);
        this.total = new SimpleDoubleProperty(total);
    }
    
    public FacturaBean(){
        this.id=new SimpleIntegerProperty(0);
        this.idcliente=new SimpleIntegerProperty(0);
        this.servicios = new SimpleObjectProperty<List<ServicioBean>>(new ArrayList<>());
        this.date = new SimpleStringProperty("");
        this.total = new SimpleDoubleProperty(0);
    }

    /**
     * @return the servicios
     */
    public List<ServicioBean> getServicios() {
        return servicios.get();
    }

    /**
     * @param servicios the servicios to set
     */
    public void setServicios(List<ServicioBean> servicios) {
        this.servicios.set(servicios);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * @return the idcliente
     */
    public Integer getIdcliente() {
        return idcliente.get();
    }

    /**
     * @param idcliente the idcliente to set
     */
    public void setIdcliente(Integer idcliente) {
        this.idcliente.set(idcliente);
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date.get();
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date.set(date);
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total.get();
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total.set(total);
    }
 
    public String toString(){
        return this.getId()+", "+this.getIdcliente()+", "+this.getDate()+", "+this.getServicios()+", "+this.getTotal();
    }
    
}
