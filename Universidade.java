package entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author 07337354959
 */
public class Universidade {
    private String nome;
    private String CNPJ;
    private List<Curso> cursos;
    private static int geradorCurso = 0;

    public Universidade(String nome, String CNPJ) {
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.cursos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public Curso criarCurso(String nome, String dataCriacao, String descricao) {
        Curso curso = new Curso(++geradorCurso, nome, dataCriacao, descricao);
        cursos.add(curso);
        return curso;
    }

    public Optional<Curso> buscarCurso(int codigo) {
        return cursos.stream().filter(c -> c.getCodigo() == codigo).findFirst();
    }

    public boolean adicionarProfessor(int codCurso, String dataAdmissao, String nome, String dataNascimento, String CPF) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        if (curso != null) {
            return curso.getProfessores().add(new Professor(curso, dataAdmissao, nome, dataNascimento, CPF));
        } else {
            System.out.println("Curso não encontrado nesta Universidade");
            return false;
        }
    }

    public boolean criarTurma(int codCurso, String nome, int qtdVagas, String matriculaProf) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        Professor prof = curso.buscarProfessor(matriculaProf).orElse(null);

        if (prof != null) {
            return curso.addTurma(nome, qtdVagas, prof);
        } else {
            System.out.println("Dados não encontrados nesta Universidade.");
            return false;
        }
    }

    public boolean matricularAluno(int codCurso, int codTurma, Aluno aluno) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        Turma turma = curso.buscarTurma(codTurma).orElse(null);

        if (turma != null) {
            if (turma.getAlunos().size() + 1 <= turma.getQtdVagas() && !turma.getAlunos().contains(aluno)) {
                return turma.getAlunos().add(aluno);
            } else {
                System.out.println("Excedeu o limite de inscritos da turma, ou o aluno já encontra-se matriculado.");
                return false;
            }
        } else  {
            System.out.println("Dados não encontrados nesta Universidade.");
            return false;
        }
    }

    public boolean registrarNotaAluno(int codCurso, int codTurma, String matricula, Double nota) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        Turma turma = curso.buscarTurma(codTurma).orElse(null);
        Aluno aluno = turma.buscarAluno(matricula).orElse(null);

        if (aluno != null) {
            return turma.getNotas().add(new Nota(aluno, this, curso, turma, nota));
        } else {
            System.out.println("Dados não encontrados nesta Universidade.");
            return false;
        }
    }

    public Map<Aluno, Double> calcMediaAlunos(int codCurso, int codTurma) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        Turma turma = curso.buscarTurma(codTurma).orElse(null);
        
        Map<Aluno, Double> medias = turma.getNotas()
                                          .stream()
                                          .collect(Collectors.groupingBy(a -> a.getAluno(), 
                                                   Collectors.averagingDouble(Nota::getNota)));
        
        /*System.out.println("Médias por aluno:");
        for (Map.Entry<String, Double> notas : medias.entrySet()) {
            System.out.printf("%s - %.2f\n", notas.getKey(), notas.getValue());
        }*/
        
        return medias;
    }
    
    public Map<Aluno, Double> mediasOrdemAlfabetica(int codCurso, int codTurma) {
        Map<Aluno, Double> ordemAlfabetica = this.calcMediaAlunos(codCurso, codTurma)
                                                  .entrySet()
                                                  .stream()
                                                  .sorted((o1, o2)->o1.getKey().getNome().
                                                          compareTo(o2.getKey().getNome()))
                                                  .collect(Collectors.toMap(
                                                          Entry::getKey, 
                                                          Entry::getValue, 
                                                          (e1, e2) -> e1, 
                                                          LinkedHashMap::new));
        
        System.out.println("Médias por aluno - Ordem alfabética:");
        for (Entry<Aluno, Double> notas : ordemAlfabetica.entrySet()) {
            System.out.printf("%s - %.2f\n", notas.getKey().getNome(), notas.getValue());
        }
        
        return ordemAlfabetica;
    }
    
    public Map<Aluno, Double> mediasOrdemDesempenho(int codCurso, int codTurma) {
        Map<Aluno, Double> ordemDesempenhoa = this.calcMediaAlunos(codCurso, codTurma)
                .entrySet()
                .stream()
                .sorted((o1, o2)->o2.getValue().
                        compareTo(o1.getValue()))
                .collect(Collectors.toMap(
                        Entry::getKey, 
                        Entry::getValue, 
                        (e1, e2) -> e1, 
                        LinkedHashMap::new));
        
        System.out.println("Médias por aluno - Por desempenho:");
        for (Map.Entry<Aluno, Double> notas : ordemDesempenhoa.entrySet()) {
            System.out.printf("%s - %.2f\n", notas.getKey().getNome(), notas.getValue());
        }
        
        return ordemDesempenhoa;
    }
    
    public Set<Aluno> alunosInscritos(int codCurso) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        Set<Aluno> alunosInscritos = curso.getTurmas()
                                          .stream()
                                          .map(Turma::getAlunos)
                                          .flatMap(Collection::stream)
                                          .collect(Collectors.toSet());
        
        alunosInscritos.forEach(a -> System.out.printf("%s - %s\n", a.getMatricula(), a.getNome()));
        
        return alunosInscritos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Universidade: ").append(getNome())
          .append("; CNPJ: ").append(getCNPJ())
          .append("\n\nCursos: \n").append(cursos.stream()
                                                 .map(Curso::toString)
                                                 .collect(Collectors.joining("\n\n")));
        return sb.toString();
    }
}
