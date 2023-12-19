package Objetos;

public class Venda extends Transacao {
  private Cliente clienteComprador;

  public Venda(Cliente cliente) {
    super();
    setClienteComprador(cliente);
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
    sbVenda.append("Recibo da Venda\n");
    sbVenda.append("Data de Emissão: ").append(this.getDataDaTransacao()).append("\n");
    sbVenda.append("Cliente: ").append(this.getClienteComprador().getNome()).append("\n");
    return sbVenda.toString() + super.obterReciboFormatado();
  }
}