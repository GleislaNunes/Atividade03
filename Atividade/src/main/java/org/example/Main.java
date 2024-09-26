package org.example;

import org.example.dao.AlunoDAO;
import org.example.dao.CursoDAO;
import org.example.model.Alunos;
import org.example.model.Area;
import org.example.model.Cursos;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;


public class Main {
    private AlunoDAO alunoDAO = new AlunoDAO();
    private CursoDAO cursoDAO = new CursoDAO();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Gerenciamento de Alunos e Cursos");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel alunosPanel = new JPanel();
        alunosPanel.setLayout(new FlowLayout());

        JButton btnAddAluno = new JButton("Adicionar Aluno");
        JButton btnUpdateAluno = new JButton("Atualizar Aluno");
        JButton btnDeleteAluno = new JButton("Deletar Aluno");
        JButton btnViewAlunos = new JButton("Visualizar Alunos");

        btnAddAluno.addActionListener(e -> addAluno());
        btnUpdateAluno.addActionListener(e -> updateAluno());
        btnDeleteAluno.addActionListener(e -> deleteAluno());
        btnViewAlunos.addActionListener(e -> viewAlunos());

        alunosPanel.add(btnAddAluno);
        alunosPanel.add(btnUpdateAluno);
        alunosPanel.add(btnDeleteAluno);
        alunosPanel.add(btnViewAlunos);


        JPanel cursosPanel = new JPanel();
        cursosPanel.setLayout(new FlowLayout());

        JButton btnAddCurso = new JButton("Adicionar Curso");
        JButton btnUpdateCurso = new JButton("Atualizar Curso");
        JButton btnDeleteCurso = new JButton("Deletar Curso");
        JButton btnViewCursos = new JButton("Visualizar Cursos");

        btnAddCurso.addActionListener(e -> addCurso());
        btnUpdateCurso.addActionListener(e -> updateCurso());
        btnDeleteCurso.addActionListener(e -> deleteCurso());
        btnViewCursos.addActionListener(e -> viewCursos());

        cursosPanel.add(btnAddCurso);
        cursosPanel.add(btnUpdateCurso);
        cursosPanel.add(btnDeleteCurso);
        cursosPanel.add(btnViewCursos);


        tabbedPane.addTab("Alunos", alunosPanel);
        tabbedPane.addTab("Cursos", cursosPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }


