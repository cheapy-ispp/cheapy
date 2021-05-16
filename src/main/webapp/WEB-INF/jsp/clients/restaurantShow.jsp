<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<style>
.restaurantShowImage {
	border-radius: 8px;
	width:500px
}
</style>

<cheapy:layout pageName="client">

    <h2 class="titulo" style="font-family: 'Lobster'; text-align:center; font-size:200%; padding:10px; text-transform: uppercase;"><c:out value="${client.name}"/></h2>


	
    <table class="table table-striped" id="clientTable">
    	<thead>
        <tr>
            <th><fmt:message key="clientInit"/></th>
            <td><c:out value="${client.init}h"/></td>
        </tr>
        <tr>
            <th><fmt:message key="clientFinish"/></th>
            <td><c:out value="${client.finish}h"/></td>
        </tr>
		<tr>
            <th><fmt:message key="name"/></th>
            <td><c:out value="${client.name}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="email"/></th>
            <td><c:out value="${client.email}"/> </td>
        </tr>
        
        <tr>
            <th><fmt:message key="addressClient"/></th>
            <td><c:out value="${client.address}"/> </td>
        </tr>
        <tr>
            <th><fmt:message key="parking"/></th>
            <td>
            <c:if test="${client.parking == 'true'}">
				<c:out value="Si"/>
			</c:if>	
			<c:if test="${client.parking == 'false'}">
				<c:out value="No"/>
			</c:if>
            </td>
        </tr><tr>
            <th><fmt:message key="telephone"/></th>
            <td><c:out value="${client.telephone}"/> </td>
        </tr><tr>
            <th><fmt:message key="descriptionClient"/></th>
            <td><c:out value="${client.description}"/> </td>
        </tr><tr>
            <th><fmt:message key="municipio"/></th>
            <td><c:out value="${client.municipio}"/> </td>
        </tr><tr>
            <th><fmt:message key="foodClient"/></th>
            <td><c:out value="${client.food}"/> </td>     
        
        </thead>
    </table>
    
    <c:if test="${!(client.image eq null)}">
	    <div style="text-align: center;padding:20px">
	    	<img src="${client.image}" alt="La imagen no es válida" class="restaurantShowImage">
		</div>
	</c:if>
    
    <div style="font-size: 150%" >
	    <sec:authorize access="hasAnyAuthority('usuario')">
		    <fmt:message key="reviews"/>
				<cheapy:showStars  value="${reviews}"></cheapy:showStars> 
		</sec:authorize>
	</div>
    <div class="btn-menu">
    <button id='volver' type="button" onclick="history.back()" name="volver atrás" value="volver atrás" style="font-family: 'Lobster'; margin: 0px; margin-top: 10px;">Volver</button>
    <div class="text-left">
    <c:if test="${client.usuar.username != null}">
    	<spring:url value="/reviewsClientList/{client}/0" var="reviewsListUrl">
    		<spring:param name="client" value="${client.usuar.username}"/>
    	</spring:url>
    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(reviewsListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		Valoraciones</button>
 	</c:if>
 	<sec:authorize access="hasAnyAuthority('usuario')">
 	     
    	<spring:url value="/reviewsClient/new/{client}/" var="reviewsCreateUrl">
    		<spring:param name="client" value="${client.usuar.username}"/>
    	</spring:url>
    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(reviewsCreateUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		Valorar</button>
 	
 	</sec:authorize>
 	
 	<sec:authorize access="hasAnyAuthority('usuario')">
 	    <c:if test="${favoritos == 1}">
    	<spring:url value="/usuarios/favoritos/{clientId}/remove" var="favoritosUrl">
    		<spring:param name="clientId" value="${client.id}"/>
    	</spring:url>
    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(favoritosUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		Quitar de favoritos</button>
 		</c:if>
 		<c:if test="${favoritos == 2}">
    	<spring:url value="/usuarios/favoritos/{clientId}/add" var="favoritosUrl">
    		<spring:param name="clientId" value="${client.id}"/>
    	</spring:url>
    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(favoritosUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		Añadir a favoritos</button>
 		</c:if>
 	</sec:authorize>
 	
  	</div>
  	</div>

</cheapy:layout>
