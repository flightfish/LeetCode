
public static void main(String[] args) {
        GraphW graphW = new GraphW(6);
        graphW.addEdge(0,1,10);
        graphW.addEdge(0,4,15);
        graphW.addEdge(1,2,15);
        graphW.addEdge(1,3,2);
        graphW.addEdge(3,2,1);
        graphW.addEdge(2,5,5);
        graphW.addEdge(3,5,12);
        graphW.addEdge(4,5,10);

        graphW.dijkstra(0,5);


}

class GraphW {
    public int v; // 顶点个数
    public LinkedList<Edge>[] adj; // 邻接表

    public GraphW(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t, int w) {
        this.adj[s].add(new Edge(s, t, w));

    }

    private class Edge {
        public int sid; // 边的起始顶点编号
        public int tid; // 边的终止顶点编号
        public int w; // 权重

        public Edge(int s, int t, int w) {
            this.sid = s;
            this.tid = t;
            this.w = w;
        }
    }

    // 下面这个类是为了dijkstra实现用的
    private class Vertex{
        public  int id; //顶点编号Id
        public int dist; // 从起始顶点到这个顶点的距离

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }

    public void dijkstra(int s, int t) { // 从顶点s到顶点t的最短路径
        int[] predecessor = new int[this.v]; // 用来还原最短路径
        Vertex[] vertexes = new Vertex[this.v];
        for (int i = 0; i < this.v; ++i) {
            vertexes[i] = new Vertex(i, Integer.MAX_VALUE);
        }
        PriorityQueue queue = new PriorityQueue(this.v);// 小顶堆
        boolean[] inqueue = new boolean[this.v]; // 标记是否进入过队列
        vertexes[s].dist = 0;
        queue.add(vertexes[s]);
        inqueue[s] = true;
        while (!queue.isEmpty()) {
            Vertex minVertex = queue.poll(); // 取堆顶元素并删除
            if (minVertex.id == t) break; // 最短路径产生了
            for (int i = 0; i < adj[minVertex.id].size(); ++i) {
                Edge e = adj[minVertex.id].get(i); // 取出一条minVetex相连的边
                Vertex nextVertex = vertexes[e.tid]; // minVertex-->nextVertex
                if (minVertex.dist + e.w < nextVertex.dist) { // 更新next的dist
                    nextVertex.dist = minVertex.dist + e.w;
                    predecessor[nextVertex.id] = minVertex.id;
                    if (inqueue[nextVertex.id]) {
                        queue.update(nextVertex); // 更新队列中的dist值
                    } else {
                        queue.add(nextVertex);
                        inqueue[nextVertex.id] = true;
                    }
                }
            }
        }
        // 输出最短路径
        System.out.print(s);
        print(s, t, predecessor);
    }

    private void print(int s, int t, int[] predecessor) {
        if (s == t) return;
        print(s, predecessor[t], predecessor);
        System.out.print("->" + t);
    }

    class PriorityQueue{//构建小顶堆
        Vertex[] nodes;
        private int count;//队列个数
        public PriorityQueue(int v){
            nodes = new Vertex[v+1];//小顶堆，数组从小标1开始，好计算
            this.count = 0;//初始0个元素
        }
        public Vertex poll(){
            Vertex v = nodes[1];//返回堆顶原始
            nodes[1] = nodes[count];//将最后一个元素添加到堆顶，自上而下堆化
            --count;
            heapifyUpToDown(1);//堆顶从上而下堆化
            return v;
        }
        public void add(Vertex vertex){
            nodes[++count] = vertex;
//            vertex.id = count;
            heapifyDownToUp(count);//从下往上堆化
        }
        public void update(Vertex vertex){
            //查找，并更新
            nodes[vertex.id].dist = vertex.dist;
            heapifyDownToUp(vertex.id);//从下往上堆化
        }
        public boolean isEmpty(){
            return this.count == 0 ? true : false;
        }
        //自上而下堆化
        private void heapifyUpToDown(int i) {
            while(i<=count){
                int maxPos = i;
                if((i*2)<=count && nodes[maxPos].dist > nodes[i*2].dist) maxPos = 2*i;
                else if((i*2+1)<=count && nodes[maxPos].dist > nodes[i*2+1].dist) maxPos = 2*i+1;
                else if(maxPos == i)break;
                swap(i,maxPos);//交换
                i = maxPos;
            }
        }
        //从下往上堆化
        private void heapifyDownToUp(int i) {
            while (i / 2 > 0 && nodes[i].dist < nodes[i / 2].dist) {
                swap(i,i/2);//交换
                i = i / 2;
            }
        }
        /**
         * 数据交换
         * @param i
         * @param maxPos
         */
        private void swap(int i, int maxPos) {
//            nodes[i].id = maxPos;//下标交换记录
//            nodes[maxPos].id = i;

            Vertex tmp = nodes[i];
            nodes[i] = nodes[maxPos];
            nodes[maxPos] = tmp;
        }
    }


}