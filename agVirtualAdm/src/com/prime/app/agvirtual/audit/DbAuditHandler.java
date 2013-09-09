package com.prime.app.agvirtual.audit;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import com.prime.infra.audit.AuditHandler;
import com.prime.infra.audit.AuditInfo;
import com.prime.infra.dao.jdbc.GenericJdbcDao;

public class DbAuditHandler extends GenericJdbcDao implements AuditHandler {

    public void logEvent(AuditInfo auditInfo) {
    	
    	DateTime dataHora = auditInfo.getDataHora();
        Map<String, Object> parameters = new HashMap<String, Object>(6);
        parameters.put("DATA_HORA", dataHora != null ? dataHora.toDate() : null);
        parameters.put("SERVICO", auditInfo.getServico());
        parameters.put("METODO", auditInfo.getMetodo());
        parameters.put("CANAL", auditInfo.getCanal());
        parameters.put("TIPO_TERMINAL", auditInfo.getTipoTerminal());
        parameters.put("NUMERO_TERMINAL", auditInfo.getNumeroTerminal());
        parameters.put("DESCR", auditInfo.get("desc"));

        getSimpleJdbcTemplate().update(
            "insert into"
                + " tb_log_audi (CD_LOG, DH_LOG, TX_SERV, TX_METO, TX_CNAL, TX_TIPO_TERN, TX_NUME_TERN, TX_DSCR)"
                + " values (seq_id.nextval, :DATA_HORA, :SERVICO, :METODO, "
                + ":CANAL, :TIPO_TERMINAL, :NUMERO_TERMINAL, :DESCR)", parameters);
    }

}
