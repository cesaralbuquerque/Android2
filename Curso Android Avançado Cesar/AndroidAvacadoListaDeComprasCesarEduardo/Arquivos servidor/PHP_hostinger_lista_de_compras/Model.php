<?php

/**
 *  Class with functions for database manipulation
 *	@author Ricardo Cherobin

 		$sql = "INSERT INTO colegas (txt_nome, txt_email, txt_telefone)VALUES ('$name','$email','$telefone')";
 */

require_once "Config.php";

Class Model extends Config {
    
    # Link connection to the database 
    private $link;
	
    /**
     *	Open the connection to the database and setting charset
	 *
	 *	@access public
	 *	@name open_connection()
     */
    public function open_connection() {
        $this->link = mysql_connect(parent::get_host(), parent::get_user(), parent::get_pass());

        # setting charset mysql 
        mysql_set_charset('utf8', $this->link);  
    }

    /**
     *	Select the database
	 *
	 *	@access public
	 *	@name select_database()
     */
    public function select_database() {
        mysql_select_db(parent::get_database());
    }

    /**
     *	Close the connection to the database
	 *
	 *	@access public
	 *	@name close_connection()
     */
    public function close_connection() {
        mysql_close($this->link);
    }

    /**
     *	Executes a sql and returns the result on success
	 *
	 *	@access public
	 *	@name execute_query()
     *	@param string
     *  @return result
     */
    public function execute_query($sql) {
        try {
            $result = mysql_query($sql);
            return $result;
        } catch (Exception $e) {
            return $e;
        }
    }

    /**
     *  Returns an associative array indexed or the result of a query
	 *
	 *	@access public
	 *	@name fetch_results()
     *	@param result 
     *  @return array
     */
    public function fetch_results($result) {
        return mysql_fetch_array($result, MYSQL_BOTH);
    }

    /**
     *  returns the number of rows from a query
	 *
	 *	@access public
	 *	@name get_num_rows()
     *	@param result
     *  @return int
     */
    public function get_num_rows($result) {
        return mysql_num_rows($result);
    }
	
	public function return_json($result) {
		 echo json_encode($result);		 
	}
	
	/**public function gera_chave_id($id) {
		$vals = Array();
		$validation_time = date("Y-m-d H:i:s" ,strtotime("+3 hours"));		
		$size = mcrypt_get_iv_size(MCRYPT_CAST_256, MCRYPT_MODE_CFB);
		$iv = mcrypt_create_iv($size, MCRYPT_DEV_RANDOM);	 
		$key = bin2hex($iv);	
		$this->execute_query("UPDATE colegas set 
		key_mobile ='".$key."',  validation_time = '".$validation_time."' where id like '$id'");
		$vals[] = Array('key' => $key);
		$this->	return_json($vals);	
	}*/
	public function gera_chave() {	
		$size = mcrypt_get_iv_size(MCRYPT_CAST_256, MCRYPT_MODE_CFB);
		$iv = mcrypt_create_iv($size, MCRYPT_DEV_RANDOM);	 
		$key = bin2hex($iv);
		return $key; 
	}
	
	
	
			
	public function validacaoIMEI($imei, $key, $id ) {	 	 
			$sql = "select * from telefones_acesso where device_id = '$imei'";            
			$result = $this->execute_query($sql);			 
				if ($result) {
					if ($this->get_num_rows($result) > 0){													
						return $this-> validaTokenHorario($key, $id );			
					}
				}			
	}
		
	public function validaTokenHorario($key, $id ) {	
 			$select = $this->execute_query("SELECT * FROM users_mobile where id = $id");			
						if ($select) {
							if ($this->get_num_rows($select) > 0) {									
								while ($row = $this->fetch_results($select)) {
										$key_mobile_banco = $row['key_mobile'];
										$validation_time_banco = $row['validation_time'];
										$time_finish = $row['time_finish'];										
										$time_now = date("Y-m-d H:i:s");
										$active = $row['active'];										
								}								
								if($key == $key_mobile_banco && $active==1){							
									if(strtotime($time_finish) < strtotime($time_now)){																	
										$logar = "logar";
										return  $logar;
									}else{										
										if(strtotime($time_now) > strtotime($validation_time_banco)){										
											$this->gera_chave_id($id);
											}else{																						
												$mostrar = "mostrar";
												return  $mostrar;
											}																						
										}										
									}										
							}
							
						}							 
		}
	
}

?>
