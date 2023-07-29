package com.github.terralian.fastmaj.test.yama;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yama.IYama;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.worker.tenhou.TenhouYamaWorker;
import com.github.terralian.fastmaj.yama.Yama;

/**
 * {@link Yama}的测试
 * <p/>
 * 牌山的构建基于{@link TenhouYamaWorker}
 * 
 * @author terra.lian 
 */
public class YamaTest {

    /**
     * 测试
     */
    @Test
    public void testDeals() {
        // 2022042310gm-00a9-0000-5e2eefdb&tw=1
        String seed =
                "mt19937ar-sha512-n288-base64,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";
        int round = 0;

        // 东1局
        IYama yama = createYama(seed, round);
        List<ITehai> tehais = yama.deals(0);
        assertEquals("27m067p12s112447z", EncodeMark.encode(tehais.get(0)));
        assertEquals("467m238p577s1133z", EncodeMark.encode(tehais.get(1)));
        assertEquals("127899m3457p14s5z", EncodeMark.encode(tehais.get(2)));
        assertEquals("2688m569p347s357z", EncodeMark.encode(tehais.get(3)));

        round = 1;
        yama = createYama(seed, round);
        tehais = yama.deals(0);
        assertEquals("1289m149p17s2345z", EncodeMark.encode(tehais.get(0)));
        assertEquals("2466m113689p39s1z", EncodeMark.encode(tehais.get(1)));
        assertEquals("233p133457s1334z", EncodeMark.encode(tehais.get(2)));
        assertEquals("16m26688p1566s23z", EncodeMark.encode(tehais.get(3)));

        round = 2;
        yama = createYama(seed, round);
        tehais = yama.deals(0);
        assertEquals("335m378p12347s13z", EncodeMark.encode(tehais.get(0)));
        assertEquals("4477m2459p9s4456z", EncodeMark.encode(tehais.get(1)));
        assertEquals("6m337p248s235677z", EncodeMark.encode(tehais.get(2)));
        assertEquals("1467m11p15889s26z", EncodeMark.encode(tehais.get(3)));

        // 东二局
        round = 3;
        yama = createYama(seed, round);
        tehais = yama.deals(1);
        assertEquals("5668m479p18s2577z", EncodeMark.encode(tehais.get(1)));
        assertEquals("12367m3p1136s147z", EncodeMark.encode(tehais.get(2)));
        assertEquals("33m3789p409s1235z", EncodeMark.encode(tehais.get(3)));
        assertEquals("05m2344p356689s4z", EncodeMark.encode(tehais.get(0)));

        round = 4;
        yama = createYama(seed, round);
        tehais = yama.deals(1);
        assertEquals("128m5589p3368s26z", EncodeMark.encode(tehais.get(1)));
        assertEquals("147m1406p26778s2z", EncodeMark.encode(tehais.get(2)));
        assertEquals("13309m2278p234s3z", EncodeMark.encode(tehais.get(3)));
        assertEquals("2669m156p59s2357z", EncodeMark.encode(tehais.get(0)));

        round = 5;
        yama = createYama(seed, round);
        tehais = yama.deals(1);
        assertEquals("2444m19p338s1237z", EncodeMark.encode(tehais.get(1)));
        assertEquals("679m1p13689s2234z", EncodeMark.encode(tehais.get(2)));
        assertEquals("11236m208p0s1155z", EncodeMark.encode(tehais.get(3)));
        assertEquals("1169m228p1246s36z", EncodeMark.encode(tehais.get(0)));

        // 东3局
        round = 6;
        yama = createYama(seed, round);
        tehais = yama.deals(2);
        assertEquals("2247m14p2359s456z", EncodeMark.encode(tehais.get(2)));
        assertEquals("368m115p189s2345z", EncodeMark.encode(tehais.get(3)));
        assertEquals("13889m5689p1s247z", EncodeMark.encode(tehais.get(0)));
        assertEquals("14m2389p4568s146z", EncodeMark.encode(tehais.get(1)));

        round = 7;
        yama = createYama(seed, round);
        tehais = yama.deals(2);
        assertEquals("5668m2p2359s5677z", EncodeMark.encode(tehais.get(2)));
        assertEquals("22567m68p12s2336z", EncodeMark.encode(tehais.get(3)));
        assertEquals("127m148p23448s34z", EncodeMark.encode(tehais.get(0)));
        assertEquals("134m67p13578s244z", EncodeMark.encode(tehais.get(1)));

        // 东4局
        round = 8;
        yama = createYama(seed, round);
        tehais = yama.deals(3);
        assertEquals("089m457p16679s34z", EncodeMark.encode(tehais.get(3)));
        assertEquals("349m123p169s1357z", EncodeMark.encode(tehais.get(0)));
        assertEquals("1288m178p288s145z", EncodeMark.encode(tehais.get(1)));
        assertEquals("13569m3p34s14677z", EncodeMark.encode(tehais.get(2)));

        // 南1局
        round = 9;
        yama = createYama(seed, round);
        tehais = yama.deals(0);
        assertEquals("28m479p12678s157z", EncodeMark.encode(tehais.get(0)));
        assertEquals("1689m1889p1s2335z", EncodeMark.encode(tehais.get(1)));
        assertEquals("33459m2369p7s237z", EncodeMark.encode(tehais.get(2)));
        assertEquals("567m479p249s4577z", EncodeMark.encode(tehais.get(3)));

        // 南2局
        round = 10;
        yama = createYama(seed, round);
        tehais = yama.deals(1);
        assertEquals("15679m257p4s2267z", EncodeMark.encode(tehais.get(1)));
        assertEquals("2789m8p126669s14z", EncodeMark.encode(tehais.get(2)));
        assertEquals("23488m17p5789s14z", EncodeMark.encode(tehais.get(3)));
        assertEquals("156m4479p1258s57z", EncodeMark.encode(tehais.get(0)));

        // 南3局
        round = 11;
        yama = createYama(seed, round);
        tehais = yama.deals(2);
        assertEquals("23m47p149s123467z", EncodeMark.encode(tehais.get(2)));
        assertEquals("29m1235689p4089s", EncodeMark.encode(tehais.get(3)));
        assertEquals("114469m256p26s35z", EncodeMark.encode(tehais.get(0)));
        assertEquals("3467m19p3579s677z", EncodeMark.encode(tehais.get(1)));

        // 南4局
        round = 12;
        yama = createYama(seed, round);
        tehais = yama.deals(3);
        assertEquals("12349m479p248s45z", EncodeMark.encode(tehais.get(3)));
        assertEquals("6m24678p123568s5z", EncodeMark.encode(tehais.get(0)));
        assertEquals("23378m279p178s67z", EncodeMark.encode(tehais.get(1)));
        assertEquals("179m58p367s13357z", EncodeMark.encode(tehais.get(2)));
    }

