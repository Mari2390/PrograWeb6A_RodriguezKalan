<?php
class Buscaminas {
    private $filas;
    private $columnas;
    private $minas;

    // Constructor de la clase Buscaminas
    public function __construct($nivel) {
        // Definir las opciones de acuerdo al nivel seleccionado
        switch ($nivel) {
            case 'facil':
                $this->filas = 8;
                $this->columnas = 8;
                $this->minas = 10;
                break;
            case 'medio':
                $this->filas = 16;
                $this->columnas = 16;
                $this->minas = 40;
                break;
            case 'dificil':
                $this->filas = 16;
                $this->columnas = 30;
                $this->minas = 99;
                break;
            default:
                // Opcion por defecto si el nivel no es reconocido
                $this->filas = 8;
                $this->columnas = 8;
                $this->minas = 10;
                break;
        }
    }

    // Metodo para generar el tablero del juego
    public function generarTablero() {
        // Inicializar el tablero con celdas vacias
        $tablero = array_fill(0, $this->filas, array_fill(0, $this->columnas, 0));

        // Colocar las minas en posiciones aleatorias
        for ($i = 0; $i < $this->minas; $i++) {
            do {
                $fila = rand(0, $this->filas - 1);
                $columna = rand(0, $this->columnas - 1);
            } while ($tablero[$fila][$columna] == -1);

            // Colocar la mina en la posicion aleatoria
            $tablero[$fila][$columna] = -1;

            // Incrementar los valores alrededor de la mina
            for ($j = $fila - 1; $j <= $fila + 1; $j++) {
                for ($k = $columna - 1; $k <= $columna + 1; $k++) {
                    if ($j >= 0 && $j < $this->filas && $k >= 0 && $k < $this->columnas && $tablero[$j][$k] !== -1) {
                        $tablero[$j][$k]++;
                    }
                }
            }
        }

        return $tablero;
    }
}

// Verificar si la solicitud es de tipo POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Decodificar los datos JSON recibidos
    $data = json_decode(file_get_contents('php://input'));

    // Obtener el nivel seleccionado desde los datos
    $nivel = $data->nivel;

    // Crear una instancia de la clase Buscaminas con el nivel seleccionado
    $buscaminas = new Buscaminas($nivel);

    // Generar el tablero del Buscaminas
    $tablero = $buscaminas->generarTablero();
    
    // Iniciar la sesion para guardar el tablero y hacerlo disponible en el siguiente script
    session_start();
    $_SESSION['tablero'] = $tablero;

    // Devolver una respuesta JSON con el nivel y el tablero generado
    echo json_encode([
        'nivel' => $nivel,
        'table' => $tablero,
    ]);
}
?>


 
    
        

    
         
      

