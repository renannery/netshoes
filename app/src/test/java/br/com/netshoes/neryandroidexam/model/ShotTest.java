package br.com.netshoes.neryandroidexam.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by nery on 6/26/2015.
 */
public class ShotTest {

    @Test
    public void shotTest_shouldReturnShotObject(){
        Player player = new Player();
        assertTrue(player instanceof  Player);
    }

}
