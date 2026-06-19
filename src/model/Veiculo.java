package model;

public abstract class Veiculo implements Alugavel {

    private int id;
    private String modelo;
    private String placa;
    protected double valorDiaria;
    private boolean disponivel;

    public Veiculo(String modelo, String placa, double valorDiaria) {
        this.modelo = modelo;
        this.placa = placa;
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
    }

    @Override
    public abstract double calcularValorAluguel(int dias);

    @Override
    public abstract String getTipo();

    @Override
    public String toString() {
        return getTipo() + " - " + modelo + " | Placa: " + placa
                + " | Diária: R$ " + String.format("%.2f", valorDiaria);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public double getValorDiaria() { return valorDiaria; }

    @Override
    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}


interface Alugavel {

    double calcularValorAluguel(int dias);

    String getTipo();

    boolean isDisponivel();
}