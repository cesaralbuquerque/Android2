<?php

/**
 *	 
  *	@author Ricardo Cherobin
 */

require_once "Model.php";

Class deletarProduto extends Model {

		public function __construct() {
			parent::open_connection();
			parent::select_database();		 	
		}
 
 
		
		public function deletarProduto($id){
		  //DELETE FROM table_name WHERE some_column = some_value
		 if(parent::execute_query("DELETE FROM listadecompras WHERE id='$id'")){
				  header('HTTP/1.1 200 ', true, 200);
				echo "ok";
			}else {
				  header('HTTP/1.1 500 ', true, 500);
			echo "error";
			}
		}	
	}
	$Object = new deletarProduto();	  
 
	 	
	$Object->deletarProduto($_POST['id']);
?>


