/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.logic.imp;

import app.config.db.DbManager;
import app.logic.pojo.ClienteBean;
import app.logic.interfaces.ClienteManager;
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
public class ClienteDAO implements ClienteManager{
    private static final Logger logger = Logger.getLogger(ClienteDAO.class.getName());
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private Connection dbconn;
    
    public ClienteDAO(){
        this.dbconn=DbManager.getInstance().getDbconn();
    }
    
    public Collection<ClienteBean> getClientes(){
        int count = 0;
        String query="SELECT * FROM CLIENTES";
        ArrayList<ClienteBean> list = new ArrayList<>();
        try {
            Statement stm = dbconn.createStatement();
            ResultSet res = stm.executeQuery(query);
            
            while(res.next()){
                
                list.add(new ClienteBean(res.getInt("ID_CLI"),res.getString("NAME_CLI"),
                res.getString("LASTNAME"),res.getString("PHONE"),res.getString("EMAIL"),
                res.getString("ZIPCODE"),res.getDate("REGDATE").toLocalDate().format(dateFormatter)));
                count++;
            }
            res.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public boolean deleteCliente(ClienteBean cliente){
        boolean success=false;
        String query="DELETE FROM CLIENTES WHERE ID_CLI=?";

        try {
            PreparedStatement stm = dbconn.prepareStatement(query);
            stm.setInt(1, cliente.getId());
            int res = stm.executeUpdate();
            stm.close();
            
            if(res>0){
                success=true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    public boolean updateCliente(ClienteBean cliente){
        boolean success=false;
        String query="UPDATE CLIENTES SET NAME_CLI=?, LASTNAME=?, PHONE=?, EMAIL=?, ZIPCODE=? WHERE ID_CLI=?";

        try {
            PreparedStatement stm = dbconn.prepareStatement(query);
            stm.setString(1, cliente.getName());
            stm.setString(2, cliente.getLastname());
            stm.setString(3, cliente.getPhone());
            stm.setString(4, cliente.getEmail());
            stm.setString(5, cliente.getZipcode());
            stm.setInt(6, cliente.getId());
            
            int res = stm.executeUpdate();
            stm.close();
            
            if(res>0){
                success=true;
                logger.log(Level.INFO, "Modificado cliente id: {0}", cliente.getId());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return success;
    }
    
    @Override
    public boolean insertCliente(ClienteBean cliente){
        boolean success=false;
        String query="INSERT INTO CLIENTES (NAME_CLI, LASTNAME, PHONE, EMAIL, ZIPCODE, REGDATE) VALUES (?,?,?,?,?,sysdate)";
        
        try {
            PreparedStatement stm = dbconn.prepareStatement(query);
            stm.setString(1, cliente.getName());
            stm.setString(2, cliente.getLastname());
            stm.setString(3, cliente.getPhone());
            stm.setString(4, cliente.getEmail());
            stm.setString(5, cliente.getZipcode());
            //stm.setDate(7, new java.sql.Date(newDate.getTime()));
            
            int res = stm.executeUpdate();
            stm.close();
            if(res>0){
                success=true;
                logger.log(Level.INFO, "Insertado nuevo cliente id: {0}", cliente.getId());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, "SqlException", ex);
        }
        return success;
    }
}