    /**
     * 测试从牌山摸一下枚牌
     */
    @Test
    public void testNextHai() {
        // 2022042310gm-00a9-0000-5e2eefdb&tw=1
        String seed =
                "mt19937ar-sha512-n288-base64,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";
        // 取南3局
        int round = 11;
        IYama yama = createYama(seed, round);
        yama.deals(3);
        String yamaString = "7p,4p,8m,1z,"
                + "7s,5s,5s,2m,"
                + "4z,1s,3s,1p,"
                + "4z,1m,2z,6s,"
                + "2p,6s,7s,9m,"
                + "5z,2s,9p,7m,"
                + "8s,8s,2m,6m,"
                + "8m,6m,2s,0p,"
                + "7z,1p,7m,3m,"
                + "3m,3p,0m,1z,"
                + "8m,8p,2z,7p,"
                + "8m,7s,8p,8p,"
                + "1s,4s,2s,1s,"
                + "3z,2z,2p,8s,"
                + "6p,7p,3s,5z,"
                + "4z,6z,3z,4p,"
                + "6p,9p,1z,4s,"
                + "7m,6z";
        String[] yamaMarks = yamaString.split(",");
        int countdown = 70;
        for (String haiMark : yamaMarks) {
            countdown--;
            IHai hai = yama.nextHai();
            assertEquals(haiMark, EncodeMark.encode(hai));
            assertEquals(countdown, yama.getCountdown());
        }
        assertEquals(0, yama.getCountdown());
    }


