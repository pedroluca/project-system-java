package Objetos;

public class ItemTransacao {
  private Produto produto;
  private int quantidade;
  private double valor;
  private double subtotal;
  private double desconto;

  public ItemTransacao(Produto produto, int quantidade) {
    setProduto(produto);
    setQuantidade(quantidade);
    setValor(produto.getPreco());
    setSubtotal();
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }

  public Produto getProduto() {
    return produto;
  }

  public void setQuantidade(int quantidade) {
    if (quantidade > 0) this.quantidade = quantidade;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  public double getValor() {
    return valor;
  }

  public void setSubtotal() {
    subtotal = aplicarDesconto(valor) * quantidade;
  }

  public double getSubtotal() {
    return subtotal;
  }

  public void setDesconto(double desconto) {
    this.desconto = desconto;
  }

  public double getDesconto() {
    return desconto;
  }

  public double aplicarDesconto(double valorParaReceberDesconto) {
    return valorParaReceberDesconto - (valorParaReceberDesconto * desconto);
  }

  @Override
  public String toString() {
    return "Produto: " + getProduto().getNome() + " Quantidade: " + getQuantidade() + " Valor total: R$" + getSubtotal();
  }
}
