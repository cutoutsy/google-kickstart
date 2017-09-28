package codejam;




import java.util.*;

public class Problem3 {
    // 顶点
    private List<Vertex> vertexs;
    // 边
    private int[][] edges;
    // 没有访问的顶点
    private PriorityQueue<Vertex> unVisited;

    public Problem3(List<Vertex> vertexs, int[][] edges) {
        this.vertexs = vertexs;
        this.edges = edges;
        initUnVisited();
    }

    // 初始化未访问顶点集合
    private void initUnVisited() {
        unVisited = new PriorityQueue<Vertex>();
        for (Vertex v : vertexs) {
            unVisited.add(v);
        }
    }

    // 搜索各顶点最短路径
    public void search() {
        while (!unVisited.isEmpty()) {
            Vertex vertex = unVisited.peek();
            vertex.setMarked(true);
            List<Vertex> neighbors = getNeighbors(vertex);
            updatesDistabce(vertex, neighbors);
            unVisited.poll();
            List<Vertex> temp = new ArrayList<>();
            while (!unVisited.isEmpty()) {
                temp.add(unVisited.poll());
            }
            for (Vertex v : temp) {
                unVisited.add(v);
            }
        }
    }

    // 更新所有邻居的最短路径
    private void updatesDistabce(Vertex vertex, List<Vertex> neighbors) {
        for (Vertex neighbor: neighbors) {
            int distance = getDistance(vertex, neighbor) + vertex.getPath();
            if (distance < neighbor.getPath()) {
                neighbor.setPath(distance);
            }
        }
    }

    private int getDistance(Vertex source, Vertex destination) {
        int sourceIndex = vertexs.indexOf(source);
        int destIndex = vertexs.indexOf(destination);
        return edges[sourceIndex][destIndex];
    }

    private List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        int position = vertexs.indexOf(vertex);
        Vertex neighbor = null;
        int distance;
        for (int i = 0; i < vertexs.size(); i++) {
            if (i == position) {
                continue;
            }
            distance = edges[position][i];
            if (distance < Integer.MAX_VALUE) {
                neighbor = vertexs.get(i);
                if (!neighbor.isMarked()) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    public static double calculateExpectedTimeSmallDataSet(int locations, int codejamGo, List<String> graph) {
        int[][] edges = new int[locations][locations];
        for (int i = 0; i < locations; i++) {
            for (int j = 0; j < locations; j++) {
                edges[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < graph.size(); i++) {
            String edge = graph.get(i);
            int source = Integer.valueOf(edge.split(" ")[0]);
            int destination = Integer.valueOf(edge.split(" ")[1]);
            int value = Integer.valueOf(edge.split(" ")[2]);
            edges[source - 1][destination - 1] = value;
            edges[destination - 1][source - 1] = value;
        }
        List<Vertex> vertexs = new ArrayList<>();

        for (int begin = 0; begin < locations; begin++) {
            vertexs.clear();
            for (int i = 0; i < locations; i++) {
                Vertex vertex = (i == begin) ? new Vertex(0) : new Vertex();
                vertexs.add(vertex);
            }

            Problem3 problem3 = new Problem3(vertexs, edges);
            problem3.search();
            for (int i = 0; i < vertexs.size(); i++) {
                edges[begin][i] = vertexs.get(i).getPath();
            }
        }

        double[][] dp = new double[codejamGo + 1][locations];
        Arrays.fill(dp[0], 0);

        for (int i = 1; i < codejamGo + 1; i++) {
            for (int j = 0; j < locations; j++) {
                double sum = 0;
                for (int k = 0; k < locations; k++) {
                    if (k != j) {
                        sum += dp[i - 1][k] + edges[j][k];
                    }
                }
                dp[i][j] = sum / (locations - 1);
            }
        }

        return dp[codejamGo][0];
    }

    public static double calculateExpectedTimeLargeDataSet(int locations, int codejamGo, List<String> graph) {
        int[][] edges = new int[locations][locations];
        for (int i = 0; i < locations; i++) {
            for (int j = 0; j < locations; j++) {
                edges[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < graph.size(); i++) {
            String edge = graph.get(i);
            int source = Integer.valueOf(edge.split(" ")[0]);
            int destination = Integer.valueOf(edge.split(" ")[1]);
            int value = Integer.valueOf(edge.split(" ")[2]);
            edges[source - 1][destination - 1] = value;
            edges[destination - 1][source - 1] = value;
        }
        List<Vertex> vertexs = new ArrayList<>();
        int[] distanceSum = new int[locations];
        for (int begin = 0; begin < locations; begin++) {
            for (int i = 0; i < locations; i++) {
                vertexs.add((i == begin) ? new Vertex(0) : new Vertex());
            }

            Problem3 problem3 = new Problem3(vertexs, edges);
            problem3.search();
            int totalDistance = 0;
            for (int i = 0; i < vertexs.size(); i++) {
                totalDistance += vertexs.get(i).getPath();
            }
            distanceSum[begin] = totalDistance;
            vertexs.clear();
        }

            double[][] zeroCodeJam = new double[locations + 1][1];
            for (int i = 0; i < zeroCodeJam.length; i++) {
                for (int j = 0; j < zeroCodeJam[i].length; j++) {
                    zeroCodeJam[i][j] = 0;
                }
            }
            zeroCodeJam[locations][0] = 1;
            double[][] matris = new double[locations + 1][locations + 1];
            for (int i = 0; i < matris.length; i++) {
                if (i != locations) {
                    for (int j = 0; j < matris[i].length; j++) {
                        if (i == j) {
                            matris[i][j] = 0;
                        } else if (j == locations) {
                            matris[i][j] = distanceSum[i] * 1.0/(locations - 1);
                        } else {
                            matris[i][j] = 1.0/(locations - 1);
                        }
                    }
                }
                if (i == locations) {
                    for (int j = 0; j < matris[i].length; j++) {
                        matris[i][j] = j == locations ? 1 : 0;
                    }
                }
            }

            matris = MatricQuickPower.multiplyPower(matris, codejamGo);
            double ans = 0;
            for (int i = 0; i < zeroCodeJam.length; i++) {
                ans += matris[0][i] * zeroCodeJam[i][0];
            }
            return ans;
    }

    public static void main(String[] args) {
        List<String> data = Utils.readFileToList("./data/C-large-practice.in", "UTF-8");
        int caseIndex = 1;
        int lineIndex = 1;
        while (lineIndex < data.size()) {
            String graphInfo = data.get(lineIndex);
            int locations = Integer.valueOf(graphInfo.split(" ")[0]);
            int edgeNums = Integer.valueOf(graphInfo.split(" ")[1]);
            int codejamGO = Integer.valueOf(graphInfo.split(" ")[2]);
            List<String> graph = new ArrayList<>();
            for (int i = 1; i <= edgeNums; i++) {
                graph.add(data.get(lineIndex + i));
            }
            double expectedTime = calculateExpectedTimeLargeDataSet(locations, codejamGO, graph);
            System.out.println("Case #" + caseIndex + ": " +  expectedTime);
            lineIndex += edgeNums + 1;
            caseIndex++;
        }
    }
}
