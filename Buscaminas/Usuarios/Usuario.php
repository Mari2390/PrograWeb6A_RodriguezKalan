<?php

include 'UsuarioDAO.php';

class Usuario{
    private $id;
    private $nombres;
    private $apellidos;
    private $correo;

    public function __construct($id = null){
        if($id != null){
            $usuarioDAO = new UsuarioDAO();
            $usuario = $usuarioDAO->buscar($id);
            $this->id = $usuario[0]['id'];
            $this->nombres = $usuario [0] ['nombres'];
            $this->apellidos = $usuario[0]['apellidos'];
            $this->correo = $usuario[0]['correo'];
        }
    }

    //getters
    public function getId(){
        return $this->id;
    }

    public function getNombres(){
        return $this->nombres;
    }

    public function getIApellidos(){
        return $this->apellidos;
    }

    public function getCorreo(){
        return $this->correo;
    }

    //Setters
    public function setId(){
        $this->id;
    }

    public function setNombres(){
        $this->nombres;
    }

    public function setIApellidos(){
        $this->apellidos;
    }

    public function setCorreo(){
        $this->correo;
    }

    public function crear($datos) {
        $usuario = new Usuario();
        $usuario >setNombres($datos['nombres']);
        $usuario->setApellidos($datos['apellidos']);
        $usuario >setCorreo($datos['correo']);
        return $usuario->guardar();
    }
   
    public function actualizar() {
        $usuarioDAO = new UsuarioDAO();
        return $usuarioDAO >actualizar($this);
    }
   
    public function eliminar($id) {
        $usuarioDAO = new UsuarioDAO();
        return $usuarioDAO->eliminar($id);
    }
}