<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<br />

<h5 style="position: relative; top: 35px;">Administration
	logistique</h5>
<div id="accordion">
	<h3>
		<a href="#">Gestion d'utilisateur</a>
	</h3>
	<div>
		<table>
			<tr>
				<td><a href="<c:url value="/app/admin/ajoutUser" />"><img
						src="<c:url value="../../images/AjouterUser.png"/>" />
						<div>Ajouter un Utilisateur</div> </a></td>
				<td><a href="<c:url value="/app/admin/ajoutUsers" />"><img
						src="<c:url value="../../images/AjouterUser.png"/>" />
						<div>Import Utilisateur via CSV</div> </a></td>
			</tr>
		</table>
	</div>
	<h3>
		<a href="#">Gestion de recherche d'utilisateurs</a>
	</h3>
	<div>
		<table>
			<tr>
				<form:form modelAttribute="userSearchCriteria" action="dosearchUser"
					method="get">
					<form:errors path="*" cssClass="errors" />
					<div class="section">
						<fieldset>
							<div class="form-row">
								<label for="query">Recherche d'utilisateurs :</label>
								<div class="input">
									<form:input path="query" />
									<input type="image" src="../../images/search.png"
										alt="Rechercher"
										onmouseover="this.src='../../images/searchHover.png';"
										onmouseout="this.src='../../images/search.png';" />
								</div>
							</div>
						</fieldset>
					</div>
				</form:form>
			</tr>
			<tr>
				<form:form modelAttribute="userSearchCriteria" action="dosearchProf"
					method="get">
					<form:errors path="*" cssClass="errors" />
					<div class="section">
						<fieldset>
							<div class="form-row">
								<label for="query">Recherche de professeurs :</label>
								<div class="input">
									<form:input path="query" />
									<input type="image" src="../../images/search.png"
										alt="Rechercher"
										onmouseover="this.src='../../images/searchHover.png';"
										onmouseout="this.src='../../images/search.png';" />
								</div>
							</div>
						</fieldset>
					</div>
				</form:form>
			</tr>
			<tr>
				<form:form modelAttribute="userSearchCriteria"
					action="dosearchForClasse" method="get">
					<form:errors path="*" cssClass="errors" />
					<div class="section">
						<fieldset>
							<div class="form-row">
								<label for="query">Recherche par classe :</label>
								<div>
									<form:select path="query" items="${lesClasses}" itemValue="nom"
										itemLabel="nom"></form:select>
									<input type="image" src="../../images/search.png"
										alt="Rechercher"
										onmouseover="this.src='../../images/searchHover.png';"
										onmouseout="this.src='../../images/search.png';" />
								</div>
							</div>
						</fieldset>
					</div>
				</form:form>
			</tr>
		</table>
	</div>
	<h3>
		<a href="#">Recherche des demandes</a>
	</h3>
	<div>
		<table>
			<tr>
				<form:form modelAttribute="userSearchCriteria"
					action="doSearchDctap" method="get">
					<form:errors path="*" cssClass="errors" />
					<div class="section">
						<fieldset>
							<div class="form-row">
								<label for="query">Recherche par �l�ve :</label>
								<div class="input">
									<form:input path="query" />
									<input type="image" src="../../images/search.png"
										alt="Rechercher"
										onmouseover="this.src='../../images/searchHover.png';"
										onmouseout="this.src='../../images/search.png';" />
								</div>
							</div>
						</fieldset>
					</div>
				</form:form>
			</tr>
			<tr>
				<form:form modelAttribute="userSearchCriteria"
					action="doSearchDctap" method="get">
					<form:errors path="*" cssClass="errors" />
					<div class="section">
						<fieldset>
							<div class="form-row">
								<label for="query">Recherche par professeur :</label>
								<div class="input">
									<form:input path="query" />
									<input type="image" src="../../images/search.png"
										alt="Rechercher"
										onmouseover="this.src='../../images/searchHover.png';"
										onmouseout="this.src='../../images/search.png';" />
								</div>
							</div>
						</fieldset>
					</div>
				</form:form>
			</tr>
			<tr>
				<form:form modelAttribute="userSearchCriteria"
					action="doSearchDctapClasse" method="get">
					<form:errors path="*" cssClass="errors" />
					<div class="section">
						<fieldset>
							<div class="form-row">
								<label for="query">Recherche par classe :</label>
								<div>
									<form:select path="query" items="${lesClasses}" itemValue="nom"
										itemLabel="nom"></form:select>
									<input type="image" src="../../images/search.png"
										alt="Rechercher"
										onmouseover="this.src='../../images/searchHover.png';"
										onmouseout="this.src='../../images/search.png';" />
								</div>
							</div>
						</fieldset>
					</div>
				</form:form>
			</tr>
		</table>
	</div>
</div>