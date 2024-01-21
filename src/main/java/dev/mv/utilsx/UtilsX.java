package dev.mv.utilsx;

import dev.mv.utilsx.check.Match;
import dev.mv.utilsx.check.MatchReturn;
import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.misc.ClassFinder;
import dev.mv.utilsx.nullHandler.NullHandler;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class UtilsX {
    private final static Map<String, Integer> counter = new HashMap<>();
    private final static Random random = new Random();

    private UtilsX() {}

    /**
     * Turn a list of directory names into a string path using the system default separator.
     *
     * @param dirs the list of directory names.
     * @return string path to the directory.
     */
    public static String getPath(String... dirs) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < dirs.length - 1; i++) {
            res.append(dirs[i]);
            res.append(File.separator);
        }
        res.append(dirs[dirs.length - 1]);
        return res.toString();
    }

    /**
     * Turn a list of directory names into a string path using the system default separator, starts with default separator.
     *
     * @param dirs the list of directory names.
     * @return string path to the directory starting with default separator.
     */
    public static String getInnerPath(String... dirs) {
        StringBuilder res = new StringBuilder(File.separator);
        for (int i = 0; i < dirs.length - 1; i++) {
            res.append(dirs[i]);
            res.append(File.separator);
        }
        res.append(dirs[dirs.length - 1]);
        return res.toString();
    }

    /**
     * Converts the given path string to the system-specific path format.
     *
     * @param path The path string to be converted.
     * @return The system-specific path string with the correct file separator.
     */

    public static String getSystemPath(String path) {
        return path.replaceAll("/|\\\\", File.separator);
    }

    /**
     * Concatenates all the strings provided without creating a lot of new strings, with a separator between the strings.
     *
     * @param strings   the strings to concatenate.
     * @param separator the separator between the strings.
     * @return the concatenated string.
     */
    public static String concat(String separator, Object... strings) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < strings.length - 1; i++) {
            res.append(strings[i].toString());
            res.append(separator);
        }
        res.append(strings[strings.length - 1]);
        return res.toString();
    }

    /**
     * Turns a radian value into a degree value.
     *
     * @param rad radian value.
     * @return degree value.
     */
    public static double radToDeg(double rad) {
        return rad * 57.29577951308232;
    }

    /**
     * Turns a radian value into a degree value.
     *
     * @param rad radian value.
     * @return degree value.
     */
    public static float radToDeg(float rad) {
        return (float) (rad * 57.29577951308232);
    }

    /**
     * Turns a degree value into a radian value.
     *
     * @param deg degree value.
     * @return radian value.
     */
    public static double degToRad(double deg) {
        return deg * 0.017453292519943295;
    }

    /**
     * Turns a degree value into a radian value.
     *
     * @param deg degree value.
     * @return radian value.
     */
    public static float degToRad(float deg) {
        return (float) (deg * 0.017453292519943295);
    }

    /**
     * Get the percentage from a value of a total.
     *
     * @param value the value.
     * @param total the total.
     * @return percentage.
     */
    public static float getPercent(float value, int total) {
        return ((float) value / (float) total * 100f);
    }

    /**
     * Get the value from a percentage of the total.
     *
     * @param percentage the percentage.
     * @param total      the total.
     * @return the value.
     */
    public static int getValue(float percentage, int total) {
        return (int) (percentage / 100f * (float) total);
    }

    /**
     * Get the square of the input value.
     *
     * @param value the value to be squared.
     * @return the squared value.
     */
    public static int square(int value) {
        return value * value;
    }

    /**
     * Get the square of the input value.
     *
     * @param value the value to be squared.
     * @return the squared value.
     */
    public static float square(float value) {
        return value * value;
    }

    /**
     * States if any of the objects match the first object using {@link Object#equals(Object)}.
     *
     * @param obj  the object to compare.
     * @param objs the objects to compare the main object against.
     * @return true if one of the objects is equal to the first object.
     */
    public static boolean isAnyOf(Object obj, Object... objs) {
        for (Object o : objs) {
            if (obj == null) {
                if (o == null) return true;
                continue;
            }
            if (obj.equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * States if any of the objects is null.
     *
     * @param objs the objects to check for null.
     * @return true if any of the objects is null.
     */
    public static boolean isAnyNull(Object... objs) {
        for (Object o : objs) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * States if the {@link String} contains any of the other strings using {@link String#contains(CharSequence)}.
     *
     * @param str  the main string.
     * @param strings the other strings.
     * @return true if the main string contains any of the other strings.
     */
    public static boolean containsAny(String str, String... strings) {
        if (str == null || strings == null) return false;
        for (String s : strings) {
            if (str.contains(s)) return true;
        }
        return false;
    }

    /**
     * Merge multiple lists into one list, returns the merged list as an {@link ArrayList ArrayList}.
     *
     * @param lists The lists to be merged.
     * @return {@link ArrayList} instance with all the merged lists.
     */
    public static <T> List<T> merge(List<T>... lists) {
        List<T> mergedList = new ArrayList<>();
        for (List<T> list : lists) {
            mergedList.addAll(list);
        }
        return mergedList;
    }

    /**
     * Merge multiple vecs into one vec, returns the merged vec as an {@link Vec}.
     *
     * @param vecs The vecs to be merged.
     * @return {@link Vec} instance with all the merged vecs.
     */
    public static <T> Vec<T> merge(Vec<T>... vecs) {
        Vec<T> mergedVec = new Vec<>();
        for (Vec<T> vec : vecs) {
            mergedVec.append(vec);
        }
        return mergedVec;
    }

    /**
     * Creates a null check instance on the object, allowing you to execute certain
     * code only if the object is null and other code only if it is not null.
     *
     * @param t the object to be null checked.
     * @return {@link NullHandler}, allows you to run functions based on the state of the object.
     */
    public static <T> NullHandler<T> ifNotNull(T t) {
        return new NullHandler<T>(t);
    }

    /**
     * Checks if a character is a letter OR a digit.
     *
     * @param c the char value.
     * @return true if the char is a digit or a letter.
     */
    public static boolean isCharLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') ||
            (c >= 'A' && c <= 'Z') ||
            (c >= '0' && c <= '9');
    }

    /**
     * Checks if a character is an ASCII character (this does NOT include extended ASCII).
     *
     * @param c the char value.
     * @return true if the char is a regular ASCII character.
     */
    public static boolean isCharAscii(char c) {
        return c <= 255;
    }

    /**
     * Clamps the value to the range specified by the minimum and maximum, if
     * it is lower than the minimum, the minimum is returned, if it is higher
     * than the maximum, the maximum is returned, otherwise, the value remains unchanged.
     *
     * @param value the value to clamp.
     * @param min   the minimum the value must be.
     * @param max   the maximum the value must be.
     * @return the value clamped to the range specified by the minimum and the maximum.
     */
    public static int clamp(int value, int min, int max) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     * Clamps the value to the range specified by the minimum and maximum, if
     * it is lower than the minimum, the minimum is returned, if it is higher
     * than the maximum, the maximum is returned, otherwise, the value remains unchanged.
     *
     * @param value the value to clamp.
     * @param min   the minimum the value must be.
     * @param max   the maximum the value must be.
     * @return the value clamped to the range specified by the minimum and the maximum.
     */
    public static float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     * Maps a value to a loop between the minimum and the maximum, inclusive. If the value is higher than
     * the maximum or lower than the minimum, it will overflow and go back to the minimum. This is
     * repeated until the value falls within range of the minimum or the maximum.
     * <p>
     * For example:
     * <ul>
     * <li>{@code overlap(3, 1, 5)} returns {@code 3}
     * <li>{@code overlap(6, 1, 5)} returns {@code 1}
     * <li>{@code overlap(8, 1, 5)} returns {@code 3}
     * <li>{@code overlap(-3, 1, 5)} returns {@code 2}
     * </ul>
     *
     * @param value the value to overlap.
     * @param min   the minimum for the value, inclusive.
     * @param max   the maximum for the value, inclusive.
     * @return the value after the overlap, within the boundary.
     */
    public static int overlap(int value, int min, int max) {
        if (value > max) {
            value = min + (value - max - 1) % (max - min + 1);
        } else if (value < min) {
            value = max - (min - value - 1) % (max - min + 1);
        }
        return value;
    }

    /**
     * Maps a value to a loop between the minimum and the maximum, inclusive. If the value is higher than
     * the maximum or lower than the minimum, it will overflow and go back to the minimum. This is
     * repeated until the value falls within range of the minimum or the maximum.
     * <p>
     * For example:
     * <ul>
     * <li>{@code overlap(3, 1, 5)} returns {@code 3}
     * <li>{@code overlap(6, 1, 5)} returns {@code 1}
     * <li>{@code overlap(8, 1, 5)} returns {@code 3}
     * <li>{@code overlap(-3, 1, 5)} returns {@code 2}
     * </ul>
     *
     * @param value the value to overlap.
     * @param min   the minimum for the value, inclusive.
     * @param max   the maximum for the value, inclusive.
     * @return the value after the overlap, within the boundary.
     */
    public static long overlap(long value, long min, long max) {
        if (value > max) {
            value = min + (value - max - 1) % (max - min + 1);
        } else if (value < min) {
            value = max - (min - value - 1) % (max - min + 1);
        }
        return value;
    }

    /**
     * Creates an int array with all the integers between 0 and the maximum, not including the maximum.
     *
     * @param max number to end add, exclusive.
     * @return filled array.
     */
    public static int[] range(int max) {
        if (max <= 0) throw new IllegalArgumentException("\"max\" can't be smaller than 0!");
        int[] ret = new int[max];
        for (int i = 0; i < max; i++) {
            ret[i] = i;
        }
        return ret;
    }

    /**
     * Creates an int array with all the integers between the minimum, inclusive, and maximum, exclusive.
     *
     * @param start number to start on, inclusive.
     * @param end   number to end add, exclusive.
     * @return filled array.
     */
    public static int[] range(int start, int end) {
        if (end <= start) throw new IllegalArgumentException("\"end\" can't be smaller than \"start\"!");
        int[] ret = new int[end - start];
        for (int i = start; i < end; i++) {
            ret[i - start] = i;
        }
        return ret;
    }

    /**
     * Returns an array of the specified length with copies of the object filling it.
     *
     * @param t     the object to fill the array.
     * @param times length of the array.
     * @return the filled array.
     * @throws ClassCastException if the type provided is a primitive type, and not an {@link Object}.
     */
    public static <T> T[] repeat(T t, int times) {
        T[] ret = (T[]) new Object[times];
        for (int i = 0; i < times; i++) {
            ret[i] = t;
        }
        return ret;
    }

    /**
     * Get all the classes in the current JVM classPath with a filter on the full name of the class.
     *
     * @param filter the filter, true to add the class, false to not add.
     * @return a {@link Vec} with all the classes as {@link Class<?>} objects.
     */
    public static Vec<Class<?>> getAllClasses(Predicate<String> filter) {
        try {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            return ClassFinder.findAllClasses().filter(filter).filterMap(name -> {
                try {
                    return Option.some(loader.loadClass(name));
                } catch (Throwable e) {
                    return Option.none();
                }
            }).collect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the next available id, starting from 0, for the use.
     *
     * @param use the use to associate the id with.
     * @return the id.
     */
    public static int nextId(String use) {
        Integer i = counter.get(use);
        if (i == null) {
            counter.put(use, 0);
            return 0;
        }
        counter.put(use, i + 1);
        return i + 1;
    }

    /**
     * Sets the id for the given use.
     *
     * @param use the use to associate the id with.
     */
    public static void setId(String use, int num) {
        counter.put(use, num);
    }

    /**
     * Resets the id to 0 for the given use.
     *
     * @param use the use to associate the id with.
     */
    public static void resetId(String use) {
        setId(use, 0);
    }

    /**
     * Gets the last used id, for the use.
     *
     * @param use the use to associate the id with.
     * @return the id.
     */

    public static int currentId(String use) {
        Integer i = counter.get(use);
        if (i == null) {
            counter.put(use, 0);
            return 0;
        }
        return i;
    }

    /**
     * Returns a {@link Match<T>} which checks the value provided.
     *
     * @param t the value.
     * @return The {@link Match<T>}.
     */
    public static <T> Match<T> match(T t) {
        return new Match<T>(t);
    }

    /**
     * Returns a {@link MatchReturn} which checks the value provided.
     *
     * @param t the value.
     * @return The {@link MatchReturn}.
     */
    public static <T, R> MatchReturn<T, R> matchReturn(T t) {
        return new MatchReturn<T, R>(t);
    }

    /**
     * Returns a random element from the given list.
     *
     * @param list The list to get a random element from.
     * @return A random element from the list.
     */
    public static <T> T random(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Returns a random element from the given vec.
     *
     * @param vec The vec to get a random element from.
     * @return A random element from the vec.
     */
    public static <T> T random(Vec<T> vec) {
        return vec.get(random.nextInt(vec.len()));
    }

    /**
     * Returns a random element from the given array.
     *
     * @param array The array to get a random element from.
     * @return A random element from the array.
     */
    public static <T> T random(T[] array) {
        return array[random.nextInt(array.length)];
    }

    /**
     * Maps the given value from the range [from, to] to the range [targetFrom, targetTo].
     * If the value is outside the range [from, to], the original value is returned.
     *
     * @param value      The value to map to the new range.
     * @param from       The lower bound of the original range.
     * @param to         The upper bound of the original range.
     * @param targetFrom The lower bound of the target range.
     * @param targetTo   The upper bound of the target range.
     * @return The mapped value within the range [targetFrom, targetTo], or the original value if outside the range [from, to].
     */
    public static float map(float value, float from, float to, float targetFrom, float targetTo) {
        if (value < from || value > to) return value;
        return ((value - from) * (targetTo - targetFrom) / (to - from)) + targetFrom;
    }

    /**
     * Casts all elements in the arr to the specified type.
     *
     * @param arr The source array to be cast.
     * @param target The target class the elements should be cast to.
     * @return The T[] with its cast elements.
     * @param <T> The type the elements should be cast to.
     */
    public static <T> T[] cast(Object[] arr, Class<T> target) {
        T[] ret = (T[]) Array.newInstance(target, arr.length);
        for (int i = 0; i < arr.length; i++) {
            ret[i] = (T) arr[i];
        }

        return ret;
    }

    /**
     * Repeats the given action a specified number of times.
     *
     * @param times  The number of times to repeat the action.
     * @param action The action to be repeated.
     */
    public static void repeat(int times, Runnable action) {
        for (int i = 0; i < times; i++) {
            action.run();
        }
    }

    /**
     * Repeats the given action a specified number of times, providing the current iteration index to the action.
     *
     * @param times  The number of times to repeat the action.
     * @param action The action to be repeated, accepting the current iteration index as a parameter.
     */
    public static void repeat(int times, IntConsumer action) {
        for (int i = 0; i < times; i++) {
            action.accept(i);
        }
    }

}
