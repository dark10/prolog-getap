<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="org.ldv.sio.getap.app.service.impl.DBManagerGeTAP"%>


<h3>Mes demandes de consommation de TAP</h3>

<c:if test="${empty mesdctaps}">
	Il n'y a encore aucune demande. 
</c:if>

<c:if test="${not empty mesdctaps}">

	<table id="myTable" class="tablesorter">
		<thead>
			<tr>
				<th>Professeurs</th>
				<th>Date</th>
				<th>Temps (min)</th>
				<th>Type d'aide</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${mesdctaps}" var="dctap">
				<tr>
					<td>${dctap.prof.nom} ${dctap.prof.prenom}</td>
					<td>${dctap.dateAction}</td>
					<td>${dctap.minutes}</td>
					<td>${dctap.accPers.nom}</td>
					<c:if test="${dctap.etat < 2 }">
						<td><a
							href="<c:url value="/app/eleve/edit?id=${dctap.id}" />"><input
								type="button" value="Modifier"> </a></td>
					</c:if>
					<c:if test="${dctap.etat > 1 }">
						<td><input
							title="Modifié par le professeur" type="button" value="Modifier"
							disabled="true"></td>
					</c:if>
					<td><a href=""
						onclick="if(confirm('Voulez-vous vraiment supprimer cette demande ?')){window.location.href='delete/${dctap.id}';}"><input
							type="button" value="Supprimer"> </a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

<div class="buttonGroup">
	<a href="<c:url value="/app/eleve/index" />"
		style="text-decoration: none"><input type="button" value="Retour">
	</a>
</div>
