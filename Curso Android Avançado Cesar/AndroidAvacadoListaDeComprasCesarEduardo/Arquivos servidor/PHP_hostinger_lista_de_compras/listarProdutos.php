<?php

/**
 *	listarCompras
  *	@author Ricardo Cherobin
 */

require_once "Model.php";

Class listarCompras extends Model {

		public function __construct() {
			parent::open_connection();
			parent::select_database();		 	
		}
 
 
		
		public function mostraDado(){
			$vals = Array();
			$select = parent::execute_query("SELECT * FROM listadecompras ORDER BY id ");		
			while ($row = parent::fetch_results($select)) {						 
						$vals[] = Array(
							'id' => $row['id'],
							'descricao' => $row['txt_descricao'],
							'quantidade' => $row['int_quantidade'],
							'comprado' => $row['bool_comprado']  							
							);					
				}				
			parent::return_json($vals);						   
		}	
	}
	$Object = new listarCompras();	
  
 
   	
	$Object->mostraDado();
?>


