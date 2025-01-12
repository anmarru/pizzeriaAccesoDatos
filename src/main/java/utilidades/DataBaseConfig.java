package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConfig {

    public static final String URL = "jdbc:mysql://localhost:3306/pizzeria";
    public static final String USER = "root";
    public static final String PASS = "admin";

    //CREAR TABLA CLIENTES
    public static String CREATE_TABLE_CLIENTES= "CREATE TABLE IF NOT EXISTS clientes ("+
            "id INT AUTO_INCREMENT PRIMARY KEY, "+
            "dni VARCHAR(255) NOT NULL UNIQUE, "+
            "nombre VARCHAR(255) NOT NULL, "+
            "direccion VARCHAR(255) NOT NULL, "+
            "telefono VARCHAR(255) NULL UNIQUE , "+
            "email VARCHAR(255) NOT NULL UNIQUE, "+
            "password VARCHAR(255) NOT NULL UNIQUE, "+
            "esAdministrador BOOLEAN DEFAULT 0 "+
            ");";

    //CREAR TABLA PRODUCTO
    public static String CREATE_TABLE_PRODUCTO= "CREATE TABLE IF NOT EXISTS producto( "+
            "id INT AUTO_INCREMENT PRIMARY KEY, "+
            "nombre VARCHAR(255) NOT NULL,"+
            "precio DECIMAL(10,2),"+
            "tipo VARCHAR(255) NOT NULL,"+
            "tamanio VARCHAR(255) DEFAULT NULL"+
            ");";

    //CREAR TABLA INGREDIENTE
    public static String CREATE_TABLE_INGREDIENTE= "CREATE TABLE IF NOT EXISTS ingrediente( "+
            "id INT AUTO_INCREMENT PRIMARY KEY, "+
            "nombre VARCHAR(255) NOT NULL ,"+
            "id_producto INT NOT NULL ,"+ //relaciono el ingrediente con el producto
            "FOREIGN KEY (id_producto) REFERENCES producto(id) ON DELETE CASCADE "+//si se elimina el producto se elimina los ingredientes
            ");";

    //CRAEAR TABLA ALERGENO
    public static String CREATE_TABLE_ALERGENO= "CREATE TABLE IF NOT EXISTS alergeno("+
            "id INT AUTO_INCREMENT PRIMARY KEY ,"+
            "nombre VARCHAR(255) NOT NULL"+
            ");";

    //CREAR TABLA INGREDIENTE_ALERGENO relacion de muchos a muchos (nueva tabla)
    public static String CREATE_TABLE_INGREDIENTE_ALERGENO= "CREATE TABLE IF NOT EXISTS ingrediente_alergeno( "+
            "id_ingrediente INT NOT NULL, "+
            "id_alergeno INT NOT NULL, "+
            "PRIMARY KEY (id_ingrediente, id_alergeno), "+
            "FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id) ON DELETE CASCADE, "+
            "FOREIGN KEY (id_alergeno) REFERENCES alergeno(id) ON DELETE CASCADE "+
            ");";

    //CREAR TABLA INGREDIENTE_PRODUCTO
    public static String CREATE_TABLE_INGREDIENTE_PRODUCTO = "CREATE TABLE IF NOT EXISTS ingrediente_producto( " +
            "id_ingrediente INT NOT NULL, " +
            "id_producto INT NOT NULL, " +
            "PRIMARY KEY (id_ingrediente, id_producto), " +
            "FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (id_producto) REFERENCES producto(id) ON DELETE CASCADE " +
            ");";

    //CREAR TABLA PEDIDO
    public  static  String CREATE_TABLE_PEDIDO= "CREATE TABLE IF NOT EXISTS pedido ("+
            "id INT AUTO_INCREMENT PRIMARY KEY ,"+
            "fecha DATE NOT NULL,"+
            "estado VARCHAR(255) NOT NULL ,"+
            "cliente INT NOT NULL, "+
            "FOREIGN KEY (cliente) REFERENCES clientes(id) ON DELETE NO ACTION ON UPDATE CASCADE"+
            ");";

    //CREATE TABLE LINEA_PEDIDO
    public static String CREATE_TABLE_LINEAS_PEDIDO= "CREATE TABLE IF NOT EXISTS linea_pedido("+
            "id INT AUTO_INCREMENT PRIMARY KEY, "+
            "id_pedido INT NOT NULL, "+
            "id_producto INT NOT NULL, "+
            "cantidad INT NOT NULL,"+
            "FOREIGN KEY (id_pedido) REFERENCES pedido(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (id_producto) REFERENCES producto(id) ON DELETE CASCADE" +
            ");";



    //INSERTAR CAMPOS A LAS TABLAS
    public static final String INSERT_CLIENTES= "INSERT INTO clientes (dni, nombre, direccion, telefono, email, password, esAdministrador) VALUES (?,?,?,?,?,?,?)";
    public static final String INSET_PRODUCTO= "INSERT INTO producto (nombre, precio, tipo,tamanio) VALUES ( ?, ?, ? ,?)";
    public static final String INSERT_INGREDIENTE= "INSERT INTO ingrediente (nombre, id_producto) VALUES (?, ?)";
    public static final String INSERT_ALERGENO= "INSERT INTO alergeno (nombre) VALUES (?)";
    public static final String INSERT_INGREDIENTE_ALERGENO= "INSERT INTO ingrediente_alergeno (id_ingrediente, id_alergeno) VALUES (?, ?)";
    public static final String INSERT_INGREDIENTE_PRODUCTO = "INSERT INTO ingrediente_producto (id_ingrediente, id_producto) VALUES (?, ?)";
    public static final String INSERT_PEDIDO= "INSERT INTO pedido (id, cliente_id, fecha, estado) VALUES (?, ?, ?, ?)";


    //ELIMINAR CAMPOS
    public static final String DELETE_CLIENTES = "DELETE FROM CLIENTES WHERE dni=?";
    public static final String DELETE_PRODUCTO= "DELETE FROM producto WHERE id= ?";
    public static final String DELETE_INGREDIENTE= "DELETE FROM ingrediente WHERE id_producto= ? ";
    public static final String DELETE_INGREDIENTE_ALERGENO= "DELETE FROM ingrediente_alergeno WHERE id_ingrediente= ?";
    public static final String DELETE_INGREDIENTE_PRODUCTO = "DELETE FROM ingrediente_producto WHERE id_ingrediente = ? AND id_producto = ?";
    public static final String DELETE_PEDIDO= "DELETE FROM pedido WHERE id = ?";

    //ACTUALIZAR TABLAS
    public static final String UPDATE_CLIENTES = "UPDATE CLIENTES SET dni=? ,nombre = ?, direccion = ? , telefono = ?, email=?, password=?, esAdministrador=? ";
    public static final String UPDATE_PRODUCTO= "UPDATE producto SET nombre=? , precio= ?, tipo= ?, tamanio = ? WHERE id= ?";
    public static final String UPDATE_PEDIDO= "UPDATE pedido SET precio_total = ?, estado = ? WHERE id = ?";

    //ELIMINAR TABLAS
    private static final String DROP_TABLE_CLIENTES = "DROP TABLE IF EXISTS clientes";
    private static final String DROP_TABLE_PRODUCTO = "DROP TABLE IF EXISTS producto";
    private static final String DROP_TABLE_INGREDIENTE= "DROP TABLE IF EXISTS ingrediente";
    private static final String DROP_TABLE_ALERGENO= "DROP TABLE IF EXISTS alergeno";
    private static final String DROP_TABLE_INGREDIENTE_ALERGENO = "DROP TABLE IF EXISTS ingrediente_alergeno";
    private static final String DROP_TABLE_INGREDIENTE_PRODUCTO= "DROP TABLE IF EXISTS ingrediente_producto";
    private static final String DROP_TABLE_PEDIDO = "DROP TABLE IF EXISTS pedido";
    private static final String DROP_TABLE_LINEAS_PEDIDO= "DROP TABLE IF EXISTS lineas_pedido";


    //BUSCAR CLIENTE EMAIL
    public static final String BUSCAR_CLIENTE_EMAIL= " SELECT id, dni ,nombre ,direccion, telefono, password , esAdministrador FROM clientes WHERE email=? ";

    //BUSCAR TODOS LOS CLIENTES
    public static final String BUSCAR_TODOS_CLIENTES= "SELECT id, dni, nombre, direccion, telefono, email, password, esAdministrador FROM clientes";

    //BUSCAR PRODUCTOS POR ID
    public static final String BUSCAR_TODOS_PRODUCTOS= "SELECT * FROM producto ";
    public static final String BUSCAR_TODOS_INGREDIENTES= "SELECT ingrediente.id, ingrediente.nombre, ingrediente.id_producto FROM ingrediente WHERE id_producto = ?";
    //obtengo los alergenos asociados a un ingrediente
    public static final String BUSCAR_TODOS_ALERGENOS= "SELECT alergeno.* FROM ingrediente_alergeno "+
            "JOIN alergeno ON ingrediente_alergeno.id_alergeno = alergeno.id "+
            "WHERE ingrediente_alergeno.id_ingrediente= ?";

    public static final String PRODUCTO_PRUEBA= "SELECT p.id, p.nombre, p.precio, p.tipo, p.tamanio, i.nombre AS ingrediente, a.nombre AS alergeno\n" +
            "FROM producto p\n" +
            "LEFT JOIN ingrediente_producto ip ON p.id = ip.producto_id\n" +
            "LEFT JOIN ingrediente i ON ip.ingrediente_id = i.id\n" +
            "LEFT JOIN ingrediente_alergeno ia ON i.id = ia.ingrediente_id\n" +
            "LEFT JOIN alergeno a ON ia.alergeno_id = a.id;\n";

     public  static final String SELECT_PRUEBA = "SELECT ingrediente.id, ingrediente.nombre\n" +
             "FROM ingrediente\n" +
             "JOIN ingrediente_producto ON ingrediente.id = ingrediente_producto.id_ingrediente\n" +
             "JOIN producto ON producto.id = ingrediente_producto.id_producto\n" +
             "WHERE producto.id = ?\n";


    //BUSCAR PEDIDOS POR ID
    public static final String BUSCAR_PEDIDOS_ID= "SELECT * FROM pedido WHERE id = ?";
    public static final String BUSCAR_TODOS_PEDIDOS= "SELECT p.id, p.fecha, p.estado, p.cliente" +
            "FROM pedido p " + "JOIN clientes c ON p.cliente = c.id; //unir con la tabla de clientes para obtener el nombre";
    public static final String BUSCAR_LINEASP_PEDIDO= "SELECT lp.id, lp.producto, lp.cantidad, lp.precio, pr.nombre " +
            "FROM linea_pedido lp " + "JOIN producto pr ON lp.producto = pr.id " +
            "WHERE lp.pedido = ?";
    public static final String OBTENER_PEDIDOS= "SELECT * FROM pedido";


    public static void createTables() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_CLIENTES);
            stmt.execute(CREATE_TABLE_PRODUCTO);
            stmt.execute(CREATE_TABLE_INGREDIENTE);
            stmt.execute(CREATE_TABLE_ALERGENO);
            stmt.execute(CREATE_TABLE_INGREDIENTE_ALERGENO);
            stmt.execute(CREATE_TABLE_INGREDIENTE_PRODUCTO);
            stmt.execute(CREATE_TABLE_PEDIDO);
            stmt.execute(CREATE_TABLE_LINEAS_PEDIDO);
            System.out.println("Se han creado la tabla clientes, producto, alergeno");

        }
    }

    public static void dropAndCreateTables() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.execute(DROP_TABLE_LINEAS_PEDIDO);
            stmt.execute(DROP_TABLE_PEDIDO);
            stmt.execute(DROP_TABLE_INGREDIENTE_ALERGENO);
            stmt.execute(DROP_TABLE_INGREDIENTE_PRODUCTO);
            stmt.execute(DROP_TABLE_ALERGENO);
            stmt.execute(DROP_TABLE_INGREDIENTE);
            stmt.execute(DROP_TABLE_CLIENTES);
            stmt.execute(DROP_TABLE_PRODUCTO);

            System.out.println("Se han borrado las tablas clientes y producto");

            createTables();
        }
    }
}
