

package MODELO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;



public class Empleado extends Persona{
    
    private String Codigo;
    private int id_empleado;
    conexion cn;
    
    public Empleado(){}
    public Empleado(int id_empleado, String Codigo, String nombres, String apellidos, String direccion, String telefono, String nacimiento, int puesto) {
        super(nombres, apellidos, direccion, telefono, nacimiento, puesto);
        this.id_empleado = id_empleado;
        this.Codigo = Codigo;
    }
    
    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int  id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }
    
    @Override
    public void agregar(){
        try {
        PreparedStatement parametro;
        cn = new conexion();
        cn.abrir_conexion();
        String query = "INSERT INTO empleados(codigo, nombres, apellidos, direccion, telefono, fecha_nacimiento, id_puesto) VALUES(?,?,?,?,?,?,?);";
        parametro = (PreparedStatement) cn.conectar_db.prepareStatement(query);
        parametro.setString(1, getCodigo());
        parametro.setString(2, getNombres());
        parametro.setString(3, getApellidos());
        parametro.setString(4, getDireccion());
        parametro.setString(5, getTelefono());
        parametro.setString(6, getNacimiento());
        parametro.setInt(7, getPuesto());  

        int executar = parametro.executeUpdate();
        System.out.println("Ingreso exitoso: " + executar);
        cn.cerrar_conexion();
    } catch (SQLException ex) {
        System.out.println("Error en agregar: " + ex.getMessage());
    }
    }
    
    public DefaultTableModel leer() {
    DefaultTableModel tabla = new DefaultTableModel();

        try {
        cn = new conexion();
        cn.abrir_conexion();

        
        String query = "SELECT e.id_empleado, e.codigo, e.nombres, e.apellidos, e.direccion, e.telefono, e.fecha_nacimiento, p.puesto " +
                       "FROM empleados e " +
                       "INNER JOIN puestos p ON e.id_puesto = p.id_puesto;";  

        ResultSet consulta = cn.conectar_db.createStatement().executeQuery(query);

        
        String encabezado[] = {"ID", "CODIGO", "NOMBRES", "APELLIDOS", "DIRECCION", "TELEFONO", "NACIMIENTO", "PUESTO"};
        tabla.setColumnIdentifiers(encabezado);

        
        String datos[] = new String[8];  

        while (consulta.next()) {
            datos[0] = consulta.getString("id_empleado");
            datos[1] = consulta.getString("codigo");
            datos[2] = consulta.getString("nombres");
            datos[3] = consulta.getString("apellidos");
            datos[4] = consulta.getString("direccion");
            datos[5] = consulta.getString("telefono");
            datos[6] = consulta.getString("fecha_nacimiento");
            datos[7] = consulta.getString("puesto");  
            tabla.addRow(datos);
        }
        cn.cerrar_conexion();
    } catch (SQLException ex) {
        cn.cerrar_conexion();
        System.out.println("Error en leer: " + ex.getMessage());
    }
    return tabla;  
    }
    
    @Override
    public void actualizar(){
       try {
        PreparedStatement parametro;
        cn = new conexion();
        cn.abrir_conexion();

        
        String query = "UPDATE empleados SET codigo = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, fecha_nacimiento = ?, id_puesto = ? WHERE id_empleado = ?;";

        parametro = (PreparedStatement) cn.conectar_db.prepareStatement(query);

        
        parametro.setString(1, getCodigo());
        parametro.setString(2, getNombres());
        parametro.setString(3, getApellidos());
        parametro.setString(4, getDireccion());
        parametro.setString(5, getTelefono());
        parametro.setString(6, getNacimiento());
        parametro.setInt(7, getPuesto());

        
        parametro.setInt(8, getId_empleado());

        
        int executar = parametro.executeUpdate();
        System.out.println("Modificación exitosa: " + executar);

        
        cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Error en actualizar: " + ex.getMessage());
        }
    }
    
    @Override
    public void borrar(){
        try {
            PreparedStatement parametro;
            cn = new conexion();
            cn.abrir_conexion();
            String query = "DELETE FROM empleados WHERE id_empleado = ?;";

            
            parametro = cn.conectar_db.prepareStatement(query); 

            
            parametro.setInt(1, getId_empleado());

            int executar = parametro.executeUpdate();
            System.out.println("Borrado exitoso: " + executar);

            
            parametro.close();
            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Error en borrar: " + ex.getMessage());
            }
        }
}
