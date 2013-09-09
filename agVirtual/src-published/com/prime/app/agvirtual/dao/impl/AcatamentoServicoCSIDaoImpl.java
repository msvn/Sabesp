package com.prime.app.agvirtual.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AcatamentoServicoCSIDao;
import com.prime.app.agvirtual.eae.EAEBusinessException;
import com.prime.app.agvirtual.eae.EAEDaoSupport;
import com.prime.app.agvirtual.eae.EAEException;
import com.prime.app.agvirtual.to.AcatamentoServicoTO;
import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.IspecModelRef;
import com.unisys.jellybeans.LINCStatus;

@Repository
public class AcatamentoServicoCSIDaoImpl extends EAEDaoSupport implements AcatamentoServicoCSIDao {
	
    private static final Logger logger = LoggerFactory.getLogger(AcatamentoServicoCSIDaoImpl.class);

	private static final String AVWEB_ISPEC = "AVWEB";

	private static final String RGI_FIELD = "NRRGILIG";

	private static final String GRUPO_SERVICO_FIELD = "CDGRPSERV";

	private static final String SUB_SERVICO_FIELD = "CDSERVCOM";

	private static final String E_MAIL_FIELD = "DSTELA50";

	private static final String OBS_FIELD = "DSTELA60";

	private static final String PRIORIDADE_FIELD = "NRTELA2B";

	private static final String QTDE_PARCELAS_FIELD = "NRTELA2A";

	private static final String SERVICO_ACATADO_FIELD = "DSTELA60B";

	private static final String PROTOCOLO_FIELD = "DSTELA30A";

	private static final String MENSAGEM_ERRO_FIELD = "DSTELA30";

	private static final String MENSAGEM_CSI_FIELD = "DSTELA60A";

	public AcatamentoServicoTO acataServico(AcatamentoServicoTO acatamentoTO) throws Exception, EAEBusinessException {

		try {
			
			openConnection();

			loadIspec(AVWEB_ISPEC);

			registraAcatamentoServico(acatamentoTO);

		} catch (EAEBusinessException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			closeConnection();
		}

		return acatamentoTO;
	}

	private void registraAcatamentoServico(AcatamentoServicoTO acatamentoTO) throws EAEException, EAEBusinessException {

		IspecModel currentIspec = objLINC.getCurrentIspec();

       	currentIspec.setFieldValueFromLong(RGI_FIELD, acatamentoTO.getRgi());
       	logger.debug("==> Acatamento= Param RGI->" + acatamentoTO.getRgi());
		
       	currentIspec.setFieldValueFromInt(GRUPO_SERVICO_FIELD, acatamentoTO.getCdGrupoServico());
       	logger.debug("==> Acatamento= Param Grupo Serviço->" + acatamentoTO.getCdGrupoServico());
		
		if (acatamentoTO.getCdSubServico() != null) {
    		currentIspec.setFieldValueFromInt(SUB_SERVICO_FIELD, acatamentoTO.getCdSubServico());
    		logger.debug("==> Acatamento= Param SubServico->" + acatamentoTO.getCdSubServico());
		}
		
		if (acatamentoTO.getSolicitante() != null && acatamentoTO.getSolicitante().getDsEmail() != null) {
    		 currentIspec.setFieldValue(E_MAIL_FIELD, acatamentoTO.getSolicitante().getDsEmail());
    		 logger.debug("==> Acatamento= Param Email->" + acatamentoTO.getSolicitante().getDsEmail());
		}

		if (acatamentoTO.getPrioridade() != null) {
    		currentIspec.setFieldValueFromInt(PRIORIDADE_FIELD, acatamentoTO.getPrioridade());
    		 logger.debug("==> Acatamento= Param Prioridade->" + acatamentoTO.getPrioridade());
		}

		if (acatamentoTO.getOrcamentoOferecido() != null && acatamentoTO.getOrcamentoOferecido().getQtParcela() != null) {
    		currentIspec.setFieldValueFromInt(QTDE_PARCELAS_FIELD, acatamentoTO.getOrcamentoOferecido().getQtParcela());
    		logger.debug("==> Acatamento= Param QtParcelas->" + acatamentoTO.getOrcamentoOferecido().getQtParcela());
		}

		if (acatamentoTO.getObs() != null) {
    		currentIspec.setFieldValue(OBS_FIELD, acatamentoTO.getObs());
    		logger.debug("==> Acatamento= Param Obs->" + acatamentoTO.getObs());
		}

        LINCStatus lincStatus = objLINC.makeLINCStatus();
		IspecModelRef newIspecRef = objLINC.makeIspecModelRef();
		int result = objLINC.transaction(currentIspec, newIspecRef, lincStatus);
		
		trataStatus(lincStatus, result);
		
		IspecModel resultIspec = newIspecRef.getIspec();
		
		// se algo deu errado, retorna apenas o grupo, subgrupo e o rgi da solicitacao
		acatamentoTO.setMensagemErroCSI(resultIspec.getFieldValue(MENSAGEM_ERRO_FIELD));
		logger.debug("==> Acatamento= Resultado Erro->" + acatamentoTO.getMensagemErroCSI());
		
		acatamentoTO.setMensagemCSI(resultIspec.getFieldValue(MENSAGEM_CSI_FIELD));
		logger.debug("==> Acatamento= Resultado MSG->" + acatamentoTO.getMensagemCSI());
		
		acatamentoTO.setMensagemServicoAcatadoCSI(resultIspec.getFieldValue(SERVICO_ACATADO_FIELD));
		logger.debug("==> Acatamento= Resultado Msg Acatado Csi->" + acatamentoTO.getMensagemServicoAcatadoCSI());
		
		acatamentoTO.setProtocolo(resultIspec.getFieldValue(PROTOCOLO_FIELD));
		logger.debug("==> Acatamento= Resultado Protocolo->" + acatamentoTO.getProtocolo());
		
		trataErroNegocio(acatamentoTO);
	}

	private void trataErroNegocio(AcatamentoServicoTO acatamentoTO) throws EAEBusinessException {
			
		if (acatamentoTO.getMensagemCSI() != null && acatamentoTO.getMensagemCSI().trim().length() > 0) {
			throw new EAEBusinessException(acatamentoTO.getMensagemCSI());
		}
	}

}
