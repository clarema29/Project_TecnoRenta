package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import com.google.gson.Gson;
import beans.Usuario;
import connection.DBConnection;
import java.util.HashMap;
import java.util.Map;

public class UsuarioController implements IUsuarioController {

    @Override
    public String login(String username, String clave) {
        Gson gson = new Gson();
        DBConnection con = new DBConnection();
        String sql = "Select * from usuario where username = '" + username
                + "' and clave = '" + clave + "'";

        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                double saldo = rs.getDouble("saldo");
                boolean premium = rs.getBoolean("premium");
                Usuario usuario = new Usuario(username, clave, nombre, apellido, email, saldo, premium);
                return gson.toJson(usuario);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }
        return "false";
    }

    @Override
    public String register(String username, String clave, String nombre, String apellido, String email,
            double saldo, boolean premium) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();
        String sql = "Insert into usuario values('" + username + "', '" + clave + "', '" + nombre
                + "', '" + apellido + "', '" + email + "', " + saldo + ", " + premium + ")";

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            Usuario usuario = new Usuario(username, clave, nombre, apellido, email, saldo, premium);

            st.close();

            return gson.toJson(usuario);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        } finally {
            con.desconectar();
        }

        return "false";

    }

    @Override
    public String pedir(String username) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();
        String sql = "Select * from usuario where username = '" + username + "'";

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String clave = rs.getString("clave");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                double saldo = rs.getDouble("saldo");
                boolean premium = rs.getBoolean("premium");

                Usuario usuario = new Usuario(username, clave,
                        nombre, apellido, email, saldo, premium);

                return gson.toJson(usuario);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }

    @Override
    public String modificar(String username, String nuevaClave,
            String nuevoNombre, String nuevosApellido,
            String nuevoEmail, double nuevoSaldo, boolean nuevoPremium) {

        DBConnection con = new DBConnection();

        String sql = "Update usuario set clave = '" + nuevaClave
                + "', nombre = '" + nuevoNombre + "', "
                + "apellido = '" + nuevosApellido + "', email = '"
                + nuevoEmail + "', saldo = " + nuevoSaldo + ", premium = ";

        if (nuevoPremium == true) {
            sql += " 1 ";
        } else {
            sql += " 0 ";
        }

        sql += " where username = '" + username + "'";

        try {

            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";

    }

    @Override
    public String verCantidad(String username) {

        DBConnection con = new DBConnection();
        String sql = "Select id,count(*) as num_cantidad from alquiler where username = '"
                + username + "' group by id;";

        Map<Integer, Integer> cantidad = new HashMap<Integer, Integer>();

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                int num_cantidad = rs.getInt("num_cantidad");

                cantidad.put(id, num_cantidad);
            }

            devolverProductos(username, cantidad);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";

    }

    @Override
    public String devolverProductos(String username, Map<Integer, Integer> cantidad) {

        DBConnection con = new DBConnection();

        try {
            for (Map.Entry<Integer, Integer> producto : cantidad.entrySet()) {
                int id = producto.getKey();
                int num_cantidad = producto.getValue();

                String sql = "Update producto set cantidad = (Select cantidad + " + num_cantidad
                        + " from producto where id = " + id + ") where id = " + id;

                Statement st = con.getConnection().createStatement();
                st.executeUpdate(sql);

            }

            this.eliminar(username);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }
        return "false";
    }

    @Override
    public String eliminar(String username) {

        DBConnection con = new DBConnection();

        String sql1 = "Delete from alquiler where username = '" + username + "'";
        String sql2 = "Delete from usuario where username = '" + username + "'";

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }
    
     @Override
    public String restarDinero(String username, double nuevoSaldo) {

        DBConnection con = new DBConnection();
        String sql = "Update usuario set saldo = " + nuevoSaldo + " where username = '" + username + "'";

        try {

            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }
}