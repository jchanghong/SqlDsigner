/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import mysqls.graph.ClassNode;
import mysqls.graph.Graph;

/**
 * @author 长宏
 *
 */
public final class MyIOutil {

	/**
	 *
	 */
	private MyIOutil() {
		// TODO Auto-generated constructor stub
	}

	public static void savefile(Graph graph, File file) {
		List<ClassNode> list = graph.getClassNOdes();
		StringBuilder builder = new StringBuilder();
		for (ClassNode classNode : list) {
			builder.append(SQLCreator.create(classNode.mTable));
		}
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

	public static void copy(String sql, OutputStream out) {
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
