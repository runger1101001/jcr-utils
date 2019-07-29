package net.jaardvark.jcr.stress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LCG {

        public static List<Integer> generateSequence(int N) {
            List<Integer> result = new ArrayList<>();
            Random r = new Random(System.nanoTime());
            int M = nextLargestPowerOfTwo(N);
            int c = r.nextInt(M / 2) * 2 + 1; // make c any odd number between 0 and M
            int a = r.nextInt(M / 4) * 4 + 1; // M = 2^m, so make (a-1) divisible by all prime factors, and 4

            int start = r.nextInt(M);
            int x = start;
            do
            {
                x = (a * x + c) % M;
                if (x < N)
                    result.add(x);
            } while (x != start);
            return result;
        }

        public static int nextLargestPowerOfTwo(int n) {
            n |= (n >> 1);
            n |= (n >> 2);
            n |= (n >> 4);
            n |= (n >> 8);
            n |= (n >> 16);
            return (n + 1);
        }

        public static void main(String[] args) throws InterruptedException {
            for (int i=0;i<10;i++) {
                List<Integer> r = generateSequence(10);
                System.out.println(Arrays.toString(r.toArray()));
            }
        }

}
