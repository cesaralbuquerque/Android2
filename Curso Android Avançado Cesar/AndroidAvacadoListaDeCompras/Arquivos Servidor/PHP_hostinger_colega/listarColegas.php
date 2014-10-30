<?php

/**
 *	phoneListarTelefones
  *	@author Ricardo Cherobin
 */

require_once "Model.php";

Class listarColegas extends Model {

		public function __construct() {
			parent::open_connection();
			parent::select_database();		 	
		}
 
 
		
		public function mostraDado(){
			$vals = Array();
			$select = parent::execute_query("SELECT * FROM colegas ORDER BY id ");		
			while ($row = parent::fetch_results($select)) {						 
						$vals[] = Array(
							'id' => $row['id'],
							'nome' => $row['nome'],
							'email' => $row['email'],
							'telefone' => $row['telefone'] 							
							);					
				}				
			parent::return_json($vals);						   
		}	
	}
	$Object = new listarColegas();	
  
 
   	
	$Object->mostraDado();
?>


