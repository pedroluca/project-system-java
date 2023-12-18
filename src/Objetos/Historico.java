package Objetos;

import java.util.ArrayList;

public class Historico {
  private int quantidadeDeTransacoes = 0;
  private String tipoDeTransacao;
  private ArrayList<Transacao> listaDeTransacoes;

  public Historico(String tipo) {
    setTipoDeTransacao(tipo);
    listaDeTransacoes = new ArrayList<Transacao>();
  }

  public void setTipoDeTransacao(String tipo) {
    this.tipoDeTransacao = tipo;
  }

  public String getTipoDeTransacao() {
    return tipoDeTransacao;
  }

  public void setNovaTransacao(Transacao novaTransacao) {
    listaDeTransacoes.add(novaTransacao);
  }

  public ArrayList<Transacao> getListaDeTransacoes() {
    return listaDeTransacoes;
  }

  public void setQuantidadeDeTransacoes() {
    this.quantidadeDeTransacoes = listaDeTransacoes.size();
  }

  public int getQuantidadeDeTransacoes() {
    return quantidadeDeTransacoes;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Histórico de ").append(tipoDeTransacao).append(":\n");
    for (Transacao transacao : listaDeTransacoes) {
      sb.append(transacao.obterReciboFormatado());
      sb.append("\n");
    }
    return sb.toString();
  }
}
