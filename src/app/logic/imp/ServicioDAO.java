/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.logic.imp;

import app.config.db.DbManager;
import app.logic.pojo.ClienteBean;
import app.logic.pojo.ServicioBean;
import app.logic.interfaces.ServicioManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ServicioDAO implements ServicioManager{
    private static final Logger logger = Logger.getLogger(ServicioDAO.class.getName());
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private Connection dbconn;
    
    public ServicioDAO(){
        this.dbconn=DbManager.getInstance().getDbconn();
    }
    
    public Collection<ServicioBean> getServicios(){
        int count = 0;
        String query="SELECT * FROM SERVICIOS";
        ArrayList<ServicioBean> list = new ArrayList<>();
        try {
            Statement stm = dbconn.createStatement();
            ResultSet res = stm.executeQuery(query);
            
            while(res.next()){
                
                list.add(new ServicioBean(res.getInt("ID_SER"),res.getString("NAME_SER"),
                res.getString("DESCRIPTION"),res.getDouble("PRICE")));
                count++;
            }
            res.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServicioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public boolean deleteServicio(ServicioBean servicio){
        boolean success=false;
        String query="DELETE FROM SERVICIOS WHERE ID_SER=?";

        try {
            PreparedStatement stm = dbconn.prepareStatement(query);
            stm.setInt(1, servicio.getId());
            int res = stm.executeUpdate();
            stm.close();
            
            if(res>0){
                success=true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ServicioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    public boolean updateServicio(ServicioBean servicio){
        boolean success=false;
        String query="UPDATE SERVICIOS SET NAME_SER=?, DESCRIPTION=?, PRICE=? WHERE ID_SER=?";

        try {
            PreparedStatement stm = dbconn.prepareStatement(query);
            stm.setString(1, servicio.getName());
            stm.setString(2, servicio.getDescription());
            stm.setDouble(3, servicio.getPrice());
            stm.setInt(4, servicio.getId());
            
            int res = stm.executeUpdate();
            stm.close();
            
            if(res>0){
                success=true;
                logger.log(Level.INFO, "Modificado servicio id: {0}", servicio.getId());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ServicioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return success;
    }
    
    @Override
    public boolean insertServicio(ServicioBean servicio){
        boolean success=false;
        String query="INSERT INTO SERVICIOS (NAME_SER, DESCRIPTION, PRICE) VALUES (?,?,?)";
        
        try {
            PreparedStatement stm = dbconn.prepareStatement(query);
            stm.setString(1, servicio.getName());
            stm.setString(2, servicio.getDescription());
            stm.setFloat(3, servicio.getPrice().floatValue());
            
            int res = stm.executeUpdate();
            stm.close();
            if(res>0){
                success=true;
                logger.log(Level.INFO, "Insertado nuevo servicio id: {0}", servicio.getId());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ServicioDAO.class.getName()).log(Level.SEVERE, "SqlException", ex);
        }
        return success;
    }
}
