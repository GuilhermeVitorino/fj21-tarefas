<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="resources/css/tarefas.css" rel="stylesheet">	
	<link href="resources/jquery-ui-1.11.2.custom/jquery-ui.css" rel="stylesheet">
	<script type="text/javascript" src="resources/jquery-ui-1.11.2.custom/external/jquery/jquery.js" /></script>
    <script type="text/javascript" src="resources/jquery-ui-1.11.2.custom/jquery-ui.js" /></script>

<script type="text/javascript">
	function finalizaAgoraOLD(id) {
		$.get("finalizaTarefa?id=" + id, function(dadosDeResposta) {
				alert("Tarefa Finalizada!");
				$("#"+ id).html("Tarefa finalizada");
		});
	}
	function finalizaAgora(id) {
		$.post("finalizaTarefa", {'id' : id}, function(resposta) {
			// selecionando o elemento html através da
			// ID e alterando o HTML dele
			//$("#tarefa_finaliza_"+id).html("Tarefa finalizada");
			$("#tarefa_"+id).html(resposta);
		});
	}
	function removeAgora(id) {
		$.post("removeTarefa", {'id' : id}, function() {
			// selecionando o elemento html através da
			// ID e alterando o HTML dele
			$("#tarefa_"+id).closest("tr").hide();
		});
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<body>
	<a href="novaTarefa">Criar nova tarefa</a>
	<br /><br />
	<div id="divTabela">
	<table>
		<tr>
			<th>Id</th>
			<th>Descrição</th>
			<th>Finalizado?</th>
			<th>Data de finalização</th>
		</tr>
	
		<c:forEach items="${tarefas}" var="tarefa">
		<tr id="tarefa_${tarefa.id}">
			<td>${tarefa.id}</td>
			<td>${tarefa.descricao}</td>
			<c:if test="${tarefa.finalizado eq false}">
			
			<td><a href="#" onclick="finalizaAgora(${tarefa.id})">
				Finalizar agora!</a>
			</td>
	
				
			</c:if>
			<c:if test="${tarefa.finalizado eq true}">
				<td>Tarefa finalizada</td>
			</c:if>
			
			<td>
				<fmt:formatDate
				value="${tarefa.dataFinalizacao.time}"
				pattern="dd/MM/yyyy"/>
			</td>
			
			
			<td><a href="mostraTarefa?id=${tarefa.id}">Alterar</a></td>
			
			<td><a href="#" onclick="removeAgora(${tarefa.id})">
					Remover
				</a>
			</td>
		</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>