package br.com.projetoMVC.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.projetoMVC.model.Medico;
import br.com.projetoMVC.util.ConnectionFactory;

public class MedicoDAOImpl implements GenericDAO {

	private Connection conn;

	// Construtor vazio da classe ProdutoDAOImpl, iniciando a conexão com o banco
	// de dados através da classe ConnectionFactory
	public MedicoDAOImpl() throws Exception {
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
				Medico medico = new Medico();
				medico.setId(rs.getInt("id"));
				medico.setNome(rs.getString("nome"));
				medico.setEspecialidade(rs.getString("especialidade"));
				medico.setCrm(rs.getString("crm"));
				medico.setPlantao(rs.getBoolean("isPlantao"));
				lista.add(medico);
			}
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao listar Medico " + ex.getMessage());
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
		Medico medico = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM medico WHERE id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				medico.setId(rs.getInt("id"));
				medico.setNome(rs.getString("nome"));
				medico.setEspecialidade(rs.getString("especialidade"));
				medico.setCrm(rs.getString("crm"));
				medico.setPlantao(rs.getBoolean("isPlantao"));
			}
		} catch (SQLException ex) {
			System.out.println("Problema na DAO ao listar Medico por id! " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, rs);
			} catch (Exception e) {
				System.out.println("Problema na DAO ao fechar Conexão! " + e.getMessage());
				e.printStackTrace();
			}
		}
		return medico;
	}

	@Override
	public boolean cadastrar(Object object) {

		Medico medico = (Medico) object;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO medico (descricao) VALUES (?)";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, medico.getNome());
			stmt.setString(2, medico.getEspecialidade());
			stmt.setString(3, medico.getCrm());
			stmt.setBoolean(4, medico.isPlantao());
			stmt.execute();
			return true;
		} catch (Exception ex) {
			System.out.println("Problemas na DAO ao cadastrar Medico " + ex.getMessage());
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
		Medico medico = (Medico) object;
		PreparedStatement stmt = null;
		String sql = "UPDATE medico SET nome = ?, especialidade = ?, crm = ?, isPlantao = ? WHERE id = ?";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, medico.getNome());
			stmt.setString(2, medico.getEspecialidade());
			stmt.setString(3, medico.getCrm());
			stmt.setBoolean(4, medico.isPlantao());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Erros na DAO ao alterar Medico! " + ex.getMessage());
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
			System.out.println("Problema na DAO ao excluir Medico!" + ex.getMessage());
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
