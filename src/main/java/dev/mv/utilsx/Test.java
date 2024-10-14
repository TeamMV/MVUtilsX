package dev.mv.utilsx;

import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Option;

@SuppressWarnings("unchecked")
public class Test {
    public static void main(String[] args) {
        Vec<Integer> vec = new Vec<>();

        vec.append(new Integer[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });

        System.out.println(vec.toDebugString());

        vec.push(13);

        System.out.println(vec.toDebugString());
    }
}
