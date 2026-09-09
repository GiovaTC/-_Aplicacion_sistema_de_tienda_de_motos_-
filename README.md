# -_Aplicacion_sistema_de_tienda_de_motos_- :.

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/04782aea-cf31-4773-9a9f-50a9ee84a64d" />  

# 🏍️ Tienda de Motos:

Aplicación de escritorio desarrollada en **Java 21**, utilizando **IntelliJ IDEA**, **Maven**, **JDBC** y **Oracle Database 19c**.

El proyecto implementa una arquitectura **por capas**, separando la conexión a la base de datos, los modelos, acceso a datos, lógica de negocio y la aplicación principal.

---

## 📋 Características

La aplicación permite administrar:

* 🏍️ Motos
* 👤 Clientes
* 🧾 Ventas
* 📦 Inventario
* 💰 Total de ventas
* 🔎 Consultas de información
* ✏️ Actualización de registros
* 🗑️ Eliminación de registros

---

# 🛠️ Tecnologías

| Tecnología      | Versión     |
| --------------- | ----------- |
| Java            | 21          |
| IntelliJ IDEA   | 2023+       |
| Maven           | 3.9+        |
| Oracle Database | 19c         |
| JDBC            | Oracle JDBC |
| Arquitectura    | Por capas   |

---

# 📁 Estructura del proyecto

```text
TiendaMotos/
│
├── pom.xml
│
├── README.md
│
└── src/
    └── main/
        └── java/
            └── com/
                └── tiendamotos/
                    │
                    ├── app/
                    │   └── Main.java
                    │
                    ├── config/
                    │   └── ConexionBD.java
                    │
                    ├── modelo/
                    │   ├── Moto.java
                    │   ├── Cliente.java
                    │   ├── Venta.java
                    │   └── DetalleVenta.java
                    │
                    ├── dao/
                    │   ├── MotoDAO.java
                    │   ├── ClienteDAO.java
                    │   ├── VentaDAO.java
                    │   └── DetalleVentaDAO.java
                    │
                    └── servicio/
                        ├── MotoService.java
                        ├── ClienteService.java
                        └── VentaService.java
```

---

# 🗄️ 1. Base de datos Oracle 19c

## Crear las tablas

Ejecutar el siguiente script en **Oracle SQL Developer**.

