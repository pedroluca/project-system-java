package Objetos;

public class Compra extends Transacao {
  public Compra() {
    super();
    setCodigo(gerarCodigo());
  }

  @Override
  public String gerarCodigo() {
    return "C" + super.gerarCodigo();
  }

  @Override
  public String obterReciboFormatado() {
    StringBuilder sbVenda = new StringBuilder();
    sbVenda.append("Recibo\n");
    sbVenda.append("Código da Compra: ").append(this.getCodigo()).append("\n");
    sbVenda.append("Data de Emissão: ").append(this.getDataDaTransacao()).append("\n");
    return sbVenda.toString() + super.obterReciboFormatado();
  }
}
