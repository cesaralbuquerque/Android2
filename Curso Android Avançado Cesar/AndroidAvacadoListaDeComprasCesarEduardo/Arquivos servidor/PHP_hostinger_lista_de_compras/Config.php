<?php

/**
 *	 Class configuration panel
 *	@author Ricardo Cherobin
 
 mysqli_connect("mysql.hostinger.com.br","u739145095_admin","floripa","u739145095_coleg");
 */

Class Config {

	# Your mysql user
    private $user = "u739145095_admin";

	# Your mysql password
    private $pass = "floripa";

	# Your mysql server host
    private $host = "mysql.hostinger.com.br";

	# Your mysql database
    private $database = "u739145095_coleg";

    /**
     *  Return the user mysql
	 *
     *  @access public
     *  @name get_user()
     *  @return string
     */
    public function get_user() {
        return $this->user;
    }

    /**
     *  Return the password mysql
	 *
     *  @access public
     *  @name get_pass()
     *  @return string
     */
    public function get_pass() {
        return $this->pass;
    }
    
    /**
     *  Return the host server mysql
	 *
     *  @access public
     *  @name get_host()
     *  @return string
     */
    public function get_host() {
        return $this->host;
    }

    /**
     *  Returns the name of the database
	 *
     *  @access public
     *  @name get_database()
     *  @return string
     */
    public function get_database() {
        return $this->database;
    }
}

?>
