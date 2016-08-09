/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

/**
 * @author 长宏 边的数据，4个，前后的表和列
 *
 */
public class EdgeData {
	/**
	 * 没有箭头的一边，有外键的一边
	 */
	public Table sTable;
	/**
	 * 被参考的一边
	 *
	 */
	public Table eTable;
	public TableColumn sColumn;
	public TableColumn eColumn;

}
