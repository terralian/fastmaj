package com.github.terralian.fastmaj.test.yama;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.github.terralian.fastmaj.yama.worker.tenhou.TenhouYamaWorker;


/**
 * {@link TenhouLogYamaWorker} 测试集
 * <p/>
 * 由于手头没有旧版本的牌谱，所以这部分没测
 * 
 * @author terra.lian 
 */
public class TenhouYamaWorkerTest {

    /**
     * 官方提供的验证Data
     * 
     * @see http://blog.tenhou.net/article/174202532.html
     */
    @Test
    public void officalTest() throws NoSuchAlgorithmException {
        String seed =
                "mt19937ar-sha512-n288-base64,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";
        TenhouYamaWorker parser = new TenhouYamaWorker(seed);
        int[] yama = parser.getNextYama(1);
        // 官方提供的验证结果
        String predicalYama =
                "22,91,36,115,56,19,60,16,124,35,59,43,107,9,5,11,57,73,18,41,42,20,25,30,103,100,126,130,77,109,17,15,67,46,72,65,131,118,102,61,113,123,89,122,92,3,129,81,97,28,24,76,37,69,31,26,66,78,51,54,112,64,94,38,88,128,13,133,87,21,27,114,105,50,10,29,1,4,48,70,32,14,86,33,23,84,93,12,117,47,75,96,44,111,95,62,74,39,116,63,53,6,2,58,79,71,108,68,121,8,49,55,34,135,82,125,90,98,83,45,132,106,0,101,134,40,7,85,110,99,52,80,120,104,119,127";
        String[] predicalYamaArray = predicalYama.split(",");

        assertEquals(predicalYamaArray.length, yama.length);
        for (int i = 0; i < yama.length; i++) {
            assertEquals(Integer.valueOf(predicalYamaArray[i]).intValue(), yama[i]);
        }
    }

    /**
     * from https://tenhou.net/3/?log=2020050719gm-0009-0000-af2ddb14&tw=2
     */
    @Test
    public void sampleTest1() throws NoSuchAlgorithmException {
        String seed =
                "mt19937ar-sha512-n288-base64,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";
        TenhouYamaWorker parser = new TenhouYamaWorker(seed);
        int[] yama = parser.getNextYama(1);

        String predicalYama =
                "35,125,58,55,25,123,54,28,87,10,129,109,59,85,127,52,82,113,70,135,23,130,68,30,110,118,120,79,72,38,126,43,32,37,116,2,91,51,45,132,62,104,76,46,63,65,98,80,8,5,20,78,122,88,40,97,69,119,99,47,102,106,41,17,11,14,49,18,36,13,16,12,24,53,64,128,34,121,71,96,75,19,115,42,60,26,73,57,44,29,15,107,50,108,81,6,112,111,86,31,56,94,33,7,9,1,84,124,74,66,27,4,48,100,21,0,103,90,117,92,134,3,22,105,114,83,101,133,93,95,39,77,89,131,61,67";
        String[] predicalYamaArray = predicalYama.split(",");

        assertEquals(predicalYamaArray.length, yama.length);
        for (int i = 0; i < yama.length; i++) {
            assertEquals(Integer.valueOf(predicalYamaArray[i]).intValue(), yama[i]);
            // System.out.print(Encode34.toMarkString(yama[i] / 4) + ",");
        }
    }
}
