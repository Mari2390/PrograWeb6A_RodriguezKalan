<?php
function Juego($j1, $j2){

    $Valores = [0, 1, 2, 3, 4]; //Valores permitidos para los jugadores

    //Verifica los valores permitidos en un array. ! es para negar el resultado, true si el valor no existe y false, lo contrario 
    if(!in_array($j1, $Valores) || !in_array($j2, $Valores)){ 
        echo "Error: Los valores no estan dentro del parametro permitido. Escriba del 0 al 4";
        return;
    }

    $Opciones = ["Piedra", "Papel", "Tijera", "Lagarto", "Spock"];
    echo "Jugador 1: " . $Opciones[$j1] . "\n";
    echo "Jugador 2: " . $Opciones[$j2] . "\n";

    //Se crea un array para las reglas del juego
    $reglas = [
        0 => [2, 3],    // Piedra vence a Tijera y Lagarto
        1 => [0, 4],    // Papel vence a Piedra y Spock
        2 => [1, 3],    // Tijera vence a Papel y Lagarto
        3 => [1, 4],    // Lagarto vence a Papel y Spock
        4 => [0, 2]     // Spock vence a Piedra y Tijera
    ];

    //Logica del juego usando la matriz de las reglas del juego
    if ($j1 == $j2) {
        echo "Empate\n";
        //Verifica si la opcion del jugador 2 esta en el rango permitido que vence al jugador 1
    } elseif (in_array($j2, $reglas[$j1])) { 
        echo "Jugador 1 gana\n";
    } else { //De lo contrario ganas el jugador 2
        echo "Jugador 2 gana\n";
    }
}

// Ejemplo de uso. Para dar valores a los parametros
if (isset($argv[1], $argv[2])) { //isset: se utiliza para asegurarse de que los argumentos esten definidos antes de usar
    //El intval convierte los argumentos en entero. Es para ser tratado como numero y no como cadena de texto
    $j1 = intval($argv[1]); 
    $j2 = intval($argv[2]);
    Juego($j1, $j2); //Llama a la funcion con los dos argumentos convertidos
} else {
    echo "Por favor, ingrese dos numeros como argumento.\n";
}
