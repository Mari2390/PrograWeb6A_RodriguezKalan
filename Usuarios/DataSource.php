<?php

include 'EnvLoader.php';

class DataSource {
  private $conexion;
  private $cadenaParaConexion;
  /**
   * Inicia la conexion con la base de datos
   */
  public function __construct() {
    try {
      $host = EnvLoader::get('DB_HOST');
      $dbname = EnvLoader::get('DB_DATABASE');
      $user = EnvLoader::get('DB_USERNAME');
      $password = EnvLoader::get('DB_PASSWORD');

      $this->cadenaParaConexion = "mysql:host=$host;dbname=$dbname";
      $this->conexion = new PDO($this->cadenaParaConexion, $user, $password);
    } catch (PDOException $e) {
      echo "Error: " . $e->getMessage();
    }
  }
  /**
   * Permite realizar traer un registro de la base de datos
   *
   * @param string $sql   Sentencia SQL
   * @param array $values Valores para la consulta
   * @return $tabla_datos Devuelve un registro de la base de datos
   */
  public function ejecutarConsulta($sql = "", $values = []) {
    if ($sql != "") {
      $consulta = $this->conexion->prepare($sql);
      $consulta->execute($values);
      return $consulta->fetchAll(PDO::FETCH_ASSOC);
    } else {
      return 0;
    }
  }

  /**
   * Permite obtener un entero de las tablas afectadas,
   * 0 indica que no se realizo ninguna accion
   *
   * @param string $sql                Sentencia SQL 
   * @param array $values              Valores para la consulta
   * @return $numero_tablas_afectadas  Devuelve un entero de las tablas afectadas
   */
  public function ejecutarActualizacion ($sql = "", $values = []) {
    if ($sql != "") {
      $consulta = $this->conexion->prepare($sql);
      $consulta->execute($values);
      return $consulta->rowCount();
    } else {
      return 0;
    }
  }

   // Retorna el ID del último registro insertado
   public function lastInsertId() {
    return $this->conexion->lastInsertId();
}
}