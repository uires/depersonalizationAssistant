package org.depersonalizationAssistant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.depersonalizationAssistant.factory.ConnectionFactory;
import org.depersonalizationAssistant.model.NomePatologia;
import org.depersonalizationAssistant.model.Patologia;
import org.depersonalizationAssistant.model.Relatorio;
import org.springframework.stereotype.Repository;

@Repository
public class RelatorioDAO {

	public void cadastraRelatorio(Relatorio relatorio, Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO relatorio ( " + "id_paciente, id_patologia, descricao) " + "VALUES (?, ?, ?)");
		try {
			Connection conexao = ConnectionFactory.getConnection();
			PreparedStatement preparedStatement = conexao.prepareStatement(sql.toString());
			preparedStatement.setLong(1, id);
			preparedStatement.setLong(2, this.insertPatologia(conexao, relatorio.getPatologia()));
			preparedStatement.setString(3, relatorio.getDescricao());
			preparedStatement.execute();
			preparedStatement.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private long insertPatologia(Connection conexao, Patologia patologia) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO patologia ( ");
		sql.append("nome_patologia, data_inicio) " + "VALUES (?, ?)");
		try {
			PreparedStatement prepareStatement = conexao.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setString(1, patologia.getNomePatologia().toString());
			java.sql.Date dateConvert = new java.sql.Date(patologia.getDataInicio().getTimeInMillis());
			prepareStatement.setDate(2, dateConvert);
			prepareStatement.executeUpdate();
			ResultSet executeQuery = prepareStatement.getGeneratedKeys();
			if (executeQuery.next()) {
				long long1 = executeQuery.getLong(1);
				return long1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public LinkedList<Relatorio> selectAllRelatoriosPacienteSession(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("ID NÃO PODE SER NULO!");
		}
		LinkedList<Relatorio> relatoriosList = new LinkedList<>();
		String sql = "SELECT * FROM relatorio WHERE id_paciente = ?";
		try {
			Connection conexao = ConnectionFactory.getConnection();
			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				Relatorio relato = new Relatorio();
				relato.setId(resultSet.getLong("id"));
				relato.setIdPaciente(resultSet.getLong("id_paciente"));
				relato.setDescricao(resultSet.getString("descricao"));
				relato.setIdPatologia(resultSet.getLong("id_patologia"));
				relato.setPatologia(this.selectPatologiaById(conexao, relato.getIdPatologia()));
				relatoriosList.add(relato);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return relatoriosList;
	}

	private Patologia selectPatologiaById(Connection conexao, Long idPatologia) {
		String sql = "SELECT * FROM patologia WHERE id = ?";
		try {
			PreparedStatement preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setLong(1, idPatologia);
			ResultSet executeQuery = preparedStatement.executeQuery();
			if (executeQuery.next()) {
				Patologia patologia = new Patologia();
				Calendar date = new GregorianCalendar();
				date.setTime(executeQuery.getDate("date_inicio"));
				patologia.setDataInicio(date);
				patologia.setNomePatologia(NomePatologia.valueOf(executeQuery.getString("nome_patologia")));
				preparedStatement.close();
				conexao.close();
				return patologia;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
