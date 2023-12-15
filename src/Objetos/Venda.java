package Objetos;

public class Venda extends Transacao {
  private Cliente clienteComprador;

  public Venda(Cliente cliente) {
    super();
    setCodigo(this.gerarCodigo());
    setClienteComprador(cliente);
  }

  @Override
  public String gerarCodigo() {
    return "V" + super.gerarCodigo();
  }

  public void setClienteComprador(Cliente cliente) {
    this.clienteComprador = cliente;
  }

  public Cliente getClienteComprador() {
    return clienteComprador;
  }

  @Override
  public String obterReciboFormatado() {
    StringBuilder sbVenda = new StringBuilder();
    sbVenda.append("Recibo\n");
    sbVenda.append("Código da Venda: ").append(this.getCodigo()).append("\n");
    sbVenda.append("Data de Emissão: ").append(this.getDataDaTransacao()).append("\n");
    sbVenda.append("Cliente: ").append(this.getClienteComprador().getNome()).append("\n");
    return sbVenda.toString() + super.obterReciboFormatado();
  }
}