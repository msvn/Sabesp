/*
 * Created on Jan 8, 2005
 * This is the Hibernate dialect for the Unisys 2200 Relational Database (RDMS).
 * This dialect was developed for use with Hibernate 2.1.6. Other versions may
 * require modifications to the dialect.
 *
 * Version History:
 * Also change the version displayed below in the constructor
 * 1.3
 * 1.2  2005-10-24  CDH - Cleanup
 * 1.1  2005-08-03  CDH - Update the getSequenceNextValString() method
 * 1.0  2005-03-11  CDH - First dated version for use with RTC PoC v4.2
 */
package net.sf.hibernate.dialect;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.sql.CaseFragment;
import org.hibernate.sql.DecodeCaseFragment;
import org.hibernate.sql.JoinFragment;
import org.hibernate.sql.OracleJoinFragment;


/**
 * @author Ploski and Hanson
 *
 */
public class RDMSOS2200Dialect extends Dialect {

    private static Log log = LogFactory.getLog(RDMSOS2200Dialect.class); 

    public RDMSOS2200Dialect() {
        super();
        // Display the dialect version if logging is enabled.
        // Display the dialect version.
        log.info("RDMSOS2200Dialect version: 1.2");

        registerFunction("abs", new StandardSQLFunction("abs"));
        registerFunction("sign", new StandardSQLFunction("sign",Hibernate.INTEGER) );

        registerFunction("ascii", new StandardSQLFunction("ascii",Hibernate.INTEGER) );
        registerFunction("char_length", new StandardSQLFunction("char_length",Hibernate.INTEGER) );
        registerFunction("character_length", new StandardSQLFunction("character_length",Hibernate.INTEGER) );
        registerFunction("length",  new StandardSQLFunction("length", Hibernate.INTEGER) );
        registerFunction("concat",  new StandardSQLFunction("concat", Hibernate.STRING) );
        registerFunction("instr",   new StandardSQLFunction("instr",  Hibernate.STRING) );
        registerFunction("lpad",    new StandardSQLFunction("lpad",   Hibernate.STRING) );
        registerFunction("replace", new StandardSQLFunction("replace",Hibernate.STRING) );
        registerFunction("rpad",    new StandardSQLFunction("rpad",   Hibernate.STRING) );
        registerFunction("substr",  new StandardSQLFunction("substr", Hibernate.STRING) );
                                                            

        registerFunction("lcase", new StandardSQLFunction("lcase") );
        registerFunction("lower", new StandardSQLFunction("lower") );
        registerFunction("ltrim", new StandardSQLFunction("ltrim") );
        registerFunction("reverse", new StandardSQLFunction("reverse") );
        registerFunction("rtrim",   new StandardSQLFunction("rtrim" ) );
        registerFunction("soundex", new StandardSQLFunction("soundex") );
        registerFunction("space",   new StandardSQLFunction("space",Hibernate.STRING) );
        registerFunction("ucase",   new StandardSQLFunction("ucase") );
        registerFunction("upper",   new StandardSQLFunction("upper") );
                                                      
        registerFunction("acos", new StandardSQLFunction("acos", Hibernate.DOUBLE) );
        registerFunction("asin", new StandardSQLFunction("asin", Hibernate.DOUBLE) );
        registerFunction("atan", new StandardSQLFunction("atan", Hibernate.DOUBLE) );
        registerFunction("cos",  new StandardSQLFunction("cos",  Hibernate.DOUBLE) );
        registerFunction("cosh", new StandardSQLFunction("cosh", Hibernate.DOUBLE) );
        registerFunction("cot",  new StandardSQLFunction("cot",  Hibernate.DOUBLE) );
        registerFunction("exp",  new StandardSQLFunction("exp",  Hibernate.DOUBLE) );
        registerFunction("ln",   new StandardSQLFunction("ln",   Hibernate.DOUBLE) );
        registerFunction("log",  new StandardSQLFunction("log",  Hibernate.DOUBLE) );
        registerFunction("log10",new StandardSQLFunction("log10",Hibernate.DOUBLE) );
        registerFunction("pi",   new NoArgSQLFunction   ("pi",   Hibernate.DOUBLE) );
        registerFunction("rand", new NoArgSQLFunction   ("rand", Hibernate.DOUBLE) );
        registerFunction("sin",  new StandardSQLFunction("sin",  Hibernate.DOUBLE) );
        registerFunction("sinh", new StandardSQLFunction("sinh", Hibernate.DOUBLE) );
        registerFunction("sqrt", new StandardSQLFunction("sqrt", Hibernate.DOUBLE) );
        registerFunction("tan",  new StandardSQLFunction("tan",  Hibernate.DOUBLE) );
        registerFunction("tanh", new StandardSQLFunction("tanh", Hibernate.DOUBLE) );
                                                   
        registerFunction("round", new StandardSQLFunction("round") );
        registerFunction("trunc", new StandardSQLFunction("trunc") );
        registerFunction("ceil" , new StandardSQLFunction("ceil" ) );
        registerFunction("floor", new StandardSQLFunction("floor") );
                                                    
        registerFunction("chr", new StandardSQLFunction("chr",Hibernate.CHARACTER) );
        registerFunction("initcap", new StandardSQLFunction("initcap") );

        registerFunction( "user", new NoArgSQLFunction("user",Hibernate.STRING, false) );

        registerFunction("curdate", new NoArgSQLFunction("curdate",Hibernate.DATE) );
        registerFunction("curtime", new NoArgSQLFunction("curtime",Hibernate.TIME) );
        registerFunction("days",  new StandardSQLFunction("days",Hibernate.INTEGER) );
        registerFunction("dayofmonth", new StandardSQLFunction("dayofmonth",Hibernate.INTEGER) );
        registerFunction("dayname",    new StandardSQLFunction("dayname",   Hibernate.STRING) );
        registerFunction("dayofweek",  new StandardSQLFunction("dayofweek", Hibernate.INTEGER) );
        registerFunction("dayofyear",  new StandardSQLFunction("dayofyear", Hibernate.INTEGER) );
        registerFunction("hour",       new StandardSQLFunction("hour",      Hibernate.INTEGER) );
                                                        
        // In RDMS, "last_day" really does include an underscore character in its name.
        registerFunction("last_day",    new StandardSQLFunction("last_day",   Hibernate.DATE) );
        registerFunction("microsecond", new StandardSQLFunction("microsecond",Hibernate.INTEGER) );
        registerFunction("minute",      new StandardSQLFunction("minute",     Hibernate.INTEGER) );
        registerFunction("month",       new StandardSQLFunction("month",      Hibernate.INTEGER) );
        registerFunction("monthname",   new StandardSQLFunction("monthname",  Hibernate.STRING) );
        registerFunction("now", 	new NoArgSQLFunction("now",Hibernate.TIMESTAMP) );
        registerFunction("quarter", new StandardSQLFunction("quarter",Hibernate.INTEGER) );
        registerFunction("second",  new StandardSQLFunction("second", Hibernate.INTEGER) );
        registerFunction("time",    new StandardSQLFunction("time",   Hibernate.TIME) );
        registerFunction("timestamp", new StandardSQLFunction("timestamp",Hibernate.TIMESTAMP) );
        registerFunction("week",  new StandardSQLFunction("week",  Hibernate.INTEGER) );
        registerFunction("year",  new StandardSQLFunction("year",  Hibernate.INTEGER) );
        registerFunction("atan2", new StandardSQLFunction("atan2", Hibernate.DOUBLE) );
        registerFunction( "mod",  new StandardSQLFunction( "mod",  Hibernate.INTEGER) );
        registerFunction( "nvl",  new StandardSQLFunction( "nvl"  ) );
        registerFunction( "power",new StandardSQLFunction( "power",Hibernate.DOUBLE) );


        /**
         * For a list of column types to register, see section A-1
         * in 7862 7395, the Unisys JDBC manual.
         *
         * Here are column sizes as documented in Table A-1 of
         * 7831 0760, "Enterprise Relational Database Server
         * for ClearPath OS2200 Administration Guide"
         * Numeric - 21
         * Decimal - 22 (21 digits plus one for sign)
         * Float   - 60 bits
         * Char    - 28000
         * NChar   - 14000
         * BLOB+   - 4294967296 (4 Gb)
         * + RDMS JDBC driver does not support BLOBs
         *
         * DATE, TIME and TIMESTAMP literal formats are
         * are all described in section 2.3.4 DATE Literal Format
         * in 7830 8160.
         * The DATE literal format is: YYYY-MM-DD
         * The TIME literal format is: HH:MM:SS[.[FFFFFF]]
         * The TIMESTAMP literal format is: YYYY-MM-DD HH:MM:SS[.[FFFFFF]]
         */
        // Note that $l (dollar-L) will use the length value if provided.
        registerColumnType(Types.BIT, "SMALLINT");
        registerColumnType(Types.TINYINT, "SMALLINT");
        registerColumnType(Types.BIGINT, "NUMERIC(21,0)");
        registerColumnType(Types.SMALLINT, "SMALLINT");
        registerColumnType(Types.CHAR, "CHARACTER(1)");
        registerColumnType(Types.DOUBLE, "DOUBLE PRECISION");
        registerColumnType(Types.FLOAT, "FLOAT");
        registerColumnType(Types.REAL, "REAL");
        registerColumnType(Types.INTEGER, "INTEGER");
        registerColumnType(Types.NUMERIC, "NUMERIC(21,$l)");
        registerColumnType(Types.DECIMAL, "NUMERIC(21,$l)");
        registerColumnType(Types.DATE, "DATE");
        registerColumnType(Types.TIME, "TIME");
        registerColumnType(Types.TIMESTAMP, "TIMESTAMP");
        registerColumnType(Types.VARCHAR, "CHARACTER($l)");
    }

