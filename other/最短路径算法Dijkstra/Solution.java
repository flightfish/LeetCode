



private class Vertex {
    public int id; // 顶点编号ID
    public int dist; // 从起始顶点，到这个顶点的距离，也就是g(i)
    public int f; // 新增：f(i)=g(i)+h(i)
    public int x, y; // 新增：顶点在地图中的坐标（x, y）
    public Vertex(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.f = Integer.MAX_VALUE;
        this.dist = Integer.MAX_VALUE;
    }
}
    // Graph类的成员变量，在构造函数中初始化
    Vertex[] vertexes = new Vertex[this.v];
    // 新增一个方法，添加顶点的坐标
    public void addVetex(int id, int x, int y) {
        vertexes[id] = new Vertex(id, x, y)
    }



    public void astar(int s, int t) { // 从顶点s到顶点t的路径
        int[] predecessor = new int[this.v]; // 用来还原路径
        // 按照vertex的f值构建的小顶堆，而不是按照dist
        PriorityQueue queue = new PriorityQueue(this.v);
        boolean[] inqueue = new boolean[this.v]; // 标记是否进入过队列
        vertexes[s].dist = 0;
        vertexes[s].f = 0;
        queue.add(vertexes[s]);
        inqueue[s] = true;
        while (!queue.isEmpty()) {
            Vertex minVertex = queue.poll(); // 取堆顶元素并删除
            for (int i = 0; i < adj[minVertex.id].size(); ++i) {
                Edge e = adj[minVertex.id].get(i); // 取出一条minVetex相连的边
                Vertex nextVertex = vertexes[e.tid]; // minVertex-->nextVertex
                if (minVertex.dist + e.w < nextVertex.dist) { // 更新next的dist,f
                    nextVertex.dist = minVertex.dist + e.w;
                    nextVertex.f
                            = nextVertex.dist+hManhattan(nextVertex, vertexes[t]);
                    predecessor[nextVertex.id] = minVertex.id;
                    if (inqueue[nextVertex.id] == true) {
                        queue.update(nextVertex);
                    } else {
                        queue.add(nextVertex);
                        inqueue[nextVertex.id] = true;
                    }
                }
                if (nextVertex.id == t) { // 只要到达t就可以结束while了
                    queue.clear(); // 清空queue，才能推出while循环
                    break;
                }
            }
        }
        // 输出路径
        System.out.print(s);
        print(s, t, predecessor); // print函数请参看Dijkstra算法的实现
    }


