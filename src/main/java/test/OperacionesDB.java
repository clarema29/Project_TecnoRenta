package test;

import beans.Productos;
import connection.DBConnection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OperacionesDB {
    public static void main(String[] args) {
        listarProducto();
    }
    public static void actualizarProducto(int id, String referencia){
        DBConnection con = new DBConnection();
        String sql = "UPDATE producto SET referencia = '" +referencia+ "'WHERE id = " +id;
        
        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);
           }catch (Exception ex) {
                    System.out.println(ex.getMessage());
           }finally {
                    con.desconectar();    
           }
    }
    
    public static void listarProducto(){
        
        DBConnection con = new DBConnection();
        String sql = "SELECT * FROM producto";

        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                String referencia = rs.getString("referencia");
                String marca = rs.getString("marca");
                int cantidad = rs.getInt("cantidad");
                boolean novedad = rs.getBoolean("novedad");
                Productos productos = new Productos(id, tipo, referencia, marca, cantidad, novedad);
                
                System.out.println(productos.toString());
            }
            
            st.executeQuery(sql);
            
           }catch (Exception ex) {
                    System.out.println(ex.getMessage());
           }finally {
                    con.desconectar();    
           }
    }
}