    @Test
    public void testGetDora() {
        // 2022042310gm-00a9-0000-5e2eefdb&tw=1
        String seed =
                "mt19937ar-sha512-n288-base64,Z9wyfRPuBlwECJk4igSEQOtYauqZllP8V4iX5/CNgCKvxEkrFKHlFkLpOwmB/4dR+W77Xq0SW7CebFSsKnr+0nY6W5ct+iER8tXHdcUhJCrUWIVaJHO549Z9lyOL91CcWuwhV+8AQqFBmHMtmYbQfhys/P4xCcxE8ZmwuLS4QMbrJ04LXOvFtAJs8rIkkEngWB1HKD64ngB47kV1vTTh1iYF196jgS3UL0i8ex/yN1LLpUghF1gRHzOJUmc7AkXxq0GuskCvYpM9o+oWW3sO0HwJ2jzmHlOzb/b+c2PFipvTQ0vhlyIrkf2Bnj++D732Rpd6TMX5l77auSggE1B05mOsUnJn6d3MiVZjyaH05I8uAVfQU+EF4t5AAr5QEloIDeGWyG1dp1tfIOGCV0JLktysG3HSnHwNDM/VUMleKbO7RAhjXfqbE3Cx/kKg8vJRjnZvEucJMqVwGRYV4H7hUaBaneL+LMMhZ4k/m5oQ7FeeTWnSfRJekbouy7XOlKclKFilVvU6ZHsLKytMIcVNDVIdBDpNW1rAP049HBrbTlvarSPmr7il8RSRujGcaD/QYHc74q0LOwZp+LRFVAH0ZW9zCWYxLg5JynFWHrneTXq99MtopcXJTt+jmCmXOiV/KwvvKVJw1ZiZqq1dP7y58zovOXOV8YN5t5U2h+qfzHdOtzuQjR/Eoqe1o4QDuWQcYgjoBCbuXXauNuvSDnoeEy+m+3GoKWiIu5MNmmHTxa/ksNYZtQ3ZyKRvLZw53zi+LDWdozQQd4kQNEXkAwIe/NzBL3JJJcK6PaZVqQkD8vLBT6iJEjSmtJme63Ajgz/rELO3E/d4dTKSRFH+9ynX0fN9XOWdwuBnfdD3gJ3QU9Fw++UybwpbxlJNqY53IM542S7FbVHYld8SXBqpb8C+MWspeB3W/+5KiLYVEjB1RpLm80kK56Z+EYzwg1ygeO8KEa1dXcfm93Vuk4Qcue+oJFX17sfIwQ91OvmUtiT0ipI6zLpgw8Lg6d0HNV3emWEDzksI/xi0BK0VIGVAoSqDGYfGUSLaRlsYK/Vrzrovlpp9gLUfZI5oYwU5ruT3GvZqeFsPHCQ3H4jnN+kvVkM9OMwKx0L/TFAzgGWxNv2tQpjIv2GCT3mXT8ZW3ySoLBwpIkG+0SzGuwjhGeLyrJJ5lLMguuP0jaboP/k1U8XS0tw8SuMSNsCIBWI4BKxpWuwDKKJk8t4mdcTuJTJbYfbPMEJmsQJ55R82kQ3gBlsYkajUNzfGJij6qnBV0pDql/mx0zFFxKm/evhpBtqRNlZ+3IqFdxMOSQK9A7sI4M5xsV86Kp3JTqnsyOrI0kJte/5yDrhAoH7TzwGBxQzMrkR/ePxQ0Lv6yPlQvWfvjN3eHt8kCsHVxnEyVAUnhQwiycCYLlHI3fV+3FRkaiuaQAmejCs6er2HJEdEQje4kzvwy3I3S6rbalJDZVLIGw5Uu0oi8ErM2SFyXukNJRKnJoq6AOjZcEQib2V2Sc5pmHWTS/L6+lFXGWOkp2nQ2B4VXbTFzeTg7cqWzhniwP5JQhf2iT/M/2BxVWVU+rdbNDjWXu8nb9SecdCVD+tCEO58eaEX11pnI36wyOWaw4HyGdCjxDvBFk/HKIakhqV9T4+EUSPV3tjh0QMnDNBO04ajngGmDgHoVdvIjI9P0UwtrHcTYNHW/LWlqhUcVIgjbDxeC/c2skWUjvQdizrTPENa41bVDbWzk0OQIbP6/AtaIshWm1ipFTjicQku7RXt3tTmTIBqb2lwX91f//KTx9iNjW8SdGsfJN3zAtOeU5ObCPRTav8V4ZNOrzENTjIQABDg952HiqRMAsLvSKnfqe2KWU4H/5lndNRM4nyPxR7dufxC5piXu/D+WoMJhgrLqul/MOYVH+99EcNs/mmXx9xCGae3NHVrulbPbpkPXRiXiap+kg8Lqvx29AJ0BrFxXWE5uDmEY3y2dDcnFi0TS1G7rhnsazgb3b2LZbXT7HTyWFLd5l6+N2t0MHdWSLGJowt00EhFVuCMdD1QATPvgjf5QVhL+2+46YqcZXtFsxCbtkXIxXzpJO4jIzqzA14wYZjTO/uK04JitJHFGqkJKUSnrH4WkYRKiZNLhBHoJy0CZQDI3peMR23zkC/26DKOC42KAeD1BhOBGlWBdJ0iUUc+KjE7cQKbkZNOEjyGrEUItQUu9L9b4249OrGQckLOSqHQMdPjrsze0sM+xx1qhIV6pqSlPNQBPoUgPNQmc4SeSg1OTFmhemlE5FlMcjr/NsOUF4cxbopqSID1xAj7fIKF+Inx1bxwlMIReVNQ9xb1nyhPIfYmdo6+MbPn8MejB9oD+H1h2CBlXToEAKseYjtbz+D7GZederrdqhjJE2R5tFZAmQ+da9sy+/hG3ifUPIts+JfoSh5EryhgNAwAYbKc7jw1ywayynl2uQphpRD63t7JF1apBdWu9NonUbOVdL8SP0yFRJoczrKHqXjDSR7yQWPVR06tF+6bblB/bBsMgv+ReVYDxjgA+n8+ydSdPuCAe//jiD05oukMPLjwok4zfHwGmy6oFj12LjF/9aDVQD+/Xd4ORxe5+4jkcTYy5GhvgGva4bXVyJPXIt4arohg/yBA0//9InvNESfbDVPJaj4AfrcoTJO8DQHqhIRHqKrImZaQn4F7BVw3wgio/3ykpYhU6moc2eeiZ0xRg1iMB1k665GnkG69ElWfNMS23Wb1+ufFMg7gerk53HB/fJ7k4qB9dpk0BWlVGZtSxvhNZu1elNnvFjhtLKqS7Jhh7eDIu8uQ2kvMfQcCNv0e5evbqx6rm+8ocwofpsVElCWo4KpXxD607gamR8xN0OlLOOHnLi8JlvcAccQke91zJI45kvv7BnM8Q2nQ3WO9t1Vil7pZ8BAWSTFo8751ZIka44xuOCjEQGaKkM/VWa47XCWdc1k/b6jAUPuiKdPB9iBhQmlcMG16TauTTZ+jXhnTsQzM2ebe7QK9Jfs/A3IWgnstsEBpfsVdXn/PatN9Dr5e392Cw1r2Aw2R09ohpe+WeUQ7MjTNNestaMqNa3pU00ttAcjRq4+bRuihe1zGw1Ribp2bMsm2UIXdUx0W2KnbpLfkcDJ4RtaTUqUPIGIX8JfYAeLN+mwIus/nt/AWmf+reGt/u5p+o9DHfcduSBotuvazRbPcStLSSzh4sR+s0eqV01dnVjRDgccI5PZLmw94nuY9oPfFm02RnGtBw/nZjRePHIU02XZBZL4WRQfG0PTQbG9jrsKx5uaTqsbz3YRG0pZg9Cqz6hQOgn6i7uIvpwdOTcmdW3uszXt/oDAqSWFor9ZO";
        // 取南1局
        int round = 9;

        // 宝牌
        IYama yama = createYama(seed, round);
        List<IHai> doraDisplays = yama.getDoraDisplay();
        assertEquals(1, doraDisplays.size());
        assertEquals(Encode34.SO_7, doraDisplays.get(0).getValue());
        // 里宝牌
        List<IHai> uraDoraDisplays = yama.getUraDoraDisplay();
        assertEquals(1, uraDoraDisplays.size());
        assertEquals(Encode34.MAN_5, uraDoraDisplays.get(0).getValue());

        IHai nextDoraDisplay = yama.nextDoraDisplay();
        assertEquals(Encode34.SO_5, nextDoraDisplay.getValue());
        uraDoraDisplays = yama.getUraDoraDisplay();
        assertEquals(Encode34.MAN_8, uraDoraDisplays.get(1).getValue());
        
        nextDoraDisplay = yama.nextDoraDisplay();
        assertEquals(Encode34.SO_8, nextDoraDisplay.getValue());
        uraDoraDisplays = yama.getUraDoraDisplay();
        assertEquals(Encode34.PIN_8, uraDoraDisplays.get(2).getValue());

        nextDoraDisplay = yama.nextDoraDisplay();
        assertEquals(Encode34.MAN_1, nextDoraDisplay.getValue());
        uraDoraDisplays = yama.getUraDoraDisplay();
        assertEquals(Encode34.SO_5, uraDoraDisplays.get(3).getValue());

        nextDoraDisplay = yama.nextDoraDisplay();
        assertEquals(Encode34.PIN_8, nextDoraDisplay.getValue());
        uraDoraDisplays = yama.getUraDoraDisplay();
        assertEquals(Encode34.MAN_3, uraDoraDisplays.get(4).getValue());

        nextDoraDisplay = yama.nextDoraDisplay();
        assertNull(nextDoraDisplay);
    }