```sql
-- =====================================================
-- BASE DE DATOS TIENDA DE MOTOS
-- ORACLE DATABASE 19c
-- =====================================================

-- =====================================================
-- ELIMINAR TABLAS SI EXISTEN
-- =====================================================

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE DETALLE_VENTA CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE VENTAS CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE MOTOS CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE CLIENTES CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

-- =====================================================
-- ELIMINAR SEQUENCES
-- =====================================================

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_CLIENTES';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_MOTOS';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_VENTAS';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_DETALLE_VENTA';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

-- =====================================================
-- TABLA CLIENTES
-- =====================================================

CREATE TABLE CLIENTES (
    ID_CLIENTE NUMBER PRIMARY KEY,
    NOMBRE VARCHAR2(50) NOT NULL,
    APELLIDO VARCHAR2(50) NOT NULL,
    DOCUMENTO VARCHAR2(20) UNIQUE NOT NULL,
    TELEFONO VARCHAR2(20),
    CORREO VARCHAR2(100)
);

-- =====================================================
-- TABLA MOTOS
-- =====================================================

CREATE TABLE MOTOS (
    ID_MOTO NUMBER PRIMARY KEY,
    MARCA VARCHAR2(50) NOT NULL,
    MODELO VARCHAR2(50) NOT NULL,
    CILINDRADA NUMBER(5) NOT NULL,
    ANIO NUMBER(4) NOT NULL,
    PRECIO NUMBER(12,2) NOT NULL,
    STOCK NUMBER DEFAULT 0 NOT NULL
);

-- =====================================================
-- TABLA VENTAS
-- =====================================================

CREATE TABLE VENTAS (
    ID_VENTA NUMBER PRIMARY KEY,
    ID_CLIENTE NUMBER NOT NULL,
    FECHA_VENTA DATE DEFAULT SYSDATE NOT NULL,
    TOTAL NUMBER(12,2) DEFAULT 0 NOT NULL,

    CONSTRAINT FK_VENTA_CLIENTE
        FOREIGN KEY (ID_CLIENTE)
        REFERENCES CLIENTES(ID_CLIENTE)
);

-- =====================================================
-- TABLA DETALLE_VENTA
-- =====================================================

CREATE TABLE DETALLE_VENTA (
    ID_DETALLE NUMBER PRIMARY KEY,
    ID_VENTA NUMBER NOT NULL,
    ID_MOTO NUMBER NOT NULL,
    CANTIDAD NUMBER NOT NULL,
    PRECIO NUMBER(12,2) NOT NULL,
    SUBTOTAL NUMBER(12,2) NOT NULL,

    CONSTRAINT FK_DETALLE_VENTA
        FOREIGN KEY (ID_VENTA)
        REFERENCES VENTAS(ID_VENTA),

    CONSTRAINT FK_DETALLE_MOTO
        FOREIGN KEY (ID_MOTO)
        REFERENCES MOTOS(ID_MOTO)
);

-- =====================================================
-- SEQUENCES
-- =====================================================

CREATE SEQUENCE SEQ_CLIENTES
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE SEQ_MOTOS
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE SEQ_VENTAS
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE SEQ_DETALLE_VENTA
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- =====================================================
-- DATOS DE PRUEBA - CLIENTES
-- =====================================================

INSERT INTO CLIENTES
(ID_CLIENTE, NOMBRE, APELLIDO, DOCUMENTO, TELEFONO, CORREO)
VALUES
(SEQ_CLIENTES.NEXTVAL, 'Carlos', 'Rodriguez',
 '1001001001', '3001112233', 'carlos@gmail.com');

INSERT INTO CLIENTES
(ID_CLIENTE, NOMBRE, APELLIDO, DOCUMENTO, TELEFONO, CORREO)
VALUES
(SEQ_CLIENTES.NEXTVAL, 'Andres', 'Martinez',
 '1001001002', '3002223344', 'andres@gmail.com');

INSERT INTO CLIENTES
(ID_CLIENTE, NOMBRE, APELLIDO, DOCUMENTO, TELEFONO, CORREO)
VALUES
(SEQ_CLIENTES.NEXTVAL, 'Laura', 'Gomez',
 '1001001003', '3003334455', 'laura@gmail.com');

-- =====================================================
-- DATOS DE PRUEBA - MOTOS
-- =====================================================

INSERT INTO MOTOS
(ID_MOTO, MARCA, MODELO, CILINDRADA, ANIO, PRECIO, STOCK)
VALUES
(SEQ_MOTOS.NEXTVAL, 'Yamaha', 'MT-03', 321, 2025, 28000000, 5);

INSERT INTO MOTOS
(ID_MOTO, MARCA, MODELO, CILINDRADA, ANIO, PRECIO, STOCK)
VALUES
(SEQ_MOTOS.NEXTVAL, 'Honda', 'CB190R', 184, 2025, 18500000, 8);

INSERT INTO MOTOS
(ID_MOTO, MARCA, MODELO, CILINDRADA, ANIO, PRECIO, STOCK)
VALUES
(SEQ_MOTOS.NEXTVAL, 'Suzuki', 'GSX-S150', 147, 2024, 16500000, 6);

INSERT INTO MOTOS
(ID_MOTO, MARCA, MODELO, CILINDRADA, ANIO, PRECIO, STOCK)
VALUES
(SEQ_MOTOS.NEXTVAL, 'Kawasaki', 'Ninja 400', 399, 2025, 35000000, 3);

INSERT INTO MOTOS
(ID_MOTO, MARCA, MODELO, CILINDRADA, ANIO, PRECIO, STOCK)
VALUES
(SEQ_MOTOS.NEXTVAL, 'BMW', 'G 310 R', 313, 2025, 32000000, 4);

COMMIT;

-- =====================================================
-- CONSULTAS DE PRUEBA
-- =====================================================

SELECT * FROM CLIENTES;

SELECT * FROM MOTOS;

SELECT * FROM VENTAS;

SELECT * FROM DETALLE_VENTA;
```

---

# ☕ 2. Crear proyecto Maven

En IntelliJ IDEA:

