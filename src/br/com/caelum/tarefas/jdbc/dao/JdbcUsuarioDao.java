package br.com.caelum.tarefas.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.caelum.tarefas.modelo.Tarefa;
import br.com.caelum.tarefas.modelo.Usuario;

import java.sql.PreparedStatement;

@Repository
public class JdbcUsuarioDao {
	
	//conexão com o banco de dados
	private Connection connection;
	
	//TODO: Fazer receber a conexao via filtro.
	@Autowired
	public JdbcUsuarioDao(DataSource dataSource) {
		try {
			this.connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean existeUsuario(Usuario usuario) {
		
		try {
			ArrayList<Usuario> userList = new ArrayList<Usuario>();
			PreparedStatement stmt = (PreparedStatement) this.connection.
					prepareStatement("select login from usuarios where login=? and senha=?");
			
			//prepared statement para inserção
			stmt.setString(1,usuario.getLogin());
			stmt.setString(2,usuario.getSenha());

			//executa
			ResultSet rs = stmt.executeQuery();
			
			return rs.first();
	
		}catch(SQLException e){
			return false;
		}
				
	}	

}