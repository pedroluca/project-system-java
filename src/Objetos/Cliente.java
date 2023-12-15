package Objetos;

public class Cliente {
  private String nome;
  private String CPF;
  private int idade;

  public Cliente(String nome, String CPF, int idade) {
    setNome(nome);
    setCPF(CPF);
    setIdade(idade);
  }

  public void setNome(String nome) {
    if (nome.length() > 0) this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setCPF(String CPF) {
    if (CPF.length() > 0) this.CPF = CPF;
  }

  public String getCPF() {
    return CPF;
  }

  public void setIdade(int idade) {
    if (idade > 0) this.idade = idade;
  }

  public int getIdade() {
    return idade;
  }

  public String toString() {
    return "Nome: " + getNome() + " CPF: " + getCPF();
  }
}
