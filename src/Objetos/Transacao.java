package Objetos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Transacao {
  private ArrayList<ItemTransacao> listaDeProdutos;
  private double valorTotal;
  private LocalDate dataDaTransacao;

  public Transacao() {
    setDataDaTransacao(LocalDate.now());
    listaDeProdutos = new ArrayList<ItemTransacao>();
  }

  public void setDataDaTransacao(LocalDate data) {
    this.dataDaTransacao = data;
  }

  public LocalDate getDataTransacaoSemFormatar() {
    return dataDaTransacao;
  }

  public String getDataDaTransacao() {
    String dataAtualFormatada = dataDaTransacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    return dataAtualFormatada;
  }

  public void setValorTotal() {
    for (ItemTransacao itemTransacao : listaDeProdutos) {
      this.valorTotal += itemTransacao.getSubtotal();
    }
  }

  public double getValorTotal() {
    return valorTotal;
  }

  public void setProduto(Produto produto, int quantidade) {
    ItemTransacao novoItem = new ItemTransacao(produto, quantidade);
    listaDeProdutos.add(novoItem);
  }

  public ArrayList<ItemTransacao> getListaDeProdutos() {
    return listaDeProdutos;
  }

  public String obterReciboFormatado() {
    StringBuilder sb = new StringBuilder();
    sb.append("Produtos:\n");
    for (ItemTransacao produto : this.getListaDeProdutos()) {
        sb.append("- ").append(produto.getProduto().getNome()).append(":\n Quant.: ").append(produto.getQuantidade()).append(" x R$").append(produto.getValor()).append(" = R$").append(produto.getSubtotal()).append("\n");
    }
    sb.append("Valor Total: R$").append(this.getValorTotal()).append("\n");
    return sb.toString();
  }
}
