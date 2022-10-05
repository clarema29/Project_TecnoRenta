
package controller;

import java.util.Map;

public interface IUsuarioController {

    public String login(String username, String clave);

    public String register(String username, String clave,
            String nombre, String apellido, String email, double saldo, boolean premium);

    public String pedir(String username);

    public String modificar(String username, String nuevaClave,
            String nuevoNombre, String nuevosApellido, String nuevoEmail,
            double nuevoSaldo, boolean nuevoPremium);

    public String verCantidad(String username);

    public String devolverProductos(String username, Map<Integer, Integer> cantidad);

    public String eliminar(String username);

    public String restarDinero(String username, double nuevoSaldo);

}