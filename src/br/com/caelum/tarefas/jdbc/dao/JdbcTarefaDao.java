package br.com.caelum.tarefas.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.caelum.tarefas.modelo.Tarefa;
import br.com.caelum.tarefas.modelo.TarefaDao;

import java.sql.PreparedStatement;

@Repository
public class JdbcTarefaDao implements TarefaDao {
	
	//conexão com o banco de dados
	private static Connection connection;
	
	@Autowired
	public JdbcTarefaDao(DataSource dataSource) {
		try {
				this.connection = dataSource.getConnection();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	public void adiciona(Tarefa tarefa) {
		//adiciona tarefa
		String sql = "insert into tarefas" +
		"(descricao,finalizado,dataFinalizacao)" +
		" values (?,?,?)";
		
		try {
			//prepared statement para inserção
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			
			//seta os valores
			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			
			try{
				stmt.setDate(3, new Date(
						tarefa.getDataFinalizacao().getTimeInMillis()));
			}catch(Exception e) {
				stmt.setString(3, "0000-00-00");
			}
			
			
			// executa
			stmt.execute();
			stmt.close();
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public ArrayList<Tarefa> lista() {
		try {
			ArrayList<Tarefa> tarefaLista = new ArrayList<Tarefa>();
			PreparedStatement stmt = (PreparedStatement) this.connection.
					prepareStatement("select * from tarefas");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				// criando o objeto Contato
				Tarefa tarefa = new Tarefa();
				
				tarefa.setId(rs.getLong("id"));
				
				tarefa.setDescricao(rs.getString("descricao"));
				
				// montando a data através do Calendar
				Calendar data = Calendar.getInstance();
				try{
					rs.getDate("dataFinalizacao"); 
					data.setTime(rs.getDate("dataFinalizacao"));
					tarefa.setDataFinalizacao(data);
				}catch(Exception e) {
					tarefa.setDataFinalizacao(null);
				}
				tarefa.setFinalizado(rs.getBoolean("finalizado"));
				
				// adicionando o objeto à lista
				tarefaLista.add(tarefa);
			}
			rs.close();
			stmt.close();
			return tarefaLista;
			}catch(SQLException e){
				throw new RuntimeException(e);		
			}
	}

	public void remove(Tarefa tarefa) {

		String sql = "delete from tarefas where id=?";

		try {
			//prepared statement para inserção
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);

			stmt.setFloat(1, tarefa.getId());
			
			//executa
			stmt.execute();
			stmt.close();
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Tarefa buscaPorId(Long id) {
				
		String sql = "select * from tarefas where id=?";
		
		try {
			
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);

			stmt.setInt(1, Integer.parseInt(""+id));
			
			ResultSet rs = stmt.executeQuery();

			Tarefa tarefa = new Tarefa();
			
			while (rs.next()){
				
				tarefa.setId(id);
				
				tarefa.setDescricao(rs.getString("descricao"));
				
				// montando a data através do Calendar
				Calendar data = Calendar.getInstance();
				try{
					rs.getDate("dataFinalizacao"); 
					data.setTime(rs.getDate("dataFinalizacao"));
					tarefa.setDataFinalizacao(data);
				}catch(Exception e) {
					tarefa.setDataFinalizacao(null);
				}
				tarefa.setFinalizado(rs.getBoolean("finalizado"));
			}	

				rs.close();
				stmt.close();
				return tarefa;

			}catch(SQLException e){
				throw new RuntimeException(e);		
			}
	}

	public void altera(Tarefa tarefa) {
		
 		String sql = 
 				"update tarefas set descricao=?, finalizado=?, dataFinalizacao=? where id=?";
		try {

			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			
			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			
			try{
				stmt.setDate(3, new Date(
						tarefa.getDataFinalizacao().getTimeInMillis()));
			}catch(Exception e) {
				stmt.setString(3, "0000-00-00");
			}
			
			stmt.setInt(4, Integer.parseInt(""+tarefa.getId()));
			
			stmt.execute();
			stmt.close();
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void finaliza(Long id) {
		
 		String sql = 
 				"update tarefas set finalizado=?, dataFinalizacao=? where id=?";
		try {

			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			
			stmt.setBoolean(1, true);
			
			stmt.setString(2, Calendar.getInstance().toString());

			stmt.setInt(3, Integer.parseInt(""+id));
			
			stmt.execute();
			stmt.close();
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
}