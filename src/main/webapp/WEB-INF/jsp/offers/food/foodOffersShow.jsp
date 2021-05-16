<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<style>
.foodOfferImage {
	border-radius: 8px;
	width:500px
}
</style>

<cheapy:layout pageName="foodOffer">
	<script>
		$(document).ready(function(){
		  $('[data-toggle="desplegable"]').popover();   
		});
	</script>

    <h2 class="titulo" style="font-family: 'Lobster'; text-align:left; font-size:200%; padding:10px; margin-bottom:20px;"><fmt:message key="foodOffer"/>
    	<a title="Información" data-toggle="desplegable" data-trigger="hover" data-placement="bottom" data-content="Descuento al consumir el plato indicado en la oferta">
    	<span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="padding: 5px"> </span></a>
    </h2>


	
    <table class="table table-striped" id="foodOfferTable">
    	<thead>
    	<tr>
            <th><fmt:message key="client"/></th>
            <td><c:out value="${foodOffer.client.name}"/> </td>
        </tr>
        <tr>
            <th><fmt:message key="offerBeginning"/></th>
            <td><c:out value="${localDateTimeFormat.format(foodOffer.start)}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="offerEnding"/></th>
            <td><c:out value="${localDateTimeFormat.format(foodOffer.end)}"/></td>
        </tr>
		<tr>
            <th><fmt:message key="foodInOffer"/></th>
            <td><c:out value="${foodOffer.food}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="price"/></th>
            <td><c:out value="${foodOffer.price}€"/> </td>
        </tr>
        <tr>
            <th><fmt:message key="discount"/></th>
            <td><c:out value="${foodOffer.discount}%"/> </td>
        </tr>
        <tr>
            <th><fmt:message key="newPrice"/></th>
            <td><c:out value="${foodOffer.newPrice}€"/> </td>
        </tr>
        <tr>
            <th><fmt:message key="municipio"/></th>
            <td><c:out value="${foodOffer.client.municipio}"/> </td>
        </tr>
		<sec:authorize access="isAuthenticated()">
        <tr>
            <th><fmt:message key="offerCode"/></th>
            <td><b><c:out value="${foodOffer.code}"/></b></td>
        </tr>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
        <tr>
            <th><fmt:message key="offerCode"/></th>
            <td><b>Para acceder al código debe iniciar sesión</b></td>
        </tr>
        </sec:authorize>
        </thead>
    </table>
    
    <c:if test="${!(foodOffer.image eq null)}">
	    <div style="text-align: center;padding:20px">
	    	<img src="${foodOffer.image}" alt="La imagen no es válida" class="foodOfferImage">
		</div>
	</c:if>
	
    <div class="btn-menu">
	    
	<sec:authorize access="hasAnyAuthority('client')">
	<sec:authentication var="principal" property="principal" />
      <div class="btns-edit">
      	<c:if test="${ principal.username eq foodOffer.client.usuar.username}">
      		<c:if test="${foodOffer.status eq 'active' || foodOffer.status eq 'hidden' }">
      		
		        <spring:url value="{foodOfferId}/edit" var="editUrl">
		        <spring:param name="foodOfferId" value="${foodOffer.id}"/>
		        </spring:url>
		        <button type="button" role="link" onclick="window.location='${fn:escapeXml(editUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		            <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
		          Editar oferta</button>
	         </c:if>
		
		<c:if test="${foodOffer.status eq 'hidden' }">
	        <spring:url value="{foodOfferId}/activate" var="activateUrl">
	        <spring:param name="foodOfferId" value="${foodOffer.id}"/>
	        </spring:url>
	        <button type="button" role="link" onclick="window.location='${fn:escapeXml(activateUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
	            <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
	          Activar oferta</button>
		</c:if>
		
		<c:if test="${foodOffer.status eq 'active' }">
	        <spring:url value="{foodOfferId}/disable" var="deactivateUrl">
	        <spring:param name="foodOfferId" value="${foodOffer.id}"/>
	        </spring:url>
	        <button type="button" role="link" onclick="window.location='${fn:escapeXml(deactivateUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
	            <span class="glyphicon glyphicon glyphicon-trash" aria-hidden="true" style="padding: 5px"> </span>
	          Desactivar oferta</button>
	        
         </c:if>
        </c:if>
        
      </div>
     <div class="eliminar">
      <c:if test="${!(foodOffer.image eq null)}">
	        <spring:url value="{foodOfferId}/delete/image" var="deleteImageUrl">
	        <spring:param name="foodOfferId" value="${foodOffer.id}"/>
	        </spring:url>
	        <button type="button" role="link" onclick="window.location='${fn:escapeXml(deleteImageUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
	            <span class="glyphicon glyphicon glyphicon-trash" aria-hidden="true" style="padding: 5px"> </span>
	          Eliminar imagen</button>
         </c:if>
      </div>
      </sec:authorize>
      <button id='volver' type="button" onclick="history.back()" name="volver atrás" value="volver atrás" style="font-family: 'Lobster'; font-size: 15px;">Volver</button>
    </div>
  	

</cheapy:layout>
