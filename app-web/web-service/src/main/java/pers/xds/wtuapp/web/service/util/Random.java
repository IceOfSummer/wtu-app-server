package pers.xds.wtuapp.web.service.util;

/**
 * @author DeSen Xu
 * @date 2022-12-19 15:45
 */
public class Random {

    private Random() {}

    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public static String generateCaptcha(int length) {
        int min = (int) Math.pow(10, length - 1);
        int max = (int) (Math.pow(10, length) - 1);
        return String.valueOf(randomInt(min, max));
    }


}