    @Test
    public void testGetTrumpHai() {
        // 2022042310gm-00a9-0000-5e2eefdb&tw=1
        String seed =
                "mt19937ar-sha512-n288-base64,Z9wyfRPuBlwECJk4igSEQOtYauqZllP8V4iX5/CNgCKvxEkrFKHlFkLpOwmB/4dR+W77Xq0SW7CebFSsKnr+0nY6W5ct+iER8tXHdcUhJCrUWIVaJHO549Z9lyOL91CcWuwhV+8AQqFBmHMtmYbQfhys/P4xCcxE8ZmwuLS4QMbrJ04LXOvFtAJs8rIkkEngWB1HKD64ngB47kV1vTTh1iYF196jgS3UL0i8ex/yN1LLpUghF1gRHzOJUmc7AkXxq0GuskCvYpM9o+oWW3sO0HwJ2jzmHlOzb/b+c2PFipvTQ0vhlyIrkf2Bnj++D732Rpd6TMX5l77auSggE1B05mOsUnJn6d3MiVZjyaH05I8uAVfQU+EF4t5AAr5QEloIDeGWyG1dp1tfIOGCV0JLktysG3HSnHwNDM/VUMleKbO7RAhjXfqbE3Cx/kKg8vJRjnZvEucJMqVwGRYV4H7hUaBaneL+LMMhZ4k/m5oQ7FeeTWnSfRJekbouy7XOlKclKFilVvU6ZHsLKytMIcVNDVIdBDpNW1rAP049HBrbTlvarSPmr7il8RSRujGcaD/QYHc74q0LOwZp+LRFVAH0ZW9zCWYxLg5JynFWHrneTXq99MtopcXJTt+jmCmXOiV/KwvvKVJw1ZiZqq1dP7y58zovOXOV8YN5t5U2h+qfzHdOtzuQjR/Eoqe1o4QDuWQcYgjoBCbuXXauNuvSDnoeEy+m+3GoKWiIu5MNmmHTxa/ksNYZtQ3ZyKRvLZw53zi+LDWdozQQd4kQNEXkAwIe/NzBL3JJJcK6PaZVqQkD8vLBT6iJEjSmtJme63Ajgz/rELO3E/d4dTKSRFH+9ynX0fN9XOWdwuBnfdD3gJ3QU9Fw++UybwpbxlJNqY53IM542S7FbVHYld8SXBqpb8C+MWspeB3W/+5KiLYVEjB1RpLm80kK56Z+EYzwg1ygeO8KEa1dXcfm93Vuk4Qcue+oJFX17sfIwQ91OvmUtiT0ipI6zLpgw8Lg6d0HNV3emWEDzksI/xi0BK0VIGVAoSqDGYfGUSLaRlsYK/Vrzrovlpp9gLUfZI5oYwU5ruT3GvZqeFsPHCQ3H4jnN+kvVkM9OMwKx0L/TFAzgGWxNv2tQpjIv2GCT3mXT8ZW3ySoLBwpIkG+0SzGuwjhGeLyrJJ5lLMguuP0jaboP/k1U8XS0tw8SuMSNsCIBWI4BKxpWuwDKKJk8t4mdcTuJTJbYfbPMEJmsQJ55R82kQ3gBlsYkajUNzfGJij6qnBV0pDql/mx0zFFxKm/evhpBtqRNlZ+3IqFdxMOSQK9A7sI4M5xsV86Kp3JTqnsyOrI0kJte/5yDrhAoH7TzwGBxQzMrkR/ePxQ0Lv6yPlQvWfvjN3eHt8kCsHVxnEyVAUnhQwiycCYLlHI3fV+3FRkaiuaQAmejCs6er2HJEdEQje4kzvwy3I3S6rbalJDZVLIGw5Uu0oi8ErM2SFyXukNJRKnJoq6AOjZcEQib2V2Sc5pmHWTS/L6+lFXGWOkp2nQ2B4VXbTFzeTg7cqWzhniwP5JQhf2iT/M/2BxVWVU+rdbNDjWXu8nb9SecdCVD+tCEO58eaEX11pnI36wyOWaw4HyGdCjxDvBFk/HKIakhqV9T4+EUSPV3tjh0QMnDNBO04ajngGmDgHoVdvIjI9P0UwtrHcTYNHW/LWlqhUcVIgjbDxeC/c2skWUjvQdizrTPENa41bVDbWzk0OQIbP6/AtaIshWm1ipFTjicQku7RXt3tTmTIBqb2lwX91f//KTx9iNjW8SdGsfJN3zAtOeU5ObCPRTav8V4ZNOrzENTjIQABDg952HiqRMAsLvSKnfqe2KWU4H/5lndNRM4nyPxR7dufxC5piXu/D+WoMJhgrLqul/MOYVH+99EcNs/mmXx9xCGae3NHVrulbPbpkPXRiXiap+kg8Lqvx29AJ0BrFxXWE5uDmEY3y2dDcnFi0TS1G7rhnsazgb3b2LZbXT7HTyWFLd5l6+N2t0MHdWSLGJowt00EhFVuCMdD1QATPvgjf5QVhL+2+46YqcZXtFsxCbtkXIxXzpJO4jIzqzA14wYZjTO/uK04JitJHFGqkJKUSnrH4WkYRKiZNLhBHoJy0CZQDI3peMR23zkC/26DKOC42KAeD1BhOBGlWBdJ0iUUc+KjE7cQKbkZNOEjyGrEUItQUu9L9b4249OrGQckLOSqHQMdPjrsze0sM+xx1qhIV6pqSlPNQBPoUgPNQmc4SeSg1OTFmhemlE5FlMcjr/NsOUF4cxbopqSID1xAj7fIKF+Inx1bxwlMIReVNQ9xb1nyhPIfYmdo6+MbPn8MejB9oD+H1h2CBlXToEAKseYjtbz+D7GZederrdqhjJE2R5tFZAmQ+da9sy+/hG3ifUPIts+JfoSh5EryhgNAwAYbKc7jw1ywayynl2uQphpRD63t7JF1apBdWu9NonUbOVdL8SP0yFRJoczrKHqXjDSR7yQWPVR06tF+6bblB/bBsMgv+ReVYDxjgA+n8+ydSdPuCAe//jiD05oukMPLjwok4zfHwGmy6oFj12LjF/9aDVQD+/Xd4ORxe5+4jkcTYy5GhvgGva4bXVyJPXIt4arohg/yBA0//9InvNESfbDVPJaj4AfrcoTJO8DQHqhIRHqKrImZaQn4F7BVw3wgio/3ykpYhU6moc2eeiZ0xRg1iMB1k665GnkG69ElWfNMS23Wb1+ufFMg7gerk53HB/fJ7k4qB9dpk0BWlVGZtSxvhNZu1elNnvFjhtLKqS7Jhh7eDIu8uQ2kvMfQcCNv0e5evbqx6rm+8ocwofpsVElCWo4KpXxD607gamR8xN0OlLOOHnLi8JlvcAccQke91zJI45kvv7BnM8Q2nQ3WO9t1Vil7pZ8BAWSTFo8751ZIka44xuOCjEQGaKkM/VWa47XCWdc1k/b6jAUPuiKdPB9iBhQmlcMG16TauTTZ+jXhnTsQzM2ebe7QK9Jfs/A3IWgnstsEBpfsVdXn/PatN9Dr5e392Cw1r2Aw2R09ohpe+WeUQ7MjTNNestaMqNa3pU00ttAcjRq4+bRuihe1zGw1Ribp2bMsm2UIXdUx0W2KnbpLfkcDJ4RtaTUqUPIGIX8JfYAeLN+mwIus/nt/AWmf+reGt/u5p+o9DHfcduSBotuvazRbPcStLSSzh4sR+s0eqV01dnVjRDgccI5PZLmw94nuY9oPfFm02RnGtBw/nZjRePHIU02XZBZL4WRQfG0PTQbG9jrsKx5uaTqsbz3YRG0pZg9Cqz6hQOgn6i7uIvpwdOTcmdW3uszXt/oDAqSWFor9ZO";
        // 取南1局
        int round = 9;

        // 宝牌
        IYama yama = createYama(seed, round);
        IHai hai = yama.nextTrumpHai();
        assertEquals(Encode34.PIN_7, hai.getValue());

        hai = yama.nextTrumpHai();
        assertEquals(Encode34.DONG, hai.getValue());

        hai = yama.nextTrumpHai();
        assertEquals(Encode34.PIN_7, hai.getValue());

        hai = yama.nextTrumpHai();
        assertEquals(Encode34.SO_1, hai.getValue());

        hai = yama.nextTrumpHai();
        assertNull(hai);
    }