```text
File
 ↓
New
 ↓
Project
 ↓
Maven
```

Seleccionar:

```text
JDK: 21
```

Nombre:

```text
TiendaMotos
```

---

# 📦 3. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="
         http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.tiendamotos</groupId>

    <artifactId>TiendaMotos</artifactId>

    <version>1.0-SNAPSHOT</version>

    <properties>

        <maven.compiler.source>21</maven.compiler.source>

        <maven.compiler.target>21</maven.compiler.target>

        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

    </properties>

    <dependencies>

        <!-- Oracle JDBC -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>23.3.0.23.09</version>
        </dependency>

    </dependencies>

</project>
```

> `ojdbc11` es compatible con aplicaciones Java modernas y permite trabajar con Oracle 19c.

---

# 🔌 4. Conexión a Oracle

Archivo:

```text
config/ConexionBD.java
```

```java
package com.tiendamotos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521/orcl";

    private static final String USUARIO =
            "SYSTEM";

    private static final String PASSWORD =
            "TU_PASSWORD";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }

    public static void cerrar(Connection connection) {

        if (connection != null) {

            try {

                connection.close();

            } catch (SQLException e) {

                System.out.println(
                        "Error cerrando conexión: "
                                + e.getMessage()
                );
            }
        }
    }
}
```

### ⚠️ Importante

Cambiar:

```java
private static final String PASSWORD =
        "TU_PASSWORD";
```

por la contraseña real del usuario Oracle.

Si utilizas otro usuario:

```java
private static final String USUARIO =
        "TIENDAMOTOS";
```

---

# 🏍️ 5. Modelo Moto

Archivo:

```text
modelo/Moto.java
```

```java
package com.tiendamotos.modelo;

public class Moto {

    private int idMoto;
    private String marca;
    private String modelo;
    private int cilindrada;
    private int anio;
    private double precio;
    private int stock;

    public Moto() {
    }

