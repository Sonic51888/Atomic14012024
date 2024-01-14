package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Main {
    static AtomicInteger length3 = new AtomicInteger(0);
    static AtomicInteger length4 = new AtomicInteger(0);
    static AtomicInteger length5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {


        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        final List<String> testString1 = new ArrayList<>();
        final List<String> testString2 = new ArrayList<>();
        final List<String> testString3 = new ArrayList<>();
        Thread thread1 = new Thread(() -> {
            IntStream.range(0, texts.length)
                    .forEach(x -> {
                        final boolean[] condition = {true};
                        int start = 0;
                        int end = texts[x].length() - 1;
                        while (condition[0]) {
                            if (texts[x].charAt(start) == texts[x].charAt(end)) {
                                if (start == end - 1 || start == end - 2) {
                                    if (texts[x].length() == 3) {
                                        length3.incrementAndGet();
                                    }
                                    if (texts[x].length() == 4) {
                                        length4.incrementAndGet();
                                    }
                                    if (texts[x].length() == 5) {
                                        length5.incrementAndGet();
                                    }
                                    testString1.add(texts[x]);
                                    break;
                                }
                                start += 1;
                                end -= 1;
                                condition[0] = true;
                            } else {
                                condition[0] = false;
                            }
                        }
                    });
        });

        Thread thread2 = new Thread(() -> {
            IntStream.range(0, texts.length)
                    .forEach(x -> {
                        final boolean[] condition = {true};
                        int start = 0;
                        while (condition[0]) {
                            if (texts[x].charAt(start) == texts[x].charAt(start + 1)) {
                                if (start == texts[x].length() - 2) {
                                    if (texts[x].length() == 3) {
                                        length3.incrementAndGet();
                                    }
                                    if (texts[x].length() == 4) {
                                        length4.incrementAndGet();
                                    }
                                    if (texts[x].length() == 5) {
                                        length5.incrementAndGet();
                                    }
                                    testString2.add(texts[x]);
                                    break;
                                }
                                start += 1;
                                condition[0] = true;
                            } else {
                                condition[0] = false;
                            }
                        }
                    });
        });

        Thread thread3 = new Thread(() -> {
            IntStream.range(0, texts.length)
                    .forEach(x -> {
                        final boolean[] condition = {true};
                        int start = 0;
                        while (condition[0]) {
                            if (texts[x].charAt(start) < texts[x].charAt(start + 1) || texts[x].charAt(start) == texts[x].charAt(start + 1)) {
                                if (start == texts[x].length() - 2) {
                                    if (texts[x].length() == 3) {
                                        length3.incrementAndGet();
                                    }
                                    if (texts[x].length() == 4) {
                                        length4.incrementAndGet();
                                    }
                                    if (texts[x].length() == 5) {
                                        length5.incrementAndGet();
                                    }
                                    testString3.add(texts[x]);
                                    break;
                                }
                                start += 1;
                                condition[0] = true;
                            } else {
                                condition[0] = false;
                            }
                        }
                    });
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.printf("Красивых слов с длиной 3: %d шт\n", length3.get());
        System.out.printf("Красивых слов с длиной 4: %d шт\n", length4.get());
        System.out.printf("Красивых слов с длиной 5: %d шт\n\n", length5.get());
        System.out.println("Часть выборки по первому критерию: " + testString1.subList(0, 15));
        System.out.println("Часть выборки по второму критерию: " + testString2.subList(0, 15));
        System.out.println("Часть выборки по третьему критерию: " + testString3.subList(0, 15));
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}