    @Test
    public void testDeal2s() {
        String seed =
                "mt19937ar-sha512-n288-base64,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";
        int round = 0;
        // 东1局
        IYama yama = createYama(seed, round);
        List<ITehai> tehais = yama.deals(0);
        assertEquals("678m11357p225s36z", EncodeMark.encode(tehais.get(0)));
        assertEquals("6m12367889p08s45z", EncodeMark.encode(tehais.get(1)));
        assertEquals("9m24p13456777s24z", EncodeMark.encode(tehais.get(2)));
        assertEquals("156899m99p1889s7z", EncodeMark.encode(tehais.get(3)));

        // 东2局
        round = 1;
        yama = createYama(seed, round);
        tehais = yama.deals(1);
        assertEquals("24m2089p18s24457z", EncodeMark.encode(tehais.get(0)));
        assertEquals("33667m466p48s557z", EncodeMark.encode(tehais.get(1)));
        assertEquals("1379m267p6778s36z", EncodeMark.encode(tehais.get(2)));
        assertEquals("128m1368p45689s1z", EncodeMark.encode(tehais.get(3)));
    }

    @Test
    public void testDeal3() {
        String seed =
                "mt19937ar-sha512-n288-base64,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";
        IYama yama = createYama(seed, 3);
        List<ITehai> tehais = yama.deals(2);
        assertEquals("4079m23458p248s6z", EncodeMark.encode(tehais.get(0)));
        assertEquals("12m11389p347s557z", EncodeMark.encode(tehais.get(1)));
        assertEquals("99m13p1344667s12z", EncodeMark.encode(tehais.get(2)));
        assertEquals("357m2249p238s446z", EncodeMark.encode(tehais.get(3)));
    }

    /**
     * 根据种子构建牌山
     * 
     * @param seed 种子
     * @param round 场次
     */
    private IYama createYama(String seed, int round) {
        IYamaWorker parser = new TenhouYamaWorker(seed);
        int[] yama = parser.getNextYama(round + 1);
        return new Yama(yama, true, 4);
    }
}