    public Moto(
            int idMoto,
            String marca,
            String modelo,
            int cilindrada,
            int anio,
            double precio,
            int stock) {

        this.idMoto = idMoto;
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.anio = anio;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdMoto() {
        return idMoto;
    }

    public void setIdMoto(int idMoto) {
        this.idMoto = idMoto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {

        return "Moto{" +
                "idMoto=" + idMoto +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cilindrada=" + cilindrada +
                ", anio=" + anio +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
```

---

# 👤 6. Modelo Cliente

Archivo:

```text
modelo/Cliente.java
```

```java
package com.tiendamotos.modelo;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String apellido;
    private String documento;
    private String telefono;
    private String correo;

    public Cliente() {
    }

    public Cliente(
            int idCliente,
            String nombre,
            String apellido,
            String documento,
            String telefono,
            String correo) {

        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {

        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", documento='" + documento + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
```

---

# 🏍️ 7. MotoDAO

Archivo:

```text
dao/MotoDAO.java
```

```java
package com.tiendamotos.dao;

import com.tiendamotos.config.ConexionBD;
import com.tiendamotos.modelo.Moto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoDAO {

    public void insertar(Moto moto) {

        String sql = """
                INSERT INTO MOTOS
                (
                    ID_MOTO,
                    MARCA,
                    MODELO,
                    CILINDRADA,
                    ANIO,
                    PRECIO,
                    STOCK
                )
                VALUES
                (
                    SEQ_MOTOS.NEXTVAL,
                    ?, ?, ?, ?, ?, ?
                )
                """;

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, moto.getMarca());
            statement.setString(2, moto.getModelo());
            statement.setInt(3, moto.getCilindrada());
            statement.setInt(4, moto.getAnio());
            statement.setDouble(5, moto.getPrecio());
            statement.setInt(6, moto.getStock());

            statement.executeUpdate();

            System.out.println(
                    "Moto registrada correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error registrando moto: "
                            + e.getMessage()
            );
        }
    }

    public List<Moto> listar() {

        List<Moto> motos = new ArrayList<>();

        String sql = """
                SELECT
                    ID_MOTO,
                    MARCA,
                    MODELO,
                    CILINDRADA,
                    ANIO,
                    PRECIO,
                    STOCK
                FROM MOTOS
                ORDER BY ID_MOTO
                """;

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Moto moto = new Moto();

                moto.setIdMoto(
                        result.getInt("ID_MOTO")
                );

                moto.setMarca(
                        result.getString("MARCA")
                );

                moto.setModelo(
                        result.getString("MODELO")
                );

                moto.setCilindrada(
                        result.getInt("CILINDRADA")
                );

                moto.setAnio(
                        result.getInt("ANIO")
                );

                moto.setPrecio(
                        result.getDouble("PRECIO")
                );

                moto.setStock(
                        result.getInt("STOCK")
                );

                motos.add(moto);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error consultando motos: "
                            + e.getMessage()
            );
        }

        return motos;
    }

    public void actualizar(Moto moto) {

        String sql = """
                UPDATE MOTOS
                SET
                    MARCA = ?,
                    MODELO = ?,
                    CILINDRADA = ?,
                    ANIO = ?,
                    PRECIO = ?,
                    STOCK = ?
                WHERE ID_MOTO = ?
                """;

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, moto.getMarca());
            statement.setString(2, moto.getModelo());
            statement.setInt(3, moto.getCilindrada());
            statement.setInt(4, moto.getAnio());
            statement.setDouble(5, moto.getPrecio());
            statement.setInt(6, moto.getStock());
            statement.setInt(7, moto.getIdMoto());

            int filas = statement.executeUpdate();

            if (filas > 0) {

                System.out.println(
                        "Moto actualizada correctamente."
                );

            } else {

                System.out.println(
                        "No se encontró la moto."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error actualizando moto: "
                            + e.getMessage()
            );
        }
    }

    public void eliminar(int idMoto) {

        String sql =
                "DELETE FROM MOTOS WHERE ID_MOTO = ?";

        try (Connection connection = ConexionBD.conectar();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, idMoto);

            int filas = statement.executeUpdate();

            if (filas > 0) {

                System.out.println(
                        "Moto eliminada correctamente."
                );

            } else {

                System.out.println(
                        "No se encontró la moto."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error eliminando moto: "
                            + e.getMessage()
            );
        }
    }
}
```

---

# ⚙️ 8. MotoService

Archivo:

```text
servicio/MotoService.java
```

```java
package com.tiendamotos.servicio;

import com.tiendamotos.dao.MotoDAO;
import com.tiendamotos.modelo.Moto;

import java.util.List;

public class MotoService {

    private final MotoDAO motoDAO;

    public MotoService() {

        motoDAO = new MotoDAO();
    }

    public void registrarMoto(Moto moto) {

        if (moto.getMarca() == null ||
                moto.getMarca().isBlank()) {

            System.out.println(
                    "La marca es obligatoria."
            );

            return;
        }

        if (moto.getModelo() == null ||
                moto.getModelo().isBlank()) {

            System.out.println(
                    "El modelo es obligatorio."
            );

            return;
        }

        if (moto.getPrecio() <= 0) {

            System.out.println(
                    "El precio debe ser mayor que cero."
            );

            return;
        }

        if (moto.getStock() < 0) {

            System.out.println(
                    "El stock no puede ser negativo."
            );

            return;
        }

        motoDAO.insertar(moto);
    }

    public List<Moto> listarMotos() {

        return motoDAO.listar();
    }

    public void actualizarMoto(Moto moto) {

        motoDAO.actualizar(moto);
    }

    public void eliminarMoto(int idMoto) {

        motoDAO.eliminar(idMoto);
    }
}
```

---

# 🖥️ 9. Main.java

Archivo:

```text
app/Main.java
```

```java
package com.tiendamotos.app;

import com.tiendamotos.modelo.Moto;
import com.tiendamotos.servicio.MotoService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final MotoService motoService =
            new MotoService();

