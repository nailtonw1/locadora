

public abstract class Veiculo {

    protected int id;
    protected String modelo;
    protected String placa;
    protected double valorDiaria;
    protected boolean disponivel;

    public Veiculo(String modelo, String placa, double valorDiaria) {
        this.modelo = modelo;
        this.placa = placa;
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
    }


    public abstract double calcularValorAluguel(int dias);

    public abstract String getTipo();


    @Override
    public String toString() {
        return getTipo() + " | " + modelo + " | Placa: " + placa
                + " | Diária: R$ " + valorDiaria
                + " | " + (disponivel ? "Disponível" : "Alugado");
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getModelo() { return modelo; }

    public String getPlaca() { return placa; }

    public double getValorDiaria() { return valorDiaria; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}
