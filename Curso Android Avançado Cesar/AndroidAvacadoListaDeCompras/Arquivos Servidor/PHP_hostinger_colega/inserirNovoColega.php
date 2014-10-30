<?php

/**
 *	 
  *	@author Ricardo Cherobin
 */

require_once "Model.php";

Class inserirNovoColega extends Model {

		public function __construct() {
			parent::open_connection();
			parent::select_database();		 	
		}
 
 
		
		public function inserirNovoColega($nome, $email, $telefone){
		  //$sql = "INSERT INTO colegas (txt_nome, txt_email, txt_telefone)VALUES ('$name','$email','$telefone')";
		 if(parent::execute_query("INSERT INTO colegas(txt_nome,txt_email,txt_telefone) VALUES ('$name', '$email', '$telefone')")){
				  header('HTTP/1.1 200 ', true, 200);
				echo "ok";
			}else {
				  header('HTTP/1.1 500 ', true, 500);
			echo "error";
			}
		}	
	}
	$Object = new inserirNovoColega();	  
 
	 	
	$Object->inserirNovoUsuario($_POST['nome'], $_POST['telefone']);
?>


