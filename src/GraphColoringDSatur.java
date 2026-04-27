import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class GraphColoringDSatur {
    
    class VertexInfo {
        int sat;
        int deg;
        int vertex;
        
        VertexInfo(int sat, int deg, int vertex)
        {
            this.sat = sat;
            this.deg = deg;
            this.vertex = vertex;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VertexInfo that = (VertexInfo) o;
            return vertex == that.vertex;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(vertex);
        }
    }
    
    class MaxSat implements Comparator<VertexInfo> {
        @Override public int compare(VertexInfo lhs, VertexInfo rhs)
        {
            if (lhs.sat != rhs.sat) {
                return Integer.compare(rhs.sat, lhs.sat);
            }
            else if (lhs.deg != rhs.deg) {
                return Integer.compare(rhs.deg, lhs.deg);
            }
            else {
                return Integer.compare(rhs.vertex, lhs.vertex);
            }
        }
    }
    
    private final Graph graph;
    private int[] colors;
    private int[] coloringOrder;

    public GraphColoringDSatur(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph nao pode ser nulo");
        }
        this.graph = graph;
        this.colors = new int[0];
    }

    public Graph getGraph() {
        return graph;
    }

    public void color() {
        int u, i;
        int n = graph.V();
        coloringOrder = new int[n];
        int auxCont = 0;
        boolean[] used = new boolean[n];
        int[] c = new int[n];
        int[] d = new int[n];
        HashSet<Integer>[] adjCols = new HashSet[n];
        PriorityQueue<VertexInfo> Q
            = new PriorityQueue<>(new MaxSat());

        for (u = 0; u < n; u++) {
            c[u] = -1;
            d[u] = graph.degree(u);
            adjCols[u] = new HashSet<>();
            Q.add(new VertexInfo(0, d[u], u));
        }

        while (!Q.isEmpty()) {
            VertexInfo maxPtr = Q.poll();
            u = maxPtr.vertex;
            coloringOrder[auxCont++] = u;
            for (int v : graph.adj(u)) {
                if (c[v] != -1) {
                    used[c[v]] = true;
                }
            }
            for (i = 0; i < used.length; i++) {
                if (!used[i]) {
                    break;
                }
            }
            for (int v : graph.adj(u)) {
                if (c[v] != -1) {
                    used[c[v]] = false;
                }
            }
            c[u] = i;
            for (int v : graph.adj(u)) {
                if (c[v] == -1) {
                    Q.remove(new VertexInfo(adjCols[v].size(),
                                        d[v], v));
                    adjCols[v].add(i);
                    d[v]--;
                    Q.add(new VertexInfo(adjCols[v].size(),
                                    d[v], v));
                }
            }
        }

        this.colors = c;
    }

    public int getColor(int vertex) {
        return colors[vertex];
    }

    public int getColorCount() {
        int maxNum = -1;
        for(int i = 0; i < colors.length; i++) {
            if(colors[i] > maxNum) {
                maxNum = colors[i];
            }
        }
        return maxNum +1;
    }

    public int[] getColoringOrder() {
        return this.coloringOrder;
    }

    public boolean isValidColoring() {
        if(colors == null) return false;
        for(int v = 0; v < graph.V(); v++) {
            for(int vizinho : graph.adj(v)) {
                if(colors[v] == colors[vizinho]) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getLabel(int vertex) {
        return "Vértice: " + vertex + ", Cor: " + colors[vertex];
    }
}