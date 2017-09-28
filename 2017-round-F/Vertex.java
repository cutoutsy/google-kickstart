package codejam;

public class Vertex implements Comparable<Vertex>{
    // 最短路径长度
    private int path;
    // 节点是否已经出列（是否已经处理完毕）
    private boolean isMarked;

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public Vertex() {
        this.path = Integer.MAX_VALUE;
        this.isMarked = false;
    }

    public Vertex(int path) {
        this.path = path;
        this.isMarked = false;
    }

    @Override
    public int compareTo(Vertex o) {
        return o.path > path ? -1 : 1;
    }

    public void setMarked(boolean mark) {
        this.isMarked = mark;
    }

    public boolean isMarked() {
        return this.isMarked;
    }
}
