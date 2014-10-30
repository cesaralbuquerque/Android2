<?php

/**
 *	 
  *	@author Ricardo Cherobin
 */

require_once "Model.php";

Class inserirProduto extends Model {

		public function __construct() {
			parent::open_connection();
			parent::select_database();		 	
		}
 
 
		
		public function inserirProduto($descricao, $quantidade, $comprado){
		  //$sql = "INSERT INTO colegas (txt_nome, txt_email, txt_telefone)VALUES ('$name','$email','$telefone')";
		 if(parent::execute_query("INSERT INTO listadecompras(txt_descricao,int_quantidade,bool_comprado) VALUES ('$descricao', '$quantidade', '$comprado')")){
				  header('HTTP/1.1 200 ', true, 200);
				echo "ok";
			}else {
				  header('HTTP/1.1 500 ', true, 500);
			echo "error";
			}
		}	
	}
	$Object = new inserirProduto();	  
 
	 	
	$Object->inserirProduto($_POST['descricao'], $_POST['quantidade'], $_POST['comprado']);
?>


