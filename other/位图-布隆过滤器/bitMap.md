
# 布隆过滤器
>我们使用 K 个哈希函数，对同一个数字进行求哈希值，那会得到 K 个不同的哈希值，
>我们分别记作 X1​，X2​，X3​，…，XK​。我们把这 K 个数字作为位图中的下标，
>将对应的 BitMap[X1​]，BitMap[X2​]，BitMap[X3​]，…，BitMap[XK​]都设置成 true，也就是说，我们用 K 个二进制位，来表示一个数字的存在。
>当我们要查询某个数字是否存在的时候，我们用同样的 K 个哈希函数，对这个数字求哈希值，分别得到 Y1​，Y2​，Y3​，…，YK​。我们看这 K 个哈希值，
>对应位图中的数值是否都为 true，如果都是 true，则说明，这个数字存在，如果有其中任意一个不为 true，那就说明这个数字不存在。

## 布隆过滤器的误判有一个特点，那就是，它只会对存在的情况有误判。
如果某个数字经过布隆过滤器判断不存在，那说明这个数字真的不存在，不会发生误判；


# 假设我们有 1 亿个整数，数据范围是从 1 到 10 亿，如何快速并且省内存地给这 1 亿个数据从小到大排序？
传统的做法：1亿个整数，存储需要400M空间，排序时间复杂度最优 N×log(N)
使用位图算法：数字范围是1到10亿，用位图存储125M就够了，然后将1亿个数字依次添加到位图中，然后再将位图按下标从小到大输出值为1的下标，排序就完成了，时间复杂度为 N




```java

public class BitMap { // Java中char类型占16bit，也即是2个字节
  private char[] bytes;
  private int nbits;
  
  public BitMap(int nbits) {
    this.nbits = nbits;
    this.bytes = new char[nbits/16+1];
  }

  public void set(int k) {
    if (k > nbits) return;
    int byteIndex = k / 16;
    int bitIndex = k % 16;
    bytes[byteIndex] |= (1 << bitIndex);
  }

  public boolean get(int k) {
    if (k > nbits) return false;
    int byteIndex = k / 16;
    int bitIndex = k % 16;
    return (bytes[byteIndex] & (1 << bitIndex)) != 0;
  }
}
```