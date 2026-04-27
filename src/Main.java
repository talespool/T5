public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException(
                    "informe o arquivo de entrada. Ex.: java Main ../dados/brasil.txt"
            );
        }

        In in = new In(args[0]);
        Graph graph = new Graph(in);
        GraphColoringDSatur dsatur = new GraphColoringDSatur(graph);

        StdOut.println("Grafo carregado:");
        StdOut.println(graph);
        StdOut.println();

        dsatur.color();

        int[] order = dsatur.getColoringOrder();
        StdOut.print("Ordem de coloração: ");
        for (int i = 0; i < order.length; i++) {
            StdOut.print(order[i] + (i < order.length - 1 ? " -> " : ""));
        }
        StdOut.println("\n");

        StdOut.println("Resultado da Coloração:");
        for (int v = 0; v < graph.V(); v++) {
            StdOut.println(dsatur.getLabel(v));
        }

        StdOut.println("\nTotal de cores: " + dsatur.getColorCount());
        
        if (dsatur.isValidColoring()) {
            StdOut.println("A coloração é VÁLIDA.");
        } else {
            StdOut.println("A coloração é INVÁLIDA.");
        }
    }
}