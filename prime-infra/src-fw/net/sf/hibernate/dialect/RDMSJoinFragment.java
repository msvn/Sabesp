package net.sf.hibernate.dialect;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.sql.JoinFragment;

public class RDMSJoinFragment extends JoinFragment{	
		  private StringBuffer afterFrom;
		  private StringBuffer afterWhere;
		  private static final Set OPERATORS = new HashSet();

		  public RDMSJoinFragment()
		  {
		    this.afterFrom = new StringBuffer();
		    this.afterWhere = new StringBuffer();
		  }

		  public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, int joinType) {
		    addCrossJoin(tableName, alias);

		    for (int j = 0; j < fkColumns.length; ++j) {
		      setHasThetaJoins(true);
		      this.afterWhere.append(" and ").append(fkColumns[j]);

		      if ((joinType == 2) || (joinType == 4)) this.afterWhere.append("");
		      this.afterWhere.append('=').append(alias).append('.').append(pkColumns[j]);

		      if ((joinType != 1) && (joinType != 4)) continue; this.afterWhere.append("");
		    }
		  }

		  public String toFromFragmentString()
		  {
		    return this.afterFrom.toString();
		  }

		  public String toWhereFragmentString() {
		    return this.afterWhere.toString();
		  }

		  public void addJoins(String fromFragment, String whereFragment) {
		    this.afterFrom.append(fromFragment);
		    this.afterWhere.append(whereFragment);
		  }

		  public JoinFragment copy() {
			  RDMSJoinFragment copy = new RDMSJoinFragment();
		    copy.afterFrom = new StringBuffer(this.afterFrom.toString());
		    copy.afterWhere = new StringBuffer(this.afterWhere.toString());
		    return copy;
		  }

		  public void addCondition(String alias, String[] columns, String condition) {
		    for (int i = 0; i < columns.length; ++i)
		      this.afterWhere.append(" and ").append(alias).append('.').append(columns[i]).append(condition);
		  }

		  public void addCrossJoin(String tableName, String alias)
		  {
		    this.afterFrom.append(", ").append(tableName).append(' ').append(alias);
		  }

		  public void addCondition(String alias, String[] fkColumns, String[] pkColumns)
		  {
		    throw new UnsupportedOperationException();
		  }

		  public boolean addCondition(String condition) {
		    return addCondition(this.afterWhere, condition);
		  }

		  public void addFromFragmentString(String fromFragmentString) {
		    this.afterFrom.append(fromFragmentString);
		  }

		  public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, int joinType, String on)
		  {
		    addJoin(tableName, alias, fkColumns, pkColumns, joinType);
		    if (joinType == 0) {
		      addCondition(on);
		    }
		    else if (joinType == 1) {
		      addLeftOuterJoinCondition(on);
		    }
		    else
		      throw new UnsupportedOperationException("join type not supported by OracleJoinFragment (use Oracle9Dialect)");
		  }

		  private void addLeftOuterJoinCondition(String on)
		  {
		    StringBuffer buf = new StringBuffer(on);
		    for (int i = 0; i < buf.length(); ++i) {
		      char character = buf.charAt(i);
		      boolean isInsertPoint = (OPERATORS.contains(new Character(character))) || ((character == ' ') && (buf.length() > i + 3) && ("is ".equals(buf.substring(i + 1, i + 4))));

		      if (isInsertPoint) {
		        buf.insert(i, "");
		        i += 3;
		      }
		    }
		    addCondition(buf.toString());
		  }

		  static
		  {
		    OPERATORS.add(new Character('='));
		    OPERATORS.add(new Character('<'));
		    OPERATORS.add(new Character('>'));
		  }
}
