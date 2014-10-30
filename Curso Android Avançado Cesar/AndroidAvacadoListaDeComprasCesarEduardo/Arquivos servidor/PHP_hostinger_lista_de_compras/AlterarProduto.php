<?php

/**
 *	 
  *	@author Ricardo Cherobin
 */

require_once "Model.php";

Class alterarProduto extends Model {

		public function __construct() {
			parent::open_connection();
			parent::select_database();		 	
		}
 
 
		
		public function alterarProduto($id, $descricao, $quantidade, $comprado){
		  //$sql = "INSERT INTO colegas (txt_nome, txt_email, txt_telefone)VALUES ('$name','$email','$telefone')";
		 if(parent::execute_query("UPDATE listadecompras SET txt_descricao='$descricao', int_quantidade='$quantidade', bool_comprado='$comprado'  WHERE id='$id'")){
				  header('HTTP/1.1 200 ', true, 200);
				echo "ok";
			}else {
				  header('HTTP/1.1 500 ', true, 500);
			echo "error";
			}
		}	
	}
	$Object = new alterarProduto();	  
 
	 	
	$Object->alterarProduto($_POST['id'], $_POST['descricao'], $_POST['quantidade'], $_POST['comprado']);
?>


