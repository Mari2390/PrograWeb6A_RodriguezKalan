<?php
include 'Usuario.php';

$usuarioDAO = new UsuarioDAO();

$bugs = new Usuario();
$bugs->setNombres('Bugs');
$bugs->setApellidos('Bunny');
$bugs->setCorreo('bugsbunny@wb.com');
$bugs->guardar();

$lola = new Usuario();
$lola->setNombres('Lola');
$lola->setApellidos('Bunny');
$lola->setCorreo('lolabunny@wb.com');
$lola->guardar();

$lucas = new Usuario();
$lucas->setNombres('Daffy');
$lucas->setApellidos('Duck');
$lucas->setCorreo('patolucas@wb.com');
$lucas->guardar();

$porky = new Usuario();
$porky->setNombres('Porky');
$porky->setApellidos('Pig');
$porky->setCorreo('porkypigs@wb.com');
$porky->guardar();

$usuarios = $usuarioDAO->buscarTodos();

foreach ($usuarios as $usuario) {
    echo $usuario->getNombres() . ' ' . $usuario->getApellidos() . ' ' . $usuario->getCorreo() . '<br>';
}

echo'<br>';
$porky->setCorreo('porkypig@wb.com');
$porky->actualizar();

$bugs->eliminar();

$usuarios = $usuarioDAO->buscarTodos();
foreach ($usuarios as $usuario){
    echo $usuario ->getNombres(). ' ' . $usuario ->getApellidos(). ' ' . $usuario->getCorreo(). '<br>';
}

?>
