package br.com.projetoMVC.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.projetoMVC.model.Paciente;
import br.com.projetoMVC.util.ConnectionFactory;

public class PacienteDAOImpl implements GenericDAO {

	private Connection conn;

	// Construtor vazio da classe ProdutoDAOImpl, iniciando a conexão com o banco
	// de dados através da classe ConnectionFactory
	public PacienteDAOImpl() throws Exception {
		try {
			this.conn = ConnectionFactory.getConnection();
			// System.out.println("ProdutoDAOImpl: Conectado com sucesso!");
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public List<Object> listarTodos() {

		List<Object> lista = new ArrayList<Object>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM medico";

		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Paciente paciente = new Paciente();
				paciente.setId(rs.getInt("id"));
				paciente.setNome(rs.getString("nome"));
				paciente.setCpf(rs.getString("cpf"));
				paciente.setInternado(rs.getBoolean("isInternado"));
				paciente.setIdade(rs.getInt("idade"));
				lista.add(paciente);
			}
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao listar Paciente " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, rs);
			} catch (Exception ex) {
				System.out.println("Problemas na DAO ao fechar Conexão!" + ex.getMessage());
			}
		}

		return lista;
	}

	@Override
	public Object listarPorId(int id) {
		Paciente paciente = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM paciente WHERE id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				paciente.setId(rs.getInt("id"));
				paciente.setNome(rs.getString("nome"));
				paciente.setCpf(rs.getString("cpf"));
				paciente.setInternado(rs.getBoolean("isInternado"));
				paciente.setIdade(rs.getInt("idade"));
			}
		} catch (SQLException ex) {
			System.out.println("Problema na DAO ao listar Paciente por id! " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, rs);
			} catch (Exception e) {
				System.out.println("Problema na DAO ao fechar Conexão! " + e.getMessage());
				e.printStackTrace();
			}
		}
		return paciente;
	}

	@Override
	public boolean cadastrar(Object object) {

		Paciente paciente = (Paciente) object;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO paciente (nome, cpf, isInternado, idade) VALUES (?)";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paciente.getNome());
			stmt.setString(2, paciente.getCpf());
			stmt.setBoolean(3, paciente.isInternado());
			stmt.setInt(4, paciente.getIdade());
			stmt.execute();
			return true;
		} catch (Exception ex) {
			System.out.println("Problemas na DAO ao cadastrar Paciente " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, null);
			} catch (Exception e) {
				System.out.println("Problema na DAO ao fechar Conexão! " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean alterar(Object object) {
		Paciente paciente = (Paciente) object;
		PreparedStatement stmt = null;
		String sql = "UPDATE paciente SET nome = ?, cpf = ?, crm = ?, isInternado = ?, idade = ? WHERE id = ?";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paciente.getNome());
			stmt.setString(2, paciente.getCpf());
			stmt.setBoolean(3, paciente.isInternado());
			stmt.setInt(4, paciente.getIdade());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Erros na DAO ao alterar Paciente! " + ex.getMessage());
			ex.printStackTrace();
			return false;

		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, null);
			} catch (Exception e) {
				System.out.println("Problema na DAO ao fechar Conexão! " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void excluir(int id) {
		PreparedStatement stmt = null;
		String sql = "DELETE FROM medico WHERE id = ?";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();

		} catch (Exception ex) {
			System.out.println("Problema na DAO ao excluir Paciente!" + ex.getMessage());
		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, null);
			} catch (Exception e) {
				System.out.println("Problema na DAO ao fechar Conexão! " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
