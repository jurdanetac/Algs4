import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException();

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) rq.enqueue(StdIn.readString());

        int n = rq.size();

        if (k < 0 || k > n) throw new IllegalArgumentException();

        for (int i = 0; i < k; i++) System.out.println(rq.dequeue());
    }
}
