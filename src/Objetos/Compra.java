package Objetos;

public class Compra extends Transacao {
  public Compra() {
    super();
  }

  @Override
  public String obterReciboFormatado() {
    StringBuilder sbVenda = new StringBuilder();
    sbVenda.append("Recibo da Compra\n");
    sbVenda.append("Data de Emissão: ").append(this.getDataDaTransacao()).append("\n");
    return sbVenda.toString() + super.obterReciboFormatado();
  }
}
