package utils;

/**
 * 
 * @author polob
 *
 * @param <T> Nom de la table
 * @param <U> Nom de la colonne
 * @param <V> Valeur de l'opérand, défini dans l'interface BaseDonnee
 * @param <W> Valeur
 */
public class Trio<T, U, V, W> {
	
	private T tableName;
	private U colonneName;
	private V operand; 
	private W value;
	
	public Trio(T tableName, U colonneName, V operand, W value) {
		this.tableName = tableName;
		this.colonneName = colonneName;
		this.operand = operand;
		this.value = value;
	}

	public T getTableName() {
		return tableName;
	}

	public void setTableName(T tableName) {
		this.tableName = tableName;
	}

	public U getColonneName() {
		return colonneName;
	}

	public void setColonneName(U colonneName) {
		this.colonneName = colonneName;
	}

	public V getOperand() {
		return operand;
	}

	public void setOperand(V operand) {
		this.operand = operand;
	}

	public W getValue() {
		return value;
	}

	public void setValue(W value) {
		this.value = value;
	}

}
