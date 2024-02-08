<?php

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $data = json_decode(file_get_contents('php://input'));

    $fila = $data->fila;
    $columna = $data->columna;

    session_start();
    $tablero = $_SESSION['tablero'];

    $valor = $tablero[$fila][$columna];

    // Si se revela una mina, terminar el juego y enviar la ubicación de todas las minas
    if ($valor === -1) {
        $minas = obtenerUbicacionMinas($tablero);
        echo json_encode([
            'valor' => $valor,
            'minas' => $minas
        ]);
    } else {
        echo json_encode([
            'valor' => $valor,
        ]);
    }
}

/**
 * Función para obtener la ubicación de todas las minas.
 *
 * @param array $tablero El tablero del juego.
 * @return array La ubicación de todas las minas.
 */
function obtenerUbicacionMinas($tablero) {
    $minas = [];
    foreach ($tablero as $fila => $valores) {
        foreach ($valores as $columna => $valor) {
            if ($valor === -1) {
                $minas[] = ['fila' => $fila, 'columna' => $columna];
            }
        }
    }
    return $minas;
}
?>
