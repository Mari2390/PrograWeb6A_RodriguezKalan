<?php
// Verificar si la solicitud es de tipo POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
     // Decodificar los datos JSON recibidos
    $data = json_decode(file_get_contents('php://input'));

    // Obtener las coordenadas de la celda
    $fila = $data->fila;
    $columna = $data->columna;

     // Iniciar la sesion para acceder al tablero
    session_start();
    $tablero = $_SESSION['tablero'];

    // Obtener el valor de la celda en las coordenadas dadas
    $valor = $tablero[$fila][$columna];

    // Si se revela una mina, terminar el juego y enviar la ubicacion de todas las minas
    if ($valor === -1) {
        $minas = obtenerUbicacionMinas($tablero);
        echo json_encode([
            'valor' => $valor,
            'minas' => $minas
        ]);
    } else {
         // Si no es una mina, simplemente devolver el valor de la celda
        echo json_encode([
            'valor' => $valor,
        ]);
    }
}

/**
 * Función para obtener la ubicacion de todas las minas.
 *
 * @param array $tablero El tablero del juego.
 * @return array La ubicacion de todas las minas.
 */
function obtenerUbicacionMinas($tablero) {
    $minas = [];
    foreach ($tablero as $fila => $valores) {
        foreach ($valores as $columna => $valor) {
            if ($valor === -1) {
                 // Si el valor de la celda es -1, es una mina
                // Agregar la ubicacion al array de minas
                $minas[] = ['fila' => $fila, 'columna' => $columna];
            }
        }
    }
    return $minas;
}
?>
