package br.com.netshoes.neryandroidexam.helper;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by nery on 6/27/2015.
 */
public class PlayerUtilsTest {
    protected static final String URL = "https://d13yacurqjgara.cloudfront.net/users/3460/avatars/normal/f6debbb5044f838dcf78bc3299172657.jpg?1421783568";

    @Test
    public void avatarPath_shouldReturnValidUrl() {
        assertNotEquals(PlayerUtils.cleanAvatarPath(URL), "Failure on clear avatar path");
    }
}
