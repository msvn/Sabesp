package com.prime.app.agvirtual.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "AutoAtendimentoAcao.findAll", query = "select o from AutoAtendimentoAcao o") })
@Table(name = "AGV_TAB_AUTOAT_ACAO", schema = Schema.DB_OWNER)
@PrimaryKeyJoinColumn(name = "CD_AUTOATENDIMENTO")
public class AutoAtendimentoAcao extends AgvTabAutoatendimento {

}