    public static void main(String[] args) {

        int opcion;

        do {

            mostrarMenu();

            opcion = leerEntero(
                    "Seleccione una opción: "
            );

            switch (opcion) {

                case 1 -> registrarMoto();

                case 2 -> listarMotos();

                case 3 -> actualizarMoto();

                case 4 -> eliminarMoto();

                case 0 ->
                        System.out.println(
                                "Programa finalizado."
                        );

                default ->
                        System.out.println(
                                "Opción inválida."
                        );
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void mostrarMenu() {

        System.out.println();
        System.out.println(
                "======================================"
        );
        System.out.println(
                "       🏍️ TIENDA DE MOTOS"
        );
        System.out.println(
                "======================================"
        );
        System.out.println(
                "1. Registrar moto"
        );
        System.out.println(
                "2. Listar motos"
        );
        System.out.println(
                "3. Actualizar moto"
        );
        System.out.println(
                "4. Eliminar moto"
        );
        System.out.println(
                "0. Salir"
        );
        System.out.println(
                "======================================"
        );
    }

    private static void registrarMoto() {

        System.out.println();
        System.out.println("=== REGISTRAR MOTO ===");

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();

        int cilindrada =
                leerEntero("Cilindrada: ");

        int anio =
                leerEntero("Año: ");

        double precio =
                leerDouble("Precio: ");

        int stock =
                leerEntero("Stock: ");

        Moto moto = new Moto(
                0,
                marca,
                modelo,
                cilindrada,
                anio,
                precio,
                stock
        );

        motoService.registrarMoto(moto);
    }

    private static void listarMotos() {

        System.out.println();
        System.out.println("=== LISTADO DE MOTOS ===");

        List<Moto> motos =
                motoService.listarMotos();

        if (motos.isEmpty()) {

            System.out.println(
                    "No existen motos registradas."
            );

            return;
        }

        for (Moto moto : motos) {

            System.out.println(
                    "ID: " + moto.getIdMoto()
            );

            System.out.println(
                    "Marca: " + moto.getMarca()
            );

            System.out.println(
                    "Modelo: " + moto.getModelo()
            );

            System.out.println(
                    "Cilindrada: "
                            + moto.getCilindrada()
                            + " cc"
            );

            System.out.println(
                    "Año: " + moto.getAnio()
            );

            System.out.println(
                    "Precio: $"
                            + moto.getPrecio()
            );

            System.out.println(
                    "Stock: " + moto.getStock()
            );

            System.out.println(
                    "----------------------------------"
            );
        }
    }

    private static void actualizarMoto() {

        System.out.println();
        System.out.println("=== ACTUALIZAR MOTO ===");

        int id =
                leerEntero("ID de la moto: ");

        System.out.print("Nueva marca: ");
        String marca = scanner.nextLine();

        System.out.print("Nuevo modelo: ");
        String modelo = scanner.nextLine();

        int cilindrada =
                leerEntero("Nueva cilindrada: ");

        int anio =
                leerEntero("Nuevo año: ");

        double precio =
                leerDouble("Nuevo precio: ");

        int stock =
                leerEntero("Nuevo stock: ");

        Moto moto = new Moto(
                id,
                marca,
                modelo,
                cilindrada,
                anio,
                precio,
                stock
        );

        motoService.actualizarMoto(moto);
    }

    private static void eliminarMoto() {

        System.out.println();
        System.out.println("=== ELIMINAR MOTO ===");

        int id =
                leerEntero("ID de la moto: ");

        motoService.eliminarMoto(id);
    }

    private static int leerEntero(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                int valor =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

                return valor;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingrese un número válido."
                );
            }
        }
    }

    private static double leerDouble(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                double valor =
                        Double.parseDouble(
                                scanner.nextLine()
                        );

                return valor;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingrese un número válido."
                );
            }
        }
    }
}
```

---

# 🧪 10. Prueba de conexión

Antes de ejecutar todo el CRUD, se recomienda comprobar que Oracle está funcionando.

La conexión utilizada es:

```text
jdbc:oracle:thin:@localhost:1521/orcl
```

Ejemplo:

```text
Host: localhost
Puerto: 1521
Service Name: orcl
Usuario: SYSTEM
Password: ********
```

Si tu Oracle utiliza otro `SERVICE_NAME`, debes modificar:

```java
jdbc:oracle:thin:@localhost:1521/orcl
```

---

# ▶️ 11. Ejecutar el proyecto

Desde IntelliJ:

```text
Main.java
    ↓
Run 'Main.main()'
```

Deberá aparecer:

```text
======================================
       🏍️ TIENDA DE MOTOS
