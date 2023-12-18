package Objetos;

public class Caixa {
  private double saldo;
  private Historico historicoDeVendas;
  private Historico historicoDeCompras;

  public Caixa(double saldo) {
    setSaldo(saldo);
    historicoDeCompras = new Historico("Compras");
    historicoDeVendas = new Historico("Vendas");
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public double getSaldo() {
    return saldo;
  }

  public void realizarVenda(double valorDaVenda) {
    this.saldo += valorDaVenda;
  }

  public void realizarCompra(double valorCompra) {
    this.saldo -= valorCompra;
  }

  public void setNovaCompraParaHistorico(Compra novaCompra) {
    historicoDeCompras.setNovaTransacao(novaCompra);
  }

  public void setNovaVendaParaHistorico(Venda novaVenda) {
    historicoDeVendas.setNovaTransacao(novaVenda);
  }

  public Historico getHistoricoDeCompras() {
    return historicoDeCompras;
  }

  public Historico getHistoricoDeVendas() {
    return historicoDeVendas;
  }
}
