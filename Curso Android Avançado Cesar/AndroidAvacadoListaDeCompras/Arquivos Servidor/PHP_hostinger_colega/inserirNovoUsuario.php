<?php

/**
 *	 
  *	@author Ricardo Cherobin
 */

require_once "Model.php";

Class inserirNovoUsuario extends Model {

		public function __construct() {
			parent::open_connection();
			parent::select_database();		 	
		}
 
 
		
		public function inserirNovoUsuario($nome, $password){
		  	 
		 if(parent::execute_query("INSERT INTO usuario(nome,password) VALUES ('$nome', '$password')")){
				  header('HTTP/1.1 200 ', true, 200);
				echo "ok";
			}else {
				  header('HTTP/1.1 500 ', true, 500);
			echo "error";
			}
		}	
	}
	$Object = new inserirNovoUsuario();	  
 
	 	
	$Object->inserirNovoUsuario($_POST['nome'], $_POST['password']);
?>


