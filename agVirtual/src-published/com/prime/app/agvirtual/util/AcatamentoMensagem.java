package com.prime.app.agvirtual.util;

/**
 * Classe utilitaria que converte as mensagens vindas do CSI.
 * @author gustavons
 *
 */
public class AcatamentoMensagem {
	
	public static String recuperaMensagem(String msg){
		
		if(msg.equals("E-MAIL.deve.ser.informado")){
			return "";
		}else if(msg.equals("RGI.Zerado")){
			return "N�mero de RGI Inv�lido";
		}else{
			return "Erro na grava��o dos dados no sistema";
		}
	}
	
	/*
	  Erro gen�rico (n�o identificado)	N�o foi poss�vel concluir sua solicita��o
	<Tentar Novamente> e <Concluir>
	Erro do sistema	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	 (RGI.Zerado)	N�mero de RGI Inv�lido
	(** Nome.do.Servico.nao.Identificado)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(E-MAIL.deve.ser.informado)	N/A
	(CCG03:Ligacao.nao.Localizada)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(CCG01:Imovel.nao.Localizado)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(CCG06:Solicitacao.em.Duplicidade)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(CEA07:Hidrometro.nao.Localizado) 	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(CTB23:Unidade.nao.Localizada)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(CTB01:quantidade.de.parecela.nao.permitida)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(CTB01:Servico.Indisponivel.para.Unidade)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(CTB01:Cobranca.a.Vista)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(RGI.Nao.localizado)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(Mes.e.Ano.referencia.invalido)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(Event:conta.nao.localizada)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(conta.nao.esta.em.aberto)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>
	(Conta.com.mais.de.5.emissoes)	Erro na grava��o dos dados no sistema
	<Tentar Novamente> e <Concluir>*/

}
