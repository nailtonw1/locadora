


public class Moto extends Veiculo {

    private int cilindrada;

    public Moto(String modelo, String placa, double valorDiaria, int cilindrada) {
        super(modelo, placa, valorDiaria);
        this.cilindrada = cilindrada;
    }

    @Override
    public double calcularValorAluguel(int dias) {
        // Regra simples: valor da diária x dias, sem taxa extra
        // mas com 10% de desconto a partir de 5 dias
        double total = valorDiaria * dias;
        if (dias >= 5) {
            total = total * 0.9;
        }
        return total;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }

    public int getCilindrada() { return cilindrada; }
}
