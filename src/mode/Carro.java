


public class Carro extends Veiculo {

    private int numPortas;

    public Carro(String modelo, String placa, double valorDiaria, int numPortas) {
        super(modelo, placa, valorDiaria);
        this.numPortas = numPortas;
    }

    @Override
    public double calcularValorAluguel(int dias) {
        double taxaSeguro = 20.0;
        return (valorDiaria * dias) + taxaSeguro;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }

    public int getNumPortas() { return numPortas; }
}
