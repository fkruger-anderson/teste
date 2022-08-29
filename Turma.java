package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author 07337354959
 */
public class Turma {
    private int codigo;
    private String nome;
    private int qtdVagas;
    private List<Aluno> alunos;
    private Professor professor;
    private List<Nota> notas;

    public Turma(int codigo, String nome, int qtdVagas, Professor professor) {
        this.codigo = codigo;
        this.nome = nome;
        this.qtdVagas = qtdVagas;
        this.alunos = new ArrayList<>();
        this.professor = professor;
        this.notas = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getQtdVagas() {
        return qtdVagas;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }
    
    public List<Nota> getNotas() {
        return notas;
    }

    public Optional<Aluno> buscarAluno(String matricula) {
        return alunos.stream()
                     .filter(a -> a.getMatricula().equalsIgnoreCase(matricula))
                     .findFirst();
    }

    public String alunosInscritos() {
        return alunos.stream()
                     .map(Pessoa::toString)
                     .collect(Collectors.joining("\n"));
    }

    public String infosCompletaTurma() {
        StringBuilder sb = new StringBuilder();
        sb.append(this)
          .append(":\n").append(alunosInscritos());
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCodigo()).append(" - ").append(getNome())
          .append(", Qtd vagas: ").append(qtdVagas)
          .append(", Professor(a): ").append(professor.getMatricula()).append(" - ").append(professor.getNome());
        return sb.toString();
    }
}