======================================
1. Registrar moto
2. Listar motos
3. Actualizar moto
4. Eliminar moto
0. Salir
======================================
Seleccione una opción:
```

---

# 🧪 12. Ejemplo de registro

Seleccionar:

```text
1
```

Ingresar:

```text
Marca: Yamaha
Modelo: MT-07
Cilindrada: 689
Año: 2025
Precio: 42000000
Stock: 3
```

Resultado:

```text
Moto registrada correctamente.
```

---

# 📋 13. Listar motos

Seleccionar:

```text
2
```

Ejemplo:

```text
=== LISTADO DE MOTOS ===

ID: 1
Marca: Yamaha
Modelo: MT-03
Cilindrada: 321 cc
Año: 2025
Precio: $2.8E7
Stock: 5
----------------------------------

ID: 2
Marca: Honda
Modelo: CB190R
Cilindrada: 184 cc
Año: 2025
Precio: $1.85E7
Stock: 8
----------------------------------
```

---

# 🏗️ 14. Arquitectura por capas

El proyecto utiliza el siguiente patrón:

```text
┌───────────────────────────┐
│           APP             │
│         Main.java         │
└─────────────┬─────────────┘
              │
              ▼
┌───────────────────────────┐
│        SERVICIO           │
│      MotoService          │
└─────────────┬─────────────┘
              │
              ▼
┌───────────────────────────┐
│           DAO             │
│         MotoDAO           │
└─────────────┬─────────────┘
              │
              ▼
┌───────────────────────────┐
│       CONEXIÓN JDBC       │
│       ConexionBD          │
└─────────────┬─────────────┘
              │
              ▼
┌───────────────────────────┐
│       ORACLE 19c          │
│                           │
│ CLIENTES                  │
│ MOTOS                     │
│ VENTAS                    │
│ DETALLE_VENTA             │
└───────────────────────────┘
```

---

# 📌 Responsabilidad de cada capa

## Modelo

Contiene las clases que representan las entidades de la base de datos.

```text
Moto
Cliente
Venta
DetalleVenta
```

No contiene consultas SQL.

---

## DAO

El DAO significa:

```text
Data Access Object
```

Su función es comunicarse directamente con Oracle.

Ejemplo:

```java
INSERT
SELECT
UPDATE
DELETE
```

---

## Servicio

La capa `service` contiene las reglas de negocio.

Ejemplo:

```java
if (moto.getPrecio() <= 0) {
    System.out.println("Precio inválido");
}
```

El DAO no debería encargarse de estas validaciones.

---

## App

Contiene el programa principal y el menú.

```text
Main.java
```

Esta capa recibe los datos del usuario y llama a los servicios.

---

# 🔐 Recomendación de seguridad

Para un proyecto académico se puede utilizar:

```java
private static final String PASSWORD =
        "TU_PASSWORD";
```

Sin embargo, en un proyecto real no se recomienda guardar las credenciales directamente en el código.

Una futura mejora sería utilizar:

```text
application.properties
```

o variables de entorno.

---

# 🚀 Próximas mejoras

La primera versión implementa la estructura principal y el CRUD de motos.

Las siguientes versiones pueden agregar:

### 👤 Gestión de clientes

```text
1. Registrar cliente
2. Listar clientes
3. Buscar cliente
4. Actualizar cliente
5. Eliminar cliente
```

### 🧾 Gestión de ventas

```text
1. Crear venta
2. Agregar motos
3. Calcular subtotal
4. Calcular total
5. Actualizar stock
6. Consultar venta
```

### 📊 Reportes

```text
Ventas del día
Ventas por cliente
Motos más vendidas
Motos sin stock
Inventario
Total vendido
```

### 🖥️ Interfaz gráfica

Como evolución del proyecto se puede reemplazar el menú de consola por:

```text
JavaFX
   │
   ├── Login
   ├── Dashboard
   ├── Motos
   ├── Clientes
   ├── Ventas
   └── Reportes
```

---

# 👨‍💻 Autor

Proyecto académico desarrollado con:

```text
Java 21
IntelliJ IDEA
Oracle Database 19c
JDBC
Maven
```

---

# 📄 Licencia

Proyecto educativo para fines de aprendizaje y práctica de desarrollo de aplicaciones Java con Oracle Database.
