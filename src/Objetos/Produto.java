package Objetos;

public abstract class Produto {
  private String codigo;
  private String nome;
  private double preco;
  private int quantidade;

  public Produto(String codigo, String nome, double preco, int quantidade) {
    setCodigo(codigo);
    setNome(nome);
    setPreco(preco);
    setQuantidade(quantidade);
  }

  public void setCodigo(String codigo) {
    if (codigo.length() > 0) this.codigo = codigo;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setNome(String nome) {
    if (nome.length() > 0) this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setPreco(double preco) {
    if (preco > 0) this.preco = preco;
  }

  public double getPreco() {
    return preco;
  }

  public void setQuantidade(int quantidade) {
    if (quantidade > 0) this.quantidade = quantidade;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void reduzirQuantidadePosVenda(int quantidadeVendida) {
    this.quantidade -= quantidadeVendida;
  }

  public void aumentarQuantidadePosCompra(int quantidadeComprada) {
    this.quantidade += quantidadeComprada;
  }

  public String toString() {
    return "Código: " + getCodigo() + " Nome: " + getNome() + " Preço: R$" + getPreco() + " Quantidade: " + getQuantidade();
  }
}