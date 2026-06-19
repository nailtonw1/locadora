


public class Aluguel {

    private int id;
    private Cliente cliente;
    private Veiculo veiculo;
    private int dias;
    private double valorTotal;

    public Aluguel(Cliente cliente, Veiculo veiculo, int dias) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dias = dias;
        // POLIMORFISMO em ação: o método chamado é o mesmo,
        // mas o comportamento muda dependendo se "veiculo" é um Carro ou uma Moto.
        this.valorTotal = veiculo.calcularValorAluguel(dias);
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente.getNome()
                + " | Veículo: " + veiculo.getModelo() + " (" + veiculo.getTipo() + ")"
                + " | Dias: " + dias
                + " | Valor Total: R$ " + String.format("%.2f", valorTotal);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public Veiculo getVeiculo() { return veiculo; }
    public int getDias() { return dias; }
    public double getValorTotal() { return valorTotal; }
}
