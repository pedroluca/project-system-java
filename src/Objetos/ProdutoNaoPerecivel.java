package Objetos;

public class ProdutoNaoPerecivel extends Produto {
  private String material;

  public ProdutoNaoPerecivel(String codigo, String nome, double preco, int quantidade, String material) {
    super(codigo, nome, preco, quantidade);
    setMaterial(material);
  }

  public void setMaterial(String material) {
    this.material = material;
  }

  public String getMaterial() {
    return material;
  }

  @Override
  public String toString() {
    return super.toString() + " Material: " + getMaterial();
  }
}