package Objetos;

public class Caixa {
  private double saldo;

  public Caixa(double saldo) {
    setSaldo(saldo);
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
}
