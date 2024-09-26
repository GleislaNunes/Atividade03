package org.example.dao;

import org.example.config.ConnectionFactory;
import org.example.model.Area;
import org.example.model.Cursos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CursoDAO implements ICursoDAO {

    @Override
    public Cursos create(Cursos curso) {

        if (curso.getCodigo() == null) {
            throw new IllegalArgumentException("O código do curso não pode ser nulo.");
        }

        try (Connection connection = ConnectionFactory.getConnection()) {

            String checkQuery = "SELECT COUNT(*) FROM curso WHERE codigo = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setLong(1, curso.getCodigo());
                ResultSet checkResult = checkStmt.executeQuery();
                if (checkResult.next() && checkResult.getInt(1) > 0) {
                    throw new IllegalArgumentException("O código do curso já existe.");
                }
            }

            String query = "INSERT INTO curso (codigo, nome, sigla, area) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, curso.getCodigo());
            preparedStatement.setString(2, curso.getNome());
            preparedStatement.setString(3, curso.getSigla());
            preparedStatement.setString(4, curso.getArea().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return curso;
    }

    @Override
    public void update(Cursos curso) {
        if (curso == null || curso.getCodigo() == null) {
            throw new IllegalArgumentException("Curso ou código do curso não pode ser nulo.");
        }

        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "UPDATE curso SET nome = ?, sigla = ?, area = ?, area_id = ? WHERE codigo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, curso.getNome());
            preparedStatement.setString(2, curso.getSigla());
            preparedStatement.setString(3, curso.getArea().toString());
            preparedStatement.setInt(4, curso.getAreaId());
            preparedStatement.setLong(5, curso.getCodigo());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("Nenhum curso encontrado com o código: " + curso.getCodigo());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar curso: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }



    @Override
    public void delete(Cursos curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "DELETE FROM curso WHERE codigo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, curso.getCodigo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cursos> findAll() {
        List<Cursos> cursos = new ArrayList<>();
        String query = "SELECT * FROM curso";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Cursos curso = new Cursos();
                curso.setCodigo(resultSet.getLong("codigo"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Area.valueOf(resultSet.getString("area")));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cursos;
    }

    @Override
    public Optional<Cursos> findById(Long id) {
        Cursos curso = null;
        String query = "SELECT * FROM curso WHERE codigo = ?";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                curso = new Cursos();
                curso.setCodigo(resultSet.getLong("codigo"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Area.valueOf(resultSet.getString("area")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(curso);
    }

    @Override
    public List<Cursos> findByArea(Area area) {
        List<Cursos> cursos = new ArrayList<>();
        String query = "SELECT * FROM curso WHERE area = ?";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, area.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Cursos curso = new Cursos();
                curso.setCodigo(resultSet.getLong("codigo"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Area.valueOf(resultSet.getString("area")));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cursos;
    }

    @Override
    public Optional<Cursos> findBySigla(String sigla) {
        Cursos curso = null;
        String query = "SELECT * FROM curso WHERE sigla = ?";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, sigla);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                curso = new Cursos();
                curso.setCodigo(resultSet.getLong("codigo"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Area.valueOf(resultSet.getString("area")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(curso);
    }
}
