public class Program {
    public static void main(String[] args) {
        Universidade uni1 = new Universidade("UDESC", "01.768.417.0001/61");
        uni1.criarCurso("Engenharia de Software", "01/10/2010", "Curso para formar desenvolvedores");
        uni1.adicionarProfessor(1, "01/01/2020", "Fernando", "01/05/1970", "016.958.969-20");
        uni1.adicionarProfessor(1, "01/01/2020", "Matheus", "01/05/1970", "016.958.969-20");

        Aluno aluno1 = new Aluno("Anderson", "15/03/1993", "073.373.549-59");
        Aluno aluno2 = new Aluno("Gabriela", "15/03/1993", "073.373.549-59");
        Aluno aluno3 = new Aluno("Jaqueline", "15/03/1993", "073.373.549-59");
        Aluno aluno4 = new Aluno("Ricardo", "15/03/1993", "073.373.549-59");

        uni1.criarTurma(1, "Programação I", 30, "P20201");
        uni1.matricularAluno(1, 1, aluno1);
        uni1.matricularAluno(1, 1, aluno2);
        uni1.matricularAluno(1, 1, aluno3);
        uni1.matricularAluno(1, 1, aluno4);

        uni1.criarTurma(1, "Programação II", 30, "P20202");
        uni1.matricularAluno(1, 2, aluno1);
        uni1.matricularAluno(1, 2, aluno2);
        uni1.matricularAluno(1, 2, aluno3);
        uni1.matricularAluno(1, 2, aluno4);

        System.out.println(uni1);

        uni1.registrarNotaAluno(1, 1, "A19931", 8.0);
        uni1.registrarNotaAluno(1, 1, "A19932", 8.0);
        uni1.registrarNotaAluno(1, 1, "A19933", 8.0);
        uni1.registrarNotaAluno(1, 1, "A19934", 8.0);
        uni1.registrarNotaAluno(1, 1, "A19931", 5.0);
        uni1.registrarNotaAluno(1, 1, "A19931", 6.0);

        uni1.calcMediaAlunos(1,1);

    }
}
