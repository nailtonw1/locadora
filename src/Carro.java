


public class Carro extends Veiculo {

    private int numPortas;

    public Carro(String modelo, String placa, double valorDiaria, int numPortas) {
        super(modelo, placa, valorDiaria); // chama o construtor da classe pai
        this.numPortas = numPortas;
    }

    @Override
    public double calcularValorAluguel(int dias) {
        // Regra simples: valor da diária x dias, mais uma taxa fixa de seguro
        double taxaSeguro = 20.0;
        return (valorDiaria * dias) + taxaSeguro;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }

    public int getNumPortas() { return numPortas; }
}