    // The following methods over-ride the default behaviour in the Dialect object.

    public boolean qualifyIndexName() {
        // Not supported by RDMS
        return false;
    }

    public boolean supportsForUpdate() {
        // RDMS does not support this function.
        // We can modify this function to return TRUE after a
        // change is made to RDMS.
        return false;
    }

//    public boolean supportsForUpdateOf() {
//        // While RDMS supports this, The JDBC driver does not.
//        return false;
//    }

    public String getAddColumnString() {
        return "add";
    }

    public String getNullColumnString() {
        // The keyword used to specify a nullable column.
        return " null";
    }

    public boolean supportsSequences() {
        return true;
    }

    // *** Sequence methods - start. The RDMS dialect needs these
    // methods to make it possible to use the Native Id generator
    public String getSequenceNextValString(String sequenceName) {
        return  "select permuted_id('NEXT',31) from rdms.rdms_dummy where key_col = 1 ";
    }

    public String getCreateSequenceString(String sequenceName) {
        // We must return a valid RDMS/RSA command from this method to
        // prevent RDMS/RSA from issuing *ERROR 400
        return "";
    }

    public String getDropSequenceString(String sequenceName) {
        // We must return a valid RDMS/RSA command from this method to
        // prevent RDMS/RSA from issuing *ERROR 400
        return "";
    }

    public String getCascadeConstraintsString() {
        // Used with DROP TABLE to delete all records in the table.
        return " including contents";
    }

    public CaseFragment createCaseFragment() {
        return new DecodeCaseFragment();
    }

    public boolean supportsLimit() {
        return true;
    }

    public boolean supportsLimitOffset() {
        return false;
    }

    public String getLimitString(String sql, boolean hasOffset, int limit) {
        return new StringBuffer(sql.length() + 40)
            .append(sql)
            .append(" fetch first ")
            .append(limit)
            .append(" rows only ")
            .toString();
    }

    public boolean supportsVariableLimit() {
        return false;
    }

    public boolean useMaxForLimit() {
        return true;
    }
    
    public JoinFragment createOuterJoinFragment()
    {
      return new RDMSJoinFragment();
    }

}   // End of class RDMSOS2200Dialect
