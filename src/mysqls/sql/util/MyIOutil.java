/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.util;

import mysqls.graph.Graph;

import java.io.*;

/**
 * @author 长宏 文件输入输出
 *
 */
public final class MyIOutil {

    /**
     * 不能new对象
     */
    private MyIOutil() {
        // TODO Auto-generated constructor stub
    }

    public static void savefile(Graph graph, File file) {
        StringBuilder builder = new StringBuilder();
        graph.getClassNOdes().stream().forEach(a -> builder.append(SQLCreator.create(a.mTable)));
        MyIOutil.copy(builder.toString(), file);
    }

    public static void copy(String sql, File file) {
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            MyIOutil.copy(sql, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void copy(String sql, OutputStream out) {
        Writer writer = new OutputStreamWriter(new BufferedOutputStream(out));
        try {

            try {
                writer.write(sql);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            // TODO: handle finally clause
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public static String read(File file) {
        StringBuilder builder = new StringBuilder();
        Reader reader = null;
        try {
            reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int i = -1;
        try {
            char[] buff = new char[1024];
            try {

                i = reader.read(buff);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            while (i != -1) {
                builder.append(buff, 0, i);
                try {
                    i = reader.read(buff);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        } finally {
            // TODO: handle finally clause
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();

    }

}
