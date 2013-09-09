package com.prime.app.agvirtual.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AcatamentoContaCSIDao;
import com.prime.app.agvirtual.eae.EAEDaoSupport;
import com.prime.app.agvirtual.eae.EAEException;
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.to.Acatamento2ViaTO;
import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.IspecModelRef;
import com.unisys.jellybeans.LINCStatus;

@Repository
public class AcatamentoContaCSIDaoImpl extends EAEDaoSupport implements AcatamentoContaCSIDao {
	
    private static final Logger logger = LoggerFactory.getLogger(AcatamentoContaCSIDaoImpl.class);

	private static final String ACATA_ISPEC = "ACAT1";

	private static final String RGI_FIELD = "NRRGILIG";

	private static final String PERIODO_FIELD = "NRTELA3A";

	private static final String TIPO_CONTA_FIELD = "TPCONTA";

	private static final String SEQ_CTA2_FIELD = "NRSEQCTA2";

	private static final String SEQ_CTA_FIELD = "NRSEQCTA";

	private static final String TIPO_LOGRADOURO_FIELD = "DSTPLOGAB";

	private static final String TITULO_LOGRADOURO_FIELD = "SGTITLOG";

	private static final String PREPOSTO_LOGRADOURO_FIELD = "SGPREPLOG";

	private static final String LOGRADOURO_FIELD = "DSENDEREC";

	private static final String NUMERO_FIELD = "NRENDEREC";

	private static final String COMPLEMENTO_FIELD = "DSCOMPLEM";

	private static final String BAIRRO_FIELD = "NMBAIRRO";

	private static final String CEP_FIELD = "CDCEP";

	private static final String MUNICIPIO_FIELD = "NMMUNICIP";

	private static final String UF_FIELD = "SGESTADO";

	private static final String MENSAGEM_ERRO_FIELD = "DSTELA70";

	private static final String MENSAGEM_SUCESSO_FIELD = "DSTELA10";

	public Acatamento2ViaTO acata2Via(Acatamento2ViaTO acatamento2ViaTO) throws Exception {

		try {
			
			openConnection();
			
			loadIspec(ACATA_ISPEC);
			
			registra2Via(acatamento2ViaTO);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			closeConnection();
		}

		return acatamento2ViaTO;
	}

	private void registra2Via(Acatamento2ViaTO acatamento2ViaTO) throws EAEException {

		IspecModel currentIspec = objLINC.getCurrentIspec();

      	currentIspec.setFieldValueFromLong(RGI_FIELD, acatamento2ViaTO.getRgi());
      	
      	setContas(currentIspec, acatamento2ViaTO.getContas());

		if (acatamento2ViaTO.getEndereco().getDsTipoLogradouro() != null) {
	       	currentIspec.setFieldValue(TIPO_LOGRADOURO_FIELD, acatamento2ViaTO.getEndereco().getDsTipoLogradouro());
		}       	
		
		if (acatamento2ViaTO.getEndereco().getDsHonorifico() != null) {
			currentIspec.setFieldValue(TITULO_LOGRADOURO_FIELD, acatamento2ViaTO.getEndereco().getDsHonorifico());
		}
		
		if (acatamento2ViaTO.getEndereco().getDsPreposicao() != null) {
	       	currentIspec.setFieldValue(PREPOSTO_LOGRADOURO_FIELD, acatamento2ViaTO.getEndereco().getDsPreposicao());
		}		
		
       	currentIspec.setFieldValue(LOGRADOURO_FIELD, acatamento2ViaTO.getEndereco().getDsEndereco());

		if (acatamento2ViaTO.getEndereco().getNrEndereco() != null) {
			currentIspec.setFieldValueFromInt(NUMERO_FIELD, Integer.parseInt(acatamento2ViaTO.getEndereco().getNrEndereco()));
		}
		
		if (acatamento2ViaTO.getEndereco().getDsComplemento() != null) {
    		 currentIspec.setFieldValue(COMPLEMENTO_FIELD, acatamento2ViaTO.getEndereco().getDsComplemento());
		}
		
		if (acatamento2ViaTO.getEndereco().getNmBairro() != null) {
    		 currentIspec.setFieldValue(BAIRRO_FIELD, acatamento2ViaTO.getEndereco().getNmBairro());
		}
		
		if (acatamento2ViaTO.getEndereco().getCdCep() != null) {
           	currentIspec.setFieldValueFromInt(CEP_FIELD, Integer.parseInt(acatamento2ViaTO.getEndereco().getCdCep()));
		}
		
       	currentIspec.setFieldValue(MUNICIPIO_FIELD, acatamento2ViaTO.getEndereco().getNmMunicipio());
		
       	currentIspec.setFieldValue(UF_FIELD, acatamento2ViaTO.getEndereco().getDsUF());

        LINCStatus lincStatus = objLINC.makeLINCStatus();
		IspecModelRef newIspecRef = objLINC.makeIspecModelRef();
		int result = objLINC.transaction(currentIspec, newIspecRef, lincStatus);
		
		trataStatus(lincStatus, result);
		
		IspecModel resultIspec = newIspecRef.getIspec();
		
		acatamento2ViaTO.setMensagemErro2Via(resultIspec.getFieldValue(MENSAGEM_ERRO_FIELD));
		
		acatamento2ViaTO.setMensagemSucesso2Via(resultIspec.getFieldValue(MENSAGEM_SUCESSO_FIELD));

	}
	
	private static void setContas(IspecModel currentIspec, List<Conta> contas) {

		for (int i = 0; i < contas.size(); i++) {

	       	currentIspec.setFieldValueFromInt(PERIODO_FIELD, i, Integer.parseInt(contas.get(i).getDataReferenciaAnoJuliano()));
	
	       	currentIspec.setFieldValueFromInt(TIPO_CONTA_FIELD, i, Integer.parseInt(contas.get(i).getTpConta()));
	
	       	currentIspec.setFieldValueFromInt(SEQ_CTA2_FIELD, i, Integer.parseInt(contas.get(i).getNrSequenciaConta2()));

	       	currentIspec.setFieldValueFromInt(SEQ_CTA_FIELD, i, Integer.parseInt(contas.get(i).getNrSequenciaConta1()));

		}

	}

}
