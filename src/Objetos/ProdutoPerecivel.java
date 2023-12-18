package Objetos;

import java.time.LocalDate;

public class ProdutoPerecivel extends Produto {
  private LocalDate dataVencimento;

  public ProdutoPerecivel(String codigo, String nome, double preco, int quantidade, LocalDate dataVencimento) {
    super(codigo, nome, preco, quantidade);
    setDataVencimento(dataVencimento);
  }

  public void setDataVencimento(LocalDate dataVencimento) {
    this.dataVencimento = dataVencimento;
  }

  public LocalDate getDataVencimento() {
    return dataVencimento;
  }

  @Override
  public String toString() {
    return super.toString() + " Data de validade: " + getDataVencimento();
  }
}