    private void addAluno() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        JTextField matriculaField = new JTextField();
        JTextField nomeField = new JTextField();
        JTextField telefoneField = new JTextField();
        JTextField sexoField = new JTextField();
        JTextField cursoField = new JTextField();
        JCheckBox maioridadeField = new JCheckBox("Maioridade");

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneField);
        panel.add(new JLabel("Sexo:"));
        panel.add(sexoField);
        panel.add(new JLabel("Curso:"));
        panel.add(cursoField);
        panel.add(maioridadeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Aluno", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Alunos aluno = new Alunos();
            aluno.setNome(nomeField.getText());
            aluno.setTelefone(telefoneField.getText());
            aluno.setSexo(sexoField.getText());
            aluno.setCurso(cursoField.getText());
            aluno.setMaioridade(maioridadeField.isSelected());
            alunoDAO.create(aluno);
            JOptionPane.showMessageDialog(null, "Aluno adicionado com sucesso!");
        }
    }


    private void updateAluno() {
        String matricula = JOptionPane.showInputDialog("Digite a matrícula do aluno a ser atualizado:");
        if (matricula != null) {
            Long id = Long.valueOf(matricula);
            Optional<Alunos> optionalAluno = alunoDAO.findById(id);
            if (optionalAluno.isPresent()) {
                Alunos aluno = optionalAluno.get();
                JPanel panel = new JPanel(new GridLayout(0, 2));
                JTextField nomeField = new JTextField(aluno.getNome());
                JTextField telefoneField = new JTextField(aluno.getTelefone());
                JTextField sexoField = new JTextField(aluno.getSexo());
                JTextField cursoField = new JTextField(aluno.getCurso());
                JCheckBox maioridadeField = new JCheckBox("Maioridade", aluno.isMaioridade());

                panel.add(new JLabel("Nome:"));
                panel.add(nomeField);
                panel.add(new JLabel("Telefone:"));
                panel.add(telefoneField);
                panel.add(new JLabel("Sexo:"));
                panel.add(sexoField);
                panel.add(new JLabel("Curso (Sigla):"));
                panel.add(cursoField);
                panel.add(maioridadeField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Atualizar Aluno", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    aluno.setNome(nomeField.getText());
                    aluno.setTelefone(telefoneField.getText());
                    aluno.setSexo(sexoField.getText());
                    aluno.setCurso(cursoField.getText());
                    aluno.setMaioridade(maioridadeField.isSelected());
                    alunoDAO.update(aluno);
                    JOptionPane.showMessageDialog(null, "Aluno atualizado com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
            }
        }
    }

    private void deleteAluno() {
        String matricula = JOptionPane.showInputDialog("Digite a matrícula do aluno a ser deletado:");
        if (matricula != null) {
            Long id = Long.valueOf(matricula);
            Optional<Alunos> optionalAluno = alunoDAO.findById(id);
            if (optionalAluno.isPresent()) {
                alunoDAO.delete(optionalAluno.get());
                JOptionPane.showMessageDialog(null, "Aluno deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
            }
        }
    }

    private void viewAlunos() {
        List<Alunos> alunos = alunoDAO.findAll();
        StringBuilder message = new StringBuilder();
        for (Alunos aluno : alunos) {
            message.append("Nome: ").append(aluno.getNome())
                    .append(", Matricula: ").append(aluno.getMatricula())
                    .append(", Curso: ").append(aluno.getCurso())
                    .append(", Sexo: ").append(aluno.getSexo())
                    .append(", Telefone: ").append(aluno.getTelefone())
                    .append(", Maioridade: ").append(aluno.isMaioridade() ? "Sim" : "Não")
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString(), "Lista de Alunos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addCurso() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        JTextField codigoField = new JTextField();
        JTextField nomeField = new JTextField();
        JTextField siglaField = new JTextField();
        JComboBox<Area> areaCombo = new JComboBox<>(Area.values());
        JTextField areaIdField = new JTextField();

        panel.add(new JLabel("Código:"));
        panel.add(codigoField);
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Sigla:"));
        panel.add(siglaField);
        panel.add(new JLabel("Área:"));
        panel.add(areaCombo);
        panel.add(new JLabel("Área ID:"));
        panel.add(areaIdField);


        areaCombo.addActionListener(e -> {
            Area selectedArea = (Area) areaCombo.getSelectedItem();
            areaIdField.setText(String.valueOf(selectedArea.ordinal()));
        });

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Curso", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Cursos curso = new Cursos();
            try {
                Long codigo = Long.parseLong(codigoField.getText());
                curso.setCodigo(codigo);
                curso.setNome(nomeField.getText());
                curso.setSigla(siglaField.getText());
                Area areaSelecionada = (Area) areaCombo.getSelectedItem();
                curso.setArea(areaSelecionada);


                try {
                    int areaId = Integer.parseInt(areaIdField.getText());
                    curso.setAreaId(areaId);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Área ID inválida. Deve ser um número.");
                    return;
                }

                cursoDAO.create(curso);
                JOptionPane.showMessageDialog(null, "Curso adicionado com sucesso!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Código inválido. Deve ser um número.");
            }
        }
    }







    private void updateCurso() {
        String sigla = JOptionPane.showInputDialog("Digite a sigla do curso a ser atualizado:");
        if (sigla != null) {
            Optional<Cursos> optionalCurso = cursoDAO.findBySigla(sigla);
            if (optionalCurso.isPresent()) {
                Cursos curso = optionalCurso.get();
                JPanel panel = new JPanel(new GridLayout(0, 2));
                JTextField codigoField = new JTextField(String.valueOf(curso.getCodigo()));
                JTextField nomeField = new JTextField(curso.getNome());
                JTextField siglaField = new JTextField(curso.getSigla());
                JTextField areaIdField = new JTextField(String.valueOf(curso.getAreaId()));
                JComboBox<Area> areaCombo = new JComboBox<>(Area.values());
                areaCombo.setSelectedItem(curso.getArea());

                panel.add(new JLabel("Código:"));
                panel.add(codigoField);
                panel.add(new JLabel("Nome:"));
                panel.add(nomeField);
                panel.add(new JLabel("Sigla:"));
                panel.add(siglaField);
                panel.add(new JLabel("Área:"));
                panel.add(areaCombo);
                panel.add(new JLabel("Área ID:"));
                panel.add(areaIdField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Atualizar Curso", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        Long codigo = Long.parseLong(codigoField.getText());
                        curso.setCodigo(codigo);
                        curso.setNome(nomeField.getText());
                        curso.setSigla(siglaField.getText());
                        curso.setArea((Area) areaCombo.getSelectedItem());
                        curso.setAreaId(Integer.parseInt(areaIdField.getText()));
                        cursoDAO.update(curso);
                        JOptionPane.showMessageDialog(null, "Curso atualizado com sucesso!");
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Código ou área ID inválido. Devem ser números.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Curso não encontrado.");
            }
        }
    }




    private void deleteCurso() {
        String sigla = JOptionPane.showInputDialog("Digite a sigla do curso a ser deletado:");
        if (sigla != null) {
            Optional<Cursos> optionalCurso = cursoDAO.findBySigla(sigla);
            if (optionalCurso.isPresent()) {
                cursoDAO.delete(optionalCurso.get());
                JOptionPane.showMessageDialog(null, "Curso deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Curso não encontrado.");
            }
        }
    }

    private void viewCursos() {
        List<Cursos> cursos = cursoDAO.findAll();
        StringBuilder message = new StringBuilder();
        for (Cursos curso : cursos) {
            message.append("Nome: ").append(curso.getNome())
                    .append(", Sigla: ").append(curso.getSigla())
                    .append(", Área: ").append(curso.getArea())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString(), "Lista de Cursos", JOptionPane.INFORMATION_MESSAGE);
    }
}
