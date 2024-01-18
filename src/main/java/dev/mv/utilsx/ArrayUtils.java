package dev.mv.utilsx;

import java.lang.reflect.Array;

public class ArrayUtils {

    public static final float phi = 1.6180339887498948482f;
    public static final float inversePhi = 1f / phi;

    /**
     * Creates a new array with the contents of the input in reverse order.
     * @param bytes input array.
     * @return A new reversed array.
     */
    public static byte[] flipped(byte[] bytes) {
        byte[] flipped = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            flipped[i] = bytes[bytes.length - 1 - i];
        }
        return flipped;
    }

    /**
     * Reverses the input array, without making any new arrays.
     * @param bytes input array.
     * @return The input array.
     */
    public static byte[] flip(byte[] bytes) {
        byte temp;
        for (int i = 0; i < bytes.length / 2; i++) {
            temp = bytes[i];
            bytes[i] = bytes[bytes.length - i - 1];
            bytes[bytes.length - i - 1] = temp;
        }
        return bytes;
    }

    /**
     * Creates a new array with the contents of the input in reverse order.
     * @param shorts input array.
     * @return A new reversed array.
     */
    public static short[] flipped(short[] shorts) {
        short[] flipped = new short[shorts.length];
        for (int i = 0; i < shorts.length; i++) {
            flipped[i] = shorts[shorts.length - 1 - i];
        }
        return flipped;
    }

    /**
     * Reverses the input array, without making any new arrays.
     * @param shorts input array.
     * @return The input array.
     */
    public static short[] flip(short[] shorts) {
        short temp;
        for (int i = 0; i < shorts.length / 2; i++) {
            temp = shorts[i];
            shorts[i] = shorts[shorts.length - i - 1];
            shorts[shorts.length - i - 1] = temp;
        }
        return shorts;
    }

    /**
     * Creates a new array with the contents of the input in reverse order.
     * @param ints input array.
     * @return A new reversed array.
     */
    public static int[] flipped(int[] ints) {
        int[] flipped = new int[ints.length];
        for (int i = 0; i < ints.length; i++) {
            flipped[i] = ints[ints.length - 1 - i];
        }
        return flipped;
    }

    /**
     * Reverses the input array, without making any new arrays.
     * @param ints input array.
     * @return The input array.
     */
    public static int[] flip(int[] ints) {
        int temp;
        for (int i = 0; i < ints.length / 2; i++) {
            temp = ints[i];
            ints[i] = ints[ints.length - i - 1];
            ints[ints.length - i - 1] = temp;
        }
        return ints;
    }

    /**
     * Creates a new array with the contents of the input in reverse order.
     * @param longs input array.
     * @return A new reversed array.
     */
    public static long[] flipped(long[] longs) {
        long[] flipped = new long[longs.length];
        for (int i = 0; i < longs.length; i++) {
            flipped[i] = longs[longs.length - 1 - i];
        }
        return flipped;
    }

    /**
     * Reverses the input array, without making any new arrays.
     * @param longs input array.
     * @return The input array.
     */
    public static long[] flip(long[] longs) {
        long temp;
        for (int i = 0; i < longs.length / 2; i++) {
            temp = longs[i];
            longs[i] = longs[longs.length - i - 1];
            longs[longs.length - i - 1] = temp;
        }
        return longs;
    }

    /**
     * Creates a new array with the contents of the input in reverse order.
     * @param floats input array.
     * @return A new reversed array.
     */
    public static float[] flipped(float[] floats) {
        float[] flipped = new float[floats.length];
        for (int i = 0; i < floats.length; i++) {
            flipped[i] = floats[floats.length - 1 - i];
        }
        return flipped;
    }

    /**
     * Reverses the input array, without making any new arrays.
     * @param floats input array.
     * @return The input array.
     */
    public static float[] flip(float[] floats) {
        float temp;
        for (int i = 0; i < floats.length / 2; i++) {
            temp = floats[i];
            floats[i] = floats[floats.length - i - 1];
            floats[floats.length - i - 1] = temp;
        }
        return floats;
    }
    /**
     * Creates a new array with the contents of the input in reverse order.
     * @param doubles input array.
     * @return A new reversed array.
     */
    public static double[] flipped(double[] doubles) {
        double[] flipped = new double[doubles.length];
        for (int i = 0; i < doubles.length; i++) {
            flipped[i] = doubles[doubles.length - 1 - i];
        }
        return flipped;
    }

    /**
     * Reverses the input array, without making any new arrays.
     * @param doubles input array.
     * @return The input array.
     */
    public static double[] flip(double[] doubles) {
        double temp;
        for (int i = 0; i < doubles.length / 2; i++) {
            temp = doubles[i];
            doubles[i] = doubles[doubles.length - i - 1];
            doubles[doubles.length - i - 1] = temp;
        }
        return doubles;
    }

    /**
     * Creates a new array with the contents of the input in reverse order.
     * @param booleans input array.
     * @return A new reversed array.
     */
    public static boolean[] flipped(boolean[] booleans) {
        boolean[] flipped = new boolean[booleans.length];
        for (int i = 0; i < booleans.length; i++) {
            flipped[i] = booleans[booleans.length - 1 - i];
        }
        return flipped;
    }

    /**
     * Reverses the input array, without making any new arrays.
     * @param booleans input array.
     * @return The input array.
     */
    public static boolean[] flip(boolean[] booleans) {
        boolean temp;
        for (int i = 0; i < booleans.length / 2; i++) {
            temp = booleans[i];
            booleans[i] = booleans[booleans.length - i - 1];
            booleans[booleans.length - i - 1] = temp;
        }
        return booleans;
    }

    /**
     * Creates a new array with the contents of the input in reverse order.
     * @param data input array.
     * @return A new reversed array.
     */
    public static <T> T[] flipped(T[] data) {
        T[] flipped = (T[]) Array.newInstance(data.getClass().getComponentType(), data.length);
        for (int i = 0; i < data.length; i++) {
            flipped[i] = data[data.length - i - 1];
        }
        return flipped;
    }

    /**
     * Reverses the input array, without making any new arrays.
     * @param data input array.
     * @return The input array.
     */
    public static <T> T[] flip(T[] data) {
        T temp;
        for (int i = 0; i < data.length / 2; i++) {
            temp = data[i];
            data[i] = data[data.length - i - 1];
            data[data.length - i - 1] = temp;
        }
        return data;
    }

    /**
     * Turn a {@link Float} array into a primitive float array.
     *
     * @param array the {@link Float} array.
     * @return a primitive float array with the copied data.
     */
    public static float[] toPrimitive(Float[] array) {
        float[] ret = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a {@link Double} array into a primitive double array.
     *
     * @param array the {@link Double} array.
     * @return a primitive double array with the copied data.
     */
    public static double[] toPrimitive(Double[] array) {
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a {@link Integer} array into a primitive int array.
     *
     * @param array the {@link Integer} array.
     * @return a primitive int array with the copied data.
     */
    public static int[] toPrimitive(Integer[] array) {
        int[] ret = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a {@link Long} array into a primitive long array.
     *
     * @param array the {@link Long} array.
     * @return a primitive long array with the copied data.
     */
    public static long[] toPrimitive(Long[] array) {
        long[] ret = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a {@link Byte} array into a primitive byte array.
     *
     * @param array the {@link Byte} array.
     * @return a primitive byte array with the copied data.
     */
    public static byte[] toPrimitive(Byte[] array) {
        byte[] ret = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a {@link Character} array into a primitive char array.
     *
     * @param array the {@link Character} array.
     * @return a primitive char array with the copied data.
     */
    public static char[] toPrimitive(Character[] array) {
        char[] ret = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a {@link Boolean} array into a primitive boolean array.
     *
     * @param array the {@link Boolean} array.
     * @return a primitive boolean array with the copied data.
     */
    public static boolean[] toPrimitive(Boolean[] array) {
        boolean[] ret = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a {@link Short} array into a primitive short array.
     *
     * @param array the {@link Short} array.
     * @return a primitive short array with the copied data.
     */
    public static short[] toPrimitive(Short[] array) {
        short[] ret = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a primitive float array into a {@link Float} array.
     *
     * @param array the primitive float array.
     * @return a {@link Float} array with the copied data.
     */
    public static Float[] toObject(float[] array) {
        Float[] ret = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a primitive double array into a {@link Double} array.
     *
     * @param array the primitive double array.
     * @return a {@link Double} array with the copied data.
     */
    public static Double[] toObject(double[] array) {
        Double[] ret = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a primitive int array into a {@link Integer} array.
     *
     * @param array the primitive int array.
     * @return a {@link Integer} array with the copied data.
     */
    public static Integer[] toObject(int[] array) {
        Integer[] ret = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a primitive long array into a {@link Long} array.
     *
     * @param array the primitive long array.
     * @return a {@link Long} array with the copied data.
     */
    public static Long[] toObject(long[] array) {
        Long[] ret = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a primitive byte array into a {@link Byte} array.
     *
     * @param array the primitive byte array.
     * @return a {@link Byte} array with the copied data.
     */
    public static Byte[] toObject(byte[] array) {
        Byte[] ret = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a primitive char array into a {@link Character} array.
     *
     * @param array the primitive char array.
     * @return a {@link Character} array with the copied data.
     */
    public static Character[] toObject(char[] array) {
        Character[] ret = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a primitive boolean array into a {@link Boolean} array.
     *
     * @param array the primitive boolean array.
     * @return a {@link Boolean} array with the copied data.
     */
    public static Boolean[] toObject(boolean[] array) {
        Boolean[] ret = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Turn a primitive short array into a {@link Short} array.
     *
     * @param array the primitive short array.
     * @return a {@link Short} array with the copied data.
     */
    public static Short[] toObject(short[] array) {
        Short[] ret = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

}
