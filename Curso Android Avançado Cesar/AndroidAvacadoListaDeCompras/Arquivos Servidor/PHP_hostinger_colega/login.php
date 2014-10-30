<?php

/**
 *	login
  *	@author Ricardo Cherobin
 */

require_once "Model.php";

Class login extends Model {
 
		
		public function __construct() {
			parent::open_connection();
			parent::select_database();		 	
		}
		public function check_user() {
			 
					 				
						$usernameGET = preg_replace('/[^[:alnum:]_]/', '',$_POST['nome']);	
						$passwordGET = preg_replace('/[^[:alnum:]_]/', '',$_POST['password']);	
						 				 
					    $select = parent::execute_query("SELECT * FROM usuarios where txt_login like '$usernameGET' and txt_senha like '$passwordGET'");		 
						 	 
								if ($select) {
									if (parent::get_num_rows($select) == 1) {	
										  header('HTTP/1.1 200 logado ', true, 200);					
									}else{
									
									  header('HTTP/1.1 500 login ou senha invalido ', true, 500);
									}
								}else {
								 header('HTTP/1.1 500 login ou senha invalido ', true, 500);
								}
				  
			 
			parent::close_connection();
	 }
		
	
	}
	$Object = new login();
	$Object->check_user();
?>
