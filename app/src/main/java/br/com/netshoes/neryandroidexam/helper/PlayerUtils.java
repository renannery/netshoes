package br.com.netshoes.neryandroidexam.helper;

/**
 * Created by nery on 6/27/2015.
 */
public class PlayerUtils {
    public static String cleanAvatarPath(String path) {
        String avatarPath;
        try {
            avatarPath = path.substring(0, path.indexOf("?"));
        } catch (Exception e) {
            avatarPath = "Failure on clear avatar path";
        }
        return avatarPath;
    }
}
