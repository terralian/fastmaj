package com.github.terralian.fastmaj.test.paifu.tenhou;

import com.github.terralian.fastmaj.paifu.tenhou.TenhouPaifuHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link TenhouPaifuHelper}测试
 *
 * @author terra.tian
 */
public class TenhouPaifuHelperTest {

    @Test
    public void testGetRawId() {
        String res = TenhouPaifuHelper.getRawId("2011012516gm-00a9-0000-6e60e3de&tw=2.mjlog");
        Assert.assertEquals("2011012516gm-00a9-0000-6e60e3de", res);

        res = TenhouPaifuHelper.getRawId("2011012516gm-00a9-0000-6e60e3de&tw=2");
        Assert.assertEquals("2011012516gm-00a9-0000-6e60e3de", res);

        res = TenhouPaifuHelper.getRawId("2011012516gm-00a9-0000-6e60e3de");
        Assert.assertEquals("2011012516gm-00a9-0000-6e60e3de", res);

        res = TenhouPaifuHelper.getRawId("2011012516gm-00a9-0000");
        Assert.assertNull(res);
    }
}
