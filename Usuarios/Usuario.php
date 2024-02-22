<?php

include 'UsuarioDAO.php';

class Usuario{
    private $id;
    private $nombres;
    private $apellidos;
    private $correo;
    private $usuarioDAO; //Aqui implementamos una propiedad

    public function __construct($id = null){
        $this->usuarioDAO = new UsuarioDAO(); // Inicializamos el DAO en el constructor
        if($id != null){
            $usuario = $this->usuarioDAO->buscar($id);
            $this->id = $usuario[0]['id'];
            $this->nombres = $usuario[0]['nombres'];
            $this->apellidos = $usuario[0]['apellidos'];
            $this->correo = $usuario[0]['correo'];
        }
    }

    //getters
    public function getNombres(){
        return $this->nombres;
    }

    public function getApellidos(){
        return $this->apellidos;
    }

    public function getCorreo(){
        return $this->correo;
    }

    public function getId(){
        return $this->id;
    }

    //Setters
    public function setNombres($nombres){
        $this->nombres = $nombres;
    }

    public function setApellidos($apellidos){
        $this->apellidos = $apellidos;
    }

    public function setCorreo($correo){
        $this->correo = $correo;
    }

    public function setId($id){
        $this->id = $id;
    }

    public function crear($datos){
        $usuario = new Usuario(); 
        $usuario->setNombres($datos['nombres']);
        $usuario->setApellidos($datos['apellidos']);
        $usuario->setCorreo($datos['correo']);
        return $usuario->guardar();
    }

    public function actualizar(){
        return $this->usuarioDAO->actualizar($this);
    }
    
    public function eliminar(){
        return $this->usuarioDAO->eliminar($this->id);
    }

    public function guardar(){
        $id = $this->usuarioDAO->insertar($this);
        $this->setId($id); // Establece el ID del objeto Usuario
        return $id;
    }
}