<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

<cheapy:layout pageName="nuOffer">
	<script>
		$(document).ready(function(){
		  $('[data-toggle="desplegable"]').popover();   
		});
	</script>
	
	<h2 class="titulo" style="font-family: 'Lobster'; text-align:left; font-size:200%; padding:10px; margin-bottom:20px;"><fmt:message key="nuOffer" />
		<a title="Información" data-toggle="desplegable" data-trigger="hover" data-placement="bottom" data-content="Descuento al consumir con más comensales que alguno de los tres posibles objetivos">
    	<span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="padding: 5px"> </span></a>
	</h2>

    <table class="table table-striped" id="nuOfferTable">
    	<tr>
            <th><fmt:message key="client"/></th>
            <td><c:out value="${nuOffer.client.name}"/> </td>
        </tr>
        <tr>
            <th><fmt:message key="offerBeginning"/></th>
            <td><c:out value="${localDateTimeFormat.format(nuOffer.start)}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="offerEnding"/></th>
            <td><c:out value="${localDateTimeFormat.format(nuOffer.end)}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="goldGoal"/>
            <i class="fas fa-bullseye" id="goldGD"></i>
            </th>
            <td><c:out value="${nuOffer.gold} comensales" /></td>
        </tr>
        <tr>
            <th><fmt:message key="goldDiscount"/> 
            <i class="fas fa-medal" id="goldGD"></i>
            </th>
            <td><c:out value="${nuOffer.discountGold}%"/></td>
        </tr>
        <tr>
            <th><fmt:message key="silverGoal"/>
            <i class="fas fa-bullseye" id="silverGD"></i>
            </th>
            <td><c:out value="${nuOffer.silver} comensales"/></td>
        </tr>
        <tr>
            <th><fmt:message key="silverDiscount"/>
            <i class="fas fa-medal" id="silverGD"></i>
            </th>
            <td><c:out value="${nuOffer.discountSilver}%"/></td>
        </tr>
        <tr>
            <th><fmt:message key="bronzeGoal"/>
            <i class="fas fa-bullseye" id="bronzeGD"></i>
            </th>
            <td><c:out value="${nuOffer.bronze} comensales"/></td>
        </tr>
        <tr>
            <th><fmt:message key="bronzeDiscount"/>
            <i class="fas fa-medal" id="bronzeGD"></i>
            </th>
            <td><c:out value="${nuOffer.discountBronze}%"/></td>
        </tr>
        <tr>
            <th><fmt:message key="municipio"/></th>
            <td><c:out value="${nuOffer.client.municipio}"/></td>
        </tr>
        <sec:authorize access="isAuthenticated()">
        <tr>
            <th><fmt:message key="offerCode"/></th>
            <td><b><c:out value="${nuOffer.code}"/></b></td>
        </tr>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
        <tr>
            <th><fmt:message key="offerCode"/></th>
            <td><b>Para acceder al código debe iniciar sesión</b></td>
        </tr>
        </sec:authorize>
       
                    
                
    </table>

    <div class="btn-menu">
	    
	<sec:authorize access="hasAnyAuthority('client')">
	<sec:authentication var="principal" property="principal" />
		<div class="btns-edit">
		<button  type="button" onclick="history.back()" name="volver atrás" value="volver atrás" style="font-family: 'Lobster'; font-size: 23.5px;">Volver</button>
		<c:if test="${ principal.username eq nuOffer.client.usuar.username}">
			<c:if test="${nuOffer.status eq 'active' || nuOffer.status eq 'hidden' }">
			    <spring:url value="{nuOfferId}/edit" var="editUrl">
			    <spring:param name="nuOfferId" value="${nuOffer.id}"/>
			    </spring:url>
			    <button type="button" role="link" onclick="window.location='${fn:escapeXml(editUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
	            <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
		        Editar oferta</button>
	        </c:if>
			<c:if test="${nuOffer.status eq 'hidden' }">
		        <spring:url value="{nuOfferId}/activate" var="activateUrl">
		        <spring:param name="nuOfferId" value="${nuOffer.id}"/>
		        </spring:url>
		        <button type="button" role="link" onclick="window.location='${fn:escapeXml(activateUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		            <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
			        Activar oferta</button>
			</c:if>
			
			<c:if test="${nuOffer.status eq 'active' }">
				<spring:url value="{nuOfferId}/disable" var="deactivateUrl">
			    <spring:param name="nuOfferId" value="${nuOffer.id}"/>
			    </spring:url>
			    <button type="button" role="link" onclick="window.location='${fn:escapeXml(deactivateUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		           <span class="glyphicon glyphicon glyphicon-trash" aria-hidden="true" style="padding: 5px"> </span>
			        Desactivar oferta</button>
		    </c:if>
		</c:if>
		
	    </div>
	    
	    </sec:authorize>
    </div>
	

</cheapy:layout>
