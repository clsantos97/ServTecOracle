/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.logic.imp;

import app.config.db.DbManager;
import app.logic.pojo.FacturaBean;
import app.logic.pojo.ServicioBean;
import app.logic.interfaces.FacturaManager;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class FacturaDAO implements FacturaManager {

    private static final Logger logger = Logger.getLogger(FacturaDAO.class.getName());
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private Connection dbconn;

    public FacturaDAO() {
        this.dbconn = DbManager.getInstance().getDbconn();
    }

    public Collection<FacturaBean> getFacturas() {
        //System.out.println("getfacturas");
        int count = 0;
        String query = "SELECT * FROM FACTURAS";
        ArrayList<FacturaBean> facturasList = new ArrayList<>();
        ArrayList<ServicioBean> serviciosList = new ArrayList<>();
        try {
            Statement stm = dbconn.createStatement();
            ResultSet res = stm.executeQuery(query);

            while (res.next()) {
                Array servArray = res.getArray("SERVS");
                if (servArray != null) {
                    Object[] servicios = (Object[]) servArray.getArray();

                    for (int i = 0; i < servicios.length; i++) {
                        ServicioBean servicioBean = new ServicioBean();
                        Struct servicio = (Struct) servicios[i];
                        Object[] attrib = servicio.getAttributes();
                        servicioBean.setId(Integer.valueOf(attrib[0].toString()));
                        servicioBean.setName(attrib[1].toString());
                        servicioBean.setDescription(attrib[2].toString());
                        servicioBean.setPrice(Double.parseDouble(attrib[3].toString()));
                        serviciosList.add(servicioBean);
                    }
                }

                //serviciosList.forEach(s -> System.out.println(s.getName()));
                // Create factura with resultSet data
                FacturaBean factura = new FacturaBean();
                factura.setId(res.getInt("ID_FAC"));
                factura.setIdcliente(res.getInt("ID_CLI"));
                factura.setDate(res.getDate("DATE_FAC").toLocalDate().format(dateFormatter));
                factura.setTotal(res.getDouble("TOTAL"));
                factura.setServicios(serviciosList);
                facturasList.add(factura);
            }

            facturasList.stream().forEach(f -> System.out.println("Id: " + f.getId()
                    + "  Idcliente: " + f.getIdcliente()
                    + " Servicios: " + f.getServicios().toString()
                    + "  Date: " + f.getDate() + "  Total: " + f.getTotal()));

            //System.out.println(facturasList.toString());
            res.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return facturasList;
    }

    public boolean deleteFactura(FacturaBean factura) {
        boolean success = false;
        String query = "DELETE FROM FACTURAS WHERE ID_FAC=?";

        try {
            PreparedStatement stm = dbconn.prepareStatement(query);
            stm.setInt(1, factura.getId());
            int res = stm.executeUpdate();
            stm.close();

            if (res > 0) {
                success = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return success;
    }

    public boolean updateFactura(FacturaBean factura) {
        boolean success = false;
        if (factura != null) {
            try {
                String query = "UPDATE MANAGER.FACTURAS SET ID_CLI=?, DATE_FAC=?, TOTAL=? WHERE ID_FAC=?";
                java.util.Date fdate = sdf.parse(factura.getDate());
                Object[] servicios = factura.getServicios().toArray();

//                Array servArray = dbconn.createArrayOf("MANAGER.SERVICIOS_LIST", servicios);
                PreparedStatement stm = dbconn.prepareStatement(query);
                stm.setInt(1, factura.getIdcliente());
//                stm.setArray(2, servArray);
                stm.setDate(2, new Date(fdate.getTime()));
                stm.setDouble(3, factura.getTotal());
                stm.setInt(4, factura.getId());

                int res = stm.executeUpdate();
                stm.close();

                if (res > 0) {
                    success = true;
                    logger.log(Level.INFO, "Modificada factura id: {0}", factura.getId());
                }

            } catch (SQLException ex) {
                Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return success;
    }

    @Override
    public boolean insertFactura(FacturaBean factura) {
        boolean success = false;
        String query = "INSERT INTO FACTURAS (ID_CLI, DATE_FAC, TOTAL) VALUES (?,?,?)";

        try {
            PreparedStatement stm = dbconn.prepareStatement(query);
            stm.setInt(1, factura.getIdcliente());
            stm.setDate(2, new Date(Date.valueOf(LocalDate.now()).getTime()));
            stm.setDouble(3, factura.getTotal());

            int res = stm.executeUpdate();
            stm.close();
            if (res > 0) {
                success = true;
                logger.log(Level.INFO, "Insertado nueva factura id: {0}", factura.getId());
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, "SqlException", ex);
        }
        return success;
    }
}
