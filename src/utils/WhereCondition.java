package utils;

public class WhereCondition extends Trio<String, String, String, Object>{

	public WhereCondition(String tableName, String colonneName, String operand, Object value) {
		super(tableName, colonneName, operand, value);
	}

}
