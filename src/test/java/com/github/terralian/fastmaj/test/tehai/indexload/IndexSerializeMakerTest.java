package com.github.terralian.fastmaj.test.tehai.indexload;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.github.terralian.fastmaj.tehai.FastSyantenCalculator;
import com.github.terralian.fastmaj.util.StopWatch;
import org.junit.Test;

/**
 * 向听序列化制作
 *
 * @author terra.lian
 * @since 2023-03-03
 */
public class IndexSerializeMakerTest {

    @Test
    public void serialize() throws IOException, ClassNotFoundException {
        String fileName = FastSyantenCalculator.class.getClassLoader().getResource("syanten.zip").getPath();
        File file = new File(fileName);
        int[][] mp1;
        int[][] mp2;
        try (ZipFile zipFile = new ZipFile(file)) {
            Enumeration<? extends ZipEntry> enums = zipFile.entries();
            Map<String, ZipEntry> map = new HashMap<>();
            while (enums.hasMoreElements()) {
                ZipEntry entry = enums.nextElement();
                map.put(entry.getName(), entry);
            }
            mp1 = parseIndexZipEntry(zipFile.getInputStream(map.get("index_s.csv")));
            mp2 = parseIndexZipEntry(zipFile.getInputStream(map.get("index_h.csv")));
        }

        IndexSerializable indexSerializable = new IndexSerializable();
        indexSerializable.setMp1(mp1);
        indexSerializable.setMp2(mp2);

        String projectPath = System.getProperty("user.dir").replaceAll("\\\\", "/");
        String resourcePath = projectPath + "/src/test/resources/index";
        String fullName = resourcePath + "/" + "index.data";
        file = new File(fullName);
        // 制作序列化文件
        try (FileOutputStream fo = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fo)) {
            out.writeObject(indexSerializable);
            out.flush();
        }
        System.out.println("序列化对象成功");

        try (FileInputStream fi = new FileInputStream(file); ObjectInputStream oi = new ObjectInputStream(fi)) {
            IndexSerializable o = (IndexSerializable) oi.readObject();
            System.out.println(Arrays.toString(o.getMp1()[0]));
            System.out.println(Arrays.toString(o.getMp2()[0]));
        }
    }

    @Test
    public void deserialized() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String projectPath = System.getProperty("user.dir").replaceAll("\\\\", "/");
        String resourcePath = projectPath + "/src/test/resources/index";
        String fullName = resourcePath + "/" + "index.data";

        File file = new File(fullName);
        try (FileInputStream fi = new FileInputStream(file); BufferedInputStream ba = new BufferedInputStream(fi);
             ObjectInputStream oi = new ObjectInputStream(ba)) {
            IndexSerializable o = (IndexSerializable) oi.readObject();
            System.out.println(Arrays.toString(o.getMp1()[0]));
            System.out.println(Arrays.toString(o.getMp2()[0]));
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    public void deserializedZip() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String projectPath = System.getProperty("user.dir").replaceAll("\\\\", "/");
        String resourcePath = projectPath + "/src/test/resources/index";
        String fullName = resourcePath + "/" + "index.zip";
        System.out.println("读取文件：" + fullName);

        File file = new File(fullName);
        try (ZipFile zipFile = new ZipFile(file)) {
            Enumeration<? extends ZipEntry> enums = zipFile.entries();
            if (!enums.hasMoreElements()) {
                throw new IllegalArgumentException("ZIP中不含文件");
            }
            ZipEntry entry = enums.nextElement();
            InputStream is = zipFile.getInputStream(entry);
            try (BufferedInputStream br = new BufferedInputStream(is); ObjectInputStream oi = new ObjectInputStream(br)) {
                IndexSerializable o = (IndexSerializable) oi.readObject();
                System.out.println(Arrays.toString(o.getMp1()[0]));
                System.out.println(Arrays.toString(o.getMp2()[0]));
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private static int[][] parseIndexZipEntry(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("GBK")));
        List<String> lines = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }

        int[][] mp = new int[lines.size()][10];
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);
            String[] array = line.split(",");
            for (int j = 0; j < array.length; j++) {
                mp[i][j] = Integer.parseInt(array[j]);
            }
        }
        return mp;
    }
}
