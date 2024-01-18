package dev.mv.utilsx;

import dev.mv.utilsx.generic.Pair;
import dev.mv.utilsx.sequence.Sequence;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Test {
    public static void main(String[] args) {
        Sequence<Pair<String, String>> seq = Sequence.once(new Pair<>("Hello", "world"));

        String str = Sequence
                .random()
                .bind(65, 122)
                .ints()
                .take(10)
                .map(x -> (char) (int) x)
                .fold("", (s, c) -> s + c)
                .getUnchecked();
        System.out.println(str);

        List<List<Integer>> w = Sequence
                .random()
                .bind(0, 100)
                .ints()
                .take(10)
                .windows(3)
                .map(Arrays::asList)
                .collect();

        System.out.println(w);

        List<Integer> l = Sequence.from(1, 2, 3).cycle().take(10).collect();
        System.out.println(l);
    }
}
