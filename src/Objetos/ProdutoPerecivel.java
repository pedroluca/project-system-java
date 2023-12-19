package Objetos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
      DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      String dataFormatada = getDataVencimento().format(formatoBrasileiro);
      return super.toString() + " Data de validade: " + dataFormatada;
  }
}